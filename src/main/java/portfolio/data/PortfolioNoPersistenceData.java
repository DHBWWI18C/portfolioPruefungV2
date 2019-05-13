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
   
   NoPersistenceCourseDAO courseManager;
   NoPersistenceStudentClassDAO stundentClassManager;
   NoPersistenceTeacherDAO teacherManager;
   
   private static PortfolioNoPersistenceData portfolioNoPersistenceData;
   
   public static PortfolioNoPersistenceData getInstance() {
	   if(portfolioNoPersistenceData == null)
		   portfolioNoPersistenceData = new PortfolioNoPersistenceData();
	   return portfolioNoPersistenceData;
   }
   
   private PortfolioNoPersistenceData() {
	   courseManager = new NoPersistenceCourseDAO();
	   stundentClassManager = new NoPersistenceStudentClassDAO();
	   teacherManager = new NoPersistenceTeacherDAO();
   }
   
   @Override
   public DAO<Course> getCourseData() {
      return courseManager;
   }

   @Override
   public DAO<StudentClass> getStudentClassData() {
      return stundentClassManager;
   }

   @Override
   public DAOMany<Teacher, Subject> getTeacherData() {
      return teacherManager;
   } 
   /*
    * NoPersistenceCourseDAO
    * 
    * Uses List<Course> courses as "persistence medium"
    * 
    * depends heavenly on the implementations of Course.equals()
    * (two courses a, b are equal if and only if a.id==b.id)
    */
   private class NoPersistenceCourseDAO  implements DAO<Course>{
	   
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

   private class NoPersistenceStudentClassDAO  implements DAO<StudentClass>{


      @Override
      public StudentClass get(int id) {
         for (StudentClass studentClass: studentClasses){
            if (studentClass.getId() == id) return studentClass;
         }
         return null;
      }

      @Override
      public List<StudentClass> getAll() {
         return new ArrayList<>(studentClasses);
      }

      @Override
      public void insert(StudentClass studentClass) {
         if (studentClass == null) throw new IllegalArgumentException("Null kann nicht eingefügt werden");
         if (!studentClasses.contains(studentClass)) studentClasses.add(studentClass);
         else throw new IllegalArgumentException("Kurs " + studentClass + " bereits vorhanden");
      }

      @Override
      public void update(int id, StudentClass studentClass) {
         /*
         hs: Logik aus der Vorlage übernommen auch wenn sie fehleranfällig ist
         wenn Objekte mit der ID 1 und 2 vorhanden sind ist es möglich das Objekt
         mit der ID 1 mit einem Objekt mit der ID 2 zu überschreiben
         ID wäre somit nicht mehr eindeutig
         Es wurde daher auch kein Test für dieses Szenario geschrieben
          */
         boolean found = false;
         if (studentClass == null) throw new IllegalArgumentException("Null kann nicht eingefügt werden.");
         StudentClass removeStudentClass = null;
         for (StudentClass s: studentClasses){
            if (s.getId() == id){
//               Beim entfernen innerhalb der Schleife tritt eine Exception auf, findet daher erst am Ende statt
               found = true;
               removeStudentClass = s;
            }
         }
         if (!found) throw new IllegalArgumentException("Kein Kurs mit der ID " + id + " gefunden");
         else {
            studentClasses.remove(removeStudentClass);
            studentClasses.add(studentClass);
         }
      }

      @Override
      public void delete(StudentClass studentClass) {
         if (!studentClasses.contains(studentClass)) {
            throw new IllegalArgumentException("Kurs " + studentClass + " nicht gefunden.");
         } else {
            studentClasses.remove(studentClass);
         }
      }
   }

   private class NoPersistenceTeacherDAO  implements DAOMany<Teacher, Subject>{

      @Override
      public Teacher get(int id) {
         for (Teacher teacher: teachers){
            if (teacher.getId() == id) return teacher;
         }
         return null;
      }

      @Override
      public List<Teacher> getAll() {
         return new ArrayList<>(teachers);
      }

      @Override
      public void insert(Teacher teacher) {
         if (teacher == null) throw new IllegalArgumentException("Null kann nicht eingefügt werden");
         if (!teachers.contains(teacher)) teachers.add(teacher);
         else throw new IllegalArgumentException("Dozent " + teacher + " bereits vorhanden");
      }

      @Override
      public void update(int id, Teacher teacher) {
         if (teacher == null) throw new IllegalArgumentException("Null kann nicht eingefügt werden");
         Teacher removeTeacher = null;
         boolean found = false;
         for (Teacher t: teachers){
            if (t.getId() == id){
               found = true;
               removeTeacher = t;
            }
         }
         if (!found) throw new IllegalArgumentException("Kein Dozent mit der ID " + id + " gefunden");
         else {
            teachers.remove(removeTeacher);
            teachers.add(teacher);
         }
      }

      @Override
      public void delete(Teacher teacher) {
         if (!teachers.contains(teacher)) {
            throw new IllegalArgumentException("Dozent " + teacher + " nicht gefunden.");
         } else {
            teachers.remove(teacher);
         }
      }


      /*
      Es wird mittels eines Hashspeichers eine Verknüpfung
      zwischen Dozenten und deren Fächern erstellt
      teacher ist hierbei der Key über den der Zugriff erfolgt
       */

      List <Subject> subjects;
      /*
      Liste wird neu initialisiert wenn noch kein Eintrag für den Dozenten in der Hashmap
      'mapTeachersToSubject' vorhanden ist
       */
      @Override
      public boolean addManyToManyEntry(Teacher teacher, Subject subject) {
         if (teacher == null || subject == null){
            throw new IllegalArgumentException("Übergebene Instanzen dürfen nicht null.");
         }
         if (!mapTeacherToSubjects.containsKey(teacher)){
            subjects = new ArrayList<>();
            mapTeacherToSubjects.put(teacher, subjects);
            subjects.add(subject);
            return true;
         } else {
            subjects = mapTeacherToSubjects.get(teacher);
            if (!subjects.contains(subject)){
               subjects.add(subject);
               return true;
            }
         }
         return false;
      }



      /*
      Abgleich zwischen dem Dozenten und dem Fach
      Wenn match wird das Fach gelöscht
       */
      @Override
      public boolean deleteManyToManyEntry(Teacher teacher, Subject subject) {
         if (teacher == null || subject == null){
            throw new IllegalArgumentException("Übergebene Instanzen dürfen nicht null sein.");
         }
         subjects = mapTeacherToSubjects.get(teacher);
         if (subjects.contains(subject)){
            subjects.remove(subject);
            return true;
         }
         return false;
      }

//      Gibt alle Subjects zurück die mit einem Teacher in Verbindung stehen
      @Override
      public List<Subject> getAllFor(Teacher teacher) {
         subjects = mapTeacherToSubjects.get(teacher);
         if (teacher != null && subjects != null) return new ArrayList<>(subjects);
         return null;
      }
   }

}
