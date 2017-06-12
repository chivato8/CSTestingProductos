package com.example.juansevillano.testingproductos;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class EntrarCon extends AppCompatActivity implements View.OnClickListener {

    //Definimos una variable de tipo SQLiteDatabase
    SQLiteDatabase db;

    //defining view objects
    private ImageView imageViewInvitado;
    private ImageView imageViewGmail;
    private ImageView imageViewFacebook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar_con);

        imageViewInvitado = (ImageView) findViewById(R.id.imageViewInvitado);
        imageViewGmail = (ImageView) findViewById(R.id.imageViewGmail);
        imageViewFacebook = (ImageView) findViewById(R.id.imageViewFacebook);

        //attaching listener to button
        imageViewInvitado.setOnClickListener(this);
        imageViewGmail.setOnClickListener(this);
        imageViewFacebook.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        //calling register method on click
        if(view == imageViewInvitado){
            //Llamamos a la Funcion de creacion de la barra cargadora
            funcionBD();

            //Esperamos 1900 milisegundos hasta que termine.
            SystemClock.sleep(0);
        }

        if(view == imageViewGmail){
            //open login activity IniciarSesionGmail
            startActivity(new Intent(this, IniciarSesionGmail.class));
            finish();
        }

        if(view == imageViewFacebook){
            //open login activity IniciarSesionFacebook
            //startActivity(new Intent(this, IniciarSesionFacebook.class));
            //finish();
        }
    }

    /**
     * @name private void  funcionBD()
     * @description Funcion gestion de la base de datos
     * @return void
     */
    private void  funcionBD()
    {

        //Abrimos la Base de datos "BDUsuario" en modo escritura.
        BDUsuario Usuarios=new BDUsuario(this,"BDUsuario",null,1);

        //Ponemos la Base de datos en Modo Escritura.
        db= Usuarios.getWritableDatabase();

        //Toast.makeText(getBaseContext(), "Usuario Cargado Correctamente", Toast.LENGTH_LONG).show();

        //Comprobamos que la base de datos existe
        if(db!=null)
        {

            //db.execSQL("INSERT INTO Usuarios (Nombre, Apellidos) VALUES('Juan','Santander')");
            //db.execSQL("INSERT INTO Usuarios (Nombre, Apellidos) VALUES('Juan2','Santander2')");

            //Comprobamos si la Base de datos con la que estamos trabajando esta VACIA
            Cursor count=db.rawQuery("SELECT Nombre FROM Usuarios",null);

            if(count.getCount()>0) //La Base de Datos SI tiene Usuario Registrado
            {
                //count.moveToFirst();
                //Toast.makeText(getBaseContext(), "Usuarios Registrados: " + count.getCount(), Toast.LENGTH_LONG).show();
                Toast.makeText(getBaseContext(), "Accediendo a la Aplicación.", Toast.LENGTH_SHORT).show();

                //Accedemos a la Aplición para la Eleccion del Modo de Escaneo
                Intent ListSong = new Intent(getApplicationContext(), VentanaOpcionesEscaner.class);
                startActivity(ListSong);
                finish();

            }//La Base de Datos NO tiene Ningun Usuario Registrado
            else
            {
                Toast.makeText(getBaseContext(), "!BASE DE DATOS VACIA¡ - Procederemos al Registro de un Usuario.", Toast.LENGTH_SHORT).show();

                //Esperamos 50 milisegundos
                SystemClock.sleep(50);

                //Cerramos la Base de Datos
                //db.close();

                //Accedemos a la Pantalla del Registro del Usuario
                Intent ListSong = new Intent(getApplicationContext(), VentanaRegistroUsuario.class);
                startActivity(ListSong);
                finish();

            }

            //Log.i(this.getClass().toString(), "Datos Iniciales INSERTADOS");
        }

        //Cerramos la Base de Datos
        //db.close();

    }

    /**
     * @name public void onBackPressed ()
     * @description Si hacemos clic en el boton hacia atras saldremos de la aplicacion
     * @return void
     */
    @Override
    public void onBackPressed () {

        if (true)
        {
            finish();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            super.onBackPressed();
        }
    }
}
