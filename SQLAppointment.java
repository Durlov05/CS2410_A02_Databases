package tattooPayroll;

/**
 * SQL Code pertaining to the Appointment Table to create, insert, drop and select data.
 * @author Syed Mujibur Rahman (Mujib) + Nikki Burr + Nikki Buzianis
 */
public class SQLAppointment {
	
	/**
	 * Creates the Appointment table with an auto-generated primary key for ApptID.
	 */
	public static final String createTable = 
			"CREATE TABLE Appointment ("
			+ "ApptID  int not null primary key "
			+ "GENERATED ALWAYS AS IDENTITY "
			+ "(START WITH 800, INCREMENT BY 1), "
			+ "ArtistID int,"
			+ "CustomerID int,"
			+ "ApptDate DATE,"
			+ "Hours int"
			+ ")";
	
	/**
	 * Inserts data into the Appointment table.
	 */
	public static final String insertData = 
			"INSERT INTO Appointment (ArtistID, CustomerID, ApptDate, Hours) VALUES "
			+ "(500, 101, '2023-10-10',2), "
			+ "(501, 103, '2023-09-25',1), "
			+ "(500, 102, '2023-10-09',3), "
			+ "(504, 100, '2023-10-01',5), "
			+ "(502, 109, '2023-01-27',3), "
			+ "(500, 106, '2023-01-07',5), "
			+ "(504, 109, '2023-11-02',6), "
			+ "(501, 107, '2023-12-03',8), "
			+ "(500, 106, '2023-02-05',1), "
			+ "(502, 103, '2023-04-25',2), "
			+ "(504, 102, '2023-04-14',3), "
			+ "(502, 108, '2023-06-12',11), "
			+ "(500, 106, '2023-05-04',2), "
			+ "(503, 106, '2023-01-01',6), "
			+ "(501, 102, '2023-08-27',3), "
			+ "(503, 106, '2023-08-27',13), "
			+ "(501, 109, '2023-08-27',1), "
			+ "(500, 106, '2023-08-27',5), "
			+ "(500, 108, '2023-08-27',2), "
			+ "(504, 101, '2023-08-27',4), "
			+ "(504, 106, '2023-08-27',3), "
			+ "(500, 109, '2023-08-27',5), "
			+ "(501, 106, '2023-08-27',8)";
			;
	
	/**
	 * Selects all data from the Appointment table.
	 */
	public static final String selectAll = 
			"Select * from Appointment";
	
	/**
	 * Deletes the Appointment table.
	 */
	public static final String dropTable = 
			"DROP TABLE Appointment";
	
	/**
	 * Selects Total Revenue for the shop by Month.
	 */
	public static String selectTotalRevenue() {
		return "Select COALESCE(CASE WHEN month(ApptDate) = 1 THEN 'January' "
				+ "WHEN month(ApptDate) = 2 THEN 'February' "
				+ "WHEN month(ApptDate) = 3 THEN 'March' "
				+ "WHEN month(ApptDate) = 4 THEN 'April' "
				+ "WHEN month(ApptDate) = 5 THEN 'May' "
				+ "WHEN month(ApptDate) = 6 THEN 'June' "
				+ "WHEN month(ApptDate) = 7 THEN 'July' "
				+ "WHEN month(ApptDate) = 8 THEN 'August' "
				+ "WHEN month(ApptDate) = 9 THEN 'September' "
				+ "WHEN month(ApptDate) = 10 THEN 'October' "
				+ "WHEN month(ApptDate) = 11 THEN 'November' "
				+ "WHEN month(ApptDate) = 12 THEN 'December' "
				+ "END, 'Shop Total') as Month, "
				+ "sum(apt.hours * art.HourlyRate) as totalRevenue "
				+ "from Appointment as apt "
				+ "inner join artist as art on apt.artistID = art.artistid "
				+ "group by rollup (CASE WHEN month(ApptDate) = 1 THEN 'January' "
				+ "WHEN month(ApptDate) = 2 THEN 'February' "
				+ "WHEN month(ApptDate) = 3 THEN 'March' "
				+ "WHEN month(ApptDate) = 4 THEN 'April' "
				+ "WHEN month(ApptDate) = 5 THEN 'May' "
				+ "WHEN month(ApptDate) = 6 THEN 'June' "
				+ "WHEN month(ApptDate) = 7 THEN 'July' "
				+ "WHEN month(ApptDate) = 8 THEN 'August' "
				+ "WHEN month(ApptDate) = 9 THEN 'September' "
				+ "WHEN month(ApptDate) = 10 THEN 'October' "
				+ "WHEN month(ApptDate) = 11 THEN 'November' "
				+ "WHEN month(ApptDate) = 12 THEN 'December' "
				+ "END) ";
	}
	
	/**
	 * Selects Total Revenue for the shop by Month, Customer Name and Artist.
	 */
	public static String selectTotalArtistRevenue(int artID) {
		return "Select COALESCE(CASE WHEN month(ApptDate) = 1 THEN 'January' "
				+ "WHEN month(ApptDate) = 2 THEN 'February' "
				+ "WHEN month(ApptDate) = 3 THEN 'March' "
				+ "WHEN month(ApptDate) = 4 THEN 'April' "
				+ "WHEN month(ApptDate) = 5 THEN 'May' "
				+ "WHEN month(ApptDate) = 6 THEN 'June' "
				+ "WHEN month(ApptDate) = 7 THEN 'July' "
				+ "WHEN month(ApptDate) = 8 THEN 'August' "
				+ "WHEN month(ApptDate) = 9 THEN 'September' "
				+ "WHEN month(ApptDate) = 10 THEN 'October' "
				+ "WHEN month(ApptDate) = 11 THEN 'November' "
				+ "WHEN month(ApptDate) = 12 THEN 'December' "
				+ "END, 'Artist Total') as Month, "
				+ "Customer.FirstName + ' ' + Customer.LastName AS Customer_Name "
				+ "sum(apt.hours * art.HourlyRate) as Revenue "
				+ "from Appointment as apt "
				+ "inner join artist as art on apt.artistID = art.artistid "
				+ "and art.artistid = " + artID + " "
				+ "inner join Customer as Cust on apt.CustomerID = Cust.CustomerId"
				+ "group by rollup (CASE WHEN month(ApptDate) = 1 THEN 'January' "
				+ "WHEN month(ApptDate) = 2 THEN 'February' "
				+ "WHEN month(ApptDate) = 3 THEN 'March' "
				+ "WHEN month(ApptDate) = 4 THEN 'April' "
				+ "WHEN month(ApptDate) = 5 THEN 'May' "
				+ "WHEN month(ApptDate) = 6 THEN 'June' "
				+ "WHEN month(ApptDate) = 7 THEN 'July' "
				+ "WHEN month(ApptDate) = 8 THEN 'August' "
				+ "WHEN month(ApptDate) = 9 THEN 'September' "
				+ "WHEN month(ApptDate) = 10 THEN 'October' "
				+ "WHEN month(ApptDate) = 11 THEN 'November' "
				+ "WHEN month(ApptDate) = 12 THEN 'December' "
				+ "END, "
				+ "Cust.FirstName + ' ' + Cust.LastName) ";
	}
	
	/**
	 * Inserts new line of data into the Appointment table.
	 */
	public static String addAppt(int artID, int custID, String apptDate, int hours) {
		return "INSERT INTO Appointment (ArtistID, CustomerID, ApptDate, Hours) VALUES "
				+ "("+artID+", "+custID+", '"+apptDate+"',"+hours+") ";
	}
	
	/**
	 * Deletes line of data from the Appointment table using Appointment ID.
	 */
	public static String deleteAppt(int apptID) {
		return "DELETE FROM Appointment WHERE ApptID = " + apptID;
	}
	
	/**
	 * Updates line of data in the Appointment table using Appointment ID.
	 */
	public static String updateAppt(int apptID, int artID, int custID, String apptDate, int hours) {
		return "UPDATE Appointment SET ArtistID = "+artID+", CustomerID = "+custID+", "
				+ "ApptDate = '"+apptDate+"', Hours = "+hours + " "
				+ "WHERE ApptID = " + apptID;
	}
	
	/**
	 * 
	 */
	public static final String selectAllInfo = 
			"Select Appointment.ApptID, Appointment.ApptDate, "
			+ "Customer.FirstName || ' ' || Customer.LastName AS Customer_Name, "  
			+ "Artist.ArtistName As Artist_Name, Appointment.Hours "
			+ "from Appointment "
			+ "join Artist ON Appointment.ArtistID = Artist.ArtistId "
			+ "join Customer on Appointment.CustomerID = Customer.CustomerId";
	
	/**
	 * 
	 */
	public static String selectArtistAppt(int artID) {
		return "Select Appointment.ApptDate, Customer.FirstName + ' ' + Customer.LastName AS Customer_Name, Appointment.Hours "
				+ "from Appointment "
				+ "inner join Artist on Appointment.ArtistID = Artist.ArtistId "
				+ "and Artist.ArtistId = " + artID + " "
				+ "inner join Customer on Appointment.CustomerID = Customer.CustomerId";
	}
}



