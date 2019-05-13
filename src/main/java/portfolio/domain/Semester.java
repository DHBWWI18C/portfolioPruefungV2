package portfolio.domain;

public enum Semester implements Comparable<Semester>{
   
   WS ("Wintersemester"),
   SS ("Sommersemester"),
   SSCL ("Summer School");
   
   Semester(String description){
	   if(description != null)
		   this.description=description;
	   else description = "";
   }
   
   private String description;
   
   public String getDescription() {
      return description;
   }
}
