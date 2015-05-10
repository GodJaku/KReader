package com.lucetek.kreader;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by noirCynical on 15. 5. 10..
 */
public class FileListAdapter extends ArrayAdapter {

    private Context mContext= null;
    private ArrayList<FileListItem> mList= null;

    public FileListAdapter(Context context, int resId, ArrayList<FileListItem> list){
        super(context, resId, list);
        mContext= context;
        mList= list;
    }

    @Override
    public View getView(int pos, View v, ViewGroup container){
        if( v == null ) v= LayoutInflater.from(mContext).inflate(R.layout.item_filelist, null);

        if(mList != null && mList.get(pos) != null ){
//            ((TextView)v.findViewById(R.id.textFileListItem)).setText(mList.get(pos).getFilename());
            if(mList.get(pos).isDir())
                ((TextView)v.findViewById(R.id.textFileListItem)).setText(Html.fromHtml("<b>"+mList.get(pos).getFilename()+"</b>"));
            else
                ((TextView)v.findViewById(R.id.textFileListItem)).setText(mList.get(pos).getFilename());
//                ((TextView)v.findViewById(R.id.textFileListItem)).setTypeface(null, Typeface.BOLD);
        }

        return v;
    }

    @Override
    public FileListItem getItem(int pos){ return mList.get(pos); }
    @Override
    public int getCount(){ return mList.size(); }
    @Override
    public long getItemId(int position){ return position; }
}
