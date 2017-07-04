package com.example.juansevillano.testingproductos;


import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class VentanaOpcionesEscaner extends AppCompatActivity implements View.OnClickListener {

    //Usamos la Clase BDUsuario para acceder a los datos de la Base de Datos.
    //BDUsuario bdUsuario=new BDUsuario(this,"BDUsuario",null,1);

    //ID Usuario Elegido.
    String elegido="-1";

    //Definimos una Variable de tipo Cursor
    Cursor res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_opciones_escaner);

        ImageButton cbarra=  (ImageButton) findViewById(R.id.barra);
        cbarra.setOnClickListener(this);

        ImageButton btexto=  (ImageButton) findViewById(R.id.bingredinete);
        btexto.setOnClickListener(this);

    }

    /**
     * @name public void onClick(View v)
     * @description Metodo para los Botones Codigo de Barra y Texto de Ingredientes
     * @return void
     */
    public void onClick(View v)
    {

        if(v.getId()==R.id.barra) {

            if(elegido.equals("-1"))
            {
                createRadioListDialog();
            }
            else
            {
                Intent ListSong = new Intent(getApplicationContext(), CodigoBarra.class);
                startActivity(ListSong);
                finish();
            }
        }

        if(v.getId()==R.id.bingredinete)
        {
            if(elegido.equals("-1"))
            {

            }
            else
            {

            }
        }
    }

    /**
     * @name public boolean onCreateOptionsMenu(Menu menu)
     * @description Metodo para la creación del menu
     * @return boolean
     */
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_usuario, menu);
        return true;
    }

    /**
     * @name public boolean onOptionsItemSelected (MenuItem item)
     * @description Metodo para cuando hagamos clic en un apartado del menu
     * @return boolean
     */
    public boolean onOptionsItemSelected (MenuItem item)
    {
        switch (item.getItemId())
        {
            //Si hacemos clic en insertar
            case R.id.insertar_usuario:
                Intent intentregistro = new Intent (this,VentanaRegistroUsuario.class);
                startActivity(intentregistro);
                finish();
                return true;
            case R.id.editar_usuario:
                //Pedimos al Usuario que elige un usuario de la lista.
                createRadioListDialogEditar();
                return true;
            case R.id.borrar_usuario:
                createRadioListDialogBorrar();
                return true;
            case R.id.irventanausuarios:
                Intent intentventanausuarios = new Intent (this,EntrarCon.class);
                startActivity(intentventanausuarios);
                finish();
                return true;
            case R.id.info14alergenico:
                DownloadFiles();
                return true;
            case R.id.salirfin:
                Toast toast = Toast.makeText(getApplicationContext(), "Salimos de la Aplicación.", Toast.LENGTH_SHORT);
                toast.show();
                finish();
                return true;
            default:
                return true;
        }
    }

    /**
     * @name public int createRadioListDialogEditar()
     * @description Crea un diálogo con una lista de radios para la Eleccion de Usuario
     * @return int
     */
    public void createRadioListDialogEditar() {

        //Definimos un vector MenuItem, que representa los elementos que tienen.
        final CharSequence[] items;

        //Abrimos la Base de datos "BDUsuario" en modo escritura.
        BDUsuario bdUsuarios=new BDUsuario(this,"BDUsuario",null,1);

        SQLiteDatabase db = bdUsuarios.getWritableDatabase();
        res=db.rawQuery("SELECT ID, Nombre, Apellidos FROM Usuarios",null);
        //db.close();

        //Definimos una variable de tipo CharSequence de tamaño res.getCount()
        items=new CharSequence[res.getCount()];

        //Movemos el cursor al primer elemento
        res.moveToFirst();

        //Mostramos los datos mediente un bucle for
        for (int i=0;i<items.length;i++) {
            items[i]=res.getString(1) + " "+res.getString(2);
            res.moveToNext();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona el Usuario que deseas Editar:");

        //Mostramos la lista de usuarios a elegir para poder empezar la lectura de codigos de barras
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {
                //Movemos el cursor al primer elemento
                res.moveToPosition(item);
                elegido=res.getString(0);
                //Toast toast = Toast.makeText(getApplicationContext(), "Haz elegido la opcion: " +res.getString(1)+" "+elegido, Toast.LENGTH_SHORT);
                //toast.show();
                dialog.cancel();

                //Si se ha seleccionado un usuario
                if(!elegido.equals("-1"))
                {
                    //Accedemos a la Ventana Editar Usuario.
                    Intent ListSong = new Intent (VentanaOpcionesEscaner.this, VentanaEditarUsuario.class);
                    ListSong.putExtra("elegido", elegido);
                    startActivity(ListSong);
                    finish();
                }
            }

        });

        AlertDialog alert = builder.create();
        alert.show();
    }


    /**
     * @name public int createRadioListDialogEditar()
     * @description Crea un diálogo con una lista de radios para la Eleccion de Usuario
     * @return int
     */
    public void createRadioListDialogBorrar() {

        //Definimos un vector MenuItem, que representa los elementos que tienen.
        final CharSequence[] items;

        //Abrimos la Base de datos "BDUsuario" en modo escritura.
        BDUsuario bdUsuarios=new BDUsuario(this,"BDUsuario",null,1);

        SQLiteDatabase db = bdUsuarios.getWritableDatabase();
        res=db.rawQuery("SELECT ID, Nombre, Apellidos FROM Usuarios",null);
        //db.close();

        //Definimos una variable de tipo CharSequence de tamaño res.getCount()
        items=new CharSequence[res.getCount()];

        //Movemos el cursor al primer elemento
        res.moveToFirst();

        //Mostramos los datos mediente un bucle for
        for (int i=0;i<items.length;i++) {
            items[i]=res.getString(1) + " "+res.getString(2);
            res.moveToNext();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona el Usuario que deseas Eliminar:");

        //Mostramos la lista de usuarios a elegir para poder empezar la lectura de codigos de barras
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {
                //Movemos el cursor al primer elemento
                res.moveToPosition(item);
                elegido=res.getString(0);
                //Toast toast = Toast.makeText(getApplicationContext(), "Haz elegido la opcion: " +res.getString(1)+" "+elegido, Toast.LENGTH_SHORT);
                //toast.show();
                dialog.cancel();

                //Si se ha seleccionado un usuario
                if(!elegido.equals("-1"))
                {

                    //Pedimos confirmación si desea eliminar el usuario definitivamente de la aplicación.
                    android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(VentanaOpcionesEscaner.this);

                    alertDialog.setMessage("¿Deseas Eliminar al Usuario Seleccionado definitivamente de la Aplicación?");
                    alertDialog.setTitle("Importante");
                    alertDialog.setIcon(R.mipmap.atencion_opt);
                    alertDialog.setCancelable(false);
                    alertDialog.setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //No Realizamos ninguna Acceion

                        }
                    });
                    alertDialog.setNegativeButton("Confirmar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            //Abrimos la Base de datos "BDUsuarioIngrediente" en modo escritura.
                            BDUsuario bdUsuariosIngrediente=new BDUsuario(VentanaOpcionesEscaner.this,"BDUsuarioIngrediente",null,1);

                            SQLiteDatabase dbUI = bdUsuariosIngrediente.getWritableDatabase();
                            //Sentenica SQL para borrar todos los Usuario_Ingredientes que existan.
                            dbUI.execSQL("DELETE FROM Usuario_Ingrediente WHERE id_usuario='" + elegido.toString() + "'");

                            //Borramos al Usuario
                            //Abrimos la Base de datos "BDUsuario" en modo escritura.
                            BDUsuario bdUsuarios=new BDUsuario(VentanaOpcionesEscaner.this,"BDUsuario",null,1);

                            SQLiteDatabase db = bdUsuarios.getWritableDatabase();
                            //Sentencia SQL para borrar al usuario
                            db.execSQL("DELETE FROM Usuarios WHERE ID='" + elegido.toString() + "'");

                            Toast toast = Toast.makeText(getApplicationContext(), "!Usuario Borrado Correctamente¡", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });

                    alertDialog.show();


                }
            }

        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * @name public int createRadioListDialog()
     * @description Crea un diálogo con una lista de radios para la Eleccion de Usuario
     * @return int
     */
    public void createRadioListDialog() {

        //Definimos un vector MenuItem, que representa los elementos que tienen.
        final CharSequence[] items;

        //Abrimos la Base de datos "BDUsuario" en modo escritura.
        BDUsuario bdUsuarios=new BDUsuario(this,"BDUsuario",null,1);

        SQLiteDatabase db = bdUsuarios.getWritableDatabase();
        res=db.rawQuery("SELECT ID, Nombre, Apellidos FROM Usuarios",null);
        //db.close();

        //Definimos una variable de tipo CharSequence de tamaño res.getCount()
        items=new CharSequence[res.getCount()];

        //Movemos el cursor al primer elemento
        res.moveToFirst();

        //Mostramos los datos mediente un bucle for
        for (int i=0;i<items.length;i++) {
            items[i]=res.getString(1) + " "+res.getString(2);
            res.moveToNext();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciones el Usuario con el que quiere ejecutar la Aplicación:");

        //Mostramos la lista de usuarios a elegir para poder empezar la lectura de codigos de barras
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {
                //Movemos el cursor al primer elemento
                res.moveToPosition(item);
                elegido=res.getString(0);
                //int num=Integer.parseInt(elegido);
                Toast toast = Toast.makeText(getApplicationContext(), "Haz elegido la opcion: " +res.getString(1)+" "+elegido, Toast.LENGTH_SHORT);
                toast.show();
                dialog.cancel();
                if(!elegido.equals("-1"))
                {
                    Intent ListSong = new Intent(getApplicationContext(), CodigoBarra.class);
                    startActivity(ListSong);
                    finish();
                }
            }

        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * @name public void onBackPressed ()
     * @description Si hacemos clic en el boton hacia atras saldremos de la aplicacion
     * @return void
     */
    @Override
    public void onBackPressed () {

        if (true) {
            android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);

            alertDialog.setMessage("¿Deseas Volver a la Elección de Usuario en la Aplicación?");
            alertDialog.setTitle("Importante");
            alertDialog.setIcon(R.mipmap.atencion_opt);
            alertDialog.setCancelable(false);
            alertDialog.setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //No Realizamos ninguna Acceion

                }
            });
            alertDialog.setNegativeButton("Confirmar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Toast.makeText(getBaseContext(), "Saliendo de la Aplicación. Accedemos a la Ventana de Eleccion de Usuario....", Toast.LENGTH_SHORT).show();
                    //Esperamos 50 milisegundos
                    SystemClock.sleep(500);
                    Salir();
                }
            });

            alertDialog.show();
        }
        else
        {
            super.onBackPressed();
        }
    }

    /**
     * @name public void DownloadFiles()
     * @description Funcion para descargar pdf de información de los 14 Alergenicos.
     * @return void
     */
    public void DownloadFiles(){
        try {
            Uri path = Uri.parse("http://tfgalimentos.16mb.com/alergenicos.pdf");
            Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW,path);

            try {
                startActivity(pdfOpenintent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getApplicationContext(),"Necesita Instalar un programa para pode visualizar PDF.", Toast.LENGTH_LONG).show();
            }
        } catch (SecurityException se) {
            Log.e("SYNC getUpdate", "security error", se);
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
        Intent intent = new Intent(this, EntrarCon.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}


