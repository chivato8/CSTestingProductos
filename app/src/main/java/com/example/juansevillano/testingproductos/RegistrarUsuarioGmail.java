package com.example.juansevillano.testingproductos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
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

public class RegistrarUsuarioGmail extends AppCompatActivity implements View.OnClickListener {

    //defining view objects
    private EditText editTextNombreApellido;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup;
    private ProgressDialog progressDialog;


    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;

    //Datos del Usuario
    String nombre;
    String correo;
    Uri photoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario_gmail);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //initializing views
        editTextNombreApellido = (EditText) findViewById(R.id.editTextNombreApellido);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        buttonSignup = (Button) findViewById(R.id.buttonSignup);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        buttonSignup.setOnClickListener(this);

        TextView textView= (TextView)findViewById(R.id.text1);
        //textView.setText(Html.fromHtml("<a href=''>Leer más ..."));
        //textView.setMovementMethod(LinkMovementMethod.getInstance());

        textView.setPaintFlags(textView.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
    }

    private void registerUser(){

        //getting email and password from edit texts
        String nombreapellido = editTextNombreApellido.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(nombreapellido)){
            Toast.makeText(this,"Por favor inserte su nombre y apellidos.",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Por favor inserte email.",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Por favor inserte contraseña.",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registrando usuario espere por favor...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){
                            //display some message here
                            Toast.makeText(RegistrarUsuarioGmail.this,"Usuario Registrado Correctamente.",Toast.LENGTH_LONG).show();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            nombre = user.getDisplayName();
                            correo = user.getEmail();
                            photoUrl = user.getPhotoUrl();

                            //Accedemos a la Ventana Iniciar Sesión con Gmail.
                            Intent ListSong = new Intent(getApplicationContext(), IniciarSesionGmail.class);
                            startActivity(ListSong);
                            finish();

                        }else{
                            //display some message here
                            Toast.makeText(RegistrarUsuarioGmail.this,"Error de Registro. Email o Contraseña no Correcto. El Email debe de estar creado en gmail y la contraseña a introducir es la del correo que usted inserte.",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void onClick(View view) {
        //calling register method on click
        registerUser();
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
