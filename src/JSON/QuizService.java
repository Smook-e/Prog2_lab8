/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JSON;

import Courses.Question;
import Courses.Quiz;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author HP
 */
public class QuizService {
   
    private JsonDatabaseManager<Quiz> quizDb;
    private JsonDatabaseManager<Question> questionDb;
    public QuizService()
    {
        try {
            quizDb= new JsonDatabaseManager<>("C:\\Users\\HP\\OneDrive\\Documents\\GitHub\\Prog2_lab8\\src\\JSON\\quizzes.json",Quiz.class);
            questionDb=new JsonDatabaseManager<>("C:\\Users\\HP\\OneDrive\\Documents\\GitHub\\Prog2_lab8\\src\\JSON\\questions.json",Question.class);
        } catch (IOException ex) {
            System.out.print("error intializing json file.");
        }
    }
    public boolean quizIdExists(String quizId)
    {
        return quizDb.getDb().stream().anyMatch(q->q.getQuizId().equals(quizId));
    }
    public Quiz createQuiz(String quizId)
    {
        Quiz quiz=new Quiz(quizId);
        quizDb.getDb().add(quiz);
        return quiz;
    }
    public Question createQuestion(Quiz quiz,String text,List<String> choices,int correctChoice)
    {
       String questionId=quiz.getQuizId()+"_Q"+(quiz.getQuestionIds().size()+1);
       Question question=new Question(questionId,text,choices,correctChoice);
       questionDb.getDb().add(question);
       quiz.addQuestionIds(questionId);
       return question;
    }
    public void save()
    {
        quizDb.save();
        questionDb.save();
    }
    public List<Quiz> getQuizzes()
    {
        return quizDb.getDb();
    }
    public List<Question> getQuestionsOfQuiz(Quiz quiz)
    {
        List<Question> questions= new ArrayList<>();
        for(String questionId:quiz.getQuestionIds())
        {
            questionDb.getDb().stream().filter(q->q.getQuestionId().equals(questionId)).findFirst().ifPresent(questions::add);
        }
        return questions;
    }
    public Quiz getQuizById(String quizId)
    {
        return quizDb.getDb().stream().filter(p->p.getQuizId().equals(quizId)).findFirst().orElse(null);
    }
    public Question getQuestionById(String questionId)
    {
        return questionDb.getDb().stream().filter(p->p.getQuestionId().equals(questionId)).findFirst().orElse(null);
    }
    public List<Question> takeQuiz(String quizId)
    {
        Quiz quiz=getQuizById(quizId);
        if(quiz==null)
        { throw new IllegalArgumentException("quiz doesn't exist.");}
        return getQuestionsOfQuiz(quiz);
    }
    public int calculateScore(String quizId,List<Integer> answers)
    {
        List<Question> questions=takeQuiz(quizId);
        int score=0;
        int i;
        for(i=0;i<questions.size()&&i<answers.size();i++)
        {
            if(questions.get(i).getCorrectChoice()==answers.get(i))
            {
                score++;
            }
        }
        return score;
    }
}
