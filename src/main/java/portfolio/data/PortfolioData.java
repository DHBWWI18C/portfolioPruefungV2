package portfolio.data;

import portfolio.dao.DAO;
import portfolio.dao.DAOMany;
import portfolio.domain.Course;
import portfolio.domain.StudentClass;
import portfolio.domain.Subject;
import portfolio.domain.Teacher;

public interface PortfolioData {
   public  DAO<Course> getCourseData();
   public  DAO<StudentClass> getStudentClassData();
   public  DAOMany<Teacher, Subject> getTeacherData();   
}
