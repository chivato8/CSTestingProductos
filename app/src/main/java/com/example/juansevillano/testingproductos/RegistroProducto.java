package com.example.juansevillano.testingproductos;

import android.content.Intent;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.otto.Subscribe;

import java.util.List;

import static android.content.ContentValues.TAG;

public class RegistroProducto extends Fragment{

    public EditText codbarra;

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_registro_producto, container, false);
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
}
