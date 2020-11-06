package gui;


import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Deserialize {
	public static ObjectInputStream obi;
	
	public static void main(String[] args){
		openStream();
		viewList();
		closeStream();
	}
	
	public static void openStream() {
		try {
			obi = new ObjectInputStream(Files.newInputStream(Paths.get("studentList.ser")));
		}catch(FileNotFoundException e) {
			e.getStackTrace();
			System.err.println("File cannot found");
		}catch(IOException e) {
			System.err.println("File cannot read");
			e.getStackTrace();
		}
	}
	
	public static Student readStudent() throws ClassNotFoundException, IOException{
		return (Student) obi.readObject(); //deserialization of object
	}
	
	public static void viewList() {
		try{
			while(true){ 
				Student student = readStudent();
				System.out.println("*****************************************************");
				System.out.println("Student ID:"+ student.getStdID());
				System.out.println("Student Name: " + student.getFirstName() + " " + student.getLastName());
				System.out.println("Courses: " + student.getCourses());
				System.out.println();
			}
		}catch (EOFException endOfFileException) {
			System.out.println("Result End");
		}catch (ClassNotFoundException classNotFoundException){
			System.err.println("Invalid object type.");
		}catch (IOException err){
			System.err.println("File cannot read.");
			System.err.println("e");
		}
	}
	
	public static void closeStream() {
		try{
			if (obi != null)
				obi.close();
		}catch (IOException err){
			System.err.println("Error closing file.");
			System.exit(1);
		}
	}
}