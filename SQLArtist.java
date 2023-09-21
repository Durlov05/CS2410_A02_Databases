package A02_Database_App;

/**
 * SQL Code pertaining to the Artist Table to create, insert, drop and select data.
 * @author Syed Mujibur Rahman (Mujib) + Nikki Burr + Nikki Buzianis
 */
public class SQLArtist {
	public static final String createTable = 
			"CREATE TABLE Artist ("
			+ "ArtistId  int not null primary key "
			+ "GENERATED ALWAYS AS IDENTITY "
			+ "(START WITH 500, INCREMENT BY 1), "
			+ "ArtistName varchar(255),"
			+ "HourlyRate Decimal(5,2)"
			+ ")";
	
	public static final String insertData = 
			"INSERT INTO Artist (ArtistName, HourlyRate) VALUES "
			+ "('Tomart', 10.50), "
			+ "('Loius', 15.50), "
			+ "('BobbyO', 25.50), "
			+ "('Tish', 55.50), "
			+ "('Sly', 99.99) ";

	
	public static final String selectAll = 
			"Select * from Artist";

	public static final String dropTable = 
			"DROP TABLE Artist";
}