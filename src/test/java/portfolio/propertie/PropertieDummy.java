package portfolio.propertie;

import java.util.StringTokenizer;

import portfolio.domain.Gender;
import portfolio.domain.Teacher;

public class PropertieDummy {

	public static void main(String[] args) {
		TeacherManager tManager = TeacherManager.getInstance("teacher.properties");
		
		tManager.insert(new Teacher(1, "abc", "nachName", "vorName", Gender.M));
		tManager.insert(new Teacher(2, "abc", "nachName", "vorName", Gender.W));
//		StringTokenizer tokenizer = new StringTokenizer((String) value, "-");
		
//		while(tokenizer.hasMoreTokens()){
//			System.out.println(tokenizer.nextToken());
//		}
	}

}
