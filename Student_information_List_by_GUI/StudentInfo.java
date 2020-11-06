package gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StudentInfo extends JFrame{
	//Main menu panel buttons
	private JButton addNewStudentButton; 
	private JButton studentListButton;
	private JButton quitButton;
	//Add a new student information button
	private JButton saveButton;
	private JButton finishButton;
	//List of students information button
	private JButton closeButton;
	//Add new student labels and fields
	private JTextField[] fields = {
			new JTextField(10),
			new JTextField(10),
			new JTextField(10)};
	private JLabel[] labels = {
			new JLabel("Student ID "),  
			new JLabel("First Name "), 
			new JLabel("Last Name ")
			};
	private JLabel courseLabel;
	private JTextField courseField;
	
//	private boolean isMainScreen = false;
//	private boolean isAddStudentScreen = false;
	private boolean isStudentList = true;

	private Student student;
	
	public StudentInfo() {
		setLayout(null);
		setTitle("WORKSHOP5"); // Main frame Title
		setSize(Main.SCREEN_HEIGHT, Main.SCREEN_WIDTH);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
//////////////////////Main Page Panel Set///////////////////////////////////
		addNewStudentButton = new JButton("Add Student");
		addNewStudentButton.setBounds(50, 10, 400, 100);
		addNewStudentButton.setBackground(new Color(176, 224, 230));
		addNewStudentButton.setForeground(Color.WHITE);
		addNewStudentButton.setFont(new Font("Elephant", Font.BOLD, 30));
		addNewStudentButton.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				addStudent();				
			}
		});
		add(addNewStudentButton);
		
		studentListButton = new JButton("Student List");
		studentListButton.setBounds(50, 130, 400, 100);
		studentListButton.setBackground(new Color(000, 255, 127));
		studentListButton.setForeground(Color.WHITE);
		studentListButton.setFont(new Font("Elephant", Font.BOLD, 30));
		studentListButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				//listPage();
				listStudent();
			}
		});
		add(studentListButton);
		
		quitButton = new JButton("Quit");
		quitButton.setBounds(50, 250, 400, 100);
		quitButton.setBackground(new Color(210, 180, 140));
		quitButton.setForeground(Color.CYAN);
		quitButton.setFont(new Font("Elephant", Font.BOLD, 30));
		quitButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		add(quitButton);
		
   ///// Add student set ///////
		courseLabel = new JLabel("Enter Subject Codes(*Please add space between code)");
		courseField = new JTextField(50);
		student = null;
		
   ////// Save and Finish buttons set //////
		saveButton = new JButton("Save");
		saveButton.setBackground(new Color(173, 255, 047));
		finishButton = new JButton("Finish");
		finishButton.setBackground(new Color(176, 224, 230));

   /////// Close button set //////////
		closeButton = new JButton("Close");
		closeButton.setBackground(new Color(189, 183, 107));
	}
   ////// add new student event method /////////
	public void addStudent() {
		Serialize.openStream(); // 
		JFrame addSt = new JFrame("Add New Student"); // Add new student frame container
		addSt.setLayout(null);
		addSt.setSize(Main.SCREEN_HEIGHT, Main.SCREEN_WIDTH);
		addSt.setResizable(false);
		addSt.setVisible(true);
		addSt.setLocationRelativeTo(null);
		
		///set size and add into frame ////
		labels[0].setBounds(30, 15, 170, 50);
		labels[0].setFont(new Font("Elephant", Font.PLAIN, 15));
		fields[0].setBounds(190, 25, 180, 30);
		labels[1].setBounds(30, 55, 170, 50);
		labels[1].setFont(new Font("Elephant", Font.PLAIN, 15));
		fields[1].setBounds(190, 65, 180, 30);
		labels[2].setBounds(30, 95, 170, 50);
		labels[2].setFont(new Font("Elephant", Font.PLAIN, 15));
		fields[2].setBounds(190, 105, 180, 30);
		
		for(int i = 0; i < labels.length; i++){
			addSt.add(labels[i]);
			addSt.add(fields[i]);
		}

		courseLabel.setBounds(30, 135, 420, 50);
		courseLabel.setFont(new Font("Elephant", Font.PLAIN, 15));
		courseField.setBounds(30, 185, 420, 30);
		addSt.add(courseLabel); 
		addSt.add(courseField);		
		
		saveButton.setBounds(100, 260, 100, 60);
		addSt.add(saveButton);
		saveButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				
				fileGenerate(); //start writing

				//clear textField
				for(int i = 0; i < fields.length; i++){
					fields[i].setText("");
				}
				courseField.setText(""); 
				student = null; // set null
			}
		});
		
		finishButton.setBounds(300, 260, 100, 60);
		addSt.add(finishButton);
		finishButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				Serialize.closeStream(); //call serialize close stream
				addSt.dispose(); // return running frame
			}
		});
		
	}
	
	public void fileGenerate() {
		try {
			student = new Student();
			student.setStdID(Integer.parseInt(fields[0].getText()));
			student.setFirstName(fields[1].getText());
			student.setLastName(fields[2].getText());
			
			String courses = courseField.getText();
			String[] tokens = courses.trim().split("\\s+");
			//set the courses
			for(int i = 0; i < tokens.length; i++){
				student.setCourses(tokens[i]);
			}			
			Serialize.obs.writeObject(student);
			
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
   //////////////// Display student list /////////////////////////////////////
	
	public void listStudent() {

		Deserialize.openStream();
		
		JFrame listSt = new JFrame("Student List");
		
		listSt.setLayout(new BoxLayout(listSt.getContentPane(), BoxLayout.Y_AXIS));
		listSt.setSize(Main.SCREEN_HEIGHT_LIST,Main.SCREEN_WIDTH_LIST);
		listSt.setVisible(true);
		listSt.setLocationRelativeTo(null);
		listSt.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		try {
			while(isStudentList) {
				Student stdlist = (Student) Deserialize.obi.readObject();
				
				JPanel studentList = new JPanel();
				studentList.setLayout(new BoxLayout(studentList, BoxLayout.Y_AXIS));
								
				JLabel stdId = new JLabel ("Student ID: " + stdlist.getStdID());
				stdId.setFont(new Font("Consolas", Font.PLAIN, 16));
				studentList.add(stdId);
				
				JLabel fullName = new JLabel ("Student Full Name: " + stdlist.getFirstName() + stdlist.getLastName());
				fullName.setFont(new Font("Consolas", Font.PLAIN, 16));
				studentList.add(fullName);
				
				JLabel courses = new JLabel ("Subject List: " + stdlist.getCourses());
				courses.setFont(new Font("Consolas", Font.PLAIN, 16));
				studentList.add(courses);	
				
				JLabel line = new JLabel ("**************************************************");
				line.setFont(new Font("Consolas", Font.BOLD, 20));
				studentList.add(line);
				
				studentList.setAlignmentX(Component.CENTER_ALIGNMENT);
				listSt.add(studentList);
			}
		}catch (EOFException e) {
			System.out.printf("Read all the file%n");
		}catch (ClassNotFoundException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}
		Deserialize.closeStream();
		
		closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		listSt.add(closeButton);
		closeButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				listSt.dispose();
			}
		});
	}
	
}
