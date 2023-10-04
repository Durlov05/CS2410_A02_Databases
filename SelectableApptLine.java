package tattooPayroll;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.border.EmptyBorder;
import java.awt.Cursor;

/**
 * Houses all of an appointment's data and SQL data and displays it visually. 
 * Displays an appointment's information in any appointment view table. When
 * clicked, it will move to a new screen where the appointment can be
 * edited/updated.
 * 
 * @author Syed Mujibur Rahman (Mujib) + Nikki Burr + Nikki Buzianis
 */
public class SelectableApptLine extends JPanel {
	private static final long serialVersionUID = 1L;
	private int apptID;
	private int artistID;
	private int customerID;
	private String date;
	private String artistName;
	private String customerName;
	private int apptLength;

	/**
	 * Inititalizes all of the values for an appointment. Meant to be displayed on All Appointments view. 
	 */
	public SelectableApptLine(Object apptId, Object date, Object artistID, Object customerID, Object length, Object artistName,
			Object custName) {
		setLayout();

		this.apptID = Integer.parseInt(apptId.toString());
		this.date = date.toString();
		this.artistID = Integer.parseInt(artistID.toString());
		this.artistName = artistName.toString();
		this.customerID = Integer.parseInt(customerID.toString());
		this.customerName = custName.toString();
		apptLength = Integer.parseInt(length.toString());
	}

	/**
	 * Inititalizes all of the values for an appointment. Meant to be displayed on an artist's aapointment page. 
	 */
	public SelectableApptLine(Object apptId, Object date, Object artistID, Object customerID, Object length, Object custName) {
		setLayout();

		this.apptID = Integer.parseInt(apptId.toString());
		this.date = date.toString();
		this.artistID = Integer.parseInt(artistID.toString());
		// this.artistName = artistName.toString();
		this.customerID = Integer.parseInt(customerID.toString());
		this.customerName = custName.toString();
		apptLength = Integer.parseInt(length.toString());
	}

	/**
	 * Adds an appointment's date, artist, customer, and length to a line. 
	 */
	public void fillOutAppointment() {
		setLayout(new GridLayout(0, 4, 5, 0));
		add(createLbl(this.date));
		add(createLbl(artistName));
		add(createLbl(customerName));
		add(createLbl("" + apptLength));
	}

	/**
	 *Adds an appointment's date, customer, and length to a line. 
	 */
	public void displayArtistAppts() {
		setLayout(new GridLayout(0, 3, 5, 0));
		add(createLbl(this.date));
		add(createLbl(customerName));
		add(createLbl("" + apptLength));
	}

	private void setLayout() {
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setMaximumSize(new Dimension(32767, 40));
	}

	private JLabel createLbl(String info) {
		JLabel lblData = new JLabel(info);
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 25));
		return lblData;
	}

	public int getApptID() {
		return apptID;
	}

	public int getArtistID() {
		return artistID;
	}

	public int getCustomerID() {
		return customerID;
	}

	public String getArtistName() {
		return artistName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getDate() {
		return date;
	}

	public int getApptLength() {
		return apptLength;
	}
}