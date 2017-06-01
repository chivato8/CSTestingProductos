package com.example.juansevillano.testingproductos;

/**
 * Created by Juan Sevillano on 01/06/2017.
 */
public class TipoProducto {

    private String id;
    private String tipo;

    public TipoProducto(){}

    public TipoProducto(String id, String tipo){
        this.setId(id);
        this.setTipo(tipo);
    }


    public String getId()
    {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo()
    {
        return tipo;
    }

    public void setTipo(String tipo)
    {
        this.tipo = tipo;
    }
}
