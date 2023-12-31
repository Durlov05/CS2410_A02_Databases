package tattooPayroll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 * Demonstrates that our database tables can created and contain data.
 * 
 * @author Syed Mujibur Rahman (Mujib) + Nikki Burr + Nikki Buzianis 
 */

public class A02_Database_Applicator {
	private static final String databaseURL = "jdbc:derby:FirstDatabase;create=true";
	private static Vector<DropdownItem> dropdownArtistList = new Vector<>();

	public static void main(String[] args) {
		try (Connection connection = DriverManager.getConnection(databaseURL);
				Statement statement = connection.createStatement()) {
			//statement.execute(SQLCustomer.dropTable);
			//statement.execute(SQLCustomer.createTable);
			//statement.execute(SQLCustomer.insertData);
//			ResultSet resultSet = statement.executeQuery(SQLCustomer.selectAll);// (selectStuff);
//			printTableData(resultSet);
//			System.out.println("");

			//statement.execute(SQLArtist.dropTable);
			//statement.execute(SQLArtist.createTable);
			//statement.execute(SQLArtist.insertData);
//			ResultSet resultSet2 = statement.executeQuery(SQLArtist.selectAll);// (selectStuff);
//			printTableData(resultSet2);
//			System.out.println("\nBLOOP BLORP\n");
//			while(resultSet2.next()) {
//				Object id = resultSet2.getObject("ARTISTID");
//				Object name = resultSet2.getObject("ARTISTNAME");
//				DropdownItem item = new DropdownPersonItem(id, name);	
//				dropdownArtistList.add(item);
//			}

			//statement.execute(SQLAppointment.dropTable);
//			statement.execute(SQLAppointment.createTable);
//			statement.execute(SQLAppointment.insertData);
			ResultSet resultSet3 = statement.executeQuery(SQLAppointment.selectAll);// (selectStuff);
			printTableData(resultSet3);
			System.out.println("^^^^");

//			ResultSet resultSet4 = statement.executeQuery(SQLAppointment.selectAllInfo);// (selectStuff);
//			printTableData(resultSet4);
//			System.out.println("");
			
//			ResultSet resultSet5 = statement.executeQuery(SQLAppointment.selectArtistAppt(500));// (selectStuff);
//			printTableData(resultSet5);
//			System.out.println("");

		} catch (SQLException e) {
			System.out.println("Something went wrong accessing SQL.");
			e.printStackTrace();
		}
		System.out.println("\ndone.\n");

		System.out.println(dropdownArtistList);

	}

	private static void printTableData(ResultSet resultSet) throws SQLException {
		ResultSetMetaData metaData = resultSet.getMetaData();
		int numberOfColumns = metaData.getColumnCount();
		System.out.println("numberOfColumns = " + numberOfColumns + "\n");
		// System.out.println("metaData = " + metaData);

		// print header
		// print data
		int strlen = 0;
		int[] columnWidths = new int[metaData.getColumnCount() + 1];

		for (int i = 1; i <= metaData.getColumnCount(); i++) {
			String label = metaData.getColumnLabel(i);
			System.out.print(label + " ");
			strlen = strlen + label.length() + 1;
			columnWidths[i] = label.length();
		}
		System.out.println();
		// System.out.println("-".repeat(strlen));

		// System.out.printf("%-7s %-8s", "Jon", "Meier")

		while (resultSet.next()) {
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				System.out.printf("%-" + columnWidths[i] + "s ", resultSet.getObject(i));
			}
			System.out.println();
		}
	}

}