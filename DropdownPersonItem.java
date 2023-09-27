package tattooPayroll;

public class DropdownPersonItem extends DropdownItem {
	private int id;

	public DropdownPersonItem(Object id, Object name) {
		super(name.toString());
		this.id = Integer.parseInt(id.toString());
	}

	public DropdownPersonItem(Object id, String name) {
		super(name);
		this.id = Integer.parseInt(id.toString());
	}
	public int getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = Integer.parseInt(id.toString());
	}

	@Override
	public String toString() {
		return super.getName();
	}

}