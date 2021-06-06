/**
 * NIM : 10118402
 * Nama : Yolanda Patricia
 * Kelas : IF-10
 * Tanggal pengerjaan : 4 Juni 2021
 */

package com.example.uts_akb_10118402.main.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.uts_akb_10118402.R;
import com.example.uts_akb_10118402.main.Database;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListNoteActivity extends AppCompatActivity {

    String[] daftar, daftar2, daftar3, daftar4, daftar5, id;
    ListView listView;
    int kategori;
    private Cursor cursor;
    Database database;
    public static ListNoteActivity ln;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_note);

        this.setTitle("Notes");
        setContentView(R.layout.activity_list_note);

        Intent getKategori = getIntent();
        kategori = getKategori.getIntExtra("kategori", 0);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListNoteActivity.this, NotesActivity.class);
                intent.putExtra("kategori", kategori);
                startActivity(intent);
            }
        });

        ln = this;
        database = new Database(this);
        RefreshListNote();
    }

    public void RefreshListNote() {
        SQLiteDatabase db = database.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM note WHERE id_kategori = "+kategori, null);
        id = new String[cursor.getCount()];
        daftar = new String[cursor.getCount()];
        daftar2 = new String[cursor.getCount()];
        daftar3 = new String[cursor.getCount()];
        daftar4 = new String[cursor.getCount()];
        daftar5 = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int cc=0; cc < cursor.getCount(); cc++){
            cursor.moveToPosition(cc);
            id[cc] = cursor.getString(0).toString();
            daftar[cc] = cursor.getString(1).toString();
            daftar2[cc] = cursor.getString(2).toString();
            daftar3[cc] = cursor.getString(3).toString();
            daftar4[cc] = cursor.getString(4).toString();
            daftar5[cc] = cursor.getString(5).toString();
        }

        listView = findViewById(R.id.listNote);

        ListNoteActivity.MyAdapter adapter = new ListNoteActivity.MyAdapter();
        listView.setAdapter(adapter);
        listView.setSelected(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final int selection = Integer.parseInt(id[arg2]);
                Intent in = new Intent(getApplicationContext(), UpdateNoteActivity.class);
                in.putExtra("id", selection);
                startActivity(in);
            }});

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id2) {
                final int which_item = position;

                AlertDialog.Builder builder = new AlertDialog.Builder(ListNoteActivity.this);
                builder.setTitle("Delete Note");
                builder.setMessage("Yakin menghapus note ini?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = database.getWritableDatabase();
                        db.execSQL("delete from note where id ="+ id[which_item]);
                        RefreshListNote();
                    }
                });
                builder.setNegativeButton("No", null);
                builder.create().show();
                return true;
            }
        });
    }

    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return cursor.getCount();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.items_note, parent, false);

            TextView noteTitle = convertView.findViewById(R.id.text_title);
            TextView noteDesc = convertView.findViewById(R.id.text_content);
            TextView noteDate = convertView.findViewById(R.id.text_date);
            TextView noteTime = convertView.findViewById(R.id.text_time);

            noteDate.setText(daftar[position]);
            noteTime.setText(daftar2[position]);
            noteTitle.setText(daftar3[position]);
            noteDesc.setText(daftar4[position]);

            return convertView;
        }
    }
}