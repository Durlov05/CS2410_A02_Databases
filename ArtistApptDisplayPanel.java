package tattooPayroll;

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
import javax.swing.SwingConstants;

/**
 * This program displays all the appointments for a specific Artist in the
 * Tattoo Shop.
 * 
 * @author Syed Mujibur Rahman (Mujib) + Nikki Burr + Nikki Buzianis
 */
public class ArtistApptDisplayPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private int id;
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

		setBorder(new EmptyBorder(25, 45, 30, 25));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		apptList = createApptListPanel();
		accessDbApptPnl();

		JPanel titlePanel = createTitlePanel();
		add(titlePanel);

		JPanel headerPanel = createHeaderPanel();
		add(headerPanel);

		scrollPane = createScrollPane();
		add(scrollPane);
	}

	private JPanel createTitlePanel() {
		JPanel titlePanel = new JPanel();
		titlePanel.setBorder(new EmptyBorder(0, 10, 0, 10));
		titlePanel.setMaximumSize(new Dimension(32767, 45));
		titlePanel.setPreferredSize(new Dimension(10, 50));
		titlePanel.setLayout(new GridLayout(1, 0, 0, 0));

		JLabel artistName = createArtistNameLbl();
		titlePanel.add(artistName);

		return titlePanel;
	}

	private JLabel createArtistNameLbl() {
		JLabel artistName = new JLabel((name + "'s Schedule").toUpperCase());
		artistName.setBorder(new EmptyBorder(25, 0, 25, 0));
		artistName.setFont(new Font("Tahoma", Font.PLAIN, 38));
		artistName.setHorizontalAlignment(SwingConstants.CENTER);
		return artistName;
	}

	private JScrollPane createScrollPane() {
		JScrollPane scrollPane = new JScrollPane(apptList);
		scrollPane.setBorder(new EmptyBorder(2, 10, 10, 10));
		return scrollPane;
	}

	private JPanel createHeaderPanel() {
		JPanel header = new JPanel();
		header.setBorder(new EmptyBorder(10, 10, 0, 10));
		header.setMaximumSize(new Dimension(32767, 50));
		header.setPreferredSize(new Dimension(10, 50));
		header.setLayout(new GridLayout(1, 0, 0, 0));

		header.add(createHeaderLbls("Date"));
		header.add(createHeaderLbls("Customer"));
		header.add(createHeaderLbls("Hour Length"));

		return header;
	}

	private JLabel createHeaderLbls(String title) {
		JLabel header = new JLabel(title);
		header.setBorder(new EmptyBorder(5, 5, 5, 5));
		header.setFont(new Font("Tahoma", Font.PLAIN, 30));
		return header;
	}

	private JPanel createApptListPanel() {
		JPanel apptList = new JPanel();
		apptList.setBackground(SystemColor.menu);
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
				Object custName = artistApptRs.getObject("Customer_Name");

				SelectableApptLine apptLine = new SelectableApptLine(apptID, apptDate, artistID, customerID, hours,
						custName);

				apptLine.displayArtistAppts();
				apptLine.setBackground(SystemColor.control);
				apptLine.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						apptLine.setBackground(SystemColor.controlHighlight);
					}

					@Override
					public void mouseExited(MouseEvent e) {
						apptLine.setBackground(SystemColor.control);
					}

					@Override
					public void mouseReleased(MouseEvent e) {
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