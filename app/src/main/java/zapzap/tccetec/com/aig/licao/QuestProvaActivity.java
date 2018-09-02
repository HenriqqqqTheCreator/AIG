package zapzap.tccetec.com.aig.licao;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import zapzap.tccetec.com.aig.CadastrarActivity;
import zapzap.tccetec.com.aig.LoginActivity;
import zapzap.tccetec.com.aig.R;

import static android.content.Intent.FLAG_ACTIVITY_MULTIPLE_TASK;

public class QuestProvaActivity extends AppCompatActivity {

    public static final String EXTRA_SCORE = "extraScore";
    private static final long COUNTDOWN_IN_MILLIS = 20000;
    public static final String KEY_SAVED_SCORE = "savedScoreIniciante";

    private Button btnRespostaUm, btnRespostaDois, btnRespostaTres, btnRespostaQuatro;
    private TextView tvPergunta;

    private ColorStateList textColorDafaultCd;
    private CountDownTimer countDownTimer;

    private long timeLeftInMillis;
    private long backPressedTime;

    private Context context = this;

    private List<Question> questionList;
    private TextView textViewContagem;
    private TextView textViewPontuacao;
    private TextView textViewQuestionCount;
    private Button btnProximaPergunta;

    private Usuario usuario = new Usuario();

    private ColorStateList textColorDefaultRb;
    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;

    private int score;
    private boolean answered;
    private String categoriaShared;

   // private static final String SAVE_PONTUACAO = "http://192.168.1.238/delaroy/get_data.php";

    private static final String SAVE_PONTUACAO = "http://10.0.2.2/delaroy/get_data.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest);

        overridePendingTransition(R.anim.activity_filho_entrando, R.anim.activity_pai_saindo);

        Intent intent = getIntent();

        SharedPreferences prefs = getSharedPreferences(TemaActivity.SHARED_PREFS, MODE_PRIVATE);
        categoriaShared = prefs.getString(TemaActivity.KEY_CATEGORIA_QUEST, "lecal");

        btnRespostaUm = findViewById(R.id.btnRespostaUm);
        btnRespostaDois = findViewById(R.id.btnRespostaDois);
        btnRespostaTres = findViewById(R.id.btnRespostaTres);
        btnRespostaQuatro = findViewById(R.id.btnRespostaQuatro);
        tvPergunta = findViewById(R.id.tvPergunta);
        btnProximaPergunta = findViewById(R.id.btnProximaPergunta);


        textViewContagem = findViewById(R.id.text_view_contagem);
        textViewPontuacao = findViewById(R.id.text_view_pontuacao);
        textViewQuestionCount = findViewById(R.id.text_view_question_count);

        textColorDefaultRb = btnRespostaUm.getTextColors();
        textColorDafaultCd = textViewContagem.getTextColors();

        QuizDbHelperProva dbHelper = new QuizDbHelperProva(this);
        questionList = dbHelper.getAllQuestions();
        questionCountTotal = questionList.size();
        Collections.shuffle(questionList);

        showNextQuestion();

        clickRespostas();


    }

    private void showNextQuestion() {

        btnProximaPergunta.setVisibility(View.INVISIBLE);

        btnRespostaUm.setTextColor(textColorDefaultRb);
        btnRespostaDois.setTextColor(textColorDefaultRb);
        btnRespostaTres.setTextColor(textColorDefaultRb);
        btnRespostaQuatro.setTextColor(textColorDefaultRb);

        if (questionCounter < questionCountTotal) {

            currentQuestion = questionList.get(questionCounter);

            tvPergunta.setText(currentQuestion.getQuestion());
            btnRespostaUm.setText(currentQuestion.getOption1());
            btnRespostaDois.setText(currentQuestion.getOption2());
            btnRespostaTres.setText(currentQuestion.getOption3());
            btnRespostaQuatro.setText(currentQuestion.getOption4());

            questionCounter++;
            textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionCountTotal);

            timeLeftInMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();
            answered = false;

        } else {

            SharedPreferences prefsQuest = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editorQuesta = prefsQuest.edit();
            editorQuesta.putInt(KEY_SAVED_SCORE, score); //InputString: from the EditText
            editorQuesta.commit();


            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.custom_dialog);
            dialog.setTitle("Title...");

            // set the custom dialog components - text, image and button
            TextView text = dialog.findViewById(R.id.textDialogOk);
            if(score == 0){

                text.setText("Pontuação insuficiente");
            }else if(score == 1){

                text.setText("Pontuação insuficiente");
            } else if(score == 2){

                text.setText("Pontuação insuficiente");
            } else if(score == 3){

                text.setText("Pontuação insuficiente");
            } else if(score == 4){

                text.setText("Pontuação insuficiente");
            } else if(score == 5){

                text.setText("Parabens! \n Você acertou 50% do quiz, em breve você receberá um email com seu certificado.");
            }else if(score == 6){

                text.setText("Parabens! \n Você acertou 60% do quiz, em breve você receberá um email com seu certificado.");
            }else if(score == 7){

                text.setText("Parabens! \\n Você acertou 70% do quiz, em breve você receberá um email com seu certificado.");
            }else if(score == 8){

                text.setText("Parabens! \\n Você acertou 80% do quiz, em breve você receberá um email com seu certificado.");
            }else if(score == 9){

                text.setText("Parabens! \\n Você acertou 90% do quiz, em breve você receberá um email com seu certificado.");
            }else if(score == 10){

                text.setText("Parabens! \\n Você acertou 100% do quiz, em breve você receberá um email com seu certificado.");
            }

            Button dialogButton = dialog.findViewById(R.id.dialogButtonOK);
            dialogButton.setText("OK!");
            // if button is clicked, close the custom dialog

            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();

                    finishQuiz();

                }
            });

            dialog.show();


        }
    }


    private void startCountDown(){

        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMillis = l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
                checkAnswer(9);
            }
        }.start();

    }

    private void updateCountDownText(){

        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        textViewContagem.setText(timeFormatted);

        if(timeLeftInMillis < 10000){
            textViewContagem.setTextColor(Color.RED);
        }else{
            textViewContagem.setTextColor(textColorDafaultCd);
        }
    }

    private void checkAnswer(int botaoSelecionado) {

        answered = true;

        countDownTimer.cancel();

        int answerNr = botaoSelecionado;
        int currentAnswer = currentQuestion.getAnswerNr();

        if (answerNr == currentAnswer) {
            score++;
            usuario.setHighscore(score);
            textViewPontuacao.setText("Score: " + score);
        }

        showSolution();

    }

    private void showSolution() {

        switch (currentQuestion.getAnswerNr()) {
            case 1:
                btnRespostaUm.setTextColor(Color.GREEN);
                break;
            case 2:
                btnRespostaDois.setTextColor(Color.GREEN);
                break;
            case 3:
                btnRespostaTres.setTextColor(Color.GREEN);
                break;
            case 4:
                btnRespostaQuatro.setTextColor(Color.GREEN);
                break;
        }

        if (questionCounter < questionCountTotal) {

            btnProximaPergunta.setText("PROXIMA PERGUNTA");
            btnProximaPergunta.setVisibility(View.VISIBLE);

        } else {

            btnProximaPergunta.setText("Finalizar");
            btnProximaPergunta.setVisibility(View.VISIBLE);
        }
    }

    private void clickRespostas() {

        btnProximaPergunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNextQuestion();
            }
        });

        btnRespostaUm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(1);
            }
        });

        btnRespostaDois.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(2);
            }
        });

        btnRespostaTres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(3);
            }
        });

        btnRespostaQuatro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(4);
            }
        });

    }

    private void finishQuiz() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SCORE, score);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(countDownTimer != null){
            countDownTimer.cancel();
        }
    }
}