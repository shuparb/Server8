package com.shubhamparab.server8;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.database.DataSetObserver;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Shubham on 20-06-2017.
 */
/**
    ServersFragment: this fragment lists all servers user has created which user can click to get in-depth information about the servers
 */
public class ServersFragment extends Fragment implements AdapterView.OnItemClickListener{
    RelativeLayout mainLayout = null;   //main layout for ServerFragment
    ListView serversList = null;        //list of all the directories inside serversDir
    FileManager fileManager =  null;    //for getting all the files and directories
    ArrayAdapter<String> serversListAdapter = null; //adapter for the serversList
    Server server = null;               //server

    boolean isAnyServerRunning = false;
    String runningServer = "";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        buildUI();
        return mainLayout;
    }

    public void buildUI(){
        mainLayout = new RelativeLayout(getContext());
        serversList = new ListView(getContext());
        serversList.setOnItemClickListener(this);
        fileManager = new FileManager(getActivity());
        fillServersList();
        serversList.setAdapter(serversListAdapter);
        mainLayout.addView(serversList);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String selectedServer = parent.getItemAtPosition(position).toString();
        if(isAnyServerRunning){
            if(runningServer==selectedServer){
                if(server!=null){
                    server.stop();
                    server = null;
                    Toast.makeText(getContext(), "Server: "+selectedServer+" stopped", Toast.LENGTH_SHORT).show();
                    runningServer = "";
                    isAnyServerRunning = false;
                }
            }else{
                Toast.makeText(getContext(), "Another server: "+selectedServer+" is running", Toast.LENGTH_SHORT).show();
            }
        }else{
            isAnyServerRunning = true;
            runningServer = selectedServer;
           server = new Server(8080, getActivity(), new File(fileManager.serversDir+"/"+selectedServer+"/"));
            Toast.makeText(getContext(), "server "+selectedServer+" started", Toast.LENGTH_SHORT).show();
            server.start();
        }
    }

    /**
     * fill up the list to display all the server directories
     * to be used by the users
     */
    public void fillServersList(){
        serversListAdapter = new ArrayAdapter<String>(getActivity(), R.layout.layout_server_list, R.id.text);
        File[] serversDir = fileManager.serversDir.listFiles();
        for(int i=0;i<serversDir.length;i++){
            serversListAdapter.add(serversDir[i].getName());
        }
    }
}
