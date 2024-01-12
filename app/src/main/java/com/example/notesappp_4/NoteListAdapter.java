package com.example.notesappp_4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

//По прежнему необходим для передачи данных в RecyclerViwe
public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {
    //преобразует  XML объкутов RecyclerView в фактический объект View.
    private List<Note> mNotes = new ArrayList<>();// Инициализируем список заметок пустым списком
    private OnNoteClickListener onNoteClickListener;//интерфейс обработки кликов
    public NoteListAdapter(){

    }
    public void setOnNoteClickListener(OnNoteClickListener onNoteClickListener){  // Метод для установки слушателя кликов
        this.onNoteClickListener = onNoteClickListener;
    }
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        if (!mNotes.isEmpty()){
            Note currentNote  = mNotes.get(position);
            holder.noteItemView.setText(currentNote.getContent());
            holder.note_title.setText(currentNote.getTitle());
        }
        else {
            holder.noteItemView.setText("No Note");
            holder.note_title.setText("No Title");
        }
    }

    public void setNotes(List<Note> notes){
        mNotes = notes; //объявляем передачу текущего состояния бд на отгрузку
        notifyDataSetChanged();  // Уведомляем RecyclerView об изменении данных
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView note_title;
        private  TextView noteItemView;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteItemView = itemView.findViewById(R.id.note_content);
            note_title = itemView.findViewById(R.id.note_title);

            itemView.setOnClickListener(this); // !!! слушатель кликов для itemView (начало процесса обработки)
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition(); // получили позицию элемента
            onNoteClickListener.onNoteClick(position);  // передали int значение позиции в конструктор, который предварительно настроили на принтяие int значений
        }
    }

}
