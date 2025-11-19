/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Courses;

import java.util.List;

/**
 *
 * @author USER
 */
public class Quiz {
    private String lessonId;
    private List<Integer> correctAnswers;

    public Quiz(String lessonId, List<Integer> correctAnswers) {
        this.lessonId = lessonId;
        this.correctAnswers = correctAnswers;
    }

    public String getLessonId() {
        return lessonId;
    }

    public List<Integer> getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(List<Integer> correctAnswers) {
        this.correctAnswers = correctAnswers;
    }
}