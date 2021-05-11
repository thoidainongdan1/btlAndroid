package com.example.btl.activities;

import android.Manifest;
import android.app.Activity;
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
import com.example.btl.model.Note;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateNoteActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private static final int REQUEST_PERMISSION = 2;

    private EditText inputNoteTitle, inputNoteSubtitle, inputNoteText;
    private TextView textDateTime;
    private View viewSubtitleIndicator;
    private ImageView imageBack, imageSave;
    private ImageView color_1, color_2, color_3, color_4, color_5;
    private ImageView checked_color_1, checked_color_2, checked_color_3, checked_color_4, checked_color_5;
    private String selectedColor, selectedImagePath;
    private AppCompatButton imageChooser, imageDel;
    private ImageView imageNote;
    private TextView imageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initView();
        initColor();

        textDateTime.setText(getCurrentDateTime());

        imageBack.setOnClickListener(v -> finish());
        imageSave.setOnClickListener(v -> {
            if (addNote()) {
                Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        imageChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(
                        getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            CreateNoteActivity.this,
                            new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_PERMISSION
                    );
                } else {
                    chooseImage();
                }
            }
        });
        imageDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageName.setText("");
                selectedImagePath = null;
                imageNote.setVisibility(View.GONE);
                imageDel.setVisibility(View.GONE);
            }
        });

        selectColorListener();
    }

    private boolean addNote() {
        if (inputNoteTitle.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Chưa có tiêu đề!!!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (inputNoteSubtitle.getText().toString().trim().isEmpty()
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
        note.setImagePath(selectedImagePath);
        note.setLastTime(new Date().getTime());
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
        imageChooser = findViewById(R.id.imageChooser);
        imageDel = findViewById(R.id.imageDel);
        imageName = findViewById(R.id.imageName);
        imageNote = findViewById(R.id.imageNote);
    }

    private String getCurrentDateTime() {
        return new SimpleDateFormat("EEEE, dd-MM-yyyy HH:mm a", Locale.getDefault())
                .format(new Date());
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

    private void chooseImage() {
         Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
         if(intent.resolveActivity(getPackageManager()) != null) {
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
            if(uri != null) {
                try {
                    String path = getPathFromUri(uri);
                    imageName.setText(getImageName(path));
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    imageNote.setImageBitmap(bitmap);
                    imageNote.setVisibility(View.VISIBLE);
                    imageDel.setVisibility(View.VISIBLE);

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
                .query(uri, null,null,null,null);
        if(cursor == null) {
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
        if(requestCode == REQUEST_PERMISSION && grantResults.length > 0) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                chooseImage();
            } else {
                Toast.makeText(this, "Không được phép truy cập!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}