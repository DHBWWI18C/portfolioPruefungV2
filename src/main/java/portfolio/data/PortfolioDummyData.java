package portfolio.data;

import java.util.List;

import portfolio.dao.DAO;
import portfolio.dao.DAOMany;
import portfolio.domain.Course;
import portfolio.domain.StudentClass;
import portfolio.domain.Subject;
import portfolio.domain.Teacher;

public class PortfolioDummyData implements PortfolioData {

   @Override
   public DAO<Course> getCourseData() {
      return new DAO<Course>() {
         public Course get(int id) { return null; }
         public List<Course> getAll() { return null; }
         public void insert(Course t) { }
         public void update(int id, Course t) { }
         public void delete(Course t) { }  
      };
   }
   @Override
   public DAO<StudentClass> getStudentClassData() {
      return new DAO<StudentClass>() {
         public StudentClass get(int id) { return null; }
         public List<StudentClass> getAll() { return null; }
         public void insert(StudentClass t) { }
         public void update(int id, StudentClass t) { }
         public void delete(StudentClass t) { }  
      };
   }
   @Override
   public DAOMany<Teacher, Subject> getTeacherData() {
      return new DAOMany<Teacher, Subject>(){

         @Override
         public Teacher get(int id) {
            // TODO Auto-generated method stub
            return null;
         }

         @Override
         public List<Teacher> getAll() {
            // TODO Auto-generated method stub
            return null;
         }

         @Override
         public void insert(Teacher t) {
            // TODO Auto-generated method stub
            
         }

         @Override
         public void update(int id, Teacher t) {
            // TODO Auto-generated method stub
            
         }

         @Override
         public void delete(Teacher t) {
            // TODO Auto-generated method stub
            
         }

         @Override
         public boolean addManyToManyEntry(Teacher t1, Subject t2) {
            // TODO Auto-generated method stub
            return false;
         }

         @Override
         public boolean deleteManyToManyEntry(Teacher t1, Subject t2) {
            // TODO Auto-generated method stub
            return false;
         }

         @Override
         public List<Subject> getAllFor(Teacher t1) {
            // TODO Auto-generated method stub
            return null;
         }
         
      };
   }  
   
}
