package com.example.juansevillano.testingproductos;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class VentanaBuscarProducto extends AppCompatActivity  {

    protected Activity activity;
    protected ArrayList<Ingrediente> items;
    private TextView codbarra;
    private TextView producto;
    private TextView tipoproducto;
    private TextView empresa;

    ItemProductoAdapter adapter;

    //Id del producto
    String id_producto;
    //Nombre del Producto
    String nombre_producto;

    //Codigo de barra del producto
    static String codigobarra="";

    //Ingredientes que forman parte de un producto
    String [] id_ingrediente_producto;

    //Numero de usuarios que ha verificado el producto
    String [] producto_verificado;

    String [] num_verificado;

    //Ingredientes que afectan a la dieta del usuario
    String[] items_ingredientes;

    //Trastorno de cada ingrediente
    String[] trastornos_ingrediente;

    // IP de mi Url
    String IP = "http://tfgalimentos.16mb.com";

    //Se crear un objetio de tipo ObtenerWebService
    Obtener_Producto_CB hiloconexion1;

    //Se crear un objetio de tipo ObtenerWebService
    ObtenerIngredientes_Producto_Ingrediente hiloconexion2;

    //Se crear un objetio de tipo ObtenerWebService
    Obtener_Ingrediente hiloconexion3;

    //Se crear un objetio de tipo ObtenerWebService
    Obtener_Trastorno hiloconexion5;

    //Se crear un objetio de tipo ObtenerWebService
    Obtener_tipo_producto hiloconexion7;

    //Se crear un objetio de tipo ObtenerWebService
    Obtener_empresa hiloconexion8;

    //Si el ingrediente existe en el producto
    static Boolean existe=false;

    //Para color que no es valido para su dieta
    static Boolean colocarNOok=false;

    //Si no existe el producto
    static Boolean NoExisteProducto=false;

    int num=0;
    int num1=0;

    //Lista de Ingredientes
    ArrayList<Ingrediente> ingrediente;

    //Id_Asociado a un usuario
    static String id_asociado="";

    //ID_Usuario elegido
    private String id_usuario;

    //id_tipo_producto de un producto
    static String nombre_tipo_producto="";
    String id_tipo_producto="";

    //id_empresa de una empresa
    static String nombre_empresa="";
    String id_empresa="";

    //Clave Encriptada
    Encriptado encriptado= new Encriptado();

    //URL para el listado del tipo de productos
    public String URL_LISTA_PRODUCTO = "http://tfgalimentos.16mb.com/Todos_Tipo_Producto.php?clave="+encriptado.md5();

    //URL para el listado de las empresas
    public String URL_LISTA_EMPRESAS = "http://tfgalimentos.16mb.com/Todos_Empresa.php?clave="+encriptado.md5();

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

        setContentView(R.layout.activity_ventana_buscar_producto);


        //ingrediente = new ArrayList<Ingrediente>();

        //Se crea un objetio de tipo ListView
        //ListView lstListaEscaner = (ListView) findViewById(R.id.lstListaEscaner);

        //adapter = new ItemProductoAdapter(this, ingrediente);

        //Se Instancia el Campo de Texto para el contenido  del código de barra
        codbarra = (TextView)findViewById(R.id.scan_contentbuscar);

        //Se Instancia el Campo de Texto para mostrar los datos del producto
        producto = (TextView)findViewById(R.id.datos_nombreproducto);

        //Se Instancia el Campo de Texto para el texto del tipo de producto
        tipoproducto = (TextView)findViewById(R.id.datos_tipoproducto);

        //Se Instancia el Campo de Texto para el texto de la empresa
        empresa = (TextView)findViewById(R.id.datos_empresa);

        codigobarra = getIntent().getStringExtra("codigobarra");

        if(codigobarra==""||codigobarra==null)
        {
            Intent ListSong = new Intent(this, CamaraLector.class);
            ListSong.putExtra("ventana", "VentanaBuscarProducto");
            ListSong.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(ListSong);
            finish();
        }
        else
        {
            //Codigo de Barra Escaneado.
            codigobarra = getIntent().getStringExtra("codigobarra");
            codbarra.setText("C. Barras: " + codigobarra.toString());

            System.out.println(codigobarra);

            ingrediente = new ArrayList<Ingrediente>();

            //Se crea un objetio de tipo ListView
            ListView lstListaEscaner = (ListView) findViewById(R.id.lstListaEscanerbuscar);

            adapter = new ItemProductoAdapter(this, ingrediente);

            lstListaEscaner.setAdapter(adapter);

            // Rutas de los Web Services
            //Obtenemos los productos segun su codigo de barras
            final String GET = IP + "/Obtener_Producto_CB.php?codigo_barra="+codigobarra.toString()+"&clave="+encriptado.md5();
            hiloconexion1 = new Obtener_Producto_CB();
            hiloconexion1.execute(GET,"1");

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
    public class Obtener_tipo_producto extends AsyncTask<String,Void,String> {

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
                        JSONArray respuestaJSON = new JSONArray("["+result.toString()+"]");  //Creo un JSONObject a partir del StringBuilder pasado a cadena

                        //Accedemos al vector de resultados
                        JSONObject objetoJSON= respuestaJSON.getJSONObject(0);

                        //Obtenemos el estado que es el nombre del campo en el JSON donde se asigna si tiene valor o no el archivo JSON
                        String resultJSON = objetoJSON.getString("estado");

                        if (resultJSON.equals("1")){      // Ya existe el usuario por lo que no lo volvemos a crear.
                            devuelve = "Si hay tipo producto";

                            nombre_tipo_producto=objetoJSON.getJSONObject("TipoProducto").getString("tipo");

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

            String GET = IP + "/Obtener_empresa.php?id_empresa=" + id_empresa.toString() + "&clave=" + encriptado.md5();
            hiloconexion8 = new Obtener_empresa();
            hiloconexion8.execute(GET, "1");
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
     * Clase que se encarga de realizar la ejecución de la consulta sql mediente un servicio web alojado en un hosting
     * mediente archivos php.
     */
    public class Obtener_empresa extends AsyncTask<String,Void,String> {

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
                        JSONArray respuestaJSON = new JSONArray("["+result.toString()+"]");    //Creo un JSONObject a partir del StringBuilder pasado a cadena

                        //Accedemos al vector de resultados
                        JSONObject objetoJSON= respuestaJSON.getJSONObject(0);

                        //Obtenemos el estado que es el nombre del campo en el JSON donde se asigna si tiene valor o no el archivo JSON
                        String resultJSON = objetoJSON.getString("estado");

                        if (resultJSON.equals("1")){      // Ya existe el usuario por lo que no lo volvemos a crear.
                            devuelve = "Si hay empresa";

                            nombre_empresa=objetoJSON.getJSONObject("Empresa").getString("nombre_empresa");
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
                        JSONArray respuestaJSON = new JSONArray("["+result.toString()+"]");   //Creo un JSONObject a partir del StringBuilder pasado a cadena

                        //Accedemos al vector de resultados
                        JSONObject objetoJSON= respuestaJSON.getJSONObject(0);

                        //Obtenemos el estado que es el nombre del campo en el JSON donde se asigna si tiene valor o no el archivo JSON
                        String resultJSON = objetoJSON.getString("estado");

                        if (resultJSON.equals("1")) {      // hay un producto que acabamos de insertar
                            devuelve = "Si hay Producto Registrado";
                            //Obtenemos el id del producto que acabamos de insertar
                            id_producto = objetoJSON.getJSONObject("Producto").getString("id_producto");
                            nombre_producto=objetoJSON.getJSONObject("Producto").getString("nombre_producto");
                            id_tipo_producto=objetoJSON.getJSONObject("Producto").getString("id_tipo_producto");
                            id_empresa=objetoJSON.getJSONObject("Producto").getString("id_empresa");

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
            String GET = IP + "/Obtener_tipo_producto.php?id_tipo_producto=" + id_tipo_producto.toString() + "&clave=" + encriptado.md5();
            hiloconexion7 = new Obtener_tipo_producto();
            hiloconexion7.execute(GET, "1");
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

            tipoproducto.setText("Tipo de Producto: "+nombre_tipo_producto.toString());
            System.out.println("Tipo Producto: "+tipoproducto);

            empresa.setText("Empresa: "+nombre_empresa.toString());
            System.out.println("Empresa: "+empresa);

            // Rutas de los Web Services
            //Obtenemos los ingredientes que forman parte de dicho producto
            final String GET = IP + "/ObtenerIngredientes_Producto_Ingrediente.php?id_producto="+id_producto+"&clave="+encriptado.md5();
            hiloconexion2 = new ObtenerIngredientes_Producto_Ingrediente();
            hiloconexion2.execute(GET, "1");
        }
        else
        {
            id_ingrediente_producto=null;
            items_ingredientes=null;
            trastornos_ingrediente=null;
            producto_verificado=null;
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
                    Intent intent = new Intent(VentanaBuscarProducto.this, VentanaPrincipalAdmin.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }
            });
            alertDialog.setNegativeButton("Confirmar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Toast.makeText(getBaseContext(), "Redireccionando al Menu de Elección de Entrada a la Aplicación....", Toast.LENGTH_SHORT).show();
                    //Esperamos 50 milisegundos
                    finish();
                    Intent intent = new Intent(VentanaBuscarProducto.this, VentanaRegistroProducto.class);
                    intent.putExtra("codigobarra", codigobarra);
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

                        if (resultJSON.equals("1")) {      // hay un producto que acabamos de insertar

                            //Obtenemos los ingrediente que vamos a mostrar en la aplicación
                            JSONArray pruebaJSON = objetoJSON.getJSONArray("ProductoIngrediente");

                            id_ingrediente_producto= new String[pruebaJSON.length()];
                            producto_verificado= new String[pruebaJSON.length()];
                            num_verificado= new String[pruebaJSON.length()];

                            for(int i=0;i<pruebaJSON.length();i++)
                            {
                                //Obtenemos el id del producto que acabamos de insertar
                                id_ingrediente_producto[i] = pruebaJSON.getJSONObject(i).getString("id_ingrediente");
                                num_verificado[i]=pruebaJSON.getJSONObject(i).getString("verificado");
                                System.out.println("Verificado:" +num_verificado[i]);
                                producto_verificado[i] = "Verificado por: " +pruebaJSON.getJSONObject(i).getString("verificado")+" Usuario";
                            }

                            ObtenerTrastornoIngrediente(id_ingrediente_producto);

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
     * @name private void ObtenerTrastornoIngrediente(String[] id_ingrediente_producto)
     * @description Metodo para obtener los trastornos ingredientes
     * @return void
     */
    private void ObtenerTrastornoIngrediente(String[] id_ingrediente_producto)
    {
        num=0;

        trastornos_ingrediente= new String[id_ingrediente_producto.length];

        for(int i=0;i<id_ingrediente_producto.length;i++)
        {
            // Rutas de los Web Services
            //Obtenemos los ingredientes que forman parte de dicho producto
            final String GET = IP + "/Obtener_Trastorno.php?id_ingrediente=" + id_ingrediente_producto[i].toString()+"&clave="+encriptado.md5();
            hiloconexion5 = new Obtener_Trastorno();
            hiloconexion5.execute(GET, "1");
        }

    }

    /**
     * @name private void Comparar(String[] id_ingrediente_producto)
     * @description Metodo para comparar las lista de trastornos del usuario y la lista de ingredientes del producto
     * @return void
     */
    private void Comparar(String[] id_ingrediente_producto)
    {

        num1=0;
        for(int i=0;i<id_ingrediente_producto.length;i++)
        {

            //Obtenemos los productos segun su codigo de barras
            final String GET = IP + "/Obtener_Ingrediente.php?id_ingrediente="+id_ingrediente_producto[i].toString()+"&clave="+encriptado.md5();
            hiloconexion3 = new Obtener_Ingrediente();
            hiloconexion3.execute(GET,"1",String.valueOf(i));

        }

    }

    /**
     * Clase que se encarga de realizar la ejecución de la consulta sql mediente un servicio web alojado en un hosting
     * mediente archivos php.
     */
    public class Obtener_Ingrediente extends AsyncTask<String,Void,String> {

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

                        if (resultJSON.equals("1")) {      // hay un producto que acabamos de insertar

                            //Obtenemos los ingrediente que vamos a mostrar en la aplicación
                            //JSONArray pruebaJSON = objetoJSON.getJSONArray("Ingrediente");

                            String nombre_ingrediente;
                            String id_ingrediente;
                            Integer verificado;
                            //Obtenemos el id del producto que acabamos de insertar

                            id_ingrediente = objetoJSON.getJSONObject("Ingrediente").getString("id_ingrediente");
                            nombre_ingrediente = objetoJSON.getJSONObject("Ingrediente").getString("nombre_ingrediente");


                            verificado=Integer.valueOf(num_verificado[num1].toString());

                            System.out.println(num1+"----Verificado -----:" +verificado);
                            if(verificado<=5)
                            {
                                ingrediente.add(new Ingrediente(id_ingrediente,nombre_ingrediente, trastornos_ingrediente[Integer.valueOf(params[2])],
                                        producto_verificado[num1],"drawable/verificacion1"));
                                num1++;
                            }
                            else
                            {
                                if(verificado>=6&&verificado<=10)
                                {
                                    ingrediente.add(new Ingrediente(id_ingrediente,nombre_ingrediente, trastornos_ingrediente[Integer.valueOf(params[2])],
                                            producto_verificado[num1],"drawable/verificacion2"));
                                    num1++;
                                }
                                else
                                {
                                    ingrediente.add(new Ingrediente(id_ingrediente,nombre_ingrediente, trastornos_ingrediente[Integer.valueOf(params[2])],
                                            producto_verificado[num1],"drawable/verificacion3"));
                                    num1++;
                                }
                            }


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
    public class Obtener_Trastorno extends AsyncTask<String,Void,String> {

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

                        if (resultJSON.equals("1")) {      // hay un producto que acabamos de insertar

                            //Obtenemos el id del producto que acabamos de insertar
                            trastornos_ingrediente[num]= objetoJSON.getJSONObject("Trastorno").getString("trastorno");
                            num++;

                        }

                        if(num==producto_verificado.length)
                        {
                            num=0;
                            Comparar(id_ingrediente_producto);
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

        codigobarra="";
        id_asociado="";
        finish();
        Intent intent = new Intent(this, VentanaPrincipalAdmin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

}
