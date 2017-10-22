package com.shubhamparab.server8;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by Shubham on 25-06-2017.
 */

public class Server implements Runnable{
    final String TAG = "Server";
    final int port;   //port where server is to be created
    Context context = null;
    ServerSocket serverSocket = null;
    File serverDir = null;

    boolean isRunning = false;
    public Server(int port, Context context, File serverDir){
        this.port = port;
        this.context = context;
        this.serverDir = serverDir;
    }

    public void start(){
        try{
            serverSocket = new ServerSocket(port);
        }catch(IOException e){
            Log.e(TAG, "Web server error");
        }
        if(serverSocket!=null) {
            Toast.makeText(context, "Local Port: " + serverSocket.getLocalPort(), Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "Host Name: " + serverSocket.getInetAddress().getHostName() + " Host Address: " + serverSocket.getInetAddress().getHostAddress(), Toast.LENGTH_SHORT).show();
        }
        isRunning = true;
        new Thread(this).start();
    }

    public void stop(){
        try {
            isRunning = false;
            if (null != serverSocket) {
                serverSocket.close();
                serverSocket = null;
            }
        } catch (IOException e) {
            Log.e(TAG, "Error closing the server socket.", e);
        }
    }
    @Override
    public void run() {
        if(serverSocket!=null) {
            try {
                while (isRunning) {
                    Socket socket = serverSocket.accept();
                    handle(socket);
                    socket.close();
                }
            } catch (SocketException e) {
                // The server was stopped; ignore.
            } catch (IOException e) {
                Log.e(TAG, "Web server error.", e);
            }
        }
    }

    /**
     * Respond to a request from a client.
     *
     * @param socket The client socket.
     * @throws IOException
     */
    private void handle(Socket socket) throws IOException {
        BufferedReader reader = null;
        PrintStream output = null;
        try {
            String route = null;
            // Read HTTP headers and parse out the route.
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            while (!TextUtils.isEmpty(line = reader.readLine())) {
                Log.d("REQ", line);
                if (line.startsWith("GET /")) {
                    int start = line.indexOf('/') + 1;
                    int end = line.indexOf(' ', start);
                    route = line.substring(start, end);
                    if(route.isEmpty())
                        route="index.html";
                    Log.d("REQUEST", "-"+route);
                    break;
                }
            }
            // Output stream that we send the response to
            output = new PrintStream(socket.getOutputStream());

            // Prepare the content to send.
            if (null == route) {
                writeServerError(output);
                return;
            }
            byte[] bytes = loadContent(route);
            if (null == bytes) {
                writeServerError(output);
                return;
            }

            // Send out the content.
            output.println("HTTP/1.0 200 OK");
            output.println("Content-Type: " + detectMimeType(route));
            output.println("Content-Length: " + bytes.length);
            output.println();
            output.write(bytes);
            output.flush();
        } finally {
            if (null != output) {
                output.close();
            }
            if (null != reader) {
                reader.close();
            }
        }
    }

    /**
     * Writes a server error response (HTTP/1.0 500) to the given output stream.
     *
     * @param output The output stream.
     */
    private void writeServerError(PrintStream output) {
        output.println("HTTP/1.0 500 Internal Server Error");
        output.flush();
    }

    /**
     * Loads all the content of {@code fileName}.
     *
     * @param fileName The name of the file.
     * @return The content of the file.
     * @throws IOException
     */
    private byte[] loadContent(String fileName) throws IOException {
        String index = "<html>\n" +
                "<head><title>index</title></head>\n" +
                "<body>\n" +
                "\t<h4>INDEX</h4>\n" +
                "\t<a href=\"author.html\">author</a>\n" +
                "\t<br>\n" +
                "\t<a href=\"doodle.html\">doodle</a>\n" +
                "</body>\n" +
                "</html>";
        InputStream input = null;
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            input = open(fileName);
            if(input==null)
                throw new FileNotFoundException();
            byte[] buffer = new byte[1024];
            int size;
            while (-1 != (size = input.read(buffer))) {
                output.write(buffer, 0, size);
            }
            output.flush();
            return output.toByteArray();
        } catch (FileNotFoundException e) {
            return null;
        } finally {
            if (null != input) {
                input.close();
            }
        }
    }

    /**
     * Detects the MIME type from the {@code fileName}.
     *
     * @param fileName The name of the file.
     * @return A MIME type.
     */
    private String detectMimeType(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return null;
        } else if (fileName.endsWith(".html")) {
            return "text/html";
        } else if (fileName.endsWith(".js")) {
            return "application/javascript";
        } else if (fileName.endsWith(".css")) {
            return "text/css";
        } else {
            return "application/octet-stream";
        }
    }

    public InputStream open (String fileName) throws FileNotFoundException{
        File mainFile = new File(serverDir, fileName);
        if(mainFile.exists()){
            return new FileInputStream(mainFile);
        }
        return null;
    }

}
