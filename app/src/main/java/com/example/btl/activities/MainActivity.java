package com.example.btl.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.btl.R;
import com.example.btl.dao.SQLiteDB;
import com.example.btl.entities.Note;
import com.example.btl.recyclerview.NoteViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    private SQLiteDB db;
    private RecyclerView recyclerView;
    private NoteViewAdapter adapter;
    private FloatingActionButton btnAddNote;
    private AutoCompleteTextView inputSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        db = new SQLiteDB(this);
        adapter = new NoteViewAdapter(this);
        List<Note> list = db.getAllNotes();
        adapter.setNotes(list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        btnAddNote.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), CreateNoteActivity.class);
            startActivity(intent);
        });

        // Autocomplete search input
        int listSize = list.size();
        if(listSize > 0) {
            String[] listTitle = new String[listSize];
            int i = 0;
            for(Note note : list) {
                listTitle[i++] = note.getTitle();
            }
            ArrayAdapter<String> aa = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, listTitle);
            inputSearch.setAdapter(aa);
        }

        // Search listener
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().trim().equals("")) {
                    List<Note> list = db.searchByTitle(s.toString().trim());
                    adapter.setNotes(list);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(
                            new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                } else if(s.toString().equals("")) {
                    List<Note> list = db.getAllNotes();
                    adapter.setNotes(list);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(
                            new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                }
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        inputSearch.setText("");

        List<Note> list = db.getAllNotes();
        adapter.setNotes(list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        // Autocomplete search input
        int listSize = list.size();
        if(listSize > 0) {
            String[] listTitle = new String[listSize];
            int i = 0;
            for(Note note : list) {
                listTitle[i++] = note.getTitle();
            }
            ArrayAdapter<String> aa = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, listTitle);
            inputSearch.setAdapter(aa);
        }
    }

    private void initView() {
        btnAddNote = findViewById(R.id.btnAddNote);
        recyclerView = findViewById(R.id.noteRecyclerView);
        inputSearch = findViewById(R.id.inputSearch);
    }
}