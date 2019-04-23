package portfolio.domain;

import java.util.Comparator;

public class Teacher implements Comparable<Teacher>{
	
   public Comparator<Teacher> SORT_NACHNAME = new SortNachname();
   public Comparator<Teacher> SORT_ID = new SortId();
   
   private int id;          //eindeutig, nicht änderbar
   private String shortcut; //genau 3 Buchstaben, eindeutig
   private String nachname; //mind. ein Buchstabe
   private String vorname;  //optional, falls null -> ""
   private Gender gender;   //optional
   
   //LQ
   public Teacher() { }
   
   public Teacher(int id, String shortcut, String nachname, String vorname, Gender gender) {
	super();
	this.id = id;
	this.shortcut = shortcut;
	this.nachname = nachname;
	this.vorname = vorname;
	this.gender = gender;
}

@Override
   public int compareTo(Teacher o) {//TODO
	   //order by shortcut (only) ///LQ: Vielleicht irgendwas mit den Compare-Klassen unten machen?
	   Teacher teacher = (Teacher)o; //kein Abfangen von Exceptions - eventuell TODO
	   return this.shortcut.compareTo(teacher.shortcut);
   }
   
   //toString() 
   public String toString() {	//LQ
	      //String teacherString += "ID 
	      return String.format("%3d %s %s %s %s", id, shortcut, nachname, vorname, gender);
	   }
   
   //equals(nur id ist ausschlaggebend)
   @Override
   public boolean equals(Object obj) {	//LQ
      if (this == obj)
         return true;
      if (obj == null || getClass() != obj.getClass())
         return false;
      Teacher other = (Teacher) obj;
      return id == other.id;
   }
   
   //hashCode()   TODO
   @Override
   public int hashCode() {
	   final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
   }
   
   
   private class SortNachname implements Comparator<Teacher> {
      @Override
      public int compare(Teacher t1, Teacher t2) {
         return t1.nachname.toUpperCase().compareTo( t2.nachname.toUpperCase() );
      }  
   }
   private class SortId implements Comparator<Teacher> {
      @Override
      public int compare(Teacher t1, Teacher t2) {
         // TODO
    	  Integer id1, id2;
    	   id1 = t1.id;
    	   id2 = t2.id;
         return id1.compareTo(id2);
         
      }    
   }
}
