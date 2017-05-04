package com.example.juansevillano.testingproductos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class EditarUsuario extends Fragment{

    //Definimos una variable de tipo SQLiteDatabase
    SQLiteDatabase db;

    //ID Usuario Elegido.
    String elegido;

    //Definimos una Variable de tipo Cursor
    public Cursor res;

    public EditarUsuario() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_editar_usuario, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Abrimos la Base de datos "BDUsuario" en modo escritura.
        BDUsuario Usuario=new BDUsuario(getActivity(),"BDUsuario",null,1);

        //Ponemos la Base de datos en Modo Escritura.
        db= Usuario.getWritableDatabase();

        // Obtener el control de la TabAFm
        System.out.println("PRUEBA - 1");

        final VentanaEditarUsuario activity = ((VentanaEditarUsuario) getActivity());

        //VentanaEditarUsuario activity = new VentanaEditarUsuario();

        //Obtenemos el id_usuario que hemos elegido
        String[] elusuario = new String[] {activity.getMyData()};

        System.out.println(elusuario[0]);

        System.out.println("PRUEBA - 2");

        //Realizamos una consulta, en la que buscamos el usaurio elegido.
        res=db.rawQuery("SELECT Nombre, Apellidos, Telefono, Correo_Electronico FROM Usuarios WHERE ID=?",elusuario);

        res.moveToFirst();

        System.out.println("PRUEBA - 3 ");

        // Se une FragmentActivity
        final VentanaEditarUsuario activity2 = ((VentanaEditarUsuario) getActivity());

        // Obtener el control de los EditText de los Fragment
        EditText nom = (EditText)activity2.fragments.get(0).getView().findViewById(R.id.nombreE);
        EditText apel = (EditText)activity2.fragments.get(0).getView().findViewById(R.id.apellidosE);
        EditText cor = (EditText)activity2.fragments.get(0).getView().findViewById(R.id.correoE);
        EditText tel = (EditText)activity2.fragments.get(0).getView().findViewById(R.id.telefonoE);

        //Realizamos una Pruea que mostramos para comprobar el valor que obtenemos.
        String nombre= res.getString(0);
        System.out.println(nombre);

        //Modificamos el valor que tiene el EditText
        nom.setText(res.getString(0));
        apel.setText(res.getString(1));
        tel.setText(res.getString(2));
        cor.setText(res.getString(3));

        System.out.println("PRUEBA - 4");

        db.close();

        /*

        */
    }

}
