package zapzap.tccetec.com.aig.licao;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.widget.RelativeLayout.LayoutParams;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import zapzap.tccetec.com.aig.CadastrarActivity;
import zapzap.tccetec.com.aig.LoginActivity;
import zapzap.tccetec.com.aig.ProvaFinalHEHEActivity;
import zapzap.tccetec.com.aig.R;
import zapzap.tccetec.com.aig.fragment.SegundoFragment;

public class TemaActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighScore";
    public static final String KEY_CATEGORIA_QUEST = "keyCategoria";
    public static final String SHARED_PROVA = "sharedProva";
    public static final String KEY_INICIAR_PROVA = "keyStart";

    private int highscore;

    private static final int REQUEST_CODE_QUIZ = 1;

    private static final String GET_PONTUACAO = "http://10.0.2.2/delaroy/get_pontuacao.php";


//    private static final String GET_PONTUACAO = "http://192.168.1.238/delaroy/get_pontuacao.php";

    private TextView tvTitle, tvDescription, tvCategory;
    private ImageView img;
    private int background;
    private ListView listView;
    private LinearLayout linearLayoutTema;
    private Intent listIntent;
    private Intent passarID;
    private Intent data;
    private Button btnQuest;
    final Context context = this;

    private boolean primeiroPontoSet = false;

    private ArrayList<String> students;
    private JSONArray result;

    private int[] IMAGESIni = {R.drawable.mercadoacoes,
            R.drawable.mercadocapitais,
            R.drawable.comoinvestir,
            R.drawable.porqueabolsa,
            R.drawable.comoganhar,
            R.drawable.fatores,
            R.drawable.woman};

    private int[] IMAGESInter = {R.drawable.teamwork,
            R.drawable.calculator,
            R.drawable.archive,
            R.drawable.graphtres,
            R.drawable.poor,
            R.drawable.presentationum,
            R.drawable.notes,
            R.drawable.businessman};

    private int[] IMAGESAvan = {R.drawable.gold,
            R.drawable.cityscape,
            R.drawable.investment,
            R.drawable.store,
            R.drawable.grocery,
            R.drawable.receipt};


    private String[] NAMESIni = {"Mercado de ações",
            "Mercado de capitais",
            "Como investir?",
            "Porque a bolsa de valores?",
            "Como lucrar na Bolsa de Valores?",
            "Fatores internos e externos",
            "Como tomar decisões"};

    private String[] NAMESInter = {"Concorrencia no mercado",
            "A contabilidade",
            "Conceitos gerais da contabilidade",
            "Evolução da contabilidade",
            "Finalidade da contabilidade",
            "Campo de aplicação",
            "Principios de contabilidade",
            "Educação Financeira"};

    private String[] NAMESAvan = {"Tesouro direto",
            "Fundos Imobiliários",
            "Debêntures",
            "Letras de crédito imobiliário",
            "Letras de crédito do Agronegócio",
            "Como investir em ações"};

    private Intent categoriaQuest;

    private int pontum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tema);

        listIntent = new Intent(TemaActivity.this, LicaoActivity.class);

        categoriaQuest = new Intent(TemaActivity.this, QuestActivity.class);
        Usuario mUsuario = new Usuario();

        data = getIntent();

        btnQuest = new Button(this);
        btnQuest.setText("HORA DO QUIZ");
        btnQuest.setBackgroundColor(getColor(R.color.colorPrimaryDark));
        btnQuest.setTextColor(getColor(R.color.colorAccent));

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int data = prefs.getInt(QuestActivity.KEY_SAVED_SCORE, 0); //no id: default value

        loadHighScore();

        //tvDescription = (TextView) findViewById(R.id.temaDescricao);
        img = (ImageView) findViewById(R.id.temathumbnail);


        // Recieve data
        Intent intent = getIntent();
        String Title = intent.getExtras().getString("Title");
        String Description = intent.getExtras().getString("Description");
        int image = intent.getExtras().getInt("Thumbnail");
        background = intent.getExtras().getInt("CorFundo");


        // Setting values
        img.setImageResource(image);
        img.setBackground(getDrawable(background));


        //List view
        listView = findViewById(R.id.listviewLicoes);

        CustomAdapter customAdapter = new CustomAdapter();

        listView.setAdapter(customAdapter);

        listViewLicoesListener();

        listView.addFooterView(btnQuest);

        students = new ArrayList<String>();

    }

    public String getIntentString() {

        // Recieve data
        Intent intent = getIntent();

        String Title = intent.getExtras().getString("Title");

        return Title;
    }

    class CustomAdapter extends BaseAdapter {

        Intent intent = getIntent();

        @Override
        public int getCount() {
            return IMAGESIni.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater inflater = getLayoutInflater();
            view = inflater.inflate(R.layout.listviewlayout, null);


            if (getIntentString().equals("Iniciante")) {

                categoriaQuest.putExtra("categoria", "Iniciante");

                SharedPreferences prefs = getSharedPreferences(SHARED_PROVA, MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(KEY_INICIAR_PROVA, "NO");
                editor.apply();

                TextView txtTitulo = view.findViewById(R.id.tvListView);
                ImageView imageView = view.findViewById(R.id.ivListView);


                txtTitulo.setText(NAMESIni[i]);
                imageView.setImageResource(IMAGESIni[i]);

                btnQuest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // custom dialog
                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.custom_dialog);
                        dialog.setTitle("Title...");

                        // set the custom dialog components - text, image and button
                        TextView text = dialog.findViewById(R.id.textDialogOk);
                        String textOk = "Hora de testar tudo o que você leu! \n" + "Você só tera 20 segundos para escolher uma resposta\n" + "Preparado?";
                        text.setText(textOk);

                        Button dialogButton = dialog.findViewById(R.id.dialogButtonOK);
                        // if button is clicked, close the custom dialog
                        dialogButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();

                                categoriaQuest.putExtra(KEY_CATEGORIA_QUEST, "Iniciante");
                                startActivity(categoriaQuest);
                            }
                        });

                        dialog.show();

                    }
                });

            } else if (getIntentString().equals("Intermediario")) {

                if (i < NAMESInter.length) {

                    SharedPreferences prefs = getSharedPreferences(SHARED_PROVA, MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(KEY_INICIAR_PROVA, "NO");
                    editor.apply();

                    TextView txtTitulo = view.findViewById(R.id.tvListView);
                    ImageView imageView = view.findViewById(R.id.ivListView);

                    txtTitulo.setText(NAMESInter[i]);
                    imageView.setImageResource(IMAGESInter[i]);

                    btnQuest.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // custom dialog
                            final Dialog dialog = new Dialog(context);
                            dialog.setContentView(R.layout.custom_dialog);
                            dialog.setTitle("Title...");

                            // set the custom dialog components - text, image and button
                            TextView text = dialog.findViewById(R.id.textDialogOk);
                            String textOk = "Hora de testar tudo o que você leu! \n" + "Você só tera 20 segundos para escolher uma resposta\n" + "Preparado?";
                            text.setText(textOk);

                            Button dialogButton = dialog.findViewById(R.id.dialogButtonOK);
                            // if button is clicked, close the custom dialog
                            dialogButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();

                                    startActivity(new Intent(TemaActivity.this, QuestInterActivity.class));
                                }
                            });

                            dialog.show();

                        }
                    });
                    listIntent.putExtra("categoria", "Intermediario");

                } else {

                    CardView cardView = view.findViewById(R.id.cardViewCustomLayout);
                    cardView.setBackgroundColor(getResources().getColor(R.color.fundoFragment, null));

                }

            } else if (getIntentString().equals("Avançado")) {

                if (i < NAMESAvan.length) {

                    SharedPreferences prefs = getSharedPreferences(SHARED_PROVA, MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(KEY_INICIAR_PROVA, "NO");
                    editor.apply();

                    TextView txtTitulo = view.findViewById(R.id.tvListView);
                    ImageView imageView = view.findViewById(R.id.ivListView);

                    txtTitulo.setText(NAMESAvan[i]);
                    imageView.setImageResource(IMAGESAvan[i]);

                    btnQuest.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            // custom dialog
                            final Dialog dialog = new Dialog(context);
                            dialog.setContentView(R.layout.custom_dialog);
                            dialog.setTitle("Title...");

                            // set the custom dialog components - text, image and button
                            TextView text = dialog.findViewById(R.id.textDialogOk);
                            String textOk = "Hora de testar tudo o que você leu! \n" + "Você só tera 20 segundos para escolher uma resposta\n" + "Preparado?";
                            text.setText(textOk);

                            Button dialogButton = dialog.findViewById(R.id.dialogButtonOK);
                            // if button is clicked, close the custom dialog
                            dialogButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();

                                    startActivity(new Intent(TemaActivity.this, QuestAvanActivity.class));
                                }
                            });

                            dialog.show();

                        }
                    });
                    listIntent.putExtra("categoria", "Avançado");


                } else {

                    CardView cardView = view.findViewById(R.id.cardViewCustomLayout);
                    cardView.setBackgroundColor(getResources().getColor(R.color.fundoFragment, null));
                    cardView.setClickable(false);

                    LayoutParams params = new LayoutParams(
                            0,
                            0
                    );
                    cardView.setLayoutParams(params);

                }

            } else if (getIntentString().equals("Prova Final")) {

                SharedPreferences prefse = getSharedPreferences(SegundoFragment.SHARED_SECONDFRAGPREF, MODE_PRIVATE);
                Boolean restoredOne = prefse.getBoolean("ta liberado", false);

                if(i < 1) {
                    if(restoredOne == true){
                        Intent intentG = new Intent(TemaActivity.this, ProvaFinalHEHEActivity.class);
                        intentG.putExtra("VAIDARNAO", true);
                        startActivity(intentG);
                    }else if(restoredOne == false){
                        Intent intentG = new Intent(TemaActivity.this, ProvaFinalHEHEActivity.class);
                        intentG.putExtra("VAIDARNAO", false);
                        startActivity(intentG);
                    }
                    finish();
                }else{
                    finish();
                }
                /*
                if (i < 1) {

                listView.removeFooterView(btnQuest);

                CardView cardView = view.findViewById(R.id.cardViewCustomLayout);
                cardView.setBackgroundColor(getResources().getColor(R.color.fundoFragment, null));

                LayoutParams params = new LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                );

                cardView.setLayoutParams(params);

                TextView txtTitulo = view.findViewById(R.id.tvListView);
                ImageView imageView = view.findViewById(R.id.ivListView);

                txtTitulo.setText("Quiz final!");
                imageView.setImageResource(R.drawable.pen);

            } else {

                CardView cardView = view.findViewById(R.id.cardViewCustomLayout);
                cardView.setBackgroundColor(getResources().getColor(R.color.fundoFragment, null));
                cardView.setClickable(false);

                LayoutParams params = new LayoutParams(
                        0,
                        0
                );
                cardView.setLayoutParams(params);

            }
            */
            //cardView.setClickable(false);

            //LayoutParams params = new LayoutParams(
            //        0,
            //        0
            //);

            //cardView.setLayoutParams(params);

        }

            return view;
    }

}

    public void listViewLicoesListener() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                if (getIntentString().equals("Iniciante")) {

                    String itemSelecionado = NAMESIni[i];
                    listIntent.putExtra("TituloList", itemSelecionado);
                    listIntent.putExtra("pid", i);

                    listIntent.putExtra("categoria", "iniciante");

                } else if (getIntentString().equals("Intermediario")) {

                    String itemSelecionado = NAMESInter[i];
                    listIntent.putExtra("TituloList", itemSelecionado);
                    listIntent.putExtra("pid", i);

                    listIntent.putExtra("categoria", "intermediario");

                } else if (getIntentString().equals("Avançado")) {

                    String itemSelecionado = NAMESAvan[i];
                    listIntent.putExtra("TituloList", itemSelecionado);
                    listIntent.putExtra("pid", i);

                    listIntent.putExtra("categoria", "avançado");


                }

                startActivity(listIntent);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_QUIZ) {
            if (resultCode == RESULT_OK) {
                int score = data.getIntExtra(QuestActivity.EXTRA_SCORE, 0);
                if (score > highscore) {
                    updateHighscore(score);
                }
            }
        }
    }

    private void loadHighScore() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        highscore = prefs.getInt(KEY_HIGHSCORE, 0);
    }

    private void updateHighscore(int highscoreNew) {
        highscore = highscoreNew;

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGHSCORE, highscore);
        editor.apply();
    }

}
