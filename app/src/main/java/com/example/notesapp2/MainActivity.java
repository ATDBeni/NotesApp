package com.example.notesapp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NotesAdapter adapter;
    private Button addNoteButton;
    private SharedPreferences sharedPreferences;
    private ArrayList<String> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        addNoteButton = findViewById(R.id.addNoteButton);
        sharedPreferences = getSharedPreferences("NotesApp", Context.MODE_PRIVATE);

        notesList = new ArrayList<>(sharedPreferences.getStringSet("notes", new HashSet<>()));
        adapter = new NotesAdapter(notesList, sharedPreferences);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddNoteActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }

    private void loadNotes() {
        notesList.clear();
        notesList.addAll(sharedPreferences.getStringSet("notes", new HashSet<>()));
        adapter.notifyDataSetChanged();
    }
}
