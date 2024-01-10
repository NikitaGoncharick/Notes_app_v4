package com.example.notesappp_4;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();// Метод для доступа к функциям DAO

    private static volatile NoteDatabase database; // volatile обеспечивает "видимость" изменений переменной всеми потоками.
    private static final int NUMBERS_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBERS_OF_THREADS);

    static NoteDatabase getDataBase(final Context context){ // зачем  final
        if (database == null){
            database = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "note_databse")
                    .fallbackToDestructiveMigrationOnDowngrade().build();
        }
        return database;
    }

}
