package zapzap.tccetec.com.aig.licao;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import zapzap.tccetec.com.aig.R;

public class TemaActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighScore";

    private TextView textViewHighScore;

    private int highscore;

    private static final int REQUEST_CODE_QUIZ = 1;

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

    private int[] IMAGESIni = {R.drawable.mercadoacoes,
            R.drawable.mercadocapitais,
            R.drawable.comoinvestir,
            R.drawable.porqueabolsa,
            R.drawable.comoganhar,
            R.drawable.fatores,
            R.drawable.woman};

    private int[] IMAGESInter = {R.drawable.coin_clean,
            R.drawable.suitcase,
            R.drawable.pricetagquatro};

    private int[] IMAGESAvan = {R.drawable.coin_clean,
            R.drawable.suitcase,
            R.drawable.pricetagquatro};


    private String[] NAMESIni = {"Mercado de ações",
            "Mercado de capitais",
            "Como investir?",
            "Porque a bolsa de valores?",
            "Como lucrar na Bolsa de Valores?",
            "Fatores internos e externos",
            "Como tomar decisões"};

    private String[] NAMESInter = {"Intermediario 1",
            "Intermediario 2",
            "Intermediario 3"};

    private String[] NAMESAvan = {"Avançado 1",
            "Avançado 2",
            "Avançado 3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tema);

        listIntent = new Intent(TemaActivity.this, LicaoActivity.class);

        Usuario mUsuario = new Usuario();

        data = getIntent();

        btnQuest = new Button(this);
        btnQuest.setText("HORA DO QUIZ");
        btnQuest.setBackgroundColor(getColor(R.color.colorPrimaryDark));
        btnQuest.setTextColor(getColor(R.color.colorAccent));

        textViewHighScore = findViewById(R.id.text_view_temaScore);
        textViewHighScore.setHeight(0);
        textViewHighScore.setVisibility(View.INVISIBLE);
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

        listView.addFooterView(btnQuest);

        listViewLicoesListener();


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

                Intent categoriaQuest = new Intent(TemaActivity.this, QuestActivity.class);
                categoriaQuest.putExtra("categoria", "Iniciante");

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
                        TextView text = dialog.findViewById(R.id.text);
                        text.setText("A prova será em formato de quiz e você terá somente 30 segundos!\n Preparado?");

                        Button dialogButton = dialog.findViewById(R.id.dialogButtonOK);
                        // if button is clicked, close the custom dialog
                        dialogButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();

                                Intent intentQuiz = new Intent(TemaActivity.this, QuestActivity.class);
                                startActivityForResult(intentQuiz, REQUEST_CODE_QUIZ);
                            }
                        });

                        dialog.show();

                    }
                });

            } else if (getIntentString().equals("Intermediario")) {

                if (i < NAMESInter.length) {

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
                            TextView text = dialog.findViewById(R.id.text);
                            text.setText("A prova será em formato de quiz e você terá somente 30 segundos!\n Preparado?");

                            Button dialogButton = dialog.findViewById(R.id.dialogButtonOK);
                            // if button is clicked, close the custom dialog
                            dialogButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();

                                    startActivity(new Intent(TemaActivity.this, QuestActivity.class));
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

                if (i < NAMESInter.length) {

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
                            TextView text = dialog.findViewById(R.id.text);
                            text.setText("A prova será em formato de quiz e você terá somente 30 segundos!\n Preparado?");

                            Button dialogButton = dialog.findViewById(R.id.dialogButtonOK);
                            // if button is clicked, close the custom dialog
                            dialogButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();

                                    startActivity(new Intent(TemaActivity.this, QuestActivity.class));
                                }
                            });

                            dialog.show();

                        }
                    });
                    listIntent.putExtra("categoria", "Avançado");


                } else {

                    CardView cardView = view.findViewById(R.id.cardViewCustomLayout);
                    cardView.setBackgroundColor(getResources().getColor(R.color.fundoFragment, null));

                }

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
        textViewHighScore.setVisibility(View.VISIBLE);
        textViewHighScore.setHeight(100);
        textViewHighScore.setText("Pontuação máxima: " + highscore);
    }

    private void updateHighscore(int highscoreNew) {
        highscore = highscoreNew;
        textViewHighScore.setVisibility(View.VISIBLE);
        textViewHighScore.setHeight(100);
        textViewHighScore.setText("Pontuação máxima: " + highscore);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGHSCORE, highscore);
        editor.apply();
    }

}
