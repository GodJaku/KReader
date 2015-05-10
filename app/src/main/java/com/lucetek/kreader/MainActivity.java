package com.lucetek.kreader;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.lucetek.kreader.fragments.MainFragment;
import com.lucetek.kreader.fragments.TextViewFragment;

import static com.lucetek.kreader.Constants.COMIC;
import static com.lucetek.kreader.Constants.LIST;
import static com.lucetek.kreader.Constants.PDF;
import static com.lucetek.kreader.Constants.TEXT;


public class MainActivity extends FragmentActivity {
    private long backPressedTime;
    private Toast toast= null;

    private FragmentTransaction frgTransaction= null;

    private int mCurrentFrg= LIST;
    private MainFragment mMainFragment= null;
    private TextViewFragment mTextViewer= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        Log.d("test activity", Integer.toString(getResources().getDimensionPixelSize(getResources().getIdentifier("status_bar_height", "dimen", "android"))));
        Log.d("test activity", Integer.toString(getResources().getDimensionPixelSize(getResources().getIdentifier("navigation_bar_height", "dimen", "android"))));
    }

    @Override
    public void onResume(){
        super.onResume();

        makeFragment();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    private void makeFragment(){
        mMainFragment= new MainFragment();

        frgTransaction= getSupportFragmentManager().beginTransaction();
        frgTransaction.replace(R.id.container, mMainFragment);
        frgTransaction.addToBackStack(null);

        frgTransaction.commit();
    }

    public void moveToViewer(int type, String path){
        mCurrentFrg= type;

        frgTransaction= getSupportFragmentManager().beginTransaction();
        switch(type){
            case TEXT:
                if(mTextViewer == null) mTextViewer= new TextViewFragment();
                mTextViewer.setTextFile(path);
                frgTransaction.replace(R.id.container, mTextViewer);
                break;
            case PDF:
                break;
            case COMIC:
                break;
        }
        frgTransaction.addToBackStack(null);
        frgTransaction.commit();
    }

    private void moveToMain(){

    }

    @Override
    public void onBackPressed(){
        if(mCurrentFrg == LIST) {
            if(System.currentTimeMillis() > backPressedTime + 2000){
                backPressedTime= System.currentTimeMillis();
                toast= Toast.makeText(getApplicationContext(), getResources().getString(R.string.backFinishText), Toast.LENGTH_SHORT);
                toast.show();
                return ;
            }
            if(System.currentTimeMillis() <= backPressedTime+2000){
                toast.cancel();
                finish();
            }
        }

        super.onBackPressed();
    }

    private void savedSharedPreferences(){

    }

    private void loadSharedPreferences(){

    }
}