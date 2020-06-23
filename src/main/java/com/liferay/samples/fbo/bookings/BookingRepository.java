package com.liferay.samples.fbo.bookings;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.liferay.samples.fbo.bookings_web_service.Booking;

@Component
public class BookingRepository {

	private Map<String, Booking> bookings;
	
	@PostConstruct
	public void initData() {
		
		this.bookings = new ConcurrentHashMap<>();
		
	}
	
	public void addBooking(Booking booking) {
		this.bookings.put(booking.getBookingId(), booking);
	}
	
	public Booking getBooking(String bookingId) {
		return bookings.get(bookingId);
	}

	public List<String> getBookings(int start, int count) {

		return bookings.keySet().stream().skip(start).limit(count).collect(Collectors.toList());
		
	}

	
}
