package com.dovdekeyser.noteapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NoteAdapter extends ArrayAdapter<Note> {
    private static final String TAG = "NoteAdapter";
    private ArrayList<Note> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtNoteTitle;

    }

    public NoteAdapter(ArrayList<Note> data, Context context){
        super(context, R.layout.note_item, data);
        this.dataSet = data;
        this.mContext=context;
    }

    private int lastPosition = -1;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Note dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.note_item, parent, false);
            viewHolder.txtNoteTitle = (TextView) convertView.findViewById(R.id.note_title);

            convertView.setTag(viewHolder);


            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Object object= getItem(position);
                    Note note=(Note) object;
                    Toast.makeText(mContext, note.getTitle(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(),NoteActivity.class);
                    intent.putExtra("NOTE_TITLE",note.getTitle());
                    intent.putExtra("NOTE_BODY",note.getBody());

                    mContext.startActivity(intent);
                }
            });

            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Object object= getItem(position);
                    Note note=(Note) object;
                    DatabaseManager databaseManager = new DatabaseManager(mContext);
                    int result = databaseManager.delete(note);
                    if(result>0){
                        Toast.makeText(mContext, "Delete", Toast.LENGTH_SHORT).show();

                        try {
                            dataSet.remove(note);
                            notifyDataSetChanged();
                        }catch (Exception e){
                            Log.v(TAG,e.getMessage());
                        }

                    }else{
                        Toast.makeText(mContext, "Issue while deleting record...", Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }
            });

//            convertView.setOnTouchListener(new OnSwipeTouchListener(mContext){
//                @Override
//                public void onSwipeLeft(){
//                    Object object= getItem(position);
//                    Note note=(Note) object;
//                    DatabaseManager databaseManager = new DatabaseManager(mContext);
//                    int result = databaseManager.delete(note);
//                    if(result>0){
//                        Toast.makeText(mContext, "Delete", Toast.LENGTH_SHORT).show();
//                        dataSet.remove(note);
//                        notifyDataSetChanged();
//                    }else{
//                        Toast.makeText(mContext, "Issue while deleting record...", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

//        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
//        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtNoteTitle.setText(dataModel.getTitle());

        // Return the completed view to render on screen
        return convertView;
    }
}
