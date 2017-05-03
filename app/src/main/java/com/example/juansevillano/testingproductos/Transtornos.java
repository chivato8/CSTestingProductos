package com.example.juansevillano.testingproductos;

import java.util.ArrayList;

/**
 * Created by Juan Sevillano on 03/04/2017.
 */

public class Transtornos {

    public static int id_transtorno;
    public static String transtorno;

    Transtornos()
    {
    }

    Transtornos(int id_transtorno, String transtorno)
    {
        this.id_transtorno=id_transtorno;
        this.transtorno=transtorno;
    }

    public int getid_transtorno()
    {
        return id_transtorno;
    }

    public void setid_transtorno(int id_transtorno)
    {
        this.id_transtorno=id_transtorno;
    }

    public String gettranstorno()
    {
        return transtorno;
    }

    public void settranstorno(String transtorno)
    {
        this.transtorno=transtorno;
    }

}
