package com.example.juansevillano.testingproductos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.List;

import java.util.List;

import static android.content.ContentValues.TAG;

public class RegistroProducto extends Fragment implements AdapterView.OnItemSelectedListener {

    //EditText para almacenar el codigo de barra que leemos.
    public EditText codbarra;

    //Spinner para el tipo de productos
    private Spinner spinnertipo;

    //Spinner para la empresa que fabrico el producto
    private Spinner spinnerempresa;

    // array para listar las frutas
    //private ArrayList<TipoProducto> tipoProductos;

    //Para la Creacion de Dialogos.
    ProgressDialog pDialogproductos;
    ProgressDialog pDialogempresa;

    public String URL_LISTA_PRODUCTO = "http://tfgalimentos.16mb.com/Todos_Tipo_Producto.php";

    public String URL_LISTA_EMPRESAS = "http://tfgalimentos.16mb.com/Todos_Empresa.php";

    public RegistroProducto() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_registro_producto, container, false);
        spinnertipo = (Spinner) v.findViewById(R.id.spinnertipoproducto);
        spinnerempresa = (Spinner) v.findViewById(R.id.spinnerempresa);

        ((VentanaRegistroProducto)getActivity()).list_tipo_producto = new ArrayList<TipoProducto>();
        ((VentanaRegistroProducto)getActivity()).list_empresa = new ArrayList<Empresa>();

        // seleccionar el tipo de empresa del spinner
        spinnertipo.setOnItemSelectedListener(this);
        // seleccionar el nombre de la empresa del spinner
        spinnerempresa.setOnItemSelectedListener(this);
        return v;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.getView().findViewById(R.id.scaner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("Prueba 1");
                //Se instancia un objeto de la clase IntentIntegrator
                IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity());
                //Se procede con el proceso de scaneo
                scanIntegrator.initiateScan();
                System.out.println("Prueba 2");
            }
        });

        new ObtenerListaProducto().execute();

        new ObtenerListaEmpresa().execute();

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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        //Se obtiene el resultado del proceso de scaneo y se parsea
        System.out.println("Prueba 10");
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            System.out.println("Prueba 11");
            //Quiere decir que se obtuvo resultado pro lo tanto:
            //Desplegamos en pantalla el contenido del código de barra scaneado
            System.out.println(scanningResult.getContents());
            if(scanningResult.getContents()!=null)
            {
                String scanContent = scanningResult.getContents();
                System.out.println(scanContent.toString());
                //Se Instancia el Campo de Texto para el contenido  del código de barra
                codbarra = (EditText)getActivity().findViewById(R.id.cbarra);
                codbarra.setText(scanContent.toString());
                System.out.println(codbarra.getText().toString());
                System.out.println("Prueba 12");
            }

        }else{
            //Quiere decir que NO se obtuvo resultado
            Toast toast = Toast.makeText(this.getActivity().getApplicationContext(),
                    "!No se ha recibido datos del escaneo¡", Toast.LENGTH_SHORT);
            toast.show();
        }


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
                            ((VentanaRegistroProducto)getActivity()).list_tipo_producto.add(new TipoProducto(pruebaJSON.getJSONObject(i).getString("id_tipo_producto"),pruebaJSON.getJSONObject(i).getString("tipo")));
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
                            ((VentanaRegistroProducto)getActivity()).list_empresa.add(new Empresa(pruebaJSON.getJSONObject(i).getString("id_empresa"),pruebaJSON.getJSONObject(i).getString("nombre_empresa")));
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

    private void populateSpinnertipoproducto() {
        List<String> lables = new ArrayList<String>();

        //txtAgregar.setText("");

        for (int i = 0; i < ((VentanaRegistroProducto)getActivity()).list_tipo_producto.size(); i++) {
            lables.add(((VentanaRegistroProducto)getActivity()).list_tipo_producto.get(i).getTipo());
        }


        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, lables);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnertipo.setAdapter(spinnerAdapter);

    }

    private void populateSpinnerEmpresa() {
        List<String> lables = new ArrayList<String>();

        //txtAgregar.setText("");

        for (int i = 0; i < ((VentanaRegistroProducto)getActivity()).list_empresa.size(); i++) {
            lables.add(((VentanaRegistroProducto)getActivity()).list_empresa.get(i).getEmpresa());
        }


        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, lables);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerempresa.setAdapter(spinnerAdapter);

    }

    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        Toast.makeText(
                getActivity().getBaseContext(),
                parent.getItemAtPosition(position).toString() + " Seleccionado" ,
                Toast.LENGTH_SHORT).show();

    }


    public void onNothingSelected(AdapterView<?> arg0) {
    }
}
