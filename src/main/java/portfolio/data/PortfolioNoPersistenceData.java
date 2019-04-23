package portfolio.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import portfolio.dao.DAO;
import portfolio.dao.DAOMany;
import portfolio.domain.Course;
import portfolio.domain.StudentClass;
import portfolio.domain.Subject;
import portfolio.domain.Teacher;

public class PortfolioNoPersistenceData implements PortfolioData {
   List<Course> courses = new ArrayList<>();
   List<StudentClass> studentClasses = new ArrayList<>();
   List<Teacher> teachers = new ArrayList<>();
   Map<Teacher, List<Subject>> mapTeacherToSubjects = new HashMap<>();

   @Override
   public DAO<Course> getCourseData() {
      return new NoPersistenceCourseDAO();
   }

   @Override
   public DAO<StudentClass> getStudentClassData() {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public DAOMany<Teacher, Subject> getTeacherData() {
      // TODO Auto-generated method stub
      return null;
   } 
   /*
    * NoPersistenceCourseDAO
    * 
    * Uses List<Course> courses as "persistence medium"
    * 
    * depends heavenly on the implementations of Course.equals()
    * (two courses a, b are equal if and only if a.id==b.id)
    */
   class NoPersistenceCourseDAO  implements DAO<Course>{
      @Override
      public Course get(int id) {
         for( Course c : courses )
            if( c.getId()==id )
               return c;
         return null;      //nothing found
      }

      @Override
      public List<Course> getAll() {
         return new ArrayList<>( courses );
      }

      @Override
      public void insert(Course t) {
         if( courses.contains( t )) {
            throw new RuntimeException("insert(). Lehrveranstaltung mit id=" + t.getId() + " bereits vorhanden.");
         }
         courses.add( t );
      }

      @Override
      public void update(int id, Course t) {
         if( !courses.contains( t )){
            throw new RuntimeException("update(). Lehrveranstaltung mit id=" + t.getId() + " nicht vorhanden.");
         }
         courses.remove( t );
         courses.add( t );
      }

      @Override
      public void delete(Course t) {
         if( !courses.remove( t )) {
            throw new RuntimeException("delete(). Lehrveranstaltung mit id=" + t.getId() + " konnte nicht gelöscht werden.");
         }
      }     
   }
}
