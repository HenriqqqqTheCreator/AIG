package zapzap.tccetec.com.aig;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    private FirebaseAuth autenticacao;

    //ip wifi dani 192.168.123.2
    //ip wifi casa 192.168.1.238

    private static final String REGISTER_URL = "http://192.168.1.238/delaroy/volleyRegister.php";

    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        inputFullName = findViewById(R.id.editNomeCadastroID);
        inputEmail = findViewById(R.id.editEmailCadastroID);
        inputPassword = findViewById(R.id.editSenhaCadastroID);
        btnRegister = findViewById(R.id.btnCadastrarID);
        btnLinkToLogin = findViewById(R.id.btnCancelarCadastroID);

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

                /*if(inputEmail != null && inputFullName != null && inputPassword != null) {

                    Usuario usuario = new Usuario();
                    usuario.setEmail(inputEmail.getText().toString());
                    usuario.setNome(inputFullName.getText().toString());
                    usuario.setSenha(inputPassword.getText().toString());

                    ConfiguracaoFirebase configuracaoFirebase = new ConfiguracaoFirebase();

                    autenticacao = configuracaoFirebase.getAutenticacao();
                    autenticacao.createUserWithEmailAndPassword(
                            usuario.getEmail(),
                            usuario.getSenha()
                    ).addOnCompleteListener(CadastrarActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if( task.isSuccessful() ){
                                Toast.makeText(CadastrarActivity.this, "Cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(CadastrarActivity.this, "Erro no cadastro:" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else{

                    Toast.makeText(CadastrarActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();

                }*/

                registerUser();
            }
        });

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });

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
                params.put(KEY_USERNAME,username);
                params.put(KEY_PASSWORD,password);
                params.put(KEY_EMAIL, email);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}