package com.example.notesapp2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private ArrayList<String> notes;
    private SharedPreferences sharedPreferences;
    private Context context;

    public NotesAdapter(ArrayList<String> notes, SharedPreferences sharedPreferences) {
        this.notes = notes;
        this.sharedPreferences = sharedPreferences;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.noteText.setText(notes.get(position));
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDelete(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    private void confirmDelete(int position) {
        new AlertDialog.Builder(context)
                .setTitle("Confirm Delete")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        removeNote(position);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void removeNote(int position) {
        String noteToRemove = notes.get(position);
        notes.remove(position);
        notifyItemRemoved(position);

        Set<String> updatedNotes = new HashSet<>(notes);
        sharedPreferences.edit().putStringSet("notes", updatedNotes).apply();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView noteText;
        ImageView deleteButton;

        ViewHolder(View itemView) {
            super(itemView);
            noteText = itemView.findViewById(R.id.noteText);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
