package com.example.projectsql;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    Button batal, simpan;
    EditText editNamaDpn, editNamaBlkg, editHP, editEmail;
    Spinner spinOrganisasi;
    SQLiteDatabase sql;
    KoneksiDB db;
    RadioButton pria,wanita;
    RadioGroup pw;
    ListView lv_data;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        db = new KoneksiDB(this);

        batal = findViewById(R.id.button_batal);
        simpan = findViewById(R.id.button_simpan);
        editNamaDpn = findViewById(R.id.nama_depan);
        editNamaBlkg = findViewById(R.id.nama_belakang);
        editHP = findViewById(R.id.nohp);
        editEmail = findViewById(R.id.email);
        spinOrganisasi = findViewById(R.id.organisasi);
        pria = findViewById(R.id.rb_pria);
        wanita = findViewById(R.id.rb_wanita);
        pw = findViewById(R.id.radioGroup);
        lv_data = findViewById(R.id.data_contact);


        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editNamaDpn.setText("");
                editNamaBlkg.setText("");
                editHP.setText("");
                editEmail.setText("");
                spinOrganisasi.setSelection(0);
                pw.clearCheck();
            }
        });
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editNamaDpn.getText().toString().equals("")||editNamaBlkg.getText().toString().equals("")||editHP.getText().toString().equals("")||editEmail.getText().toString().equals("")||(!pria.isChecked()&&!wanita.isChecked())){
                    if(editNamaDpn.getText().toString().equals("")){
                        editNamaDpn.setError("");
                    }
                    if(editNamaBlkg.getText().toString().equals("")){
                        editNamaBlkg.setError("");
                    }
                    if(editHP.getText().toString().equals("")){
                        editHP.setError("");
                    }
                    if(editEmail.getText().toString().equals("")){
                        editEmail.setError("");
                    }
                    Toast.makeText(AddActivity.this, "Masukan Data dengan benar", Toast.LENGTH_LONG).show();
                }
                else{
                    ContentValues cv = new ContentValues();
//                  get data and then prepare data to input data to database
                    cv.put(KoneksiDB.namadepan, editNamaDpn.getText().toString());
                    cv.put(KoneksiDB.namabelakang, editNamaBlkg.getText().toString());
                    cv.put(KoneksiDB.email, editEmail.getText().toString());
                    cv.put(KoneksiDB.nohp, editHP.getText().toString());
                    cv.put(KoneksiDB.organisasi, spinOrganisasi.getSelectedItem().toString());
                    if(pria.isChecked()){
                        cv.put(KoneksiDB.gender, "Pria");
                    }
                    else {
                        cv.put(KoneksiDB.gender, "Wanita");
                    }
//                  insert into database
                    sql = db.getWritableDatabase();
                    sql.insert(KoneksiDB.tabel, null, cv);
                    sql.close();
                    Toast.makeText(AddActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
