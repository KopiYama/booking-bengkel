package com.kopiyama.services;

import com.kopiyama.interfaces.IBengkelPayment;
import com.kopiyama.models.*;
import com.kopiyama.repositories.CustomerRepository;
import com.kopiyama.repositories.ItemServiceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

    public static void bookService(String customerId) {
        Customer customer = getCustomerById(customerId);
        if (customer == null) {
            System.out.println("Customer tidak ditemukan.");
            return;
        }

        System.out.println("Masukan Vehicle Id:");
        String vehicleId = input.nextLine();

        // Check if the customer owns the vehicle
        if (!customerOwnsVehicle(customer, vehicleId)) {
            System.out.println("Vehicle Id tidak ditemukan pada customer ini.");
            return;
        }

        List<ItemService> availableServices = getServicesForVehicleType(vehicleId);
        PrintService.printService(availableServices);

        List<ItemService> selectedServices = new ArrayList<>();
        String serviceId;
        boolean addingMoreServices;

        do {
            System.out.println("Silahkan masukan Service Id:");
            serviceId = input.nextLine();
            ItemService service = findServiceById(serviceId, availableServices);
            if (service != null) {
                selectedServices.add(service);
                System.out.println("Apakah anda ingin menambahkan Service Lainnya? (Y/T)");
                String response = input.nextLine();
                addingMoreServices = response.equalsIgnoreCase("Y");
            } else {
                System.out.println("Service Id tidak valid.");
                addingMoreServices = true;
            }
        } while (addingMoreServices);

        double totalCost = calculateTotalCost(selectedServices);
        BookingOrder bookingOrder = new BookingOrder(generateBookingId(customerId), customer, selectedServices, "Cash", totalCost, totalCost);

        // Payment method selection and application of discount if the customer is a MemberCustomer
        if (customer instanceof MemberCustomer) {
            System.out.println("Silahkan Pilih Metode Pembayaran (Saldo Coin atau Cash)");
            System.out.println("1. Saldo Coin\n2. Cash");
            int paymentChoice = Integer.parseInt(input.nextLine());

            if (paymentChoice == 1) {
                bookingOrder.setPaymentMethod("Saldo Coin");
                bookingOrder.calculatePayment(); // Calculate the discounted price
                updateMemberSaldo((MemberCustomer) customer, bookingOrder.getTotalPayment());
            } else {
                bookingOrder.calculatePayment();
            }
        } else {
            bookingOrder.calculatePayment();
        }

        System.out.println("Booking Berhasil!!!");
        System.out.printf("Total Harga Service : %.2f%n", totalCost);
        System.out.printf("Total Pembayaran : %.2f%n", bookingOrder.getTotalPayment());
    }

    private static void updateMemberSaldo(MemberCustomer member, double amount) {
        double newSaldo = member.getSaldoCoin() - amount;
        member.setSaldoCoin(newSaldo);
        CustomerRepository.updateCustomer(member);  // Perbarui data customer di repositori
        System.out.printf("Saldo Coin Updated: New Balance = %.2f%n", newSaldo);
    }

    private static boolean customerOwnsVehicle(Customer customer, String vehicleId) {
        return customer.getVehicles().stream().anyMatch(v -> v.getVehiclesId().equals(vehicleId));
    }

    private static List<ItemService> getServicesForVehicleType(String vehicleId) {
        // First, find the vehicle's type by its ID
        String vehicleType = getVehicleTypeById(vehicleId);

        // Filter services based on the vehicle's type
        return ItemServiceRepository.getAllItemService().stream()
                .filter(service -> service.getVehicleType().equalsIgnoreCase(vehicleType))
                .collect(Collectors.toList());
    }

    private static String choosePaymentMethod(Customer customer) {
        if (customer instanceof MemberCustomer) {
            System.out.println("Silahkan Pilih Metode Pembayaran (Saldo Coin atau Cash)");
            System.out.println("1. Saldo Coin\n2. Cash");
            int paymentChoice = Integer.parseInt(input.nextLine());
            return paymentChoice == 1 ? "Saldo Coin" : "Cash";
        } else {
            System.out.println("Metode pembayaran: Cash (default for non-members)");
            return "Cash";
        }
    }

    private static String getVehicleTypeById(String vehicleId) {
        // Assuming you have access to all vehicles from a repository or other storage
        List<Customer> customers = CustomerRepository.getAllCustomer();
        for (Customer customer : customers) {
            for (Vehicle vehicle : customer.getVehicles()) {
                if (vehicle.getVehiclesId().equals(vehicleId)) {
                    return vehicle.getVehicleType(); // Assuming VehicleType is correctly set as "Car" or "Motorcycle"
                }
            }
        }
        return null; // Or handle this case, perhaps throw an exception or return a default type
    }

    private static ItemService findServiceById(String serviceId, List<ItemService> services) {
        for (ItemService service : services) {
            if (service.getServiceId().equals(serviceId)) {
                return service;
            }
        }
        return null;
    }

    private static double calculateTotalCost(List<ItemService> services) {
        double total = 0;
        for (ItemService service : services) {
            total += service.getPrice();
        }
        return total;
    }

    private static String generateBookingId(String customerId) {
        // Simplified booking ID generation logic
        return "Book-" + customerId + "-" + ((int) (Math.random() * 1000));
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
