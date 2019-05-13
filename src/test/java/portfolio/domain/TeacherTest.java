package portfolio.domain;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TeacherTest {

    Teacher t1;
    Teacher t2;
    Teacher t3;
    Teacher tNull;


    @Before
    public void setUp() throws Exception {
        t1 = new Teacher(789, "HSD", "Strenger", "Hendrik", Gender.D);
        t2 = new Teacher(123, "LQW", "Quitadamo", "Luca", Gender.W);
        t3 = new Teacher(4, "TRM", "Raynoschek", "Tom", Gender.M);
        tNull = null;
        }

    @After
    public void tearDown() throws Exception {
        t1 = null;
        t2 = null;
        t3 = null;
    }

    //Der Konstruktor arbeitet mit den set-Methoden, daher werden diese nicht explizit getestet.
    
    //Positivfall
    @Test
    public void constructorTest1() {
    	Teacher t4 = new Teacher(789, "HSD", "Strenger", "Hendrik", Gender.D);
    	assertTrue(t1.equals(t4));
    }
    
    //Test ohne shortcut
    @Test
    public void constructorTest2() throws Exception {
    	Teacher t4;
    	String s = "";
		try {
			t4 = new Teacher(789, "", "Strenger", "Hendrik", Gender.D);
		} catch (IllegalArgumentException e) {
			s = e.getMessage();
		}
    	assertEquals(s,"Shortcut darf nicht 'null' sein und muss 3 Buchstaben enthalten.");
    }
    
    //Test shortcut null
    @Test
    public void constructorTest3() throws Exception {
    	Teacher t4;
    	String s = null;
		try {
			t4 = new Teacher(789, s, "Strenger", "Hendrik", Gender.D);
		} catch (IllegalArgumentException e) {
			s = e.getMessage();
		}
    	assertEquals(s,"Shortcut darf nicht 'null' sein.");
    }
    
    //ohne Nachname
    @Test
    public void constructorTest4() {
    	Teacher t4;
    	String s = "";
			try {
				t4 = new Teacher(789, "ABC", s, "Hendrik", Gender.D);
			} catch (IllegalArgumentException e) {
				
				s = e.getMessage();
			}
		assertEquals(s,"Nachname muss mindestens 1 Buchstabe enthalten.");
    }
    
    //Nachname null
    @Test
    public void constructorTest5() {
    	Teacher t4;
    	String s = "";
    	String t = null;
    		try {
    			t4 = new Teacher(789, "ABC", t, "Hendrik", Gender.D);
    		} catch (IllegalArgumentException e) {
    			s = e.getMessage();
    		}
    	assertEquals(s,"Nachname darf nicht 'null' sein.");
    }
    
  //ohne Nachname
    @Test
    public void constructorTest6() {
    	Teacher t4;
    	String s = "8";
			try {
				t4 = new Teacher(789, "ABC", s, "Hendrik", Gender.D);
			} catch (IllegalArgumentException e) {
				
				s = e.getMessage();
			}
		assertEquals(s,"Nachname muss mindestens 1 Buchstabe enthalten.");
    }
    
    //ohne Vorname
    @Test
    public void constructorTest7() {
    	Teacher t4;
    	String s = "";
			t4 = new Teacher(789, "ABC", "Strenger", s, Gender.D);
    	assertTrue(t4.getVorname().equals(s));
    }
    
    //Vorname null
    @Test
    public void constructorTest8() {
    	Teacher t4;
    	String s = "";
    	String t = null;
			t4 = new Teacher(789, "ABC", "Strenger", t, Gender.D);
    	assertTrue(t4.getVorname().equals(s));
    }
    
    //Gender prüfen
    @Test
    public void constructorTest9() {
    	Teacher t4;
			t4 = new Teacher(789, "ABC", "Strenger", "Hendrik", Gender.D);
    	assertTrue(t4.getGender().equals(Gender.D));
    }
    
    
    //HashCode testen
    @Test
    public void hashCodeTest() {
    	Teacher t4;
			t4 = new Teacher(789, "ABC", "Strenger", "Hendrik", Gender.D);
		int hash = t4.hashCode();
    	assertTrue(hash == 789);
    }
    
    //Shortcut HSD zu LQW
    @Test
    public void compareTo1(){
        /*
        TODO:
        was soll grÃ¶ÃŸer und was soll kleiner null sein?
         */
        int test1= t1.compareTo(t2);
        Assert.assertTrue(test1 < 0);
    }
    
    //Shortcut LQW zu HSD
    @Test
    public void compareTo2(){
        /*
        TODO:
        was soll grÃ¶ÃŸer und was soll kleiner null sein?
         */
        int test2 = t2.compareTo(t1);
        Assert.assertTrue(test2 > 0 );
    }
    
    //Shortcut zu sich selbst
    @Test
    public void compareTo3(){
        int test3 = t3.compareTo(t3);
        Assert.assertTrue(test3 == 0);
    }
    
    //Shortcut HSD zu null
    //TODO Null in Methode abfangen, oder was soll im Test passieren?
    @Test
    public void compareTo4(){	//LQ11
        String s = "";
		try {
			t1.compareTo(tNull);
		} catch (IllegalArgumentException e) {
			s = e.getMessage();
		}
    	assertEquals(s, "Lehrer (teacher) darf nicht 'null' sein.");
    }
    
    //Selbes Objekt
    @Test
    public void equals1(){
        Assert.assertTrue(t1.equals(t1));
    }

    //Gleiche ID
    @Test
    public void equals2(){
    	Teacher t4 = new Teacher(789, "LQW", "Quitadamo", "Luca", Gender.W);
        Assert.assertTrue(t1.equals(t4));
    }
    
    //Verschiedene ID
    @Test
    public void equals3(){
        Assert.assertFalse(t1.equals(t3));
    }

    //Verschiedenes Objekt
    @Test
    public void equals4(){
    	Object t4 = new Object();
        Assert.assertFalse(t1.equals(t4));
    }
    
    //Objekt null
    @Test
    public void equals5(){
        Assert.assertFalse(t1.equals(tNull));
    }
    
    //Nachname Objekt 1 null
    //TODO weiß nicht wie der Test aussehen muss, oder ob die Methode null abfangen muss
    @Test
    public void compareNachnameTest1() {	//LQ11
    	
    	boolean thrown = false;
		try {
			Teacher.SORT_NACHNAME.compare(tNull,t2);
		} catch (NullPointerException e) {
			thrown = true;
		}
		assertTrue(thrown);
    }
    
    //Nachname Objekt 2 null
    //TODO weiß nicht wie der Test aussehen muss, oder ob die Methode null abfangen muss
    @Test
    public void compareNachnameTest2() {	//LQ11
    	
    	boolean thrown = false;
		try {
			Teacher.SORT_NACHNAME.compare(t1,tNull);
		} catch (NullPointerException e) {
			thrown = true;
		}
		assertTrue(thrown);
    }
    
    //Nachname beide null
    //TODO weiß nicht wie der Test aussehen muss, oder ob die Methode null abfangen muss
    @Test
    public void compareNachnameTest3() {	//LQ11
    	
    	boolean thrown = false;
		try {
			Teacher.SORT_NACHNAME.compare(null, null);
		} catch (NullPointerException e) {
			thrown = true;
		}
		assertTrue(thrown);
    }
    
    //Nachname gleich, verschiedenes Objekt
    @Test
    public void compareNachnameTest4() {
    	Teacher t4 = new Teacher(789, "HSD", "Strenger", "Hendrik", Gender.D);
    	int test = Teacher.SORT_NACHNAME.compare(t1,t4);
    	assertTrue(test == 0);
    }
    
    //Nachname gleiches Objekt
    @Test
    public void compareNachnameTest5() {
    	int test = Teacher.SORT_NACHNAME.compare(t1,t1);
    	assertTrue(test == 0);
    }
    
    //Nachname, t1 im Alphabet nach t2
    @Test
    public void compareNachnameTest6() {
    	int test = Teacher.SORT_NACHNAME.compare(t1,t2);
    	assertTrue(test > 0);
    }
    
  //Nachname, t2 im Alphabet vor t1
    @Test
    public void compareNachnameTest7() {
    	int test = Teacher.SORT_NACHNAME.compare(t2,t1);
    	assertTrue(test < 0);
    }
    
    //ID Objekt 1 null
    //TODO weiß nicht wie der Test aussehen muss, oder ob die Methode null abfangen muss
    @Test
    public void compareIDTest1() {	//LQ11
    	
    	boolean thrown = false;
		try {
			Teacher.SORT_ID.compare(tNull,t2);
		} catch (NullPointerException e) {
			thrown = true;
		}
		assertTrue(thrown);
    }
    
    //ID Objekt 2 null
    //TODO weiß nicht wie der Test aussehen muss, oder ob die Methode null abfangen muss
    @Test
    public void compareIDTest2() {	//LQ11
    	
    	boolean thrown = false;
		try {
			Teacher.SORT_ID.compare(t1, null);
		} catch (NullPointerException e) {
			thrown = true;
		}
		assertTrue(thrown);
    }
    
    //ID beide null
    //TODO weiß nicht wie der Test aussehen muss, oder ob die Methode null abfangen muss
    @Test
    public void compareIDTest3() {	//LQ11
    	
    	boolean thrown = false;
		try {
			Teacher.SORT_ID.compare(tNull,t2);
		} catch (NullPointerException e) {
			thrown = true;
		}
		assertTrue(thrown);
    }
    
    //ID gleich, verschiedenes Objekt
    @Test
    public void compareIDTest4() {
    	Teacher t4 = new Teacher(789, "HSD", "Strenger", "Hendrik", Gender.D);
    	int test = Teacher.SORT_ID.compare(t1,t4);
    	assertTrue(test == 0);
    }
    
    //ID gleiches Objekt
    @Test
    public void compareIDTest5() {
    	int test = Teacher.SORT_ID.compare(t1,t1);
    	assertTrue(test == 0);
    }
    
    //ID, t1 größer t2
    @Test
    public void compareIDTest6() {
    	int test = Teacher.SORT_ID.compare(t1,t2);
    	assertTrue(test > 0);
    }
    
  //ID, t2 kleiner t1
    @Test
    public void compareIDTest7() {
    	int test = Teacher.SORT_ID.compare(t2,t1);
    	assertTrue(test < 0);
    }
 
//    TODO: wie soll man den hashCode testen? (von Luca, habe das aber gelöst denke ich)




}