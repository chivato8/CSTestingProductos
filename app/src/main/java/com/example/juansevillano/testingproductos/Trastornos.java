package com.example.juansevillano.testingproductos;

/**
 * Created by Juan Sevillano on 03/04/2017.
 */

public class Trastornos {

    public static int id_trastorno;
    public static String trastorno;

    Trastornos()
    {
    }

    Trastornos(int id_trastorno, String trastorno)
    {
        this.id_trastorno=id_trastorno;
        this.trastorno=trastorno;
    }

    public int getid_trastorno()
    {
        return id_trastorno;
    }

    public void setid_trastorno(int id_transtorno)
    {
        this.id_trastorno=id_transtorno;
    }

    public String gettrastorno()
    {
        return trastorno;
    }

    public void settrastorno(String transtorno)
    {
        this.trastorno=transtorno;
    }

}
