package com.example.btl.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.example.btl.R;
import com.example.btl.activities.CreateNoteActivity;
import com.example.btl.dao.SQLiteDB;
import com.example.btl.model.Note;
import com.example.btl.model.Schedule;
import com.example.btl.recyclerview.NoteViewAdapter;
import com.example.btl.recyclerview.ScheduleViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ScheduleFragment extends Fragment {

    private SQLiteDB db;
    private RecyclerView recyclerView;
    private ScheduleViewAdapter adapter;
    private FloatingActionButton btnAddSchedule;
    private EditText inputSearch;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_schedule, container, false);
        initView();

        db = new SQLiteDB(view.getContext());
        adapter = new ScheduleViewAdapter(view.getContext());
        List<Schedule> list = db.getAllSchedules();
        list = new ArrayList<>();
        list.add(new Schedule(1,"a"));
        list.add(new Schedule(2,"b"));
        adapter.setSchedules(list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        btnAddSchedule.setOnClickListener(v -> {
            Intent intent = new Intent(view.getContext(), CreateNoteActivity.class);
            startActivity(intent);
        });

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
                    List<Schedule> list = db.searchByContent(s.toString().trim());
                    adapter.setSchedules(list);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                } else if(s.toString().equals("")) {
                    List<Schedule> list = db.getAllSchedules();
                    adapter.setSchedules(list);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                }
            }
        });
        return view;
    }

    private void initView() {
        btnAddSchedule = view.findViewById(R.id.btnAddSchedule);
        recyclerView = view.findViewById(R.id.scheduleRecyclerView);
        inputSearch = view.findViewById(R.id.inputSearch);
    }
}