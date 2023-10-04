package tattooPayroll;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.border.EmptyBorder;
import java.awt.Insets;

import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.Dimension;


/**
 * ??? A panel where the user can add a new appointment to the system, including
 * the artist, customer name, appointment date, and length in hours.
 * 
 * @author Syed Mujibur Rahman (Mujib) + Nikki Burr + Nikki Buzianis
 */
public class CreateApptPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final String databaseURL = "jdbc:derby:FirstDatabase;create=true";

	private static Vector<DropdownPersonItem> dropdownArtistList = new Vector<>();
	private static Vector<DropdownPersonItem> dropdownCustomerList = new Vector<>();

	private JComboBox<DropdownPersonItem> clientDropDown;
	private JComboBox<DropdownPersonItem> artistDropDown;
	private JComboBox<Integer> lengthOfApptDropDown;
	private JTextField dateTextField;
	private JPanel clientDeets;
	private JPanel artistDeets;
	private JPanel dateDeets;
	private JPanel lengthDeets;

	/**
	 * Create the panel by calling Appointment, Artist and Customer information.
	 */
	public CreateApptPanel() {
		setBorder(new EmptyBorder(30, 70, 30, 70));
		setLayout(new BorderLayout(0, 0));

		getDataFromDB();

		// NORTH - title
		JPanel NewApptTitlePanel = createTitlePanel();
		add(NewApptTitlePanel, BorderLayout.NORTH);

		// CENTER - appt details
		JPanel enterApptDetailsPanel = createApptDetailsPanel();
		add(enterApptDetailsPanel, BorderLayout.CENTER);

		// SOUTH = create button
		JPanel createApptPanel = createCreateApptBtnPanel();
		add(createApptPanel, BorderLayout.SOUTH);
	}

	private JPanel createApptDetailsPanel() {
		JPanel enterApptDetailsPanel = new JPanel();
		enterApptDetailsPanel.setBorder(new EmptyBorder(50, 100, 20, 50));
		enterApptDetailsPanel.setLayout(new GridLayout(0, 2, 10, 10));

		JPanel clientDeets = createClientDeetsPanel();
		enterApptDetailsPanel.add(clientDeets);

		JPanel dateDeets = createDateDeetsPanel();
		enterApptDetailsPanel.add(dateDeets);
		
		JPanel artistDeets = createArtistDeetsPanel();
		enterApptDetailsPanel.add(artistDeets);

		JPanel lengthDeets = createLengthDeetsPanel();
		enterApptDetailsPanel.add(lengthDeets);

		return enterApptDetailsPanel;
	}

	private JPanel createLengthDeetsPanel() {
		lengthDeets = new JPanel();
		lengthDeets.setBorder(new EmptyBorder(10, 0, 10, 0));
		lengthDeets.setLayout(new BoxLayout(lengthDeets, BoxLayout.PAGE_AXIS));
		JLabel apptLengthLbl = createApptLengthLbl();
		lengthDeets.add(apptLengthLbl);

		lengthOfApptDropDown = createLengthOfApptDropdownCB();
		lengthDeets.add(lengthOfApptDropDown);
		return lengthDeets;
	}

	private JPanel createDateDeetsPanel() {
		dateDeets = new JPanel();
		dateDeets.setBorder(new EmptyBorder(10, 0, 10, 0));
		dateDeets.setLayout(new BoxLayout(dateDeets, BoxLayout.PAGE_AXIS));
		JLabel lblDate = createDateLbl();
		dateDeets.add(lblDate);

		dateTextField = createDateTextField();
		dateDeets.add(dateTextField);
		return dateDeets;
	}

	private JPanel createArtistDeetsPanel() {
		artistDeets = new JPanel();
		artistDeets.setBorder(new EmptyBorder(10, 0, 10, 0));
		artistDeets.setLayout(new BoxLayout(artistDeets, BoxLayout.PAGE_AXIS));
		JLabel artistNameLbl = createArtistNameLbl();
		artistDeets.add(artistNameLbl);

		artistDropDown = createArtistDropdownCB();
		artistDeets.add(artistDropDown);
		return artistDeets;
	}

	private JPanel createClientDeetsPanel() {
		clientDeets = new JPanel();
		clientDeets.setBorder(new EmptyBorder(10, 0, 10, 0));
		clientDeets.setLayout(new BoxLayout(clientDeets, BoxLayout.PAGE_AXIS));

		JLabel clientNameLbl = createClientNameLbl();
		clientDeets.add(clientNameLbl);

		clientDropDown = createClientDropdownCB();
		clientDeets.add(clientDropDown);

		return clientDeets;
	}

	private JPanel createCreateApptBtnPanel() {
		JPanel createApptPanel = new JPanel();
		createApptPanel.setBorder(new EmptyBorder(20, 0, 10, 0));
		
		JButton createApptBtn = createApptBtn();
		createApptPanel.add(createApptBtn);
		return createApptPanel;
	}

	private JPanel createTitlePanel() {
		JPanel NewApptTitlePanel = new JPanel();
		JLabel NewApptLbl = createTitleLbl();
		NewApptTitlePanel.add(NewApptLbl);
		return NewApptTitlePanel;
	}

	private JLabel createTitleLbl() {
		JLabel NewApptLbl = createNewApptLbl();
		return NewApptLbl;
	}

	private JComboBox<Integer> createLengthOfApptDropdownCB() {
		Integer[] hours = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
		JComboBox<Integer> lengthOfApptDropDown = new JComboBox<>();
		lengthOfApptDropDown.setPreferredSize(new Dimension(30, 50));
		lengthOfApptDropDown.setMaximumSize(new Dimension(300, 50));
		lengthOfApptDropDown.setAlignmentX(Component.LEFT_ALIGNMENT);
		lengthOfApptDropDown.setFont(new Font("Tahoma", Font.PLAIN, 30));
		for (Integer i : hours) {
			lengthOfApptDropDown.addItem(i);
		}

		return lengthOfApptDropDown;
	}

	private JComboBox<DropdownPersonItem> createArtistDropdownCB() {
		JComboBox<DropdownPersonItem> artistDropDown = new JComboBox<DropdownPersonItem>();
		artistDropDown.setPreferredSize(new Dimension(30, 50));
		artistDropDown.setMaximumSize(new Dimension(300, 50));
		artistDropDown.setAlignmentX(Component.LEFT_ALIGNMENT);
		artistDropDown.setFont(new Font("Dialog", Font.PLAIN, 30));
		for (DropdownPersonItem artist : dropdownArtistList) {
			artistDropDown.addItem(artist);
		}
		return artistDropDown;
	}

	private JComboBox<DropdownPersonItem> createClientDropdownCB() {
		JComboBox<DropdownPersonItem> clientDropDown = new JComboBox<DropdownPersonItem>();
		clientDropDown.setPreferredSize(new Dimension(30, 50));
		clientDropDown.setAlignmentX(Component.LEFT_ALIGNMENT);
		clientDropDown.setMaximumSize(new Dimension(300, 50));
		clientDropDown.setFont(new Font("Dialog", Font.PLAIN, 30));
		for (DropdownPersonItem customer : dropdownCustomerList) {
			clientDropDown.addItem(customer);
		}
		return clientDropDown;
	}

	private JButton createApptBtn() {
		JButton createApptBtn = new JButton("Create");
		createApptBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// get artist id
				DropdownPersonItem artist = (DropdownPersonItem) artistDropDown.getSelectedItem();
				int artistID = artist.getId();
				// get cust id
				DropdownPersonItem client = (DropdownPersonItem) clientDropDown.getSelectedItem();
				int custID = client.getId();
				// get date
				String date = dateTextField.getText();
				// get hours
				int apptHours = (int) lengthOfApptDropDown.getSelectedItem();
				// send to sql
				try (Connection connection = DriverManager.getConnection(databaseURL);
						Statement statement = connection.createStatement()) {
					statement.execute(SQLAppointment.addAppt(artistID, custID, date, apptHours));
				} catch (SQLException el) {
					System.out.println("Something went wrong inserting appointment with SQL.");
					el.printStackTrace();
				}
				// dialog box succesfully created
				JOptionPane.showMessageDialog(null, "Appointment successfully created!");
			}
		});
		createApptBtn.setMargin(new Insets(10, 10, 10, 10));
		createApptBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 32));
		return createApptBtn;
	}

	private JLabel createApptLengthLbl() {
		JLabel apptLengthLbl = new JLabel("Hour Length:");
		apptLengthLbl.setBorder(new EmptyBorder(10, 0, 10, 0));
		apptLengthLbl.setFont(new Font("Dialog", Font.PLAIN, 30));
		return apptLengthLbl;
	}

	private JTextField createDateTextField() {
		JTextField date = new JTextField();
		date.setPreferredSize(new Dimension(7, 50));
		date.setMaximumSize(new Dimension(300, 50));
		date.setAlignmentX(Component.LEFT_ALIGNMENT);
		date.setFont(new Font("Dialog", Font.PLAIN, 30));
		date.setText("YYYY-MM-DD");
		date.setColumns(8);
		return date;
	}

	private JLabel createDateLbl() {
		JLabel lblDate = new JLabel("Date:");
		lblDate.setBorder(new EmptyBorder(10, 0, 10, 0));
		lblDate.setAlignmentY(Component.TOP_ALIGNMENT);
		lblDate.setFont(new Font("Dialog", Font.PLAIN, 30));
		return lblDate;
	}

	private JLabel createArtistNameLbl() {
		JLabel artistNameLbl = new JLabel("Choose Artist:");
		artistNameLbl.setBorder(new EmptyBorder(10, 0, 10, 0));
		artistNameLbl.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		return artistNameLbl;
	}

	private JLabel createClientNameLbl() {
		JLabel clientNameLbl = new JLabel("Choose Client:");
		clientNameLbl.setBorder(new EmptyBorder(10, 0, 10, 0));
		clientNameLbl.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		return clientNameLbl;
	}

	private JLabel createNewApptLbl() {
		JLabel NewApptLbl = new JLabel("Enter New Appointment Information:");
		NewApptLbl.setFont(new Font("Lucida Grande", Font.PLAIN, 35));
		return NewApptLbl;
	}

	private void getDataFromDB() {
		try (Connection connection = DriverManager.getConnection(databaseURL);
				Statement statement = connection.createStatement()) {
			ResultSet customerTableRs = statement.executeQuery(SQLCustomer.selectAll);
			while (customerTableRs.next()) {
				Object id = customerTableRs.getObject("CustomerId");
				Object fname = customerTableRs.getObject("FirstName");
				Object lname = customerTableRs.getObject("LastName");
				String name = fname.toString() + " " + lname.toString();
				DropdownPersonItem item = new DropdownPersonItem(id, name);
				dropdownCustomerList.add(item);
			}

			ResultSet aristTableRs = statement.executeQuery(SQLArtist.selectAll);// (selectStuff);
			while (aristTableRs.next()) {
				Object id = aristTableRs.getObject("ARTISTID");
				Object name = aristTableRs.getObject("ARTISTNAME");
				DropdownPersonItem item = new DropdownPersonItem(id, name);
				dropdownArtistList.add(item);
			}

		} catch (SQLException e) {
			System.out.println("Something went wrong accessing SQL.");
			e.printStackTrace();
		}
	}
}
