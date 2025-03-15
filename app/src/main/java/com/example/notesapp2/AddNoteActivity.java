package com.example.notesapp2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashSet;
import java.util.Set;

public class AddNoteActivity extends AppCompatActivity {
    private EditText noteInput;
    private Button saveButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        noteInput = findViewById(R.id.noteInput);
        saveButton = findViewById(R.id.saveButton);
        sharedPreferences = getSharedPreferences("NotesApp", Context.MODE_PRIVATE);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    private void saveNote() {
        String newNote = noteInput.getText().toString();
        if (!newNote.isEmpty()) {
            Set<String> notes = sharedPreferences.getStringSet("notes", new HashSet<>());
            notes.add(newNote);
            sharedPreferences.edit().putStringSet("notes", notes).apply();
            finish(); // Închide activitatea după salvare
        }
    }
}
