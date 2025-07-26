import java.sql.*;
import java.util.*;
import java.io.*;

interface Comman{
	public void add();
	public void view();
}
class DBConnection {
    public static Connection getConnection() throws Exception {
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream("dbconfig.properties");
        props.load(fis);

        String url = props.getProperty("url");
        String user = props.getProperty("user");
        String pass = props.getProperty("pass");

        Connection conn = DriverManager.getConnection(url, user, pass);
        return conn;
    }
}
class Patient implements Comman{
	String name,gender,password;
	Doctors doctors = new Doctors();
	int age;
	String queryToInsert = "INSERT INTO patient(patientName,patientAge,patientGender,patientPassword) VALUES(?,?,?,?)";
	String queryToSelect = "SELECT * FROM patient";
	String LoginQuery = "SELECT patientName,patientPassword FROM patient WHERE patientName=? and patientPassword=?";
	Scanner sc = new Scanner(System.in);
	
	public void login() {
		sc.nextLine();
		System.out.print("\nEnter Your Name: ");
		String checkname = sc.nextLine();
		System.out.print("Enter Your Password: ");
		password = sc.nextLine();
		try {
			Connection connection = DBConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(LoginQuery);
			statement.setString(1, checkname);
			statement.setString(2, password);
			
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				if(password.equalsIgnoreCase(resultSet.getString("patientPassword"))) {
					System.out.println("\nLogin sucessfully!\n");
					options();
				}
			}
			System.out.println("Wrong password!");
			Main obj = new Main();
			obj.startPoint();
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	@Override
	public void add() {
		System.out.print("Enter patient name: ");
		this.name=sc.nextLine();
		System.out.print("Enter patient Age: ");
		this.age=sc.nextInt();
		System.out.print("Enter patient Gender (male,female): ");
		this.gender=sc.next();
		System.out.print("Enter Strong Password: ");
		this.password=sc.next();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DBConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(queryToInsert);
			statement.setString(1,this.name);
			statement.setInt(2, age);
			statement.setString(3, gender);
			statement.setString(4, password);
			int x = statement.executeUpdate();
			if(x>0) {
				System.out.println("\nPatient Added Sucessfully!\n");
			}else {
				System.out.println("\nPatiend Not Added!\n");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		options();
	}
	public void options()
	{
		while(true) {
		    System.out.println("1. View Patient");
		    System.out.println("2. view Doctor");
		    System.out.println("3. Book Appointment");
		    System.out.println("4. EXIT");
		    System.out.print("Enter choice: ");
		    int ch = sc.nextInt();
		    switch (ch) {
			case 1:this.view();
				break;
			case 2:doctors.view();
				break;
			case 3:doctors.bookAppoitment();
				break;
			case 4:
				return;
			default:
				System.out.println("Choose between (1,2,3) !");
			}
		    
		}
	}

	@Override
	public void view() {
		try {
			Connection connection = DBConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(queryToSelect);
			ResultSet resultSet = statement.executeQuery();
			ResultSetMetaData metadata = resultSet.getMetaData();
			int countColumn = metadata.getColumnCount();
			System.out.println();
			for(int i=1;i<=countColumn-2;i++) {
				System.out.printf("%-15s",metadata.getColumnName(i));
			}
			System.out.println();
			
			while(resultSet.next()) {
				for(int i=1;i<=countColumn-2;i++) {
					System.out.printf("%-15s",resultSet.getString(i));
				}
				System.out.println();
			}
			System.out.println();
		
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
}
class Doctors implements Comman{
	int patientAge,doctorID;
	String queryToSelect = "SELECT * FROM doctor";
	Scanner sc = new Scanner(System.in);
	String patientName,patientGender,contactNumber,appoDate;
	String queryToInsert="INSERT INTO bookAppointment(patientName,patientAge,patientGender,contactNumber,doctorID,dateOfAppointment) VALUES(?,?,?,?,?,?)";
	String queryToId = "SELECT bookID FROM bookAppointment WHERE patientName=?";
	@Override
	public void add() {
		
	}

	@Override
	public void view() {
		try {
			Connection connection = DBConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(queryToSelect);
			ResultSet resultSet = statement.executeQuery();
			ResultSetMetaData metadata = resultSet.getMetaData();
			int countColumn = metadata.getColumnCount();
			System.out.println();
			for(int i=1;i<=countColumn;i++) {
				System.out.printf("%-20s",metadata.getColumnName(i));
			}
			System.out.println();
			
			while(resultSet.next()) {
				for(int i=1;i<=countColumn;i++) {
					System.out.printf("%-20s",resultSet.getString(i));
				}
				System.out.println();
			}
			System.out.println();
		
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	public void bookAppoitment() {
		System.out.print("Enter patient name: ");
		this.patientName=sc.nextLine();
		System.out.print("Enter patient age: ");
		this.patientAge=sc.nextInt();
		System.out.print("Enter patient gender (male,female): ");
		this.patientGender=sc.next();
		System.out.print("Enter contact number: ");
		this.contactNumber=sc.next();
		System.out.print("Enter doctor id: ");
		this.doctorID = sc.nextInt();
		System.out.print("Date of appoitment(YYYY-MM-DD): ");
		this.appoDate=sc.next();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DBConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(queryToInsert);
			statement.setString(1,this.patientName);
			statement.setInt(2,this.patientAge);
			statement.setString(3,this.patientGender);
			statement.setString(4,this.contactNumber);
			statement.setInt(5,this.doctorID);
			statement.setString(6,this.appoDate);
			System.out.println("Appoitment Booked!");
			int x = statement.executeUpdate();
			PreparedStatement statement2 = connection.prepareStatement(queryToId);
			statement2.setString(1, patientName);
			ResultSet resultSet = statement2.executeQuery();
			if(resultSet.next()) {
				System.out.println("Appointment id = "+resultSet.getInt("bookID"));
			}else {
				System.out.println("Booking server down.....");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
}


public class Main {
	int x=1;
	Scanner sc = new Scanner(System.in);
	Patient patient = new Patient();
	Doctors doctors = new Doctors();
	String title = "Hospital Management";
	public void startPoint() {
		while (true) {
			System.out.printf("Hospital Management");
		    System.out.println("\n1. SignUp");
		    System.out.println("2. Login");
		    System.out.println("3. Exit");
		    System.out.print("Enter Choice: ");
		    int ch = sc.nextInt();  

		    switch (ch) {
		        case 1:
		            patient.add();
		            break;
		        case 2:
		        	patient.login();
		        case 3:
		            return;
		        default:  System.out.println("Please choose a valid option (1, 2).");
		    }
		}
	}
	public static void main(String[] args) {
		Main obj = new Main();
		obj.startPoint();
	}	
}

