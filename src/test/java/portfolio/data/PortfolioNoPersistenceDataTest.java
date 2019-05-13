package portfolio.data;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import portfolio.dao.DAO;
import portfolio.dao.DAOMany;
import portfolio.domain.*;

import java.util.List;

import static org.junit.Assert.*;

public class PortfolioNoPersistenceDataTest {

    DAO<Course> courseDAO;
    DAO<StudentClass> studentClassDAO;
    DAOMany<Teacher, Subject> teacherDAO;
    PortfolioNoPersistenceData portfolioNoPersistenceData;

    StudentClass studentClass1;
    StudentClass studentClass2;
    Course course1;
    Course course2;
    Teacher teacher1;
    Teacher teacher2;

    @Before
    public void setUp() throws Exception {
        portfolioNoPersistenceData = PortfolioNoPersistenceData.getInstance();
        courseDAO = portfolioNoPersistenceData.getCourseData();
        studentClassDAO = portfolioNoPersistenceData.getStudentClassData();
        teacherDAO = portfolioNoPersistenceData.getTeacherData();
        
        studentClass1 = new StudentClass(1, "WI", 18);
        studentClass2 = new StudentClass(2, "WI", 19);
        course1 = new Course(1, Subject.WIRTSCHAFTSINFORMATIK, "EPK", studentClass1, Semester.WS, 18);
        course2 = new Course(2, Subject.DATENBANKEN, "Datenbanken", studentClass2, Semester.WS, 19);
        teacher1 = new Teacher(1, "LQW", "Quitadamo", "Luca", Gender.W);
        teacher2 = new Teacher(2, "TRM", "Raynoschek", "Tom", Gender.M);

        studentClassDAO.insert(studentClass1);
        studentClassDAO.insert(studentClass2);
        courseDAO.insert(course1);
        courseDAO.insert(course2);
        teacherDAO.insert(teacher1);
        teacherDAO.insert(teacher2);

        teacherDAO.addManyToManyEntry(teacher1, Subject.MATHEMATIK);
        teacherDAO.addManyToManyEntry(teacher1, Subject.PHYSIK);
        teacherDAO.addManyToManyEntry(teacher2, Subject.DATENBANKEN);
    }

    @After
    public void tearDown() throws Exception {

    	courseDAO.getAll().forEach(c -> {
    		courseDAO.delete(c);
    	});
    	studentClassDAO.getAll().forEach(sc -> {
    		studentClassDAO.delete(sc);
    	});
    	teacherDAO.getAll().forEach(t -> {
    		teacherDAO.delete(t);
    	});
    	
    	portfolioNoPersistenceData = null;
        courseDAO = null;
        studentClassDAO = null;
        teacherDAO = null;
        
        studentClass1 = null;
        studentClass2 = null;
        course1 = null;
        course2 = null;
        teacher1 = null;
        teacher2 = null;
        

    }

    //    Kommt das richtige Objekt zurÃ¼ck?
    @Test
    public void testStudentClassGet1() {
        assertTrue(studentClassDAO.get(1).equals2(studentClass1));
    }

    //    Test darauf ob Null zurÃ¼ck kommt wenn eine unbekannte ID ausgewÃ¤hlt wird
    @Test
    public void testStudentClassGet2() {
        assertNull(studentClassDAO.get(3));
    }

    //    Test ob Elemente eingefÃ¼gt werden kÃ¶nnen
//    Kontrolle mittels .size() und .get()
    @Test
    public void testStudentClassInsert1() {
        int oldSize = portfolioNoPersistenceData.studentClasses.size();
        StudentClass studentClass3 = new StudentClass(3, "AB", 18);
        studentClassDAO.insert(studentClass3);
        assertTrue((portfolioNoPersistenceData.studentClasses.size() == oldSize + 1) &&
                (studentClassDAO.get(3).equals2(studentClass3)));
    }

    //    Test beim einfÃ¼gen von null
    @Test
    public void testStudentClassInsert2() {
        String errorMessage = "";
        try {
            studentClassDAO.insert(null);
        } catch (IllegalArgumentException e) {
            errorMessage = e.getMessage();
        }
        assertTrue(errorMessage.equals("Null kann nicht eingefügt werden"));
    }

    //    Test beim einfÃ¼gen eines vorhanden Objekts
    @Test
    public void testStudentClassInsert3() {
        String errorMessage = "";
        try {
            studentClassDAO.insert(studentClass1);
        } catch (IllegalArgumentException e) {
            errorMessage = e.getMessage();
        }
        assertTrue(errorMessage.contains("Kurs ") && errorMessage.contains(" bereits vorhanden"));
    }

    //    Test beim updaten eines andern Objekts
    @Test
    public void testStudentClassUpdate1() {
        StudentClass studentClass3 = new StudentClass(3, "WI", 12);
        studentClassDAO.update(1, studentClass3);
        assertTrue((studentClassDAO.get(3).equals2(studentClass3)) &&
                studentClassDAO.get(1) == null);
    }

    //    Test beim Versuch eine nicht existierende ID zu Ã¼berschreiben
    @Test
    public void testStudentClassUpdate2() {
        String errorMessage = "";
        StudentClass studentClass3 = new StudentClass(3, "WI", 12);
        try {
            studentClassDAO.update(4, studentClass3);
        } catch (IllegalArgumentException e) {
            errorMessage = e.getMessage();
        }
        assertTrue(errorMessage.contains("Kein Kurs mit der ID ") && errorMessage.contains(" gefunden"));
    }

    //Test beim updaten einer Studentclass mit Null
    @Test
    public void testStudentClassUpdate3() {
        String errorMessage = "";
        try {
            studentClassDAO.update(1, null);
        } catch (IllegalArgumentException e) {
            errorMessage = e.getMessage();
        }
        assertTrue(errorMessage.equals("Null kann nicht eingefügt werden."));
    }

    // Löschen eines Objektes
    @Test
    public void testStudentClassDelete1() {
        int oldSize = portfolioNoPersistenceData.studentClasses.size();
        studentClassDAO.delete(studentClass1);
        assertTrue((portfolioNoPersistenceData.studentClasses.size() == oldSize - 1) &&
                (studentClassDAO.get(1) == null));
    }

    // Versuch null zu löschen
    @Test
    public void testStudentClassDelete2() {
        String errorMessage = "";
        try {
            studentClassDAO.delete(null);
        } catch (IllegalArgumentException e) {
            errorMessage = e.getMessage();
        }
        assertTrue(errorMessage.contains("Kurs ") && errorMessage.contains(" nicht gefunden"));
    }

    // Versuch nicht existierendes Objekt zu löschen
    // Gleiches verhalten wie bei null
    @Test
    public void testStudentClassDelete3() {
        String errorMessage = "";
        try {
            studentClassDAO.delete(new StudentClass(5, "AB", 99));
        } catch (IllegalArgumentException e) {
            errorMessage = e.getMessage();
        }
        assertTrue(errorMessage.contains("Kurs ") && errorMessage.contains(" nicht gefunden"));
    }


    //    Kommt das richtige Objekt zurück?
    @Test
    public void testTeacherGet1() {
        assertTrue(teacherDAO.get(1).equals(teacher1));
    }

    //    Test darauf ob Null zurück kommt wenn eine unbekannte ID ausgewÃ¤hlt wird
    @Test
    public void testTeacherGet2() {
        assertNull(teacherDAO.get(3));
    }

    //    Test ob Elemente eingefügt werden können
//    Kontrolle mittels .size() und .get()
    @Test
    public void testTeacherInsert1() {
        int oldSize = portfolioNoPersistenceData.teachers.size();
        Teacher teacher3 = new Teacher(3, "FFM", "Fischer", "Fritz", Gender.M);
        teacherDAO.insert(teacher3);
        assertTrue((portfolioNoPersistenceData.teachers.size() == oldSize + 1) &&
                (teacherDAO.get(3).equals(teacher3)));
    }

    //    Test beim einfÃ¼gen von null
    @Test
    public void testTeacherInsert2() {
        String errorMessage = "";
        try {
            teacherDAO.insert(null);
        } catch (IllegalArgumentException e) {
            errorMessage = e.getMessage();
        }
        assertTrue(errorMessage.equals("Null kann nicht eingefügt werden"));
    }

    //    Test beim einfügen eines vorhanden Objekts
    @Test
    public void testTeacherInsert3() {
        String errorMessage = "";
        try {
            teacherDAO.insert(teacher1);
        } catch (IllegalArgumentException e) {
            errorMessage = e.getMessage();
        }
        assertTrue(errorMessage.contains("Dozent ") && errorMessage.contains(" bereits vorhanden"));
    }

    //    Test beim updaten eines andern Objekts
    @Test
    public void testTeacherUpdate1() {
        Teacher teacher3 = new Teacher(3, "FFM", "Fischer", "Fritz", Gender.M);
        teacherDAO.update(1, teacher3);
        assertTrue((teacherDAO.get(3).equals(teacher3)) &&
                teacherDAO.get(1) == null);
    }

    //    Test beim Versuch eine nicht existierende ID zu Überschreiben
    @Test
    public void testTeacherUpdate2() {
        String errorMessage = "";
        Teacher teacher3 = new Teacher(3, "FFM", "Fischer", "Fritz", Gender.M);
        try {
            teacherDAO.update(4, teacher3);
        } catch (IllegalArgumentException e) {
            errorMessage = e.getMessage();
        }
        assertTrue(errorMessage.contains("Kein Dozent mit der ID ") && errorMessage.contains(" gefunden"));
    }

    //Test beim updaten einer Studentclass mit Null
    @Test
    public void testTeacherUpdate3() {
        String errorMessage = "";
        try {
            teacherDAO.update(1, null);
        } catch (IllegalArgumentException e) {
            errorMessage = e.getMessage();
        }
        assertTrue(errorMessage.equals("Null kann nicht eingefügt werden"));
    }

    // Löschen eines Objektes
    @Test
    public void testTeacherDelete1() {
        int oldSize = portfolioNoPersistenceData.teachers.size();
        teacherDAO.delete(teacher1);
        assertTrue(portfolioNoPersistenceData.teachers.size() == oldSize - 1);
        assertTrue(teacherDAO.get(1) == null);
    }

    // Versuch null zu löschen
    @Test
    public void testTeacherDelete2() {
        String errorMessage = "";
        try {
            teacherDAO.delete(null);
        } catch (IllegalArgumentException e) {
            errorMessage = e.getMessage();
        }
        assertTrue(errorMessage.contains("Dozent ") && errorMessage.contains(" nicht gefunden"));
    }

    // Versuch nicht existierendes Objekt zu löschen
    // Gleiches verhalten wie bei null
    @Test
    public void testTeacherDelete3() {
        String errorMessage = "";
        try {
            teacherDAO.delete(new Teacher(3, "FFM", "Fischer", "Fritz", Gender.M));
        } catch (IllegalArgumentException e) {
            errorMessage = e.getMessage();
        }
        assertTrue(errorMessage.contains("Dozent ") && errorMessage.contains(" nicht gefunden"));
    }


    //Test ob Einträge korrekt hinzugefügt werden können
    //Es wurden hier bewusst nicht die Methoden von DAOMany verwendet
    @Test
    public void testAddManyToManyEntry1() {
        teacherDAO.addManyToManyEntry(teacher1, Subject.MATHEMATIK);
        teacherDAO.addManyToManyEntry(teacher1, Subject.PHYSIK);
        teacherDAO.addManyToManyEntry(teacher2, Subject.DATENBANKEN);
        assertTrue(portfolioNoPersistenceData.mapTeacherToSubjects.containsKey(teacher1));
        assertTrue(portfolioNoPersistenceData.mapTeacherToSubjects.containsKey(teacher2));
        assertTrue(portfolioNoPersistenceData.mapTeacherToSubjects.get(teacher1).get(0) == Subject.MATHEMATIK);
        assertTrue(portfolioNoPersistenceData.mapTeacherToSubjects.get(teacher1).get(1) == Subject.PHYSIK);
        assertTrue(portfolioNoPersistenceData.mapTeacherToSubjects.get(teacher2).get(0) == Subject.DATENBANKEN);
        assertTrue(portfolioNoPersistenceData.mapTeacherToSubjects.size() == 2);
    }

    //Test beim Übergeben von null Elementen
    @Test
    public void testAddManyToManyEntry2() {
        String errorMessage = "";
        try {
            teacherDAO.addManyToManyEntry(null, Subject.MATHEMATIK);
        } catch (IllegalArgumentException e){
            errorMessage = e.getMessage();
        }
        assertTrue(errorMessage.equals("Ãœbergebene Instanzen dÃ¼rfen nicht null."));
    }

    //Test beim Übergeben von null Elementen
    @Test
    public void testAddManyToManyEntry3() {
        String errorMessage = "";
        try {
            teacherDAO.addManyToManyEntry(teacher1, null);
        } catch (IllegalArgumentException e){
            errorMessage = e.getMessage();
        }
        assertTrue(errorMessage.equals("Ãœbergebene Instanzen dÃ¼rfen nicht null."));
    }

    //Test beim doppelten einfügen eines Eintrags
    @Test
    public void testAddManyToManyEntry4() {
        teacherDAO.addManyToManyEntry(teacher1, Subject.MATHEMATIK);
        assertFalse(teacherDAO.addManyToManyEntry(teacher1, Subject.MATHEMATIK));
    }

    //Test beim Löschen eines Fachs an einem Dozenten mit mehreren Fächern
    @Test
    public void testDeleteManyToManyEntry1() {
        assertTrue(teacherDAO.deleteManyToManyEntry(teacher1,Subject.MATHEMATIK ));
        assertTrue(portfolioNoPersistenceData.mapTeacherToSubjects.containsKey(teacher1));
        assertTrue(portfolioNoPersistenceData.mapTeacherToSubjects.containsKey(teacher2));
        assertFalse(portfolioNoPersistenceData.mapTeacherToSubjects.get(teacher1).get(0) == Subject.MATHEMATIK);
        try {
            assertFalse(portfolioNoPersistenceData.mapTeacherToSubjects.get(teacher1).get(1) == Subject.PHYSIK);
        } catch (IndexOutOfBoundsException e){

        }
        assertTrue(portfolioNoPersistenceData.mapTeacherToSubjects.get(teacher1).get(0) == Subject.PHYSIK);
        assertTrue(portfolioNoPersistenceData.mapTeacherToSubjects.get(teacher2).get(0) == Subject.DATENBANKEN);
        assertTrue(portfolioNoPersistenceData.mapTeacherToSubjects.size() == 2);
    }

    //Test beim LÃ¶schen eines Fachs an einem DOzenten mit einem FÃ¤chern
    @Test
    public void testDeleteManyToManyEntry2() {
        assertTrue(teacherDAO.deleteManyToManyEntry(teacher2,Subject.DATENBANKEN ));
        assertTrue(portfolioNoPersistenceData.mapTeacherToSubjects.containsKey(teacher1));
        assertTrue(portfolioNoPersistenceData.mapTeacherToSubjects.containsKey(teacher2));
        assertTrue(portfolioNoPersistenceData.mapTeacherToSubjects.size() == 2);
        assertTrue(portfolioNoPersistenceData.mapTeacherToSubjects.get(teacher1).get(0) == Subject.MATHEMATIK);
        try {
            assertTrue(portfolioNoPersistenceData.mapTeacherToSubjects.get(teacher2).get(0) != Subject.DATENBANKEN);
        } catch (IndexOutOfBoundsException e){}
    }

    //Test beim LÃ¶schen eines Fachs mit Ã¼bergebenem null wert
    @Test
    public void testDeleteManyToManyEntry3() {
        try {
            teacherDAO.deleteManyToManyEntry(null, Subject.MATHEMATIK);
        } catch (IllegalArgumentException e){
            assertTrue(e.getMessage().equals("Ãœbergebene Instanzen dÃ¼rfen nicht null sein."));
        }
    }

    //Test beim Löschen eines Fachs mit Ã¼bergebenem null wert
    @Test
    public void testDeleteManyToManyEntry4() {
        try {
            teacherDAO.deleteManyToManyEntry(teacher1, null);
        } catch (IllegalArgumentException e){
            assertTrue(e.getMessage().equals("Ãœbergebene Instanzen dÃ¼rfen nicht null sein."));
        }
    }

    //Laden aller Subjects fÃ¼r einen Dozenten
    @Test
    public void testGetAllFor() {
        List<Subject> subjects = teacherDAO.getAllFor(teacher1);
        assertTrue(subjects.get(0) == Subject.MATHEMATIK);
        assertTrue(subjects.get(1) == Subject.PHYSIK);
        assertTrue(subjects.size() == 2);
    }

    //Laden aller Subjects fÃ¼r einen unbekannten Dozenten
    @Test
    public void testGetAllFor2() {
        assertNull(teacherDAO.getAllFor(new Teacher(3, "ABC", "Fischer", "Fritz")));
    }

    //Laden aller Subjects fÃ¼r einen Dozenten (null)
    @Test
    public void testGetAllFor3() {
        assertNull(teacherDAO.getAllFor(null));
    }


}