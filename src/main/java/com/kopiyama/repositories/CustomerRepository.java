package com.kopiyama.repositories;

import com.kopiyama.models.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomerRepository {
	private static List<Customer> customers = new ArrayList<>();  // List untuk menyimpan data pelanggan

	static {
		// Inisialisasi data customer dengan beberapa contoh data
		customers = getAllCustomerInitial();
	}

	// Method untuk mengembalikan data pelanggan awal
	public static List<Customer> getAllCustomerInitial(){
		Car carCust1 = new Car("B201E", "Silver", "Honda", "Manual", 2000, 4);
		Motorcyle motorCust1 = new Motorcyle("B231E", "Hitam", "Yamaha", "Otomatis", 2017, 155);
		List<Vehicle> listCust1Vechicle = Arrays.asList(carCust1, motorCust1);

		Customer cust1 = new Customer("Cust-001", "Budi", "Bandung", "cust001", listCust1Vechicle);

		Vehicle motorCust2 = new Motorcyle("D6734ZD", "Merah", "Honda", "Otomatis", 2019, 125);
		List<Vehicle> listCust2Vechicle = Arrays.asList(motorCust2);

		Customer cust2 = new Customer("Cust-002", "Yanto", "Bandung", "cust002", listCust2Vechicle);

		Car carCust3 = new Car("Z4551E", "Biru", "Daihatsu", "Otomatis", 2010, 4);
		Motorcyle motorCust3 = new Motorcyle("Z231DHW", "Hitam", "Suzuki", "Manual", 2012, 135);
		List<Vehicle> listCust3Vechicle = Arrays.asList(carCust3, motorCust3);

		MemberCustomer cust3 = new MemberCustomer("Cust-003", "Rahmat", "Garut", "cust002", listCust3Vechicle, 2000000);

		return Arrays.asList(cust1, cust2, cust3);
	}

	// Method untuk mengambil semua data customer yang tersimpan
	public static List<Customer> getAllCustomer(){
		return customers;
	}

	// Method untuk memperbarui data customer
	public static void updateCustomer(Customer updatedCustomer) {
		for (int i = 0; i < customers.size(); i++) {
			if (customers.get(i).getCustomerId().equals(updatedCustomer.getCustomerId())) {
				customers.set(i, updatedCustomer);
				break;
			}
		}
	}
}
