package com.lucetek.kreader;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.widget.Toast;

import com.lucetek.kreader.fragments.MainFragment;


public class MainActivity extends FragmentActivity {
    private long backPressedTime;
    private Toast toast= null;

    private FragmentTransaction frgTransaction= null;

    private int mCurrentFrg= Constants.LIST;
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

    @Override
    public void onBackPressed(){
        if(mCurrentFrg == Constants.LIST) {
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
}
