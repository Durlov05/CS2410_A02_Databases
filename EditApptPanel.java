package tattooPayroll;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

/**
 * Creates a panel for the user to edit an already existing appointment.
 * 
 * @author Syed Mujibur Rahman (Mujib) + Nikki Burr + Nikki Buzianis
 */
public class EditApptPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final String databaseURL = "jdbc:derby:FirstDatabase;create=true";

	private static Vector<DropdownPersonItem> dropdownArtistList = new Vector<>();
	private static Vector<DropdownPersonItem> dropdownCustomerList = new Vector<>();

	private SelectableApptLine appt;

	private JComboBox<DropdownPersonItem> clientDropDown;
	private JComboBox<DropdownPersonItem> artistDropDown;
	private JComboBox<Integer> lengthOfApptDropDown;
	private JTextField dateTextField;
	private JPanel clientDeets;
	private JPanel artistDeets;
	private JPanel dateDeets;
	private JPanel lengthDeets;

	/**
	 * Create the panel.
	 */
	public EditApptPanel(SelectableApptLine appt) {
		this.appt = appt;

		setBorder(new EmptyBorder(30, 70, 30, 70));
		setLayout(new BorderLayout(0, 0));

		getDataFromDB();

		// NORTH - title
		JPanel NewApptTitlePanel = createTitlePanel();
		add(NewApptTitlePanel, BorderLayout.NORTH);

		// CENTER - appt details
		JPanel enterApptDetailsPanel = createApptDetailsPanel();
		add(enterApptDetailsPanel, BorderLayout.CENTER);

		// SOUTH = update and delete buttons
		JPanel buttonPanel = createButtonPanel();
		add(buttonPanel, BorderLayout.SOUTH);
	}

	private JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnUpdate = createConfirmBtn();
		buttonPanel.add(btnUpdate);

		buttonPanel.add(Box.createRigidArea(new Dimension(20, 1)));

		JButton btnDelete = createDeleteBtn();
		buttonPanel.add(btnDelete);

		return buttonPanel;
	}

	private JButton createDeleteBtn() {
		JButton btnDelete = new JButton("Delete Appointment");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// send to sql
				try (Connection connection = DriverManager.getConnection(databaseURL);
						Statement statement = connection.createStatement()) {
					statement.execute(SQLAppointment.deleteAppt(appt.getApptID()));
				} catch (SQLException el) {
					System.out.println("Something went wrong deleting the appointment with SQL.");
					el.printStackTrace();
				}
				// dialog box successfully deleted
				JOptionPane.showMessageDialog(null, "Appointment successfully deleted!");
			}
		});
		btnDelete.setMargin(new Insets(10, 10, 10, 10));
		btnDelete.setFont(new Font("Lucida Grande", Font.PLAIN, 32));

		return btnDelete;
	}

	private JButton createConfirmBtn() {
		JButton btnUpdate = new JButton("Confirm Changes");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// get apptID
				int apptID = appt.getApptID();
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
					statement.execute(SQLAppointment.updateAppt(apptID, artistID, custID, date, apptHours));
				} catch (SQLException el) {
					System.out.println("Something went wrong updating the appointment with SQL.");
					el.printStackTrace();
				}
				// dialog box successfully updated
				JOptionPane.showMessageDialog(null, "Appointment successfully updated!");
			}
		});
		btnUpdate.setMargin(new Insets(10, 10, 10, 10));
		btnUpdate.setFont(new Font("Lucida Grande", Font.PLAIN, 32));
		return btnUpdate;
	}

	private JComboBox<DropdownPersonItem> createArtistDropdownCB() {
		JComboBox<DropdownPersonItem> artistDropDown = new JComboBox<DropdownPersonItem>();
		artistDropDown.setPreferredSize(new Dimension(30, 50));
		artistDropDown.setMaximumSize(new Dimension(300, 50));
		artistDropDown.setAlignmentX(Component.LEFT_ALIGNMENT);
		artistDropDown.setFont(new Font("Dialog", Font.PLAIN, 30));

		DropdownPersonItem defaultVal = null;
		for (DropdownPersonItem artist : dropdownArtistList) {
			artistDropDown.addItem(artist);
			if (artist.getId() == appt.getArtistID())
				defaultVal = artist;
		}
		artistDropDown.setSelectedItem(defaultVal);
		return artistDropDown;
	}

	private JComboBox<DropdownPersonItem> createClientDropdownCB() {
		JComboBox<DropdownPersonItem> clientDropDown = new JComboBox<DropdownPersonItem>();
		clientDropDown.setPreferredSize(new Dimension(30, 50));
		clientDropDown.setAlignmentX(Component.LEFT_ALIGNMENT);
		clientDropDown.setMaximumSize(new Dimension(300, 50));
		clientDropDown.setFont(new Font("Dialog", Font.PLAIN, 30));

		DropdownPersonItem defaultVal = null;
		for (DropdownPersonItem customer : dropdownCustomerList) {
			clientDropDown.addItem(customer);
			if (customer.getId() == appt.getCustomerID())
				defaultVal = customer;
		}
		clientDropDown.setSelectedItem(defaultVal);
		return clientDropDown;
	}

	private JComboBox<Integer> createLengthOfApptDropdownCB() {
		Integer[] hours = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
		JComboBox<Integer> lengthOfApptDropDown = new JComboBox<>();
		lengthOfApptDropDown.setPreferredSize(new Dimension(30, 50));
		lengthOfApptDropDown.setMaximumSize(new Dimension(300, 50));
		lengthOfApptDropDown.setAlignmentX(Component.LEFT_ALIGNMENT);
		lengthOfApptDropDown.setFont(new Font("Tahoma", Font.PLAIN, 30));

		int defaultVal = 0;
		for (Integer i : hours) {
			lengthOfApptDropDown.addItem(i);
			if (i == appt.getApptLength())
				defaultVal = i;
		}
		lengthOfApptDropDown.setSelectedItem(defaultVal);

		return lengthOfApptDropDown;
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
		date.setText(appt.getDate());
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

	private JPanel createTitlePanel() {
		JPanel NewApptTitlePanel = new JPanel();
		JLabel NewApptLbl = createTitleLbl();
		NewApptTitlePanel.add(NewApptLbl);
		return NewApptTitlePanel;
	}

	private JLabel createTitleLbl() {
		JLabel NewApptLbl = new JLabel("Update Appointment Information:");
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