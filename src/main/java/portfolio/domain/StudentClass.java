package portfolio.domain;

public class StudentClass implements Comparable<StudentClass> { //Kurs, z.B. WI16A, IT18
   private String courseShortcut; //WI, IT, etc.
   private int startYear;   //z.B. 16, 17, 18 
   private String spezifier; // z.B. A, B, auch Leerstring möglich
   
   public StudentClass(String courseShortcut, int startYear) {
      this(courseShortcut, startYear, "");
   }
   
   public StudentClass(String courseShortcut, int startYear, String spezifier) {
      if( courseShortcut==null || courseShortcut.length()!=2 || !courseShortcut.equals( courseShortcut.toUpperCase() ) ) {
         throw new IllegalArgumentException("kein erlaubtes Studiengangkürzel: " + courseShortcut);
      }
      
      if( startYear<0 || startYear>99 ) {
         throw new IllegalArgumentException("Kein erlaubter Jahrgang: " + startYear);
      }  
      
      if( spezifier==null || spezifier.length()==0 ) {
         spezifier="";
      }
      else if( spezifier.length()>1 || spezifier.charAt(0)<'A' || spezifier.charAt(0)>'Z' ) {
         throw new IllegalArgumentException("Keine erlaubte Zusatzbezeichnung (A, B, C, ...) für Kurs: " + spezifier);
      }
      this.courseShortcut = courseShortcut;
      this.startYear = startYear;
      this.spezifier = spezifier;
   }
   
   public String toString() {
      String yearString = String.format("%02d", startYear);
      return courseShortcut + yearString + this.spezifier;
   }
   
   public boolean equals2( Object o ) {
      if( o==this )
         return true;
      if( o==null || o.getClass()!=this.getClass() )
         return false;
      StudentClass other =(StudentClass)o;
      return other.courseShortcut.equals( this.courseShortcut ) &&
             other.spezifier.equals( this.spezifier ) &&
             other.startYear == this.startYear;
   }

   public int hashCode() {
      int p = 31;
      return ((courseShortcut.hashCode() * p) + spezifier.hashCode()) * p + startYear;
   }

   @Override
   public int compareTo(StudentClass c) {
      int cmp = this.courseShortcut.compareTo(c.courseShortcut);
      if( cmp!=0 )
         return cmp;
      
      cmp = this.startYear - c.startYear;
      if( cmp!=0 )
         return cmp;
      
      return this.spezifier.compareTo( c.spezifier );
   }

   
}
