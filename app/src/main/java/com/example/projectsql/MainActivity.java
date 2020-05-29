package com.example.projectsql;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.microedition.khronos.egl.EGLDisplay;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase sql;
    KoneksiDB db;
    SimpleCursorAdapter adapter;
    ListView lv_data;
    FloatingActionButton buttonadd;
    String selectedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new KoneksiDB(this);
        buttonadd = findViewById(R.id.floatingadd);
        lv_data = findViewById(R.id.data_contact);

        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pindah = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(pindah);
            }
        });

        lv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String namadepan, namabelakang, nohp, organisasi, gender, email;
                Cursor row = (Cursor) adapterView.getItemAtPosition(i);
                selectedId = row.getString(0);
                namadepan = row.getString(1);
                namabelakang = row.getString(2);
                nohp = row.getString(3);
                organisasi = row.getString(4);
                gender = row.getString(5);
                email = row.getString(6);
                Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                intent.putExtra("Id", selectedId);
                intent.putExtra("Namadpn", namadepan);
                intent.putExtra("Namablkg", namabelakang);
                intent.putExtra("NoHP", nohp);
                intent.putExtra("Organisasi", organisasi);
                intent.putExtra("Gender", gender);
                intent.putExtra("Email", email);
                startActivity(intent);
            }
        });

        lv_data.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String namadepan, namabelakang, nohp, organisasi, gender, email;
                Cursor row = (Cursor) adapterView.getItemAtPosition(i);
                selectedId = row.getString(0);
                namadepan = row.getString(1);
                namabelakang = row.getString(2);
                nohp = row.getString(3);
                organisasi = row.getString(4);
                gender = row.getString(5);
                email = row.getString(6);
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Warning!");
                builder.setMessage("What do you want to do to " + namadepan + "?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialog.Builder builderdelete = new AlertDialog.Builder(MainActivity.this);
                        builderdelete.setTitle("Warning!");
                        builderdelete.setMessage("Are you sure want to delete " + namadepan + " ?");
                        builderdelete.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sql = db.getWritableDatabase();
                                sql.delete(KoneksiDB.tabel, KoneksiDB.id + "=?", new String[]{selectedId});
                                sql.close();
                                fetchdata();
                                Toast.makeText(MainActivity.this, "Data berhasil dihapus", Toast.LENGTH_LONG).show();
                            }
                        });
                        builderdelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplicationContext(), "Data tidak jadi dihapus", Toast.LENGTH_LONG).show();
                            }
                        });
                        AlertDialog dialogdelete = builderdelete.create();
                        dialogdelete.show();
                    }
                });
                builder.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                        intent.putExtra("Id", selectedId);
                        intent.putExtra("Namadpn", namadepan);
                        intent.putExtra("Namablkg", namabelakang);
                        intent.putExtra("NoHP", nohp);
                        intent.putExtra("Organisasi", organisasi);
                        intent.putExtra("Gender", gender);
                        intent.putExtra("Email", email);
                        startActivity(intent);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return false;
            }
        });
        fetchdata();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchdata();
    }

    public void fetchdata(){
        sql = db.getReadableDatabase();
        Cursor c = sql.query(KoneksiDB.tabel, null, null, null, null, null, null);
        adapter = new SimpleCursorAdapter(this, R.layout.data_contact, c, new String[]{
                KoneksiDB.namadepan, KoneksiDB.nohp},
                new int[]{
                        R.id.namadepankontak, R.id.namabelakangkontak
                }, 1);
        lv_data.setAdapter(adapter);
    }
}
