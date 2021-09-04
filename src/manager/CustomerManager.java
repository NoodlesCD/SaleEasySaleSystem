package manager;

import java.util.ArrayList;

import objects.Customer;

public class CustomerManager {
	static ArrayList<Customer> customerList = new ArrayList<>();
	
	public static void addCustomer(Customer customer) {
		customerList.add(customer);
	}
	public static void editCustomer(Customer customer) {
		for (int x = 0; x < customerList.size(); x++) {
			if (customerList.get(x).getCustomerId() == customer.getCustomerId()) {
				customerList.set(x, customer);
				break;
			}
		}
	}
	
	public static ArrayList<Customer> getCustomerList() {
		return customerList;
	}
	
	public static int generateCustomerId() {
		if (customerList.size() == 0) {
			return 000001;
		}
		else {
			return customerList.get(customerList.size() - 1).getCustomerId() + 1;
		}
	}
}
