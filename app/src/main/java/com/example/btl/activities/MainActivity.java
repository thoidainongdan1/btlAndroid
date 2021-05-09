package com.example.btl.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.btl.R;
import com.example.btl.dao.SQLiteDB;
import com.example.btl.entities.Note;
import com.example.btl.recyclerview.NoteViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SQLiteDB db;
    private RecyclerView recyclerView;
    private NoteViewAdapter adapter;
    private FloatingActionButton btnAddNote;

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
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        List<Note> list = db.getAllNotes();
        adapter.setNotes(list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }

    private void initView() {
        btnAddNote = findViewById(R.id.btnAddNote);
        recyclerView = findViewById(R.id.noteRecyclerView);
    }
}