package tattooPayroll;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JLabel;
import java.awt.Font;

public class ApptDisplayPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final String databaseURL = "jdbc:derby:FirstDatabase;create=true";
	StringBuilder apptSb = new StringBuilder();


	/**
	 * Create the panel.
	 */
	public ApptDisplayPanel() {
		setBackground(new Color(199, 21, 133));
		setLayout(new BorderLayout(0, 0));
		accessDbApptPnl();
		
		JPanel apptDisplayPanel = createApptDisplayPnl();
		add(apptDisplayPanel, BorderLayout.CENTER);
		
		JLabel apptInfoLbl = createApptInfoLbl();
		apptDisplayPanel.add(apptInfoLbl);
		
		JPanel titlePanel = new JPanel();
		add(titlePanel, BorderLayout.NORTH);
		titlePanel.setLayout(new GridLayout(1, 0, 0, 0));

	}

	public JLabel createApptInfoLbl() {
		JLabel apptInfoLbl = new JLabel(apptSb.toString());
		apptInfoLbl.setFont(new Font("Lucida Grande", Font.PLAIN, 16));		
		return apptInfoLbl;

		
	}
	
	
	
	
	
		private void accessDbApptPnl() {
			try (Connection connection = DriverManager.getConnection(databaseURL);
					Statement statement = connection.createStatement()) {
				ResultSet apptTableRs = statement.executeQuery(SQLAppointment.selectAll);
				
				
				
				while (apptTableRs.next()) {
					Object apptID = apptTableRs.getObject("ApptID");
					Object artistID = apptTableRs.getObject("ArtistID");
					Object customerID = apptTableRs.getObject("CustomerID");
					Object apptDate = apptTableRs.getObject("ApptDate");
					Object hours = apptTableRs.getObject("Hours");

					
					//System.out.println(apptID + " " + artistID + " " + customerID + " " + apptDate + " " + hours);
					apptSb.append(apptID + " " + artistID + " " + customerID + " " + apptDate + " " + hours + "\n");
					
					
					
				}
				
				
				ResultSet aristTableRs = statement.executeQuery(SQLArtist.selectAll);// (selectStuff);
				while (aristTableRs.next()) {
					Object id = aristTableRs.getObject("ARTISTID");
					Object name = aristTableRs.getObject("ARTISTNAME");
					DropdownPersonItem item = new DropdownPersonItem(id, name);
				}

//				statement.execute(SQLAppointment.dropTable);
//				statement.execute(SQLAppointment.createTable);
//				statement.execute(SQLAppointment.insertData);
//				ResultSet resultSet3 = statement.executeQuery(SQLAppointment.selectAll);//(selectStuff);
//				printTableData(resultSet3);
//				System.out.println("");

			} catch (SQLException e) {
				System.out.println("Something went wrong accessing SQL.");
				e.printStackTrace();
			}
		}
		
		
		
		
		

	

	public JPanel createApptDisplayPnl() {
		JPanel apptDisplayPanel = new JPanel();
		apptDisplayPanel.setBackground(new Color(138, 43, 226));
		apptDisplayPanel.setBorder(new EmptyBorder(60, 100, 60, 100));
		return apptDisplayPanel;
	}

}