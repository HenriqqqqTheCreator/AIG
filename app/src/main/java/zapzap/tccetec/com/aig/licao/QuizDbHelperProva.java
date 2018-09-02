package zapzap.tccetec.com.aig.licao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import zapzap.tccetec.com.aig.licao.QuizContractProva.*;

import java.util.ArrayList;
import java.util.List;


public class QuizDbHelperProva extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyAwesomeQuizProva.db";
    private static final int DATABASE_VERSION = 3;

    private SQLiteDatabase db;

    public QuizDbHelperProva(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTableProva.TABLE_NAME + " ( " +
                QuestionsTableProva._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTableProva.COLUMN_QUESTION + " TEXT, " +
                QuestionsTableProva.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTableProva.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTableProva.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTableProva.COLUMN_OPTION4 + " TEXT, " +
                QuestionsTableProva.COLUMN_ANSWER_NR + " INTEGER" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTableProva();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTableProva.TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionsTableProva() {

        Question q1 = new Question("Por que o LCA foi criado?",
                "Para o controle dos créditos agrícolas.",
                "Para o financiamento do setor agroindustrial",
                "Para um fundo de investimento dos agricultores",
                "Para um controle dos investimentos do agronegócio",4);
        addQuestion(q1);


        Question q2 = new Question("Ao comprar ações o que você se torna?",
                "Acionista da empresa",
                "Funcionário da empresa",
                "Sócio da empresa",
                "Nada", 3);
        addQuestion(q2);

        Question q3 = new Question("Quanto mais ações você estiver com a empresa:",
                "Maior desembolso vai ter",
                "Maior é a sua parcela",
                "Mais importante ela vai ser",
                "Mais risco vai ter", 2);
        addQuestion(q3);

        Question q4 = new Question("O que é uma corretora?",
                "São empresas que auxiliam o Sistema Financeiro Nacional",
                "São instituições financeiras indispensáveis para o investidor que quer apostar seu dinheiro na Bolsa",
                "São empresa que intermediam a compra e venda de ações demais títulos financeiros.",
                "Todas as alternativas", 4);
        addQuestion(q4);

        Question q5 = new Question("Para investir na bolsa você precisa ter:",
                "Conta corrente",
                "Conta com corretora",
                "Conta com a Bovespa",
                "Conta com todos os bancos", 2);
        addQuestion(q5);

        Question q6 = new Question("Quais os benefícios que podem acontecer ao investir?",
                "Potencial de alto retorno",
                "Simplicidade e praticidade",
                "Lucro garantido",
                "Créditos com o banco", 1);
        addQuestion(q6);

        Question q7 = new Question("Quais os ricos que podem acontecer ao investir?",
                "Perda de ações",
                "Não receber lucros",
                "Queda na bolsa",
                "Todas as alternativas", 4);
        addQuestion(q7);

        Question q8 = new Question("o que é educação financeiras?",
                "A capacidade de tomar e executar planejamentos que envolvam dinheiro no nosso cotidiano",
                "A capacidade de controlar seu capital, possibilitando assim um melhor gasto de recursos",
                "O ato de termos total controle e entendimento sobre assuntos econômicos e financeiros",
                "Todas as alternativas", 1);
        addQuestion(q8);

        Question q9 = new Question("qual a consequência de uma má educação financeira?",
                "Não podermos realizar viagens ou ir a lugares que desejamos muito pala falta de dinheiro",
                "Não termos nenhum conhecimento relacionando a economia ou finanças perdendo assim nossa voz em discussões relacionadas a tais temas",
                "As consequências são diversas, desde não poder comprar coisas que são de necessidade básica e desorganização na vida pessoal",
                "Nenhuma", 3);
        addQuestion(q9);


        Question q10 = new Question("Segundo Braunstein a administração ineficiente do dinheiro deixa os consumidores:",
                "Vulneráveis ao mercado e a escolha de seus preços",
                "Vulneráveis a crises financeiras mais graves",
                "Vulneráveis a golpes graves sobre seu capital",
                "Vulneráveis aos impostos do governo", 2);
        addQuestion(q10);
    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTableProva.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTableProva.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTableProva.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTableProva.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTableProva.COLUMN_OPTION4, question.getOption4());
        cv.put(QuestionsTableProva.COLUMN_ANSWER_NR, question.getAnswerNr());
        db.insert(QuestionsTableProva.TABLE_NAME, null, cv);
    }

    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTableProva.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTableProva.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTableProva.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTableProva.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTableProva.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionsTableProva.COLUMN_OPTION4)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTableProva.COLUMN_ANSWER_NR)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}
