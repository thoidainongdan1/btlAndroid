package com.example.btl.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.R;
import com.example.btl.dao.SQLiteDB;
import com.example.btl.model.Schedule;

import java.util.ArrayList;
import java.util.List;

public class ScheduleViewAdapter extends RecyclerView.Adapter<ScheduleViewAdapter.ScheduleViewHolder> {
    private final Context context;
    private List<Schedule> list;

    public ScheduleViewAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setSchedules(List<Schedule> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                             int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_schedule, parent, false);
        return new ScheduleViewHolder(v);
    }

    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        Schedule schedule = list.get(position);
        holder.scheduleContent.setText(schedule.getContent());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SQLiteDB db = new SQLiteDB(context);
                db.deleteSchedule(schedule.getId());
                notifyItemRemoved(position);
                Toast.makeText(context, "Done!!!", Toast.LENGTH_SHORT).show();
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

    class ScheduleViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox checkBox;
        private final TextView scheduleContent;

        public ScheduleViewHolder(@NonNull View v) {
            super(v);
            checkBox = v.findViewById(R.id.checkBox);
            scheduleContent = v.findViewById(R.id.scheduleContent);
        }
    }
}
