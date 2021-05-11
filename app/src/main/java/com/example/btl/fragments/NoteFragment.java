package com.example.btl.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.btl.R;
import com.example.btl.activities.CreateNoteActivity;
import com.example.btl.dao.SQLiteDB;
import com.example.btl.model.Note;
import com.example.btl.recyclerview.NoteViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NoteFragment extends Fragment {

    private SQLiteDB db;
    private RecyclerView recyclerView;
    private NoteViewAdapter adapter;
    private FloatingActionButton btnAddNote;
    private AutoCompleteTextView inputSearch;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_note, container, false);
        initView();

        db = new SQLiteDB(view.getContext());
        adapter = new NoteViewAdapter(view.getContext());
        List<Note> list = db.getAllNotes();
        adapter.setNotes(list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        btnAddNote.setOnClickListener(v -> {
            Intent intent = new Intent(view.getContext(), CreateNoteActivity.class);
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
            ArrayAdapter<String> aa = new ArrayAdapter<>(view.getContext(),
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
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
            ArrayAdapter<String> aa = new ArrayAdapter<>(view.getContext(),
                    android.R.layout.simple_list_item_1, listTitle);
            inputSearch.setAdapter(aa);
        }
    }

    private void initView() {
        btnAddNote = view.findViewById(R.id.btnAddNote);
        recyclerView = view.findViewById(R.id.noteRecyclerView);
        inputSearch = view.findViewById(R.id.inputSearch);
    }
}