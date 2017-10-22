package com.shubhamparab.server8;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Shubham on 22-06-2017.
 */

public class AddServer extends Activity {
    FileManager fileManager = null;
    EditText serverName = null;
    Button createServer = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_server);
        buildUI();
    }

    public void buildUI(){
        fileManager = new FileManager(getApplicationContext());
        serverName = (EditText)findViewById(R.id.edit_server_name);
        createServer = (Button)findViewById(R.id.button_create_server);
        createServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fileManager.createFolderInServerDir(serverName.getText().toString())){
                    setResult(1);
                    finish();
                }else{
                    setResult(0);
                    finish();
                }
            }
        });
    }
}
