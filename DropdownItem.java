package tattooPayroll;

/** 
* Creates a a dropdown menu item option and stores its name value.
* @author Syed Mujibur Rahman (Mujib) + Nikki Burr + Nikki Buzianis
*/
public class DropdownItem {
	private String name;

	public DropdownItem(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
}