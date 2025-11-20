/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Courses;

import java.util.List;

/**
 *
 * @author HP
 */
public class Question {
    private String questionId;
    private String text;
    private List<String> choices;
    private int correctChoice;
    public Question(){};
    public Question(String questionId,String text,List<String> choices,int correctChoice)
    {
        this.questionId=questionId;
        this.choices=choices;
        this.text=text;
        this.correctChoice=correctChoice;
    }
    public String getQuestionId()
    {
        return questionId;
    }
    public String getText()
    {
        return text;
    }
    public List<String> getChoices()
    {
        return choices;
    }
    public int getCorrectChoice()
    {
        return correctChoice;
    }
    public void setQuestionId(String questionId)
    {
        this.questionId=questionId;
    }
    public void setText(String text)
    {
        this.text=text;
    }
    public void setChoices(List<String> choices)
    {
        this.choices=choices;
    }
    public void setCorrectChoice(int correctChoice)
    {
        this.correctChoice=correctChoice;
    }
    @Override
    public String toString()
    {
        return "Question(" +questionId+ "): "+text;
    }
}
