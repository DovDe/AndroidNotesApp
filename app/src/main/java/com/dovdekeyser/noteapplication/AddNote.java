package com.dovdekeyser.noteapplication;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNote extends AppCompatActivity {


   FloatingActionButton addNoteToDb;
    EditText editTextTitle;
    EditText editTextBody;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextTitle = (EditText)findViewById(R.id.editText_title);
        editTextBody  = (EditText)findViewById(R.id.editText_body);

        addNoteToDb = (FloatingActionButton) findViewById(R.id.fab_addToDb);

        addNoteToDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString();
                String body = editTextBody.getText().toString();

                Note note = new Note(title,body);

                DatabaseManager db = new DatabaseManager(getApplicationContext());
                long result =  db.insert(note);
                if(result>0){
                    Toast.makeText(AddNote.this, "added to db", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddNote.this,NoteList.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(AddNote.this, "some issue with insert", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
