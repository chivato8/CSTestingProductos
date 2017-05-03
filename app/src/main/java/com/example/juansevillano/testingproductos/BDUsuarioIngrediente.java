package com.example.juansevillano.testingproductos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Juan Sevillano on 20/04/2017.
 */

public class BDUsuarioIngrediente extends SQLiteOpenHelper {
    /**
     * Constructor de la Clase BDUsuarioIngrediente
     */
    public BDUsuarioIngrediente(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    /**
     * Crea una Base de datos
     *
     * @return void
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(this.getClass().toString(), "Creando Base de Datos.");

        //Si no Existe la BD la crea y ejecuta los siguientes comandos.
        db.execSQL( "CREATE TABLE Usuario_Ingrediente(" +
                " id_usuario_ingrediente INTEGER PRIMARY KEY AUTOINCREMENT," +
                " id_usuario INTEGER," +
                " id_ingrediente INTEGER");
    }

    /**
     * Actualiza una Base de datos
     *
     * @return void
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Se Elimina la Tabla anterior y la volvemos a crear.
        db.execSQL("DROP TABLE IF EXISTS Usuarios");

        //Vovemos a craer de nuevo la Tabla Alumnos.
        db.execSQL( "CREATE TABLE Usuario_Ingrediente(" +
                " id_usuario_ingrediente INTEGER PRIMARY KEY AUTOINCREMENT," +
                " id_usuario INTEGER," +
                " id_ingrediente INTEGER");
    }
}
