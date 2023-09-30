package tattooPayroll;
/** ????
* This is a constructor that returns the Artist's Name in the Drop down menus.
* @author Syed Mujibur Rahman (Mujib) + Nikki Burr + Nikki Buzianis
*/
public class DropdownShopItem extends DropdownItem {

	public DropdownShopItem(String name) {
		super(name);
	}
	
	@Override
	public String toString() {
		return super.getName();
	}

	
}
