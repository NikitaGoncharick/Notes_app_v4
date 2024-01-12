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

        //---------------------------------ЛОГИКА ИНТЕРФЕЙСА, КОТОРЫЙ ВЫЗЫВАЕТСЯ ПРИ СРАБАТЫВАНИИ КЛИКА ПО ЗАМЕТКЕ--------------
        adapter.setOnNoteClickListener(new OnNoteClickListener() {
            @Override
            public void onNoteClick(int position) { // автоматически принимаем на вход int позицию, которую толкьо что получили в адаптере, когда произошел клик
                Note selectedNote = noteViewModel.getAllNotes().getValue().get(position);//получение заметки по ее позиции пришедшей из адаптера
                openNoteBottomSheetFragment(selectedNote); // вызываем метод открытия BottomSheetFragment передав ему на вход позцию элемента по которому был клик

            }
        });

        //-----------------------------------------------------ВТОРАЯ ФАЗА--------------------------------------------------
        // Подписываемся на наблюдение за данными с помощью метода observe.
        // Когда данные в noteViewModel или sharedViewModel изменяются, наша активность реагирует на эти изменения.

        // Получаем NoteViewModel, который управляет данными заметок, гарантируя что данные не потеряются, пока приложение работает
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, notes -> { // получаем доступ к наблюдению за изменениями внутри базы данных через LiveData
            adapter.setNotes(notes); //т.к было изменнеие в Note, Вызываем срабатывание адаптера, обновляя список заметок в нем, когда данные изменились
        });

        //--------------------------------------------------------ПЕРВАЯ ФАЗА-------------------------------------------------------------
        // Получаем SharedViewModel, который используется для обмена данными между фрагментами и активностью
        //  SharedViewModel находится в режиме автоматического отслеживания MutableLiveData<Note>
        // после чего при замеченный изменениях вызывает срабатывание noteViewModel.insert(note);
        // заполняя нашу MVVM систему данными
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        sharedViewModel.getSelectedNote().observe(this, note -> {
            if (note != null){
                if (note.isMarkedForDeletion()){
                    noteViewModel.delete(note);
                } else if (note.getId() != 0) {
                    noteViewModel.update(note); // вызов ИЗМЕНЕНИЙ В Note
                }
                else {
                    noteViewModel.insert(note); // вызов ДОБАВЛЕНИЯ В Note
                }
            }
        });
        //---------------------------------------СОЗДАНИЕ НОВОЙ ЗАМЕТКИ------------------------------------------

        FloatingActionButton fab = findViewById(R.id.fb_add);// страт создания НОВОЙ заметки
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNoteBottomSheetFragment(null); //null указывает, что вы не редактируете существующую заметку, а создаёте новую.
            }
        });
    }
   private void openNoteBottomSheetFragment(Note note){
        NoteBottomSheetFragment bottomSheet = new NoteBottomSheetFragment();
        bottomSheet.setCurrentNote(note); // если  вызывали openNoteBottomSheetFragment(null);, note здесь будет null.
                                            // Это означает, что в диалоговом окне не будет загружена существующая заметка
                                            // Если не null - откроется соответствующая ячейка
                                            // + сразу же передаем в NoteBottomSheetFragment позицию элемента в RecyclerView
        bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
   }
    //---------------------------------------------------------------------------------------------------------------------
}