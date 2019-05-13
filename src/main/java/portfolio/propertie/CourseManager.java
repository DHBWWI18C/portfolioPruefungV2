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
import portfolio.domain.Course;
import portfolio.domain.Semester;
import portfolio.domain.Subject;
import portfolio.domain.Teacher;

public class CourseManager implements DAO<Course>{

	private String fileName;
	private static CourseManager courseManager = null;
	
	private CourseManager(String fileName) {
		this.fileName = fileName;
	}
	
	public static CourseManager getInstance(String fileName) {
		if(courseManager == null)
			courseManager= new CourseManager(fileName);
		return courseManager;
	}
	
	@Override
	public Course get(int id) {
		List<Course> courses = this.getAll();
		for (Course c : courses) {
			if(c.getId() == id)
				return c;
		}
		return null;
	}

	@Override
	public List<Course> getAll() {
		List<Course> courses = new ArrayList<>();
		Properties properties = new Properties();
		StudentClassManager scm = StudentClassManager.getInstance(this.fileName);
		
		try {
			properties.load(new FileInputStream(fileName));
			
			properties.forEach( (key, value) -> {
				int id = Integer.parseInt((String) key);
				StringTokenizer tokenizer = new StringTokenizer((String) value, "-");
				
				courses.add(new Course(
						id,
						getSubjectByName(tokenizer.nextToken()),
						tokenizer.nextToken(),
						scm.get(Integer.parseInt(tokenizer.nextToken())),
						getSemesterByName(tokenizer.nextToken()),
						Integer.parseInt((String)tokenizer.nextToken())
				));
			});
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return courses;
	}
	
	public Subject getSubjectByName(String s) {
		switch(s) {
		case "DATENBANKEN":
			return Subject.DATENBANKEN;
		case "MATHEMATIK":
			return Subject.MATHEMATIK;
		case "MODELLIERUNG":
			return Subject.MODELLIERUNG;
		case "PHYSIK":
			return Subject.PHYSIK;
		case "PROGRAMMIEREN":
			return Subject.PROGRAMMIEREN;
		case "PROJEKTMANAGEMENT":
			return Subject.PROJEKTMANAGEMENT;
		case "WIRTSCHAFTSINFORMATIK":
			return Subject.WIRTSCHAFTSINFORMATIK;
		default:
			throw new IllegalArgumentException("Subject nicht gefunden.");
		}
	}
	
	public Semester getSemesterByName(String name) {
		switch(name) {
		case "WS":
			return Semester.WS;
		case "SS":
			return Semester.SS;
		case "SSCL":
			return Semester.SSCL;
		default:
			throw new IllegalArgumentException("Semester nicht gefunden.");
		}
	}

	@Override
	public void insert(Course t) {
		ArrayList<Course> courses = (ArrayList<Course>)this.getAll();
		
		courses.add(t);
		this.clearAndWrite(courses);
	}

	@Override
	public void update(int id, Course t) {
		ArrayList<Course> courses = (ArrayList<Course>)this.getAll();
		
		delete(get(id));
		courses.add(t);
		this.clearAndWrite(courses);
	}

	@Override
	public void delete(Course t) {
		ArrayList<Course> courses = (ArrayList<Course>)this.getAll();
		
		Course c = get(t.getId());
		courses.remove(c);
		
		this.clearAndWrite(courses);
	}
	
	public void clearAndWrite(List<Course> courses) {
		Properties properties = new Properties();
		
		courses.forEach(c -> 
			{
				String value = "";
				value +=c.getSubject().toString() 
						+ "-" + c.getDetail() 
						+ "-" + c.getStudentClass().toString() 
						+ "-" + c.getSemester().toString() 
						+ "-" + c.getStartYear()
						+ "-";
				Teacher t = c.getTeacher();
				if(t != null)
					value += t.getId();
				
				properties.setProperty("" + c.getId(), value);
							//public Course(int id, Subject subject, String detail, StudentClass studentClass, Semester semester, int startYear, Teacher teacher) {
				try {
					properties.store(new FileOutputStream(fileName), null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
	}
}
