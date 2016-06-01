package com.project.tigunibh2015_2.rastreador.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.tigunibh2015_2.rastreador.Modelos.Dependente;
import com.project.tigunibh2015_2.rastreador.R;

import java.util.ArrayList;

/**
 * Created by Jonatas on 23/04/2016.
 */
public class DependenteAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Dependente> lista;

    public DependenteAdapter (Context context, ArrayList<Dependente> lista){
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Dependente dependente = lista.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate (R.layout.item_lista_rastreador, null);

        TextView cabecalho = (TextView) layout.findViewById(R.id.textView2);
        cabecalho.setText("Dependente");

        TextView nome = (TextView) layout.findViewById(R.id.editText5);
        nome.setText(dependente.getNome());

        return layout;
    }
}
