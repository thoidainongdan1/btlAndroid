package com.example.btl.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.btl.model.Note;
import com.example.btl.model.Schedule;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "NoteDB.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql1 = "CREATE TABLE note(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "subtitle TEXT," +
                "dateTime TEXT," +
                "noteText TEXT," +
                "imagePath TEXT," +
                "color TEXT," +
                "lastTime LONG);\n";

        String sql2 = "CREATE TABLE schedule(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "content TEXT)";

        db.execSQL(sql1);
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addNote(Note note) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", note.getTitle());
        contentValues.put("subtitle", note.getSubtitle());
        contentValues.put("dateTime", note.getDateTime());
        contentValues.put("noteText", note.getNoteText());
        contentValues.put("imagePath", note.getImagePath());
        contentValues.put("color", note.getColor());
        contentValues.put("lastTime", note.getLastTime());
        SQLiteDatabase db = getWritableDatabase();
        db.insert("note", null, contentValues);
    }

    public void addSchedule(Schedule schedule) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("content", schedule.getContent());
        SQLiteDatabase db = getWritableDatabase();
        db.insert("schedule", null, contentValues);
    }

    public List<Note> getAllNotes() {
        List<Note> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("note", null, null, null,
                null, null, "lastTime DESC");
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String subtitle = cursor.getString(2);
            String dateTime = cursor.getString(3);
            String noteText = cursor.getString(4);
            String imagePath = cursor.getString(5);
            String color = cursor.getString(6);
            long lastTime = cursor.getLong(7);
            list.add(new Note(id, title, subtitle, dateTime, noteText, imagePath, color, lastTime));
        }
        cursor.close();

        return list;
    }

    public List<Schedule> getAllSchedules() {
        List<Schedule> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("schedule", null, null, null,
                null, null, "id DESC");
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String content = cursor.getString(1);
            list.add(new Schedule(id, content));
        }
        cursor.close();

        return list;
    }

    public Note getNoteById(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(id)};
        Note note = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("note", null, whereClause, whereArgs, null,
                null, null);
        if (cursor != null && cursor.moveToNext()) {
            String title = cursor.getString(1);
            String subtitle = cursor.getString(2);
            String dateTime = cursor.getString(3);
            String noteText = cursor.getString(4);
            String imagePath = cursor.getString(5);
            String color = cursor.getString(6);
            long lastTime = cursor.getLong(7);
            note = new Note(id, title, subtitle, dateTime, noteText, imagePath, color, lastTime);

            cursor.close();
        }

        return note;
    }

    public int deleteNote(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("note", whereClause, whereArgs);
    }

    public int deleteSchedule(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("schedule", whereClause, whereArgs);
    }

    public int updateNote(Note note) {
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(note.getId())};

        ContentValues contentValues = new ContentValues();
        contentValues.put("title", note.getTitle());
        contentValues.put("subtitle", note.getSubtitle());
        contentValues.put("dateTime", note.getDateTime());
        contentValues.put("noteText", note.getNoteText());
        contentValues.put("imagePath", note.getImagePath());
        contentValues.put("color", note.getColor());
        contentValues.put("lastTime", note.getLastTime());
        SQLiteDatabase db = getWritableDatabase();
        return db.update("note", contentValues, whereClause, whereArgs);
    }

    public List<Note> searchByTitle(String key) {
        List<Note> list = new ArrayList<>();
        String whereClause = "title like ?";
        String[] whereArgs = {"%" + key + "%"};
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query("note", null, whereClause, whereArgs,
                null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String subtitle = cursor.getString(2);
            String dateTime = cursor.getString(3);
            String noteText = cursor.getString(4);
            String imagePath = cursor.getString(5);
            String color = cursor.getString(6);
            long lastTime = cursor.getLong(7);
            list.add(new Note(id, title, subtitle, dateTime, noteText, imagePath, color, lastTime));
        }
        cursor.close();

        return list;
    }

    public List<Schedule> searchByContent(String key) {
        List<Schedule> list = new ArrayList<>();
        String whereClause = "content like ?";
        String[] whereArgs = {"%" + key + "%"};
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query("schedule", null, whereClause, whereArgs,
                null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String content = cursor.getString(1);
            list.add(new Schedule(id, content));
        }
        cursor.close();

        return list;
    }
}
