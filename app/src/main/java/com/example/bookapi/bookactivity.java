package com.example.bookapi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class bookactivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<bookdata>> {
    private TextView emptytext;
    ArrayAdapter<bookdata> adapter;
    private int i;
    ProgressBar pp;
    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookactivity);
        s=getIntent().getExtras().getString("Value");
        LoaderManager loaderManager=getLoaderManager();
        loaderManager.initLoader(1,null,this);
        pp=findViewById(R.id.progress);
        emptytext=(TextView)findViewById(R.id.empty);
        adapter=new bookadapter(this,0,new ArrayList<bookdata>());
        ListView booklist=(ListView)findViewById(R.id.list);
        booklist.setAdapter(adapter);
        booklist.setEmptyView(emptytext);
        booklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bookdata data=adapter.getItem(position);
                Uri earthquakeUri = Uri.parse(data.geturl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
        ConnectivityManager conMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        if ( conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED ) {

            i=1;

        }
        else if ( conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) {

            i=2;
        }
    }
    @Override
    public Loader<ArrayList<bookdata>> onCreateLoader(int id, Bundle args) {
        return new fetchloader(this,s);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<bookdata>> loader, ArrayList<bookdata> data) {

        adapter.clear();
        pp.setVisibility(View.GONE);
        if(i==1)
          emptytext.setText("No books found");
        else if(i==2) {
          emptytext.setText("No Internet Connection");
        }
        if((data!=null)&&(!data.isEmpty()))
            adapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<bookdata>> loader) {
        adapter.clear();
    }
}