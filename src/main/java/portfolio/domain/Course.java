package portfolio.domain;

import java.util.Comparator;

import portfolio.data.PortfolioNoPersistenceData;

/*
 * Lehrveranstaltung
 */
public class Course implements Comparable<Course> { // Titel der Lehrveranstaltung

	public static Comparator<Course> SORT_STARTYEAR = new SortStartYear();
	public static Comparator<Course> SORT_SEMESTER = new SortSemester();
	
	private int id;
	private Subject subject; // z.B. MATHEMATIK, PHYSIK, BWL, PROGRAMMIEREN, DATENBANKEN, PROJEKTMANAGEMENT
	private String detail; // z.B. Logik und Algebra, Analysis, Unternehmensorganisation, Java-1, Java-2
	private StudentClass studentClass; // z.B. WI18A
	private Semester semester;
	private int startYear; // 18 für "Sommersemester 18" sowie "Wintersemester 18/19"
	private Teacher teacher;

	public Course(int id, Subject subject, String detail, StudentClass studentClass, Semester semester, int startYear, Teacher teacher) {
		// id darf nicht kleiner null sein
		if (id <= 0)
			throw new IllegalArgumentException("ID darf nicht kleiner null sein: " + id);
		
		this.id = id;
		
		this.setSubject(subject);
		this.setDetail(detail);
		this.setStudentClass(studentClass);
		this.setSemester(semester);
		this.setStartYear(startYear);
		this.setTeacher(teacher);
	}
	
	public Course(int id, Subject subject, String detail, StudentClass studentClass, Semester semester, int startYear) {
		this(id, subject, detail, studentClass, semester, startYear, null);
	}
	
	
	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		// subject darf weder null noch Leerstring sein
		// -->Enum kann nur null oder einen Wert enthalten
		if (subject == null)
			throw new IllegalArgumentException("Lehrgebiet (subject) darf nicht leer sein.");
		this.subject = subject;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		// detail darf null und auch Leerstring sein -> auf Leerstring setzen
		if (detail == null)
			this.detail = "";
		else
			this.detail = detail;
	}

	public StudentClass getStudentClass() {
		return studentClass;
	}

	public void setStudentClass(StudentClass studentClass) {
		// studentClass darf nicht null sein
		if (studentClass == null)
			throw new IllegalArgumentException("Kurs (studentClass) darf nicht leer sein.");
		this.studentClass = studentClass;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(Semester semester) {
		// semester darf nicht null sein
		if (semester == null)
			throw new IllegalArgumentException("Semester darf nicht leer sein.");
		this.semester = semester;
	}

	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
		// startYear muss zwischen 0 und 99 sein
		if (startYear <= 0 && startYear < 100)
			throw new IllegalArgumentException("Startjahr muss zwischen 0 und 99 liegen: " + startYear);
		this.startYear = startYear;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	// -->Bewusstes nicht verwenden
//	public void setId(int id) {
//		this.id = id;
//	}


	

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public int getId() {
		return id;
	}

	@Override
	public int compareTo(Course o) {
		// order by studentClass, title, startYear, semester (WS vor SS)
		// |-> title nicht vorhanden, stattdessen mit subject verglichen

		int result = this.studentClass.compareTo(o.studentClass);
		if (result != 0)
			return result;

		// toString(), da nach Bezeichung von Enum-Element verglichen werden soll
		result = this.subject.toString().compareTo(o.subject.toString());
		if (result != 0)
			return result;

		result = SORT_STARTYEAR.compare(this, o);
		if (result != 0) {
			return result;
		}

		return SORT_SEMESTER.compare(this, o);
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
		// war gegeben, deshalb nicht verändert
		return id == other.id;
	}

	public String toString() {
		String jahrString = String.valueOf(startYear);
		if (semester == Semester.WS)
			jahrString = jahrString + "/" + (startYear + 1);
		return String.format("%3d %s %s %s%s %s", id, studentClass.toString(), subject.toString(), semester, jahrString,
				teacher + "");
	}

//	public static void testEntity() {
////		todo: müssen wir die testEntity hier vervollständigen??
//		StudentClass sc1 = new StudentClass("WI", 18, "A");
//		StudentClass sc2 = new StudentClass("IT", 17);
//		StudentClass sc3 = new StudentClass("BA", 18, "C");
//
//		Course c1 = new Course(1, Subject.MATHEMATIK, "Logik und Algebra", sc1, Semester.WS, 18);
//		Course c2 = new Course(2, Subject.PHYSIK, "Einführung", sc2, Semester.SS, 18);
//		Course c3 = new Course(3, Subject.DATENBANKEN, "SQL 1", sc1, Semester.SS, 19);
//
//	}

	private static class SortStartYear implements Comparator<Course> {
		@Override
		public int compare(Course c1, Course c2) {
			Integer i1 = c1.startYear;
			Integer i2 = c2.startYear;
			return i1.compareTo(i2);
		}
	}

	private static class SortSemester implements Comparator<Course> {
		@Override
		public int compare(Course c1, Course c2) {
			if (c1.semester == c2.semester)
				return 0;
			if (c1.semester == Semester.WS)
				return -1; // TODO Prüfen/ Testen
			if (c2.semester == Semester.WS)
				return 1;
			if (c1.semester == Semester.SSCL)
				return 1;
			if (c2.semester == Semester.SSCL)
				return -1;

			throw new IllegalArgumentException("Falsche Werte eingetragen.");
		}
	}
}