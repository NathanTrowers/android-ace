package com.nathan_trowers.androidace.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/*==================This class is a schema for questions.==================*/
public class Question extends RealmObject {

/*BEGIN***Variable Declaration*/
    @PrimaryKey
    private int questionNumber;
    private String question;
    private String answer;
    private String[] options = new String[4];
//    private String option1;
//    private String option2;
//    private String option3;
//    private String option4;
/*END***Variable Declaration*/


/*BEGIN***Getters*/
    //Question Number
    public int getQuestionNumber() {
        return questionNumber;
    }

    //Question
    public String getQuestion()
    {
        return question;
    }

    //Answer
    public String getAnswer() {
        return answer;
    }

    //Options
    public String[] getOptions()
    {
        return options;
    }



//    //Option 2
//    public String getOption2()
//    {
//        return option2;
//    }
//
//    //Option 3
//    public String getOption3()
//    {
//        return option3;
//    }
//
//    //Option 4
//    public String getOption4()
//    {
//        return option4;
//    }
/*END***Getters*/


/*BEGIN***Setters*/
    //Question Number
    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    //Question
    public void setQuestion(String question)
    {
        this.question = question;
    }

    //Answer
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    //Options
    private void setOptions( String[] options)
    {
        this.options = options;
    }


//    //Option 2
//    private void setOption2( String option2)
//    {
//        this.option2 = option2;
//    }
//
//    //Option 3
//    private void setOption3( String option3)
//    {
//        this.option3 = option3;
//    }
//
//    //Option 4
//    private void setOption4( String option4)
//    {
//        this.option4 = option4;
//    }
/*END***Setters*/
}


