package tattooPayroll;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.Dimension;

public class ApptDisplayPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final String databaseURL = "jdbc:derby:FirstDatabase;create=true";

	private TattooPayrollApp frame;
	private JScrollPane scrollPane;
	private JPanel apptList;

	/**
	 * Create the panel.
	 */
	public ApptDisplayPanel(TattooPayrollApp frame) {
		this.frame = frame;
		setBorder(new EmptyBorder(10, 25, 40, 25));
		setBackground(new Color(199, 21, 133));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		apptList = createApptListPanel();
		accessDbApptPnl();

		JPanel titlePanel = createTitlePanel();
		add(titlePanel);

		scrollPane = createScrollPane();
		add(scrollPane);

	}

	private JPanel createTitlePanel() {
		JPanel titlePanel = new JPanel();
		titlePanel.setBorder(new EmptyBorder(0, 10, 0, 10));
		titlePanel.setMaximumSize(new Dimension(32767, 45));
		titlePanel.setPreferredSize(new Dimension(10, 50));
		titlePanel.setLayout(new GridLayout(1, 0, 0, 0));

		titlePanel.add(createHeaderLbls("Date"));
		titlePanel.add(createHeaderLbls("Artist"));
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
		apptList.setLayout(new BoxLayout(apptList, BoxLayout.PAGE_AXIS));
		return apptList;
	}

	private JScrollPane createScrollPane() {
		JScrollPane scrollPane = new JScrollPane(apptList);
		return scrollPane;
	}

	private void accessDbApptPnl() {

		try (Connection connection = DriverManager.getConnection(databaseURL);
				Statement statement = connection.createStatement()) {
			ResultSet apptTableRs = statement.executeQuery(SQLAppointment.selectAllInfo);

			while (apptTableRs.next()) {
				Object apptID = apptTableRs.getObject("ApptID");
				Object artistID = apptTableRs.getObject("ArtistID");
				Object customerID = apptTableRs.getObject("CustomerID");
				Object apptDate = apptTableRs.getObject("ApptDate");
				Object hours = apptTableRs.getObject("Hours");
				Object artName = apptTableRs.getObject("Artist_Name");
				Object custName = apptTableRs.getObject("Customer_Name");
				
				//SelectableApptLine apptLine = new SelectableApptLine(apptDate, artistID, customerID, hours);
				SelectableApptLine apptLine = new SelectableApptLine(apptDate, artistID, customerID, hours, artName, custName);
				apptLine.displayAllAppts();
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
//						int id = Integer.parseInt(apptID.toString());
//						JPanel editApt = new EditApptPanel(id);
//						String name = apptID.toString();
//						frame.addNewCard(editApt, name);
//						frame.switchToCard(name);
						
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