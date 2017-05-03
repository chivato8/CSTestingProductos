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
    SQLiteDatabase db;

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

                // Obtener el control de la TabAFm
                System.out.println("PRUEBA - 1");
                EditText nom = (EditText)activity.fragments.get(0).getView().findViewById(R.id.nombreU);
                String nombre= nom.getText().toString();
                EditText apel = (EditText)activity.fragments.get(0).getView().findViewById(R.id.apellidos);
                String apellidos = apel.getText().toString();
                EditText cor = (EditText)activity.fragments.get(0).getView().findViewById(R.id.correo);
                String correo = cor.getText().toString();
                EditText tel = (EditText)activity.fragments.get(0).getView().findViewById(R.id.telefono);
                String telefono = tel.getText().toString();

                if(!nombre.equals("")&&!apellidos.equals(""))
                {
                    System.out.println("PRUEBA - 2");
                    insertarUsuario(nombre,apellidos,telefono,correo);
                    System.out.println("PRUEBA - 5");
                    //Falta Insertar Lista de Transtornos Alimenticios en la Nueva Tabla

                    Toast.makeText(getActivity().getBaseContext(), "!Usuarios Registrado Correctamente¡", Toast.LENGTH_LONG).show();

                    //Cerramos la Base de Datos
                    db.close();

                    //Esperamos 50 milisegundos
                    SystemClock.sleep(50);

                    getView().setId(R.id.restablecer);

                    Intent intent = new Intent (getActivity(),VentanaOpcionesEscaner.class);
                    startActivity(intent);
                    getActivity().finish();
                }
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





                ///////////////////////////////////////////
                ListView lstLista = (ListView) activity.fragments.get(1).getView().findViewById(R.id.lstLista);

                int nItems = lstLista.getChildCount();
                CheckBox lMarcado;
                //TextView cCodigoMarcado;
                String cMensaje = "Marcados\n\n";
                if(true){
                    for (int i = 0; i < nItems; i++) {

                        lMarcado = (CheckBox) lstLista.getChildAt(i).findViewById(R.id.chkEstado);
                        if (lMarcado.isChecked())
                        {
                            Toast.makeText(activity, ((TextView)(lstLista.getChildAt(i).findViewById(R.id.txtCompleto))).getText().toString(), Toast.LENGTH_SHORT).show();
                            //cCodigoMarcado = ((TextView) lstLista.getChildAt(i).findViewById(R.id.txtDia)).getText().toString();

                            //cMensaje = cMensaje + cCodigoMarcado + "\n";
                        }
                    }
                }

                //System.out.println(nombre.getText());

                Toast.makeText(activity,nombre,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void insertarUsuario(String nombre, String apellidos, String telefono, String correo)
    {
        //Abrimos la Base de datos "BDAlumnos" en modo escritura.
        BDUsuario bdUsuario = new BDUsuario(getView().getContext(), "BDUsuario", null, 1);

        //Ponemos la Base de datos en Modo Escritura.
        db = bdUsuario.getWritableDatabase();
        System.out.println("PRUEBA - 3");
        //Definimos Usuario mediante un constructor
        System.out.println("PRUEBA - 4");

        String Columna1="Nombre";
        String Columna2="Apellidos";
        String Columna3="Telefono";
        String Columna4="Correo_electronico";

        ContentValues contentValues = new ContentValues();
        contentValues.put(Columna1,nombre);
        contentValues.put(Columna2,apellidos);
        contentValues.put(Columna3,telefono);
        contentValues.put(Columna4,correo);
        db.insert("Usuarios",null,contentValues);
        db.close();
    }
}
