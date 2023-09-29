package tattooPayroll;

/**
 * SQL Code pertaining to the Customer Table to create, insert, drop and select data.
 * @author Syed Mujibur Rahman (Mujib) + Nikki Burr + Nikki Buzianis
 */
public class SQLCustomer {
	public static final String createTable = 
			"CREATE TABLE Customer ("
			+ "CustomerId  int not null primary key "
			+ "GENERATED ALWAYS AS IDENTITY "
			+ "(START WITH 100, INCREMENT BY 1), "
			+ "FirstName varchar(255),"
			+ "LastName varchar(255),"
			+ "Phone varchar(255)"
			+ ")";
	
	public static final String insertData = 
			"INSERT INTO Customer (FirstName, LastName, Phone) VALUES "
			+ "('Tom', 'Ball', '123-456-7890'), "
			+ "('Joe', 'Mark', '123-456-8211'), "
			+ "('Bob', 'Bell', '123-456-8212'), "
			+ "('Tia', 'Jane', '123-456-8222'), "
			+ "('Seth', 'Ball', '123-235-7890'), "
			+ "('Tina', 'Mark', '123-232-8211'), "
			+ "('Will', 'Iam', '123-734-8212'), "
			+ "('Tommy', 'Hil', '123-844-8222'), "
			+ "('Myra', 'Johnson', '523-232-8212'), "
			+ "('Kolinda', 'Kossum', '735-754-8222')";
	
	public static final String selectAll = 
			"Select * from Customer";

	public static final String dropTable = 
			"DROP TABLE Customer";
}