package portfolio.application;

import portfolio.dao.DAO;
import portfolio.dao.DAOMany;
import portfolio.data.PortfolioDAOFactory;
import portfolio.data.PortfolioData;
import portfolio.domain.Course;
import portfolio.domain.Gender;
import portfolio.domain.Semester;
import portfolio.domain.StudentClass;
import portfolio.domain.Subject;
import portfolio.domain.Teacher;

public class PortfolioTest {
   private static PortfolioData data = PortfolioDAOFactory.getPortfolioData();
   private static DAO<Course> courseData = data.getCourseData();
   private static DAO<StudentClass> studentClassData = data.getStudentClassData();
   private static DAOMany<Teacher, Subject> teacherData = data.getTeacherData(); 
   
   public static void main(String[] args) {
      
      //
      //testEntities();
      
      StudentClass sc1 = new StudentClass( "WI", 18, "A");
      StudentClass sc2 = new StudentClass( "IT", 17);
      StudentClass sc3 = new StudentClass( "BA", 18, "C");
      
      Course c1 = new Course(1, Subject.MATHEMATIK, "Logik und Algebra", sc1,  Semester.WS, 18);
      Course c2 = new Course(2, Subject.PHYSIK, "Einführung", sc2,  Semester.SS, 18);
      Course c3 = new Course(3, Subject.DATENBANKEN, "SQL 1", sc1,  Semester.SS, 19);
      
      System.out.println(c1.toString());
      
      courseData.insert( c1 );
      courseData.insert( c2 );
      courseData.insert( c3 );
      
      courseData.update(1, new Course(1, Subject.MATHEMATIK, "", sc3, Semester.WS, 17));
      
      for( Course c : courseData.getAll() )
         System.out.println( c );
      
      Teacher t1 = new Teacher(789, "HSD", "Strenger", "Hendrik", Gender.D);
      Teacher t2 = new Teacher();
      Teacher t3 = new Teacher();
      
      System.out.println(t1.toString());
      
      c1.setTeacher(t1);
      System.out.println(c1.toString());
/*      
      teachers.addManyToManyEntry(t1, Subject.DATENBANKEN);
      teachers.addManyToManyEntry(t1, Subject.MODELLIERUNG );
      teachers.addManyToManyEntry(t1,  Subject.WIRTSCHAFTSINFORMATIK);
      
      System.out.println("Lehrbefähigung für " + t1 + ": ");
      for( Subject s : teachers.getAllFor(t1) ) {
         System.out.print( s + " ");
      }
      System.out.println();
      */
   }
   private static void testEntities() {
      Course.testEntity();
      /*
      StudentClass.testEntity();
      Teacher.testEntity()
      */
   }
}
