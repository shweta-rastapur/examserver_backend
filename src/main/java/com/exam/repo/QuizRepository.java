package com.exam.repo;

import com.exam.entity.exam.Category;
import com.exam.entity.exam.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Long> {
 public List<Quiz> findByCategory(Category catrgory);

 public List<Quiz> findByActive(Boolean b );

 public List<Quiz> findByCategoryAndActive(Category c,Boolean b );


}
