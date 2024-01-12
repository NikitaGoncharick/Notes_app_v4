package com.example.notesappp_4;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Таблица БД
@Entity
public class Note {
    private boolean isMarkedForDeletion;
    public boolean isMarkedForDeletion(){
        return isMarkedForDeletion;
    }
    public void setMarkedForDeletion(boolean markedForDeletion){
        isMarkedForDeletion = markedForDeletion;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "content") // поле для текста заметки, в таблице оно будет называться "content"
    private String content; // Текст заметки

    // Конструктор для создания новой заметки
    public Note(String title, String content){
        this.title = title;
        this.content = content;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
