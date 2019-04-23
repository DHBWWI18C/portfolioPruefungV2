package portfolio.dao;

import java.util.List;

public interface DAOMany<T1, T2> extends DAO<T1> {
   /*
    * Hinzufügen eines Eintrags für die Beziehung t1-t2.
    * Bedingungen: Entities t1 und t2 müssen existieren
    *              Eintrag t1-t2 darf nicht schon vorhanden sein
    * Return: Erfolgsfall=true, sonst=false
    */
   boolean addManyToManyEntry (T1 t1, T2 t2);   
   /*
    * Löschen des Eintrags für die Beziehung t1-t2.
    * (kein (!) Löschen der Entities t1 resp. t2
    */
   boolean deleteManyToManyEntry( T1 t1, T2 t2);  
   /*
    * Gibt alle Entities t aus T2 zurück, die zu t1 in Beziehung t1-t stehen
    * Bedingung: Entity t1 muss existieren
    * Return: List, gegebenefalls leer; Fehlerfall=null
    */
   List<T2> getAllFor(T1 t1);
}
