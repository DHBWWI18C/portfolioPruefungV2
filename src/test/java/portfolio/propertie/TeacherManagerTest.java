package portfolio.propertie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import portfolio.domain.Gender;
import portfolio.domain.Teacher;
import portfolio.propertie.TeacherManager;

public class TeacherManagerTest {
    Teacher t1;
    Teacher t2;
    Teacher t3;
    TeacherManager tManager;


    @Before
    public void setUp() throws Exception {
        t1 = new Teacher(789, "HSD", "Strenger", "Hendrik", Gender.D);
        t2 = new Teacher(123, "LQW", "Quitadamo", "Luca", Gender.W);
        t3 = new Teacher(4, "TRM", "Raynoschek", "Tom", Gender.M);
        
        tManager = TeacherManager.getInstance("teacher.properties");
        }

    @After
    public void tearDown() throws Exception {
        t1 = null;
        t2 = null;
        t3 = null;
    }
    
/*
 * 1. insert
 * 		- 
 * 		- null
 * 2. delete
 * 		- null
 * 3. get
 * 		- null
 * 4. getAll
 * 		- null
 * 5. update    
 * 		- null
 */
    @Test
    public void insertTest1() {
    	tManager.insert(t1);
    	assertEquals(t1, tManager.get(t1.getId()));
    }
    @Test
    public void insertTest2() {	//NullPointer ok oder was?
    	int size = tManager.getAll().size(); 
    	tManager.insert(null);
    	assertEquals(size, tManager.getAll().size());
    }
    @Test
    public void insertTest3() {
    	Teacher t = new Teacher(123, "ADB", "Lastname", "", Gender.W);
    	tManager.insert(t);
    	assertEquals(t, tManager.get(123));
    }
    
    
    @Test
    public void updateTest1() {
    	Teacher teacher1 = new Teacher(765, "MAA", "Hase", "", Gender.W);
    	Teacher teacher2 = new Teacher(765, "MAB", "Hase", "Hans", Gender.W);
    	
    	tManager.insert(teacher1);
    	tManager.update(765, teacher2);
    	assertTrue(teacher2.equals(tManager.get(765)));
    }
    @Test
    public void updateTest2() {	//NullPointer ok oder was?
    	int size = tManager.getAll().size(); 
    	tManager.insert(null);
    	assertEquals(size, tManager.getAll().size());
    }
    @Test
    public void updateTest3() {
    	Teacher t = new Teacher(123, "ADB", "Lastname", "", Gender.W);
    	tManager.insert(t);
    	assertEquals(t, tManager.get(123));
    }
}
