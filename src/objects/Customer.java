package objects;

public class Customer {
	private int customerId;
	private String customerName;
	private String dateOfBirth;
	private String streetAddress;
	private String city;
	private String province;
	private String postalCode;
	private String primaryPhoneNumber;
	private String secondaryPhoneNumber;
	private double balance;
	private double balanceLimit;

	public Customer() {
	}

	public Customer(int customerId, String customerName, String streetAddress, String city,
			String province, String postalCode, String primaryPhoneNumber,
			double balance, double balanceLimit) {
		this.customerId = customerId;
		this.customerName = customerName;
		this.streetAddress = streetAddress;
		this.city = city;
		this.province = province;
		this.postalCode = postalCode;
		this.primaryPhoneNumber = primaryPhoneNumber;
		this.balance = balance;
		this.balanceLimit = balanceLimit;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getPrimaryPhoneNumber() {
		return primaryPhoneNumber;
	}
	public void setPrimaryPhoneNumber(String cellPhoneNumber) {
		this.primaryPhoneNumber = cellPhoneNumber;
	}
	public String getSecondaryPhoneNumber() {
		return secondaryPhoneNumber;
	}
	public void setSecondaryPhoneNumber(String homePhoneNumber) {
		this.secondaryPhoneNumber = homePhoneNumber;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getBalanceLimit() {
		return balanceLimit;
	}
	public void setBalanceLimit(double balanceLimit) {
		this.balanceLimit = balanceLimit;
	}


}