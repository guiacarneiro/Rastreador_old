package com.project.tigunibh2015_2.rastreador;

import java.util.ArrayList;

/**
 * Created by Jonatas on 26/09/2015.
 */
public class ListaDeCoordenadas {
    private static ArrayList<Coordenadas> lista;

    protected void InserirCoordenada (Coordenadas C){
        ListaDeCoordenadas.lista.add(C);
    }

    protected ArrayList<Coordenadas> PegarLista(){
        return ListaDeCoordenadas.lista;
    }
}
