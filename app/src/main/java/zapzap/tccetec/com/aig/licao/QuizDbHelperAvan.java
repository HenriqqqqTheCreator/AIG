package zapzap.tccetec.com.aig.licao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import zapzap.tccetec.com.aig.licao.QuizContractAvan.*;

import java.util.ArrayList;
import java.util.List;


public class QuizDbHelperAvan extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyAwesomeQuizAvan.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public QuizDbHelperAvan(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTableAvan.TABLE_NAME + " ( " +
                QuestionsTableAvan._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTableAvan.COLUMN_QUESTION + " TEXT, " +
                QuestionsTableAvan.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTableAvan.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTableAvan.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTableAvan.COLUMN_OPTION4 + " TEXT, " +
                QuestionsTableAvan.COLUMN_ANSWER_NR + " INTEGER" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTableAvan();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTableAvan.TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionsTableAvan() {
        Question q1 = new Question("Qual vantagem o homem viu na riqueza?",
                "Com o acumulo do mesmo não sentia necessidade de conviver em grupo e alimentava seu ego",
                "Percebeu com isso que o acumulo lhe dava descanso até o mesmo acabar",
                "Viu que com o acumulo criava para si um patrimônio",
                "Nenhuma das alternativas", 2);
        addQuestion(q1);
        Question q2 = new Question("O que a educação financeira nos proporciona?",
                "Um melhor controle de nosso capital",
                "A possibilidade de não entrarmos em dívida",
                "Um aumento de nossos recursos",
                "Satisfação",1);
        addQuestion(q2);
        Question q3 = new Question("Por qual motivo o tesouro direto foi criado?",
                "Tornar o acesso aos títulos públicos federais mais fácil e simples",
                "Abrir capital público para empresas",
                "Pagar dívidas públicas",
                "Aumentar a infra-estrutura dos cofres públicos",1);
        addQuestion(q3);
        Question q4 = new Question("O que se pode fazer para verificar a transparência dos seus investimentos?",
                "Perguntando a um amigo",
                "Confiando no funcionário da empresa",
                "Você pode verificar o valor dos seus investimentos e se existem interessados em comprar ou vender as cotas",
                "Pedindo a um servidor público",3);
        addQuestion(q4);
        Question q5 = new Question("O que significa LCI?",
                "Lucro do custo industrial",
                "Lucro do custo de investidor",
                "Letra de crédito do investidor",
                "Letra de crédito imobiliário",3);
        addQuestion(q5);
    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTableAvan.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTableAvan.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTableAvan.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTableAvan.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTableAvan.COLUMN_OPTION4, question.getOption4());
        cv.put(QuestionsTableAvan.COLUMN_ANSWER_NR, question.getAnswerNr());
        db.insert(QuestionsTableAvan.TABLE_NAME, null, cv);
    }

    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTableAvan.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTableAvan.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTableAvan.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTableAvan.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTableAvan.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionsTableAvan.COLUMN_OPTION4)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTableAvan.COLUMN_ANSWER_NR)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}
