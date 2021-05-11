package com.example.btl.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.btl.R;
import com.example.btl.dao.SQLiteDB;
import com.example.btl.entities.Note;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditNoteActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 1;
    private static final int REQUEST_PERMISSION = 2;

    private EditText inputNoteTitle, inputNoteSubtitle, inputNoteText;
    private TextView textDateTime;
    private View viewSubtitleIndicator;
    private ImageView imageBack, imageSave;
    private ImageView color_1, color_2, color_3, color_4, color_5;
    private ImageView checked_color_1, checked_color_2, checked_color_3, checked_color_4, checked_color_5;
    private String selectedColor, selectedImagePath;
    private AppCompatButton imageChooser, imageDel, btnDelNote;
    private ImageView imageNote;
    private TextView imageName;

    private Note note;
    private SQLiteDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initView();
        db = new SQLiteDB(this);

        // get note information
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        note = db.getNoteById(id);
        inputNoteTitle.setText(note.getTitle());
        inputNoteSubtitle.setText(note.getSubtitle());
        inputNoteText.setText(note.getNoteText());
        textDateTime.setText(note.getDateTime());
        initColor();
        initImage();

        imageBack.setOnClickListener(v -> finish());
        imageSave.setOnClickListener(v -> {
            if (updateNote()) {
                Toast.makeText(this, "Sửa thành công!!!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        imageChooser.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(
                    getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        EditNoteActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSION
                );
            } else {
                chooseImage();
            }
        });
        imageDel.setOnClickListener(v -> {
            imageName.setText("");
            selectedImagePath = null;
            imageNote.setVisibility(View.GONE);
        });
        btnDelNote.setOnClickListener(v -> new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Bạn có chắc chắn muốn xoá?")
                .setPositiveButton("Có", (dialog, which) -> {
                    db.deleteNote(note.getId());
                    Toast.makeText(EditNoteActivity.this, "Xoá thành công!!!",
                            Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Không", null)
                .show());
        selectColorListener();
    }

    private boolean updateNote() {
        if (inputNoteTitle.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Chưa có tiêu đề!!!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (inputNoteSubtitle.getText().toString().trim().isEmpty()
                && inputNoteText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Chưa có nội dung!!!", Toast.LENGTH_SHORT).show();
            return false;
        }

        note.setTitle(inputNoteTitle.getText().toString());
        note.setSubtitle(inputNoteSubtitle.getText().toString());
        note.setNoteText(inputNoteText.getText().toString());
        note.setDateTime(getCurrentDateTime());
        note.setColor(selectedColor);
        note.setImagePath(selectedImagePath);
        note.setLastTime(new Date().getTime());
        SQLiteDB db = new SQLiteDB(this);
        db.updateNote(note);
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
        imageChooser = findViewById(R.id.imageChooser);
        imageDel = findViewById(R.id.imageDel);
        btnDelNote = findViewById(R.id.btnDelNote);
        imageName = findViewById(R.id.imageName);
        imageNote = findViewById(R.id.imageNote);
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
        selectedColor = note.getColor();
        setSubtitleIndicatorColor();
        switch (selectedColor) {
            case "#333333":
                checked_color_1.setImageResource(R.drawable.ic_done);
                checked_color_2.setImageResource(0);
                checked_color_3.setImageResource(0);
                checked_color_4.setImageResource(0);
                checked_color_5.setImageResource(0);
                break;
            case "#FDBE3B":
                checked_color_1.setImageResource(0);
                checked_color_2.setImageResource(R.drawable.ic_done);
                checked_color_3.setImageResource(0);
                checked_color_4.setImageResource(0);
                checked_color_5.setImageResource(0);
                break;
            case "#FF4842":
                checked_color_1.setImageResource(0);
                checked_color_2.setImageResource(0);
                checked_color_3.setImageResource(R.drawable.ic_done);
                checked_color_4.setImageResource(0);
                checked_color_5.setImageResource(0);
                break;
            case "#3A52FC":
                checked_color_1.setImageResource(0);
                checked_color_2.setImageResource(0);
                checked_color_3.setImageResource(0);
                checked_color_4.setImageResource(R.drawable.ic_done);
                checked_color_5.setImageResource(0);
                break;
            case "#000000":
                checked_color_1.setImageResource(0);
                checked_color_2.setImageResource(0);
                checked_color_3.setImageResource(0);
                checked_color_4.setImageResource(0);
                checked_color_5.setImageResource(R.drawable.ic_done);
                break;
        }
    }

    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, PICK_IMAGE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }
            Uri uri = data.getData();
            if (uri != null) {
                try {
                    String path = getPathFromUri(uri);
                    imageName.setText(getImageName(path));
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    imageNote.setImageBitmap(bitmap);
                    imageNote.setVisibility(View.VISIBLE);

                    selectedImagePath = path;
                } catch (FileNotFoundException e) {
                    return;
                }
            }
        }
    }

    private String getPathFromUri(Uri uri) {
        String path;
        Cursor cursor = getContentResolver()
                .query(uri, null, null, null, null);
        if (cursor == null) {
            path = uri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex("_data");
            path = cursor.getString(index);
            cursor.close();
        }
        return path;
    }

    private String getImageName(String path) {
        int cut = path.lastIndexOf('/');
        if (cut != -1)
            path = path.substring(cut + 1);
        return path;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                chooseImage();
            } else {
                Toast.makeText(this, "Không được phép truy cập!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initImage() {
        selectedImagePath = note.getImagePath();
        if (selectedImagePath != null) {
            imageName.setText(getImageName(selectedImagePath));
            Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath);
            imageNote.setImageBitmap(bitmap);
            imageNote.setVisibility(View.VISIBLE);
        }
    }
}
