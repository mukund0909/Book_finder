package com.example.bookapi;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

public class fetchloader extends AsyncTaskLoader<ArrayList<bookdata>> {
    private String strings;

    public fetchloader(Context context, String url) {
        super(context);
        strings=url;
    }

    @Override
    public ArrayList<bookdata> loadInBackground() {
        if(strings==null)
            return null;
        ArrayList<bookdata> earthquakes=Queryutils.fetchbookdata(strings);
        return earthquakes;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

}
