package com.kopiyama.services;

import com.kopiyama.models.Customer;

import java.util.List;
import java.util.Scanner;

public class Validation {
	
	public static String validasiInput(String question, String errorMessage, String regex) {
	    Scanner input = new Scanner(System.in);
	    String result;
	    boolean isLooping = true;
	    do {
	      System.out.print(question);
	      result = input.nextLine();

	      //validasi menggunakan matches
	      if (result.matches(regex)) {
	        isLooping = false;
	      }else {
	        System.out.println(errorMessage);
	      }

	    } while (isLooping);

	    return result;
	  }
	
	public static int validasiNumberWithRange(String question, String errorMessage, String regex, int max, int min) {
	    int result;
	    boolean isLooping = true;
	    do {
	      result = Integer.valueOf(validasiInput(question, errorMessage, regex));
	      if (result >= min && result <= max) {
	        isLooping = false;
	      }else {
	        System.out.println("Pilihan angka " + min + " s.d " + max);
	      }
	    } while (isLooping);

	    return result;
	  }

	public static String validasiCustomerId(String question, String errorMessage, List<Customer> customers) {
		Scanner input = new Scanner(System.in);
		String result;
		boolean isLooping = true;

		do {
			System.out.print(question);
			result = input.nextLine();

			// Validasi Customer Id
			boolean isValidCustomerId = false;
			for (Customer customer : customers) {
				if (customer.getCustomerId().equals(result)) {
					isValidCustomerId = true;
					break;
				}
			}

			if (isValidCustomerId) {
				isLooping = false;
			} else {
				System.out.println(errorMessage);
			}
		} while (isLooping);

		return result;
	}

	public static String validasiPassword(String question, String errorMessage, String customerId, List<Customer> customers) {
		Scanner input = new Scanner(System.in);
		String result;
		boolean isLooping = true;

		do {
			System.out.print(question);
			result = input.nextLine();

			// Validasi Password
			boolean isValidPassword = false;
			for (Customer customer : customers) {
				if (customer.getCustomerId().equals(customerId) && customer.getPassword().equals(result)) {
					isValidPassword = true;
					break;
				}
			}

			if (isValidPassword) {
				isLooping = false;
			} else {
				System.out.println(errorMessage);
			}
		} while (isLooping);

		return result;
	}



}
