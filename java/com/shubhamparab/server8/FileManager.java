package com.shubhamparab.server8;

/**
 * Created by Shubham on 22-06-2017.
 */

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * FileManager: this class controls all the file insert delete in server folder created
 */
public class FileManager {
    Context mContext = null;    //Application Context needed for file management
    File serversDir = null;      //Directory to store all servers needed
    File serverList = null;     //text file to store list of servers and there information(detailed)

    //CONSTANTS
    public String rootDirPath = null;   //root Directory Path

    /**
     * Constructor File Manger
     * @param context   used to access files
     */
    public FileManager(Context context){
        mContext = context;
        rootDirPath = Environment.getExternalStorageDirectory().getPath()+"/";
        doFiles();
    }

    /**
     * check all required folders and files
     */
    public void doFiles(){
        serversDir = new File(rootDirPath+"/servers/");
        serverList = new File(rootDirPath+"serverList.txt");
        if(!serversDir.exists()){    //if server Directory doesn't exist
            serversDir.mkdirs();     //create it
        }
        updateServerList();
    }

    /**
     * create Folder in serverDir for user to save files in
     */
    public boolean createFolderInServerDir(String FolderName){
        File file = new File(serversDir, FolderName);
        if(file.exists()){
            Toast.makeText(mContext, "server already exist", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            file.mkdirs();
            updateServerList();
            return true;
        }
    }

    public void updateServerList(){
        try{
            FileOutputStream output = new FileOutputStream(serverList);
            String data = "";
            data += serversDir.list().length+"\n";
            String[] servers = serversDir.list();
            for(int i=0;i<servers.length;i++){
                data += servers[i]+"\n";
            }
            output.write(data.getBytes());
        }catch(FileNotFoundException e){
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }catch(IOException e){
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public boolean removeFolderInServerDir(String FolderName){
        File file = new File(serversDir, FolderName);
        if(file.exists()){
            deleteFilesInsideFolder(file);
            file.delete();
            updateServerList();
            Toast.makeText(mContext, "server removed", Toast.LENGTH_SHORT).show();
            return true;
        }else{
            Toast.makeText(mContext, "server doesn't exist", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void deleteFilesInsideFolder(File folder){
        File[] files = folder.listFiles();
        for(int i=0; i<files.length; i++){
            if(files[i].exists()){
                if(files[i].isDirectory()){
                    deleteFilesInsideFolder(files[i]);
                    files[i].delete();
                }else{
                    files[i].delete();
                }
            }
        }
    }
}
