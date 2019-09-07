package com.dovdekeyser.noteapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class DatabaseManager {
    private final SQLiteDatabase mDatabase;

    public DatabaseManager(Context context){
        DatabaseOpenHelper helper = new DatabaseOpenHelper(context);
        mDatabase = helper.getWritableDatabase();
    }

    public long insert(Note note){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseOpenHelper.COL_TITLE,note.getTitle());
        contentValues.put(DatabaseOpenHelper.COL_BODY,note.getBody());
        return mDatabase.insert(DatabaseOpenHelper.TABLE_NAME_NOTES,null,contentValues);
    }

    public ArrayList<Note> getAllNotes(){
        Cursor cursor = mDatabase.query(DatabaseOpenHelper.TABLE_NAME_NOTES,null,null,null,null,null,null);

        ArrayList<Note> allNotes =null;

        if(cursor.getColumnCount()>0){

            allNotes = new ArrayList<>();

            while (cursor.moveToNext()){
                int indexTitle = cursor.getColumnIndex(DatabaseOpenHelper.COL_TITLE);
                int indexBody = cursor.getColumnIndex(DatabaseOpenHelper.COL_BODY);

                String title = cursor.getString(indexTitle);
                String boody = cursor.getString(indexBody);

                Note note = new Note(title,boody);

                allNotes.add(note);
            }
        }

        cursor.close();

        return allNotes;

    }

    public int delete(Note note){
        String where = DatabaseOpenHelper.COL_TITLE+ "=?";
        //String where = DatabaseOpenHelper.COL_TITLE + "=? " + DatabaseOpenHelper.COL_ADDRESS + "=?";
        String[] whereArgs = {note.getTitle()};
        //String[] whereArgs = {member.getmName(),member.getmAddress()};
        return mDatabase.delete(DatabaseOpenHelper.TABLE_NAME_NOTES,where,whereArgs);
    }



    private static class DatabaseOpenHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "NoteDatabase";
        private static final int DATABASE_VERSION = 1;

        private static final String TABLE_NAME_NOTES = "Notes";
        private static final String COL_ID = "_id";
        private static final String COL_TITLE = "Title";
        private static final String COL_BODY = "Body";



        private static final String CREATE_TABLE_NOTES = "create table " + TABLE_NAME_NOTES
                + " ("
                + COL_ID + " integer primary key autoincrement, "
                + COL_TITLE + " text, "
                + COL_BODY+ " text "
                + ");";


        private static final String DROP_TABLE_NOTES = "drop table if exists " + TABLE_NAME_NOTES +";";

        private final Context mContext;

        public DatabaseOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            mContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_NOTES);
            Toast.makeText(mContext, "onCreate", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_TABLE_NOTES);
            onCreate(db);
            Toast.makeText(mContext, "onUpgrade", Toast.LENGTH_SHORT).show();

        }
    }
}
