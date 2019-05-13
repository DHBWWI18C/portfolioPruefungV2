package portfolio.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StudentClassTest {

	StudentClass st1;
	StudentClass st2;

	@Before
	public void before() {
	}

	@After
	public void after() {
		st1 = null;
		st2 = null;
	}

//	Der Konstruktor arbeitet mit den Setter-Methoden, deshalb wurden diese nicht explizit getestet

//	Überprüfen auf erfolgreiche Instanziierung mit 3 Parametern
	@Test
	public void constructorPositiveTest1() {
		st1 = new StudentClass(0, "WI", 18);
		String st = "";
		assertTrue(st1.getId() == 0);
		assertTrue(st1.getCourseShortcut().equals("WI"));
		assertTrue(st1.getStartYear() == 18);
		assertTrue(st1.getSpezifier().equals(st));
	}

//	Überprüfen auf erfolgreiche Instanziierung mit 4 Parametern
	@Test
	public void constructorPositiveTest2() {
		st1 = new StudentClass(1, "WI", 18, "C");
		assertTrue(st1.getId() == 1);
		assertTrue(st1.getCourseShortcut().equals("WI"));
		assertTrue(st1.getStartYear() == 18);
		assertTrue(st1.getSpezifier().equals("C"));
	}

//	Überprüfen wenn courseshortcut = null ob Exception ausgegeben wird
	@Test
	public void constructorTest1() throws Exception {
		String s = "";
		String sc = null;
		try {
			st1 = new StudentClass(1, sc, 18, "C");
		} catch (IllegalArgumentException e) {
			s = e.getMessage();
		}
		assertEquals(s, "Kein erlaubtes Studiengangkürzel: " + sc);

	}

//	Überprüfen wenn startyear < 0 ob Exception ausgegeben wird
	@Test
	public void constructorTest2() throws Exception {
		String s = "";
		int starty = -10;
		try {
			st1 = new StudentClass(1, "WI", starty, "C");
		} catch (IllegalArgumentException e) {
			s = e.getMessage();
		}
		assertEquals(s, "Kein erlaubter Jahrgang: " + starty);

	}

//	Überprüfen wenn startyear > 99 ob Exception
	@Test
	public void constructorTest3() throws Exception {
		String s = "";
		int starty = 100;
		try {
			st1 = new StudentClass(1, "WI", starty, "C");
		} catch (IllegalArgumentException e) {
			s = e.getMessage();
		}
		assertEquals(s, "Kein erlaubter Jahrgang: " + starty);
	}

//	Spezifier = null => wird zu ""
	@Test
	public void constructorTest4() {
		st1 = new StudentClass(1, "WI", 18, null);
		assertEquals(st1.getSpezifier(), "");
	}

//	Spezifier.length > 0
	@Test
	public void constructorTest5() throws Exception {
		String s = "";
		String spezi = "ABC";
		try {
			st1 = new StudentClass(1, "WI", 18, spezi);
		} catch (IllegalArgumentException e) {
			s = e.getMessage();
		}
		assertEquals(s, "Keine erlaubte Zusatzbezeichnung (A, B, C, ...) für Kurs: " + spezi);
	}

//	StudentClass und Object vergleichen
	@Test
	public void equals2Test1() {
		st1 = new StudentClass(1, "WI", 18, "C");
		Object obj = new StudentClass(1, "WI", 18, "C");
		assertTrue(st1.equals2(obj));
	}

//	StudentClass und null vergleichen
	@Test
	public void equals2Test2() {
		st1 = new StudentClass(1, "WI", 18, "C");
		assertFalse(st1.equals2(null));
	}

	//TODO warum geht das nicht? LQ-Antwort: Aufruf 
//	2 Objekte mit gleichen Attributwertbelegungen
	@Test
	public void equals2Test3() {
		st1 = new StudentClass(1, "WI", 18, "C");
		st2 = new StudentClass(1, "WI", 18, "C");
		assertTrue(st1.equals2(st2));
	}

//	courseShortcut unterschiedlich
	@Test
	public void equals2Test4() {
		st1 = new StudentClass(1, "WI", 18, "C");
		st2 = new StudentClass(1, "WA", 18, "C");
		assertFalse(st1.equals2(st2));
	}

//	spezifier unterschiedlich
	@Test
	public void equals2Test5() {
		st1 = new StudentClass(1, "WI", 18, "C");
		st2 = new StudentClass(1, "WI", 18, "A");
		assertFalse(st1.equals2(st2));
	}

//	spezifier unterschiedlich
	@Test
	public void hashCodeTest1() {
		st1 = new StudentClass(1, "WI", 18, "C");
		assertEquals(st1.hashCode(), 1);
	}

//	Zwei Objekte mit gleichen Attributwerten = 0
	@Test
	public void compareToTest1() {
		st1 = new StudentClass(1, "WI", 18, "C");
		st2 = new StudentClass(1, "WI", 18, "C");
		assertTrue(st1.compareTo(st2) == 0);
	}

//	Objekt compareTo null
	@Test
	public void compareToTest2() {
		st1 = new StudentClass(1, "WI", 18, "C");
		boolean exc = false;;
		try {
			st1.compareTo(null);
		} catch (NullPointerException e) {
			exc = true;
		}
		assertTrue(exc);
	}

//	2 Objekte mit unterschiedlichen Shortcut compareTo (st1>st2 => >0)
	@Test
	public void compareToTest3() {
		st1 = new StudentClass(1, "WI", 18, "C");
		st2 = new StudentClass(1, "WA", 18, "C");
		assertTrue(st1.compareTo(st2)>0);
	}

//	2 Objekte mit unterschiedlichen startYear (st1<st2 => >0)
	@Test
	public void compareToTest4() {
		st1 = new StudentClass(1, "WI", 18, "C");
		st2 = new StudentClass(1, "WI", 19, "C");
		assertTrue(st1.compareTo(st2)<0);
	}

//	2 Objekte mit unterschiedlichen spezifier (st1>st2 => <0)
	@Test
	public void compareToTest5() {
		st1 = new StudentClass(1, "WI", 18, "C");
		st2 = new StudentClass(1, "WI", 18, "B");
		assertTrue(st1.compareTo(st2)>0);
	}
	
//	startYear st1 < st2 => <0
	@Test
	public void compareStartYearTest1() {
		st1 = new StudentClass(1, "WI", 18, "C");
		st2 = new StudentClass(1, "WI", 19, "B");
		assertTrue(StudentClass.SORT_STARTYEAR.compare(st1, st2)<0);
	}
	
//	startYear st1>st2 => >0
	@Test
	public void compareStartYearTest2() {
		st1 = new StudentClass(1, "WI", 18, "C");
		st2 = new StudentClass(1, "WI", 17, "B");
		assertTrue(StudentClass.SORT_STARTYEAR.compare(st1, st2)>0);
	}
	
//	startYear st1, null
	@Test
	public void compareStartYearTest3() {
		st1 = new StudentClass(1, "WI", 18, "C");
		boolean exc = false;
		try {
			StudentClass.SORT_STARTYEAR.compare(st1, null);
		} catch (Exception e) {
			exc = true;
		}
		assertTrue(exc);
	}
	
//	startYear null, null
	@Test
	public void compareStartYearTest4() {
		boolean exc = false;
		try {
			StudentClass.SORT_STARTYEAR.compare(null, null);
		} catch (Exception e) {
			exc = true;
		}
		assertTrue(exc);
	}
	
//	Id st1<st2 => <0
	@Test
	public void compareSortIdTest1() {
		st1 = new StudentClass(1, "WI", 18, "C");
		st2 = new StudentClass(2, "WI", 18, "B");
		assertTrue(StudentClass.SORT_ID.compare(st1, st2)<0);
	}
	
//	Id st1>st2 => >0
	@Test
	public void compareSortIdTest2() {
		st1 = new StudentClass(2, "WI", 18, "C");
		st2 = new StudentClass(1, "WI", 18, "B");
		assertTrue(StudentClass.SORT_ID.compare(st1, st2)>0);
	}
	
//	Id st1, null
	@Test
	public void compareSortIdTest3() {
		st1 = new StudentClass(2, "WI", 18, "C");
		boolean exc = false;
		try {
			StudentClass.SORT_ID.compare(st1, null);
		} catch (Exception e) {
			exc = true;
		}
		assertTrue(exc);
	}
	
//	Id null, null
	@Test
	public void compareSortIdTest4() {
		boolean exc = false;
		try {
			StudentClass.SORT_ID.compare(null, null);
		} catch (Exception e) {
			exc = true;
		}
		assertTrue(exc);
	}

}