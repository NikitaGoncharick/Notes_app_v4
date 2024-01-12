package com.example.notesappp_4;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class NoteBottomSheetFragment  extends BottomSheetDialogFragment {
    private EditText editNoteTitle;
    private EditText editNoteContent;
    private Button saveButton;
    private Note currentNote;
    private SharedViewModel sharedViewModel;
    private ImageView deleteButton;
   public void setCurrentNote(Note note){ // Приняли позицию элемента по которому был клик, передав его из main
       currentNote = note;
   }
    public  View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Надуваем макет фрагмента
        View view = inflater.inflate(R.layout.fragment_note_bottom_sheet, container, false); // подключили дизайн

        editNoteTitle = view.findViewById(R.id.edit_note_title);
        editNoteContent = view.findViewById(R.id.edit_note_content);
        saveButton = view.findViewById(R.id.button_save);
        deleteButton = view.findViewById(R.id.delete_note_button);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class); // Получение SharedViewModel и запустили его деятельность
        //ViewModelProvider - шкаф с ящиками где каждый ящик представляет собой ViewModel
        //ViewModel — это специальный объект, который хранит информацию для вашего приложения, например, список ваших заметок.

        //requireActivity(): Это как спросить: "Эй, в какой комнате мы сейчас находимся?".
        // В ответ вы получаете название комнаты (это активность, к которой прикреплен ваш фрагмент).

        //get(SharedViewModel.class): Это как сказать: "Я хочу использовать ящик, где хранятся данные для общения между разными частями приложения".
        // Если такой ящик уже есть, вы его получаете, если нет — создаете новый.

        if (currentNote != null){
            editNoteTitle.setText(currentNote.getTitle());
            editNoteContent.setText(currentNote.getContent());
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
        
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote();
                dismiss();
            }
        });

        return view; //  вы показываете, что ваш фрагмент должен выглядеть именно так, как вы нарисовали.
    }

    private void deleteNote() {
       if (currentNote != null){
           currentNote.setMarkedForDeletion(true);
           sharedViewModel.setSelectedNote(currentNote); // Установка заметки для удаления
       }
    }

    private void saveNote(){
       String title = editNoteTitle.getText().toString();
       String content = editNoteContent.getText().toString();

       if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)){
           Toast.makeText(getContext(), "Empty Fields", Toast.LENGTH_SHORT).show();
           return;
       }

       if (currentNote!= null){
           // Обновляем заметку
           currentNote.setTitle(title);
           currentNote.setContent(content);
           sharedViewModel.setSelectedNote(currentNote);
       }
       else {
           // Создаем новую заметку
           Note newNote = new Note(title, content);
           sharedViewModel.setSelectedNote(newNote);
       }
        dismiss();
    }

}
