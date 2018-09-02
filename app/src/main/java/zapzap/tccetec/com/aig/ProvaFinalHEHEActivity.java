package zapzap.tccetec.com.aig;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.transitionseverywhere.*;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import zapzap.tccetec.com.aig.licao.QuestAvanActivity;
import zapzap.tccetec.com.aig.licao.QuestProvaActivity;
import zapzap.tccetec.com.aig.licao.TemaActivity;

public class ProvaFinalHEHEActivity extends AppCompatActivity {

    private TextView txtCalmala, txtFazOsquiz;
    private Button btnComeçarProva;
    private int i = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prova_final_hehe);

        btnComeçarProva = findViewById(R.id.btn_vamos_para_a_prova);
        txtCalmala = findViewById(R.id.txtCalmala);
        txtFazOsquiz = findViewById(R.id.txtFazOsQuizMan);

        Intent intentHE = getIntent();
        boolean deuOuNaoDeu = intentHE.getExtras().getBoolean("VAIDARNAO");

        if(deuOuNaoDeu == true){

            txtCalmala.setText("MUITO BEM!");
            txtFazOsquiz.setText("Você chegou ao ultimo estágio. Ao acertar 50% do quiz, você ganhara um certificado que comprava a conclusão do nosso curso de \n Como Investir na Bolsa de Valores");
            btnComeçarProva.setText("Vamos começar!");

            btnComeçarProva.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    startActivity(new Intent(ProvaFinalHEHEActivity.this, QuestProvaActivity.class));

                }
            });

        }else if(deuOuNaoDeu == false){

            txtCalmala.setText("OPA! Vamos com calma!");
            txtFazOsquiz.setText("Para realizar a prova final é necessario completar os quiz anteriores.");
            btnComeçarProva.setText("OK!");
            btnComeçarProva.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    finish();

                }
            });
        }

    }

}
