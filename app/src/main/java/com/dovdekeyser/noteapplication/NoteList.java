package com.dovdekeyser.noteapplication;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NoteList extends AppCompatActivity {

    ListView listView;
    ArrayList<Note> notes = new ArrayList<>();
    NoteAdapter adapter;

    FloatingActionButton addNoteFab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        listView = (ListView)findViewById(R.id.list_view);

        DatabaseManager databaseManager = new DatabaseManager(this);
        notes = databaseManager.getAllNotes();



        adapter = new NoteAdapter(notes,this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String noteTitle = (String) listView.getItemAtPosition(position);
                Toast.makeText(NoteList.this, String.valueOf(position) + " " + noteTitle, Toast.LENGTH_SHORT).show();
            }
        });

        addNoteFab = (FloatingActionButton)findViewById(R.id.fab_add_note);
        addNoteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoteList.this,AddNote.class);
                startActivity(intent);
            }
        });


    }
}
