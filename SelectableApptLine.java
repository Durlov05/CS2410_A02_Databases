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
	private String apptLength;

	/**
	 * Create the panel.
	 */
	public SelectableApptLine(Object date, Object artist, Object customer, Object length) {
		setLayout();

		this.date = date.toString();
		this.artistName = artist.toString();
		this.customerName = customer.toString();
		apptLength = length.toString();
	}

	/**
	 * TODO Needs doc comment
	 */
	public void displayAllAppts() {
		add(createLbl(this.date));
		add(createLbl(artistName));
		add(createLbl(customerName));
		add(createLbl(apptLength));
	}

	/**
	 * TODO Needs doc comment
	 */
	public void displayArtistAppts() {
		add(createLbl(this.date));
		add(createLbl(customerName));
		add(createLbl(apptLength));
	}

	private void setLayout() {
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setMaximumSize(new Dimension(32767, 40));
		setLayout(new GridLayout(0, 4, 5, 0));
	}

	private JLabel createLbl(String info) {
		JLabel lblData = new JLabel(info);
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 25));
		return lblData;
	}

}