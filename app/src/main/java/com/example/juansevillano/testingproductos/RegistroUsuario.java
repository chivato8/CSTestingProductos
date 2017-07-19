package com.example.juansevillano.testingproductos;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import static android.content.ContentValues.TAG;

public class RegistroUsuario extends Fragment{

    //Definimos una variable de tipo SQLiteDatabase
    SQLiteDatabase db;

    //ID Usuario Elegido.
    String elegido;

    //Definimos una Variable de tipo Cursor
    public Cursor res;

    int pos=0;

    /**
     * @name public RegistroUsuario()
     * @description Constructor Vacio
     * @return void
     */
    public RegistroUsuario() {
        // Required empty public constructor
    }

    /**
     * @name private void onCreate( Bundle savedInstanceState)
     * @description Primer Método que se llama al crear la clase
     * @return void
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    /**
     * @name private void onResume()
     * @description Primer Método cuando la función se está yendo de la pantalla. Muestra en que proceso nos encontramos.
     * @return void
     */
    public void onResume()
    {

        //Si estamo en la ventana VentanaRegistroUsuario
        if (comprobarActivityALaVista(getActivity(), "com.example.juansevillano.testingproductos.VentanaRegistroUsuario") == true) {
            // Definimos un objeto del activity VentanaRegistroUsuario
            final VentanaRegistroUsuario activity= ((VentanaRegistroUsuario) getActivity());
            ViewPager viewPager= activity.viewPager;
            System.out.println(viewPager.getCurrentItem());
            if(viewPager.getCurrentItem() == pos){
                pos++;
                Toast.makeText(getActivity(), "Proceso "+pos+"/16", Toast.LENGTH_SHORT).show();
                pos--;
                //Your code here. Executed when fragment is seen by user.
            }
        }
        else
        {
            //Si estamos en la ventana VentanaRegistroProducto
            if (comprobarActivityALaVista(getActivity(), "com.example.juansevillano.testingproductos.VentanaRegistroProducto") == true) {
                // Definimos un objeto del activity VentanaRegistroProducto
                final VentanaRegistroProducto activity= ((VentanaRegistroProducto) getActivity());
                ViewPager viewPager= activity.viewPager;
                if(viewPager.getCurrentItem() == pos){
                    pos++;
                    Toast.makeText(getActivity(), "Proceso "+pos+"/16", Toast.LENGTH_SHORT).show();
                    pos--;
                    //Your code here. Executed when fragment is seen by user.
                }
            }
            else
            {
                //Si estamos en la ventana VentanaEditarUsuarioAdmin
                if (comprobarActivityALaVista(getActivity(), "com.example.juansevillano.testingproductos.VentanaEditarUsuarioAdmin") == true) {
                    // Definimos un objeto del activity VentanaEditarUsuarioAdmin
                    final VentanaEditarUsuarioAdmin activity= ((VentanaEditarUsuarioAdmin) getActivity());
                    ViewPager viewPager= activity.viewPager;
                    if(viewPager.getCurrentItem() == pos){
                        pos++;
                        Toast.makeText(getActivity(), "Proceso "+pos+"/16", Toast.LENGTH_SHORT).show();
                        pos--;
                        //Your code here. Executed when fragment is seen by user.
                    }
                }
                else
                {
                    //Si estamos en la ventana VentanaEditarUsuario
                    if (comprobarActivityALaVista(getActivity(), "com.example.juansevillano.testingproductos.VentanaEditarUsuario") == true) {
                        // Definimos un objeto del activity VentanaEditarUsuario
                        final VentanaEditarUsuario activity= ((VentanaEditarUsuario) getActivity());
                        ViewPager viewPager= activity.viewPager;
                        if(viewPager.getCurrentItem() == pos){
                            pos++;
                            Toast.makeText(getActivity(), "Proceso "+pos+"/16", Toast.LENGTH_SHORT).show();
                            pos--;
                            //Your code here. Executed when fragment is seen by user.
                        }
                    }
                    else
                    {
                        //Si estamos en la ventana VentanaActualizarProducto
                        if (comprobarActivityALaVista(getActivity(), "com.example.juansevillano.testingproductos.VentanaActualizarProducto") == true) {
                            // Definimos un objeto del activity VentanaActualizarProducto
                            final VentanaActualizarProducto activity= ((VentanaActualizarProducto) getActivity());
                            ViewPager viewPager= activity.viewPager;
                            if(viewPager.getCurrentItem() == pos){
                                pos++;
                                Toast.makeText(getActivity(), "Proceso "+pos+"/16", Toast.LENGTH_SHORT).show();
                                pos--;
                                //Your code here. Executed when fragment is seen by user.
                            }
                        }
                    }
                }
            }
        }

        super.onResume();
    }

    /**
     * @name private View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
     * @description Se crea la vista de la clase
     * @return View v
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_registro_usuario, container, false);
    }

    /**
     * @name private void onActivityCreated(Bundle savedInstanceState)
     * @description Funcion para crear la Actividad
     * @return void
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(comprobarActivityALaVista(getActivity(),"com.example.juansevillano.testingproductos.VentanaEditarUsuario")==true)
        {
            //Abrimos la Base de datos "BDUsuario" en modo escritura.
            BDUsuario Usuario=new BDUsuario(getActivity(),"BDUsuario",null,1);

            //Ponemos la Base de datos en Modo Escritura.
            db= Usuario.getWritableDatabase();

            // Obtener el control de la TabAFm

            final VentanaEditarUsuario activity = ((VentanaEditarUsuario) getActivity());

            //VentanaEditarUsuario activity = new VentanaEditarUsuario();

            //Obtenemos el id_usuario que hemos elegido
            String[] elusuario = new String[] {activity.getMyData()};

            //Realizamos una consulta, en la que buscamos el usaurio elegido.
            res=db.rawQuery("SELECT Nombre, Apellidos, Telefono, Correo_Electronico FROM Usuarios WHERE ID=?",elusuario);

            //Movemos el cursor al primer elemento
            res.moveToFirst();

            // Se une FragmentActivity
            final VentanaEditarUsuario activity2 = ((VentanaEditarUsuario) getActivity());

            // Obtener el control de los EditText de los Fragment
            EditText nom = (EditText)activity2.fragments.get(0).getView().findViewById(R.id.nombreU);
            EditText apel = (EditText)activity2.fragments.get(0).getView().findViewById(R.id.apellidos);
            EditText cor = (EditText)activity2.fragments.get(0).getView().findViewById(R.id.correo);
            EditText tel = (EditText)activity2.fragments.get(0).getView().findViewById(R.id.telefono);

            //Realizamos una Pruea que mostramos para comprobar el valor que obtenemos.
            String nombre= res.getString(0);
            System.out.println(nombre);

            //Modificamos el valor que tiene el EditText
            nom.setText(res.getString(0));
            apel.setText(res.getString(1));
            tel.setText(res.getString(2));
            cor.setText(res.getString(3));


            db.close();
        }

    }

    /**
     * @name public boolean comprobarActivityALaVista(Context context, String nombreClase)
     * @description Metodo para comprobar en que activity nos encontramos actualmente
     * @return boolean
     */
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
            }

        }catch (NullPointerException e){

            Log.e(TAG, "Error al tomar el nombre de la clase actual " + e);
            return false;
        }

        // devolvemos el resultado de la comparacion
        return nombreClase.equals(nombreClaseActual);
    }
}
