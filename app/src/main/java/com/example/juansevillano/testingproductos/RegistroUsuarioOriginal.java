package com.example.juansevillano.testingproductos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegistroUsuarioOriginal extends AppCompatActivity implements View.OnClickListener {

    //Definimos una variable de tipo SQLiteDatabase
    SQLiteDatabase db;

    //Creamos un Objeto de Tipo Usuario
    Usuario usuario;

    //Creamos un Objeto de Tipo Transtorno
    Transtornos transtornos;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        //Button bcrear = (Button) findViewById(R.id.crear);
        //bcrear.setOnClickListener(this);

        //Button brestablecer = (Button) findViewById(R.id.restablecer);
        //brestablecer.setOnClickListener(this);
    }

    /**
     * @name public void onClick(View v)
     * @description Metodo para la creación del menu
     * @return boolean
     */
    /*public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();

        inflater.inflate(R.menu.menu_usuario, menu);

        return true;
    }*/

    /**
     * @name public void onClick(View v)
     * @description Metodo para los Botones Crear y Restablecer
     * @return void
     */
    public void onClick(View v)
    {

        /*if(v.getId()==R.id.crear) {
            //Toast.makeText(getBaseContext(), "Click en Boton Crear", Toast.LENGTH_LONG).show();

            EditText nom = (EditText) findViewById(R.id.nombre);
            String nombre = nom.getText().toString();
            //Toast.makeText(this,"Prueba nombre: "+nom,Toast.LENGTH_LONG).show();

            EditText ape = (EditText) findViewById(R.id.apellidos);
            String apellido = ape.getText().toString();

            EditText co = (EditText) findViewById(R.id.correo);
            String correo = co.getText().toString();

            EditText tele = (EditText) findViewById(R.id.telefono);
            String telefono = tele.getText().toString();

            //Vamos ahora con la parte de los transtornos alimenticios
            transtornos = new Transtornos();

            //Si esta seleccionado el checkbox Altamuces.
            CheckBox checkBox = (CheckBox) findViewById(R.id.altamuces);
            if (checkBox.isChecked())
            {
                 transtornos.setAltamuces(true);
            }

            //Si esta seleccionado el checkbox Apio.
            checkBox = (CheckBox) findViewById(R.id.apio);
            if (checkBox.isChecked())
            {
                transtornos.setApio(true);
            }

            //Si esta seleccionado el checkbox Azucar.
            checkBox = (CheckBox) findViewById(R.id.azucar);
            if (checkBox.isChecked())
            {
                transtornos.setAzucar(true);
            }

            //Si esta seleccionado el checkbox Cacahuete.
            checkBox = (CheckBox) findViewById(R.id.cacahuete);
            if (checkBox.isChecked())
            {
                transtornos.setCacahuetes(true);
            }

            //Si esta seleccionado el checkbox Crustaceos.
            checkBox = (CheckBox) findViewById(R.id.crustaceos);
            if (checkBox.isChecked())
            {
                transtornos.setCrustaceos(true);
            }

            //Si esta seleccionado el checkbox Frutos de Cascara.
            checkBox = (CheckBox) findViewById(R.id.frutosdecascara);
            if (checkBox.isChecked())
            {
                transtornos.setFrutosdecascara(true);
            }

            //Si esta seleccionado el checkbox Gluten.
            checkBox = (CheckBox) findViewById(R.id.gluten);
            if (checkBox.isChecked())
            {
                transtornos.setGluten(true);
            }

            //Si esta seleccionado el checkbox Grano de Sesamo.
            checkBox = (CheckBox) findViewById(R.id.granodesesamo);
            if (checkBox.isChecked())
            {
                transtornos.setGranodesesamo(true);
            }

            //Si esta seleccionado el checkbox Huevo.
            checkBox = (CheckBox) findViewById(R.id.huevo);
            if (checkBox.isChecked())
            {
                transtornos.setHuevos(true);
            }

            //Si esta seleccionado el checkbox Lacteos.
            checkBox = (CheckBox) findViewById(R.id.lacteos);
            if (checkBox.isChecked())
            {
                transtornos.setLacteos(true);
            }

            //Si esta seleccionado el checkbox Moluscos.
            checkBox = (CheckBox) findViewById(R.id.molusco);
            if (checkBox.isChecked())
            {
                transtornos.setMoluscos(true);
            }

            //Si esta seleccionado el checkbox Mostaza.
            checkBox = (CheckBox) findViewById(R.id.mostaza);
            if (checkBox.isChecked())
            {
                transtornos.setMostaza(true);
            }

            //Si esta seleccionado el checkbox Pescado.
            checkBox = (CheckBox) findViewById(R.id.pescado);
            if (checkBox.isChecked())
            {
                transtornos.setPescado(true);
            }

            //Si esta seleccionado el checkbox Soja.
            checkBox = (CheckBox) findViewById(R.id.soja);
            if (checkBox.isChecked())
            {
                transtornos.setSoja(true);
            }

            //Si esta seleccionado el checkbox Otros.
            checkBox = (CheckBox) findViewById(R.id.otros);
            if (checkBox.isChecked())
            {
                //transtornos.setSoja(true);
            }

            //Toast.makeText(this,"Prueba ape: "+ape,Toast.LENGTH_LONG).show();
            if (!nombre.equals("") && !apellido.equals("")) {
                SQLiteDatabase db;
                //Abrimos la Base de datos "BDAlumnos" en modo escritura.
                BDUsuario bdUsuario = new BDUsuario(this, "BDUsuario", null, 1);

                //Ponemos la Base de datos en Modo Escritura.
                db = bdUsuario.getWritableDatabase();

                //Definimos Usuario mediante un constructor
                usuario=new Usuario(nombre,apellido,telefono,correo);

                //Falta Insertar Lista de Transtornos Alimenticios en la Nueva Tabla


                Toast.makeText(getBaseContext(), "!Usuarios Registrado Correctamente¡", Toast.LENGTH_LONG).show();

                //Cerramos la Base de Datos
                db.close();

                //Esperamos 50 milisegundos
                SystemClock.sleep(50);

                v.setId(R.id.restablecer);

                Intent intent = new Intent (this,VentanaOpcionesEscaner.class);
                startActivity(intent);
                finish();
            }
            else
            {
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.contenidotoast, (ViewGroup)findViewById(R.id.toast_layout_root));

            TextView txtMsg = (TextView) layout.findViewById(R.id.text_toast);
            txtMsg.setText("Campos Obligatorios:");

            if(nombre.equals(""))
            {
                txtMsg.setText(txtMsg.getText().toString()+"\n- Nombre");
            }

            if(apellido.equals(""))
            {
                txtMsg.setText(txtMsg.getText().toString()+"\n- Apellido");
            }

            int duration = Toast.LENGTH_LONG;

            Toast toast=new Toast(getApplicationContext());
            toast.setDuration(duration);
            toast.setView(layout);
            toast.show();
            }
        }

        if(v.getId()==R.id.restablecer)
        {
            EditText nombre = (EditText) findViewById(R.id.nombre);
            nombre.setText(null);
            //Toast.makeText(this,"Prueba nombre: "+nom,Toast.LENGTH_LONG).show();

            EditText apellido = (EditText) findViewById(R.id.apellidos);
            apellido.setText(null);

            EditText correo = (EditText) findViewById(R.id.correo);
            correo.setText(null);

            EditText telefono = (EditText) findViewById(R.id.telefono);
            telefono.setText(null);

            CheckBox altamuces = (CheckBox) findViewById(R.id.altamuces);
            altamuces.setChecked(false);

            CheckBox apio = (CheckBox) findViewById(R.id.apio);
            apio.setChecked(false);

            CheckBox azucar = (CheckBox) findViewById(R.id.azucar);
            azucar.setChecked(false);

            CheckBox cacahuete = (CheckBox) findViewById(R.id.cacahuete);
            cacahuete.setChecked(false);

            CheckBox crustaceos = (CheckBox) findViewById(R.id.crustaceos);
            crustaceos.setChecked(false);

            CheckBox frutosdecascara = (CheckBox) findViewById(R.id.frutosdecascara);
            frutosdecascara.setChecked(false);

            CheckBox gluten = (CheckBox) findViewById(R.id.gluten);
            gluten.setChecked(false);

            CheckBox granodesesamo = (CheckBox) findViewById(R.id.granodesesamo);
            granodesesamo.setChecked(false);

            CheckBox huevo = (CheckBox) findViewById(R.id.huevo);
            huevo.setChecked(false);

            CheckBox lacteos = (CheckBox) findViewById(R.id.lacteos);
            lacteos.setChecked(false);

            CheckBox molusco = (CheckBox) findViewById(R.id.molusco);
            molusco.setChecked(false);

            CheckBox mostaza = (CheckBox) findViewById(R.id.mostaza);
            mostaza.setChecked(false);

            CheckBox pescado = (CheckBox) findViewById(R.id.pescado);
            pescado.setChecked(false);

            CheckBox soja = (CheckBox) findViewById(R.id.soja);
            soja.setChecked(false);

            CheckBox otros = (CheckBox) findViewById(R.id.otros);
            otros.setChecked(false);

            Toast.makeText(getBaseContext(), "Restablecemos el Formulario.", Toast.LENGTH_LONG).show();
        }*/
    }

    /**
     * @name public void onBackPressed ()
     * @description Si hacemos clic en el boton hacia atras saldremos de la aplicacion
     * @return void
     */
    @Override
    public void onBackPressed () {
        if (true) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            //Abrimos la Base de datos "BDUsuario" en modo escritura.
            BDUsuario Usuarios=new BDUsuario(this,"BDUsuario",null,1);

            //Ponemos la Base de datos en Modo Escritura.
            db= Usuarios.getWritableDatabase();

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

                    Toast.makeText(getBaseContext(), "Accediendo a la Venta Principal.", Toast.LENGTH_SHORT).show();

                    //Accedemos a la Aplición para la Eleccion del Modo de Escaneo
                    Intent ListSong = new Intent(getApplicationContext(), VentanaOpcionesEscaner.class);
                    startActivity(ListSong);
                    finish();

                }//La Base de Datos NO tiene Ningun Usuario Registrado
                else
                {
                    //Cerramos la Base de Datos
                    db.close();

                    alertDialog.setMessage("¿Desea Salir de la Aplicación? \nSi Sales de la Aplicación no se Procedera al Registro del Usuario.");
                    alertDialog.setTitle("Importante");
                    alertDialog.setIcon(R.mipmap.atencion_opt);
                    alertDialog.setCancelable(false);
                    alertDialog.setPositiveButton("Cancelar", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            //No Realizamos ninguna Acceion

                        }
                    });
                    alertDialog.setNegativeButton("Confirmar", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            Toast.makeText(getBaseContext(), "Saliendo de la Aplicación....", Toast.LENGTH_SHORT).show();

                            db.close();

                            //Esperamos 50 milisegundos
                            SystemClock.sleep(500);
                            finish();
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    });

                    alertDialog.show();

                }

                Log.i(this.getClass().toString(), "Datos Iniciales Alumnos INSERTADOS");
            }

            //Cerramos la Base de Datos
            db.close();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * @name public void Salir()
     * @description Funcion si hacemos click en Si
     * @return void
     */
    public void Salir()
    {

        finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

}
