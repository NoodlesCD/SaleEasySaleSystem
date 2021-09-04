package objects;

import java.util.ArrayList;

public class Invoice {
	ArrayList<Item> itemList = new ArrayList<>();
	private int invoiceNumber;
	private Customer customer;
	private double balance;
	private double cash = 0;
	private double debit = 0;
	private double visa = 0;
	private double mc = 0;
	private double amex = 0;

	public Invoice() {}

	public Invoice(int invoiceNumber, Customer customer) {
		this.invoiceNumber = invoiceNumber;
		this.customer = customer;
	}

	public int getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(int invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void addItem(Item item) {
		itemList.add(item);

	}
	public void removeItem(Item item) {
			for (int y = 0; y < itemList.size(); y++) {
				if (itemList.get(y).equals(item)) {
					itemList.remove(y);
				}
			}
	}
	public ArrayList<Item> getItems() {
		return itemList;
	}
	public double getSubtotal() {
		double subtotal = 0;
		for (Item element : itemList) {
			subtotal += (element.getPrice() * element.getQuantity());
		}
		return Math.round(subtotal * 100.0) / 100.0;
	}
	public double getGST() {
		double gst = 0;
		for (Item element : itemList) {
			gst += (element.getPrice() * element.getQuantity()) * 0.05;
		}
		return Math.round(gst * 100.0) / 100.0;
	}
	public double getTotal() {
		return Math.round((this.getSubtotal() + this.getGST()) * 100.0) / 100.0;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getBalance() {
		return Math.round(this.balance * 100.0) / 100.0;
	}

	public double getDebit() {
		return debit;
	}

	public void setDebit(double debit) {
		this.debit = debit;
	}

	public double getCash() {
		return cash;
	}

	public void setCash(double cash) {
		this.cash = cash;
	}

	public double getVisa() {
		return visa;
	}

	public void setVisa(double visa) {
		this.visa = visa;
	}

	public double getMc() {
		return mc;
	}

	public void setMc(double mc) {
		this.mc = mc;
	}

	public double getAmex() {
		return amex;
	}

	public void setAmex(double amex) {
		this.amex = amex;
	}



}
