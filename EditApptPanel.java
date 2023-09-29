package tattooPayroll;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;

public class EditApptPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private int apptID;

	/**
	 * Create the panel.
	 */
	public EditApptPanel(int apptID) {
		this.apptID = apptID;

		setBackground(new Color(144, 238, 144));

		add(new JLabel("" + apptID));

	}

}