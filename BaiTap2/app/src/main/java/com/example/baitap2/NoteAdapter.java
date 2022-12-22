package com.example.baitap2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;

import io.realm.Realm;
import io.realm.RealmResults;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {

    Context context;
    RealmResults<Note> noteList;

    public NoteAdapter(Context context, RealmResults<Note> noteList) {
        this.context = context;
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.note_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.title.setText(note.getTitle());
        holder.description.setText(note.getDescription());

        String formattedTime = DateFormat.getDateTimeInstance().format(note.createdTime);
        holder.createdTime.setText(formattedTime);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(context, v);
                menu.getMenu().add("Delete");
                menu.getMenu().add("Edit");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("Delete")){
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            note.deleteFromRealm();
                            realm.commitTransaction();
                            Toast.makeText(context, "Note deleted", Toast.LENGTH_LONG).show();
                        }else{
                            Intent i = new Intent(context, EditNoteActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.putExtra("title", note.getTitle());
                            i.putExtra("des", note.getDescription());
                            Realm realm = Realm.getDefaultInstance();
                            context.startActivity(i);
                            realm.beginTransaction();
                            note.deleteFromRealm();
                            realm.commitTransaction();
                        }
                        return true;
                    }
                });
                menu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        TextView createdTime;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleOutput);
            description = itemView.findViewById(R.id.descriptionOutput);
            createdTime = itemView.findViewById(R.id.createdTimeOutput);
        }
    }
}
