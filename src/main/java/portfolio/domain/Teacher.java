package portfolio.domain;

import java.util.Comparator;

public class Teacher implements Comparable<Teacher> {

	public static Comparator<Teacher> SORT_NACHNAME = new SortNachname();
	public static Comparator<Teacher> SORT_ID = new SortId();

	private int id; // eindeutig, nicht änderbar
	private String shortcut; // genau 3 Buchstaben, eindeutig
	private String nachname; // mind. ein Buchstabe
	private String vorname; // optional, falls null -> ""
	private Gender gender; // optional

	public Teacher(int id, String shortcut, String nachname, String vorname, Gender gender) {
		super();
		this.id = id;
		setShortcut(shortcut);
		setNachname(nachname);
		setVorname(vorname);
		setGender(gender);
	}

	public Teacher(int id, String shortcut, String nachname, String vorname) {
		this(id, shortcut, nachname, vorname, null);
	}

	public Teacher(int id, String shortcut, String nachname) {
		this(id, shortcut, nachname, "", null);
	}

	public int getId() {
		return id;
	}

	// -->Wird nicht verwendet
//	public void setId(int id) {
//		this.id = id;
//	}

	public String getShortcut() {
		return shortcut;
	}

	// TODO auf jeden Fall nochmal testen
	public void setShortcut(String shortcut) {
		if (shortcut != null) {
			if (shortcut.length() != 3 || !shortcut.chars().allMatch(Character::isLetter))
				throw new IllegalArgumentException("Shortcut darf nicht 'null' sein und muss 3 Buchstaben enthalten.");
			else
				this.shortcut = shortcut;
		} else
			throw new IllegalArgumentException("Shortcut darf nicht 'null' sein.");
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		if (nachname == null)
			throw new IllegalArgumentException("Nachname darf nicht 'null' sein.");
		if (nachname == "" || !nachname.chars().allMatch(Character::isLetter))
			throw new IllegalArgumentException("Nachname muss mindestens 1 Buchstabe enthalten.");

		this.nachname = nachname;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		if (vorname == null) {
			this.vorname = "";
			return;
		}
		if (!vorname.chars().allMatch(Character::isLetter)) {
			return;
		} else
			this.vorname = vorname;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		// optional->keine Prüfung
		this.gender = gender;
	}

	@Override
	public int compareTo(Teacher t) {
		// order by shortcut (only)

		if (t == null)
			throw new IllegalArgumentException("Lehrer (teacher) darf nicht 'null' sein.");
		return this.shortcut.compareTo(t.shortcut);
	}

	@Override
	public String toString() {
		return "Teacher [id=" + id + ", shortcut=" + shortcut + ", nachname=" + nachname + ", vorname=" + vorname
				+ ", gender=" + gender + "]";
	}

	// equals(nur id ist ausschlaggebend)
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Teacher other = (Teacher) obj;
		return id == other.id;
	}

	// hashCode()
	@Override
	public int hashCode() {
		return id;
	}

	private static class SortNachname implements Comparator<Teacher> {
		@Override
		public int compare(Teacher t1, Teacher t2) {
			return t1.nachname.toUpperCase().compareTo(t2.nachname.toUpperCase());
		}
	}

	private static class SortId implements Comparator<Teacher> {
		@Override
		public int compare(Teacher t1, Teacher t2) {
			Integer id1, id2;
			id1 = t1.id;
			id2 = t2.id;
			return id1.compareTo(id2);

		}
	}
}
