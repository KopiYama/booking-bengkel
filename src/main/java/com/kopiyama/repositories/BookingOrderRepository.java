package com.kopiyama.repositories;

import com.kopiyama.models.BookingOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingOrderRepository {
    private static List<BookingOrder> allBookings = new ArrayList<>();
    private static Map<String, Integer> bookingCountPerCustomer = new HashMap<>();

    public static void addBooking(BookingOrder bookingOrder) {
        allBookings.add(bookingOrder);
        String customerId = bookingOrder.getCustomer().getCustomerId();
        bookingCountPerCustomer.put(customerId, bookingCountPerCustomer.getOrDefault(customerId, 0) + 1);
    }

    public static List<BookingOrder> getAllBookings() {
        return new ArrayList<>(allBookings);
    }
    public static int getBookingCountForCustomer(String customerId) {
        return bookingCountPerCustomer.getOrDefault(customerId, 0);
    }
}
