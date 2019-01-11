package com.bilalkarademir.notdefterim;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Context context = this;
    EditText etbaslikEkle,eticerikEkle;
    Button btnnotEkle,btnnotVazgec;
    ListView listemiz;
    SqliteHelper db = new SqliteHelper(context);
    List<Not> list;
    ArrayAdapter<String > madapter;
    ImageButton imgbtnEkle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tanimla();
        db.getWritableDatabase();//eklenen veriler her açıldığınd agelmesi için
        verileriGoster();
        veriEklemeEkraniAc();
        NotEklemeVazgec();
        VeriEkle();



    }

    public void tanimla(){

        listemiz = findViewById(R.id.listemiz);
        imgbtnEkle = findViewById(R.id.imgbtnEkle);
        btnnotEkle = findViewById(R.id.btnNotEkle);
        btnnotVazgec = findViewById(R.id.btnNotVazgec);
        etbaslikEkle = findViewById(R.id.etbaslikEkle);
        eticerikEkle = findViewById(R.id.eticerikEkle);

    }

    public void veriEklemeEkraniAc(){


        imgbtnEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    etbaslikEkle.setText("");
                    eticerikEkle.setText("");
                    imgbtnEkle.setVisibility(View.GONE);
                    btnnotEkle.setVisibility(View.VISIBLE);
                    etbaslikEkle.setVisibility(View.VISIBLE);
                    eticerikEkle.setVisibility(View.VISIBLE);
                    btnnotVazgec.setVisibility(View.VISIBLE);


            }
        });
    }

    public void NotEklemeVazgec(){
      btnnotVazgec.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              imgbtnEkle.setVisibility(View.VISIBLE);
              btnnotEkle.setVisibility(View.GONE);
              etbaslikEkle.setVisibility(View.GONE);
              eticerikEkle.setVisibility(View.GONE);
              btnnotVazgec.setVisibility(View.GONE);
              eticerikEkle.setText("");
              etbaslikEkle.setText("");
          }
      });

    }


    public void veriEklensin(){
       // db.onUpgrade(db.getWritableDatabase(),1,2); ilk uygulamada deneme amaçlı yazdım

        /* db.NotEkle(new Not("Matematik Ödevi","Üslü sayılar ödevini yap"));
        db.NotEkle(new Not("Coğrafya Ödevi","Yer şekilleri ödevini yap"));
        db.NotEkle(new Not("Türkçe Ödevi","Paragraf çöz"));
        db.NotEkle(new Not("Çıktı Al","Ödev çıktısı al"));
        db.NotEkle(new Not("Doğum günü","Ablama hediye alacağım"));
        yukarısı deneme amaclı yazıldı
        */

    }

    public void VeriEkle(){

        btnnotEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String baslikVeri = etbaslikEkle.getText().toString();
                String icerikVeri = eticerikEkle.getText().toString();
                db.NotEkle(new Not(baslikVeri,icerikVeri));
                etbaslikEkle.setVisibility(View.GONE);
                eticerikEkle.setVisibility(View.GONE);
                btnnotVazgec.setVisibility(View.GONE);
                btnnotEkle.setVisibility(View.GONE);
                eticerikEkle.setText("");
                etbaslikEkle.setText("");
                imgbtnEkle.setVisibility(View.VISIBLE);
                yenile();
                Toast.makeText(context, "Notunu Başarıyla Eklendi", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void verileriGoster(){
        yenile();
        listemiz.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context,NotActivity.class);
                intent.putExtra("not",list.get(position).getId());
                startActivityForResult(intent,1);

            }
        });
    }

    public void yenile(){
        list= db.NotListele();
        List<String> listebaslik = new ArrayList<>();
        for(int i =0; i<list.size();i++){

            listebaslik.add(i,list.get(i).baslik);
        }
        madapter = new ArrayAdapter<String>(context,R.layout.satir_layout,R.id.listmetin,listebaslik);
        listemiz.setAdapter(madapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        yenile();
    }


}
