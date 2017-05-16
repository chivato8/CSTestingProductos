package com.example.juansevillano.testingproductos;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

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

/**
 * Created by Juan Sevillano on 09/05/2017.
 */
public class AlergenicoMolusco extends Fragment{
    //Se crea un ArrayList de tipo Dias//
    ArrayList<Ingredientes> ingredientes = new ArrayList<Ingredientes>();
    //Se crea una objeto tipo ListView
    ListView lstLista;
    //Se crea un objeto de tipo AdaptadorDias
    Adaptador adaptador;
    //Se crear un objetio de tipo ObtenerWebService
    ObtenerWebService hiloconexion;

    public AlergenicoMolusco() {
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
        return inflater.inflate(R.layout.activity_alergenico_molusco, container, false);
    }

    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        // IP de mi Url
        String IP = "http://tfgalimentos.16mb.com";
        // Rutas de los Web Services
        String GET = IP + "/obtener_transtorno_ingrediente.php?id_transtorno=11";

        lstLista = (ListView) getView().findViewById(R.id.lstLista);

        hiloconexion = new ObtenerWebService();
        hiloconexion.execute(GET, "1");   // Parámetros que recibe doInBackground

        //Se define un nuevo adaptador de tipo AdaptadorDias donde se le pasa como argumentos el contexto de la actividad y el arraylist de los dias
        adaptador = new Adaptador(this.getActivity(), ingredientes);

        //Se establece el adaptador en la Listview
        lstLista.setAdapter(adaptador);

        //Esto es mas que nada es a nivel de diseño con el objetivo de crear unas lineas mas anchas entre item y item
        lstLista.setDividerHeight(3);

        //Se le aplica un Listener donde ira lo que tiene que hacer en caso de que sea pulsado
        lstLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {

                //En caso de que la posicion seleccionada gracias a "arg2" sea true que lo cambie a false
                if (ingredientes.get(arg2).isChekeado()) {
                    ingredientes.get(arg2).setChekeado(false);
                } else {
                    //aqui al contrario que la anterior, que lo pase a true.
                    ingredientes.get(arg2).setChekeado(true);
                }
                //Se notifica al adaptador de que el ArrayList que tiene asociado ha sufrido cambios (forzando asi a ir al metodo getView())
                adaptador.notifyDataSetChanged();

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
     * Esta clase extiende de ArrayAdapter para poder personalizarla a nuestro gusto
     */
    class Adaptador extends ArrayAdapter<Ingredientes> {

        Activity contexto;
        ArrayList<Ingredientes> ingredientes;

        /**
         * @name public Adaptador(Activity context, ArrayList<Ingredientes> ingredientes)
         * @description Constructor del AdaptadorDias donde se le pasaran por parametro el contexto de la aplicacion y
         * el ArrayList de los ingredientes
         * @return void
         */
        public Adaptador(Activity context, ArrayList<Ingredientes> ingredientes) {
            //Llamada al constructor de la clase superior donde requiere el contexto, el layout y el arraylist
            super(context, R.layout.fila, ingredientes);
            this.contexto = context;
            this.ingredientes = ingredientes;

        }

        /**
         * @name public View getView(int position, View convertView, ViewGroup parent)
         * @description Este metodo es el que se encarga de dibujar cada Item de la lista
         * y se invoca cada vez que se necesita mostrar un nuevo item.
         * En el se pasan parametros como la posicion del elemento mostrado, la vista (View) del elemento
         * mostrado y el conjunto de vistas
         * @return View
         */
        public View getView(int position, View convertView, ViewGroup parent) {
            View item = convertView;

            //Creamos esta variable para almacen posteriormente en el la vista que ha dibujado
            VistaItem vistaitem;

            //Si se decide que no existe una vista reutilizable para el proximo item entra en la condicion.
            //De este modo tambien ahorramos tener que volver a generar vistas
            if (item == null) {

                //Obtenemos una referencia de Inflater para poder inflar el diseño
                LayoutInflater inflador = contexto.getLayoutInflater();

                //Se le define a la vista (item) el tipo de diseño que tiene que tener
                item = inflador.inflate(R.layout.fila, null);

                //Creamos un nuevo vistaitem que se almacenara en el tag de la vista
                vistaitem = new VistaItem();

                //Almacenamos en el objeto la referencia del TextView que contiene el id de los ingredientes
                vistaitem.id_ingrediente = (TextView) item.findViewById(R.id.idingrediente);

                //Almacenamos en el objeto la referencia del TextView que contiene el nombre de los ingredientes
                vistaitem.nombre = (TextView) item.findViewById(R.id.txtCompleto);

                //Tambien almacenamos en el objeto la referencia del CheckBox buscandolo por ID
                vistaitem.chkEstado = (CheckBox) item.findViewById(R.id.chkEstado);

                //Ahora si, guardamos en el tag de la vista el objeto vistaitem
                item.setTag(vistaitem);

            } else {
                //En caso de que la vista sea ya reutilizable se recupera el objeto VistaItem almacenada en su tag
                vistaitem = (VistaItem) item.getTag();
            }

            //Se cargan los datos desde el ArrayList
            vistaitem.id_ingrediente.setText(ingredientes.get(position).getid_ingrediente());
            vistaitem.nombre.setText(ingredientes.get(position).getingrediente());
            vistaitem.chkEstado.setChecked(ingredientes.get(position).isChekeado());

            //Se devuelve ya la vista nueva o reutilizada que ha sido dibujada
            return (item);
        }

        /**
         * Esta clase se usa para almacenar el TextView y el CheckBox de una vista y
         * es donde esta el "truco" para que las vistas se guarden
         */
        class VistaItem {
            TextView nombre;
            TextView id_ingrediente;
            CheckBox chkEstado;

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
                        JSONArray pruebaJSON = objetoJSON.getJSONArray("Transtorno_Ingrediente");

                        if(comprobarActivityALaVista(getActivity(),"com.example.juansevillano.testingproductos.VentanaEditarUsuario")==true)
                        {
                            //Definimos una variable de tipo SQLiteDatabase
                            SQLiteDatabase db;

                            //Definimos una Variable de tipo Cursor
                            Cursor res;

                            //Abrimos la Base de datos "BDAlumnosIngrediente" en modo escritura.
                            BDUsuarioIngrediente bdUsuarioIngrediente = new BDUsuarioIngrediente(getActivity(), "BDUsuarioIngrediente", null, 1);

                            //Ponemos la Base de datos en Modo Escritura.
                            db= bdUsuarioIngrediente.getWritableDatabase();

                            // Obtener el control de la VentanaEditarUsuario
                            final VentanaEditarUsuario activity = ((VentanaEditarUsuario) getActivity());

                            //VentanaEditarUsuario activity = new VentanaEditarUsuario();

                            //Obtenemos el id_usuario que hemos elegido
                            String[] elusuario = new String[] {activity.getMyData()};

                            System.out.println(elusuario[0]);

                            //Realizamos una consulta, en la que buscamos el usaurio elegido.
                            res=db.rawQuery("SELECT id_ingrediente FROM Usuario_Ingrediente WHERE id_usuario=?",elusuario);


                            int total= res.getCount();

                            boolean [] id=new boolean[pruebaJSON.length()];

                            for(int x=0;x<id.length;x++)
                            {
                                id[x]=false;
                            }

                            for(int j=0;j<pruebaJSON.length();j++)
                            {
                                res.moveToFirst();
                                //Recorremos el objeto anterior mostrando los ingredientes uno a uno
                                for(int i=0; i<total;i++)
                                {
                                    if(res.getString(0).equals(pruebaJSON.getJSONObject(j).getString("id_ingrediente")))
                                    {
                                        id[j]=true;
                                    }
                                    res.moveToNext();
                                }

                                if(id[j]==true) {
                                    ingredientes.add(new Ingredientes(pruebaJSON.getJSONObject(j).getString("id_ingrediente"),
                                            pruebaJSON.getJSONObject(j).getString("nombre_ingrediente"),
                                            true));
                                }
                                else
                                {
                                    ingredientes.add(new Ingredientes(pruebaJSON.getJSONObject(j).getString("id_ingrediente"),
                                            pruebaJSON.getJSONObject(j).getString("nombre_ingrediente"),
                                            false));
                                }

                            }
                            db.close();
                        }
                        else
                        {
                            //Recorremos el objeto anterior mostrando los ingredientes uno a uno
                            for(int i=0;i<pruebaJSON.length();i++)
                            {
                                ingredientes.add(new Ingredientes(pruebaJSON.getJSONObject(i).getString("id_ingrediente"),
                                        pruebaJSON.getJSONObject(i).getString("nombre_ingrediente"),
                                        false));
                            }
                        }

                    }
                    //Si no existe ingredientes devolvemos un String comentado que no existe ingredientes
                    else if (resultJSON.equals("2")){
                        devuelve = "No hay Ingredientes Pertenecientes a esté Alergenico.";
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
            adaptador.notifyDataSetChanged();
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
