package com.shubhamparab.server8;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentContainer;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.TypedArrayUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;
import java.util.jar.Manifest;

/**
 * Created by Shubham on 20-06-2017.
 */
/**
    MainActivity: this is the main interface user will see.
 */
public class MainActivity extends Activity implements Button.OnClickListener{
    LinearLayout mainLayout = null;     //the activity layout
    RelativeLayout fragmentContainer = null;  //fragment container to display menu items
    LinearLayout menuBar = null;     //the menu bar on top of the activity
    HorizontalScrollView menuScroll = null;      //the scroll used to scroll menuBar

    //File Manager
    FileManager fileManager = null;

    //Fragments
    AboutFragment aboutFragment = null;     //Fragment for About coloumn
    AddRemoveFragment addremoveFragment = null; //Fragment for AddRemoveFragment
    ServersFragment serversFragment = null; //Fragment for serversFragment
    NetworkInfoFragment networkInfoFragment = null; //Fragment for networkInfoFragment

    Button menuButtons[];

    //CONSTANTS
    int BUTTON_WIDTH = 400;     //width of menu button
    int BUTTON_HEIGHT = 300;    //height of menu button
    String[] BUTTON_TITLES = {"ADD/REMOVE", "SERVERS", "NETWORK INFO", "ABOUT"};
    int[] BUTTON_IDS = {R.id.button_add_remove, R.id.button_servers, R.id.button_network_info, R.id.button_about};

    public static final int REQUEST_READ_EXTERNAL = 234141231;
    public static final int REQUEST_WIFI_STATE = 231312313;
    public static final int REQUEST_WRITE_EXTERNAL = 243561;
    public static final int REQUEST_INTERNET = 4256231;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildUI();
        setContentView(mainLayout);
    }
    /*
        building the activity User Interface
     */
    public void buildUI(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            takePermissionFromNougat();
        }
        mainLayout = new LinearLayout(this);
        fragmentContainer = new RelativeLayout(this);
        fragmentContainer.setId(R.id.layout_fragment_container);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.BELOW, R.id.horizontal_menu_scroll);

        fragmentContainer.setLayoutParams(params);

        mainLayout.setOrientation(LinearLayout.VERTICAL);

        menuBar = new LinearLayout(this);
        menuBar.setOrientation(LinearLayout.HORIZONTAL);
        menuBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        menuScroll = new HorizontalScrollView(this);
        menuScroll.setId(R.id.horizontal_menu_scroll);
        menuScroll.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        menuScroll.setHorizontalScrollBarEnabled(true);

        buildMenuButtons();
        buildFragments();
        addMenuButtons();

        menuScroll.addView(menuBar);
        mainLayout.addView(menuScroll);
        mainLayout.addView(fragmentContainer);
        addFirstFragment();
    }

    /**
     * add first fragment when the activity starts
     */
    public void addFirstFragment(){
        if(fileManager==null){
            fileManager = new FileManager(getApplicationContext());
        }
        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.layout_fragment_container, addremoveFragment, "ADDREMOVE");
        transaction.commit();
    }
    /*
        building the menuButtons
     */
    public void buildMenuButtons(){
        menuButtons = new Button[BUTTON_TITLES.length];
        for(int i =0;i<BUTTON_TITLES.length; i++){
            menuButtons[i] = new Button(this);
            menuButtons[i].setWidth(BUTTON_WIDTH);
            menuButtons[i].setTextSize(14);
            menuButtons[i].setTypeface(Typeface.DEFAULT_BOLD);
            menuButtons[i].setHeight(BUTTON_HEIGHT);
            menuButtons[i].setText(BUTTON_TITLES[i]);
            menuButtons[i].setTextColor(Color.WHITE);
            menuButtons[i].setTextSize(18);
            menuButtons[i].setBackgroundDrawable(getDrawable(R.drawable.menu_button));
            menuButtons[i].setId(BUTTON_IDS[i]);
            menuButtons[i].setOnClickListener(this);
        }
    }
    /*
         adding menuButtons to the menuBar
     */
    public void addMenuButtons(){
        for(int i=0;i<menuButtons.length;i++){
            menuBar.addView(menuButtons[i]);

        }
    }
    android.app.FragmentManager manager = null;
    android.app.FragmentTransaction transaction = null;
    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_about:
                transaction = manager.beginTransaction();
                transaction.replace(R.id.layout_fragment_container, aboutFragment);
                transaction.commit();
                break;
            case R.id.button_add_remove:
                transaction = manager.beginTransaction();
                transaction.replace(R.id.layout_fragment_container, addremoveFragment);
                transaction.commit();
                break;
            case R.id.button_network_info:
                transaction = manager.beginTransaction();
                transaction.replace(R.id.layout_fragment_container, networkInfoFragment);
                transaction.commit();
                break;
            case R.id.button_servers:
                transaction = manager.beginTransaction();
                transaction.replace(R.id.layout_fragment_container, serversFragment);
                transaction.commit();
                break;
        }
    }
    //initialize all fragments
    public void buildFragments(){
        aboutFragment = new AboutFragment();
        addremoveFragment = new AddRemoveFragment();
        networkInfoFragment = new NetworkInfoFragment();
        serversFragment = new ServersFragment();
    }

    //Nougat grant permission at run time
    public void takePermissionFromNougat(){
        if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL);
        }
        if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL);
        }
        if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.INTERNET}, REQUEST_INTERNET);
        }
        if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_WIFI_STATE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_WIFI_STATE}, REQUEST_WIFI_STATE);
        }
    }
}
