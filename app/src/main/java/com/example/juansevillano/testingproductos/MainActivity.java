package com.example.juansevillano.testingproductos;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int proceso=0;
    private Handler handler = new Handler();
    private TextView textView;

    final Handler mHandler=new Handler();

    //Definimos una variable de tipo SQLiteDatabase
    //SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       //new MiTareaAsincrona().execute();

        //Transicción de la Imagen.
        efectoimagen();

        //Esperamos 200 milisegundos
        SystemClock.sleep(200);

        //Llamamos a la Funcion de creacion de la barra cargadora
        setProgressValue();

        //Esperamos 1900 milisegundos hasta que termine.
        SystemClock.sleep(0);
    }


    /**
     * @name private void efectoimagen()
     * @description Funcion para aplicar efecto a la imagen
     * @return void
     */
    private void efectoimagen()
    {
        ImageView imagen = (ImageView)findViewById(R.id.ImagenPortada);

        Animation animacionImagen = AnimationUtils.loadAnimation(this,R.anim.trans);
        animacionImagen.reset();
        imagen.startAnimation(animacionImagen);

    }

    /**
     * @name private void setProgressValue()
     * @description Funcion para la creación de la barra cargadora
     * @return void
     */
    private void setProgressValue()
    {
        final ProgressBar barracargadora=(ProgressBar)findViewById(R.id.progressBar);
        barracargadora.getIndeterminateDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
        barracargadora.setProgress(proceso);

        textView=(TextView) findViewById(R.id.textView);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(proceso<barracargadora.getMax())
                {
                    proceso+=2;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            barracargadora.setProgress(proceso);
                            textView.setText(proceso+"/"+barracargadora.getMax());
                        }
                    });
                    try
                    {
                        //Sleep for 40 milliseconds
                        Thread.sleep(40);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                SystemClock.sleep(100);

                mHandler.post(ejecutarAccion);
            }
        }).start();
    }

    /**
     * @name ejecutarAccion
     * @description Creación de un Hilo de Tipo Runnable para ejecutar la funcionBD despues
     * de que termine el hilo de la barra cargadora procesbar
     * @return void
     */
    final Runnable ejecutarAccion = new Runnable() {
        @Override
        public void run() {
            Redireccionar();
        }
    };


    private void Redireccionar()
    {
        //Accedemos a la Pantalla del Registro del Usuario
        Intent ListSong = new Intent(getApplicationContext(), ComprobarSesion.class);
        startActivity(ListSong);
        finish();
    }

    /**
     * @name private void  funcionBD()
     * @description Funcion gestion de la base de datos
     * @return void
     */
    /*private void  funcionBD()
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

    }*/

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