package com.example.juansevillano.testingproductos;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.List;

import static android.content.ContentValues.TAG;

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
        //System.out.println("PRUEBA - 1");

        final VentanaEditarUsuario activity = ((VentanaEditarUsuario) getActivity());

        //VentanaEditarUsuario activity = new VentanaEditarUsuario();

        //Obtenemos el id_usuario que hemos elegido
        String[] elusuario = new String[] {activity.getMyData()};

        //System.out.println(elusuario[0]);

        //System.out.println("PRUEBA - 2");

        //Realizamos una consulta, en la que buscamos el usaurio elegido.
        res=db.rawQuery("SELECT Nombre, Apellidos, Telefono, Correo_Electronico FROM Usuarios WHERE ID=?",elusuario);

        res.moveToFirst();

        //System.out.println("PRUEBA - 3 ");

        // Se une FragmentActivity
        final VentanaEditarUsuario activity2 = ((VentanaEditarUsuario) getActivity());

        // Obtener el control de los EditText de los Fragment
        EditText nom = (EditText)activity2.fragments.get(0).getView().findViewById(R.id.nombreU);
        EditText apel = (EditText)activity2.fragments.get(0).getView().findViewById(R.id.apellidos);
        EditText cor = (EditText)activity2.fragments.get(0).getView().findViewById(R.id.correo);
        EditText tel = (EditText)activity2.fragments.get(0).getView().findViewById(R.id.telefono);

        //Realizamos una Pruea que mostramos para comprobar el valor que obtenemos.
        // String nombre= res.getString(0);
        //System.out.println(nombre);

        //Modificamos el valor que tiene el EditText
        nom.setText(res.getString(0));
        apel.setText(res.getString(1));
        tel.setText(res.getString(2));
        cor.setText(res.getString(3));

        //System.out.println("PRUEBA - 4");

        db.close();

        System.out.println(comprobarActivityALaVista(getActivity(),"com.example.juansevillano.testingproductos.VentanaEditarUsuario"));

    }


    public boolean comprobarActivityALaVista(Context context, String nombreClase){

            // Obtenemos nuestro manejador de activitys
            ActivityManager am = (ActivityManager)
                    context.getSystemService(Context.ACTIVITY_SERVICE);
            // obtenemos la informacion de la tarea que se esta ejecutando
            // actualmente
            List< ActivityManager.RunningTaskInfo > taskInfo = am.getRunningTasks(1);
            // Creamos una variable donde vamos a almacenar
            // la activity que se encuentra a la vista
            String nombreClaseActual = null;

            try{
                // Creamos la variable donde vamos a guardar el objeto
                // del que vamos a tomar el nombre
                ComponentName componentName = null;
                // si pudimos obtener la tarea actual, vamos a intentar cargar
                // nuestro objeto
                if(taskInfo != null && taskInfo.get(0) != null){
                    componentName = taskInfo.get(0).topActivity;
                }
                // Si pudimos cargar nuestro objeto, vamos a obtener
                // el nombre con el que vamos a comparar
                if(componentName != null){
                    nombreClaseActual = componentName.getClassName();
                    System.out.println(nombreClaseActual);
                }

            }catch (NullPointerException e){

                Log.e(TAG, "Error al tomar el nombre de la clase actual " + e);
                return false;
            }

            // devolvemos el resultado de la comparacion
            return nombreClase.equals(nombreClaseActual);
    }

}
