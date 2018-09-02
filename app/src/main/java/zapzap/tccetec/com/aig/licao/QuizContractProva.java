package zapzap.tccetec.com.aig.licao;

import android.provider.BaseColumns;

public final class QuizContractProva {

    private QuizContractProva(){}

    public static class QuestionsTableProva implements BaseColumns {

        public static final String TABLE_NAME = "quiz_questions";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_OPTION4 = "option4";

        public static final String COLUMN_ANSWER_NR = "answer_nr";

    }
}
