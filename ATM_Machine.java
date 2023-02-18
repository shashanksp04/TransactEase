
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import Connect_SQL.Create_Table;

/*
 * This class has methods that implement the functions of the ATM machine such as Creating Account, Depositing Money, Withdrawing, Money
 * Checking Balance and Changing the pin
 */
public class ATM {

	// private static int withdraw_limit=15;
	// private static int withdraw_amount=1000;
	static ArrayList<String> list;  
	static Scanner sc=new Scanner(System.in);
	private static String update="";
	private static int input_amount=0;
	private static String account_number="";
	private static String account_name="";
	private static int count1=0;
	private static int count2=0;
	private static int flag;
	private static int a=0;
        
	/*
	This is a static method which creates an account number for the new user.
	It doesn't take any parameters and returns a String variable containing the Account Number which is generated using the Random class 
	*/
	public static String create_Account_Number() {
		Random random = new Random();
		String account="";
		for(int index=0;index<16;index++) {
			account+=random.nextInt(0,9);
		}
		return account;
	}

	/*
	 * This is a static method which is used to create a new Account for the user. This method doesn't have any parameters but uses 
	 * the Scanner class to take the input from the user. It uses the "create_Account_Number" method to generate account number
	 * for the user.
	 * This method has no return type
	 * After creating the account, it displays the account number to user for reference and also asks the user if he wants to see the
	 * Main Menu again 
	 */
	public static void creating_Account() {
		System.out.println("In order to create your account kindly enter the details below");
		System.out.println("Enter First name :");
		String name = sc.next()+" ";
		sc.nextLine();
		System.out.println("Enter Last name :");
		name += sc.next();
		sc.nextLine();
		//System.out.println(name);
		System.out.println("Enter address :");
		String address = sc.nextLine();
		//sc.nextLine();
		//System.out.println(address);
		System.out.println("Enter contact details :");
		String phone = sc.next();
		sc.nextLine();
		System.out.println("Enter Date of Birth in MM/DD/YYYY format :");
		String dob = sc.next();
		sc.nextLine();
		Person person = new Person(name,address,phone,dob);
		System.out.println("Kindly enter your custom pin :");
		int pin = sc.nextInt();
		sc.nextLine();
		person.setPin(pin);
		person.set_Account_Number(ATM.create_Account_Number());
		System.out.println("Your account number is :"+person.getAccount_number());
		System.out.println("Enter the amount to be added");
		input_amount=sc.nextInt();
		person.depositAmount(input_amount);
		update = "Balance : "+input_amount+"\n";
		person.setHistory(update);
		Create_Table.create_Table(++a);
		Create_Table.insert_Value(1, name, address, phone, dob,person.getBalance(),person.get_History(),pin,person.getAccount_number());
		//System.out.println(person.getAccount_number());
		System.out.println("May I help you with something else? Press * to see the main menu again");
		char ch = sc.next().charAt(0);
		if(ch == '*') {
			ATM.menu();
		}else {
			System.out.println("Wrong choice");
			System.exit(0);
		}

	}

	/*
	 * This is a static method which used to deposit money in the account of the user.
	 * The method doesn't have any parameters and uses the Scanner class to take the input from the user.
	 * It calls the "read_value" method of the Create_Table class to get the details of the user and then performs the action accordingly
	 * The method allows the ATM machine to accept three wrong inputs for the pin and after that it declines the transaction and displays 
	 * the appropriate method
	 * The method creates a temporary Person object to store the data entered by the user and then uses the "update_Value" method of the
	 * Create_Table class to update the information in the database
	 * It displays the confimation message to user for reference, once the action has been done and also asks the user if he wants to see 
	 * the Main Menu again 
	 */
	public static void depositing_Money() {
		count1=3;
		count2=3;
		flag=0;
		list = new ArrayList<>();
		System.out.println("In order to deposit the money, enter digits of your account and your name");
		System.out.println("Account_number: ");
		account_number=sc.next();
		System.out.println("First Name: ");
		account_name=sc.next()+" ";
		System.out.println("Last Name: ");
		account_name+=sc.next();
		String lab =  Create_Table.read_Value(account_name,account_number);
		String[] arr =lab.split("@");
		for(int i=0;i<arr.length;i++) {
			list.add(arr[i]);
		}
		int size = list.size();
		if(size== 4) {
			flag=1;
		}
		//System.out.println(list.get(3));
		int pin = Integer.parseInt(list.get(3));
		System.out.println("Enter the pin to proceed further");
		while(sc.nextInt() != pin) {
			System.out.println("Wrong pin, enter again");
			if(count2 == 0) {
				System.out.println("You've run out of your turns to enter the correct pin");
				System.out.println("Do you want to change your pin? Press 1 to change");
				if(sc.nextInt()==1) {
					ATM.change_pin();
				}else {
					System.exit(0);
				}
			}
			count2--;
		}
		Person obj = new Person();
		obj.depositAmount(Integer.parseInt(list.get(1)));
		obj.setHistory(list.get(2));
		System.out.println("Enter the amount to be deposited :");
		input_amount = sc.nextInt();
		obj.depositAmount(input_amount);
		update = "Balance : "+obj.getBalance()+"|| Deposit : "+input_amount+"\n";	
		obj.setHistory(update);
		ArrayList<String> updation = new ArrayList<>();
		updation.add(list.get(0));
		updation.add(String.valueOf(obj.getBalance()));
		updation.add(obj.get_History());
		System.out.println(list.get(3));
		updation.add(String.valueOf(list.get(3)));
		if(flag == 0) {
			System.out.println("Account not found");
			System.out.println("If you don't have an account, create one know.Press 1 to create account");
			if(sc.nextInt()==1) {
				ATM.creating_Account();
			}
		}else {
			try {
				if(Create_Table.update_Value(updation)) {
					System.out.println("Amount deposited successfully");
				}else {
					System.out.println("Amount deposit unsuccessfull");
				}
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}

		}
		System.out.println("May I help you with something else? Press * to see the main menu again");
		char ch = sc.next().charAt(0);
		if(ch == '*') {
			ATM.menu();
		}else {
			System.out.println("Wrong choice");
			System.exit(0);
		}
	}
	/*
	 * This is a static method which used to withdraw money from the account of the user.
	 * The method doesn't have any parameters and uses the Scanner class to take the inputs from the user.
	 * It calls the "read_value" method of the Create_Table class to get the details of the user and then performs the action accordingly
	 * The method allows the ATM machine to accept three wrong inputs for the pin and after that it declines the transaction and displays 
	 * the appropriate method
	 * The method creates a temporary Person object to store the data entered by the user and then uses the "update_Value" method of the
	 * Create_Table class to update the information in the database
	 * It displays the confimation message to user for reference, once the action has been done and also asks the user if he wants to see 
	 * the Main Menu again 
	 */
	public static void withdraw_amount() {
		count1=3;
		count2=3;
		flag=0;
		list = new ArrayList<>();
		System.out.println("In order to withdraw the money, enter digits of your account and your name");
		System.out.println("Account_number: ");
		account_number=sc.next();
		System.out.println("First Name: ");
		account_name=sc.next()+" ";
		System.out.println("Last Name: ");
		account_name+=sc.next();
		String lab =  Create_Table.read_Value(account_name,account_number);
		String[] arr =lab.split("@");
		for(int i=0;i<arr.length;i++) {
			list.add(arr[i]);
		}
		int size = list.size();
		if(size== 4) {
			flag=1;
		}
		//System.out.println(list.get(3));
		int pin = Integer.parseInt(list.get(3));
		System.out.println("Enter the pin to proceed further");
		while(sc.nextInt() != pin) {
			System.out.println("Wrong pin, enter again");
			if(count2 == 0) {
				System.out.println("You've run out of your turns to enter the correct pin");
				System.out.println("Do you want to change your pin? Press 1 to change");
				if(sc.nextInt()==1) {
					ATM.change_pin();
				}else {
					System.exit(0);
				}
			}
			count2--;
		}
		Person obj = new Person();
		obj.depositAmount(Integer.parseInt(list.get(1)));
		obj.setHistory(list.get(2));
		System.out.println("Enter the amount to be withdrawn :");
		input_amount = sc.nextInt();
		obj.withdrawAmount(input_amount);
		update = "Balance : "+obj.getBalance()+"|| Withdraw : "+input_amount+"\n";	
		obj.setHistory(update);
		ArrayList<String> updation = new ArrayList<>();
		updation.add(list.get(0));
		updation.add(String.valueOf(obj.getBalance()));
		updation.add(obj.get_History());
		//System.out.println(list.get(3));
		updation.add(String.valueOf(list.get(3)));
		if(flag == 0) {
			System.out.println("Account not found");
			System.out.println("If you don't have an account, create one know.Press 1 to create account");
			if(sc.nextInt()==1) {
				ATM.creating_Account();
			}
		}else {
			try {
				if(Create_Table.update_Value(updation)) {
					System.out.println("Amount withdrawn successfully");
				}else {
					System.out.println("Amount withdrawn unsuccessfull");
				}
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}

		}
		System.out.println("May I help you with something else? Press * to see the main menu again");
		char ch = sc.next().charAt(0);
		if(ch == '*') {
			ATM.menu();
		}else {
			System.out.println("Wrong choice");
			System.exit(0);
		}
	}
	/*
	 * This is a static method which used to display the balance in the account of the user.
	 * The method doesn't have any parameters and uses the Scanner class to take the input from the user.
	 * It calls the "read_value" method of the Create_Table class to get the details of the user and then performs the action accordingly
	 * The method allows the ATM machine to accept three wrong inputs for the pin and after that it declines the transaction and displays 
	 * the appropriate method
	 * The method creates a temporary Person object to store the data entered by the user and then uses the "update_Value" method of the
	 * Create_Table class to update the information in the database
	 * Besides Balance, it also allows the machine to ask the user if he wants to see the history of his account.
	 * It displays the Balance to user for reference, once the action has been done and also asks the user if he wants to see 
	 * the Main Menu again 
	 */
	public static void checking_Balance() {
		count1=3;
		count2=3;
		flag=0;
		list = new ArrayList<>();
		System.out.println("In order to check the balance, enter digits of your account and your name");
		System.out.println("Account_number: ");
		account_number=sc.next();
		System.out.println("First Name: ");
		account_name=sc.next()+" ";
		System.out.println("Last Name: ");
		account_name+=sc.next();
		String lab =  Create_Table.read_Value(account_name,account_number);
		String[] arr =lab.split("@");
		for(int i=0;i<arr.length;i++) {
			list.add(arr[i]);
		}
		int size = list.size();
		if(size== 4) {
			flag=1;
		}
		//System.out.println(list.get(3));
		int pin = Integer.parseInt(list.get(3));
		System.out.println("Enter the pin to proceed further");
		while(sc.nextInt() != pin) {
			System.out.println("Wrong pin, enter again");
			if(count2 == 0) {
				System.out.println("You've run out of your turns to enter the correct pin");
				System.out.println("Do you want to change your pin? Press 1 to change");
				if(sc.nextInt()==1) {
					ATM.change_pin();
				}else {
					System.exit(0);
				}
			}
			count2--;
		}
		String balance = list.get(1);
		String history = list.get(2);
		if(flag == 0) {
			System.out.println("Account not found");
			System.out.println("If you don't have an account, create one know.Press 1 to create account");
			if(sc.nextInt()==1) {
				ATM.creating_Account();
			}
		}else {
			System.out.println("Balance : "+balance);
			System.out.println("Do you also want to see the history of your account? Press 1 if yes");
			if(sc.nextInt() ==1) {
				System.out.println("Balance : "+history);
			}
		}
	}
	/*
	 * This is a static method which used to change the pin of the account of the user.
	 * The method doesn't have any parameters and uses the Scanner class to take the input from the user.
	 * It calls the "read_value" method of the Create_Table class to get the details of the user and then performs the action accordingly
	 * The method allows the ATM machine to accept three wrong inputs for the pin and after that it declines the transaction and displays 
	 * the appropriate method
	 * The method creates a temporary Person object to store the data entered by the user and then uses the "update_Value" method of the
	 * Create_Table class to update the information in the database
	 * It displays the confimation message to user for reference, once the action has been done and also asks the user if he wants to see 
	 * the Main Menu again 
	 */
	public static void change_pin() {
		count1=3;
		count2=3;
		flag=0;
		list = new ArrayList<>();
		System.out.println("In order to change the pin, enter digits of your account and your name");
		System.out.println("Account_number: ");
		account_number=sc.next();
		System.out.println("First Name: ");
		account_name=sc.next()+" ";
		System.out.println("Last Name: ");
		account_name+=sc.next();
		String lab =  Create_Table.read_Value_1(account_name,account_number);
		String[] arr =lab.split("@");
		for(int i=0;i<arr.length;i++) {
			list.add(arr[i]);
		}
		int size = list.size();
		if(size== 3) {
			flag=1;
		}
		//System.out.println(list.get(1));
		System.out.println("In order to change your pin, please enter your date of birth");
		while(!(sc.next().equals(list.get(1)))){
			System.out.println("Wrong date_of_birth, enter again");
			if(count2 == 0) {
				System.out.println("You've run out of your turns to enter the correct date_of_birth");
				System.exit(0);
			}
			count2--;
		}
		System.out.println("Enter the new pin");
		String new_pin=sc.next();
		ArrayList<String> updation = new ArrayList<>();
		updation.add(list.get(0));
		updation.add(list.get(1));
		updation.add(new_pin);
		if(flag == 0) {
			System.out.println("Account not found");
			System.out.println("If you don't have an account, create one know.Press 1 to create account");
			if(sc.nextInt()==1) {
				ATM.creating_Account();
			}
		}else {
			try {
				if(Create_Table.update_Value_1(updation)) {
					System.out.println("Pin changed successfully");
				}else {
					System.out.println("Pin changed unsuccessfull");
				}
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
		System.out.println("May I help you with something else? Press * to see the main menu again");
		char ch = sc.next().charAt(0);
		if(ch == '*') {
			ATM.menu();
		}else {
			System.out.println("Wrong choice");
			System.exit(0);
		}
	}

	/*
	 * This is the main method of this class. It greets the user when it's called and then displays a set of choices. 
	 * It accpets the input using the Scanner class and then uses switch statement to implement the choice entetred by the user
	 */
	public static void menu() {
		System.out.println("Greetings..!! How may I help you?");
		System.out.println("Press 1 for Creating an account"+"\n"+"Press 2 for Depositing money"+
				"\n"+"Press 3 for Withdrawing money"+"\n"+"Press 4 for Checking the balance"+"\n"+
				"Press 5 for changing the pin");
		int choice = sc.nextInt();
		switch (choice) {
		case 1:
			ATM.creating_Account();
			break;
		case 2:
			ATM.depositing_Money();
			break;
		case 3:
			ATM.withdraw_amount();
			break;
		case 4:
			ATM.checking_Balance();
			System.out.println("May I help you with something else? Press * to see the main menu again");
			char ch = sc.next().charAt(0);
			if(ch == '*') {
				ATM.menu();
			}else {
				System.out.println("Wrong choice");
				System.exit(0);
			}
			break;
		case 5:
			ATM.change_pin();
			break;
		default :
			System.out.println("Wrong choice");
		}
	}

}

import java.util.ArrayList;

/*
 * This class stores the detail of the user who's using the ATM machine of the bank. It has getters and setters which 
 * provide access to the private instance variables of the class
 */
public class Person {
    
	/*
	 * Instance variables to store the different details of the user who's having an account in the bank
	 */
	private String name,address,phone,dob;
	private int pin;
	private String account_number;
	private int balance;
	private ArrayList<String> history;

	/*
	 * Constructor of Person class
	 * Initalizes the instance variables of this class
	 */
	public Person(String name,String address,String phone,String dob){
		this.name=name;
		this.address=address;
		this.phone=phone;
		this.dob=dob;
		history = new ArrayList<>();
		balance=0;
	}
	/*
	 * Default constructor of the Person class
	 */
	public Person() {
		this("","","","");
	}

	/*
	 * Setters and Getter functions for the private instance variables of the Person class
	 */
	public void setPin(int pin) {
		this.pin=pin;
	}

    public void depositAmount(int cash) {
    	this.balance+=cash;
    }

    public void withdrawAmount(int cash) {
    	this.balance-=cash;
    }

    public void setHistory(String history) {
    	this.history.add(history);
    }

	public void set_Account_Number(String number) {
		this.account_number=number;
	}

	public String getName() {
		return this.name;
	}

	public String getAddress() {
		return this.address;
	}

	public String getPhone() {
		return this.phone;
	}

	public String getDob() {
		return this.dob;
	}

	public int getPin() {
		return this.pin;
	}

	public String getAccount_number() {
		return this.account_number;
	}

	public int getBalance() {
		return this.balance;
	}

    public String get_History(){
    	return this.history.toString();
    }

    public String toString() {
    	String answer="";
    	answer+="Name: "+this.name+"\n";
    	answer+="Phone: "+this.phone+"\n";
    	return answer;
    }
}

package Connect_SQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.spi.DirStateFactory.Result;

import org.postgresql.util.PSQLException;

/*
 * This class executes queries of SQL to create a database storing the information of the cutomers and returning the information 
 * for performing various actions as mentioned above
 */
public class Create_Table {
         
	static Connection connection = null; 
	static Statement statement = null;
	
	static Connect obj = new Connect();

    /*
	 * This is a static method, it creates the table title "Accounts" in SQL database which is used to store the information of the data
	 * This method is called by "creating_Account" method of ATM class to store user info 
	 */
	public static void create_Table(int a) {
		
		connection = obj.get_Connect();
		
		try {
			if(a==1) {
				String query = "CREATE TABLE Accounts(Serial INT,Name VARCHAR(1005700),Address VARCHAR(1005700),"
						+ "Phone VARCHAR(1005700), Date_Of_Birth VARCHAR(1005700),Balance VARCHAR(20000), History VARCHAR(1005700), Pin INT,Account_number VARCHAR(16));";
				statement = connection.createStatement();
				statement.executeUpdate(query);
				System.out.println("Done");
			}
		}catch(Exception e) {
			System.out.println(e);
		}
	}

	/*
	 * This is a static method which stores the value of the user into SQL Database
	 * It takes user's name,address,phone number,date of birth,balance amount,balance History,Pin and account number as parameter
	 */
	public static void insert_Value(int a,String Name, String Address, String Phone, String DOB, int Balance, String History, int Pin, String account) {
		
		connection = obj.get_Connect();
		
		int counter = a;
		String nam=Name,add=Address,phone=Phone,dOB=DOB;
		int balance = Balance;
		String history = History; 
		int pin=Pin; 
		String Account = account;
		
		try {
			String query = "INSERT INTO Accounts(serial,name,address,phone,date_of_birth,balance,history,pin,account_number) values("+counter+","+"\'"+nam+"\'"+","+"\'"+add+"\'"+","+"\'"+phone+"\'"+","+"\'"+dOB+"\'"+","+balance+","+"\'"+history+"\'"+","+pin+","+"\'"+Account+"\'"+");";
			// System.out.println(query);
			statement = connection.createStatement();
			statement.executeUpdate(query);
			System.out.println("Finished");
		}catch(Exception e) {
			System.out.println(e);
		}
	}

	/*
	 * This method is used to return the details of the account of a user who wishes to deposit/withdraw moeny or check balance
	 * It take user's name and account_number as parameters
	 */
	public static String read_Value(String name,String number) {
		connection = obj.get_Connect();
		ResultSet rs = null;
		String list = "";
		try {
			String query = "Select * FROM Accounts"+" WHERE account_number ="+"\'"+number+"\'"+" AND name="+"\'"+name+"\'"+";";
			statement = connection.createStatement();
			rs = statement.executeQuery(query);
			
			while(rs.next()) {
				list+=String.valueOf(rs.getInt(1)); // serial
				list+="@";
				list+=String.valueOf(rs.getInt(6)); // balance
				list+="@"; 
				list+=String.valueOf(rs.getString(7)); // History
				list+="@";
				list+=String.valueOf(rs.getInt(8)); // pin
				list+="@";
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	/*
	 * This method is used to return the details of the account of a user who wishes to change the pin of his account
	 * It take user's name and account_number as parameters
	 */
	public static String read_Value_1(String name,String number) {
		connection = obj.get_Connect();
		ResultSet rs = null;
		String list = "";
		try {
			String query = "Select * FROM Accounts"+" WHERE account_number ="+"\'"+number+"\'"+" AND name="+"\'"+name+"\'"+";";
			statement = connection.createStatement();
			rs = statement.executeQuery(query);
			
			while(rs.next()) {
				list+=String.valueOf(rs.getInt(1)); // serial
				list+="@";
				list+=String.valueOf(rs.getInt(5)); // dob
				list+="@"; 
				list+=String.valueOf(rs.getInt(8)); // pin
				list+="@";
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/*
	 * This method updates the information about the user in the SQL Database
	 * It returns true or false based on whether the updation took place or not
	 * It takes an ArrayList variable as a parameter which has the updated information of the user
	 * It is called when the user wants to withdraw/deposit money
	 */
	public static boolean update_Value(ArrayList list)  {
		// for list item 1: serial, item2 : balance , item3: history, item4: pin
		connection = obj.get_Connect();
		ResultSet rs = null;
		int serial = Integer.parseInt(String.valueOf(list.get(0)));
		int balance = Integer.parseInt(String.valueOf(list.get(1)));
		String history = String.valueOf(list.get(2));
		try {
			String query = "UPDATE Accounts "+"SET balance ="+balance+", history ="+"\'"+history+"\'"+" WHERE serial = "+serial+";";
			statement = connection.createStatement();
			rs = statement.executeQuery(query);
		}catch(SQLException e) {
			return true;
		}finally {
			//System.out.println("||||||");
		}
	    return false;
	}

	/*
	 * This method updates the information about the user in the SQL Database
	 * It returns true or false based on whether the updation took place or not
	 * It takes an ArrayList variable as a parameter which has the updated information of the user
	 * It is called when the user wants to change the pin of his account
	 */
	public static boolean update_Value_1(ArrayList list)  {
		// for list item 1: serial, item2 : balance , item3: history, item4: pin
		connection = obj.get_Connect();
		ResultSet rs = null;
		int serial = Integer.parseInt(String.valueOf(list.get(0)));
		String pin = String.valueOf(list.get(2));
		try {
			String query = "UPDATE Accounts "+"SET pin ="+"\'"+pin+"\'"+" WHERE serial = "+serial+";";
			statement = connection.createStatement();
			rs = statement.executeQuery(query);
		}catch(SQLException e) {
			return true;
		}
	    return false;
	}
}
