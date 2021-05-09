package com.example.btl.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl.R;
import com.example.btl.dao.SQLiteDB;
import com.example.btl.entities.Note;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreateNoteActivity extends AppCompatActivity {

    private EditText inputNoteTitle, inputNoteSubtitle, inputNoteText;
    private TextView textDateTime;
    private View viewSubtitleIndicator;
    private ImageView imageBack, imageSave;
    private ImageView color_1, color_2, color_3, color_4, color_5;
    private ImageView checked_color_1, checked_color_2, checked_color_3, checked_color_4, checked_color_5;
    private String selectedColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        initView();
        initColor();

        textDateTime.setText(getCurrentDateTime());

        imageBack.setOnClickListener(v -> finish());

        imageSave.setOnClickListener(v -> {
            if(addNote()) {
                finish();
            }
        });

        selectColorListener();
    }

    private boolean addNote() {
        if(inputNoteTitle.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Chưa có tiêu đề!!!", Toast.LENGTH_SHORT).show();
            return false;
        } else if(inputNoteSubtitle.getText().toString().trim().isEmpty()
                && inputNoteText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Chưa có nội dung!!!", Toast.LENGTH_SHORT).show();
            return false;
        }

        Note note = new Note();
        note.setTitle(inputNoteTitle.getText().toString());
        note.setSubtitle(inputNoteSubtitle.getText().toString());
        note.setNoteText(inputNoteText.getText().toString());
        note.setDateTime(textDateTime.getText().toString());
        note.setColor(selectedColor);
        SQLiteDB db = new SQLiteDB(this);
        db.addNote(note);
        return true;
    }

    private void initView() {
        inputNoteTitle = findViewById(R.id.inputNoteTitle);
        inputNoteSubtitle = findViewById(R.id.inputNoteSubtitle);
        inputNoteText = findViewById(R.id.inputNote);
        textDateTime = findViewById(R.id.textDateTime);
        viewSubtitleIndicator = findViewById(R.id.viewSubtitleIndicator);
        imageBack = findViewById(R.id.imageBack);
        imageSave = findViewById(R.id.imageSave);
        color_1 = findViewById(R.id.color_1);
        color_2 = findViewById(R.id.color_2);
        color_3 = findViewById(R.id.color_3);
        color_4 = findViewById(R.id.color_4);
        color_5 = findViewById(R.id.color_5);
        checked_color_1 = findViewById(R.id.checked_color_1);
        checked_color_2 = findViewById(R.id.checked_color_2);
        checked_color_3 = findViewById(R.id.checked_color_3);
        checked_color_4 = findViewById(R.id.checked_color_4);
        checked_color_5 = findViewById(R.id.checked_color_5);
    }

    private String getCurrentDateTime() {
        String[] dayOfWeek = {"Chủ nhật", "Thứ hai", "Thứ ba", "Thứ tư", "Thứ năm", "Thứ sáu", "Thứ bảy"};
        String dateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm a", Locale.getDefault())
                .format(new Date());
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int day = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return dayOfWeek[day] + ", " + dateTime;
    }

    private void selectColorListener() {
        color_1.setOnClickListener(v -> {
            selectedColor = "#333333";
            checked_color_1.setImageResource(R.drawable.ic_done);
            checked_color_2.setImageResource(0);
            checked_color_3.setImageResource(0);
            checked_color_4.setImageResource(0);
            checked_color_5.setImageResource(0);
            setSubtitleIndicatorColor();
        });

        color_2.setOnClickListener(v -> {
            selectedColor = "#FDBE3B";
            checked_color_1.setImageResource(0);
            checked_color_2.setImageResource(R.drawable.ic_done);
            checked_color_3.setImageResource(0);
            checked_color_4.setImageResource(0);
            checked_color_5.setImageResource(0);
            setSubtitleIndicatorColor();
        });

        color_3.setOnClickListener(v -> {
            selectedColor = "#FF4842";
            checked_color_1.setImageResource(0);
            checked_color_2.setImageResource(0);
            checked_color_3.setImageResource(R.drawable.ic_done);
            checked_color_4.setImageResource(0);
            checked_color_5.setImageResource(0);
            setSubtitleIndicatorColor();
        });

        color_4.setOnClickListener(v -> {
            selectedColor = "#3A52FC";
            checked_color_1.setImageResource(0);
            checked_color_2.setImageResource(0);
            checked_color_3.setImageResource(0);
            checked_color_4.setImageResource(R.drawable.ic_done);
            checked_color_5.setImageResource(0);
            setSubtitleIndicatorColor();
        });

        color_5.setOnClickListener(v -> {
            selectedColor = "#000000";
            checked_color_1.setImageResource(0);
            checked_color_2.setImageResource(0);
            checked_color_3.setImageResource(0);
            checked_color_4.setImageResource(0);
            checked_color_5.setImageResource(R.drawable.ic_done);
            setSubtitleIndicatorColor();
        });
    }

    private void setSubtitleIndicatorColor() {
        GradientDrawable gradientDrawable = (GradientDrawable) viewSubtitleIndicator.getBackground();
        gradientDrawable.setColor(Color.parseColor(selectedColor));
    }

    private void initColor() {
        GradientDrawable gradientDrawable = (GradientDrawable) viewSubtitleIndicator.getBackground();
        int colorId = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            colorId = gradientDrawable.getColor().getDefaultColor();
        }
        String hexColor = String.format("#%06X", (0xFFFFFF & colorId));
        switch (hexColor) {
            case "#333333":
                selectedColor = "#333333";
                checked_color_1.setImageResource(R.drawable.ic_done);
                checked_color_2.setImageResource(0);
                checked_color_3.setImageResource(0);
                checked_color_4.setImageResource(0);
                checked_color_5.setImageResource(0);
                break;
            case "#FDBE3B":
                selectedColor = "#FDBE3B";
                checked_color_1.setImageResource(0);
                checked_color_2.setImageResource(R.drawable.ic_done);
                checked_color_3.setImageResource(0);
                checked_color_4.setImageResource(0);
                checked_color_5.setImageResource(0);
                break;
            case "#FF4842":
                selectedColor = "#FF4842";
                checked_color_1.setImageResource(0);
                checked_color_2.setImageResource(0);
                checked_color_3.setImageResource(R.drawable.ic_done);
                checked_color_4.setImageResource(0);
                checked_color_5.setImageResource(0);
                break;
            case "#3A52FC":
                selectedColor = "#3A52FC";
                checked_color_1.setImageResource(0);
                checked_color_2.setImageResource(0);
                checked_color_3.setImageResource(0);
                checked_color_4.setImageResource(R.drawable.ic_done);
                checked_color_5.setImageResource(0);
                break;
            case "#000000":
                selectedColor = "#000000";
                checked_color_1.setImageResource(0);
                checked_color_2.setImageResource(0);
                checked_color_3.setImageResource(0);
                checked_color_4.setImageResource(0);
                checked_color_5.setImageResource(R.drawable.ic_done);
                break;
        }
    }
}