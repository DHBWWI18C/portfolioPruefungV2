package portfolio.propertie;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import portfolio.dao.DAO;
import portfolio.domain.StudentClass;

public class StudentClassManager implements DAO<StudentClass>{

	private String fileName;
	private static StudentClassManager studentClassManager = null;
	
	private StudentClassManager(String fileName) {
		this.fileName = fileName;
	}
	
	public static StudentClassManager getInstance(String fileName) {
		if(studentClassManager  == null)
			studentClassManager = new StudentClassManager(fileName);
		return studentClassManager;
	}
	
	@Override
	public StudentClass get(int id) {
		List<StudentClass> studentClasses = this.getAll();
		for (StudentClass sc : studentClasses) {
			if(sc.getId() == id)
				return sc;
		}
		return null;
	}

	@Override
	public List<StudentClass> getAll() {
		List<StudentClass> studentClasses = new ArrayList<>();
		Properties properties = new Properties();
		
		try {
			properties.load(new FileInputStream(fileName));
			
			properties.forEach( (key, value) -> {
				int id = Integer.parseInt((String) key);
				StringTokenizer tokenizer = new StringTokenizer((String) value, "-");
				
				studentClasses.add(new StudentClass(
						id,
						tokenizer.nextToken(),
						Integer.parseInt((String)tokenizer.nextToken())
				));
			});
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return studentClasses;
	}


	@Override
	public void insert(StudentClass t) {
		ArrayList<StudentClass> studentClasses = (ArrayList<StudentClass>)this.getAll();
		
		studentClasses.add(t);
		this.clearAndWrite(studentClasses);
	}

	@Override
	public void update(int id, StudentClass t) {
		ArrayList<StudentClass> studentClasses = (ArrayList<StudentClass>)this.getAll();
		
		delete(get(id));
		studentClasses.add(t);
		this.clearAndWrite(studentClasses);
	}

	@Override
	public void delete(StudentClass t) {
		ArrayList<StudentClass> studentClasses = (ArrayList<StudentClass>)this.getAll();
		StudentClass sc = get(t.getId());
		studentClasses.remove(sc);
		
		this.clearAndWrite(studentClasses);
	}

	public void clearAndWrite(List<StudentClass> studentClasses) {
		Properties properties = new Properties();
		
		studentClasses.forEach(sc -> 
			{
				properties.setProperty("" + sc.getId(), sc.getCourseShortcut() + "-" + sc.getStartYear() + "-" + sc.getSpezifier());
				try {
					properties.store(new FileOutputStream(fileName), null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
	}
}
