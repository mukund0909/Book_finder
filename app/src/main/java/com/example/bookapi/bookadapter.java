package com.example.bookapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class bookadapter extends ArrayAdapter<bookdata> {

    public bookadapter(@NonNull Context context, int resource, @NonNull List<bookdata> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View list=convertView;
        if(list==null)
            list= LayoutInflater.from(getContext()).inflate(R.layout.bookdata,parent,false);
        bookdata current=getItem(position);
        TextView tt=list.findViewById(R.id.text);
        tt.setText(current.getbook());
        return list;
    }
}
