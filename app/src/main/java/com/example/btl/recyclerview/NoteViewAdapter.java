package com.example.btl.recyclerview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.btl.R;
import com.example.btl.entities.Note;
import java.util.ArrayList;
import java.util.List;

public class NoteViewAdapter extends RecyclerView.Adapter<NoteViewAdapter.NoteViewHolder> {
    private List<Note> list;
    private Activity activity;

    public NoteViewAdapter(Activity activity) {
        this.activity = activity;
        list = new ArrayList<>();
    }

    public void setNotes(List<Note> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(v);
    }

    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = list.get(position);
        holder.noteTitle.setText(note.getTitle());
        holder.noteSubtitle.setText(note.getSubtitle());
        holder.noteDateTime.setText(note.getDateTime());

        GradientDrawable gradientDrawable = (GradientDrawable) holder.noteLayout.getBackground();
        if(note.getColor() != null) {
            gradientDrawable.setColor(Color.parseColor(note.getColor()));
        } else {
            gradientDrawable.setColor(Color.parseColor("#333333"));
        }

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(activity, UpdateDeleteActivity.class);
//                intent.putExtra("id", s.getId());
//                activity.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        else
            return 0;
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        private final View noteLayout;
        private final TextView noteTitle;
        private final TextView noteSubtitle;
        private final TextView noteDateTime;

        public NoteViewHolder(@NonNull View v) {
            super(v);
            noteTitle = v.findViewById(R.id.noteTitle);
            noteSubtitle = v.findViewById(R.id.noteSubtitle);
            noteDateTime = v.findViewById(R.id.noteDateTime);
            noteLayout = v.findViewById(R.id.noteLayout);
        }
    }
}
