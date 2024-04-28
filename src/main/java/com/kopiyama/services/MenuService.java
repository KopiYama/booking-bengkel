package com.kopiyama.services;

import com.kopiyama.models.Customer;
import com.kopiyama.models.ItemService;
import com.kopiyama.repositories.CustomerRepository;
import com.kopiyama.repositories.ItemServiceRepository;

import java.util.List;
import java.util.Scanner;

public class MenuService {
	private static List<Customer> listAllCustomers = CustomerRepository.getAllCustomer();
	private static List<ItemService> listAllItemService = ItemServiceRepository.getAllItemService();
	private static Scanner input = new Scanner(System.in);
	private static boolean isLoggedIn = false;
	public static void run() {
		boolean isLooping = true;
		do {
			printWelcomeMenu();
			int choice = Validation.validasiNumberWithRange("Masukan Pilihan Menu:", "Input Harus Berupa Angka!", "^[0-9]+$", 1, 0);
			switch (choice) {
				case 1:
					loginMenu();
					break;
				case 0:
					isLooping = false;
					System.out.println("Terima kasih telah menggunakan aplikasi Booking Bengkel.");
					break;
				default:
					System.out.println("Pilihan tidak valid.");
					break;
			}
		} while (isLooping);
	}

	public static void printWelcomeMenu() {
		String[] welcomeMenu = {"Login", "Exit"};
		PrintService.printMenu(welcomeMenu, "Aplikasi Booking Bengkel");
	}

	public static void loginMenu() {
		boolean isAuthenticated = true;
		do {
			System.out.println("Login");
			String customerId = Validation.validasiCustomerId("Masukkan Customer Id: ", "Customer Id tidak valid!", CustomerRepository.getAllCustomer());
			String password = Validation.validasiPassword("Masukkan Password: ", "Password tidak valid!", customerId, CustomerRepository.getAllCustomer());

			// Panggil method login dari BengkelService untuk memverifikasi login
			isAuthenticated = BengkelService.login(customerId, password);

			if (isAuthenticated) {
				System.out.println("Login berhasil!");
				// Langsung ke mainMenu jika login berhasil
				mainMenu(customerId);
			} else {
				System.out.println("Login gagal. Customer Id atau Password salah. Silakan coba lagi.");
			}
		} while (!isAuthenticated);
	}

	public static void mainMenu(String customerId) {
		String[] listMenu = {"Informasi Customer", "Booking Bengkel", "Top Up Bengkel Coin", "Informasi Booking", "Logout"};
		int menuChoice = 0;
		boolean isLooping = true;

		do {
			PrintService.printMenu(listMenu, "Booking Bengkel Menu");
			menuChoice = Validation.validasiNumberWithRange("Masukan Pilihan Menu:", "Input Harus Berupa Angka!", "^[0-9]+$", listMenu.length-1, 0);
			System.out.println(menuChoice);

			switch (menuChoice) {
			case 1:
				//panggil fitur Informasi Customer
				BengkelService.displayCustomerProfile(customerId);
				break;
			case 2:
				//panggil fitur Booking Bengkel
				BengkelService.bookService(customerId);
				break;
			case 3:
				//panggil fitur Top Up Saldo Coin
				break;
			case 4:
				//panggil fitur Informasi Booking Order
				break;
			default:
				System.out.println("Logout");
				isLooping = false;
				break;
			}
		} while (isLooping);


	}

	//Silahkan tambahkan kodingan untuk keperluan Menu Aplikasi
}
