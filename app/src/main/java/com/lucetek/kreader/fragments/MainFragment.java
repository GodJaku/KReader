package com.lucetek.kreader.fragments;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.lucetek.kreader.FileListAdapter;
import com.lucetek.kreader.FileListItem;
import com.lucetek.kreader.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by noirCynical on 15. 5. 7..
 */
public class MainFragment extends Fragment {

    private View wholeView= null;

    private String mRootDir= null;
    private String mCurrentDir= null;
    private ArrayList<FileListItem> mCurrentFileList= null;

    private ListView mFileListView= null;
    private FileListAdapter mFileListAdapter= null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        wholeView= inflater.inflate(R.layout.fragment_main, null);
        return wholeView;
    }

    @Override
    public void onResume(){
        super.onResume();
        makeView();
    }

    @Override
    public void onPause(){ super.onPause(); }

    private void makeView(){
        mFileListView= (ListView)wholeView.findViewById(R.id.listFragmentMainFileList);

//        if(!sdExist()) ((MainActivity)getActivity()).finish();
        getFileList();
        initFileList();
        viewFileList(getFileList(mRootDir));
    }

    private void getFileList() {
        if (!sdExist()) getActivity().finish();

        mCurrentDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        mRootDir= Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    private boolean sdExist(){
        String ext= Environment.getExternalStorageState();
        if(!ext.equals(Environment.MEDIA_MOUNTED)){
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.noSDCardText), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private ArrayList<FileListItem> getFileList(String path){
        File file= new File(path);
        if(!file.isDirectory()) return null;
        mCurrentDir= path;

        ArrayList<FileListItem> temp= new ArrayList<FileListItem>();
        File[] files= (new File(path)).listFiles();
        for(int i=0; i<files.length; i++){
            File tempfile= files[i];
            if(tempfile.isDirectory()) temp.add(new FileListItem(true, tempfile.getName()+"/"));
            else temp.add(new FileListItem(false, tempfile.getName()));
        }

        return temp;
    }

    private void initFileList(){
        mCurrentFileList= new ArrayList<FileListItem>();
        mFileListAdapter= new FileListAdapter(getActivity(), R.layout.item_filelist, mCurrentFileList);

        mFileListView.setAdapter(mFileListAdapter);
        mFileListView.setOnItemClickListener(itemClick);
    }

    private String getAbsolutePath(String file){
        if(file.equalsIgnoreCase("..") || file.equalsIgnoreCase("../")) return mCurrentDir.substring(0, mCurrentDir.lastIndexOf("/"));
        else return mCurrentDir+"/"+file;
    }

    private void viewFileList(ArrayList<FileListItem> list){
        if(list == null) return ;
        mCurrentFileList.clear();

        if(mRootDir.length() < mCurrentDir.length()) mCurrentFileList.add(new FileListItem(true, ".."));
        for(int i=0; i<list.size(); i++){
            Log.d("test", list.get(i).getFilename());
            mCurrentFileList.add(list.get(i));
        }
        mFileListAdapter.notifyDataSetChanged();
    }

    AdapterView.OnItemClickListener itemClick= new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if(mCurrentFileList.get(position).isDir()){
                String selectedItem= mCurrentFileList.get(position).getFilename();
                String path= getAbsolutePath(selectedItem);
                viewFileList(getFileList(path));
            }
        }
    };

    View.OnClickListener click= new View.OnClickListener(){
        @Override
        public void onClick(View v){
            int id= v.getId();

            if(id == R.id.buttonFragmentMainRefeshList) getFileList();
        }
    };
}
