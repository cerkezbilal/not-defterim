package com.bilalkarademir.notdefterim;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NotActivity extends AppCompatActivity {

    TextView txtbaslik, txticerik;
    Button btnSil,btnDuzenle;
    EditText etbaslik,eticerik;
    SqliteHelper db;
    Context context = this;
    Not secilenNot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not);
        tanimla();
        tiklamaylaGelenDeger();
        notuSil();
        duzenleAktif();

    }
    int sayac = 0;
    public void duzenleAktif(){
        btnDuzenle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sayac++;
                if(sayac%2==1){

                btnSil.setVisibility(View.GONE);
                etbaslik.setVisibility(View.VISIBLE);
                eticerik.setVisibility(View.VISIBLE);
                }
                else {
                    secilenNot.setBaslik(etbaslik.getText().toString());
                    secilenNot.setIcerik(eticerik.getText().toString());
                    db.NotGuncelle(secilenNot);
                    finish();
                    Toast.makeText(context, "Notunu Başarıyla Güncellendi", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
    public void tanimla(){

        txtbaslik = findViewById(R.id.txtbaslik);
        txticerik = findViewById(R.id.txticerik);
        btnDuzenle = findViewById(R.id.btnDuzenle);
        btnSil = findViewById(R.id.btnSil);
        etbaslik = findViewById(R.id.etbaslik);
        eticerik = findViewById(R.id.eticerik);
    }

    public void tiklamaylaGelenDeger(){
        db = new SqliteHelper(context);
        Intent intent = getIntent();
        int id = intent.getIntExtra("not",-1);
        secilenNot = db.NotOku(id);
        txtbaslik.setText(secilenNot.getBaslik());
        txticerik.setText(secilenNot.getIcerik());
    }

    public void  notuSil(){

        btnSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            db.NotSil(secilenNot);
            finish();
                Toast.makeText(context, "Notunu Başarıyla silindi", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
