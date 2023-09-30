package tattooPayroll;
/**
 * This is the main file that creates program. It creates a frame and populates the drop downs.
 * @author Syed Mujibur Rahman (Mujib) + Nikki Burr + Nikki Buzianis
 */

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class TattooPayrollApp extends JFrame {
	private static final String databaseURL = "jdbc:derby:FirstDatabase;create=true";

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private CardLayout cl = new CardLayout();
	private JPanel cardContainer;
	private TattooPayrollApp frame = this;

	private static Vector<DropdownItem> dropdownArtistList = new Vector<>();


	final static String APPTPANEL = "View All Appointments";
	final static String CREATEPANEL = "Create appointment";
	final static String ARTISTREVENUE = "Display Artist Revenue";
	final static String EDITAPPT = "Edit appointment";
	final static String SHOPREVENUE = "All Shop Revenue";

	/**
	 * Here we are loading the data into the database and launching the application. 
	 */
	public static void main(String[] args) {
		try (Connection connection = DriverManager.getConnection(databaseURL);
				Statement statement = connection.createStatement()) {
			// Should we remove these commented out lines? At least include a reason for having them.
//			statement.execute(SQLCustomer.dropTable);  
//			statement.execute(SQLCustomer.createTable);
//			statement.execute(SQLCustomer.insertData);
//			ResultSet resultSet = statement.executeQuery(SQLCustomer.selectAll);//(selectStuff);
//			printTableData(resultSet);
//			System.out.println("");

			// statement.execute(SQLArtist.dropTable);
			// statement.execute(SQLArtist.createTable);
			// statement.execute(SQLArtist.insertData);
			ResultSet aristTableRs = statement.executeQuery(SQLArtist.selectAll);// (selectStuff);
			while (aristTableRs.next()) {
				Object id = aristTableRs.getObject("ARTISTID");
				Object name = aristTableRs.getObject("ARTISTNAME");
				DropdownItem item = new DropdownPersonItem(id, name);
				dropdownArtistList.add(item);

			}
			System.out.println("");

			statement.execute(SQLAppointment.dropTable);
			statement.execute(SQLAppointment.createTable);
			statement.execute(SQLAppointment.insertData);
//			ResultSet resultSet3 = statement.executeQuery(SQLAppointment.selectAll);//(selectStuff);
//			printTableData(resultSet3);
//			System.out.println("");

		} catch (SQLException e) {
			System.out.println("Something went wrong accessing SQL.");
			e.printStackTrace();
		}
		System.out.println("\ndone.");

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TattooPayrollApp frame = new TattooPayrollApp();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the Tattoo Payroll frame.
	 */
	public TattooPayrollApp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 1000);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		createTitlePnl();

		createControlPnl();

		// TODO SHOULD THIS BE REFACTORED?
		// displays and houses card panels
		cardContainer = new JPanel();
		contentPane.add(cardContainer, BorderLayout.CENTER);
		cardContainer.setLayout(cl);

		JPanel ApptDisplayPanel = new ApptDisplayPanel(this);
		cardContainer.add(ApptDisplayPanel, APPTPANEL);

		JPanel ShopRevenueDisplayPanel = new ShopRevenueDisplayPanel();
		cardContainer.add(ShopRevenueDisplayPanel, SHOPREVENUE);

		JPanel CreateApptPanel = new CreateApptPanel();
		cardContainer.add(CreateApptPanel, CREATEPANEL);
	}
	
	/** ???
	 * The method below adds new cards into the Tattoo parlor frame that allow the user 
	 * to step into the data.
	 * @param card - the display which leads to the next screen.
	 * @param name - name of the card that needs to be called upon click.
	 */
	public void addNewCard(JPanel card, String name) {
		cardContainer.add(card, name);
	}
	
	/** ??
	 * This method switches to the card that was clicked and brings it up for display.
	 * @param name - name of the card that is called upon clicking.
	 */
	public void switchToCard(String name) {
		cl.show(cardContainer, name);
	}

	private void createControlPnl() {
		JPanel controlPnl = new JPanel();
		controlPnl.setLayout(new GridLayout(0, 1, 0, 0));
		contentPane.add(controlPnl, BorderLayout.WEST);

		JButton scheduleApptBtn = createScheduleBtn();
		controlPnl.add(scheduleApptBtn);

		JPanel dropBoxPnl = createDropBoxPnl();
		controlPnl.add(dropBoxPnl);
	}

	private JPanel createDropBoxPnl() {
		JPanel dropBoxPnl = new JPanel();
		dropBoxPnl.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel viewApptPnl = createViewApptDropboxPnl();
		dropBoxPnl.add(viewApptPnl);

		JPanel revenuePnl = createViewRevenueDropboxPnl();
		dropBoxPnl.add(revenuePnl);

		return dropBoxPnl;
	}

	private JPanel createViewRevenueDropboxPnl() {
		JPanel revenuePnl = new JPanel();
		revenuePnl.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel viewRevenueLbl = createViewRevenueLbl();
		revenuePnl.add(viewRevenueLbl);

		JComboBox<DropdownItem> revenueDropDown = createRevDropdownCB();
		revenuePnl.add(revenueDropDown);

		return revenuePnl;
	}

	private JComboBox<DropdownItem> createRevDropdownCB() {
		JComboBox<DropdownItem> revenueDropDown = new JComboBox<DropdownItem>();
		for (DropdownItem op : dropdownArtistList) {
			revenueDropDown.addItem(op);
		}
		revenueDropDown.addItem(new DropdownItem("Shop Revenue"));

		revenueDropDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (revenueDropDown.getSelectedItem() instanceof DropdownPersonItem) {
					DropdownPersonItem option = (DropdownPersonItem) revenueDropDown.getSelectedItem();
					int id = option.getId();
					String name = option.getName();
					String cardName = name + "rev";

					cardContainer.add(new ArtistRevenueDisplayPanel(id, name), cardName);
					cl.show(cardContainer, cardName);
				} else {
					System.out.println("shop revenue");
					cl.show(cardContainer, SHOPREVENUE);
				}
			}
		});
		return revenueDropDown;
	}

	private JLabel createViewRevenueLbl() {
		JLabel viewRevenueLbl = new JLabel("View Revenue");
		viewRevenueLbl.setFont(new Font("Lucida Grande", Font.PLAIN, 21));
		return viewRevenueLbl;
	}

	private JPanel createViewApptDropboxPnl() {
		JPanel viewApptPnl = new JPanel();
		viewApptPnl.setLayout(new GridLayout(2, 1, 0, 0));

		JLabel viewApptLbl = createViewApptLbl();
		viewApptPnl.add(viewApptLbl);

		JComboBox<DropdownItem> apptDropDown = createAppDropdownCB();
		viewApptPnl.add(apptDropDown);

		return viewApptPnl;
	}

	private JComboBox<DropdownItem> createAppDropdownCB() {
		JComboBox<DropdownItem> apptDropDown = new JComboBox<DropdownItem>();
		for (DropdownItem op : dropdownArtistList) {
			apptDropDown.addItem(op);
		}
		apptDropDown.addItem(new DropdownItem("All Appointments"));

		apptDropDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (apptDropDown.getSelectedItem() instanceof DropdownPersonItem) {
					DropdownPersonItem option = (DropdownPersonItem) apptDropDown.getSelectedItem();
					int id = option.getId();
					String name = option.getName();
					String cardName = name + "appt";

					cardContainer.add(new ArtistApptDisplayPanel(id, name, frame), cardName);
					cl.show(cardContainer, cardName);
				} else {
					cl.show(cardContainer, APPTPANEL);
					System.out.println("all appointments");
				}
			}
		});
		return apptDropDown;
	}

	private JLabel createViewApptLbl() {
		JLabel viewApptLbl = new JLabel("View Appointments");
		viewApptLbl.setFont(new Font("Lucida Grande", Font.PLAIN, 21));
		return viewApptLbl;
	}

	private void createTitlePnl() {
		JPanel titlePnl = new JPanel();
		contentPane.add(titlePnl, BorderLayout.NORTH);

		JLabel titleLbl = createTitleLbl();
		titlePnl.add(titleLbl);
	}

	private JButton createScheduleBtn() {
		JButton scheduleApptBtn = new JButton("Schedule Appt.");
		scheduleApptBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 21));
		scheduleApptBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cl.show(cardContainer, CREATEPANEL);
			}
		});
		return scheduleApptBtn;
	}

	private JLabel createTitleLbl() {
		JLabel titleLbl = new JLabel("Tattoo Payroll App");
		titleLbl.setFont(new Font("Lucida Grande", Font.PLAIN, 69));
		return titleLbl;
	}
}