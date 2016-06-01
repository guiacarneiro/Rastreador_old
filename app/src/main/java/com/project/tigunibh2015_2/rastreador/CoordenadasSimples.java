package com.project.tigunibh2015_2.rastreador;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Jonatas on 26/09/2015.
 */
public class CoordenadasSimples {
    private double latitude;
    private double longitude;
    private  Date horaAtual;

    public void SetTudo (Coordenadas C){
        this.latitude = C.getLatitude();
        this.longitude = C.getLatitude();
        this.horaAtual = C.getHoraAtual();
    }

    public void SetHoraAtual (){
        Calendar calendar = new GregorianCalendar();
        this.horaAtual = calendar.getTime();
    }


    public Date getHoraAtual() {
        return this.horaAtual;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
