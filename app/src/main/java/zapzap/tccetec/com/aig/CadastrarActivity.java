package zapzap.tccetec.com.aig;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import zapzap.tccetec.com.aig.R;
import zapzap.tccetec.com.aig.classesdeconexao.ConfiguracaoFirebase;
import zapzap.tccetec.com.aig.classesdeconexao.Usuario;
import zapzap.tccetec.com.aig.helper.SQLiteHandler;
import zapzap.tccetec.com.aig.helper.SessionManager;
import zapzap.tccetec.com.aig.licao.LicaoActivity;

public class CadastrarActivity extends AppCompatActivity {

    private Button btnCadastrar, btnCancelar;

    //androidHive
    private static final String TAG = CadastrarActivity.class.getSimpleName();
    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText inputFullName;
    private EditText inputEmail;
    private EditText inputPassword;
    private EditText inputSenhaConfirn;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    private String emailConfirm;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private TextView confirmarSenha;

    private FirebaseAuth autenticacao;

    //ip wifi dani 192.168.123.2
    //ip wifi casa 192.168.1.238

//    private static final String REGISTER_URL = "http://192.168.1.238/delaroy/volleyRegister.php";
    //private static final String REGISTER_URL = "http://10.0.2.2/delaroy/volleyRegister.php";
    private static final String REGISTER_URL = "http://aigdevelopment.000webhostapp.com/volleyRegister.php";

    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_EMAIL = "email";

    public static final String KEY_TESTE_EMAIL = "tb_email";
    public static final String KEY_TESTE_NOME = "tb_nome";
    public static final String KEY_TESTE_SENHA = "tb_senha";
    public static final String KEY_TESTE_PONTOS = "tb_pontos";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        overridePendingTransition(R.anim.activity_filho_entrando, R.anim.activity_pai_saindo);

        inputFullName = findViewById(R.id.editNomeCadastroID);
        inputEmail = findViewById(R.id.editEmailCadastroID);
        inputPassword = findViewById(R.id.editSenhaCadastroID);
        btnRegister = findViewById(R.id.btnCadastrarID);
        inputSenhaConfirn = findViewById(R.id.editSenhConfirmar);
        confirmarSenha = findViewById(R.id.textView8);


        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(CadastrarActivity.this,
                    MenuActivity.class);
            startActivity(intent);
            finish();
        }

        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if (inputEmail != null && inputFullName != null && inputPassword != null && inputSenhaConfirn != null){

                    emailConfirm = inputEmail.getText().toString().trim();

                if (isEmailValid(emailConfirm)) {

                    String senhaUM = inputPassword.getText().toString();
                    String senhaDois = inputSenhaConfirn.getText().toString();

                    if (senhaUM.equals(senhaDois)) {

                        registerUser();

                    } else {


                        confirmarSenha.setVisibility(View.VISIBLE);

                        Handler handler = new Handler();

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                confirmarSenha.setVisibility(View.INVISIBLE);
                            }
                        }, 5000);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Digite um email valido", Toast.LENGTH_SHORT).show();
                }

            }else{
                    Toast.makeText(CadastrarActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                }

        }
        });

    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    private void registerUser(){
        final String username = inputFullName.getText().toString().trim();
        final String password = inputPassword.getText().toString().trim();
        final String email = inputEmail.getText().toString().trim();

        Intent passarinfo = new Intent(CadastrarActivity.this, LicaoActivity.class);
        passarinfo.putExtra("LOGIN", email);
        passarinfo.putExtra("SENHA", password);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(CadastrarActivity.this, response, Toast.LENGTH_SHORT).show();

                        if(response.equals("Pronto! Agora tente logar!")){
                            startActivity(new Intent(CadastrarActivity.this, LoginActivity.class));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CadastrarActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_TESTE_EMAIL, email);
                params.put(KEY_TESTE_NOME, username);
                params.put(KEY_TESTE_SENHA, password);
                params.put(KEY_TESTE_PONTOS, "0");
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}