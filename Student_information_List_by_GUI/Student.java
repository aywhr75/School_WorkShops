package gui;


import java.util.ArrayList;

public class Student implements java.io.Serializable {
	private int stdID;
	private String firstName;
	private String lastName;
	private ArrayList<String> courses;
	
	public Student() {
		stdID = 0;
		firstName = "";
		lastName = "";
		courses = new ArrayList<String>(); 
	}

	public int getStdID() {
		return stdID;
	}

	public void setStdID(int stdID) {
		this.stdID = stdID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public ArrayList<String> getCourses() {
		return courses;
	}

	public void setCourses(String courses) {
		this.courses.add(courses);
	}
	
}
