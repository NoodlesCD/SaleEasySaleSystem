package manager;

import java.util.ArrayList;

import objects.Invoice;

public class InvoiceManager {
	static ArrayList<Invoice> invoiceList = new ArrayList<>();

	public static ArrayList<Invoice> getInvoiceList() {
		return invoiceList;
	}

	public static void addInvoice(Invoice invoice) {
		invoiceList.add(invoice);
	}
	
	public static int getInvoiceNumber() {
		if (invoiceList.size() == 0) {
			return 100001;
		}
		else {
			return invoiceList.get(invoiceList.size() - 1).getInvoiceNumber() + 1;
		}
	}
}
