package com.project.tigunibh2015_2.rastreador;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class RastreadorActivity extends AppCompatActivity{
    ArrayList<Dependente> dependentes = new ArrayList<Dependente>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rastreador);


        for(int i = 0 ; i<15; i++){
            Dependente d = new Dependente();
            d.setNome("Fulano de Tal" + (i+1));
            dependentes.add(d);
        }
        ListView lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(new DependenteAdapter(this, dependentes));
        lv.setDividerHeight(25);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(RastreadorActivity.this, dependentes.get(position).getNome(), Toast.LENGTH_LONG).show();

            }
        });

    }


}
