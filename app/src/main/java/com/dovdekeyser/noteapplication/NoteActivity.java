package com.dovdekeyser.noteapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NoteActivity extends AppCompatActivity {


    TextView noteTitle;
    TextView noteBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        Intent intent = getIntent();

        String title =intent.getStringExtra("NOTE_TITLE");
        String body = intent.getStringExtra("NOTE_BODY");

        noteTitle =(TextView)findViewById(R.id.noteTitle);
        noteTitle.setText(title);
        noteBody = (TextView)findViewById(R.id.noteBody);
        noteBody.setText(body);


    }
}
