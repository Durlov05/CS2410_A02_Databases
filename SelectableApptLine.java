package tattooPayroll;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.border.EmptyBorder;
import java.awt.Cursor;

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
	 * Create the panel.
	 */
//	public SelectableApptLine(Object date, Object artist, Object customer, Object length) {
//		setLayout();
//
//		this.date = date.toString();
//		this.artistName = artist.toString();
//		this.customerName = customer.toString();
//		apptLength = length.toString();
//	}

	public SelectableApptLine(Object date, Object artistID, Object customerID, Object length, Object artistName,
			Object custName) {
		setLayout();

		this.date = date.toString();
		this.artistID = Integer.parseInt(artistID.toString());
		this.artistName = artistName.toString();
		this.customerID = Integer.parseInt(customerID.toString());
		this.customerName = custName.toString();
		apptLength = Integer.parseInt(length.toString());
	}
	
	public SelectableApptLine(Object date, Object artistID, Object customerID, Object length, Object custName) {
		setLayout();

		this.date = date.toString();
		this.artistID = Integer.parseInt(artistID.toString());
		//this.artistName = artistName.toString();
		this.customerID = Integer.parseInt(customerID.toString());
		this.customerName = custName.toString();
		apptLength = Integer.parseInt(length.toString());
	}

	/**
	 * TODO Needs doc comment
	 */
	public void displayAllAppts() {
		setLayout(new GridLayout(0, 4, 5, 0));
		add(createLbl(this.date));
		add(createLbl(artistName));
		add(createLbl(customerName));
		add(createLbl(""+apptLength));
	}

	/**
	 * TODO Needs doc comment
	 */
	public void displayArtistAppts() {
		setLayout(new GridLayout(0, 3, 5, 0));
		add(createLbl(this.date));
		add(createLbl(customerName));
		add(createLbl(""+apptLength));
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