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

    //Definimos una Variable de tipo Cursor
    public Cursor res;
    //ID Usuario Elegido.
    String elegido="0";

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
        fragments.add(new AlergenicoApio());
        fragments.add(new AlergenicoAzufreySulfitos());
        fragments.add(new AlergenicoCacahuete());
        fragments.add(new AlergenicoCrustaceos());
        fragments.add(new AlergenicoFrutosCascara());
        fragments.add(new AlergenicoGluten());
        fragments.add(new AlergenicoSesamo());
        fragments.add(new AlergenicoHuevo());
        fragments.add(new AlergenicoLacteos());
        fragments.add(new AlergenicoMolusco());
        fragments.add(new AlergenicoMostaza());
        fragments.add(new AlergenicoPescado());
        fragments.add(new AlergenicoSoja());
        fragments.add(new AlergenicoOtros());
        fragments.add(new FinRegistroUsuario());

        //fragments.add(new FourFragment());

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //ID Usuario Elegido.
        elegido=getIntent().getStringExtra("elegido");


        FragmentViewPagerAdapter2 adapter = new FragmentViewPagerAdapter2(this.getSupportFragmentManager(), viewPager,fragments);

        //transaction = manager.beginTransaction();

        setupTabIcons();
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

    }

    public String getMyData() {
        return elegido;
    }

    private void setupTabIcons() {

        tabLayout.getTabAt(0).setIcon(R.mipmap.registrousuario);
        tabLayout.getTabAt(1).setIcon(R.mipmap.altamucesmini);
        tabLayout.getTabAt(2).setIcon(R.mipmap.apiomini);
        tabLayout.getTabAt(3).setIcon(R.mipmap.azucarmini);
        tabLayout.getTabAt(4).setIcon(R.mipmap.cacahuetesmini);
        tabLayout.getTabAt(5).setIcon(R.mipmap.mariscomini);
        tabLayout.getTabAt(6).setIcon(R.mipmap.frutosdecascaramini);
        tabLayout.getTabAt(7).setIcon(R.mipmap.glutenmini);
        tabLayout.getTabAt(8).setIcon(R.mipmap.sesamomini);
        tabLayout.getTabAt(9).setIcon(R.mipmap.huevosmini);
        tabLayout.getTabAt(10).setIcon(R.mipmap.lacteosmini);
        tabLayout.getTabAt(11).setIcon(R.mipmap.moluscosmini);
        tabLayout.getTabAt(12).setIcon(R.mipmap.mostazamini);
        tabLayout.getTabAt(13).setIcon(R.mipmap.pescadomini);
        tabLayout.getTabAt(14).setIcon(R.mipmap.sojamini);
        tabLayout.getTabAt(15).setIcon(R.mipmap.otrosmini);
        tabLayout.getTabAt(16).setIcon(R.mipmap.finregistroico);
    }

    /**
     * Adding fragments to ViewPager
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new RegistroUsuario(), "Editar");
        adapter.addFrag(new AlergenicoAltamuz(), "Altamuz");
        adapter.addFrag(new AlergenicoApio(), "Apio");
        adapter.addFrag(new AlergenicoAzufreySulfitos(), "Azufre y Sulfitos");
        adapter.addFrag(new AlergenicoCacahuete(), "Cacahuete");
        adapter.addFrag(new AlergenicoCrustaceos(), "Crustaceos");
        adapter.addFrag(new AlergenicoFrutosCascara(), "Frutos con Cascara");
        adapter.addFrag(new AlergenicoGluten(), "Gluten");
        adapter.addFrag(new AlergenicoSesamo(), "Sesamo");
        adapter.addFrag(new AlergenicoHuevo(), "Huevo");
        adapter.addFrag(new AlergenicoLacteos(), "Lacteos");
        adapter.addFrag(new AlergenicoMolusco(), "Moluscos");
        adapter.addFrag(new AlergenicoMostaza(), "Mostaza");
        adapter.addFrag(new AlergenicoPescado(), "Pescado");
        adapter.addFrag(new AlergenicoSoja(), "Soja");
        adapter.addFrag(new AlergenicoOtros(), "Otros");
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
