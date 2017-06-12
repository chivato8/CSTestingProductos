package com.example.juansevillano.testingproductos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IniciarSesionGmail extends AppCompatActivity implements View.OnClickListener {

    //defining view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup;
    private ProgressDialog progressDialog;
    private TextView textViewRegistrarse;

    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;

    //Datos del Usuario
    String nombre;
    String correo;
    Uri photoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion_gmail);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        buttonSignup = (Button) findViewById(R.id.buttonSignup);

        textViewRegistrarse = (TextView) findViewById(R.id.textViewRegistrarse);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        buttonSignup.setOnClickListener(this);
        textViewRegistrarse.setOnClickListener(this);
    }

    private void registerUser(){

        //getting email and password from edit texts
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Por favor inserte email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Por favor inserte contraseña",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Iniciando Sesión por favor espere...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            //display some message here
                            Toast.makeText(IniciarSesionGmail.this,"Usuario Registrado Correctamente",Toast.LENGTH_LONG).show();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            nombre = user.getDisplayName();
                            correo = user.getEmail();
                            photoUrl = user.getPhotoUrl();

                        }else{
                            //display some message here
                            Toast.makeText(IniciarSesionGmail.this,"Error de Inicio de Sesión. Email o Contraseña Incorrectos.",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void onClick(View view) {
        //calling register method on click
        if(view == buttonSignup){
            registerUser();
        }

        if(view == textViewRegistrarse){
            //open login activity when user taps on the already registered textview
            startActivity(new Intent(this, RegistrarUsuarioGmail.class));
        }
    }

    /**
     * @name public void onBackPressed ()
     * @description Si hacemos clic en el boton hacia atras volvemos a la ventana anterior
     * @return void
     */
    @Override
    public void onBackPressed () {

        if (true)
        {
            startActivity(new Intent(this, EntrarCon.class));
            finish();
        }
        else
        {
            super.onBackPressed();
        }
    }
}
