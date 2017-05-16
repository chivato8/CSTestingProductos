package com.example.juansevillano.testingproductos;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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

    //Definimos una variable de tipo SQLiteDatabase
    SQLiteDatabase dbUsuario;
    SQLiteDatabase dbIngrediente;

    //Creamos un Objeto de Tipo Ingrediente
    Ingredientes ingrediente;

    RegistroUsuario registroUsuario= new RegistroUsuario();

    public FinRegistroUsuario() {
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
        this.getView().findViewById(R.id.crear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(comprobarActivityALaVista(getActivity(),"com.example.juansevillano.testingproductos.VentanaEditarUsuario")==true)
                {
                    // Definimos un objeto del activity VentanaEditarUsuario
                    final VentanaEditarUsuario activity2 = ((VentanaEditarUsuario) getActivity());

                    //Obtenemos el contenido del edittext del nombre del usuario
                    nom = (EditText) activity2.fragments.get(0).getView().findViewById(R.id.nombreU);
                    nombre = nom.getText().toString();
                    //Obtenemos el contenido del edittext del apellidos del usuario
                    apel = (EditText) activity2.fragments.get(0).getView().findViewById(R.id.apellidos);
                    apellidos = apel.getText().toString();
                    //Obtenemos el contenido del edittext del correo del usuario
                    cor = (EditText) activity2.fragments.get(0).getView().findViewById(R.id.correo);
                    correo = cor.getText().toString();
                    //Obtenemos el contenido del edittext del telefono del usuario
                    tel = (EditText) activity2.fragments.get(0).getView().findViewById(R.id.telefono);
                    telefono = tel.getText().toString();
                }
                else {

                    // Definimos un objeto del activity VentanaRegistroUsuario
                    final VentanaRegistroUsuario activity = ((VentanaRegistroUsuario) getActivity());

                    //Obtenemos el contenido del edittext del nombre del usuario
                    nom = (EditText)activity.fragments.get(0).getView().findViewById(R.id.nombreU);
                    nombre= nom.getText().toString();
                    //Obtenemos el contenido del edittext del apellidos del usuario
                    apel = (EditText)activity.fragments.get(0).getView().findViewById(R.id.apellidos);
                    apellidos = apel.getText().toString();
                    //Obtenemos el contenido del edittext del correo del usuario
                    cor = (EditText)activity.fragments.get(0).getView().findViewById(R.id.correo);
                    correo = cor.getText().toString();
                    //Obtenemos el contenido del edittext del telefono del usuario
                    tel = (EditText)activity.fragments.get(0).getView().findViewById(R.id.telefono);
                    telefono = tel.getText().toString();
                }

                //Comprobamos si el edittext nombre no es vacio y si el edittext apellidos no es vacio para poder introducir
                // el usuario en la base de datos Usuario
                if(!nombre.equals("")&&!apellidos.equals(""))
                {

                    if(comprobarActivityALaVista(getActivity(),"com.example.juansevillano.testingproductos.VentanaEditarUsuario")==true)
                    {
                        // Definimos un objeto del activity VentanaEditarUsuario
                        final VentanaEditarUsuario activity2 = ((VentanaEditarUsuario) getActivity());

                        //Obtenemos el id_usuario que hemos elegido
                        String[] elusuario = new String[] {activity2.getMyData()};

                        //Llamamos a la funcion actualizar usuario.
                        actualizarUsuario(elusuario[0],nombre,apellidos,correo,telefono);

                        //Llamamos a la funcion eliminar todas las relaciones de usuario con ingrediente del usuario que estamos
                        //editando.
                        eliminar_idusuario_idingrediente(elusuario[0]);

                        //Procedemos a introducir las tuplas con las que se relaciona el usuario con los ingredientes.
                        //Por lo que recorredmos los 16 fragments de la Ventana Registro Usuario.
                        for(int j=1; j<16;j++)
                        {

                            ListView lstLista = (ListView) activity2.fragments.get(j).getView().findViewById(R.id.lstLista);

                            int nItems = lstLista.getChildCount();
                            CheckBox lMarcado;

                            System.out.println(j);

                            System.out.println(nItems);

                            //Introducimos los id_ingredientes de los que esten marcados
                            for (int i = 0; i < nItems; i++) {

                                lMarcado = (CheckBox) lstLista.getChildAt(i).findViewById(R.id.chkEstado);
                                if (lMarcado.isChecked())
                                {

                                    String id=((TextView)(lstLista.getChildAt(i).findViewById(R.id.idingrediente))).getText().toString();

                                    insertar_idusuario_idingrediente(elusuario[0],id.toString());

                                    //Toast.makeText(activity, ((TextView)(lstLista.getChildAt(i).findViewById(R.id.idingrediente))).getText().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        Toast.makeText(getActivity().getBaseContext(), "!Usuarios Modificado Correctamente¡", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        // Definimos un objeto del activity VentanaRegistroUsuario
                        final VentanaRegistroUsuario activity = ((VentanaRegistroUsuario) getActivity());

                        //Insertamos los datos del Usuario en la Base de Datos llamada Usuario.
                        insertarUsuario(nombre,apellidos,telefono,correo);

                        //Procedemos a introducir las tuplas con las que se relaciona el usuario con los ingredientes.
                        //Por lo que recorredmos los 16 fragments de la Ventana Registro Usuario.
                        for(int j=1;j<16;j++)
                        {
                            ListView lstLista = (ListView) activity.fragments.get(j).getView().findViewById(R.id.lstLista);

                            int nItems = lstLista.getChildCount();
                            CheckBox lMarcado;
                            //Introducimos los id_ingredientes de los que esten marcados
                            if(true){
                                for (int i = 0; i < nItems; i++) {

                                    lMarcado = (CheckBox) lstLista.getChildAt(i).findViewById(R.id.chkEstado);
                                    if (lMarcado.isChecked())
                                    {
                                        String id=((TextView)(lstLista.getChildAt(i).findViewById(R.id.idingrediente))).getText().toString();

                                        insertar_idusuario_idingrediente(id.toString());

                                        //Toast.makeText(activity, ((TextView)(lstLista.getChildAt(i).findViewById(R.id.idingrediente))).getText().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                        Toast.makeText(getActivity().getBaseContext(), "!Usuarios Registrado Correctamente¡", Toast.LENGTH_LONG).show();
                    }

                    //Toast.makeText(activity,nombre,Toast.LENGTH_SHORT).show();

                    //Esperamos 50 milisegundos
                    SystemClock.sleep(50);

                    getView().setId(R.id.restablecer);


                    Intent intent = new Intent (getActivity(),VentanaOpcionesEscaner.class);
                    startActivity(intent);
                    getActivity().finish();


                }
                //Si el edittext nombre y el edittex apellidos son vacios mostrarmos un mensaje u notificación advirtiendo
                //que son campos obligatorios
                else
                {
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    View layout = inflater.inflate(R.layout.contenidotoast, (ViewGroup)getView().findViewById(R.id.toast_layout_root));

                    TextView txtMsg = (TextView) layout.findViewById(R.id.text_toast);
                    txtMsg.setText("Campos Obligatorios:");

                    if(nombre.equals(""))
                    {
                        txtMsg.setText(txtMsg.getText().toString()+"\n- Nombre");
                    }

                    if(apellidos.equals(""))
                    {
                        txtMsg.setText(txtMsg.getText().toString()+"\n- Apellido");
                    }

                    int duration = Toast.LENGTH_LONG;

                    Toast toast=new Toast(getActivity().getApplicationContext());
                    toast.setDuration(duration);
                    toast.setView(layout);
                    toast.show();
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
        Cursor count=dbUsuario.rawQuery("SELECT Nombre FROM Usuarios",null);

        //Obtenemos el id el ultimo usuario introducido en al base de datos Usuario
        String id_usuario= String.valueOf(count.getCount());

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
        dbUsuario.close();
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
