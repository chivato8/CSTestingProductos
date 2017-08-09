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

    /**
     * @name private void onCreate( Bundle savedInstanceState)
     * @description Primer Método que se llama al crear la clase
     * @return void
     */
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
     * @description Creación de un Hilo de Tipo Runnable para abrir la calse Comprobar sesión despues de que se termine el proceso
     * de la barra cargadora
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