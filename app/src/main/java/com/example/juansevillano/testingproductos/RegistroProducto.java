package com.example.juansevillano.testingproductos;

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

public class RegistroProducto extends Fragment {

    //EditText para almacenar el codigo de barra que leemos.
    public EditText codbarra;

    //Se crear un objetio de tipo ObtenerWebService
    ObtenerWebService hiloconexion;

    //Spinner para el tipo de productos
    public Spinner spinnertipo;

    //Array para listar los diferentes tipos de productos que Existen
    public ArrayList<TipoProducto> tiposlist;

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
        spinnertipo = (Spinner) v.findViewById(R.id.spinner);

        hiloconexion = new ObtenerWebService();
        hiloconexion.execute();   // Parámetros que recibe doInBackground

        List<String> lables = new ArrayList<String>();

        for (int i = 0; i < tiposlist.size(); i++) {
            lables.add(tiposlist.get(i).getTipo());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item,lables);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnertipo.setAdapter(adapter);

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

        // IP de mi Url
        String IP = "http://tfgalimentos.16mb.com";
        // Rutas de los Web Services
        String GET = IP + "/Todos_Tipo_Producto.php";

        //Se obtiene el resultado del proceso de scaneo y se parsea
        System.out.println("Prueba 3");
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            System.out.println("Prueba 4");
            //Quiere decir que se obtuvo resultado pro lo tanto:
            //Desplegamos en pantalla el contenido del código de barra scaneado
            System.out.println(scanningResult.getContents());
            String scanContent = scanningResult.getContents();
            System.out.println(scanContent.toString());
            //Se Instancia el Campo de Texto para el contenido  del código de barra
            codbarra = (EditText)getActivity().findViewById(R.id.cbarra);
            codbarra.setText(scanContent.toString());
            System.out.println(codbarra.getText().toString());
            System.out.println("Prueba 5");
        }else{
            //Quiere decir que NO se obtuvo resultado
            Toast toast = Toast.makeText(this.getActivity().getApplicationContext(),
                    "!No se ha recibido datos del scaneo¡", Toast.LENGTH_SHORT);
            toast.show();
        }


    }

    /**
     * Clase que se encarga de realizar la ejecución de la consulta sql mediente un servicio web alojado en un hosting
     * mediente archivos php.
     */
    public class ObtenerWebService extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {

            String cadena = params[0];
            URL url = null; // Url de donde queremos obtener información
            String devuelve ="";

            try {
                url = new URL(cadena);
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

                        tiposlist= new ArrayList<TipoProducto>();

                        for(int i=0;i<pruebaJSON.length();i++)
                        {
                            tiposlist.add(new TipoProducto(pruebaJSON.getJSONObject(i).getString("id"),pruebaJSON.getJSONObject(i).getString("tipo")));
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

            return devuelve;
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onPostExecute(String s) {
            //resultado.setText(s);
            //super.onPostExecute(s);
            //Se notifica al adaptador de que el ArrayList que tiene asociado ha sufrido cambios (forzando asi a ir al metodo getView())
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
