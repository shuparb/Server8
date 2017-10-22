package com.shubhamparab.server8;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Shubham on 20-06-2017.
 */
/**
    AboutFragment: this fragment display author's information
 */
public class AboutFragment extends Fragment {
    RelativeLayout mainLayout = null;   //main layout used by AboutFragment
    TextView authorName = null;     //author's name here
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
        mainLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
        authorName = new TextView(getContext());
        authorName.setText("About");
        authorName.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        mainLayout.addView(authorName, params);
    }
}
