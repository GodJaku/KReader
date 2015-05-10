package com.lucetek.kreader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

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
    private boolean drawCall= false;

    private String mFilename= null;
    private File mFile= null;
    private String mContent= null;

    private int lineStart, lineEnd;
    private int screenStart, screenEnd;
    private int onepageLetter;

    private double mWidth, mHeight;

    private Paint mTextPaint= null;
    private Paint mBackgroundPaint= null;

    private int mTextSize= 30;
    private int mTextColor;
    private int mBackgroundColor;
    private int mLineLeading= 15;
    private int mEdge= 15;

    // for event
    private double startX, endX;


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
        mContent= fileRead();

        if(drawCall) invalidate();
    }

    @Override
    public void onDraw(Canvas canvas){
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
        getSize(canvas);

        drawCall= true;
        canvas.drawColor(mBackgroundColor);
        if(mContent != null && mContent.length() > 0){
            if(screenStart >= getContentLength()) screenStart= getContentLength()-100;
            int lineCurrentCount = 1, lineStart, lineEnd;
            lineStart = screenStart;
            lineEnd = lineStart;

//        progressUpdate();
            while (true) {
                while (true) {
                    if(lineEnd == getContentLength()) break;
                    else if ((mTextPaint.measureText(mContent.substring(lineStart, lineEnd)) >= mWidth - mEdge * 2)) {
                        lineEnd--;
                        break;
                    } else if (mContent.charAt(lineEnd) == '\n') {
                        lineEnd++;
                        break;
                    }
                    lineEnd++;
                }
                canvas.drawText(mContent.substring(lineStart, lineEnd), mEdge, (lineCurrentCount) * (mTextSize + mLineLeading), mTextPaint);
                lineCurrentCount++;
                if ((lineCurrentCount - 1) * (mTextSize + mLineLeading) >= mHeight - (mTextSize + mLineLeading)) {
                    screenEnd = lineEnd - 1;
                    break;
                }
                lineStart = lineEnd;
            }
        }
    }

    public void init(){
        mTextPaint= new Paint();
        mBackgroundPaint= new Paint();
        mTextPaint.setAntiAlias(true);

        if(mTextColor == 0) mTextColor= getResources().getColor(R.color.defaultcolor);
        if(mBackgroundColor == 0) mBackgroundColor= getResources().getColor(R.color.defaultbackground);
    }

    public void setLineLeading(int size){ mLineLeading= size; invalidate(); }
    public void setTextSize(int size){ mTextSize= size; invalidate(); }
    public void setTextColor(int color){ mTextColor= color; invalidate(); }
    public void setmBackgroundColor(int color){ mBackgroundColor= color; invalidate(); }

    public void getSize(Canvas canvas){
        mWidth= (int)canvas.getWidth();
        mHeight= (int)canvas.getHeight() - getStatusBarHeight()*1 - getNavigationBarheight()*1;
//        mWidth= mContext.getResources().getDisplayMetrics().widthPixels;
//        mHeight= mContext.getResources().getDisplayMetrics().heightPixels - getStatusBarHeight() - getNavigationBarheight();
    }

    private String fileRead(){
        StringBuilder builder= new StringBuilder();
        try{
            FileInputStream fis = new FileInputStream(mFile);
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("utf-16"));
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line);
                builder.append('\n');
            }
            isr.close();
        } catch(FileNotFoundException e){ e.printStackTrace();
        } catch(IOException e){ e.printStackTrace();
        } catch(Exception e){ e.printStackTrace(); }

        return builder.toString();
    }

    private int getContentLength(){ return mContent.length(); }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        if(((CremaTextViewerActivity)mContext).mFontLayout.getVisibility() == View.VISIBLE)
//            ((CremaTextViewerActivity)mContext).mFontLayout.setVisibility(View.INVISIBLE);
//        else{
//            switch (viewingMode) {
//                case VIEW:
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            startX = (int) event.getX();
                            break;
                        case MotionEvent.ACTION_UP:
                            endX = (int) event.getX();
                            if (Math.abs(startX - endX) < 5) {
                                if (startX > mWidth / 3 * 2) {
                                    if(screenEnd < mContent.length()-1){
                                        screenStart = screenEnd + 1;
                                        lineStart = screenStart;
//                                        if (menuMode == SHOW) {
//                                            ((CremaTextViewerActivity) mContext).hideButtons();
//                                            menuMode = HIDE;
//                                        }
//                                        ((CremaTextViewerActivity)mContext).upDatePageNumberView(screenStart);
                                        invalidate();
                                    }
//                                    else{
//                                        if(menuMode == SHOW){
//                                            ((CremaTextViewerActivity)mContext).hideButtons();
//                                            menuMode= HIDE;
//                                        }
//                                        else{
//                                            ((CremaTextViewerActivity)mContext).showButtons();
//                                            menuMode= SHOW;
//                                        }
//                                    }
                                } else if (startX < mWidth / 3) {
                                    if(screenStart < 1){
                                        screenStart= 0;
                                        Toast.makeText(mContext, "This is first page", Toast.LENGTH_SHORT).show();
//                                        if(menuMode == SHOW){
//                                            ((CremaTextViewerActivity)mContext).hideButtons();
//                                            menuMode= HIDE;
//                                        }
//                                        else{
//                                            ((CremaTextViewerActivity)mContext).showButtons();
//                                            menuMode= SHOW;
//                                        }
                                    }
                                    else{
                                        onepageLetter= screenEnd- screenStart;
                                        if (onepageLetter > screenStart)
                                            screenStart = 0;
                                        else
                                            screenStart = screenStart - onepageLetter;
//                                        if (menuMode == SHOW) {
//                                            ((CremaTextViewerActivity) mContext).hideButtons();
//                                            menuMode = HIDE;
//                                        }
//                                        ((CremaTextViewerActivity)mContext).upDatePageNumberView(screenStart);
                                        invalidate();
                                    }
//							onepageLetter = screenEnd - screenStart;
//							if (onepageLetter > screenStart) {
//								screenStart = 0;
//
//							} else{
//								screenStart = screenStart - onepageLetter;
//
//							}
//                                } else {
//                                    switch (menuMode) {
//                                        case HIDE:
//                                            ((CremaTextViewerActivity) mContext).showButtons();
//                                            menuMode = SHOW;
//                                            break;
//                                        case SHOW:
//                                            ((CremaTextViewerActivity)mContext).hideButtons();
//                                            menuMode = HIDE;
//                                            break;
//                                    }
                                }
                            } else {
//						System.out.println(endX - startX);
                                if ((endX - startX) > 0) {
//							onepageLetter = screenEnd - screenStart;
//							if (onepageLetter > screenStart) {
//								screenStart = 0;
//								Toast.makeText(mContext, "This is first page",
//										Toast.LENGTH_SHORT).show();
//							} else
//								screenStart = screenStart - onepageLetter;
//							if (menuMode == SHOW) {
//								((CremaTextViewerActivity) mContext).hideButtons();
//								menuMode = HIDE;
//							}
//							((CremaTextViewerActivity)mContext).upDatePageNumberView(screenStart);
//							invalidate();
                                    if(screenStart < 1){
                                        screenStart= 0;
                                        Toast.makeText(mContext, "This is first page",
                                                Toast.LENGTH_SHORT).show();
//                                        if(menuMode == SHOW){
//                                            ((CremaTextViewerActivity)mContext).hideButtons();
//                                            menuMode= HIDE;
//                                        }
//                                        else{
//                                            ((CremaTextViewerActivity)mContext).showButtons();
//                                            menuMode= SHOW;
//                                        }
                                    }
                                    else{
                                        onepageLetter= screenEnd- screenStart;
                                        if (onepageLetter > screenStart)
                                            screenStart = 0;
                                        else
                                            screenStart = screenStart - onepageLetter;
//                                        if (menuMode == SHOW) {
//                                            ((CremaTextViewerActivity) mContext).hideButtons();
//                                            menuMode = HIDE;
//                                        }
//                                        ((CremaTextViewerActivity)mContext).upDatePageNumberView(screenStart);
                                        invalidate();
                                    }
                                } else {
//							screenEnd = lineEnd - 1;
//							screenStart = lineEnd;
//							((CremaTextViewerActivity)mContext).upDatePageNumberView(screenStart);
//							invalidate();
                                    if(screenEnd < mContent.length()-1){
                                        screenStart = screenEnd + 1;
                                        lineStart = screenStart;
//                                        if (menuMode == SHOW) {
//                                            ((CremaTextViewerActivity) mContext).hideButtons();
//                                            menuMode = HIDE;
//                                        }
//                                        ((CremaTextViewerActivity)mContext).upDatePageNumberView(screenStart);
                                        invalidate();
                                    }
                                    else{
//                                        if(menuMode == SHOW){
//                                            ((CremaTextViewerActivity)mContext).hideButtons();
//                                            menuMode= HIDE;
//                                        }
//                                        else{
//                                            ((CremaTextViewerActivity)mContext).showButtons();
//                                            menuMode= SHOW;
//                                        }
                                    }
                                }
                            }
                            break;
                    }
//                    break;
//            }
//        }

        return true;
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