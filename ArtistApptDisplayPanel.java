package tattooPayroll;

/**
* This program displays all the appointments for a specific Artist in the Tattoo Shop.
* @author Syed Mujibur Rahman (Mujib) + Nikki Burr + Nikki Buzianis
*/

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.SystemColor;

public class ArtistApptDisplayPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private int id; // use this to run queries?
	private String name;
	private TattooPayrollApp frame;
	private JScrollPane scrollPane;
	private JPanel apptList;

	private static final String databaseURL = "jdbc:derby:FirstDatabase;create=true";

	/**
	 * Create the panel for the specific Artist.
	 */
	public ArtistApptDisplayPanel(int id, String name, TattooPayrollApp frame) {
		this.frame = frame;
		this.id = id;
		this.name = name;

		setBackground(new Color(255, 128, 64));
		setBorder(new EmptyBorder(10, 25, 40, 25));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		apptList = createApptListPanel();
		accessDbApptPnl();

		JPanel titlePanel = createTitlePanel();
		add(titlePanel);

		scrollPane = createScrollPane();
		add(scrollPane);
	}

	private JScrollPane createScrollPane() {
		JScrollPane scrollPane = new JScrollPane(apptList);
		return scrollPane;
	}

	private JPanel createTitlePanel() {
		JPanel titlePanel = new JPanel();
		titlePanel.setBorder(new EmptyBorder(0, 10, 0, 10));
		titlePanel.setMaximumSize(new Dimension(32767, 45));
		titlePanel.setPreferredSize(new Dimension(10, 50));
		titlePanel.setLayout(new GridLayout(1, 0, 0, 0));

		titlePanel.add(createHeaderLbls("Date"));
		titlePanel.add(createHeaderLbls("Customer"));
		titlePanel.add(createHeaderLbls("Appointment Length"));

		return titlePanel;
	}

	private JLabel createHeaderLbls(String title) {
		JLabel header = new JLabel(title);
		header.setFont(new Font("Tahoma", Font.PLAIN, 30));
		return header;
	}

	private JPanel createApptListPanel() {
		JPanel apptList = new JPanel();
		apptList.setBackground(SystemColor.activeCaption);
		apptList.setLayout(new BoxLayout(apptList, BoxLayout.PAGE_AXIS));
		return apptList;
	}

	private void accessDbApptPnl() {

		try (Connection connection = DriverManager.getConnection(databaseURL);
				Statement statement = connection.createStatement()) {
			ResultSet artistApptRs = statement.executeQuery(SQLAppointment.selectArtistAppt(id));

			while (artistApptRs.next()) {
				Object apptID = artistApptRs.getObject("ApptID");
				Object artistID = artistApptRs.getObject("ArtistID");
				Object customerID = artistApptRs.getObject("CustomerID");
				Object apptDate = artistApptRs.getObject("ApptDate");
				Object hours = artistApptRs.getObject("Hours");
				//Object artName = artistApptRs.getObject("Artist_Name");
				Object custName = artistApptRs.getObject("Customer_Name");

				//SelectableApptLine apptLine = new SelectableApptLine(apptDate, artistID, customerID, hours);
				//SelectableApptLine apptLine = new SelectableApptLine(apptDate, artistID, customerID, hours, artName, custName);
				SelectableApptLine apptLine = new SelectableApptLine(apptDate, artistID, customerID, hours, custName);
				apptLine.displayArtistAppts();
				apptLine.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						apptLine.setBackground(Color.GRAY);
					}

					@Override
					public void mouseExited(MouseEvent e) {
						apptLine.setBackground(Color.WHITE);
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						//int id = Integer.parseInt(apptID.toString());
						//int id = apptLine.getApptID();
						JPanel editApt = new EditApptPanel(apptLine);
						String name = "" + apptLine.getArtistID();
						frame.addNewCard(editApt, name);
						frame.switchToCard(name);
					}
				});
				apptList.add(apptLine);
			}

		} catch (SQLException e) {
			System.out.println("Something went wrong accessing SQL.");
			e.printStackTrace();
		}
	}

}
