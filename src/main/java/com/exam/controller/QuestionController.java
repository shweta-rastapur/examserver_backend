package com.exam.controller;

import com.exam.entity.exam.Question;
import com.exam.entity.exam.Quiz;
import com.exam.service.QuestionService;
import com.exam.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuizService quizService;

    @PostMapping("/")
    public ResponseEntity<Question> add(@RequestBody Question question)
    {
        return ResponseEntity.ok(this.questionService.addQuestion(question));
    }

    @PutMapping("/")
    public ResponseEntity<Question> update(@RequestBody Question question)
    {
        return ResponseEntity.ok(this.questionService.updateQuestion(question));
    }

    @GetMapping("/quiz/{qid}")
    public ResponseEntity<?> getQuestionsOfQuiz(@PathVariable("qid") Long qid)
    {
        /*
        Quiz quiz = new Quiz();
        quiz.setqId(qid);
        Set<Question> questionsOfQuiz = this.questionService.getQuestionsOfQuiz(quiz);
        return ResponseEntity.ok(questionsOfQuiz);

         */

        Quiz quiz= this.quizService.getQuiz(qid);

        Set<Question> questions = quiz.getQuestions();
        List<Question> list = new ArrayList(questions);
        if(list.size() > Integer.parseInt(quiz.getNumberOfQuestions()))
        {
            list = list.subList(0,Integer.parseInt(quiz.getNumberOfQuestions()+1));

        }
        list.forEach((q)-> {
            q.setAnswer(q.getAnswer());
        });

        Collections.shuffle(list);
        return ResponseEntity.ok(list);

    }
    @GetMapping("/quiz/all/{qid}")
    public ResponseEntity<?> getQuestionsOfQuizAdmin(@PathVariable("qid") Long qid)
    {

        Quiz quiz = new Quiz();
        quiz.setqId(qid);
        Set<Question> questionsOfQuiz = this.questionService.getQuestionsOfQuiz(quiz);
        return ResponseEntity.ok(questionsOfQuiz);




    }

    @GetMapping("/{quesId}")
    public   Question get(@PathVariable("quesId") Long quesId)
    {
        return this.questionService.getQuestion(quesId);
    }

    @DeleteMapping("/{quesId}")
    public void delete(@PathVariable("quesId") Long quesId)
    {
        this.questionService.deleteQuestion(quesId);
    }


    //eval quiz
    @PostMapping("/eval-quiz")
    public ResponseEntity<?>  evalQuiz(@RequestBody List<Question> questions)
    {
        double marksGot=0;
        int correctAnswers=0;
        int attempted=0;

        System.out.println(questions);
        for(Question q:questions)
        {
            //single questions
            //System.out.println(q.getGivenAnswer());
           Question question= questionService.get(q.getQuesId());

           if(question.getAnswer().equals(q.getGivenAnswer()))
            {
                //correct
                correctAnswers++;
                //single markscalculate
                double marksSingle = Double.parseDouble( questions.get(0).getQuiz().getMaxMarks())/questions.size();
                marksGot+= marksSingle;
            }
           if(q.getGivenAnswer()!=null)
           {
               attempted++;
            }
        }
        Map<String,Object> map = Map.of("marksGot", marksGot, "correctAnswers", correctAnswers, "attempted", attempted);
        return ResponseEntity.ok(map);

    }

}
