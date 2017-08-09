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
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.content.ContentValues.TAG;


public class FinRegistroUsuario extends Fragment {

    //Obtenemos el contenido del edittext del nombre del usuario
    EditText nom;
    String nombre;
    //Obtenemos el contenido del edittext del apellidos del usuario
    EditText apel;
    String apellidos;
    //Obtenemos el contenido del edittext del correo del usuario
    EditText cor;
    String correo;
    //Obtenemos el contenido del edittext del telefono del usuario
    EditText tel;
    String telefono;

    CheckBox lMarcado;
    ListView lstLista;

    //Definimos una variable de tipo SQLiteDatabase
    SQLiteDatabase dbUsuario;
    SQLiteDatabase dbIngrediente;

    //Creamos un Objeto de Tipo Ingrediente
    Ingrediente ingrediente;

    RegistroUsuario registroUsuario= new RegistroUsuario();

    int pos=15;

    /**
     * @name public FinRegistroUsuario()
     * @description Constructor Vacio
     * @return void
     */
    public FinRegistroUsuario() {
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
     * @name private View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
     * @description Se crea la vista de la clase
     * @return View v
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_fin_registro_usuario, container, false);
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
     * @name private void onActivityCreated(Bundle savedInstanceState)
     * @description Funcion para crear la Actividad
     * @return void
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button bt1 = (Button)getView().findViewById(R.id.crear);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.crear:
                        if (comprobarActivityALaVista(getActivity(), "com.example.juansevillano.testingproductos.VentanaEditarUsuario") == true) {
                            // Definimos un objeto del activity VentanaEditarUsuario
                            final VentanaEditarUsuario activity = ((VentanaEditarUsuario) getActivity());

                            //Obtenemos el contenido del edittext del nombre del usuario
                            nom = (EditText) activity.fragments.get(0).getView().findViewById(R.id.nombreU);
                            nombre = nom.getText().toString();
                            //Obtenemos el contenido del edittext del apellidos del usuario
                            apel = (EditText) activity.fragments.get(0).getView().findViewById(R.id.apellidos);
                            apellidos = apel.getText().toString();
                            //Obtenemos el contenido del edittext del correo del usuario
                            cor = (EditText) activity.fragments.get(0).getView().findViewById(R.id.correo);
                            correo = cor.getText().toString();
                            //Obtenemos el contenido del edittext del telefono del usuario
                            tel = (EditText) activity.fragments.get(0).getView().findViewById(R.id.telefono);
                            telefono = tel.getText().toString();

                            //Comprobamos si el edittext nombre no es vacio y si el edittext apellidos no es vacio para poder introducir
                            // el usuario en la base de datos Usuario
                            if (!nombre.equals("") && !apellidos.equals("")) {

                                //Obtenemos el id_usuario que hemos elegido
                                String[] elusuario = new String[]{activity.getMyData()};

                                //Llamamos a la funcion actualizar usuario.
                                actualizarUsuario(elusuario[0], nombre, apellidos, correo, telefono);

                                //Llamamos a la funcion eliminar todas las relaciones de usuario con ingrediente del usuario que estamos
                                //editando.
                                eliminar_idusuario_idingrediente(elusuario[0]);

                                //Recorremos el fragment asignado para el alergenico altamuz
                                for(Ingrediente ingrediente : ((VentanaEditarUsuario)getActivity()).list_ingredientes_altramuz){
                                    /*System.out.println("Id: "+ingrediente.getid_ingrediente()+" - nombre: "+ingrediente.getingrediente()
                                            +" - estado: "+ingrediente.isChekeado());*/
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
                            }
                            //Si el edittext nombre y el edittex apellidos son vacios mostrarmos un mensaje u notificación advirtiendo
                            //que son campos obligatorios
                            else {
                                LayoutInflater inflater = getActivity().getLayoutInflater();
                                View layout = inflater.inflate(R.layout.contenidotoast, (ViewGroup) getView().findViewById(R.id.toast_layout_root));

                                TextView txtMsg = (TextView) layout.findViewById(R.id.text_toast);
                                txtMsg.setText("Campos Obligatorios:");

                                if (nombre.equals("")) {
                                    txtMsg.setText(txtMsg.getText().toString() + "\n- Nombre");
                                }

                                if (apellidos.equals("")) {
                                    txtMsg.setText(txtMsg.getText().toString() + "\n- Apellido");
                                }

                                int duration = Toast.LENGTH_LONG;

                                Toast toast = new Toast(getActivity().getApplicationContext());
                                toast.setDuration(duration);
                                toast.setView(layout);
                                toast.show();
                            }
                        }
                        else
                        {
                            if (comprobarActivityALaVista(getActivity(), "com.example.juansevillano.testingproductos.VentanaRegistroUsuario") == true)
                            {
                                // Definimos un objeto del activity VentanaRegistroUsuario
                                final VentanaRegistroUsuario activity = ((VentanaRegistroUsuario) getActivity());

                                //Obtenemos el contenido del edittext del nombre del usuario
                                nom = (EditText) activity.fragments.get(0).getView().findViewById(R.id.nombreU);
                                nombre = nom.getText().toString();
                                //Obtenemos el contenido del edittext del apellidos del usuario
                                apel = (EditText) activity.fragments.get(0).getView().findViewById(R.id.apellidos);
                                apellidos = apel.getText().toString();
                                //Obtenemos el contenido del edittext del correo del usuario
                                cor = (EditText) activity.fragments.get(0).getView().findViewById(R.id.correo);
                                correo = cor.getText().toString();
                                //Obtenemos el contenido del edittext del telefono del usuario
                                tel = (EditText) activity.fragments.get(0).getView().findViewById(R.id.telefono);
                                telefono = tel.getText().toString();


                                if (!nombre.equals("") && !apellidos.equals("")) {
                                    //Insertamos los datos del Usuario en la Base de Datos llamada Usuario.
                                    insertarUsuario(nombre, apellidos, telefono, correo);

                                    //Recorremos el fragment asignado para el alergenico altamuz
                                    for(Ingrediente ingrediente : ((VentanaRegistroUsuario)getActivity()).list_ingredientes_altramuz){
                                    /*System.out.println("Id: "+ingrediente.getid_ingrediente()+" - nombre: "+ingrediente.getingrediente()
                                            +" - estado: "+ingrediente.isChekeado());*/
                                        if (ingrediente.isChekeado())
                                        {
                                            String id = ingrediente.getid_ingrediente();

                                            System.out.println("Insertando: " + id);
                                            insertar_idusuario_idingrediente(id.toString());
                                        }
                                    }

                                    //Recorremos el fragment asignado para el alergenico apio
                                    for(Ingrediente ingrediente : ((VentanaRegistroUsuario)getActivity()).list_ingredientes_apio){
                                        if (ingrediente.isChekeado())
                                        {
                                            String id = ingrediente.getid_ingrediente();

                                            System.out.println("Insertando: " + id);
                                            insertar_idusuario_idingrediente(id.toString());
                                        }
                                    }

                                    //Recorremos el fragment asignado para el alergenico azufre y sulfitos
                                    for(Ingrediente ingrediente : ((VentanaRegistroUsuario)getActivity()).list_ingredientes_azufreysulfito){
                                        if (ingrediente.isChekeado())
                                        {
                                            String id = ingrediente.getid_ingrediente();

                                            System.out.println("Insertando: " + id);
                                            insertar_idusuario_idingrediente(id.toString());
                                        }
                                    }

                                    //Recorremos el fragment asignado para el alergenico cacahuete
                                    for(Ingrediente ingrediente : ((VentanaRegistroUsuario)getActivity()).list_ingredientes_cacahuete){
                                        if (ingrediente.isChekeado())
                                        {
                                            String id = ingrediente.getid_ingrediente();

                                            System.out.println("Insertando: " + id);
                                            insertar_idusuario_idingrediente(id.toString());
                                        }
                                    }

                                    //Recorremos el fragment asignado para el alergenico crustacios
                                    for(Ingrediente ingrediente : ((VentanaRegistroUsuario)getActivity()).list_ingredientes_crustaceo){
                                        if (ingrediente.isChekeado())
                                        {
                                            String id = ingrediente.getid_ingrediente();

                                            System.out.println("Insertando: " + id);
                                            insertar_idusuario_idingrediente(id.toString());
                                        }
                                    }

                                    //Recorremos el fragment asignado para el alergenico frutos con cascara
                                    for(Ingrediente ingrediente : ((VentanaRegistroUsuario)getActivity()).list_ingredientes_frutoscascara){
                                        if (ingrediente.isChekeado())
                                        {
                                            String id = ingrediente.getid_ingrediente();

                                            System.out.println("Insertando: " + id);
                                            insertar_idusuario_idingrediente(id.toString());
                                        }
                                    }

                                    //Recorremos el fragment asignado para el alergenico gluten
                                    for(Ingrediente ingrediente : ((VentanaRegistroUsuario)getActivity()).list_ingredientes_gluten){
                                        if (ingrediente.isChekeado())
                                        {
                                            String id = ingrediente.getid_ingrediente();

                                            System.out.println("Insertando: " + id);
                                            insertar_idusuario_idingrediente(id.toString());
                                        }
                                    }

                                    //Recorremos el fragment asignado para el alergenico sesamo
                                    for(Ingrediente ingrediente : ((VentanaRegistroUsuario)getActivity()).list_ingredientes_sesamo){
                                        if (ingrediente.isChekeado())
                                        {
                                            String id = ingrediente.getid_ingrediente();

                                            System.out.println("Insertando: " + id);
                                            insertar_idusuario_idingrediente(id.toString());
                                        }
                                    }

                                    //Recorremos el fragment asignado para el alergenico huevo
                                    for(Ingrediente ingrediente : ((VentanaRegistroUsuario)getActivity()).list_ingredientes_huevo){
                                        if (ingrediente.isChekeado())
                                        {
                                            String id = ingrediente.getid_ingrediente();

                                            System.out.println("Insertando: " + id);
                                            insertar_idusuario_idingrediente(id.toString());
                                        }
                                    }

                                    //Recorremos el fragment asignado para el alergenico lacteos
                                    for(Ingrediente ingrediente : ((VentanaRegistroUsuario)getActivity()).list_ingredientes_lacteo){
                                        if (ingrediente.isChekeado())
                                        {
                                            String id = ingrediente.getid_ingrediente();

                                            System.out.println("Insertando: " + id);
                                            insertar_idusuario_idingrediente(id.toString());
                                        }
                                    }

                                    //Recorremos el fragment asignado para el alergenico molusco
                                    for(Ingrediente ingrediente : ((VentanaRegistroUsuario)getActivity()).list_ingredientes_molusco){
                                        if (ingrediente.isChekeado())
                                        {
                                            String id = ingrediente.getid_ingrediente();

                                            System.out.println("Insertando: " + id);
                                            insertar_idusuario_idingrediente(id.toString());
                                        }
                                    }

                                    //Recorremos el fragment asignado para el alergenico mostaza
                                    for(Ingrediente ingrediente : ((VentanaRegistroUsuario)getActivity()).list_ingredientes_mostaza){
                                        if (ingrediente.isChekeado())
                                        {
                                            String id = ingrediente.getid_ingrediente();

                                            System.out.println("Insertando: " + id);
                                            insertar_idusuario_idingrediente(id.toString());
                                        }
                                    }

                                    //Recorremos el fragment asignado para el alergenico pescado
                                    for(Ingrediente ingrediente : ((VentanaRegistroUsuario)getActivity()).list_ingredientes_pescado){
                                        if (ingrediente.isChekeado())
                                        {
                                            String id = ingrediente.getid_ingrediente();

                                            System.out.println("Insertando: " + id);
                                            insertar_idusuario_idingrediente(id.toString());
                                        }
                                    }

                                    //Recorremos el fragment asignado para el alergenico soja
                                    for(Ingrediente ingrediente : ((VentanaRegistroUsuario)getActivity()).list_ingredientes_soja){
                                        if (ingrediente.isChekeado())
                                        {
                                            String id = ingrediente.getid_ingrediente();

                                            System.out.println("Insertando: " + id);
                                            insertar_idusuario_idingrediente(id.toString());
                                        }
                                    }

                                    Toast.makeText(getActivity().getBaseContext(), "!Usuario Insertado Correctamente¡", Toast.LENGTH_LONG).show();

                                    //Toast.makeText(activity,nombre,Toast.LENGTH_SHORT).show();

                                    //Esperamos 50 milisegundos
                                    SystemClock.sleep(500);

                                    //getView().setId(R.id.restablecer);

                                    //Redireccionamos la aplicacion a la venana principal
                                    Intent intent = new Intent(getActivity(), VentanaOpcionesEscaner.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                }
                                //Si el edittext nombre y el edittex apellidos son vacios mostrarmos un mensaje u notificación advirtiendo
                                //que son campos obligatorios
                                else {
                                    LayoutInflater inflater = getActivity().getLayoutInflater();
                                    View layout = inflater.inflate(R.layout.contenidotoast, (ViewGroup) getView().findViewById(R.id.toast_layout_root));

                                    TextView txtMsg = (TextView) layout.findViewById(R.id.text_toast);
                                    txtMsg.setText("Campos Obligatorios:");

                                    if (nombre.equals("")) {
                                        txtMsg.setText(txtMsg.getText().toString() + "\n- Nombre");
                                    }

                                    if (apellidos.equals("")) {
                                        txtMsg.setText(txtMsg.getText().toString() + "\n- Apellido");
                                    }

                                    int duration = Toast.LENGTH_LONG;

                                    Toast toast = new Toast(getActivity().getApplicationContext());
                                    toast.setDuration(duration);
                                    toast.setView(layout);
                                    toast.show();
                                }
                            }

                        }
                }

            }
        });

        Button bt2 = (Button)getView().findViewById(R.id.restablecerusuario);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (comprobarActivityALaVista(getActivity(), "com.example.juansevillano.testingproductos.VentanaRegistroUsuario") == true) {
                    // Definimos un objeto del activity VentanaEditarUsuario
                    final VentanaRegistroUsuario activity = ((VentanaRegistroUsuario) getActivity());
                    activity.Restablecer();
                }
                else
                {
                    if (comprobarActivityALaVista(getActivity(), "com.example.juansevillano.testingproductos.VentanaEditarUsuario") == true) {
                        // Definimos un objeto del activity VentanaEditarUsuario
                        final VentanaEditarUsuario activity = ((VentanaEditarUsuario) getActivity());
                        activity.Restablecer();
                    }
                }

            }
        });

    }


    /**
     * @name public void insertarUsuario(String nombre, String apellidos, String telefono, String correo)
     * @description Metodo para insertar un Usuario nuevo en la base de datos
     * @return void
     */
    public void insertarUsuario(String nombre, String apellidos, String telefono, String correo)
    {
        //Abrimos la Base de datos "BDAlumnos" en modo escritura.
        BDUsuario bdUsuario = new BDUsuario(getView().getContext(), "BDUsuario", null, 1);

        //Ponemos la Base de datos en Modo Escritura.
        dbUsuario = bdUsuario.getWritableDatabase();

        String Columna1="Nombre";
        String Columna2="Apellidos";
        String Columna3="Telefono";
        String Columna4="Correo_electronico";

        ContentValues contentValues = new ContentValues();
        contentValues.put(Columna1,nombre);
        contentValues.put(Columna2,apellidos);
        contentValues.put(Columna3,telefono);
        contentValues.put(Columna4,correo);
        dbUsuario.insert("Usuarios",null,contentValues);

        //Cerramos la conexión con la base de datos.
        dbUsuario.close();
    }

    /**
     * @name public void insertarUsuario(String nombre, String apellidos, String telefono, String correo)
     * @description Metodo para insertar un Usuario nuevo en la base de datos
     * @return void
     */
    public void actualizarUsuario(String ID, String nombre, String apellidos, String telefono, String correo)
    {
        //Abrimos la Base de datos "BDAlumnos" en modo escritura.
        BDUsuario bdUsuario = new BDUsuario(getView().getContext(), "BDUsuario", null, 1);

        //Ponemos la Base de datos en Modo Escritura.
        dbUsuario = bdUsuario.getWritableDatabase();

        //Realizamos la eliminación del usuario
        dbUsuario.execSQL("UPDATE Usuarios SET Nombre='"+nombre+"', Apellidos='"+apellidos+"', Telefono='"+telefono+"', Correo_electronico='"+correo+"' WHERE ID="+ID);

        //cerramos la conexión con la base de datos
        dbUsuario.close();
    }

    /**
     * @name public void insertar_idusuario_idingrediente(String id_ingrediente)
     * @description Metodo para ingreser id_usuario y id_ingrediente en la relación que existe entre usuario y ingrediente
     * @return void
     */
    public void insertar_idusuario_idingrediente(String id_ingrediente)
    {

        //Abrimos la Base de datos "BDUsuario" en modo escritura.
        BDUsuario Usuarios=new BDUsuario(getView().getContext(),"BDUsuario",null,1);

        //Ponemos la Base de datos en Modo Escritura.
        dbUsuario= Usuarios.getWritableDatabase();

        //Comprobamos si la Base de datos con la que estamos trabajando esta VACIA
        Cursor count=dbUsuario.rawQuery("SELECT ID FROM Usuarios WHERE ID=(SELECT MAX(ID) FROM Usuarios)",null);

        //Movemos el cursor al primer elemento
        count.moveToFirst();

        //Obtenemos el id el ultimo usuario introducido en al base de datos Usuario
        String id_usuario= String.valueOf(count.getString(0));

        //Cerramos la conexión con la base de datos.
        //dbUsuario.close();

        //Abrimos la Base de datos "BDAlumnosIngrediente" en modo escritura.
        BDUsuarioIngrediente bdUsuarioIngrediente = new BDUsuarioIngrediente(getView().getContext(), "BDUsuarioIngrediente", null, 1);

        //Ponemos la Base de datos en Modo Escritura.
        dbIngrediente = bdUsuarioIngrediente.getWritableDatabase();

        String Columna1="id_usuario";
        String Columna2="id_ingrediente";

        ContentValues contentValues = new ContentValues();
        contentValues.put(Columna1,id_usuario);
        contentValues.put(Columna2,id_ingrediente);
        dbIngrediente.insert("Usuario_Ingrediente",null,contentValues);

        //Cerramos la conexión con la base de datos.
        dbIngrediente.close();
    }

    /**
     * @name public void insertar_idusuario_idingrediente(String id_ingrediente)
     * @description Metodo para ingreser id_usuario y id_ingrediente en la relación que existe entre usuario y ingrediente
     * @return void
     */
    public void insertar_idusuario_idingrediente(String id_usuario, String id_ingrediente)
    {
        //Abrimos la Base de datos "BDAlumnosIngrediente" en modo escritura.
        BDUsuarioIngrediente bdUsuarioIngrediente = new BDUsuarioIngrediente(getView().getContext(), "BDUsuarioIngrediente", null, 1);

        //Ponemos la Base de datos en Modo Escritura.
        dbIngrediente = bdUsuarioIngrediente.getWritableDatabase();

        String Columna1="id_usuario";
        String Columna2="id_ingrediente";

        ContentValues contentValues = new ContentValues();
        contentValues.put(Columna1,id_usuario);
        contentValues.put(Columna2,id_ingrediente);
        dbIngrediente.insert("Usuario_Ingrediente",null,contentValues);

        //Cerramos la conexión con la base de datos.
        dbIngrediente.close();
    }

    /**
     * @name public void eliminar_idusuario_idingrediente(String id_usuario)
     * @description Metodo para eliminar id_ingrediente en la relación que existe entre usuario y ingrediente
     * @return void
     */
    public void eliminar_idusuario_idingrediente(String id_usuario)
    {
        //Abrimos la Base de datos "BDAlumnosIngrediente" en modo escritura.
        BDUsuarioIngrediente bdUsuarioIngrediente = new BDUsuarioIngrediente(getView().getContext(), "BDUsuarioIngrediente", null, 1);

        //Ponemos la Base de datos en Modo Escritura.
        dbIngrediente = bdUsuarioIngrediente.getWritableDatabase();

        dbIngrediente.execSQL("DELETE FROM Usuario_Ingrediente WHERE id_usuario="+id_usuario);

        //Cerramos la conexión con la base de datos.
       dbIngrediente.close();
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
