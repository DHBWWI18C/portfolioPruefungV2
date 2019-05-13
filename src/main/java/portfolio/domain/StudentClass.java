package portfolio.domain;

import java.util.Comparator;

public class StudentClass implements Comparable<StudentClass> { // Kurs, z.B. WI16A, IT18

	public static Comparator<StudentClass> SORT_STARTYEAR = new SortStartYear();
	public static Comparator<StudentClass> SORT_ID = new SortId();

	private String courseShortcut; // WI, IT, etc.
	private int startYear; // z.B. 16, 17, 18
	private String spezifier; // z.B. A, B, auch Leerstring möglich
	private int id;

	public StudentClass(int id, String courseShortcut, int startYear) {
		this(id, courseShortcut, startYear, "");
	}
	// TODO 'id' nicht in Konstruktor geben

	public StudentClass(int id, String courseShortcut, int startYear, String spezifier) {
		this.id = id;// TODO
		setCourseShortcut(courseShortcut);
		setStartYear(startYear);
		setSpezifier(spezifier);
	}

	public String getCourseShortcut() {
		return courseShortcut;
	}

	public void setCourseShortcut(String courseShortcut) {
		if (courseShortcut == null || courseShortcut.length() != 2
				|| !courseShortcut.equals(courseShortcut.toUpperCase())) {
			throw new IllegalArgumentException("Kein erlaubtes Studiengangkürzel: " + courseShortcut);
		}
		this.courseShortcut = courseShortcut;
	}

	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
		if (startYear < 0 || startYear > 99) {
			throw new IllegalArgumentException("Kein erlaubter Jahrgang: " + startYear);
		}
		this.startYear = startYear;
	}

	public String getSpezifier() {
		return spezifier;
	}

	public void setSpezifier(String spezifier) {
		if (spezifier == null) {
			this.spezifier = "";
			return;
		} else {
			if (spezifier == "") {
				this.spezifier = spezifier;
				return;
			}
			if (spezifier.length() > 1 || spezifier.charAt(0) < 'A' || spezifier.charAt(0) > 'Z') {
				throw new IllegalArgumentException(
						"Keine erlaubte Zusatzbezeichnung (A, B, C, ...) für Kurs: " + spezifier);
			}
		}
		this.spezifier = spezifier;
	}

	public int getId() {
		return id;
	}

//Wird automatisch gezogen
	// public void setId(int id) {
	// this.id = id;
	// }

	public String toString() {
		// id wird bewusst nicht beachtet
		String yearString = String.format("%02d", startYear);
		return courseShortcut + yearString + this.spezifier;
	}

	// equals2: Namensbezeichnung war bereits so vorhanden
	public boolean equals2(Object o) {
		if (o == this)
			return true;
		if (o == null || o.getClass() != this.getClass())
			return false;
		StudentClass other = (StudentClass) o;
		return other.courseShortcut.equals(this.courseShortcut) && other.spezifier.equals(this.spezifier)
				&& other.startYear == this.startYear;
	}

	public int hashCode() {
		return id;
	}

	@Override
	public int compareTo(StudentClass c) {
		int cmp = this.courseShortcut.compareTo(c.courseShortcut);
		if (cmp != 0)
			return cmp;

		cmp = this.startYear - c.startYear;
		if (cmp != 0)
			return cmp;

		return this.spezifier.compareTo(c.spezifier);
	}

	private static class SortStartYear implements Comparator<StudentClass> {
		@Override
		public int compare(StudentClass sc1, StudentClass sc2) {
			Integer i1 = sc1.startYear;
			Integer i2 = sc2.startYear;
			return i1.compareTo(i2);
		}
	}

	private static class SortId implements Comparator<StudentClass> {
		@Override
		public int compare(StudentClass sc1, StudentClass sc2) {
			Integer i1 = sc1.id;
			Integer i2 = sc2.id;
			return i1.compareTo(i2);
		}
	}

}
