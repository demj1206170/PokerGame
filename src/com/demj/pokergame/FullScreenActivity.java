package com.demj.pokergame;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;


public class FullScreenActivity extends Activity {
    View mDecorView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	mDecorView= getWindow().getDecorView();
       super.onCreate(savedInstanceState);
       
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
            super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            mDecorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }
    
}
