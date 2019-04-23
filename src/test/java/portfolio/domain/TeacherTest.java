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


    @Before
    public void setUp() throws Exception {
        t1 = new Teacher(789, "HSD", "Strenger", "Hendrik", Gender.D);
        t2 = new Teacher(123, "LQW", "Quitadamo", "Luca", Gender.W);
        t3 = new Teacher(4, "TRM", "Raynoschek", "Tom", Gender.M);
    }

    @After
    public void tearDown() throws Exception {
        t1 = null;
        t2 = null;
        t3 = null;
    }

    @Test
    public void compareTo1(){
        /*
        TODO:
        was soll größer und was soll kleiner null sein?
         */
        int test1= t1.compareTo(t2);
        Assert.assertTrue(test1 < 0);
    }

    @Test
    public void compareTo2(){
        /*
        TODO:
        was soll größer und was soll kleiner null sein?
         */
        int test2 = t2.compareTo(t1);
        Assert.assertTrue(test2 > 0 );
    }

    @Test
    public void compareTo3(){
        int test3 = t3.compareTo(t3);
        Assert.assertTrue(test3 == 0);
    }

    @Test
    public void equals1(){
        Assert.assertTrue(t1.equals(t1));
    }

    @Test
    public void equals2(){
        t2 = new Teacher(789, "LQW", "Quitadamo", "Luca", Gender.W);
        Assert.assertTrue(t1.equals(t2));
    }

    @Test
    public void equals3(){
        Assert.assertFalse(t1.equals(t3));
    }

//    TODO: wie soll man den hashCode testen?




}