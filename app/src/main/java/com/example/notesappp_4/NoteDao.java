package com.example.notesappp_4;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao //интерфейс для доступа к базе данных
public interface NoteDao {
    @Insert
    void insert(Note note);

    @Update
    void upadte(Note note);

    @Delete
    void delete(Note note);

    @Query("select * from  note")
    LiveData<List<Note>> getAllNotes();
}
