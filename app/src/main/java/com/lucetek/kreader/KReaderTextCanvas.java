package com.lucetek.kreader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Created by noirCynical on 15. 5. 10..
 */
public class KReaderTextCanvas extends View{

    private Context mContext= null;

    private String mFilename= null;
    private File mFile= null;
    private String mContent= null;

    private double mWidth, mHeight;

    private Paint mTextPaint= null;
    private Paint mBackgroundPaint= null;

    private int mTextSize= 15;
    private Color mTextColor= null;
    private Color mBackgroundColor= null;
    private int mLineLeading= 15;


    public KReaderTextCanvas(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public KReaderTextCanvas(Context context, AttributeSet attr) {
        super(context, attr);
        mContext = context;
        init();
    }

    public KReaderTextCanvas(Context context, AttributeSet attr, int defstyle) {
        super(context, attr, defstyle);
        mContext = context;
        init();
    }

    public void setFile(String filename){
        mFilename= filename;
        mFile= new File(mFilename);
    }

    @Override
    public void onDraw(Canvas canvas){

    }

    public void init(){
        mTextPaint= new Paint();
        mBackgroundPaint= new Paint();

        mTextPaint.setAntiAlias(true);

        mContent= fileRead();
    }

    private String fileRead(){
        StringBuilder builder= new StringBuilder();
        try{
            FileInputStream fis = new FileInputStream(mFile);
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("euc-kr"));
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line);
                builder.append('\n');
            }
        } catch(FileNotFoundException e){ e.printStackTrace();
        } catch(IOException e){ e.printStackTrace();
        } catch(Exception e){ e.printStackTrace(); }

        return builder.toString();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    private void nextPage(){

    }
    private void prevPage(){

    }

    private int getStatusBarHeight() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) return getResources().getDimensionPixelSize(resourceId);
        else return 0;
    }
    private int getNavigationBarheight(){
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) return getResources().getDimensionPixelSize(resourceId);
        else return 0;
    }
}