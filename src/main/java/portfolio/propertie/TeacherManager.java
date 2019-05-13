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
import portfolio.domain.Gender;
import portfolio.domain.Teacher;

public class TeacherManager implements DAO<Teacher>{

	private String fileName;
	private static TeacherManager teacherManager = null;
	
	private TeacherManager(String fileName) {
		this.fileName = fileName;
	}
	
	public static TeacherManager getInstance(String fileName) {
		if(teacherManager == null)
			teacherManager= new TeacherManager(fileName);
		return teacherManager;
	}
	
	@Override
	public Teacher get(int id) {
		List<Teacher> teachers = this.getAll();
		for (Teacher t : teachers) {
			if(t.getId() == id)
				return t;
		}
		return null;
	}

	@Override
	public List<Teacher> getAll() {
		List<Teacher> teachers = new ArrayList<>();
		Properties properties = new Properties();
		
		try {
			properties.load(new FileInputStream(fileName));
			
			properties.forEach( (key, value) -> {
				int id = Integer.parseInt((String) key);
				String[] values = value.toString().split("-");
//				StringTokenizer tokenizer = new StringTokenizer((String) value, "-");
//				String shortcut = tokenizer.nextToken();
//				String nachname = tokenizer.nextToken();
//				String vorname = tokenizer.nextToken();
//				String genderString = tokenizer.nextToken();
				String shortcut = values[0];
				String nachname = values[1];
				String vorname = values[2];
				Gender gender = getGenderByName(values[3]);
				teachers.add(new Teacher(
						id,
						shortcut,
						nachname,
						vorname != nullString ? vorname : "",
								//Wenn in Propertie-Datei kein Vorname vorhanden wird "" als Vorname gesetzt
						gender
				));
			});
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return teachers;
	}
	
	
	public Gender getGenderByName(String name) {
		switch(name) {
		case "W":
			return Gender.W;
		case "M":
			return Gender.M;
		case "D":
			return Gender.D;
		default:
			return null;
		}
	}

	@Override
	public void insert(Teacher t) {
		if(t == null)
			return;
		ArrayList<Teacher> teachers = (ArrayList<Teacher>)this.getAll();
		
		teachers.add(t);
		this.clearAndWrite(teachers);
	}

	@Override
	public void update(int id, Teacher t) {
		if(t == null)
			return;
		if(id != t.getId())
			return;
		
		ArrayList<Teacher> teachers = (ArrayList<Teacher>)this.getAll();
		
		delete(get(id));
		teachers.add(t);
		this.clearAndWrite(teachers);
	}

	@Override
	public void delete(Teacher t) {
		if(t == null)
			return;
		ArrayList<Teacher> teachers = (ArrayList<Teacher>)this.getAll();
		
		Teacher teacher = get(t.getId());
		teachers.remove(teacher);
		
		this.clearAndWrite(teachers);
	}
	
	public void clearAndWrite(List<Teacher> teachers) {
		Properties properties = new Properties();
		
		teachers.forEach(t -> 
			{
				properties.setProperty("" + t.getId(), 
						t.getShortcut() 
						+ "-" + t.getNachname() 
						+ "-" + (t.getVorname() != null && t.getVorname() != "" ? t.getVorname() : nullString) 
						+ "-" + t.getGender().toString());
				try {
					properties.store(new FileOutputStream(fileName), null);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
	}
}
