package tattooPayroll;

/**
 * SQL Code pertaining to the Artist Table to create, insert, drop and select data.
 * @author Syed Mujibur Rahman (Mujib) + Nikki Burr + Nikki Buzianis
 */
public class SQLArtist {
	/**
	 * Creates the Artist table with an auto-generated primary key for ArtistId.
	 */
	public static final String createTable = 
			"CREATE TABLE Artist ("
			+ "ArtistId  int not null primary key "
			+ "GENERATED ALWAYS AS IDENTITY "
			+ "(START WITH 500, INCREMENT BY 1), "
			+ "ArtistName varchar(255),"
			+ "HourlyRate Decimal(5,2)"
			+ ")";
	
	/**
	 * Inserts data into the Artist table.
	 */
	public static final String insertData = 
			"INSERT INTO Artist (ArtistName, HourlyRate) VALUES "
			+ "('Tomart', 10.50), "
			+ "('Loius', 15.50), "
			+ "('BobbyO', 25.50), "
			+ "('Tish', 55.50), "
			+ "('Sly', 99.99) ";

	/**
	 * Selects all data from the Artist table.
	 */
	public static final String selectAll = 
			"Select * from Artist";

	/**
	 *  Deletes the Artist table.
	 */
	public static final String dropTable = 
			"DROP TABLE Artist";
}