package tattooPayroll;

/**
 * SQL Code pertaining to the Appointment Table to create, insert, drop and select data.
 * @author Syed Mujibur Rahman (Mujib) + Nikki Burr + Nikki Buzianis
 */
public class SQLAppointment {
	public static final String createTable = 
			"CREATE TABLE Appointment ("
			+ "ApptId  int not null primary key "
			+ "GENERATED ALWAYS AS IDENTITY "
			+ "(START WITH 800, INCREMENT BY 1), "
			+ "AritstID int,"
			+ "CustomerID int,"
			+ "ApptDate DATE,"
			+ "Hours int"
			+ ")";
	
	public static final String insertData = 
			"INSERT INTO Appointment (AritstID, CustomerID, ApptDate, Hours) VALUES "
			+ "(500, 101, '2023-10-10',2), "
			+ "(501, 103, '2023-09-25',1), "
			+ "(500, 102, '2023-10-09',3), "
			+ "(504, 100, '2023-10-01',5), "
			+ "(502, 106, '2023-09-27',4)";
	
	public static final String selectAll = 
			"Select * from Appointment";

	public static final String dropTable = 
			"DROP TABLE Appointment";
}