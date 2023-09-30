package tattooPayroll;

/**
* This class brings up a menu to edit the selected appointment.
* @author Syed Mujibur Rahman (Mujib) + Nikki Burr + Nikki Buzianis
*/

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;

public class EditApptPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private int apptID;

	/**
	 * Create the panel that allows the user to edit a particular appointment.
	 */
	public EditApptPanel(int apptID) {
		this.apptID = apptID;

		setBackground(new Color(144, 238, 144));

		add(new JLabel("" + apptID));

	}

}