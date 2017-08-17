package com.example.juansevillano.testingproductos;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.List;

import static android.content.ContentValues.TAG;

public class ActualizarProducto extends Fragment implements AdapterView.OnItemSelectedListener {

    //EditText para almacenar el codigo de barra que leemos.
    public EditText codbarra;
    String codigobarra;

    //EditText para almacenar el nombre del producto.
    public EditText nombreproducto;
    String nombreprod;

    //Spinner para el tipo de productos
    private Spinner spinnertipo;
    int idtipoproducto;

    //Spinner para la empresa que fabrico el producto
    private Spinner spinnerempresa;
    int idempresa;

    String idproducto;

    //Se crear un objetio de tipo ObtenerWebService
    Obtener_Producto_CB hiloconexion1;

    // array para listar las frutas
    //private ArrayList<TipoProducto> tipoProductos;

    //Para la Creacion de Dialogos.
    ProgressDialog pDialogproductos;
    ProgressDialog pDialogempresa;

    // IP de mi Url
    String IP = "http://tfgalimentos.16mb.com";

    //Clave Encriptada
    Encriptado encriptado= new Encriptado();

    //URL para el listado del tipo de productos
    public String URL_LISTA_PRODUCTO = "http://tfgalimentos.16mb.com/Todos_Tipo_Producto.php?clave="+encriptado.md5();

    //URL para el listado de las empresas
    public String URL_LISTA_EMPRESAS = "http://tfgalimentos.16mb.com/Todos_Empresa.php?clave="+encriptado.md5();

    int pos=0;

    /**
     * @name public ActualizarProducto()
     * @description Constructor Vacio
     * @return void
     */
    public ActualizarProducto() {
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
     * @name private void onResume()
     * @description Primer Método cuando la función se está yendo de la pantalla. Muestra en que proceso nos encontramos.
     * @return void
     */
    public void onResume()
    {
        // Definimos un objeto del activity VentanaActualizarProducto
        final VentanaActualizarProducto activity= ((VentanaActualizarProducto) getActivity());
        ViewPager viewPager= activity.viewPager;
        if(viewPager.getCurrentItem() == pos){
            pos++;
            Toast.makeText(getActivity(), "Proceso "+pos+"/16", Toast.LENGTH_SHORT).show();
            pos--;
            //Your code here. Executed when fragment is seen by user.
        }

        super.onResume();
    }

    /**
     * @name private View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
     * @description Se crea la vista de la clase
     * @return View v
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_actualizar_producto, container, false);

        // Definimos un objeto del activity VentanaEditarUsuario
        final VentanaActualizarProducto activity = ((VentanaActualizarProducto) getActivity());

        // Rutas de los Web Services
        final String GET = IP + "/Obtener_Producto_CB.php?codigo_barra="+activity.codigo_barra.toString()+"&clave="+encriptado.md5();
        hiloconexion1 = new Obtener_Producto_CB();
        hiloconexion1.execute(GET,"1");

        return v;
    }


    /**
     * @name private void onActivityCreated(Bundle savedInstanceState)
     * @description Funcion para crear la Actividad
     * @return void
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new ObtenerListaProducto().execute();

        new ObtenerListaEmpresa().execute();

    }

    /**
     * @name private void onActivityResult(int requestCode, int resultCode, Intent intent)
     * @description Para recuperar la información resultante de una segunda actividad.
     * @return void
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

    }

    /**
     * Clase que se encarga de realizar la ejecución de la consulta sql mediente un servicio web alojado en un hosting
     * mediente archivos php.
     */
    public class ObtenerListaProducto extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogproductos = new ProgressDialog(getActivity());
            pDialogproductos.setMessage("Obtencion de los Tipos de Productos..");
            pDialogproductos.setCancelable(false);
            pDialogproductos.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            URL url = null; // Url de donde queremos obtener información
            String devuelve ="";

            try {
                url = new URL(URL_LISTA_PRODUCTO);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
                //connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                //       " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
                //connection.setHeader("content-type", "application/json");

                int respuesta = connection.getResponseCode();
                StringBuilder result = new StringBuilder();

                if (respuesta == HttpURLConnection.HTTP_OK)
                {

                    // preparo la cadena de entrada
                    InputStream in = new BufferedInputStream(connection.getInputStream());
                    // la introduzco en un BufferedReader
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                    // que tranformar el BufferedReader a String. Esto lo hago a traves de un
                    // StringBuilder.
                    String line;
                    while ((line = reader.readLine()) != null)
                    {
                        // Paso toda la entrada al StringBuilder
                        result.append(line);
                    }

                    //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                    JSONArray respuestaJSON = new JSONArray("["+result.toString()+"]");   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                    //Accedemos al vector de resultados
                    JSONObject objetoJSON= respuestaJSON.getJSONObject(0);

                    //Obtenemos el estado que es el nombre del campo en el JSON donde se asigna si tiene valor o no el archivo JSON
                    String resultJSON = objetoJSON.getString("estado");

                    // Comprobamos si hay ingredientes para mostrar
                    if (resultJSON.equals("1"))
                    {
                        //Obtenemos los ingrediente que vamos a mostrar en la aplicación
                        JSONArray pruebaJSON = objetoJSON.getJSONArray("Tipo_Producto");

                        for(int i=0;i<pruebaJSON.length();i++)
                        {
                                ((VentanaActualizarProducto)getActivity()).list_tipo_producto.add(new TipoProducto(pruebaJSON.getJSONObject(i).getString("id_tipo_producto"),pruebaJSON.getJSONObject(i).getString("tipo")));
                        }
                    }
                    //Si no existe ingredientes devolvemos un String comentado que no existe ingredientes
                    else if (resultJSON.equals("2")){
                        devuelve = "No hay Tipo de Producto para Mostrar.";
                    }

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //return devuelve;
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialogproductos.isShowing())
                pDialogproductos.dismiss();
            populateSpinnertipoproducto();
        }


    }

    /**
     * Clase que se encarga de realizar la ejecución de la consulta sql mediente un servicio web alojado en un hosting
     * mediente archivos php.
     */
    public class ObtenerListaEmpresa extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialogempresa = new ProgressDialog(getActivity());
            pDialogempresa.setMessage("Obtencion de los Tipos de Empresas...");
            pDialogempresa.setCancelable(false);
            pDialogempresa.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            URL url = null; // Url de donde queremos obtener información
            String devuelve ="";

            try {
                url = new URL(URL_LISTA_EMPRESAS);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
                //connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                //       " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
                //connection.setHeader("content-type", "application/json");

                int respuesta = connection.getResponseCode();
                StringBuilder result = new StringBuilder();

                if (respuesta == HttpURLConnection.HTTP_OK)
                {

                    // preparo la cadena de entrada
                    InputStream in = new BufferedInputStream(connection.getInputStream());
                    // la introduzco en un BufferedReader
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                    // que tranformar el BufferedReader a String. Esto lo hago a traves de un
                    // StringBuilder.
                    String line;
                    while ((line = reader.readLine()) != null)
                    {
                        // Paso toda la entrada al StringBuilder
                        result.append(line);
                    }

                    //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                    JSONArray respuestaJSON = new JSONArray("["+result.toString()+"]");   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                    //Accedemos al vector de resultados
                    JSONObject objetoJSON= respuestaJSON.getJSONObject(0);

                    //Obtenemos el estado que es el nombre del campo en el JSON donde se asigna si tiene valor o no el archivo JSON
                    String resultJSON = objetoJSON.getString("estado");

                    // Comprobamos si hay ingredientes para mostrar
                    if (resultJSON.equals("1"))
                    {
                        //Obtenemos los ingrediente que vamos a mostrar en la aplicación
                        JSONArray pruebaJSON = objetoJSON.getJSONArray("Empresa");

                        for(int i=0;i<pruebaJSON.length();i++)
                        {
                            ((VentanaActualizarProducto)getActivity()).list_empresa.add(new Empresa(pruebaJSON.getJSONObject(i).getString("id_empresa"),pruebaJSON.getJSONObject(i).getString("nombre_empresa")));
                        }
                    }
                    //Si no existe ingredientes devolvemos un String comentado que no existe ingredientes
                    else if (resultJSON.equals("2")){
                        devuelve = "No hay Empresas para Mostrar.";
                    }

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //return devuelve;
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialogempresa.isShowing())
                pDialogempresa.dismiss();
            populateSpinnerEmpresa();
        }


    }

    /**
     * @name private void popularSpinnertipoproducto()
     * @description Función para rellenar los spinner del tipo de producto.
     * @return void
     */
    private void populateSpinnertipoproducto() {
        List<String> lables = new ArrayList<String>();

        //txtAgregar.setText("");

        for (int i = 0; i < ((VentanaActualizarProducto)getActivity()).list_tipo_producto.size(); i++) {
            lables.add(((VentanaActualizarProducto)getActivity()).list_tipo_producto.get(i).getTipo());
        }


        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, lables);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnertipo.setAdapter(spinnerAdapter);

    }

    /**
     * @name private void popularSpinneEmpresa()
     * @description Función para rellenar los spinner de las empresas
     * @return void
     */
    private void populateSpinnerEmpresa() {
        List<String> lables = new ArrayList<String>();

        //txtAgregar.setText("");

        for (int i = 0; i < ((VentanaActualizarProducto)getActivity()).list_empresa.size(); i++) {
            lables.add(((VentanaActualizarProducto)getActivity()).list_empresa.get(i).getEmpresa());
        }


        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, lables);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerempresa.setAdapter(spinnerAdapter);

    }

    /**
     * @name private void onItemSeleccted()
     * @description Función para mostrar que spinner ha sido seleccionado
     * @return void
     */
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        Toast.makeText(
                getActivity().getBaseContext(),
                parent.getItemAtPosition(position).toString() + " Seleccionado" ,
                Toast.LENGTH_SHORT).show();

    }

    /**
     * @name private void onNothingSelected()
     * @description Funcion si no se seleccionado ninguno
     * @return void
     */
    public void onNothingSelected(AdapterView<?> arg0) {
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

                        if (resultJSON.equals("1")){      // hay un producto anteriormente creado
                            devuelve = "Si hay Producto Registrado";
                            //Obtenemos los ingrediente que vamos a mostrar en la aplicación
                            //JSONArray pruebaJSON = objetoJSON.getJSONArray("Producto");
                            idproducto=objetoJSON.getJSONObject("Producto").getString("id_producto");
                            codigobarra=objetoJSON.getJSONObject("Producto").getString("codigo_barra");
                            nombreprod=objetoJSON.getJSONObject("Producto").getString("nombre_producto");
                            idtipoproducto=Integer.parseInt(objetoJSON.getJSONObject("Producto").getString("id_tipo_producto"));
                            idempresa=Integer.parseInt(objetoJSON.getJSONObject("Producto").getString("id_empresa"));
                        }
                        else {

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
            AsignarValores();
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
     * @name private void AsignarValores()
     * @description Funcion para asignar los valores a las variables correspondientes
     * @return void
     */
    private void AsignarValores()
    {
        System.out.println("Prueba 11");
        //Se Instancia el Campo de Texto para el contenido  del código de barra
        codbarra = (EditText) getActivity().findViewById(R.id.cbarraactualizar);
        //Se Instancia el Campo de Texto para el contenido  del nombre del producto
        nombreproducto= (EditText)getActivity().findViewById(R.id.nomproductoactualizar);

        spinnertipo = (Spinner) getActivity().findViewById(R.id.spinnertipoproductoactualizar);
        spinnerempresa = (Spinner) getActivity().findViewById(R.id.spinnerempresaactualizar);

        ((VentanaActualizarProducto)getActivity()).list_tipo_producto = new ArrayList<TipoProducto>();
        ((VentanaActualizarProducto)getActivity()).list_empresa = new ArrayList<Empresa>();

        System.out.println(codigobarra.toString());
        System.out.println(nombreprod.toString());
        System.out.println(idtipoproducto);
        System.out.println(idempresa);

        codbarra.setText(codigobarra.toString());
        nombreproducto.setText(nombreprod.toString());
        new Handler().postDelayed(new Runnable(){
            public void run() {
                spinnertipo.setSelection(idtipoproducto-1);
                spinnerempresa.setSelection(idempresa-1);
            }
        },1000);

    }

    /**
     * @name public boolean comprobarActivityALaVista(Context context, String nombreClase)
     * @description Método para comprobar en que activity nos encontramos actualmente
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
                //System.out.println(nombreClaseActual);
            }

        }catch (NullPointerException e){

            Log.e(TAG, "Error al tomar el nombre de la clase actual " + e);
            return false;
        }

        // devolvemos el resultado de la comparacion
        return nombreClase.equals(nombreClaseActual);
    }

}
