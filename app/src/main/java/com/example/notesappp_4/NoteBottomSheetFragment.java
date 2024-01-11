package com.example.notesappp_4;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class NoteBottomSheetFragment  extends BottomSheetDialogFragment {
    private EditText editNoteTitle;
    private EditText editNoteContent;
    private Button saveButton;

    private Note currentNote;

    public void setCurrentNote(Note note){
        currentNote = note;
    }

    public  View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_note_bottom_sheet, container, false); // подключили дизайн

        if (currentNote!=null){
            //editNoteTitle.setText();
        }

        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class); // Получение SharedViewModel и запустили его деятельность
        //ViewModelProvider - шкаф с ящиками где каждый ящик представляет собой ViewModel
        //ViewModel — это специальный объект, который хранит информацию для вашего приложения, например, список ваших заметок.

        //requireActivity(): Это как спросить: "Эй, в какой комнате мы сейчас находимся?".
        // В ответ вы получаете название комнаты (это активность, к которой прикреплен ваш фрагмент).

        //get(SharedViewModel.class): Это как сказать: "Я хочу использовать ящик, где хранятся данные для общения между разными частями приложения".
        // Если такой ящик уже есть, вы его получаете, если нет — создаете новый.

        editNoteTitle = view.findViewById(R.id.edit_note_title);
        editNoteContent = view.findViewById(R.id.edit_note_content);
        saveButton = view.findViewById(R.id.button_save);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editNoteTitle.getText().toString();
                String content = editNoteContent.getText().toString();
                Note newNote = new Note(title, content);

                // Обновление LiveData в SharedViewModel
                sharedViewModel.setSelectedNote(newNote); // забрали данные пользователя
                dismiss(); // вышли из активити
            }
        });
        return view; //  вы показываете, что ваш фрагмент должен выглядеть именно так, как вы нарисовали.
    }

}
