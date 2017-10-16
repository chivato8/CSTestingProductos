package com.example.juansevillano.testingproductos;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static android.content.ContentValues.TAG;


public class FinRegistroProducto extends Fragment {

    //Obtenemos el contenido del edittext del codigo de barra
    private TextView codbarra;
    private String codigobarra;
    //Obtenemos el contenido del edittext del nombre del producto
    private EditText nomproducto;
    private String nombreproducto;
    //Obtenemos el contenido del spinner seleccionado tipo producto
    //Spinner para el tipo de productos
    private Spinner spinnertip;
    private String tipoproducto;
    //Obtenemos el contenido del spinner seleccionado empresa que lo fabrica
    //Spinner para la empresa que fabrico el producto
    private Spinner spinneremp;
    //String para la empres que fabrica el prodcuto
    private String empresafabrica;

    //String que corresponde con el id del producto
    private String id_producto;

    //Boolean que nos sirve como bandera
    private Boolean Bandera=false;

    CheckBox lMarcado;
    ListView lstLista;

    //Definimos una variable de tipo SQLiteDatabase
    //SQLiteDatabase dbUsuario;
    //SQLiteDatabase dbIngrediente;

    //Creamos un Objeto de Tipo Ingrediente
    Ingrediente ingrediente;

    RegistroUsuario registroUsuario= new RegistroUsuario();

    //Se crear un objetio de tipo ObtenerWebService
    Obtener_Producto_CB hiloconexion1;

    //Se crear un objetio de tipo ObtenerWebService
    Insertar_Producto hiloconexion2;

    //Se crear un objetio de tipo ObtenerWebService
    Insertar_Producto_Ingrediente hiloconexion3;

    // IP de mi Url
    String IP = "http://tfgalimentos.16mb.com";
    // Rutas de los Web Services
    final String INSERT = IP + "/Insertar_Producto.php";

    int pos=15;

    //Clave Encriptada
    Encriptado encriptado= new Encriptado();

    /**
     * @name public FinRegistroProducto()
     * @description Constructor Vacio
     * @return void
     */
    public FinRegistroProducto() {
        // Required empty public constructor
    }

    /**
     * @name private void onCreate( Bundle savedInstanceState)
     * @description Primer Método que se llama al crear la clase
     * @return void
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    /**
     * @name private View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
     * @description Se crea la vista de la clase
     * @return View v
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_fin_registro_producto, container, false);
    }

    /**
     * @name private void onResume()
     * @description Primer Método cuando la función se está yendo de la pantalla. Muestra en que proceso nos encontramos.
     * @return void
     */
    public void onResume()
    {

        //Si estamo en la ventana VentanaRegistroUsuario
        if (comprobarActivityALaVista(getActivity(), "com.example.juansevillano.testingproductos.VentanaRegistroUsuario") == true) {
            // Definimos un objeto del activity VentanaRegistroUsuario
            final VentanaRegistroUsuario activity= ((VentanaRegistroUsuario) getActivity());
            ViewPager viewPager= activity.viewPager;
            System.out.println(viewPager.getCurrentItem());
            if(viewPager.getCurrentItem() == pos){
                pos++;
                Toast.makeText(getActivity(), "Proceso "+pos+"/16", Toast.LENGTH_SHORT).show();
                pos--;
                //Your code here. Executed when fragment is seen by user.
            }
        }
        else
        {
            //Si estamos en la ventana VentanaRegistroProducto
            if (comprobarActivityALaVista(getActivity(), "com.example.juansevillano.testingproductos.VentanaRegistroProducto") == true) {
                // Definimos un objeto del activity VentanaRegistroProducto
                final VentanaRegistroProducto activity= ((VentanaRegistroProducto) getActivity());
                ViewPager viewPager= activity.viewPager;
                if(viewPager.getCurrentItem() == pos){
                    pos++;
                    Toast.makeText(getActivity(), "Proceso "+pos+"/16", Toast.LENGTH_SHORT).show();
                    pos--;
                    //Your code here. Executed when fragment is seen by user.
                }
            }
            else
            {
                //Si estamos en la ventana VentanaEditarUsuarioAdmin
                if (comprobarActivityALaVista(getActivity(), "com.example.juansevillano.testingproductos.VentanaEditarUsuarioAdmin") == true) {
                    // Definimos un objeto del activity VentanaEditarUsuarioAdmin
                    final VentanaEditarUsuarioAdmin activity= ((VentanaEditarUsuarioAdmin) getActivity());
                    ViewPager viewPager= activity.viewPager;
                    if(viewPager.getCurrentItem() == pos){
                        pos++;
                        Toast.makeText(getActivity(), "Proceso "+pos+"/16", Toast.LENGTH_SHORT).show();
                        pos--;
                        //Your code here. Executed when fragment is seen by user.
                    }
                }
                else
                {
                    //Si estamos en la ventana VentanaEditarUsuario
                    if (comprobarActivityALaVista(getActivity(), "com.example.juansevillano.testingproductos.VentanaEditarUsuario") == true) {
                        // Definimos un objeto del activity VentanaEditarUsuario
                        final VentanaEditarUsuario activity= ((VentanaEditarUsuario) getActivity());
                        ViewPager viewPager= activity.viewPager;
                        if(viewPager.getCurrentItem() == pos){
                            pos++;
                            Toast.makeText(getActivity(), "Proceso "+pos+"/16", Toast.LENGTH_SHORT).show();
                            pos--;
                            //Your code here. Executed when fragment is seen by user.
                        }
                    }
                    else
                    {
                        //Si estamos en la ventana VentanaActualizarProducto
                        if (comprobarActivityALaVista(getActivity(), "com.example.juansevillano.testingproductos.VentanaActualizarProducto") == true) {
                            // Definimos un objeto del activity VentanaActualizarProducto
                            final VentanaActualizarProducto activity= ((VentanaActualizarProducto) getActivity());
                            ViewPager viewPager= activity.viewPager;
                            if(viewPager.getCurrentItem() == pos){
                                pos++;
                                Toast.makeText(getActivity(), "Proceso "+pos+"/16", Toast.LENGTH_SHORT).show();
                                pos--;
                                //Your code here. Executed when fragment is seen by user.
                            }
                        }
                    }
                }
            }
        }

        super.onResume();
    }

    /**
     * @name private void onActivityCreated(Bundle savedInstanceState)
     * @description Funcion para crear la Actividad
     * @return void
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button bt1 = (Button)getView().findViewById(R.id.crear);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.crear:
                        // Definimos un objeto del activity VentanaEditarUsuario
                        final VentanaRegistroProducto activity = ((VentanaRegistroProducto) getActivity());

                        //Obtenemos el contenido del edittext del codigo de barra
                        codbarra = (TextView) activity.fragments.get(0).getView().findViewById(R.id.cbarra);
                        codigobarra = codbarra.getText().toString();
                        //Obtenemos el contenido del edittext del nombre del producto
                        nomproducto = (EditText) activity.fragments.get(0).getView().findViewById(R.id.nomproducto);
                        nombreproducto = nomproducto.getText().toString();
                        //Obtenemos el contenido del spinner del tipo de producto
                        spinnertip = (Spinner) activity.fragments.get(0).getView().findViewById(R.id.spinnertipoproducto);
                        //Obtenemos el contenido del spinner del nombre de la empresa
                        spinneremp = (Spinner) activity.fragments.get(0).getView().findViewById(R.id.spinnerempresa);


                        // Rutas de los Web Services
                        final String GET = IP + "/Obtener_Producto_CB.php?codigo_barra="+codigobarra.toString()+"&clave="+encriptado.md5();
                        hiloconexion1 = new Obtener_Producto_CB();
                        hiloconexion1.execute(GET,"1");
                }

            }
        });

        Button bt2 = (Button)getView().findViewById(R.id.restablecerproducto);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Definimos un objeto del activity VentanaEditarUsuario
                final VentanaRegistroProducto activity = ((VentanaRegistroProducto) getActivity());
                activity.Restablecer();

            }
        });
    }


    /**
     * @name public boolean comprobarActivityALaVista(Context context, String nombreClase)
     * @description Metodo para comprobar en que activity nos encontramos actualmente
     * @return boolean
     */
    public boolean comprobarActivityALaVista(Context context, String nombreClase){

        // Obtenemos nuestro manejador de activitys
        ActivityManager am = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);
        // obtenemos la informacion de la tarea que se esta ejecutando
        // actualmente
        List< ActivityManager.RunningTaskInfo > taskInfo = am.getRunningTasks(1);
        // Creamos una variable donde vamos a almacenar
        // la activity que se encuentra a la vista
        String nombreClaseActual = null;

        try{
            // Creamos la variable donde vamos a guardar el objeto
            // del que vamos a tomar el nombre
            ComponentName componentName = null;
            // si pudimos obtener la tarea actual, vamos a intentar cargar
            // nuestro objeto
            if(taskInfo != null && taskInfo.get(0) != null){
                componentName = taskInfo.get(0).topActivity;
            }
            // Si pudimos cargar nuestro objeto, vamos a obtener
            // el nombre con el que vamos a comparar
            if(componentName != null){
                nombreClaseActual = componentName.getClassName();
                System.out.println(nombreClaseActual);
            }

        }catch (NullPointerException e){

            Log.e(TAG, "Error al tomar el nombre de la clase actual " + e);
            return false;
        }

        // devolvemos el resultado de la comparacion
        return nombreClase.equals(nombreClaseActual);
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
                        System.out.println("Prueba 1 " + resultJSON);

                        System.out.println("Bandera: "+Bandera.toString());

                        if (resultJSON.equals("1") && Bandera==false){      // hay un producto anteriormente creado
                            devuelve = "Si hay Producto Registrado";
                            ProductoYARegistrado();
                        }
                        else {
                            if (resultJSON.equals("1") && Bandera==true) {      // hay un producto que acabamos de insertar
                                devuelve = "Si hay Producto Registrado";
                                //Obtenemos el id del producto que acabamos de insertar
                                id_producto = objetoJSON.getJSONObject("Producto").getString("id_producto");
                                Insertar_Alergenicos();
                            } else {
                                devuelve = "No hay Producto Registrado";
                                //Bandera =true;
                                Insertar();
                            }
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
     * Clase que se encarga de realizar la ejecución de la consulta sql mediente un servicio web alojado en un hosting
     * mediente archivos php.
     */
    public class Insertar_Producto extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {

            String cadena = params[0];
            URL url = null; // Url de donde queremos obtener información
            String devuelve ="";

            if(params[1]=="4") { //INSERT
                try {
                    HttpURLConnection urlConn;

                    DataOutputStream printout;
                    DataInputStream input;
                    url = new URL(cadena);
                    urlConn = (HttpURLConnection) url.openConnection();
                    urlConn.setDoInput(true);
                    urlConn.setDoOutput(true);
                    urlConn.setUseCaches(false);
                    urlConn.setRequestProperty("Content-Type", "application/json");
                    urlConn.setRequestProperty("Accept", "application/json");
                    urlConn.connect();
                    //Creo el Objeto JSON
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("codigo_barra", params[2]);
                    jsonParam.put("nombre_producto", params[3]);
                    jsonParam.put("id_tipo_producto", Integer.valueOf(params[4]));
                    jsonParam.put("id_empresa",Integer.valueOf(params[5]));
                    jsonParam.put("clave",params[6]);
                    // Envio los parámetros post.
                    OutputStream os = urlConn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(jsonParam.toString());
                    writer.flush();
                    writer.close();

                    int respuesta = urlConn.getResponseCode();


                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK) {

                        String line;
                        BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                        while ((line = br.readLine()) != null) {
                            result.append(line);
                            //response+=line;
                        }

                        //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                        JSONArray respuestaJSON = new JSONArray(result.toString()+"]");   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                        //Accedemos al vector de resultados
                        JSONObject objetoJSON= respuestaJSON.getJSONObject(0);

                        //Obtenemos el estado que es el nombre del campo en el JSON donde se asigna si tiene valor o no el archivo JSON
                        String resultJSON = objetoJSON.getString("estado");


                        if (resultJSON.equals("1")) {      // Si se ha insertado correctamente el producto
                            devuelve = "Producto insertado correctamente";
                            Obtner_Id_Producto();

                        } else if (resultJSON.equals("2")) {
                            devuelve = "El Producto no pudo insertarse";
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
     * @name private void ProductoYARegistrado()
     * @description Metodo el producto ya se encuentra registrado
     * @return void
     */
    private void ProductoYARegistrado()
    {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getActivity(), "!Ya Existe un Producto con el Codigo de Barra que as insertado¡", Toast.LENGTH_LONG).show();
            }
        });

    }

    /**
     * @name private void Insertar()
     * @description Metodo para insertar el producto
     * @return void
     */
    private void Insertar()
    {
        //Obtenemos el contenido del spinner del tipo de producto
        tipoproducto= String.valueOf(spinnertip.getSelectedItemPosition()+1);
        //Obtenemos el contenido del spinner del nombre de la empresa
        empresafabrica = String.valueOf(spinneremp.getSelectedItemPosition()+1);

        hiloconexion2 = new Insertar_Producto();
        hiloconexion2.execute(INSERT, "4", codigobarra.toString(), nombreproducto.toString(), tipoproducto.toString(), empresafabrica.toString(), encriptado.md5().toString());
    }

    /**
     * @name private void Obtner_Id_Producto()
     * @description Metodo para obtener el id del producto
     * @return void
     */
    private void Obtner_Id_Producto()
    {
        Bandera=true;
        //System.out.println("CODIGO DE BARRA: "+codigobarra.toString());
        // Rutas de los Web Services
        final String GET = IP + "/Obtener_Producto_CB.php?codigo_barra="+codigobarra.toString()+"&clave="+encriptado.md5();
        hiloconexion1 = new Obtener_Producto_CB();
        hiloconexion1.execute(GET,"1");
    }

    /**
     * @name private void Insertar_Alergenicos()
     * @description Metodo para insertar los alergenicos ingredientes
     * @return void
     */
    private void Insertar_Alergenicos()
    {

        if (!codbarra.equals("") && !nombreproducto.equals(""))
        {

            //Recorremos el fragment asignado para el alergenico altamuz
            for(Ingrediente ingrediente : ((VentanaRegistroProducto)getActivity()).list_ingredientes_altramuz){

                if (ingrediente.isChekeado())
                {
                    String id = ingrediente.getid_ingrediente();
                    //Insertamos Producto e Relaciones establecidad en la Base de Datos
                    String INSERT = IP + "/Insertar_Producto_Ingrediente.php";
                    hiloconexion3 = new Insertar_Producto_Ingrediente();
                    hiloconexion3.execute(INSERT, "2", id_producto.toString(),id.toString(),encriptado.md5().toString());
                }
            }

            //Recorremos el fragment asignado para el alergenico apio
            for(Ingrediente ingrediente : ((VentanaRegistroProducto)getActivity()).list_ingredientes_apio){
                if (ingrediente.isChekeado())
                {
                    String id = ingrediente.getid_ingrediente();
                    //Insertamos Producto e Relaciones establecidad en la Base de Datos
                    String INSERT = IP + "/Insertar_Producto_Ingrediente.php";
                    hiloconexion3 = new Insertar_Producto_Ingrediente();
                    hiloconexion3.execute(INSERT, "2", id_producto.toString(),id.toString(),encriptado.md5().toString());
                }
            }

            //Recorremos el fragment asignado para el alergenico azufre y sulfitos
            for(Ingrediente ingrediente : ((VentanaRegistroProducto)getActivity()).list_ingredientes_azufreysulfito){
                if (ingrediente.isChekeado())
                {
                    String id = ingrediente.getid_ingrediente();
                    //Insertamos Producto e Relaciones establecidad en la Base de Datos
                    String INSERT = IP + "/Insertar_Producto_Ingrediente.php";
                    hiloconexion3 = new Insertar_Producto_Ingrediente();
                    hiloconexion3.execute(INSERT, "2", id_producto.toString(),id.toString(),encriptado.md5().toString());
                }
            }

            //Recorremos el fragment asignado para el alergenico cacahuete
            for(Ingrediente ingrediente : ((VentanaRegistroProducto)getActivity()).list_ingredientes_cacahuete){
                if (ingrediente.isChekeado())
                {
                    String id = ingrediente.getid_ingrediente();
                    //Insertamos Producto e Relaciones establecidad en la Base de Datos
                    String INSERT = IP + "/Insertar_Producto_Ingrediente.php";
                    hiloconexion3 = new Insertar_Producto_Ingrediente();
                    hiloconexion3.execute(INSERT, "2", id_producto.toString(),id.toString(),encriptado.md5().toString());
                }
            }

            //Recorremos el fragment asignado para el alergenico crustacios
            for(Ingrediente ingrediente : ((VentanaRegistroProducto)getActivity()).list_ingredientes_crustaceo){
                if (ingrediente.isChekeado())
                {
                    String id = ingrediente.getid_ingrediente();
                    //Insertamos Producto e Relaciones establecidad en la Base de Datos
                    String INSERT = IP + "/Insertar_Producto_Ingrediente.php";
                    hiloconexion3 = new Insertar_Producto_Ingrediente();
                    hiloconexion3.execute(INSERT, "2", id_producto.toString(),id.toString(),encriptado.md5().toString());
                }
            }

            //Recorremos el fragment asignado para el alergenico frutos con cascara
            for(Ingrediente ingrediente : ((VentanaRegistroProducto)getActivity()).list_ingredientes_frutoscascara){
                if (ingrediente.isChekeado())
                {
                    String id = ingrediente.getid_ingrediente();
                    //Insertamos Producto e Relaciones establecidad en la Base de Datos
                    String INSERT = IP + "/Insertar_Producto_Ingrediente.php";
                    hiloconexion3 = new Insertar_Producto_Ingrediente();
                    hiloconexion3.execute(INSERT, "2", id_producto.toString(),id.toString(),encriptado.md5().toString());
                }
            }

            //Recorremos el fragment asignado para el alergenico gluten
            for(Ingrediente ingrediente : ((VentanaRegistroProducto)getActivity()).list_ingredientes_gluten){
                if (ingrediente.isChekeado())
                {
                    String id = ingrediente.getid_ingrediente();
                    //Insertamos Producto e Relaciones establecidad en la Base de Datos
                    String INSERT = IP + "/Insertar_Producto_Ingrediente.php";
                    hiloconexion3 = new Insertar_Producto_Ingrediente();
                    hiloconexion3.execute(INSERT, "2", id_producto.toString(),id.toString(),encriptado.md5().toString());
                }
            }

            //Recorremos el fragment asignado para el alergenico sesamo
            for(Ingrediente ingrediente : ((VentanaRegistroProducto)getActivity()).list_ingredientes_sesamo){
                if (ingrediente.isChekeado())
                {
                    String id = ingrediente.getid_ingrediente();
                    //Insertamos Producto e Relaciones establecidad en la Base de Datos
                    String INSERT = IP + "/Insertar_Producto_Ingrediente.php";
                    hiloconexion3 = new Insertar_Producto_Ingrediente();
                    hiloconexion3.execute(INSERT, "2", id_producto.toString(),id.toString(),encriptado.md5().toString());
                }
            }

            //Recorremos el fragment asignado para el alergenico huevo
            for(Ingrediente ingrediente : ((VentanaRegistroProducto)getActivity()).list_ingredientes_huevo){
                if (ingrediente.isChekeado())
                {
                    String id = ingrediente.getid_ingrediente();
                    //Insertamos Producto e Relaciones establecidad en la Base de Datos
                    String INSERT = IP + "/Insertar_Producto_Ingrediente.php";
                    hiloconexion3 = new Insertar_Producto_Ingrediente();
                    hiloconexion3.execute(INSERT, "2", id_producto.toString(),id.toString(),encriptado.md5().toString());
                }
            }

            //Recorremos el fragment asignado para el alergenico lacteos
            for(Ingrediente ingrediente : ((VentanaRegistroProducto)getActivity()).list_ingredientes_lacteo){
                if (ingrediente.isChekeado())
                {
                    String id = ingrediente.getid_ingrediente();
                    //Insertamos Producto e Relaciones establecidad en la Base de Datos
                    String INSERT = IP + "/Insertar_Producto_Ingrediente.php";
                    hiloconexion3 = new Insertar_Producto_Ingrediente();
                    hiloconexion3.execute(INSERT, "2", id_producto.toString(),id.toString(),encriptado.md5().toString());
                }
            }

            //Recorremos el fragment asignado para el alergenico molusco
            for(Ingrediente ingrediente : ((VentanaRegistroProducto)getActivity()).list_ingredientes_molusco){
                if (ingrediente.isChekeado())
                {
                    String id = ingrediente.getid_ingrediente();
                    //Insertamos Producto e Relaciones establecidad en la Base de Datos
                    String INSERT = IP + "/Insertar_Producto_Ingrediente.php";
                    hiloconexion3 = new Insertar_Producto_Ingrediente();
                    hiloconexion3.execute(INSERT, "2", id_producto.toString(),id.toString(),encriptado.md5().toString());
                }
            }

            //Recorremos el fragment asignado para el alergenico mostaza
            for(Ingrediente ingrediente : ((VentanaRegistroProducto)getActivity()).list_ingredientes_mostaza){
                if (ingrediente.isChekeado())
                {
                    String id = ingrediente.getid_ingrediente();
                    //Insertamos Producto e Relaciones establecidad en la Base de Datos
                    String INSERT = IP + "/Insertar_Producto_Ingrediente.php";
                    hiloconexion3 = new Insertar_Producto_Ingrediente();
                    hiloconexion3.execute(INSERT, "2", id_producto.toString(),id.toString(),encriptado.md5().toString());
                }
            }

            //Recorremos el fragment asignado para el alergenico pescado
            for(Ingrediente ingrediente : ((VentanaRegistroProducto)getActivity()).list_ingredientes_pescado){
                if (ingrediente.isChekeado())
                {
                    String id = ingrediente.getid_ingrediente();
                    //Insertamos Producto e Relaciones establecidad en la Base de Datos
                    String INSERT = IP + "/Insertar_Producto_Ingrediente.php";
                    hiloconexion3 = new Insertar_Producto_Ingrediente();
                    hiloconexion3.execute(INSERT, "2", id_producto.toString(),id.toString(),encriptado.md5().toString());
                }
            }

            //Recorremos el fragment asignado para el alergenico soja
            for(Ingrediente ingrediente : ((VentanaRegistroProducto)getActivity()).list_ingredientes_soja){
                if (ingrediente.isChekeado())
                {
                    String id = ingrediente.getid_ingrediente();
                    //Insertamos Producto e Relaciones establecidad en la Base de Datos
                    String INSERT = IP + "/Insertar_Producto_Ingrediente.php";
                    hiloconexion3 = new Insertar_Producto_Ingrediente();
                    hiloconexion3.execute(INSERT, "2", id_producto.toString(),id.toString(),encriptado.md5().toString());
                }
            }

            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(), "!Producto Insertado Correctamente¡", Toast.LENGTH_LONG).show();
                }
            });

            //Esperamos 50 milisegundos
            SystemClock.sleep(500);

            //getView().setId(R.id.restablecer);

            //Redireccionamos la aplicacion a la venana principal
            Intent intent = new Intent(getActivity(), VentanaPrincipalAdmin.class);
            startActivity(intent);
            getActivity().finish();

        }
        //Si el edittext nombre y el edittex apellidos son vacios mostrarmos un mensaje u notificación advirtiendo
        //que son campos obligatorios
        else {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View layout = inflater.inflate(R.layout.contenidotoast, (ViewGroup) getView().findViewById(R.id.toast_layout_root));

            TextView txtMsg = (TextView) layout.findViewById(R.id.text_toast);
            txtMsg.setText("Campos Obligatorios:");

            if (codigobarra.equals("")) {
                txtMsg.setText(txtMsg.getText().toString() + "\n- Codigo de Barra");
            }

            if (nombreproducto.equals("")) {
                txtMsg.setText(txtMsg.getText().toString() + "\n- Nombre del Producto");
            }
            txtMsg.setText(txtMsg.getText().toString() + "\n- Tipo Producto: "+tipoproducto);
            txtMsg.setText(txtMsg.getText().toString() + "\n- Empresa Fabrica: "+empresafabrica);

            int duration = Toast.LENGTH_LONG;

            Toast toast = new Toast(getActivity().getApplicationContext());
            toast.setDuration(duration);
            toast.setView(layout);
            toast.show();

        }
    }

    /**
     * Clase que se encarga de realizar la ejecución de la consulta sql mediente un servicio web alojado en un hosting
     * mediente archivos php.
     */
    public class Insertar_Producto_Ingrediente extends AsyncTask<String,Void,String> {


        @Override
        protected String doInBackground(String... params) {

            String cadena = params[0];
            URL url = null; // Url de donde queremos obtener información
            String devuelve ="";

            if(params[1]=="2") { //INSERT
                try {
                    HttpURLConnection urlConn;

                    DataOutputStream printout;
                    DataInputStream input;
                    url = new URL(cadena);
                    urlConn = (HttpURLConnection) url.openConnection();
                    urlConn.setDoInput(true);
                    urlConn.setDoOutput(true);
                    urlConn.setUseCaches(false);
                    urlConn.setRequestProperty("Content-Type", "application/json");
                    urlConn.setRequestProperty("Accept", "application/json");
                    urlConn.connect();
                    //Creo el Objeto JSON
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("id_producto", Integer.valueOf(params[2]));
                    jsonParam.put("id_ingrediente",Integer.valueOf(params[3]));
                    jsonParam.put("clave",params[4]);
                    // Envio los parámetros post.
                    OutputStream os = urlConn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(jsonParam.toString());
                    writer.flush();
                    writer.close();

                    int respuesta = urlConn.getResponseCode();


                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK) {

                        String line;
                        BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                        while ((line = br.readLine()) != null) {
                            result.append(line);
                            //response+=line;
                        }

                        //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                        JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                        //Accedemos al vector de resultados

                        String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON

                        if (resultJSON == "1") {      // hay un alumno que mostrar
                            devuelve = "Producto insertado correctamente";

                        } else if (resultJSON == "2") {
                            devuelve = "El Producto no pudo insertarse";
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

}
