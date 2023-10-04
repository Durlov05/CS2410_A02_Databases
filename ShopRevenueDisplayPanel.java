package tattooPayroll;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;

/**
 * This is a display panel for the Tattoo Shop's Revenue.
 * 
 * @author Syed Mujibur Rahman (Mujib) + Nikki Burr + Nikki Buzianis
 */
public class ShopRevenueDisplayPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final String databaseURL = "jdbc:derby:FirstDatabase;create=true";
	
//	private String name;
//	StringBuilder revenueSb = new StringBuilder();
	
	private Container revenuePanel;
	private String monthStr;
	private String revStr;
	private int numOfData;
	JLabel[] arrayLbls;
	
	ArrayList<String> dbList = new ArrayList<String>();

	/**
	 * This creates the Revenue Display panel and calls the database to load it with
	 * the pertinent information.
	 */
	public ShopRevenueDisplayPanel() {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		accessDbRevenuePnl();
		
		JPanel titlePnl = createTitlePanel();
		titlePnl.setLayout(new BoxLayout(titlePnl, BoxLayout.X_AXIS));
		
		JLabel title = createTitleLbl();
		titlePnl.add(title);
		
		JScrollPane scrollPane = createScrollPane();
		add(scrollPane);
		
		JPanel revenuePanel = createRevenuePanel();
		scrollPane.setViewportView(revenuePanel);
		
		createRevenuePanel(revenuePanel);
		
		JPanel totalYearlyRev = createTotalRevPanel();
		add(totalYearlyRev);
			
		JLabel lblTotalYearRev = createLblTotalRev();
		totalYearlyRev.add(lblTotalYearRev);

	}

	private JPanel createRevenuePanel() {
		JPanel revenuePanel = new JPanel();
		revenuePanel.setBorder(new EmptyBorder(30, 50, 30, 50));
		return revenuePanel;
	}
	
	/**
	 * Creates a label to display total shop revenue.
	 * @return
	 */
	private JLabel createLblTotalRev() {
		JLabel lblTotalYearRev = new JLabel("$$$$");
		lblTotalYearRev.setFont(new Font("Lava Telugu", Font.PLAIN, 32));
		return lblTotalYearRev;
	}
	
	/**
	 * Creates a panel for Total Revenue to be dislayed.
	 * @return
	 */
	private JPanel createTotalRevPanel() {
		JPanel totalYearlyRev = new JPanel();
		totalYearlyRev.setFont(new Font("Lava Kannada", Font.PLAIN, 36));
		totalYearlyRev.setBorder(new TitledBorder(null, "Total Yearly Revenue", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		return totalYearlyRev;
	}
	
	/**
	 * Creates title label for Shop Revenue title.
	 * @return
	 */
	private JLabel createTitleLbl() {
		JLabel title = new JLabel("Shop Revenue");
		title.setBorder(new EmptyBorder(10, 30, 10, 30));
		title.setFont(new Font("Lava Telugu", Font.PLAIN, 32));
		return title;
	}
	/**
	 * Creates Title Panel for entre shop revenue.
	 * @return
	 */
	private JPanel createTitlePanel() {
		JPanel titlePnl = new JPanel();
		add(titlePnl, BorderLayout.NORTH);
		return titlePnl;
	}

	/**
	 * Creates Scrollable panel to display data.
	 * @return
	 */
	private JScrollPane createScrollPane() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(revenuePanel);
		return scrollPane;
	}

	/**
	 * Creates revenue panel that displays labels holding information about month and monthly revenue. 
	 * @param revenuePanel
	 */
	private void createRevenuePanel(JPanel revenuePanel) {
		revenuePanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		arrayLbls = new JLabel[numOfData];
		
		for (int i = 0; i < numOfData; i++) {
			arrayLbls[i] = new JLabel();
			arrayLbls[i].setText(dbList.get(i));
			arrayLbls[i].setFont(new Font("Lava Telugu", Font.PLAIN, 25));
			
			this.add(arrayLbls[i]);
			revenuePanel.add(arrayLbls[i]);
		}	
	}
	
	/**
	 * Access database information regarding monthly and yearly revenue for entire shop. 
	 */
	private void accessDbRevenuePnl() {
		try (Connection connection = DriverManager.getConnection(databaseURL);
				Statement statement = connection.createStatement()) {
			ResultSet revenueTableRs = statement.executeQuery(SQLAppointment.selectTotalRevenue());
			
			while (revenueTableRs.next()) {
				Object month = revenueTableRs.getObject("Month");
				Object rev = revenueTableRs.getObject("totalRevenue");
				
				monthStr = month.toString();
				revStr = rev.toString();
				
				dbList.add(monthStr);
				dbList.add(revStr);
		
				numOfData = dbList.size();
			}

		} catch (SQLException e) {
			System.out.println("Something went wrong accessing SQL.");
			e.printStackTrace();
		}
	}


	
//	private void accessDbRevenuePnl() {
//		try (Connection connection = DriverManager.getConnection(databaseURL);
//				Statement statement = connection.createStatement()) {
//			ResultSet revenueTableRs = statement.executeQuery(SQLAppointment.selectTotalRevenue());
//
//			while (revenueTableRs.next()) {
//				Object date = revenueTableRs.getObject("Month");
//				Object rev = revenueTableRs.getObject("totalRevenue");
//
//				// System.out.println(apptID + " " + artistID + " " + customerID + " " +
//				// apptDate + " " + hours);
//				revenueSb.append(date + ": " + rev + "\n");
//			}
//
//		} catch (SQLException e) {
//			System.out.println("Something went wrong accessing SQL.");
//			e.printStackTrace();
//		}
//	}

}