/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Courses;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP
 */
public class Quiz {
    private String quizId;
    private List<String> questionIds;
    public Quiz(){};
    public Quiz(String quizId)
    {
        this.quizId=quizId;
        this.questionIds=new ArrayList<>();
    }
    public String getQuizId()
    {
        return quizId;
    }
    public void setQuizId(String quizId)
    {
        this.quizId=quizId;
    }
    public void addQuestionIds(String id)
    {
        questionIds.add(id);
    }
    public List<String> getQuestionIds()
    {
        return questionIds;
    }
    @Override
    public String toString()
    {
       return "Quiz(" + quizId + ")Questions: "+questionIds.size();
    }
    
}
