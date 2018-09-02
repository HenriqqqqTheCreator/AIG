package zapzap.tccetec.com.aig;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import zapzap.tccetec.com.aig.R;
import zapzap.tccetec.com.aig.helper.SQLiteHandler;
import zapzap.tccetec.com.aig.helper.SessionManager;
import zapzap.tccetec.com.aig.licao.TemaActivity;

public class LoginActivity extends AppCompatActivity {

    public static final String KEY_LOGINAC = "loginText";
    public static final String KEY_SENHAAC = "senhaText";
    public static final String SHARED_LOGIN_EMAIL = "sharedEmail";

    private TextView txtCriarConta;
    private Button btnLogar;
    private Intent abreMenu, abreCadastro;

    private RelativeLayout layout;

    //androidhive TESTE
    private static final String TAG = CadastrarActivity.class.getSimpleName();
    private EditText inputEmail;
    private ProgressDialog pDialog;
    private EditText inputPassword;
    private SessionManager session;
    private SQLiteHandler db;
    private Button mike;

    private SessionManager sessionManager;

    //public static final String LOGIN_URL = "http://10.0.2.2/delaroy/login.php";
    //public static final String LOGIN_URL = "http://192.168.56.1/delaroy/login.php";
    public static final String LOGIN_URL = "http://aigdevelopment.000webhostapp.com/login.php";

    public static final String KEY_USERNAME = "email";
    public static final String KEY_PASSWORD = "password";

    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        overridePendingTransition(R.anim.activity_filho_entrando, R.anim.activity_pai_saindo);


        sessionManager = new SessionManager(this);
        if (sessionManager.isLoggedIn()) {
            startActivity(new Intent(LoginActivity.this, MenuActivity.class));
        }

        //Intents
        abreMenu = new Intent(this, MenuActivity.class);
        abreCadastro = new Intent(this, CadastrarActivity.class);

        btnLogar = findViewById(R.id.btnLogarID);
        txtCriarConta = findViewById(R.id.txtCadastrese);

        inputEmail = findViewById(R.id.editEmailLogarID);
        inputPassword = findViewById(R.id.editSenhaLogarID);

        //mike = findViewById(R.id.btnProfs);

        onClickOuvintes();
    }


    public void onClickOuvintes() {
        /*
        mike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, MenuActivity.class));
            }
        });*/

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = inputEmail.getText().toString().trim();
                password = inputPassword.getText().toString().trim();

                if (email != null && password != null) {
                    userLogin();
                } else {
                    Toast.makeText(LoginActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CadastrarActivity.class));
            }
        });

    }

    private void userLogin() {
        email = inputEmail.getText().toString().trim();
        password = inputPassword.getText().toString().trim();

        SharedPreferences prefs = getSharedPreferences(SHARED_LOGIN_EMAIL, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_LOGINAC, email);
        editor.apply();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.trim().equals("Falha")) {

                            Toast.makeText(LoginActivity.this, "Login ou senha incorretos", Toast.LENGTH_LONG).show();

                        } else {

                            //Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
                            startActivity(new Intent(LoginActivity.this, MenuActivity.class));

                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString(CadastrarActivity.KEY_TESTE_EMAIL, email);
                            editor.putString(CadastrarActivity.KEY_TESTE_SENHA, password);
                            editor.commit();

                            sessionManager = new SessionManager(LoginActivity.this);
                            sessionManager.setLogin(true);
                            finish();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_USERNAME, email);
                map.put(KEY_PASSWORD, password);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
