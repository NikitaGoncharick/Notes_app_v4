package com.example.notesappp_4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private NoteViewModel  noteViewModel;
    private SharedViewModel sharedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        NoteListAdapter adapter = new NoteListAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnNoteClickListener(new OnNoteClickListener() {
            @Override
            public void onNoteClick(int position) {
                Note secectedNote = noteViewModel.getAllNotes().getValue().get(position); // позиция полученная внутри адаптера путем срабатывания клика благодаря LiveData

            }
        });

        //--------------------------------------------------------------------------------------------------------------
        // Подписываемся на наблюдение за данными с помощью метода observe.
        // Когда данные в noteViewModel или sharedViewModel изменяются, наша активность реагирует на эти изменения.

        // Получаем NoteViewModel, который управляет данными заметок, гарантируя что данные не потеряются, пока приложение работает
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, notes -> { // получаем доступ к наблюдению за изменениями внутри базы данных через LiveData
            adapter.setNotes(notes); // Вызываем срабатывание адаптера, обновляя список заметок в нем, когда данные изменились
        });

        // Получаем SharedViewModel, который используется для обмена данными между фрагментами и активностью
        //  SharedViewModel находится в режиме автоматического отслеживания MutableLiveData<Note>
        // после чего при замеченный изменениях вызывает срабатывание noteViewModel.insert(note);
        // заполняя нашу MVVM систему данными
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        sharedViewModel.getSelectedNote().observe(this, note -> {
            noteViewModel.insert(note);
        });
        //--------------------------------------------------------------------------------------------------------------

        FloatingActionButton fab = findViewById(R.id.fb_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openNoteBottomSheetFragment(null);
                NoteBottomSheetFragment bottomSheetFragment = new NoteBottomSheetFragment();
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            }
        });
    }
    private void openNoteBottomSheetFragment(Note note){
        NoteBottomSheetFragment bottomSheet;
        //bottomSheet = NoteBottomSheetFragment.new
    }
}