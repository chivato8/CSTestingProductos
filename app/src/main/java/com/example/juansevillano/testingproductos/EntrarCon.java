package com.example.juansevillano.testingproductos;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class EntrarCon extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    ImageView imageViewInvitado;

    //Definimos una variable de tipo SQLiteDatabase
    SQLiteDatabase db;

    private GoogleApiClient googleApiClient;

    private SignInButton signInButton;

    public static final int SIGN_IN_CODE = 777;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private ProgressBar progressBar;

    /**
     * @name protected void onCreate( Bundle savedInstanceState)
     * @description Primer Método que se llama al crear la clase
     * @return void
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar_con);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton = (SignInButton) findViewById(R.id.signInButton);

        signInButton.setSize(SignInButton.SIZE_WIDE);

        signInButton.setColorScheme(SignInButton.COLOR_DARK);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN_CODE);
            }
        });


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //nombre=user.getDisplayName();
                    //correo=user.getEmail();
                    //id=user.getUid();
                    //photo=user.getPhotoUrl().toString();
                    //hiloconexion = new ObtenerWebService();
                    //hiloconexion.execute(INSERT, "4",id.toString(),nombre.toString(),correo.toString(),photo.toString());   // Parámetros que recibe doInBackground

                    goMainScreen();
                }
            }
        };

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        imageViewInvitado = (ImageView)findViewById(R.id.imageViewInvitado);

        imageViewInvitado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Llamamos a la Funcion de creacion de la barra cargadora
                funcionBD();

                //Esperamos 1900 milisegundos hasta que termine.
                SystemClock.sleep(0);
            }
        });
    }

    /**
     * @name public void onBackPressed ()
     * @description Si hacemos clic en el boton hacia atras saldremos de la aplicacion
     * @return void
     */
    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    /**
     * @name public void onConnectionFailed(@NonNull ConnectionResult connectionResult
     * @description Metodo por si produce un error en la conexión
     * @return void
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    /**
     * @name private void onActivityResult(int requestCode, int resultCode, Intent intent)
     * @description Para recuperar la información resultante de una segunda actividad.
     * @return void
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    /**
     * @name private void handleSignInResult(GoogleSignInResult result)
     * @description Metodo para el resultado del inicio de sesión con google
     * @return void
     */
    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            firebaseAuthWithGoogle(result.getSignInAccount());
        } else {
            Toast.makeText(this, R.string.not_log_in, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @name private void firebaseAuthWithGoogle(GoogleSignInAccount signInAccount)
     * @description Metodo para la autentificación con google.
     * @return void
     */
    private void firebaseAuthWithGoogle(GoogleSignInAccount signInAccount) {

        progressBar.setVisibility(View.VISIBLE);
        signInButton.setVisibility(View.GONE);

        AuthCredential credential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressBar.setVisibility(View.GONE);
                signInButton.setVisibility(View.VISIBLE);

                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), R.string.not_firebase_auth, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * @name private void firebaseAuthWithGoogle(GoogleSignInAccount signInAccount)private void goMainScreen()
     * @description Metodo para ir a la ventanaPrincipal cuando se inicia con la cuenta de google
     * @return void
     */
    private void goMainScreen() {
        Intent intent = new Intent(this, VentanaPrincipal.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (firebaseAuthListener != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }


    /*public void onClick(View view) {
        //calling register method on click
        if(view == imageViewInvitado){

        }
    }*/

    /**
     * @name private void  funcionBD()
     * @description Funcion gestion de la base de datos
     * @return void
     */
    private void  funcionBD()
    {

        //Abrimos la Base de datos "BDUsuario" en modo escritura.
        BDUsuario Usuarios=new BDUsuario(this,"BDUsuario",null,1);

        //Ponemos la Base de datos en Modo Escritura.
        db= Usuarios.getWritableDatabase();

        //Toast.makeText(getBaseContext(), "Usuario Cargado Correctamente", Toast.LENGTH_LONG).show();

        //Comprobamos que la base de datos existe
        if(db!=null)
        {

            //Comprobamos si la Base de datos con la que estamos trabajando esta VACIA
            Cursor count=db.rawQuery("SELECT Nombre FROM Usuarios",null);

            if(count.getCount()>0) //La Base de Datos SI tiene Usuario Registrado
            {
                //count.moveToFirst();
                //Toast.makeText(getBaseContext(), "Usuarios Registrados: " + count.getCount(), Toast.LENGTH_LONG).show();
                Toast.makeText(getBaseContext(), "Accediendo a la Aplicación.", Toast.LENGTH_SHORT).show();

                //Cerramos la Base de Datos
                db.close();

                //Accedemos a la Aplición para la Eleccion del Modo de Escaneo
                Intent ListSong = new Intent(getApplicationContext(), VentanaOpcionesEscaner.class);
                startActivity(ListSong);
                finish();

            }//La Base de Datos NO tiene Ningun Usuario Registrado
            else
            {
                Toast.makeText(getBaseContext(), "!BASE DE DATOS VACIA¡ - Procederemos al Registro de un Usuario.", Toast.LENGTH_SHORT).show();

                //Esperamos 50 milisegundos
                SystemClock.sleep(50);

                //Cerramos la Base de Datos
                db.close();

                //Accedemos a la Pantalla del Registro del Usuario
                Intent ListSong = new Intent(getApplicationContext(), VentanaRegistroUsuario.class);
                startActivity(ListSong);
                finish();

            }

            //Log.i(this.getClass().toString(), "Datos Iniciales INSERTADOS");
        }

        //Cerramos la Base de Datos
        //db.close();

    }
}
