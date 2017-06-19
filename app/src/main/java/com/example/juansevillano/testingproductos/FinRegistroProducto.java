package com.example.juansevillano.testingproductos;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.content.ContentValues.TAG;


public class FinRegistroProducto extends Fragment {

    //Obtenemos el contenido del edittext del codigo de barra
    private EditText codbarra;
    private String codigobarra;
    //Obtenemos el contenido del edittext del nombre del producto
    private EditText nomproducto;
    private String nombreproducto;
    //Obtenemos el contenido del spinner seleccionado tipo producto
    //Spinner para el tipo de productos
    private Spinner spinnertipo;
    private String tipoproducto;
    //Obtenemos el contenido del spinner seleccionado empresa que lo fabrica
    //Spinner para la empresa que fabrico el producto
    private Spinner spinnerempresa;
    private String empresafabrica;

    CheckBox lMarcado;
    ListView lstLista;

    //Definimos una variable de tipo SQLiteDatabase
    //SQLiteDatabase dbUsuario;
    //SQLiteDatabase dbIngrediente;

    //Creamos un Objeto de Tipo Ingrediente
    Ingrediente ingrediente;

    RegistroUsuario registroUsuario= new RegistroUsuario();

    public FinRegistroProducto() {
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
        return inflater.inflate(R.layout.activity_fin_registro_usuario, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button bt1 = (Button)getView().findViewById(R.id.crear);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.crear:
                        // Definimos un objeto del activity VentanaEditarUsuario
                        final VentanaRegistroProducto activity = ((VentanaRegistroProducto) getActivity());

                        //Obtenemos el contenido del edittext del nombre del usuario
                        codbarra = (EditText) activity.fragments.get(0).getView().findViewById(R.id.cbarra);
                        codigobarra = codbarra.getText().toString();
                        //Obtenemos el contenido del edittext del apellidos del usuario
                        nomproducto = (EditText) activity.fragments.get(0).getView().findViewById(R.id.nomproducto);
                        nombreproducto = nomproducto.getText().toString();
                        //Obtenemos el contenido del spinner del tipo de producto
                        spinnertipo = (Spinner) activity.fragments.get(0).getView().findViewById(R.id.spinnertipoproducto);
                        tipoproducto= String.valueOf(spinnertipo.getSelectedItemPosition()+1);
                        //Obtenemos el contenido del spinner del nombre de la empresa
                        spinnerempresa = (Spinner) activity.fragments.get(0).getView().findViewById(R.id.spinnerempresa);
                        empresafabrica = String.valueOf(spinnerempresa.getSelectedItemPosition()+1);

                        //Comprobamos si el edittext nombre no es vacio y si el edittext apellidos no es vacio para poder introducir
                        // el usuario en la base de datos Usuario
                        /*System.out.println("Id: "+ingrediente.getid_ingrediente()+" - nombre: "+ingrediente.getingrediente()
                        +" - estado: "+ingrediente.isChekeado());*/
                        if (!codbarra.equals("") && !nombreproducto.equals(""))
                        {
                        /*
                            //Insertamos Producto e Relaciones establecidad en la Base de Datos


                            //Recorremos el fragment asignado para el alergenico altamuz
                            for(Ingrediente ingrediente : ((VentanaEditarUsuario)getActivity()).list_ingredientes_altamuz){

                                if (ingrediente.isChekeado())
                                {
                                    String id = ingrediente.getid_ingrediente();
                                    System.out.println(id);
                                    insertar_idusuario_idingrediente(elusuario[0].toString(), id.toString());
                                }
                            }

                            //Recorremos el fragment asignado para el alergenico apio
                            for(Ingrediente ingrediente : ((VentanaEditarUsuario)getActivity()).list_ingredientes_apio){
                                if (ingrediente.isChekeado())
                                {
                                    String id = ingrediente.getid_ingrediente();
                                    System.out.println(id);
                                    insertar_idusuario_idingrediente(elusuario[0].toString(), id.toString());
                                }
                            }

                            //Recorremos el fragment asignado para el alergenico azufre y sulfitos
                            for(Ingrediente ingrediente : ((VentanaEditarUsuario)getActivity()).list_ingredientes_azufreysulfito){
                                if (ingrediente.isChekeado())
                                {
                                    String id = ingrediente.getid_ingrediente();
                                    System.out.println(id);
                                    insertar_idusuario_idingrediente(elusuario[0].toString(), id.toString());
                                }
                            }

                            //Recorremos el fragment asignado para el alergenico cacahuete
                            for(Ingrediente ingrediente : ((VentanaEditarUsuario)getActivity()).list_ingredientes_cacahuete){
                                if (ingrediente.isChekeado())
                                {
                                    String id = ingrediente.getid_ingrediente();
                                    System.out.println(id);
                                    insertar_idusuario_idingrediente(elusuario[0].toString(), id.toString());
                                }
                            }

                            //Recorremos el fragment asignado para el alergenico crustacios
                            for(Ingrediente ingrediente : ((VentanaEditarUsuario)getActivity()).list_ingredientes_crustaceo){
                                if (ingrediente.isChekeado())
                                {
                                    String id = ingrediente.getid_ingrediente();
                                    System.out.println(id);
                                    insertar_idusuario_idingrediente(elusuario[0].toString(), id.toString());
                                }
                            }

                            //Recorremos el fragment asignado para el alergenico frutos con cascara
                            for(Ingrediente ingrediente : ((VentanaEditarUsuario)getActivity()).list_ingredientes_frutoscascara){
                                if (ingrediente.isChekeado())
                                {
                                    String id = ingrediente.getid_ingrediente();
                                    System.out.println(id);
                                    insertar_idusuario_idingrediente(elusuario[0].toString(), id.toString());
                                }
                            }

                            //Recorremos el fragment asignado para el alergenico gluten
                            for(Ingrediente ingrediente : ((VentanaEditarUsuario)getActivity()).list_ingredientes_gluten){
                                if (ingrediente.isChekeado())
                                {
                                    String id = ingrediente.getid_ingrediente();
                                    System.out.println(id);
                                    insertar_idusuario_idingrediente(elusuario[0].toString(), id.toString());
                                }
                            }

                            //Recorremos el fragment asignado para el alergenico sesamo
                            for(Ingrediente ingrediente : ((VentanaEditarUsuario)getActivity()).list_ingredientes_sesamo){
                                if (ingrediente.isChekeado())
                                {
                                    String id = ingrediente.getid_ingrediente();
                                    System.out.println(id);
                                    insertar_idusuario_idingrediente(elusuario[0].toString(), id.toString());
                                }
                            }

                            //Recorremos el fragment asignado para el alergenico huevo
                            for(Ingrediente ingrediente : ((VentanaEditarUsuario)getActivity()).list_ingredientes_huevo){
                                if (ingrediente.isChekeado())
                                {
                                    String id = ingrediente.getid_ingrediente();
                                    System.out.println(id);
                                    insertar_idusuario_idingrediente(elusuario[0].toString(), id.toString());
                                }
                            }

                            //Recorremos el fragment asignado para el alergenico lacteos
                            for(Ingrediente ingrediente : ((VentanaEditarUsuario)getActivity()).list_ingredientes_lacteo){
                                if (ingrediente.isChekeado())
                                {
                                    String id = ingrediente.getid_ingrediente();
                                    System.out.println(id);
                                    insertar_idusuario_idingrediente(elusuario[0].toString(), id.toString());
                                }
                            }

                            //Recorremos el fragment asignado para el alergenico molusco
                            for(Ingrediente ingrediente : ((VentanaEditarUsuario)getActivity()).list_ingredientes_molusco){
                                if (ingrediente.isChekeado())
                                {
                                    String id = ingrediente.getid_ingrediente();
                                    System.out.println(id);
                                    insertar_idusuario_idingrediente(elusuario[0].toString(), id.toString());
                                }
                            }

                            //Recorremos el fragment asignado para el alergenico mostaza
                            for(Ingrediente ingrediente : ((VentanaEditarUsuario)getActivity()).list_ingredientes_mostaza){
                                if (ingrediente.isChekeado())
                                {
                                    String id = ingrediente.getid_ingrediente();
                                    System.out.println(id);
                                    insertar_idusuario_idingrediente(elusuario[0].toString(), id.toString());
                                }
                            }

                            //Recorremos el fragment asignado para el alergenico pescado
                            for(Ingrediente ingrediente : ((VentanaEditarUsuario)getActivity()).list_ingredientes_pescado){
                                if (ingrediente.isChekeado())
                                {
                                    String id = ingrediente.getid_ingrediente();
                                    System.out.println(id);
                                    insertar_idusuario_idingrediente(elusuario[0].toString(), id.toString());
                                }
                            }

                            //Recorremos el fragment asignado para el alergenico soja
                            for(Ingrediente ingrediente : ((VentanaEditarUsuario)getActivity()).list_ingredientes_soja){
                                if (ingrediente.isChekeado())
                                {
                                    String id = ingrediente.getid_ingrediente();
                                    System.out.println(id);
                                    insertar_idusuario_idingrediente(elusuario[0].toString(), id.toString());
                                }
                            }

                            Toast.makeText(getActivity().getBaseContext(), "!Usuario Modificado Correctamente¡", Toast.LENGTH_LONG).show();

                            //Toast.makeText(activity,nombre,Toast.LENGTH_SHORT).show();

                            //Esperamos 50 milisegundos
                            SystemClock.sleep(500);

                            //getView().setId(R.id.restablecer);

                            //Redireccionamos la aplicacion a la venana principal
                            Intent intent = new Intent(getActivity(), VentanaOpcionesEscaner.class);
                            startActivity(intent);
                            getActivity().finish();
                            */
                        }
                        //Si el edittext nombre y el edittex apellidos son vacios mostrarmos un mensaje u notificación advirtiendo
                        //que son campos obligatorios
                        else {
                            LayoutInflater inflater = getActivity().getLayoutInflater();
                            View layout = inflater.inflate(R.layout.contenidotoast, (ViewGroup) getView().findViewById(R.id.toast_layout_root));

                            TextView txtMsg = (TextView) layout.findViewById(R.id.text_toast);
                            txtMsg.setText("Campos Obligatorios:");

                            if (codigobarra.equals("")) {
                                txtMsg.setText(txtMsg.getText().toString() + "\n- Codigo de Barra");
                            }

                            if (nombreproducto.equals("")) {
                                txtMsg.setText(txtMsg.getText().toString() + "\n- Nombre del Producto");
                            }
                            txtMsg.setText(txtMsg.getText().toString() + "\n- Tipo Producto: "+tipoproducto.toString());
                            txtMsg.setText(txtMsg.getText().toString() + "\n- Empresa Fabrica: "+empresafabrica.toString());

                            int duration = Toast.LENGTH_LONG;

                            Toast toast = new Toast(getActivity().getApplicationContext());
                            toast.setDuration(duration);
                            toast.setView(layout);
                            toast.show();

                        }
                }

            }
        });

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
