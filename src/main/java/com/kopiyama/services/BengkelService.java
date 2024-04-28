package com.kopiyama.services;

import com.kopiyama.models.Customer;
import com.kopiyama.models.MemberCustomer;
import com.kopiyama.repositories.CustomerRepository;

import java.util.Scanner;

public class BengkelService {
    private static Scanner input = new Scanner(System.in);

    //Silahkan tambahkan fitur-fitur utama aplikasi disini
    public static boolean login(String customerId, String password) {
        // Cek apakah customer dengan customer id dan password yang dimasukkan ada di repository
        for (Customer customer : CustomerRepository.getAllCustomer()) {
            if (customer.getCustomerId().equals(customerId) && customer.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    //Info Customer
    public static void displayCustomerProfile(String customerId) {
        // Ambil data customer berdasarkan customerId
        Customer customer = getCustomerById(customerId);

        if (customer != null) {
            // Tampilkan informasi customer
            System.out.println("Customer Profile");
            System.out.println("Customer Id: " + customer.getCustomerId());
            System.out.println("Nama: " + customer.getName());
            System.out.println("Customer Status: " + getCustomerStatus(customer));
            System.out.println("Alamat: " + customer.getAddress());

            // Jika customer adalah MemberCustomer, tampilkan saldo koin
            if (customer instanceof MemberCustomer) {
                MemberCustomer memberCustomer = (MemberCustomer) customer;
                System.out.println("Saldo Koin: " + memberCustomer.getSaldoCoin());
            }

            System.out.println("List Kendaraan:");
            PrintService.printVechicle(customer.getVehicles());
        } else {
            System.out.println("Customer dengan Customer Id " + customerId + " tidak ditemukan.");
        }
    }

    private static Customer getCustomerById(String customerId) {
        // Ambil semua data customer
        for (Customer customer : CustomerRepository.getAllCustomer()) {
            // Jika Customer Id cocok, kembalikan customer
            if (customer.getCustomerId().equals(customerId)) {
                return customer;
            }
        }
        // Jika tidak ditemukan, kembalikan null
        return null;
    }

    private static String getCustomerStatus(Customer customer) {
        // Tentukan status customer berdasarkan tipe objek Customer
        if (customer instanceof MemberCustomer) {
            return "Member";
        } else {
            return "Non Member";
        }
    }
    //Booking atau Reservation

    //Top Up Saldo Coin Untuk Member Customer

    //Logout

}
