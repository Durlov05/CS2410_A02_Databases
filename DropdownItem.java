package tattooPayroll;

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