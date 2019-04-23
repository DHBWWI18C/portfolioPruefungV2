package portfolio.domain;

import java.util.Comparator;

/*
 * Lehrveranstaltung
 */
public class Course implements Comparable<Course> { // Titel der Lehrveranstaltung

	public Comparator<Course> SORT_STUDENTCLASS = new SortStudentClass();
	public Comparator<Course> SORT_SUBJECT = new SortSubject();
	public Comparator<Course> SORT_STARTYEAR = new SortStartYear();
	public Comparator<Course> SORT_SEMESTER = new SortSemester();

	private int id;
	private Subject subject; // z.B. MATHEMATIK, PHYSIK, BWL, PROGRAMMIEREN, DATENBANKEN, PROJEKTMANAGEMENT
	private String detail; // z.B. Logik und Algebra, Analysis, Unternehmensorganisation, Java-1, Java-2
	private StudentClass studentClass; // z.B. WI18A
	private Semester semester;
	private int startYear; // 18 für "Sommersemester 18" sowie "Wintersemester 18/19"
	private Teacher teacher;

	// LQ
	public Course(int id, Subject subject, String detail, StudentClass studentClass, Semester semester, int startYear) {

		// subject darf weder null noch Leerstring sein
		if (subject == null /* || subject.isEmpty() */)
			throw new IllegalArgumentException("Lehrgebiet (subject) darf nicht leer sein.");

		// detail darf null und auch Leerstring sein -> auf Leerstring setzen
		if (detail == null)
			detail = "";

		// studentClass darf nicht null sein
		if (studentClass == null)
			throw new IllegalArgumentException("Kurs (studentClass) darf nicht leer sein.");

		// semester darf nicht null sein
		if (semester == null)
			throw new IllegalArgumentException("Semester darf nicht leer sein.");

		// startYear muss zwischen 0 und 99 sein
		if (startYear <= 0 && startYear < 100)
			throw new IllegalArgumentException("Startjahr muss zwischen 0 und 99 liegen: " + startYear);

		this.id = id;
		this.subject = subject;
		this.detail = detail;
		this.studentClass = studentClass;
		this.semester = semester;
		this.startYear = startYear;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public int getId() {
		return id;
	}

	@Override
	public int compareTo(Course o) {
		// order by studentClass, title, startYear, semester (WS vor SS)
		Course course = (Course) o; // kein Abfangen von Exceptions - eventuell TODO
		return 0;
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		return id == other.id;
	}

	public String toString() {
		String jahrString = String.valueOf(startYear);
		if (semester == Semester.WS)
			jahrString = jahrString + "/" + (startYear + 1);
		return String.format("%3d %s %s %s%s %s", id, studentClass.toString(), subject.toString(), semester, jahrString,
				teacher + "");
	}

	public static void testEntity() {
		StudentClass sc1 = new StudentClass("WI", 18, "A");
		StudentClass sc2 = new StudentClass("IT", 17);
		StudentClass sc3 = new StudentClass("BA", 18, "C");

		Course c1 = new Course(1, Subject.MATHEMATIK, "Logik und Algebra", sc1, Semester.WS, 18);
		Course c2 = new Course(2, Subject.PHYSIK, "Einführung", sc2, Semester.SS, 18);
		Course c3 = new Course(3, Subject.DATENBANKEN, "SQL 1", sc1, Semester.SS, 19);

	}

	private class SortStudentClass implements Comparator<Course> {
		@Override
		public int compare(Course c1, Course c2) {
			return c1.studentClass.compareTo(c2.studentClass);
		}
	}

	private class SortSubject implements Comparator<Course> {
		@Override
		public int compare(Course c1, Course c2) {
			return c1.subject.toString().compareTo(c2.subject.toString());
		}
	}

	private class SortStartYear implements Comparator<Course> {
		@Override
		public int compare(Course c1, Course c2) {
			Integer i1 = c1.startYear;
			Integer i2 = c2.startYear;
			return i1.compareTo(i2);
		}
	}

	private class SortSemester implements Comparator<Course> {
		@Override
		public int compare(Course c1, Course c2) {
			if (c1.semester == c2.semester)
				return 0;
			if (c1.semester == Semester.WS)
				return -1; // TODO Prüfen
			if (c2.semester == Semester.WS)
				return 1;
			if (c1.semester == Semester.SSCL)
				return 1;
			if (c2.semester == Semester.SSCL)
				return -1;
			//TODO return null statt 0 oder Exeption
			throw new IllegalArgumentException("Falsche Werte eingetragen.");
		}
	}
}