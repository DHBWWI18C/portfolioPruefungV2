package portfolio.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CourseTest {

	Course c;
	Course d;

	@Before
	public void before() {

	}

	@After
	public void after() {
		c = null;
		d = null;
	}
	
//	Der Konstruktor arbeitet mit den Setter-Methoden, deshalb wurden diese nicht explizit getestet

	@Test
	public void constructorTest1() throws Exception {
		String s = "";
		int id = 0;
		try {
			c = new Course(id, Subject.WIRTSCHAFTSINFORMATIK, "EPK", new StudentClass(1, "WI", 18), Semester.WS, 19);
		} catch (IllegalArgumentException e) {
			s = e.getMessage();
		}
		assertEquals(s, "ID darf nicht kleiner null sein: " + id);
	}

	@Test
	public void constructorTest2() throws Exception {
		String s = "";
		try {
			c = new Course(1, null, "EPK", new StudentClass(1, "WI", 18), Semester.WS, 19);
		} catch (IllegalArgumentException e) {
			s = e.getMessage();
		}
		assertEquals(s, "Lehrgebiet (subject) darf nicht leer sein.");
	}

	@Test
	public void constructorTest3() throws Exception {
		c = new Course(1, Subject.WIRTSCHAFTSINFORMATIK, null, new StudentClass(1, "WI", 18), Semester.WS, 19);
		assertEquals(c.getDetail(), "");
	}

	@Test
	public void constructorTest4() throws Exception {
		String s = "";
		try {
			c = new Course(1, Subject.WIRTSCHAFTSINFORMATIK, "EPK", null, Semester.WS, 19);
		} catch (IllegalArgumentException e) {
			s = e.getMessage();
		}
		assertEquals(s, "Kurs (studentClass) darf nicht leer sein.");
	}

	@Test
	public void constructorTest5() throws Exception {
		String s = "";
		try {
			c = new Course(1, Subject.WIRTSCHAFTSINFORMATIK, "EPK", new StudentClass(1, "WI", 18), null, 19);
		} catch (IllegalArgumentException e) {
			s = e.getMessage();
		}
		assertEquals(s, "Semester darf nicht leer sein.");
	}

	@Test
	public void construcorTest6() throws Exception {
		String s = "";
		int startYear = -1;
		try {
			c = new Course(1, Subject.WIRTSCHAFTSINFORMATIK, "EPK", new StudentClass(1, "WI", 18), Semester.WS,
					startYear);
		} catch (IllegalArgumentException e) {
			s = e.getMessage();
		}
		assertEquals(s, "Startjahr muss zwischen 0 und 99 liegen: " + startYear);
	}

	@Test
	public void hashCodeTest1() {
		c = new Course(1, Subject.WIRTSCHAFTSINFORMATIK, "EPK", new StudentClass(1, "WI", 18), Semester.WS, 1);
		assertTrue(c.hashCode() == 1);
	}

	@Test
	public void equalsTest1() {
		c = new Course(1, Subject.WIRTSCHAFTSINFORMATIK, "EPK", new StudentClass(1, "WI", 18), Semester.WS, 1);
		d = new Course(1, Subject.WIRTSCHAFTSINFORMATIK, "EPK", new StudentClass(2, "WI", 18), Semester.WS, 1);
		assertTrue(c.equals(d));
	}

	@Test
	public void equalsTest2() {
		c = new Course(1, Subject.WIRTSCHAFTSINFORMATIK, "EPK", new StudentClass(1, "WI", 18), Semester.WS, 1);
		d = null;
		assertFalse(c.equals(d));
	}

	@Test
	public void equalsTest3() {
		c = new Course(1, Subject.WIRTSCHAFTSINFORMATIK, "EPK", new StudentClass(1, "WI", 18), Semester.WS, 1);
		d = new Course(2, Subject.WIRTSCHAFTSINFORMATIK, "EPK", new StudentClass(2, "WI", 18), Semester.WS, 1);
		assertFalse(c.equals(d));
	}

	@Test
	public void compareToTest1() {
		c = new Course(1, Subject.WIRTSCHAFTSINFORMATIK, "EPK", new StudentClass(1, "WI", 18), Semester.WS, 1);
		d = new Course(1, Subject.WIRTSCHAFTSINFORMATIK, "EPK", new StudentClass(1, "WI", 18), Semester.WS, 1);
		assertTrue(c.compareTo(d) == 0);
	}

	@Test
	public void compareToTest2() {
		c = new Course(1, Subject.WIRTSCHAFTSINFORMATIK, "EPK", new StudentClass(1, "WI", 19), Semester.WS, 1);
		d = new Course(1, Subject.WIRTSCHAFTSINFORMATIK, "EPK", new StudentClass(1, "WI", 18), Semester.WS, 1);
		assertTrue(c.compareTo(d) > 0);
	}

	@Test
	public void compareToTest3() {
		c = new Course(1, Subject.WIRTSCHAFTSINFORMATIK, "EPK", new StudentClass(1, "WI", 18), Semester.WS, 1);
		d = new Course(1, Subject.DATENBANKEN, "EPK", new StudentClass(1, "WI", 18), Semester.WS, 1);
		assertTrue(c.compareTo(d) > 0);
	}

	@Test
	public void compareToTest4() {
		c = new Course(1, Subject.WIRTSCHAFTSINFORMATIK, "EPK", new StudentClass(1, "WI", 18), Semester.WS, 2);
		d = new Course(1, Subject.DATENBANKEN, "EPK", new StudentClass(1, "WI", 18), Semester.WS, 1);
		assertTrue(c.compareTo(d) > 0);
	}

	@Test
	public void compareToTest5() {
		c = new Course(1, Subject.WIRTSCHAFTSINFORMATIK, "EPK", new StudentClass(1, "WI", 18), Semester.SS, 1);
		d = new Course(1, Subject.WIRTSCHAFTSINFORMATIK, "EPK", new StudentClass(1, "WI", 18), Semester.WS, 1);
		assertTrue(c.compareTo(d) > 0);
	}

	@Test
	public void compareToTest6() {
		c = new Course(1, Subject.WIRTSCHAFTSINFORMATIK, "EPK", new StudentClass(1, "WI", 18), Semester.SSCL, 1);
		d = new Course(1, Subject.WIRTSCHAFTSINFORMATIK, "EPK", new StudentClass(1, "WI", 18), Semester.SS, 1);
		assertTrue(c.compareTo(d) > 0);
	}

	@Test
	public void compareToTest7() throws Exception {
		c = new Course(1, Subject.WIRTSCHAFTSINFORMATIK, "EPK", new StudentClass(1, "WI", 18), Semester.WS, 1);
		d = null;
		boolean exception = false;
		try {
			c.compareTo(d);
		} catch (NullPointerException e) {
			exception = true;
		}
		assertTrue(exception);
	}

	@Test
	public void compareSortStartYearTest1() {
		c = new Course(1, Subject.WIRTSCHAFTSINFORMATIK, "EPK", new StudentClass(1, "WI", 18), Semester.WS, 1);
		d = new Course(1, Subject.WIRTSCHAFTSINFORMATIK, "EPK", new StudentClass(1, "WI", 18), Semester.WS, 1);
		assertTrue(Course.SORT_STARTYEAR.compare(c, d) == 0);
	}

	@Test
	public void compareSortStartYearTest2() {
		c = new Course(1, Subject.WIRTSCHAFTSINFORMATIK, "EPK", new StudentClass(1, "WI", 18), Semester.WS, 1);
		d = new Course(1, Subject.WIRTSCHAFTSINFORMATIK, "EPK", new StudentClass(1, "WI", 18), Semester.WS, 2);
		assertTrue(Course.SORT_STARTYEAR.compare(c, d) < 0);
	}

	@Test
	public void compareSortSemesterYearTest1() {
		c = new Course(1, Subject.WIRTSCHAFTSINFORMATIK, "EPK", new StudentClass(1, "WI", 18), Semester.WS, 1);
		d = new Course(1, Subject.WIRTSCHAFTSINFORMATIK, "EPK", new StudentClass(1, "WI", 18), Semester.WS, 2);
		assertTrue(Course.SORT_SEMESTER.compare(c, d) == 0);
	}

	@Test
	public void compareSortSemesterYearTest2() {
		c = new Course(1, Subject.WIRTSCHAFTSINFORMATIK, "EPK", new StudentClass(1, "WI", 18), Semester.WS, 1);
		d = new Course(1, Subject.WIRTSCHAFTSINFORMATIK, "EPK", new StudentClass(1, "WI", 18), Semester.SS, 2);
		assertTrue(Course.SORT_SEMESTER.compare(c, d) < 0);
	}

	@Test
	public void compareSortSemesterYearTest3() throws Exception {
		c = new Course(1, Subject.WIRTSCHAFTSINFORMATIK, "EPK", new StudentClass(1, "WI", 18), Semester.WS, 1);

		boolean exception = false;
		try {
			Course.SORT_SEMESTER.compare(c, null);
		}
		catch (NullPointerException e) {
			exception = true;
		}
		assertTrue(exception);
	}

}