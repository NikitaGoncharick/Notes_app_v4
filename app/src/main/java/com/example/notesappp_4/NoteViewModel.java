package com.example.notesappp_4;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

//УПРАВЛЯЕМ ДАННЫМИ ИЗ UI
public class NoteViewModel extends AndroidViewModel {
    private LiveData<List<Note>> allNotes;// Список всех заметок
    private NoteDao noteDao; //DAO для взаимодействия с базой данных
    public NoteViewModel(@NonNull Application application) {
        super(application);
        NoteDatabase noteDatabase = NoteDatabase.getDataBase(application); // почему application
        noteDao = noteDatabase.noteDao(); // получаем доступ к операциям в @Dao
        allNotes = noteDao.getAllNotes(); // получив доступ выгружаем данные
    }

    void insert(Note note){
        NoteDatabase.databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.insert(note);
            }
        });
    }
    void update(Note note){
        NoteDatabase.databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.upadte(note);
            }
        });
    }
    void delete(Note note){
        NoteDatabase.databaseExecutor.execute(new Runnable() {
            @Override
            public void run() {
                noteDao.delete(note);
            }
        });
    }

    //реализация DAO метода для получения всех заметок
    LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }
}
