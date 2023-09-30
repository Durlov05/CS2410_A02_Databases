package tattooPayroll;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;

/**
* Creates a panel for the user to edit an already existing appointment. 
* @author Syed Mujibur Rahman (Mujib) + Nikki Burr + Nikki Buzianis
*/
public class EditApptPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private int apptID;

	/**
	 * Create the panel.
	 */
	public EditApptPanel(SelectableApptLine appt) {
		this.apptID = appt.getApptID();

		setBackground(new Color(144, 238, 144));

		add(new JLabel("" + apptID));

	}

}