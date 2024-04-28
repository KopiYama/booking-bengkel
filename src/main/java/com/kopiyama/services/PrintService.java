package com.kopiyama.services;

import com.kopiyama.models.Car;
import com.kopiyama.models.ItemService;
import com.kopiyama.models.Vehicle;

import java.util.List;

public class PrintService {
	
	public static void printMenu(String[] listMenu, String title) {
		String line = "+---------------------------------+";
		int number = 1;
		String formatTable = " %-2s. %-25s %n";
		
		System.out.printf("%-25s %n", title);
		System.out.println(line);
		
		for (String data : listMenu) {
			if (number < listMenu.length) {
				System.out.printf(formatTable, number, data);
			}else {
				System.out.printf(formatTable, 0, data);
			}
			number++;
		}
		System.out.println(line);
		System.out.println();
	}
	
	public static void printVechicle(List<Vehicle> listVehicle) {
		String formatTable = "| %-2s | %-15s | %-10s | %-15s | %-15s | %-5s | %-15s |%n";
		String line = "+----+-----------------+------------+-----------------+-----------------+-------+-----------------+%n";
		System.out.format(line);
	    System.out.format(formatTable, "No", "Vechicle Id", "Warna", "Brand", "Transmisi", "Tahun", "Tipe Kendaraan");
	    System.out.format(line);
	    int number = 1;
	    String vehicleType = "";
	    for (Vehicle vehicle : listVehicle) {
	    	if (vehicle instanceof Car) {
				vehicleType = "Mobil";
			}else {
				vehicleType = "Motor";
			}
	    	System.out.format(formatTable, number, vehicle.getVehiclesId(), vehicle.getColor(), vehicle.getBrand(), vehicle.getTransmisionType(), vehicle.getYearRelease(), vehicleType);
	    	number++;
	    }
	    System.out.printf(line);
	}

	public static void printService(List<ItemService> services) {
		if (services == null || services.isEmpty()) {
			System.out.println("Tidak ada layanan tersedia untuk kendaraan ini.");
			return;
		}

		String header = "+-------+-----------------+-------------------------+----------------------+----------+";
		String rowFormat = "| %-5s | %-15s | %-23s | %-20s | %-8s |%n";

		System.out.println("List Service yang Tersedia:");
		System.out.println(header);
		System.out.format(rowFormat, "No", "Service Id", "Nama Service", "Tipe Kendaraan", "Harga");
		System.out.println(header);

		int count = 1;
		for (ItemService service : services) {
			System.out.format(rowFormat,
					count++,
					service.getServiceId(),
					service.getServiceName(),
					service.getVehicleType(),
					String.format("%.2f", service.getPrice()));
		}
		System.out.println(header);
	}


	//Silahkan Tambahkan function print sesuai dengan kebutuhan.
	
}
