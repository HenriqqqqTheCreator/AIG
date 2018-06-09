package zapzap.tccetec.com.aig.licao;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import zapzap.tccetec.com.aig.R;

import static android.content.Intent.FLAG_ACTIVITY_MULTIPLE_TASK;

public class QuestActivity extends AppCompatActivity {

    public static final String EXTRA_SCORE = "extraScore";


    private ProgressBar mProgressBar;
    private TextView mTextView;
    private Button btnRespostaUm, btnRespostaDois, btnRespostaTres, btnRespostaQuatro;
    private TextView tvPergunta;

    private int numeroDeRespostasCertas = 0;

    private Boolean finalizado = false;

    private long backPressedTime;

    //Cliques feitos
    private int btnCliqueUm = 0;
    private int btnCliqueDois = 0;
    private int btnCliqueTres = 0;
    private int btnCliqueQuatro = 0;

    private final int btnUm = 0;
    private final int btnDois = 1;
    private final int btnTres = 2;
    private final int btnQuatro = 3;

    private Context context = this;

    private Intent intent;

    private int perguntaNumero = 0;

    private int mProgressStatus = 0;
    private Handler mHandler = new Handler();

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest);



        overridePendingTransition(R.anim.activity_filho_entrando, R.anim.activity_pai_saindo);

        btnRespostaUm = findViewById(R.id.btnRespostaUm);
        btnRespostaDois = findViewById(R.id.btnRespostaDois);
        btnRespostaTres = findViewById(R.id.btnRespostaTres);
        btnRespostaQuatro = findViewById(R.id.btnRespostaQuatro);
        tvPergunta = findViewById(R.id.tvPergunta);
        btnProximaPergunta = findViewById(R.id.btnProximaPergunta);

        intent = getIntent();

        textViewContagem = findViewById(R.id.text_view_contagem);
        textViewPontuacao = findViewById(R.id.text_view_pontuacao);
        textViewQuestionCount = findViewById(R.id.text_view_question_count);

        textColorDefaultRb = btnRespostaUm.getTextColors();

        QuizDbHelper dbHelper = new QuizDbHelper(this);
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
            answered = false;
        } else {
            finishQuiz();
        }
    }



    private void checkAnswer(int botaoSelecionado) {

        answered = true;

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
    public void onBackPressed() {
        super.onBackPressed();

        if(backPressedTime + 2000 > System.currentTimeMillis()){
            finishQuiz();
        }else{
            Toast.makeText(this, "Pressione novamente para sair", Toast.LENGTH_SHORT);
        }


        backPressedTime = System.currentTimeMillis();
    }
}


