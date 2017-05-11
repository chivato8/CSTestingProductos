package com.example.juansevillano.testingproductos;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class FinRegistroUsuario extends Fragment {

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
                // Se une FragmentActivity
                final VentanaRegistroUsuario activity = ((VentanaRegistroUsuario)getActivity());

                // Obtener el control de los EditText de los Fragment
                System.out.println("PRUEBA - 1");
                //Obtenemos el contenido del edittext del nombre del usuario
                EditText nom = (EditText)activity.fragments.get(0).getView().findViewById(R.id.nombreU);
                String nombre= nom.getText().toString();
                //Obtenemos el contenido del edittext del apellidos del usuario
                EditText apel = (EditText)activity.fragments.get(0).getView().findViewById(R.id.apellidos);
                String apellidos = apel.getText().toString();
                //Obtenemos el contenido del edittext del correo del usuario
                EditText cor = (EditText)activity.fragments.get(0).getView().findViewById(R.id.correo);
                String correo = cor.getText().toString();
                //Obtenemos el contenido del edittext del telefono del usuario
                EditText tel = (EditText)activity.fragments.get(0).getView().findViewById(R.id.telefono);
                String telefono = tel.getText().toString();

                //Comprobamos si el edittext nombre no es vacio y si el edittext apellidos no es vacio para poder introducir
                // el usuario en la base de datos Usuario
                if(!nombre.equals("")&&!apellidos.equals(""))
                {
                    //Insertamos los datos del Usuario en la Base de Datos llamada Usuario.
                    insertarUsuario(nombre,apellidos,telefono,correo);

                    //Toast.makeText(activity,nombre,Toast.LENGTH_SHORT).show();

                    //Procedemos a introducir las tuplas con las que se relaciona el usuario con los ingredientes.
                    //Por lo que recorredmos los 16 fragments de la Ventana Registro Usuario.
                    for(int j=1; j<16;j++)
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
}
