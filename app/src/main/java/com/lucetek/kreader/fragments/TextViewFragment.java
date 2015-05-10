package com.lucetek.kreader.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lucetek.kreader.KReaderTextCanvas;
import com.lucetek.kreader.R;

/**
 * Created by noirCynical on 15. 5. 10..
 */
public class TextViewFragment extends Fragment {
    private View wholeView= null;

    private String selectedFile= null;

    private KReaderTextCanvas mView= null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        wholeView= inflater.inflate(R.layout.fragment_textviewer, null);
        return wholeView;
    }

    @Override
    public void onResume(){
        super.onResume();

        makeView();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    private void makeView(){
        Log.e("select file text viewer", selectedFile);
        mView= (KReaderTextCanvas)wholeView.findViewById(R.id.canvasTextFragment); //new KReaderTextCanvas(getActivity(), selectedFile);
        mView.setFile(selectedFile);
    }

    public void setTextFile(String file){ selectedFile= file; }
    public String getTextFile(){ return selectedFile; }
}
