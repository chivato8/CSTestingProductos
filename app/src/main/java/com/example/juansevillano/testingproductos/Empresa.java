package com.example.juansevillano.testingproductos;

/**
 * Created by Juan Sevillano on 01/06/2017.
 */
public class Empresa {

    private String id;
    private String empresa;

    public Empresa(){}

    public Empresa(String id, String empresa){
        this.setId(id);
        this.setEmpresa(empresa);
    }


    public String getId()
    {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmpresa()
    {
        return empresa;
    }

    public void setEmpresa(String empresa)
    {
        this.empresa = empresa;
    }
}
