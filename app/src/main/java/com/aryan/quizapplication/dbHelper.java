package com.aryan.quizapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class dbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "quizzy.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "tblQuestions";
    private static final String COLUMN_QUESTION = "questions";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_OPTION1 = "optionOne";
    private static final String COLUMN_OPTION2 = "optionTwo";
    private static final String COLUMN_OPTION3 = "optionThree";
    private static final String COLUMN_OPTION4 = "optionFour";
    private static final String COLUMN_ANSWER_NO = "answerNo";

    private SQLiteDatabase db;

    public dbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME + " ( " +
                                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                                COLUMN_QUESTION + " TEXT, " +
                                COLUMN_OPTION1 + " TEXT, " +
                                COLUMN_OPTION2 + " TEXT, " +
                                COLUMN_OPTION3 + " TEXT, " +
                                COLUMN_OPTION4 + " TEXT, " +
                                COLUMN_ANSWER_NO + " INTEGER )";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        insertQn();
    }

    private void insertQn(){
        QuestionModel q1 = new QuestionModel("Which cricketer had scored highest individual score in first-class cricket?", "Don Bradman", "Brian Lara", "Lane Hutton", "Gary Sobers", 2);
        addQuestions(q1, db);
        QuestionModel q2 = new QuestionModel("Which cricketer had scored highest individual score in ODI cricket?", "Chris Gayle", "Sachin Tendulkar", "Martin Guptill", "Rohit Sharma", 4);
        addQuestions(q2, db);
        QuestionModel q3 = new QuestionModel("Which cricketer had scored most centuries in first-class cricket?", "Lane Hutton", "Wally Hammond", "Jack Hobbs", "Sachin Tendulkar", 3);
        addQuestions(q3, db);
        QuestionModel q4 = new QuestionModel("Which cricketer had scored fastest century in ODI cricket?", "Vivan Richards", "Corey Anderson", "Shahid Afridi", "AB de Villers", 4);
        addQuestions(q4, db);
        QuestionModel q5 = new QuestionModel("Which non-wicket keeper cricketer has taken most catches in ODI cricket?", "Ricky Ponting", "Mahela Jayawardene", "Jacques Kallis", "Mark Waugh", 2);
        addQuestions(q5, db);
        QuestionModel q6 = new QuestionModel("Which cricketer had scored highest individual score in Test cricket?", "Sanath Jayasuriya", "Matthew Hayden", "Sachin Tendulkar", "Brian Lara", 4);
        addQuestions(q6, db);
        QuestionModel q7 = new QuestionModel("Which cricketer had scored most runs in a Test match?", "Graham Gooch", "Don Bradman", "Brian Lara", "Sachin Tendulkar", 1);
        addQuestions(q7, db);
        QuestionModel q8 = new QuestionModel("Which cricketer had scored fastest century in Test cricket?", "Vivian Richards", "Brendon McCullum", "Misbah-ul-Haq", "Adam Gilchrist", 2);
        addQuestions(q8, db);
        QuestionModel q9 = new QuestionModel("How many teams participated in ICC world cup 2019?", "10", "12", "8", "16", 1);
        addQuestions(q9, db);
        QuestionModel q10 = new QuestionModel("Which cricket team has won most ICC cricket World Cup titles?", "West Indies", "India", "England", "Australia", 4);
        addQuestions(q10, db);
        QuestionModel q11 = new QuestionModel("Which of the following country has won the ICC cricket world cup(50 Over format) title so far?", "New Zealand", "Bangladesh", "Sri-Lanka", "South Africa", 3);
        addQuestions(q11, db);
        QuestionModel q12 = new QuestionModel("Which of the following Indian player have got firts \"Man of the Tournament\" Award in the ICC Cricket World Cup?", "Sachin Tendulkar", "Yuvraj Singh", "M.S. Dhoni", "Mohinder Amarnath", 1);
        addQuestions(q12, db);

    }

    public void addQuestions(QuestionModel question, SQLiteDatabase db){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_QUESTION, question.getQuestion());
        cv.put(COLUMN_OPTION1, question.getOption1());
        cv.put(COLUMN_OPTION2, question.getOption2());
        cv.put(COLUMN_OPTION3, question.getOption3());
        cv.put(COLUMN_OPTION4, question.getOption4());
        cv.put(COLUMN_ANSWER_NO, question.getAnswerNo());
        db.insert(TABLE_NAME, null, cv);
    }

    public List<QuestionModel> getAllQuestions(SQLiteDatabase db){
        List<QuestionModel> questions = new ArrayList<>();
        String[] columns = {COLUMN_ID, COLUMN_QUESTION, COLUMN_OPTION1, COLUMN_OPTION2, COLUMN_OPTION3, COLUMN_OPTION4, COLUMN_ANSWER_NO};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            if(cursor.moveToFirst()) {
                do {
                    questions.add(new QuestionModel(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6)));
                }while (cursor.moveToNext());
            }
        }
        cursor.close();
        return questions;
    }
}
