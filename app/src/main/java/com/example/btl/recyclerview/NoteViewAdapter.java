package com.example.btl.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.R;
import com.example.btl.activities.EditNoteActivity;
import com.example.btl.model.Note;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class NoteViewAdapter extends RecyclerView.Adapter<NoteViewAdapter.NoteViewHolder> {
    private final Context context;
    private List<Note> list;

    public NoteViewAdapter(Context context) {
        this.context = context;
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

        // set color
        GradientDrawable gradientDrawable = (GradientDrawable) holder.noteLayout.getBackground();
        if (note.getColor() != null) {
            gradientDrawable.setColor(Color.parseColor(note.getColor()));
        } else {
            gradientDrawable.setColor(Color.parseColor("#333333"));
        }

        // set image
        if (note.getImagePath() != null) {
            holder.noteImage.setImageBitmap(BitmapFactory.decodeFile(note.getImagePath()));
            holder.noteImage.setVisibility(View.VISIBLE);

        } else {
            holder.noteImage.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditNoteActivity.class);
                intent.putExtra("id", note.getId());
                context.startActivity(intent);
            }
        });
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
        private final RoundedImageView noteImage;

        public NoteViewHolder(@NonNull View v) {
            super(v);
            noteTitle = v.findViewById(R.id.noteTitle);
            noteSubtitle = v.findViewById(R.id.noteSubtitle);
            noteDateTime = v.findViewById(R.id.noteDateTime);
            noteLayout = v.findViewById(R.id.noteLayout);
            noteImage = v.findViewById(R.id.imageNote);
        }
    }
}
