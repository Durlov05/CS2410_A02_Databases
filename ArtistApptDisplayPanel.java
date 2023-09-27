package tattooPayroll;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;

public class ArtistApptDisplayPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private int id; // use this to run queries?
	private String name;

	/**
	 * Create the panel.
	 */
	public ArtistApptDisplayPanel(int id, String name) {
		this.id = id;
		setBackground(new Color(255, 128, 64));

		JLabel lblNewLabel = new JLabel(name);
		add(lblNewLabel);

		// TODO get queries

	}

}
