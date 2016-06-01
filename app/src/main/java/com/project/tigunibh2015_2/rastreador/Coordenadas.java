package com.project.tigunibh2015_2.rastreador;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * Created by Jonatas on 19/09/2015.
 */
public class Coordenadas {
    private static double latitude;
    private static double longitude;
    private static Date horaAtual;


    public Coordenadas (){
    }

    public void SetHoraAtual (){
        Calendar calendar = new GregorianCalendar();
        this.horaAtual = calendar.getTime();
    }


    public Date getHoraAtual() {
        return Coordenadas.horaAtual;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        Coordenadas.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        Coordenadas.longitude = longitude;
    }

}
