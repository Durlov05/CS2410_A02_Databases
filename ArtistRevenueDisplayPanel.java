package tattooPayroll;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;

/**
* This is a display panel for showing the Revenue of a particular artist in the Tattoo Shop.
* @author Syed Mujibur Rahman (Mujib) + Nikki Burr + Nikki Buzianis
*/

public class ArtistRevenueDisplayPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private int id; 
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
