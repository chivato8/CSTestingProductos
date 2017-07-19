package com.example.juansevillano.testingproductos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ComprobarSesion extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient googleApiClient;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private ImageView photoImageView;
    private TextView nameTextView;
    private TextView emailTextView;

    /**
     * @name protected void onCreate( Bundle savedInstanceState)
     * @description Primer Método que se llama al crear la clase
     * @return void
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprobar_sesion);

        photoImageView = (ImageView) findViewById(R.id.photoImageView);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        emailTextView = (TextView) findViewById(R.id.emailTextView);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    setUserData(user);
                    //firebaseAuth.signOut();

                    //goLogInScreen();
                } else {
                    goLogInScreen();
                }
            }
        };
    }

    /**
     * @name private void setUserData(FirebaseUser user)
     * @description Metodo para asignar los datos del usuario
     * @return void
     */
    private void setUserData(FirebaseUser user) {
        nameTextView.setText(user.getDisplayName());
        emailTextView.setText(user.getEmail());
        Glide.with(this).load(user.getPhotoUrl()).into(photoImageView);
    }

    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    /**
     * @name private void goLogInScreen()
     * @description Metodo para cerrar sesión
     * @return void
     */
    private void goLogInScreen() {
        Intent intent = new Intent(this, EntrarCon.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * @name public void logOut(View view)
     * @description Metodo para cerrar sesión
     * @return void
     */
    public void logOut(View view) {
        firebaseAuth.signOut();

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    goLogInScreen();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.not_close_session, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * @name public void revoke(View view)
     * @description Metodo para rebocar la sesión del usuario
     * @return void
     */
    public void revoke(View view) {
        Intent intent = new Intent(this, VentanaPrincipal.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * @name public void onConnectionFailed(@NonNull ConnectionResult connectionResult
     * @description Metodo por si produce un error en la conexión
     * @return void
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (firebaseAuthListener != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }
}