package com.example.juansevillano.testingproductos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

public class VentanaRegistroProducto extends AppCompatActivity  {

    private android.app.FragmentManager manager;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    public ViewPager viewPager;
    public List<Fragment> fragments = new ArrayList<Fragment>();

    //Se crea un ArrayList de tipo Ingrediente para cada uno de los alergenicos.
    public ArrayList<Ingrediente> list_ingredientes_altramuz = new ArrayList<Ingrediente>();
    public ArrayList<Ingrediente> list_ingredientes_apio= new ArrayList<Ingrediente>();
    public ArrayList<Ingrediente> list_ingredientes_azufreysulfito = new ArrayList<Ingrediente>();
    public ArrayList<Ingrediente> list_ingredientes_cacahuete = new ArrayList<Ingrediente>();
    public ArrayList<Ingrediente> list_ingredientes_crustaceo = new ArrayList<Ingrediente>();
    public ArrayList<Ingrediente> list_ingredientes_frutoscascara = new ArrayList<Ingrediente>();
    public ArrayList<Ingrediente> list_ingredientes_gluten = new ArrayList<Ingrediente>();
    public ArrayList<Ingrediente> list_ingredientes_sesamo = new ArrayList<Ingrediente>();
    public ArrayList<Ingrediente> list_ingredientes_huevo = new ArrayList<Ingrediente>();
    public ArrayList<Ingrediente> list_ingredientes_lacteo = new ArrayList<Ingrediente>();
    public ArrayList<Ingrediente> list_ingredientes_molusco = new ArrayList<Ingrediente>();
    public ArrayList<Ingrediente> list_ingredientes_mostaza = new ArrayList<Ingrediente>();
    public ArrayList<Ingrediente> list_ingredientes_pescado = new ArrayList<Ingrediente>();
    public ArrayList<Ingrediente> list_ingredientes_soja = new ArrayList<Ingrediente>();
    //public ArrayList<Ingrediente> list_ingredientes_otros = new ArrayList<Ingrediente>();


    public ArrayList<TipoProducto> list_tipo_producto;
    public ArrayList<Empresa> list_empresa;

    public TextView Co;

    /**
     * @name private void onCreate( Bundle savedInstanceState)
     * @description Primer Método que se llama al crear la clase
     * @return void
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_registro_producto);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        manager = getFragmentManager();

        fragments.add(new RegistroProducto());
        fragments.add(new AlergenicoAltramuz());
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
        //fragments.add(new AlergenicoOtros());
        fragments.add(new FinRegistroProducto());

        //fragments.add(new FourFragment());

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        FragmentViewPagerAdapter2 adapter = new FragmentViewPagerAdapter2(this.getSupportFragmentManager(), viewPager,fragments);

        //transaction = manager.beginTransaction();

        setupTabIcons();
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    //Variable Usada para devolver el codigo de barra escaneado.
    public static String cod="";

    /**
     * @name private void onActivityResult(int requestCode, int resultCode, Intent intent)
     * @description Para recuperar la información resultante de una segunda actividad.
     * @return void
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //super.onActivityResult(requestCode, resultCode, data);

        //Se obtiene el resultado del proceso de scaneo y se parsea
        System.out.println("Prueba 10");
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanningResult != null) {
            System.out.println("Prueba 11");
            //Quiere decir que se obtuvo resultado por lo tanto:
            //Desplegamos en pantalla el contenido del código de barra scaneado
            System.out.println(scanningResult.getContents());
            if(scanningResult.getContents()!=null)
            {
                String scanContent = scanningResult.getContents();
                System.out.println(scanContent.toString());
                //Se Instancia el Campo de Texto para el contenido  del código de barra
                cod=scanContent.toString();
                Toast toast = Toast.makeText(getApplicationContext(),
                        scanContent.toString(), Toast.LENGTH_SHORT);
                toast.show();
                System.out.println("Prueba 12");

            }

        }else{
            //Quiere decir que NO se obtuvo resultado
            Toast toast = Toast.makeText(getApplicationContext(),
                    "!No se ha recibido datos del escaneo¡", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    /**
     * @name private void setupTabIcons()
     * @description Método para asignar los iconos a cada fragments
     * @return void
     */
    private void setupTabIcons() {

        tabLayout.getTabAt(0).setIcon(R.mipmap.registroproducto);
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
        //tabLayout.getTabAt(15).setIcon(R.mipmap.otrosmini);
        tabLayout.getTabAt(15).setIcon(R.mipmap.finregistroico);
    }

    /**
     * @name private void setupViewPager(ViewPager viewPager)
     * @description Método para añadir los fragments a la view.
     * @return void
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new RegistroProducto(), "Registro");
        adapter.addFrag(new AlergenicoAltramuz(), "Altamuz");
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
        //adapter.addFrag(new AlergenicoOtros(), "Otros");
        adapter.addFrag(new FinRegistroProducto(), "Fin Registro");
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
     * @name public void onSupportNavigateUp ()
     * @description Si hacemos clic en el boton hacia atras saldremos de la aplicacion
     * @return void
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
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

                    alertDialog.setMessage("¿Desea Salir de la Inserción del Producto Nuevo? \nSi Sales de la Aplicación no se Procedera al Registro del Producto.");
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
                            Toast.makeText(getBaseContext(), "Saliendo de la Inserción del Producto Nuevo....", Toast.LENGTH_SHORT).show();

                            //db.close();

                            //Esperamos 50 milisegundos
                            SystemClock.sleep(500);
                            Salir();
                        }
                    });

                    alertDialog.show();

                //}
            //}

            //Cerramos la Base de Datos
            //db.close();
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
        Intent intent = new Intent(this, VentanaPrincipal.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    /**
     * @name public void ProductoYaRegistrado(final String codigo_barra, final String id_producto)
     * @description Método para cormprobar si el producto ya existe
     * @return void
     */
    public void ProductoYaRegistrado(final String codigo_barra, final String id_producto)
    {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(VentanaRegistroProducto.this);

        alertDialog.setMessage("!Ya Existe un Producto con el Codigo de Barra que as insertado¡. ¿Desea Actualizar dicho Producto?");
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
                Intent intent = new Intent(VentanaRegistroProducto.this, VentanaActualizarProducto.class);
                intent.putExtra("codigo_barra",codigo_barra.toString());
                intent.putExtra("id_producto",id_producto.toString());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        alertDialog.show();
    }

    /**
     * @name public void Restablecer()
     * @description Funcion si hacemos click en Restablecer
     * @return void
     */
    public void Restablecer()
    {
        Intent intent = new Intent(VentanaRegistroProducto.this, VentanaRegistroProducto.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
