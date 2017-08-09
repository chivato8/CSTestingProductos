package com.example.juansevillano.testingproductos;

/**
 * Created by Juan Sevillano on 03/04/2017.
 */

public class Ingrediente {

    private String id_ingrediente;
    private String ingrediente;
    private String tipo_ingrediente;
    private boolean estado;
    private String ruta_imagen;

    Ingrediente()
    {
        this.id_ingrediente="";
        this.ingrediente="";
        this.ruta_imagen="";
    }

    Ingrediente(String id_ingrediente, String ingrediente, String tipo_ingrediente, String ruta_imagen)
    {
        this.id_ingrediente=id_ingrediente;
        this.ingrediente=ingrediente;
        this.tipo_ingrediente=tipo_ingrediente;
        this.ruta_imagen=ruta_imagen;
    }

    Ingrediente(String id_ingrediente,String ingrediente, boolean estado)
    {
        this.id_ingrediente=id_ingrediente;
        this.ingrediente=ingrediente;
        this.estado=estado;
    }

    //DE PRUEBA DESPUES ELIMINAR
    Ingrediente(String ingrediente, boolean estado)
    {
        this.ingrediente=ingrediente;
        this.estado=estado;
    }
    //DE PRUEBA DESPUES ELIMINAR

    public String getid_ingrediente(){return id_ingrediente;}

    public void setid_ingrediente(String id_ingrediente){this.id_ingrediente=id_ingrediente;}

    public String getingrediente()
    {
        return ingrediente;
    }

    public void setingrediente(String ingrediente)
    {
        this.ingrediente=ingrediente;
    }

    public String gettipo_ingrediente(){return tipo_ingrediente;}

    public void settipo_ingrediente(String tipo_ingrediente){this.tipo_ingrediente=tipo_ingrediente;}

    public String getruta_imagen(){return ruta_imagen;}

    public void setruta_imagen(String ruta_imagen){this.ruta_imagen=ruta_imagen;}

    public boolean isChekeado() {
        return estado;
    }

    public void setChekeado(boolean estado)
    {
        this.estado = estado;
    }

}
