package com.example.juansevillano.testingproductos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class VentanaEditarUsuario extends AppCompatActivity  {

    private android.app.FragmentManager manager;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public List<Fragment> fragments = new ArrayList<Fragment>();

    //Definimos una variable de tipo SQLiteDatabase
    SQLiteDatabase db;

    //ID Usuario Elegido.
    String elegido="-1";

    //Definimos una Variable de tipo Cursor
    Cursor res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_editar_usuario);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        manager = getFragmentManager();

        fragments.add(new RegistroUsuario());
        fragments.add(new AlergenicoAltamuz());
        fragments.add(new FinRegistroUsuario());

        //fragments.add(new FourFragment());

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        FragmentViewPagerAdapter2 adapter = new FragmentViewPagerAdapter2(this.getSupportFragmentManager(), viewPager,fragments);

        //transaction = manager.beginTransaction();

        setupTabIcons();
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        //Pedimos al Usuario que elige un usuario de la lista.
        createRadioListDialog();

    }

    private void setupTabIcons() {

        tabLayout.getTabAt(0).setIcon(R.mipmap.registrousuario);
        tabLayout.getTabAt(1).setIcon(R.mipmap.altamucesmini);
        tabLayout.getTabAt(2).setIcon(R.mipmap.finregistroico);
    }

    /**
     * Adding fragments to ViewPager
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new RegistroUsuario(), "Registro");
        adapter.addFrag(new AlergenicoAltamuz(), "Altamuces");
        //adapter.addFrag(new ThreeFragment(), "Tres");
        //adapter.addFrag(new FourFragment(), "Cuatro");
        //adapter.addFrag(new FiveFragment(), "Cinco");
        //adapter.addFrag(new FiveFragment(), "Seis");
        adapter.addFrag(new FinRegistroUsuario(), "Fin Registro");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
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

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("Selecciones el Usuario que deseas Editar:");

        //Mostramos la lista de usuarios a elegir para poder empezar la lectura de codigos de barras
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {
                //Movemos el cursor al primer elemento
                res.moveToPosition(item);
                elegido=res.getString(0);
                int num=Integer.parseInt(elegido);
                Toast toast = Toast.makeText(getApplicationContext(), "Haz elegido la opcion: " +res.getString(1)+" "+elegido, Toast.LENGTH_SHORT);
                toast.show();
                dialog.cancel();
                if(!elegido.equals("-1"))
                {
                    Intent ListSong = new Intent(getApplicationContext(), VentanaEditarUsuario.class);
                    startActivity(ListSong);
                    finish();
                }
            }

        });

        android.support.v7.app.AlertDialog alert = builder.create();
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
