package gui;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;

//import students.Student;

public class Serialize {
	public static ObjectOutputStream obs;
	
	public static void main(String[] args){
		openStream();
		addList();
		closeStream();
	}
	
	public static void openStream() {
		try {
			obs = new ObjectOutputStream(Files.newOutputStream(Paths.get("studentList.ser")));
		}catch(IOException e) {
			System.err.println("File cannot open");
			e.getStackTrace();
			System.exit(1);
		}
	}
	
	public static void addList() {
			try{
				Scanner info = new Scanner(System.in);
				
				boolean addMore = true;
				System.out.println("Put your follow information");
				do {
					Student student = new Student(); // create student object
					
					System.out.println("Student ID: ");
					student.setStdID(info.nextInt()); // Int scanner
					
					System.out.println("First Name: ");
					student.setFirstName(info.next());
					
					System.out.println("Last Name: ");
					student.setLastName(info.next());
					
					System.out.println("Course Names (please put space between them: )");
					student.setCourses(info.next());
					String[] tokens = info.nextLine().split("\\s+");
					//set the courses
					for(int i = 0; i < tokens.length; i++){
						if(tokens[i].equals("")){ 
							continue;
						}else
							student.setCourses(tokens[i]);
					}
					obs.writeObject(student); //write student obj
					obs.flush();// clean
					
					System.out.println("Wanna more add? yes(Y) no(N)");
					String answer = info.nextLine();
					while(!(answer.equalsIgnoreCase("y")) && !(answer.equalsIgnoreCase("n"))){
						System.out.println(answer + " is an invalid answer. Please enter Y or N: ");
	                    answer = info.nextLine();
					}
					if(answer.equalsIgnoreCase("n")) {
						addMore = false; // condition check for looping
					}
				}while(addMore); // looping
				obs.flush();
				info.close(); // close scanner
			}catch (NoSuchElementException elementException){
				System.err.println("Invalid input.Try again.");
			}catch (IOException e){
				System.err.println("File cannot write.");
				e.getStackTrace();				
			}
		}		
	public static void closeStream() {
		try{
			if(obs != null)
				obs.close();
			System.out.println("End Program");
		}catch(IOException err){
			System.err.println("Error open file.");
		}
	}
}
