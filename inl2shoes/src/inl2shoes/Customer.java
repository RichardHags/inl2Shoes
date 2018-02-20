package inl2shoes;

public class Customer {

	private String firstName;
	private String lastName;
	private String postalAdress;
	private int postalCode;
	private String postalRegion;
	private int id;
	private double totalOrderAmount;

	Customer() {
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPostalAdress() {
		return postalAdress;
	}

	public void setPostalAdress(String postalAdress) {
		this.postalAdress = postalAdress;
	}

	public int getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public String getPostalRegion() {
		return postalRegion;
	}

	public void setPostalRegion(String postalRegion) {
		this.postalRegion = postalRegion;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public double getTotalOrderAmount() {
		return totalOrderAmount;
	}

	public void setTotalOrderAmount(double totalOrderAmount) {
		this.totalOrderAmount = totalOrderAmount;
	}

	public void printCustomers() {
		System.out.println(getFirstName() + " " + getLastName());
	}
	
	public void printTotalOrderAmount() {
		System.out.printf("%s %s  %.2f \n", getFirstName(), getLastName(), getTotalOrderAmount());
	}

}
