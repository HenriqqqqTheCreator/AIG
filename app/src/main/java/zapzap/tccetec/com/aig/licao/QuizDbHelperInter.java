package zapzap.tccetec.com.aig.licao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import zapzap.tccetec.com.aig.licao.QuizContractInter.*;

import java.util.ArrayList;
import java.util.List;


public class QuizDbHelperInter extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyAwesomeQuizInt.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public QuizDbHelperInter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTableInter.TABLE_NAME + " ( " +
                QuestionsTableInter._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTableInter.COLUMN_QUESTION + " TEXT, " +
                QuestionsTableInter.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTableInter.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTableInter.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTableInter.COLUMN_OPTION4 + " TEXT, " +
                QuestionsTableInter.COLUMN_ANSWER_NR + " INTEGER" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTableInter();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTableInter.TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionsTableInter() {
        Question q1 = new Question("Qual desses fatores deve ser levado em conta na formação de uma empresa?",
                "O lugar",
                "Os impostos da região",
                "O interesse dos clientes",
                "Todas as anteriores", 4);
        addQuestion(q1);
        Question q2 = new Question("Porque avaliar a concorrência é importante?",
                "Para saber a qualidade de seus produtos e sua relação com o cliente",
                "Para saber a satisfação dos clientes e o preço de suas ações",
                "Para saber a qualidade dos produtos e seu tempo de produção",
                "Para saber o preço dos produtos e o salário dos funcionários",1);
        addQuestion(q2);
        Question q3 = new Question("Oque é a lei de oferta e procura?",
                "A quantidade do produto no mercado e o número de pessoas no mercado",
                "A quantidade do produto ofertado e o interesse sobre o mesmo",
                "A quantidade do produto ofertado e quanto querem pagar",
                "A quantidade do produto ofertado e seus benefícios",2);
        addQuestion(q3);
        Question q4 = new Question("Oque é o dumping?",
                "Vender o produto com o preço mais baixo que o concorrente",
                "Vender o produto com o preço mais alto que o concorrente",
                "Vender o produto a preço de custo com intenção de quebrar a concorrência",
                "Vender o produto a baixo do preço de custo com intenção de quebrar a concorrência",4);
        addQuestion(q4);
        Question q5 = new Question("O dumping é legal no Brasil?",
                "Acho que sim",
                "Sim",
                "Não",
                "Talvez",3);
        addQuestion(q5);
    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTableInter.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTableInter.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTableInter.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTableInter.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTableInter.COLUMN_OPTION4, question.getOption4());
        cv.put(QuestionsTableInter.COLUMN_ANSWER_NR, question.getAnswerNr());
        db.insert(QuestionsTableInter.TABLE_NAME, null, cv);
    }

    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTableInter.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTableInter.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTableInter.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTableInter.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTableInter.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionsTableInter.COLUMN_OPTION4)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTableInter.COLUMN_ANSWER_NR)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}
