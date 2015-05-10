package com.lucetek.kreader;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.lucetek.kreader.fragments.MainFragment;


public class MainActivity extends FragmentActivity {

    private FragmentTransaction frgTransaction= null;

    private MainFragment mMainFragment= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
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
}
