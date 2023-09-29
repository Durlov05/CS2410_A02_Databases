package tattooPayroll;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JLabel;
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

public class CreateApptPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final String databaseURL = "jdbc:derby:FirstDatabase;create=true";
	private JTextField dateTextField;
	
	private static Vector<DropdownPersonItem> dropdownArtistList = new Vector<>();
	private static Vector<DropdownPersonItem> dropdownCustomerList = new Vector<>();
	
	private int artistId;
	private int customerId;

	/**
	 * Create the panel.
	 */
	public CreateApptPanel() {
		setBorder(new EmptyBorder(30, 70, 30, 70));
		setBackground(new Color(0, 191, 255));
		
		setLayout(new BorderLayout(0, 0));
		
		accessDB();
		
		JPanel NewApptTitlePanel = new JPanel();
		add(NewApptTitlePanel, BorderLayout.NORTH);
		
		JLabel NewApptLbl = createNewApptLbl();
		NewApptTitlePanel.add(NewApptLbl);
		
		JPanel clientArtistInfoPanel = createClientArtistInfoPanel();
		clientArtistInfoPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel clientNameLbl = createClientNameLbl();
		clientArtistInfoPanel.add(clientNameLbl);
		
		JComboBox<DropdownPersonItem> clientDropDown = createClientDropdownCB();
		clientArtistInfoPanel.add(clientDropDown);
		
		JLabel artistNameLbl = createArtistNameLbl();
		clientArtistInfoPanel.add(artistNameLbl);
		
		JComboBox<DropdownPersonItem> artistDropDown = createArtistDropdownCB();
		clientArtistInfoPanel.add(artistDropDown);
		
		JPanel apptInfoPanel = createApptInfoPanel();
		apptInfoPanel.setLayout(new GridLayout(2, 2, 0, 0));
		
		JLabel lblDate = createDateLbl();
		apptInfoPanel.add(lblDate);
		
		createDateTextField();
		apptInfoPanel.add(dateTextField);
		
		JLabel apptLengthLbl = createApptLengthLbl();
		apptInfoPanel.add(apptLengthLbl);
		
		JComboBox lengthOfApptDropDown = new JComboBox();
		apptInfoPanel.add(lengthOfApptDropDown);
		
		JPanel createApptPanel = new JPanel();
		add(createApptPanel, BorderLayout.SOUTH);
		
		JButton createApptBtn = createApptBtn();
		createApptPanel.add(createApptBtn);

	}

	private JComboBox<DropdownPersonItem> createArtistDropdownCB() {
		JComboBox<DropdownPersonItem> artistDropDown = new JComboBox<DropdownPersonItem>();
		for(DropdownPersonItem artist : dropdownArtistList){
			artistDropDown.addItem(artist);
		}

		artistDropDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					DropdownPersonItem option = (DropdownPersonItem) artistDropDown.getSelectedItem();
					artistId = option.getId();	
					System.out.println("artist id: " + artistId);
			}
		});
		return artistDropDown;
	}

	private JComboBox<DropdownPersonItem> createClientDropdownCB() {
		JComboBox<DropdownPersonItem> clientDropDown = new JComboBox<DropdownPersonItem>();
		for(DropdownPersonItem customer : dropdownCustomerList){
			clientDropDown.addItem(customer);
		}

		clientDropDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					DropdownPersonItem option = (DropdownPersonItem) clientDropDown.getSelectedItem();
					customerId = option.getId();
					System.out.println("cust id: " + customerId);
			}
		});
		return clientDropDown;
	}

	public JButton createApptBtn() {
		JButton createApptBtn = new JButton("Create");
		createApptBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 26));
		return createApptBtn;
	}

	public JLabel createApptLengthLbl() {
		JLabel apptLengthLbl = new JLabel("Length of Appointment:");
		apptLengthLbl.setBorder(new EmptyBorder(0, 150, 0, 0));
		apptLengthLbl.setFont(new Font("Lucida Grande", Font.PLAIN, 21));
		return apptLengthLbl;
	}

	public void createDateTextField() {
		dateTextField = new JTextField();

		dateTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 21));
		dateTextField.setText("MM/DD/YY");
		dateTextField.setColumns(10);
	}

	public JLabel createDateLbl() {
		JLabel lblDate = new JLabel("Date:");
		lblDate.setBorder(new EmptyBorder(0, 250, 0, 0));
		lblDate.setFont(new Font("Lucida Grande", Font.PLAIN, 21));
		return lblDate;
	}

	public JPanel createApptInfoPanel() {
		JPanel apptInfoPanel = new JPanel();
		apptInfoPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		add(apptInfoPanel, BorderLayout.CENTER);
		return apptInfoPanel;
	}

	public JLabel createArtistNameLbl() {
		JLabel artistNameLbl = new JLabel("Choose Artist:");
		artistNameLbl.setFont(new Font("Lucida Grande", Font.PLAIN, 21));
		return artistNameLbl;
	}

	public JLabel createClientNameLbl() {
		JLabel clientNameLbl = new JLabel("Choose Client:");
		clientNameLbl.setFont(new Font("Lucida Grande", Font.PLAIN, 21));
		return clientNameLbl;
	}

	public JPanel createClientArtistInfoPanel() {
		JPanel clientArtistInfoPanel = new JPanel();
		clientArtistInfoPanel.setBorder(new EmptyBorder(0, 100, 0, 0));
		add(clientArtistInfoPanel, BorderLayout.WEST);
		return clientArtistInfoPanel;
	}

	public JLabel createNewApptLbl() {
		JLabel NewApptLbl = new JLabel("Enter New Appointment Information:");
		NewApptLbl.setFont(new Font("Lucida Grande", Font.PLAIN, 35));
		return NewApptLbl;
	}

	private void accessDB() {
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

