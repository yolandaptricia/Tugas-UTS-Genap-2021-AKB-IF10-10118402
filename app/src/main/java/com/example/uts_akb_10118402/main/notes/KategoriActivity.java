/**
 * NIM : 10118402
 * Nama : Yolanda Patricia
 * Kelas : IF-10
 * Tanggal pengerjaan : 3 Juni 2021
 */

package com.example.uts_akb_10118402.main.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uts_akb_10118402.R;
import com.example.uts_akb_10118402.main.Database;
import com.example.uts_akb_10118402.main.information.InformationActivity;
import com.example.uts_akb_10118402.main.profile.ProfileActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class KategoriActivity extends AppCompatActivity {

    String[] daftar, id_kategori;
    ListView listView;
    private Cursor cursor;
    Database database;
    public static KategoriActivity ka;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);

        this.setTitle("Kategori");
        setContentView(R.layout.activity_kategori);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setSelectedItemId(R.id.notes);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.notes:
                        return true;
                    case  R.id.profile:
                        Intent intent2 = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.about:
                        Intent intent3 = new Intent(getApplicationContext(), InformationActivity.class);
                        startActivity(intent3);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KategoriActivity.this, SaveKategoriActivity.class);
                startActivity(intent);
            }
        });

        ka = this;
        database = new Database(this);
        RefreshList();
    }

    public void RefreshList() {
        SQLiteDatabase db = database.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM kategori", null);
        id_kategori = new String[cursor.getCount()];
        daftar = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int cc=0; cc < cursor.getCount(); cc++){
            cursor.moveToPosition(cc);
            id_kategori[cc] = cursor.getString(0).toString();
            daftar[cc] = cursor.getString(1).toString();
        }

        listView = findViewById(R.id.listKategori);

        MyAdapter adapter = new MyAdapter();
        listView.setAdapter(adapter);
        listView.setSelected(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final int selection = Integer.parseInt(id_kategori[arg2]);
                Intent intent = new Intent(KategoriActivity.this, ListNoteActivity.class);
                intent.putExtra("kategori", selection);
                startActivity(intent);
            }});

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id2) {
                final int which_item = position;

                final CharSequence[] dialogitem = {"Edit Kategori", "Hapus Kategori"};
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(KategoriActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch(item){
                            case 0 :
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(KategoriActivity.this);
                                builder1.setTitle("Edit Kategori");
                                View view = getLayoutInflater().inflate(R.layout.update_kategori, null);

                                //init
                                EditText namaKategori = view.findViewById(R.id.namaKategori);

                                builder1.setView(view);

                                builder1.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SQLiteDatabase db = database.getWritableDatabase();
                                        db.execSQL("update kategori set kategori='"+ namaKategori +"' " +
                                                "WHERE id_kategori = "+ id_kategori[which_item] +"");
                                        RefreshList();
                                    }
                                });
                                builder1.setNegativeButton("Batal", null);
                                builder1.create().show();
                                break;
                            case 1 :
                                AlertDialog.Builder builder = new AlertDialog.Builder(KategoriActivity.this);
                                builder.setTitle("Delete Kategori");
                                builder.setMessage("Yakin menghapus kategori ini?");
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SQLiteDatabase db = database.getWritableDatabase();
                                        db.execSQL("delete from kategori where id_kategori ="+ id_kategori[which_item]);
                                        RefreshList();
                                    }
                                });
                                builder.setNegativeButton("No", null);
                                builder.create().show();
                                break;
                        }
                    }
                });
                builder.create().show();
                return true;
            }});
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
            convertView = getLayoutInflater().inflate(R.layout.kategori, parent, false);

            TextView kategoriTitle = convertView.findViewById(R.id.kategori_title);
            kategoriTitle.setText(daftar[position]);

            return convertView;
        }
    }
}