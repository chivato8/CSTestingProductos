package com.example.juansevillano.testingproductos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Juan Sevillano on 22/03/2017.
 */

public class BDUsuario extends SQLiteOpenHelper {
    /**
     * @name public BDUsuario(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
     * @description Constructor de la clase BDUsuario
     * @return void
     */
    public BDUsuario(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * @name private void onCreate(SQLiteDatabase db)
     * @description Primer Método que se llama al crear la clase. Crea una Base de datos.
     * @return void
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(this.getClass().toString(), "Creando Base de Datos.");

        //Si no Existe la BD la crea y ejecuta los siguientes comandos.
        db.execSQL( "CREATE TABLE Usuarios(" +
                " ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " Nombre TEXT," +
                " Apellidos TEXT,"+
                " Telefono TEXT NULL,"+
                " Correo_electronico TEXT NULL)");

        Log.i(this.getClass().toString(), "Tabla Usuarios creada");

    }

    /**
     * @name private void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
     * @description Método para actualizar la base de datos.
     * @return void
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Se Elimina la Tabla anterior y la volvemos a crear.
        db.execSQL("DROP TABLE IF EXISTS Usuarios");

        //Vovemos a craer de nuevo la Tabla Alumnos.
        db.execSQL("CREATE TABLE Usuarios(" +
                " ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " Nombre TEXT," +
                " Apellidos TEXT," +
                " Telefono TEXT NULL," +
                " Correo_electronico TEXT NULL)");
    }
}