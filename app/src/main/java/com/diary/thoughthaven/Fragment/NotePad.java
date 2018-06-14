package com.diary.thoughthaven.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.diary.thoughthaven.Activities.Note;
import com.diary.thoughthaven.Activities.NoteActivity;
import com.diary.thoughthaven.Activities.NoteAdapter;
import com.diary.thoughthaven.Activities.Utilities;
import com.diary.thoughthaven.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class NotePad extends Fragment {

    private View view;
    private ListView mListNotes;
    private FloatingActionButton fabAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_note_pad, container, false);
        mListNotes = (ListView)view. findViewById(R.id.main_listview);
        fabAdd=(FloatingActionButton)view.findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), NoteActivity.class));
            }
        });
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        //load saved notes into the listview
        //first, reset the listview
        mListNotes.setAdapter(null);
        ArrayList<Note> notes = Utilities.getAllSavedNotes(getContext());

        //sort notes from new to old
        Collections.sort(notes, new Comparator<Note>() {
            @Override
            public int compare(Note lhs, Note rhs) {
                if(lhs.getDateTime() > rhs.getDateTime()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });

        if(notes != null && notes.size() > 0) { //check if we have any notes!
            final NoteAdapter na = new NoteAdapter(getContext(), R.layout.view_note_item, notes);
            mListNotes.setAdapter(na);

            //set click listener for items in the list, by clicking each item the note should be loaded into NoteActivity
            mListNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //run the NoteActivity in view/edit mode
                    String fileName = ((Note) mListNotes.getItemAtPosition(position)).getDateTime()
                            + Utilities.FILE_EXTENSION;
                    Intent viewNoteIntent = new Intent(getContext(), NoteActivity.class);
                    viewNoteIntent.putExtra(Utilities.EXTRAS_NOTE_FILENAME, fileName);
                    startActivity(viewNoteIntent);
                }
            });
        } else { //remind user that we have no notes!
            Toast.makeText(getContext(), "you have no saved notes!\ncreate some new notes :)"
                    , Toast.LENGTH_SHORT).show();
        }
    }
}
