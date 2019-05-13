package portfolio.dao;

import java.util.List;

public interface DAO<T> {
	String nullString = "#NULL#";
	
   T get( int id );
   List<T> getAll();
   void insert(T t);
   void update(int id, T t); //updates Entity id using attributes in t
   void delete(T t);
}

