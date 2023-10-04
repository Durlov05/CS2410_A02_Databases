package tattooPayroll;

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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.FlowLayout;

/**
 * A payroll application for a tattoo shop that displays revenue by date and by
 * artist based on artist appointments and their hourly wages. It creates a
 * frame and populates the drop downs.
 * 
 * @author Syed Mujibur Rahman (Mujib) + Nikki Burr + Nikki Buzianis
 */
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
	 * Launch the application.
	 */
	public static void main(String[] args) {

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

	private static void accessDB() {
		try (Connection connection = DriverManager.getConnection(databaseURL);
				Statement statement = connection.createStatement()) {

//			statement.execute(SQLCustomer.dropTable);
//			statement.execute(SQLCustomer.createTable);
//			statement.execute(SQLCustomer.insertData);

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

//			statement.execute(SQLAppointment.dropTable);
//			statement.execute(SQLAppointment.createTable);
//			statement.execute(SQLAppointment.insertData);

		} catch (SQLException e) {
			System.out.println("Something went wrong accessing SQL.");
			e.printStackTrace();
		}
		System.out.println("\ndone.");
	}

	/**
	 * Create the frame.
	 */
	public TattooPayrollApp() {
		accessDB();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 1000);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 50, 50, 50));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		createTitlePnl();
		createControlPnl();

		// displays and houses card panels
		cardContainer = new JPanel();
		contentPane.add(cardContainer, BorderLayout.CENTER);
		cardContainer.setLayout(cl);
		addBasicCards();

	}

	private void addBasicCards() {
		JPanel ApptDisplayPanel = new ApptDisplayPanel(this);
		cardContainer.add(ApptDisplayPanel, APPTPANEL);

		JPanel ShopRevenueDisplayPanel = new ShopRevenueDisplayPanel();
		cardContainer.add(ShopRevenueDisplayPanel, SHOPREVENUE);

		JPanel CreateApptPanel = new CreateApptPanel();
		cardContainer.add(CreateApptPanel, CREATEPANEL);
	}

	/**
	 * Adds new cards to the card container so the user can switch between screens.
	 * 
	 * @param card - the display which leads to the next screen.
	 * @param name - name of the card that needs to be called upon click.
	 */
	public void addNewCard(JPanel card, String name) {
		cardContainer.add(card, name);
		System.out.println("card count: " + cardContainer.getComponentCount());
	}

	/**
	 * Changes to a new card in the card container.
	 * 
	 * @param name - name of the card that is called upon clicking.
	 */
	public void switchToCard(String name) {
		cl.show(cardContainer, name);
		System.out.println("card count: " + cardContainer.getComponentCount());
	}

	private void createControlPnl() {
		JPanel controlPnl = new JPanel();
		controlPnl.setBorder(new EmptyBorder(-100, 10, 0, 10));
		controlPnl.setLayout(new GridLayout(0, 1, 10, -20));
		contentPane.add(controlPnl, BorderLayout.WEST);

		JPanel bookApptPnl = createBtnPnl(controlPnl);
		bookApptPnl.setLayout(new FlowLayout(FlowLayout.CENTER, 0, -20));

		JButton scheduleApptBtn = createScheduleBtn();
		bookApptPnl.add(scheduleApptBtn);

		JPanel dropBoxPnl = createDropBoxPnl();
		controlPnl.add(dropBoxPnl);

		JPanel logoPnl = createLogoPnl(controlPnl);
		logoPnl.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel logoLbl = createWormwoodLogoLbl();
		logoPnl.add(logoLbl);

		JLabel lblShopImg = createLogoLbl();
		logoPnl.add(lblShopImg);
	}

	private JButton createScheduleBtn() {
		JButton scheduleApptBtn = new JButton("Book Appointment");
		scheduleApptBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cl.show(cardContainer, CREATEPANEL);
			}
		});
		scheduleApptBtn.setVerticalAlignment(SwingConstants.BOTTOM);
		scheduleApptBtn.setMargin(new Insets(20, 20, 20, 20));
		scheduleApptBtn.setFont(new Font("Lucida Grande", Font.PLAIN, 21));
		scheduleApptBtn.setContentAreaFilled(false);
		return scheduleApptBtn;
	}

	private JPanel createBtnPnl(JPanel controlPnl) {
		JPanel bookApptPnl = new JPanel();
		bookApptPnl.setBorder(new EmptyBorder(150, 0, 0, 0));
		bookApptPnl.setSize(new Dimension(200, 200));
		controlPnl.add(bookApptPnl);
		return bookApptPnl;
	}

	private JPanel createLogoPnl(JPanel controlPnl) {
		JPanel logoPnl = new JPanel();
		logoPnl.setMaximumSize(new Dimension(5050, 32767));
		controlPnl.add(logoPnl);
		return logoPnl;
	}

	private JLabel createWormwoodLogoLbl() {
		JLabel logoLbl = new JLabel("");
		logoLbl.setVerticalAlignment(SwingConstants.BOTTOM);
		logoLbl.setHorizontalAlignment(SwingConstants.CENTER);
		logoLbl.setIcon(new ImageIcon(TattooPayrollApp.class.getResource("/img/wormwoodLogo.jpg")));
		return logoLbl;
	}

	private JLabel createLogoLbl() {
		JLabel lblShopImg = new JLabel("");
		lblShopImg.setVerticalTextPosition(SwingConstants.TOP);
		lblShopImg.setVerticalAlignment(SwingConstants.TOP);
		lblShopImg.setHorizontalAlignment(SwingConstants.CENTER);
		lblShopImg.setIcon(new ImageIcon(TattooPayrollApp.class.getResource("/img/tattooLogo.png")));
		return lblShopImg;
	}

	private JPanel createDropBoxPnl() {
		JPanel dropBoxPnl = new JPanel();
		dropBoxPnl.setBorder(new EmptyBorder(0, 0, 20, 0));
		dropBoxPnl.setLayout(new GridLayout(0, 1, 0, 10));

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

					// BAD MEMORY WAY?
//					cardContainer.add(new ArtistRevenueDisplayPanel(id, name), cardName);
//					cl.show(cardContainer, cardName);

					// POSSIBLY BETTER?
					cardContainer.removeAll();
					addBasicCards();
					cardContainer.add(new ArtistRevenueDisplayPanel(id, name), cardName);
					cl.show(cardContainer, cardName);

					System.out.println("card count: " + cardContainer.getComponentCount());
				} else {
					// OLD
					// cl.show(cardContainer, SHOPREVENUE);

					// MAYBE??
					cardContainer.removeAll();
					addBasicCards();
					cl.show(cardContainer, SHOPREVENUE);
					System.out.println("card count: " + cardContainer.getComponentCount());
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

					// BAD MEMORY WAY?
//					cardContainer.add(new ArtistApptDisplayPanel(id, name, frame), cardName);
//					cl.show(cardContainer, cardName);
//					
					// MAYBE BETTER?
					cardContainer.removeAll();
					addBasicCards();
					cardContainer.add(new ArtistApptDisplayPanel(id, name, frame), cardName);
					cl.show(cardContainer, cardName);
					System.out.println("card count: " + cardContainer.getComponentCount());

				} else {
					// BAD MEMORY WAY?
//					JPanel ApptDisplayPanel = new ApptDisplayPanel(frame);
//					cardContainer.add(ApptDisplayPanel, APPTPANEL);

					// POSSIBLY BETTER?
					cardContainer.removeAll();
					addBasicCards();
					cl.show(cardContainer, APPTPANEL);
					System.out.println("card count: " + cardContainer.getComponentCount());
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

	private JLabel createTitleLbl() {
		JLabel titleLbl = new JLabel("Tattoo Payroll App");
		titleLbl.setFont(new Font("Lucida Grande", Font.PLAIN, 69));
		return titleLbl;
	}
}
