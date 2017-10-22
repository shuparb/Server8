package com.shubhamparab.server8;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Shubham on 22-06-2017.
 */

public class RemoveServer extends Activity implements AdapterView.OnItemClickListener{
    FileManager fileManager = null;
    RelativeLayout mainLayout = null;   //main layout for the activity
    ListView serverList = null;     //list of all servers
    ArrayAdapter<String> serverListAdapter = null;  //serverListAdapter for serverList
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUI();
        setContentView(mainLayout);
    }

    public void buildUI(){
        fileManager = new FileManager(this);
        mainLayout = new RelativeLayout(this);
        serverList = new ListView(this);
        serverListAdapter = new ArrayAdapter<String>(this, R.layout.layout_server_list, R.id.text);
        serverList.setOnItemClickListener(this);
        serverList.setAdapter(serverListAdapter);
        fillServerList();
        mainLayout.addView(serverList);
    }

    /*
        Fill the serverList for listing
     */
    public void fillServerList(){
        try{
            BufferedReader br = new BufferedReader(new FileReader(fileManager.serverList));
            String length = br.readLine();
            int len = Integer.decode(length);
            for(int i=0;i<len;i++){
                serverListAdapter.add(br.readLine());
            }
        }catch(FileNotFoundException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }catch(IOException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        fileManager.removeFolderInServerDir(parent.getItemAtPosition(position).toString());
        setResult(1);
        finish();
    }
}
