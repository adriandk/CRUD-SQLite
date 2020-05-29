package com.example.projectsql;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    String Id, namadepan, namabelakang, nohp, organisasi, gender, email;
    Button edit, delete, telp, sms;
    TextView namadpnedit, namablkgedit, nohpedit, emailedit;
    Spinner organisasiedit;
    SQLiteDatabase sql;
    KoneksiDB db;
    RadioButton pria,wanita;
    RadioGroup pw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        db = new KoneksiDB(this);
//        get data from MainActiviy(start)
        Id = getIntent().getStringExtra("Id");
        namadepan = getIntent().getStringExtra("Namadpn");
        namabelakang = getIntent().getStringExtra("Namablkg");
        nohp = getIntent().getStringExtra("NoHP");
        organisasi = getIntent().getStringExtra("Organisasi");
        gender = getIntent().getStringExtra("Gender");
        email = getIntent().getStringExtra("Email");
//        get data from MainActiviy(end)

//        findViewById(start)
        namadpnedit = findViewById(R.id.nama_depan);
        namablkgedit = findViewById(R.id.nama_belakang);
        nohpedit = findViewById(R.id.nohp);
        emailedit = findViewById(R.id.email);
        organisasiedit = findViewById(R.id.organisasi);
        pria = findViewById(R.id.rb_pria);
        wanita = findViewById(R.id.rb_wanita);
        pw = findViewById(R.id.radioGroup);
        edit = findViewById(R.id.button_simpan);
        delete = findViewById(R.id.button_batal);
        sms = findViewById(R.id.whatsappbutton);
        telp = findViewById(R.id.telfonbutton);
//        findViewById(end)

//        set data from database (start)
        namadpnedit.setText(namadepan);
        namablkgedit.setText(namabelakang);
        nohpedit.setText(nohp);
        emailedit.setText(email);
        if(gender.equals("Pria")){
            pria.setChecked(true);
        }
        else {
            wanita.setChecked(true);
        }
        if (organisasi.equals("POLRI")){
           organisasiedit.setSelection(0);
        }
        else if (organisasi.equals("TNI")){
            organisasiedit.setSelection(1);
        }
        else if (organisasi.equals("BNPB")){
            organisasiedit.setSelection(2);
        }
        else if (organisasi.equals("KPK")){
            organisasiedit.setSelection(3);
        }
        else{
            organisasiedit.setSelection(4);
        }
//        set data from database (end)

//        EDTI DATA (start)
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (namadpnedit.getText().toString().equals("")||namablkgedit.getText().toString().equals("")||nohpedit.getText().toString().equals("")||emailedit.getText().toString().equals("")||(!pria.isChecked()&&!wanita.isChecked())){
                    if(namadpnedit.getText().toString().equals("")){
                        namadpnedit.setError("");
                    }
                    if(namablkgedit.getText().toString().equals("")){
                        namablkgedit.setError("");
                    }
                    if(nohpedit.getText().toString().equals("")){
                        nohpedit.setError("");
                    }
                    if(emailedit.getText().toString().equals("")){
                        emailedit.setError("");
                    }
                    Toast.makeText(EditActivity.this, "Masukan Data dengan benar", Toast.LENGTH_LONG).show();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
                    builder.setTitle("Warning");
                    builder.setMessage("Are you sure want to update?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ContentValues cv = new ContentValues();
//                              get data and then prepare data to input data to database
                            cv.put(KoneksiDB.namadepan, namadpnedit.getText().toString());
                            cv.put(KoneksiDB.namabelakang, namablkgedit.getText().toString());
                            cv.put(KoneksiDB.email, emailedit.getText().toString());
                            cv.put(KoneksiDB.nohp, nohpedit.getText().toString());
                            cv.put(KoneksiDB.organisasi, organisasiedit.getSelectedItem().toString());
                            if(pria.isChecked()){
                                cv.put(KoneksiDB.gender, "Pria");
                            }
                            else {
                                cv.put(KoneksiDB.gender, "Wanita");
                            }
//                              update database
                            sql = db.getWritableDatabase();
                            sql.update(KoneksiDB.tabel, cv, KoneksiDB.id + "=?", new String[]{Id});
                            sql.close();
                            Toast.makeText(EditActivity.this, "Data berhasil diupdate", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(EditActivity.this, "Data tidak jadi diupdate", Toast.LENGTH_SHORT).show();
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builderdelete = new AlertDialog.Builder(EditActivity.this);
                builderdelete.setTitle("Warning!");
                builderdelete.setMessage("Are you sure want to delete " + namadpnedit.getText().toString() + " ?");
                builderdelete.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sql = db.getWritableDatabase();
                        sql.delete(KoneksiDB.tabel, KoneksiDB.id + "=?", new String[]{Id});
                        sql.close();
                        Toast.makeText(EditActivity.this, "Data berhasil dihapus", Toast.LENGTH_LONG).show();
                        finish();
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

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sms = new Intent(Intent.ACTION_VIEW);
                sms.setData(Uri.parse("smsto: " + nohpedit.getText().toString()));
                startActivity(sms);
            }
        });

        telp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent telepon = new Intent(Intent.ACTION_DIAL);
                telepon.setData(Uri.parse("tel:" + nohpedit.getText().toString()));
                startActivity(telepon);
            }
        });
    }
}
