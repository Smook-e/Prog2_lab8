/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JSON;

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
    
}
