package com.example.juansevillano.testingproductos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by Juan Sevillano on 30/03/2017.
 */

public class Usuario extends AppCompatActivity{

    public String Columna1="Nombre";
    public String Columna2="Apellidos";
    public String Columna3="Telefono";
    public String Columna4="Correo_electronico";

    public String Nombre;
    public String Apellidos;
    public String Telefono;
    public String Correo_electronico;

    /**
     * @name private void  Usuario
     * @description Constructor Vacio de la Clase Usuario
     * @return void
     */
    Usuario()
    {

    }

    /**
     * @name private void  Usuario
     * @description Constructor Vacio de la Clase Usuario
     * @return void
     */
    Usuario(String Nombre, String Apellidos, String Telefono, String Correo_electronico)
    {
        this.Nombre=Nombre;
        this.Apellidos=Apellidos;
        this.Telefono=Telefono;
        this.Correo_electronico=Correo_electronico;
    }

    public String getNombre()
    {
        return Nombre;
    }

    public void setNombre(String Nombre)
    {
        this.Nombre=Nombre;
    }

    public String getApellidos()
    {
        return Apellidos;
    }

    public void setApellidos(String Apellidos)
    {
        this.Apellidos=Apellidos;
    }

    public String getTelefono()
    {
        return Telefono;
    }

    public void setTelefono(String Telefono)
    {
        this.Telefono=Telefono;
    }

    public String getCorreo_electronico()
    {
        return Correo_electronico;
    }

    public void setCorreo_electronico(String Correo_electronico)
    {
        this.Correo_electronico=Correo_electronico;
    }

    /**
     * Insertamos una fila en una Base de datos.
     *
     * @return void
     */
    public void InsertarUsuario(Usuario usuario)
    {
        //Abrimos la Base de datos "BDUsuario" en modo escritura.
        BDUsuario BDusuario=new BDUsuario(this,"Usuario",null,1);
        SQLiteDatabase db = BDusuario.getWritableDatabase();

       String NameTable="Usuarios";

        ContentValues contentValues = new ContentValues();
        contentValues.put(Columna1,usuario.Nombre);
        contentValues.put(Columna2,usuario.Apellidos);
        contentValues.put(Columna3,usuario.Telefono);
        contentValues.put(Columna4,usuario.Correo_electronico);
        db.insert(NameTable,null,contentValues);
        //db.close();
    }

    public Cursor MostrarUsuarios()
    {
        //Abrimos la Base de datos "BDUsuario" en modo escritura.
        BDUsuario dbUsuarios=new BDUsuario(this,"BDUsuario",null,1);

        SQLiteDatabase db = dbUsuarios.getWritableDatabase();
        Cursor res=db.rawQuery("SELECT Nombre FROM Usuarios",null);
        //db.close();
        return res;
    }

    public Cursor BuscarUsuario(int id)
    {
        //Abrimos la Base de datos "BDUsuario" en modo escritura.
        BDUsuario Usuarios=new BDUsuario(this,"BDUsuario",null,1);

        SQLiteDatabase db = Usuarios.getWritableDatabase();
        Cursor res=db.rawQuery("SELECT Nombre, Apelldios FROM Usuarios Where ID=="+id,null);
        //db.close();
        return res;
    }
}
