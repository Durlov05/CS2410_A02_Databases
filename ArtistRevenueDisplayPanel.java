package tattooPayroll;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;

public class ArtistRevenueDisplayPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private int id; // use this to run queries?
	private String name;

	/**
	 * Create the panel.
	 */
	public ArtistRevenueDisplayPanel(int id, String name) {
		setBackground(new Color(104, 205, 50));

		JLabel lblNewLabel = new JLabel(name);
		add(lblNewLabel);
		
		//TODO get queries
	}

}
