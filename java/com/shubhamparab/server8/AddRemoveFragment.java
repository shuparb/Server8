package com.shubhamparab.server8;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Shubham on 20-06-2017.
 */

/**
 * Here user can add new servers and remove earlier created servers
 */
public class AddRemoveFragment extends Fragment {
    RelativeLayout mainLayout = null;   //main layout used by AboutFragment

    Button addServer = null;    //add new server
    Button removeServer = null; //remove existing server

    //CONSTANTS
    public final String addServerText = "ADD SERVER";
    public final String removeServerText = "REMOVE SERVER";
    public static final int addServerRequestCode = 1010101;
    public static final int removeServerRequestCode = 20210213;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        buildUI();
        return mainLayout;
    }
    /**
     build AboutFragment UI
     */
    public void buildUI(){
        mainLayout = new RelativeLayout(getContext());
        addServer = new Button(getContext());
        addServer.setId(R.id.button_addServer);
        addServer.setText(addServerText);
        addServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), AddServer.class), addServerRequestCode);
            }
        });
        removeServer = new Button(getContext());
        removeServer.setText(removeServerText);
        removeServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), RemoveServer.class), removeServerRequestCode);
            }
        });
        RelativeLayout.LayoutParams addParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        mainLayout.addView(addServer, addParams);
        RelativeLayout.LayoutParams removeParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        removeParams.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
        mainLayout.addView(removeServer, removeParams);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==addServerRequestCode){
            if(resultCode==0){
                Toast.makeText(getContext(), "server creation failed!", Toast.LENGTH_SHORT).show();
            }else if(resultCode==1){
                Toast.makeText(getContext(), "server creation successful", Toast.LENGTH_SHORT).show();
            }
        }else if(requestCode==removeServerRequestCode){
            if(resultCode==0){
                Toast.makeText(getContext(), "server removal failed!", Toast.LENGTH_SHORT).show();
            }else if(resultCode==1){
                Toast.makeText(getContext(), "server removal successful!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
