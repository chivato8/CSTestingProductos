package com.example.juansevillano.testingproductos;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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

public class EditarUsuario extends Fragment{

    String nombre;
    String correo;
    String telefono;
    String id_asociado;

    EditText nomyapel;
    EditText cor;
    EditText tel;
    EditText idasociado;

    // IP de mi Url
    String IP = "http://tfgalimentos.16mb.com";

    //Se crear un objetio de tipo ObtenerWebService
    ObtenerWebService hiloconexion;



    public EditarUsuario() {
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_editar_usuario, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Se une FragmentActivity
        final VentanaEditarUsuarioAdmin activity2 = ((VentanaEditarUsuarioAdmin) getActivity());

        // Obtener el control de los EditText de los Fragment
        nomyapel= (EditText)activity2.fragments.get(0).getView().findViewById(R.id.editnombre);
        cor = (EditText)activity2.fragments.get(0).getView().findViewById(R.id.editcorreo);
        tel = (EditText)activity2.fragments.get(0).getView().findViewById(R.id.edittelefono);

        //final VentanaPrincipal ventanaPrincipal = ((VentanaPrincipal) getActivity());
        //String id_asociado=ventanaPrincipal.id_asociado;
        id_asociado=activity2.id_asociado;

        // Rutas de los Web Services
        String GET_BY_ID = IP + "/obtener_usuarios_existentes.php?id_asociado="+id_asociado.toString();
        hiloconexion = new ObtenerWebService();
        hiloconexion.execute(GET_BY_ID,"1");
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

            if(params[1]=="1") {    // consulta por id

                try {
                    url = new URL(cadena);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
                    //connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                    //        " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
                    //connection.setHeader("content-type", "application/json");

                    int respuesta = connection.getResponseCode();
                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK) {


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

                        if (resultJSON.equals("1")) {      // hay un alumno que mostrar

                            //Obtenemos los ingrediente que vamos a mostrar en la aplicación
                            JSONArray pruebaJSON = objetoJSON.getJSONArray("Usuario");

                            nombre=pruebaJSON.getJSONObject(0).getString("nombre_apellidos");
                            correo=pruebaJSON.getJSONObject(0).getString("correo_electronico");

                            if(pruebaJSON.getJSONObject(0).getString("telefono")!="null")
                            {
                                telefono=pruebaJSON.getJSONObject(0).getString("telefono");
                            }
                            else
                            {
                                telefono="";
                            }


                        } else if (resultJSON == "2") {
                            devuelve = "No hay alumnos";
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
            Asignar_valores();
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

    public void Asignar_valores()
    {
        nomyapel.setText(nombre);
        System.out.println("N "+nomyapel.toString());
        cor.setText(correo);
        System.out.println("C "+cor.toString());
        tel.setText(telefono);
        System.out.println("T "+tel.toString());
    }

}
