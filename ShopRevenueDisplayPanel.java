package tattooPayroll;

import javax.swing.JPanel;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.BorderLayout;
import javax.swing.JLabel;

public class ShopRevenueDisplayPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private String name;
	private static final String databaseURL = "jdbc:derby:FirstDatabase;create=true";
	StringBuilder revenueSb = new StringBuilder();
	
	/**
	 * Create the panel.
	 */
	public ShopRevenueDisplayPanel() {
		setBackground(new Color(123, 104, 238));
		setLayout(new BorderLayout(0, 0));
		accessDbRevenuePnl();
		
		JPanel titlePnl = new JPanel();
		add(titlePnl, BorderLayout.NORTH);
		
		JPanel revenueDisplayPnl = new JPanel();
		add(revenueDisplayPnl, BorderLayout.CENTER);
		revenueDisplayPnl.setLayout(new BorderLayout(0, 0));
		
		JLabel revenueInfoLbl = new JLabel(revenueSb.toString());
		revenueDisplayPnl.add(revenueInfoLbl, BorderLayout.CENTER);

	}
	
	private void accessDbRevenuePnl() {
		try (Connection connection = DriverManager.getConnection(databaseURL);
				Statement statement = connection.createStatement()) {
			ResultSet revenueTableRs = statement.executeQuery(SQLAppointment.selectTotalRevenue());
			
			
			
			while (revenueTableRs.next()) {
				Object date = revenueTableRs.getObject("Month");
				Object rev = revenueTableRs.getObject("totalRevenue");


				
				//System.out.println(apptID + " " + artistID + " " + customerID + " " + apptDate + " " + hours);
				revenueSb.append(date + ": " + rev + "\n");
				
				
				
			}
			
		} catch (SQLException e) {
			System.out.println("Something went wrong accessing SQL.");
			e.printStackTrace();
		}
	}
	
	
	

}