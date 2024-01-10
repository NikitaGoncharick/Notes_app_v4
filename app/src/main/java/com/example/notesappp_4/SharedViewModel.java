package com.example.notesappp_4;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Note> selectedNote = new MutableLiveData<>();
    // Более продвинутый анагол LiveData, направленная на изменения в реальном времени
    // если система  должна не только получить данные, но и реагировать на их изменения в реальном времени
    // (например, обновлять интерфейс или изменять свое поведение в зависимости от этих данных),
    // MutableLiveData предоставляет удобный механизм для такого взаимодействия.

    public void setSelectedNote(Note note) {// Метод для получения данных, которые введет пользователь в заметке (установки в наши поля транспортировщика(set))
        selectedNote.setValue(note);
    }

    public MutableLiveData<Note> getSelectedNote(){ // Получение LiveData, которая наблюдается MainActivity
        return selectedNote;
    }
}
