package com.example.bookapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText bquery;
    private  String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bquery=findViewById(R.id.bookname);
        Button Btn=findViewById(R.id.bb);
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(MainActivity.this,bookactivity.class);
                s=bquery.getText().toString();
                in.putExtra("Value",s);
                startActivity(in);
                finish();
            }
        });
    }
}