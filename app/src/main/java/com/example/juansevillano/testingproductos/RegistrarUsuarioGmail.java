package com.example.juansevillano.testingproductos;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
    private EditText editTextNombre;
    private EditText editTextApellidos;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignup;
    private ProgressDialog progressDialog;
    private TextView textViewir;
    private CheckBox checkPrivacidad;


    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;

    //Datos del Usuario
    String nombregmail;
    String correo;
    Uri photoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario_gmail);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //initializing views
        editTextNombre = (EditText) findViewById(R.id.editTextNombre);
        editTextApellidos = (EditText) findViewById(R.id.editTextApellidos);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        checkPrivacidad = (CheckBox) findViewById(R.id.checkBoxAceptarPrivacidad);

        textViewir = (TextView) findViewById(R.id.text1);
        textViewir.setPaintFlags(textViewir.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        buttonSignup = (Button) findViewById(R.id.buttonSignup);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        buttonSignup.setOnClickListener(this);
        textViewir.setOnClickListener(this);


    }

    private void registerUser() {

        //getting email and password from edit texts
        String nombre = editTextNombre.getText().toString().trim();
        String apellidos = editTextApellidos.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if (TextUtils.isEmpty(nombre)) {
            Toast.makeText(this, "Por favor inserte su nombre.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(apellidos)) {
            Toast.makeText(this, "Por favor inserte sus apellidos.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Por favor inserte email.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Por favor inserte contraseña.", Toast.LENGTH_LONG).show();
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
                        if (task.isSuccessful()) {
                            //display some message here
                            Toast.makeText(RegistrarUsuarioGmail.this, "Usuario Registrado Correctamente.", Toast.LENGTH_LONG).show();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            nombregmail = user.getDisplayName();
                            correo = user.getEmail();
                            photoUrl = user.getPhotoUrl();

                            //Accedemos a la Ventana Iniciar Sesión con Gmail.
                            Intent ListSong = new Intent(getApplicationContext(), IniciarSesionGmail.class);
                            startActivity(ListSong);
                            finish();

                        } else {
                            //display some message here
                            Toast.makeText(RegistrarUsuarioGmail.this, "Error de Registro. Email o Contraseña no Correcto. El Email debe de estar creado en gmail y la contraseña a introducir es la del correo que usted inserte.", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void onClick(View view) {
        //calling register method on click
        if (view == buttonSignup) {
            if(checkPrivacidad.isChecked()==true)
            {
                registerUser();
            }
            else
            {
                Toast.makeText(RegistrarUsuarioGmail.this, "Debes ACETAR las Políticas de Privacidad para poder Registrarte.", Toast.LENGTH_LONG).show();
            }

        }

        if (view == textViewir) {
            createSimpleDialog();
        }
    }

    /**
     * Crea un diálogo de alerta sencillo
     *
     * @return Nuevo diálogo
     */
    public void createSimpleDialog() {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);

        alertDialog.setMessage(
                "El presente Política de Privacidad establece los términos en que http://tfgalimentos.16mb.com usa y protege la información que es proporcionada por sus usuarios al momento de utilizar su sitio web. Esta compañía está comprometida con la seguridad de los datos de sus usuarios. Cuando le pedimos llenar los campos de información personal con la cual usted pueda ser identificado, lo hacemos asegurando que sólo se empleará de acuerdo con los términos de este documento. Sin embargo esta Política de Privacidad puede cambiar con el tiempo o ser actualizada por lo que le recomendamos y enfatizamos revisar continuamente esta página para asegurarse que está de acuerdo con dichos cambios.\n" +
                        "\nInformación que es recogida\n" +
                        "Nuestro sitio web podrá recoger información personal por ejemplo: Nombre,  información de contacto como  su dirección de correo electrónica e información demográfica. Así mismo cuando sea necesario podrá ser requerida información específica para procesar algún pedido o realizar una entrega o facturación.\n" +
                        "\nUso de la información recogida\n" +
                        "Nuestro sitio web emplea la información con el fin de proporcionar el mejor servicio posible, particularmente para mantener un registro de usuarios, de pedidos en caso que aplique, y mejorar nuestros productos y servicios.  Es posible que sean enviados correos electrónicos periódicamente a través de nuestro sitio con ofertas especiales, nuevos productos y otra información publicitaria que consideremos relevante para usted o que pueda brindarle algún beneficio, estos correos electrónicos serán enviados a la dirección que usted proporcione y podrán ser cancelados en cualquier momento.\n" +
                        "http://tfgalimentos.16mb.com está altamente comprometido para cumplir con el compromiso de mantener su información segura. Usamos los sistemas más avanzados y los actualizamos constantemente para asegurarnos que no exista ningún acceso no autorizado.\n" +
                        "\nCookies\n" +
                        "Una cookie se refiere a un fichero que es enviado con la finalidad de solicitar permiso para almacenarse en su ordenador, al aceptar dicho fichero se crea y la cookie sirve entonces para tener información respecto al tráfico web, y también facilita las futuras visitas a una web recurrente. Otra función que tienen las cookies es que con ellas las web pueden reconocerte individualmente y por tanto brindarte el mejor servicio personalizado de su web.\n" +
                        "Nuestro sitio web emplea las cookies para poder identificar las páginas que son visitadas y su frecuencia. Esta información es empleada únicamente para análisis estadístico y después la información se elimina de forma permanente. Usted puede eliminar las cookies en cualquier momento desde su ordenador. Sin embargo las cookies ayudan a proporcionar un mejor servicio de los sitios web, estás no dan acceso a información de su ordenador ni de usted, a menos de que usted así lo quiera y la proporcione directamente. Usted puede aceptar o negar el uso de cookies, sin embargo la mayoría de navegadores aceptan cookies automáticamente pues sirve para tener un mejor servicio web. También usted puede cambiar la configuración de su ordenador para declinar las cookies. Si se declinan es posible que no pueda utilizar algunos de nuestros servicios.\n" +
                        "\nEnlaces a Terceros\n" +
                        "Este sitio web pudiera contener en laces a otros sitios que pudieran ser de su interés. Una vez que usted de clic en estos enlaces y abandone nuestra página, ya no tenemos control sobre al sitio al que es redirigido y por lo tanto no somos responsables de los términos o privacidad ni de la protección de sus datos en esos otros sitios terceros. Dichos sitios están sujetos a sus propias políticas de privacidad por lo cual es recomendable que los consulte para confirmar que usted está de acuerdo con estas.\n" +
                        "\nControl de su información personal\n" +
                        "En cualquier momento usted puede restringir la recopilación o el uso de la información personal que es proporcionada a nuestro sitio web.  Cada vez que se le solicite rellenar un formulario, como el de alta de usuario, puede marcar o desmarcar la opción de recibir información por correo electrónico.  En caso de que haya marcado la opción de recibir nuestro boletín o publicidad usted puede cancelarla en cualquier momento.\n" +
                        "Esta compañía no venderá, cederá ni distribuirá la información personal que es recopilada sin su consentimiento, salvo que sea requerido por un juez con un orden judicial.\n" +
                        "http://tfgalimentos.16mb.com Se reserva el derecho de cambiar los términos de la presente Política de Privacidad en cualquier momento.\n");
        alertDialog.setTitle("POLITICA DE PRIVACIDAD.");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //No Realizamos ninguna Accion

            }
        });
        /*alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getBaseContext(), "Saliendo de la Aplicación....", Toast.LENGTH_SHORT).show();
                //Esperamos 50 milisegundos
                SystemClock.sleep(500);
                finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });*/

        alertDialog.show();
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
