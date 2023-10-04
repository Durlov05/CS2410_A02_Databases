package tattooPayroll;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import java.awt.Color;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.SystemColor;
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

/**
 * This program displays all the appointments for the Tattoo Shop in the main
 * frame.
 * 
 * @author Syed Mujibur Rahman (Mujib) + Nikki Burr + Nikki Buzianis
 */
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
		JLabel artistName = new JLabel("Shop Schedule".toUpperCase());
		artistName.setBorder(new EmptyBorder(25, 0, 25, 0));
		artistName.setFont(new Font("Tahoma", Font.PLAIN, 38));
		artistName.setHorizontalAlignment(SwingConstants.CENTER);
		return artistName;
	}

	private JPanel createHeaderPanel() {
		JPanel headerPanel = new JPanel();
		headerPanel.setBorder(new EmptyBorder(10, 10, 0, 10));
		headerPanel.setMaximumSize(new Dimension(32767, 50));
		headerPanel.setPreferredSize(new Dimension(10, 50));
		headerPanel.setLayout(new GridLayout(1, 0, 0, 0));

		headerPanel.add(createHeaderLbls("Date"));
		headerPanel.add(createHeaderLbls("Artist"));
		headerPanel.add(createHeaderLbls("Customer"));
		headerPanel.add(createHeaderLbls("Hour Length"));

		return headerPanel;
	}

	private JLabel createHeaderLbls(String title) {
		JLabel header = new JLabel(title);
		header.setBorder(new EmptyBorder(5, 5, 5, 5));
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
		scrollPane.setBorder(new EmptyBorder(2, 10, 10, 10));
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

				SelectableApptLine apptLine = new SelectableApptLine(apptID, apptDate, artistID, customerID, hours,
						artName, custName);
				apptLine.fillOutAppointment();
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
						System.out.println("" + apptLine.getApptID());
						String name = "" + apptLine.getArtistID();
						System.out.println(apptLine.getArtistName());
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