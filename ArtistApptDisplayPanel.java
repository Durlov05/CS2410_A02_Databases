package tattooPayroll;

import javax.swing.JPanel;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JLabel;
import java.awt.BorderLayout;

public class ArtistApptDisplayPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private int id; // use this to run queries?
	//private String name;
	private static final String databaseURL = "jdbc:derby:FirstDatabase;create=true";
	StringBuilder artistApptSb = new StringBuilder();

	/**
	 * Create the panel.
	 */
	public ArtistApptDisplayPanel(int id, String name) {
		this.id = id;
		accessDbApptPnl();
		setBackground(new Color(255, 128, 64));
		setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel = new JLabel(artistApptSb.toString());
		add(lblNewLabel, BorderLayout.CENTER);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		add(lblNewLabel_1, BorderLayout.NORTH);

		

	}
	
	private void accessDbApptPnl() {
		try (Connection connection = DriverManager.getConnection(databaseURL);
				Statement statement = connection.createStatement()) {
			ResultSet apptTableRs = statement.executeQuery(SQLAppointment.selectArtistAppt(id));
			
			
			
			while (apptTableRs.next()) {
				Object apptID = apptTableRs.getObject("ApptID");
				Object artistID = apptTableRs.getObject("ArtistID");
				Object customerID = apptTableRs.getObject("CustomerID");
				Object apptDate = apptTableRs.getObject("ApptDate");
				Object hours = apptTableRs.getObject("Hours");

				
				//System.out.println(apptID + " " + artistID + " " + customerID + " " + apptDate + " " + hours);
				artistApptSb.append(apptID + " " + artistID + " " + customerID + " " + apptDate + " " + hours + "\n");
				
				
				
			}
			
			
			ResultSet aristTableRs = statement.executeQuery(SQLArtist.selectAll);// (selectStuff);
			while (aristTableRs.next()) {
				Object id = aristTableRs.getObject("ARTISTID");
				Object name = aristTableRs.getObject("ARTISTNAME");
				DropdownPersonItem item = new DropdownPersonItem(id, name);
			}

//			statement.execute(SQLAppointment.dropTable);
//			statement.execute(SQLAppointment.createTable);
//			statement.execute(SQLAppointment.insertData);
//			ResultSet resultSet3 = statement.executeQuery(SQLAppointment.selectAll);//(selectStuff);
//			printTableData(resultSet3);
//			System.out.println("");

		} catch (SQLException e) {
			System.out.println("Something went wrong accessing SQL.");
			e.printStackTrace();
		}
	}

}