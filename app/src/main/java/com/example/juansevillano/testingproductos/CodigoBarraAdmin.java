package com.example.juansevillano.testingproductos;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.otto.Subscribe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Juan Sevillano on 15/07/2017.
 */
public class CodigoBarraAdmin extends AppCompatActivity {

    protected Activity activity;
    protected ArrayList<Ingrediente> items;
    private TextView codbarra;
    private TextView producto;
    private TextView textoveracidad;
    private ImageView imagenveracidad;
    private ImageButton buttonescnaer;

    ItemProductoAdapter adapter;

    //Definimos una Variable de tipo Cursor
    Cursor res;

    //Definimos una variable de tipo SQLiteDatabase
    SQLiteDatabase db;

    //Id del producto
    String id_producto;
    //Nombre del Producto
    String nombre_producto;

    //Codigo de barra del producto
    String codigobarra;

    //Ingredientes que forman parte de un producto
    String [] id_ingrediente_producto;

    //Ingredientes que afectan a la dieta del usuario
    String[] items_ingredientes;

    //Transtorno de cada ingrediente
    String[] transtornos_ingrediente;

    // IP de mi Url
    String IP = "http://tfgalimentos.16mb.com";

    //Se crear un objetio de tipo ObtenerWebService
    Obtener_Producto_CB hiloconexion1;

    //Se crear un objetio de tipo ObtenerWebService
    ObtenerIngredientes_Producto_Ingrediente hiloconexion2;

    //Se crear un objetio de tipo ObtenerWebService
    Obtener_Ingrediente_noOK hiloconexion3;

    //Se crear un objetio de tipo ObtenerWebService
    Obtener_Ingrediente_siOK hiloconexion4;

    //Se crear un objetio de tipo ObtenerWebService
    Obtener_Transtorno hiloconexion5;

    //Se crear un objetio de tipo ObtenerWebService
    ObtenerIngredientes_Usuario_Ingrediente hiloconexion6;

    //Si el ingrediente existe en el producto
    static Boolean existe=false;

    //Para color que no es valido para su dieta
    static Boolean colocarNOok=false;

    //Si no existe el producto
    static Boolean NoExisteProducto=false;

    static int num=0;

    //Lista de Ingredientes
    ArrayList<Ingrediente> ingrediente;

    //Id_Asociado a un usuario
    String id_asociado;

    //ID_Usuario elegido
    private String id_usuario;


    public int getCount() {
        return items.size();
    }

    public Object getItem(int position) {
        return items.get(position);
    }

    public String getItemId(int position) {
        return items.get(position).getid_ingrediente();
    }

    /**
     * @name private void onCreate( Bundle savedInstanceState)
     * @description Primer Método que se llama al crear la clase
     * @return void
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_codigo_barra_admin);

        //ingrediente = new ArrayList<Ingrediente>();

        //Se crea un objetio de tipo ListView
        //ListView lstListaEscaner = (ListView) findViewById(R.id.lstListaEscaner);

        //adapter = new ItemProductoAdapter(this, ingrediente);

        //Se Instancia el Campo de Texto para el contenido  del código de barra
        codbarra = (TextView)findViewById(R.id.scan_contentAdmin);

        //Se Instancia el Campo de Texto para mostrar los datos del producto
        producto = (TextView)findViewById(R.id.datos_productoAdmin);

        //Se Instancia el Campo de Texto para el texto de la veracidad
        textoveracidad = (TextView)findViewById(R.id.texto_veracidadAdmin);

        buttonescnaer= (ImageButton) findViewById(R.id.escanearnuevoAdmin);

        //Se Insta el Campo de la imagen para el conteido si es apto o no.
        imagenveracidad= (ImageView)findViewById(R.id.imageAptoAdmin);

        //Se instancia un objeto de la clase IntentIntegrator
        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
        //Se procede con el proceso de scaneo
        scanIntegrator.initiateScan();

        //lstListaEscaner.setAdapter(adapter);

        //ID Usuario Elegido.
        id_asociado=getIntent().getStringExtra("id_asociado");

        buttonescnaer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Se instancia un objeto de la clase IntentIntegrator
                IntentIntegrator scanIntegrator = new IntentIntegrator(CodigoBarraAdmin.this);
                //Se procede con el proceso de scaneo
                scanIntegrator.initiateScan();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        ActivityResultBus.getInstance().register(mActivityResultSubscriber);
    }

    @Override
    public void onStop() {
        super.onStop();
        ActivityResultBus.getInstance().unregister(mActivityResultSubscriber);
    }

    private Object mActivityResultSubscriber = new Object() {
        @Subscribe
        public void onActivityResultReceived(ActivityResultEvent event) {
            System.out.println("Prueba 6");
            int requestCode = event.getRequestCode();
            System.out.println(event.getRequestCode());
            int resultCode = event.getResultCode();
            Intent data = event.getData();
            onActivityResult(requestCode, resultCode, data);
        }
    };

    /**
     * @name private void onActivityResult(int requestCode, int resultCode, Intent intent)
     * @description Para recuperar la información resultante de una segunda actividad.
     * @return void
     */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        textoveracidad.setText("SI es APTO para su dieta.");
        textoveracidad.setTextColor(textoveracidad.getContext().getResources().getColor(R.color.VVIVO));

        ingrediente = new ArrayList<Ingrediente>();

        //Se crea un objetio de tipo ListView
        ListView lstListaEscaner = (ListView) findViewById(R.id.lstListaEscanerAdmin);

        adapter = new ItemProductoAdapter(this, ingrediente);

        lstListaEscaner.setAdapter(adapter);

        //Se obtiene el resultado del proceso de scaneo y se parsea
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {
            System.out.println("Prueba 11");
            //Quiere decir que se obtuvo resultado por lo tanto:
            //Desplegamos en pantalla el contenido del código de barra scaneado
            System.out.println(scanningResult.getContents());
            if(scanningResult.getContents()!=null) {
                //Quiere decir que se obtuvo resultado pro lo tanto:
                //Desplegamos en pantalla el contenido del código de barra scaneado
                String scanContent = scanningResult.getContents();
                codbarra.setText("C. Barras: " + scanContent.toString());
                codigobarra=scanContent.toString();

                System.out.println("Prueba:  "+id_asociado);

                String GET = IP + "/ObtenerIngredientes_Usuario_Ingrediente.php?id_asociado="+id_asociado.toString();
                hiloconexion6 = new ObtenerIngredientes_Usuario_Ingrediente();
                hiloconexion6.execute(GET,"1");


                //////////////////////////////////////

                System.out.println("Prueba 12");


                ////////////////////////////
            }
            else
            {
                //Quiere decir que NO se obtuvo resultado
                Toast toast = Toast.makeText(getApplicationContext(),
                        "¡No se ha recibido datos del scaneo!", Toast.LENGTH_SHORT);
                toast.show();
                Intent ListSong = new Intent(getApplicationContext(), VentanaPrincipal.class);
                startActivity(ListSong);
                finish();
            }

        }else{
            //Quiere decir que NO se obtuvo resultado
            Toast toast = Toast.makeText(getApplicationContext(),
                    "¡No se ha recibido datos del scaneo!", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    /**
     * @name public boolean onOptionsItemSelected(MenuItem item)
     * @description Medodo para comprobar si se ha pulsado alguna de las opciones item.
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //hago un case por si en un futuro agrego mas opciones
                Log.i("ActionBar", "Atrás!");
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Clase que se encarga de realizar la ejecución de la consulta sql mediente un servicio web alojado en un hosting
     * mediente archivos php.
     */
    public class ObtenerIngredientes_Usuario_Ingrediente extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {

            String cadena = params[0];
            URL url = null; // Url de donde queremos obtener información
            String devuelve ="";


            if(params[1]=="1") //GET
            {
                try {
                    url = new URL(cadena);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();  //Abrir la conexión
                    /*connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                            " (Linux; Android 1.5; es-ES) Ejemplo HTTP");*/
                    //connection.setHeader("content-type", "application/json");

                    int respuesta = connection.getResponseCode();
                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK){


                        InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada

                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader

                        // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                        // que tranformar el BufferedReader a String. Esto lo hago a traves de un
                        // StringBuilder.

                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);        // Paso toda la entrada al StringBuilder
                        }

                        //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                        JSONArray respuestaJSON = new JSONArray(result.toString()+"]");   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                        //Accedemos al vector de resultados
                        JSONObject objetoJSON= respuestaJSON.getJSONObject(0);

                        //Obtenemos el estado que es el nombre del campo en el JSON donde se asigna si tiene valor o no el archivo JSON
                        String resultJSON = objetoJSON.getString("estado");
                        System.out.println("Prueba 1 " + resultJSON.toString());

                        if (resultJSON.equals("1")){      // Ya existe el usuario por lo que no lo volvemos a crear.
                            devuelve = "Si hay Ingrediente";
                            System.out.println("Prueba 2");
                            //Obtenemos los ingrediente que vamos a mostrar en la aplicación
                            JSONArray pruebaJSON = objetoJSON.getJSONArray("UsuarioIngrediente");

                            items_ingredientes=new String[pruebaJSON.length()];
                            for(int i=0;i<pruebaJSON.length();i++)
                            {
                                items_ingredientes[i]=pruebaJSON.getJSONObject(i).getString("id_ingrediente");
                            }
                        }
                        else
                        {
                            devuelve = "No hay Ingrediente";
                        }

                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return devuelve;

            }
            return null;
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onPostExecute(String s) {
            //resultado.setText(s);
            super.onPostExecute(s);
            Obtener_Producto();
            //Se notifica al adaptador de que el ArrayList que tiene asociado ha sufrido cambios (forzando asi a ir al metodo getView())
            //adaptador.notifyDataSetChanged();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    /**
     * @name public void Obtener_Producto()
     * @description Metodo para obtener el Id_Producto del Producto con Codigo de Barra dado.
     * @return void
     */
    public void Obtener_Producto()
    {
        // Rutas de los Web Services
        //Obtenemos los productos segun su codigo de barras
        final String GET = IP + "/Obtener_Producto_CB.php?codigo_barra="+codigobarra.toString();
        hiloconexion1 = new Obtener_Producto_CB();
        hiloconexion1.execute(GET,"1");
    }


    /**
     * Clase que se encarga de realizar la ejecución de la consulta sql mediente un servicio web alojado en un hosting
     * mediente archivos php.
     */
    public class Obtener_Producto_CB extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {

            String cadena = params[0];
            URL url = null; // Url de donde queremos obtener información
            String devuelve ="";


            if(params[1]=="1") //GET
            {
                try {
                    url = new URL(cadena);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();  //Abrir la conexión
                    /*connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                            " (Linux; Android 1.5; es-ES) Ejemplo HTTP");*/
                    //connection.setHeader("content-type", "application/json");

                    int respuesta = connection.getResponseCode();
                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK){


                        InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada

                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader

                        // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                        // que tranformar el BufferedReader a String. Esto lo hago a traves de un
                        // StringBuilder.

                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);        // Paso toda la entrada al StringBuilder
                        }

                        //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                        JSONArray respuestaJSON = new JSONArray(result.toString()+"]");   //Creo un JSONObject a partir del StringBuilder pasado a cadena

                        //Accedemos al vector de resultados
                        JSONObject objetoJSON= respuestaJSON.getJSONObject(0);

                        //Obtenemos el estado que es el nombre del campo en el JSON donde se asigna si tiene valor o no el archivo JSON
                        String resultJSON = objetoJSON.getString("estado");
                        System.out.println("Prueba 1: " + resultJSON);

                        if (resultJSON.equals("1")) {      // hay un producto que acabamos de insertar
                            devuelve = "Si hay Producto Registrado";
                            //Obtenemos el id del producto que acabamos de insertar
                            id_producto = objetoJSON.getJSONObject("Producto").getString("id_producto");
                            nombre_producto=objetoJSON.getJSONObject("Producto").getString("nombre_producto");
                            NoExisteProducto=false;
                        } else {
                            devuelve = "No hay Producto Registrado";
                            NoExisteProducto=true;
                        }

                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return devuelve;

            }
            return null;
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onPostExecute(String s) {
            //resultado.setText(s);
            super.onPostExecute(s);
            ObtenerIngredienteProducto();
            //Se notifica al adaptador de que el ArrayList que tiene asociado ha sufrido cambios (forzando asi a ir al metodo getView())
            //adaptador.notifyDataSetChanged();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    /**
     * @name private void ObtenerIngredienteProducto()
     * @description Metodo para obtener los ingredientes del producto
     * @return void
     */
    private void ObtenerIngredienteProducto()
    {
        if(NoExisteProducto.equals(false))
        {
            producto.setText("Nombre del Producto: "+nombre_producto.toString());
            System.out.println("Id del producto: "+id_producto);
            // Rutas de los Web Services
            //Obtenemos los ingredientes que forman parte de dicho producto
            final String GET = IP + "/ObtenerIngredientes_Producto_Ingrediente.php?id_producto="+id_producto;
            hiloconexion2 = new ObtenerIngredientes_Producto_Ingrediente();
            hiloconexion2.execute(GET, "1");
        }
        else
        {
            id_ingrediente_producto=null;
            items_ingredientes=null;
            transtornos_ingrediente=null;
            textoveracidad.setText("");
            imagenveracidad.setVisibility(View.INVISIBLE);
            producto.setText("Nombre del Producto: ");

            android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);

            alertDialog.setMessage("El Producto No Existe ¿Desea Registrar el Producto? Confirmar Para Iniciar como Usuario Registrado mediante Google, Cancelar Salir.");
            alertDialog.setTitle("Importante");
            alertDialog.setIcon(R.mipmap.atencion_opt);
            alertDialog.setCancelable(false);
            alertDialog.setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //No Realizamos ninguna Acceion
                    finish();
                    Intent intent = new Intent(CodigoBarraAdmin.this, VentanaOpcionesEscaner.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }
            });
            alertDialog.setNegativeButton("Confirmar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Toast.makeText(getBaseContext(), "Redireccionando al Menu de Elección de Entrada a la Aplicación....", Toast.LENGTH_SHORT).show();
                    //Esperamos 50 milisegundos
                    SystemClock.sleep(500);

                    finish();
                    Intent intent = new Intent(CodigoBarraAdmin.this, EntrarCon.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });

            alertDialog.show();
        }



    }

    /**
     * Clase que se encarga de realizar la ejecución de la consulta sql mediente un servicio web alojado en un hosting
     * mediente archivos php.
     */
    public class ObtenerIngredientes_Producto_Ingrediente extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {

            String cadena = params[0];
            URL url = null; // Url de donde queremos obtener información
            String devuelve ="";


            if(params[1]=="1") //GET
            {
                try {
                    url = new URL(cadena);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();  //Abrir la conexión
                    /*connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                            " (Linux; Android 1.5; es-ES) Ejemplo HTTP");*/
                    //connection.setHeader("content-type", "application/json");

                    int respuesta = connection.getResponseCode();
                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK){


                        InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada

                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader

                        // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                        // que tranformar el BufferedReader a String. Esto lo hago a traves de un
                        // StringBuilder.

                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);        // Paso toda la entrada al StringBuilder
                        }

                        //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                        JSONArray respuestaJSON = new JSONArray("["+result.toString()+"]");   //Creo un JSONObject a partir del StringBuilder pasado a cadena

                        //Accedemos al vector de resultados
                        JSONObject objetoJSON= respuestaJSON.getJSONObject(0);

                        //Obtenemos el estado que es el nombre del campo en el JSON donde se asigna si tiene valor o no el archivo JSON
                        String resultJSON = objetoJSON.getString("estado");
                        System.out.println("Prueba 1 " + resultJSON);

                        if (resultJSON.equals("1")) {      // hay un producto que acabamos de insertar

                            //Obtenemos los ingrediente que vamos a mostrar en la aplicación
                            JSONArray pruebaJSON = objetoJSON.getJSONArray("ProductoIngrediente");

                            id_ingrediente_producto= new String[pruebaJSON.length()];

                            for(int i=0;i<pruebaJSON.length();i++)
                            {
                                //Obtenemos el id del producto que acabamos de insertar
                                id_ingrediente_producto[i] = pruebaJSON.getJSONObject(i).getString("id_ingrediente");
                            }

                            System.out.println("TAMAÑO: "+id_ingrediente_producto.length);

                            ObtenerTranstornoIngrediente(id_ingrediente_producto);

                        } else {
                            devuelve = "No hay Producto Registrado";
                        }

                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return devuelve;

            }
            return null;
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onPostExecute(String s) {
            //resultado.setText(s);
            super.onPostExecute(s);
            //Se notifica al adaptador de que el ArrayList que tiene asociado ha sufrido cambios (forzando asi a ir al metodo getView())
            //adaptador.notifyDataSetChanged();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    /**
     * @name private void ObtenerTranstornoIngrediente(String[] id_ingrediente_producto)
     * @description Metodo para obtener los transtornos ingredientes
     * @return void
     */
    private void ObtenerTranstornoIngrediente(String[] id_ingrediente_producto)
    {
        transtornos_ingrediente= new String[id_ingrediente_producto.length];

        for(int i=0;i<id_ingrediente_producto.length;i++)
        {
            // Rutas de los Web Services
            //Obtenemos los ingredientes que forman parte de dicho producto
            final String GET = IP + "/Obtener_Transtorno.php?id_ingrediente=" + id_ingrediente_producto[i].toString();
            hiloconexion5 = new Obtener_Transtorno();
            hiloconexion5.execute(GET, "1");
        }

        Comparar(id_ingrediente_producto);

    }

    /**
     * @name private void Comparar(String[] id_ingrediente_producto)
     * @description Metodo para comparar las lista de transtornos del usuario y la lista de ingredientes del producto
     * @return void
     */
    private void Comparar(String[] id_ingrediente_producto)
    {

        this.num=0;

        for(int i=0;i<id_ingrediente_producto.length;i++)
        {
            existe=false;
            for(int j=0;j<items_ingredientes.length;j++)
            {

                if(items_ingredientes[j].equals(id_ingrediente_producto[i]))
                {
                    existe=true;
                    colocarNOok=true;

                    //Obtenemos los productos segun su codigo de barras
                    final String GET = IP + "/Obtener_Ingrediente.php?id_ingrediente="+id_ingrediente_producto[i].toString();
                    hiloconexion3 = new Obtener_Ingrediente_noOK();
                    hiloconexion3.execute(GET,"1",String.valueOf(i));
                    j=items_ingredientes.length;
                }
            }

            if(existe.equals(false))
            {
                //Obtenemos los productos segun su codigo de barras
                final String GET = IP + "/Obtener_Ingrediente.php?id_ingrediente="+id_ingrediente_producto[i].toString();
                hiloconexion4 = new Obtener_Ingrediente_siOK();
                hiloconexion4.execute(GET,"1",String.valueOf(i));
            }
            else
            {
                existe=false;
            }
            // Rutas de los Web Services
            //Obtenemos los productos segun su codigo de barras
            /*final String GET = IP + "/Obtener_Ingrediente.php?id_ingrediente="+id_ingrediente_producto[i].toString();
            hiloconexion3 = new ObtenerWebService3();
            hiloconexion3.execute(GET,"1");*/
        }

    }

    /**
     * @name private void colocar()
     * @description Metodo para colocar que no es apto para su dieta
     * @return void
     */
    private void colocar()
    {
        //Colocamos que no es apto para su dieta
        if(colocarNOok.equals(true))
        {
            Drawable drawable= this.getResources().getDrawable(R.drawable.ic_action_nook);
            imagenveracidad.setImageDrawable(drawable);
            textoveracidad.setText("NO es APTO para su dieta.");
            textoveracidad.setTextColor(textoveracidad.getContext().getResources().getColor(R.color.ROJO));
        }

    }

    /**
     * Clase que se encarga de realizar la ejecución de la consulta sql mediente un servicio web alojado en un hosting
     * mediente archivos php.
     */
    public class Obtener_Ingrediente_noOK extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {

            String cadena = params[0];
            URL url = null; // Url de donde queremos obtener información
            String devuelve ="";


            if(params[1]=="1") //GET
            {
                try {
                    url = new URL(cadena);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();  //Abrir la conexión
                    /*connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                            " (Linux; Android 1.5; es-ES) Ejemplo HTTP");*/
                    //connection.setHeader("content-type", "application/json");

                    int respuesta = connection.getResponseCode();
                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK){


                        InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada

                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader

                        // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                        // que tranformar el BufferedReader a String. Esto lo hago a traves de un
                        // StringBuilder.

                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);        // Paso toda la entrada al StringBuilder
                        }

                        //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                        JSONArray respuestaJSON = new JSONArray(result.toString()+"]");   //Creo un JSONObject a partir del StringBuilder pasado a cadena

                        //Accedemos al vector de resultados
                        JSONObject objetoJSON= respuestaJSON.getJSONObject(0);

                        //Obtenemos el estado que es el nombre del campo en el JSON donde se asigna si tiene valor o no el archivo JSON
                        String resultJSON = objetoJSON.getString("estado");
                        System.out.println("Prueba 1 " + resultJSON);

                        if (resultJSON.equals("1")) {      // hay un producto que acabamos de insertar

                            //Obtenemos los ingrediente que vamos a mostrar en la aplicación
                            //JSONArray pruebaJSON = objetoJSON.getJSONArray("Ingrediente");

                            String nombre_ingrediente;
                            String id_ingrediente;
                            //Obtenemos el id del producto que acabamos de insertar

                                System.out.println("PRUEBA 2: "+existe);
                                System.out.println("PRUEBA");
                                id_ingrediente = objetoJSON.getJSONObject("Ingrediente").getString("id_ingrediente");
                                nombre_ingrediente = objetoJSON.getJSONObject("Ingrediente").getString("nombre_ingrediente");

                                ingrediente.add(new Ingrediente(id_ingrediente,nombre_ingrediente, transtornos_ingrediente[Integer.valueOf(params[2])],
                                        "drawable/ic_verificado_x"));
                            System.out.println("PRUEBA TTTT 11111------------------- "+transtornos_ingrediente[Integer.valueOf(params[2])].toString());
                                num++;


                        } else {
                            devuelve = "No hay Producto Registrado";
                        }

                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return devuelve;

            }
            return null;
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onPostExecute(String s) {
            //resultado.setText(s);
            super.onPostExecute(s);
            //Se notifica al adaptador de que el ArrayList que tiene asociado ha sufrido cambios (forzando asi a ir al metodo getView())
            adapter.notifyDataSetChanged();
            colocar();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    /**
     * Clase que se encarga de realizar la ejecución de la consulta sql mediente un servicio web alojado en un hosting
     * mediente archivos php.
     */
    public class Obtener_Ingrediente_siOK extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {

            String cadena = params[0];
            URL url = null; // Url de donde queremos obtener información
            String devuelve ="";


            if(params[1]=="1") //GET
            {
                try {
                    url = new URL(cadena);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();  //Abrir la conexión
                    /*connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                            " (Linux; Android 1.5; es-ES) Ejemplo HTTP");*/
                    //connection.setHeader("content-type", "application/json");

                    int respuesta = connection.getResponseCode();
                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK){


                        InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada

                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader

                        // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                        // que tranformar el BufferedReader a String. Esto lo hago a traves de un
                        // StringBuilder.

                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);        // Paso toda la entrada al StringBuilder
                        }

                        //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                        JSONArray respuestaJSON = new JSONArray(result.toString()+"]");   //Creo un JSONObject a partir del StringBuilder pasado a cadena

                        //Accedemos al vector de resultados
                        JSONObject objetoJSON= respuestaJSON.getJSONObject(0);

                        //Obtenemos el estado que es el nombre del campo en el JSON donde se asigna si tiene valor o no el archivo JSON
                        String resultJSON = objetoJSON.getString("estado");
                        System.out.println("Prueba 1 " + resultJSON);

                        if (resultJSON.equals("1")) {      // hay un producto que acabamos de insertar

                            //Obtenemos los ingrediente que vamos a mostrar en la aplicación
                            //JSONArray pruebaJSON = objetoJSON.getJSONArray("Ingrediente");

                            String nombre_ingrediente;
                            String id_ingrediente;
                            //Obtenemos el id del producto que acabamos de insertar
                            System.out.println("PRUEBA 2: "+existe);
                                System.out.println("prueba");
                                id_ingrediente = objetoJSON.getJSONObject("Ingrediente").getString("id_ingrediente");
                                nombre_ingrediente = objetoJSON.getJSONObject("Ingrediente").getString("nombre_ingrediente");
                                ingrediente.add(new Ingrediente(id_ingrediente,nombre_ingrediente, transtornos_ingrediente[Integer.valueOf(params[2])],
                                        "drawable/ic_verificado_v"));
                                System.out.println("PRUEBA TTTT 2222------------------- "+transtornos_ingrediente[Integer.valueOf(params[2])].toString());
                                num++;


                        } else {
                            devuelve = "No hay Producto Registrado";
                        }

                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return devuelve;

            }
            return null;
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onPostExecute(String s) {
            //resultado.setText(s);
            super.onPostExecute(s);
            //Se notifica al adaptador de que el ArrayList que tiene asociado ha sufrido cambios (forzando asi a ir al metodo getView())
            adapter.notifyDataSetChanged();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


    /**
     * Clase que se encarga de realizar la ejecución de la consulta sql mediente un servicio web alojado en un hosting
     * mediente archivos php.
     */
    public class Obtener_Transtorno extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {

            String cadena = params[0];
            URL url = null; // Url de donde queremos obtener información
            String devuelve ="";


            if(params[1]=="1") //GET
            {
                try {
                    url = new URL(cadena);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();  //Abrir la conexión
                    /*connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                            " (Linux; Android 1.5; es-ES) Ejemplo HTTP");*/
                    //connection.setHeader("content-type", "application/json");

                    int respuesta = connection.getResponseCode();
                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK){


                        InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada

                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader

                        // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                        // que tranformar el BufferedReader a String. Esto lo hago a traves de un
                        // StringBuilder.

                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);        // Paso toda la entrada al StringBuilder
                        }

                        //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                        JSONArray respuestaJSON = new JSONArray(result.toString()+"]");   //Creo un JSONObject a partir del StringBuilder pasado a cadena

                        //Accedemos al vector de resultados
                        JSONObject objetoJSON= respuestaJSON.getJSONObject(0);

                        //Obtenemos el estado que es el nombre del campo en el JSON donde se asigna si tiene valor o no el archivo JSON
                        String resultJSON = objetoJSON.getString("estado");
                        System.out.println("Prueba 1 " + resultJSON);

                        if (resultJSON.equals("1")) {      // hay un producto que acabamos de insertar

                                //Obtenemos el id del producto que acabamos de insertar
                                transtornos_ingrediente[num]= objetoJSON.getJSONObject("Transtorno").getString("transtorno");
                                System.out.println("PRUEBA TTTT ------------------- "+transtornos_ingrediente[num].toString());
                                num++;

                            }

                            System.out.println("TAMAÑO: "+id_ingrediente_producto.length);

                        } else {
                            devuelve = "No hay Producto Registrado";
                        }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return devuelve;

            }
            return null;
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onPostExecute(String s) {
            //resultado.setText(s);
            super.onPostExecute(s);
            //Se notifica al adaptador de que el ArrayList que tiene asociado ha sufrido cambios (forzando asi a ir al metodo getView())
            //adaptador.notifyDataSetChanged();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
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
            Salir();
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
}
