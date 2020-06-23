package com.liferay.samples.fbo.bookings;

import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.liferay.samples.fbo.bookings_web_service.Booking;
import com.liferay.samples.fbo.bookings_web_service.BookingStatusEnum;
import com.liferay.samples.fbo.bookings_web_service.CheckBookingStatusRequest;
import com.liferay.samples.fbo.bookings_web_service.CheckBookingStatusResponse;
import com.liferay.samples.fbo.bookings_web_service.InitBookingRequest;
import com.liferay.samples.fbo.bookings_web_service.InitBookingResponse;
import com.liferay.samples.fbo.bookings_web_service.ListBookingsRequest;
import com.liferay.samples.fbo.bookings_web_service.ListBookingsResponse;
import com.liferay.samples.fbo.bookings_web_service.UpdateBookingStatusRequest;
import com.liferay.samples.fbo.bookings_web_service.UpdateBookingStatusResponse;

@Endpoint
public class BookingEndpoint {

	private static final String NAMESPACE_URI = "http://liferay.com/samples/fbo/bookings-web-service";
	
	private BookingRepository bookingRepository;

	@Autowired
	public BookingEndpoint(BookingRepository bookingRepository) {
		this.bookingRepository = bookingRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "initBookingRequest")
	@ResponsePayload
	public InitBookingResponse initBooking(@RequestPayload InitBookingRequest request) {
		InitBookingResponse response = new InitBookingResponse();

		Booking booking = new Booking();
		booking.setBookingInformation(request.getBookingInformation());
		booking.setBookingId(UUID.randomUUID().toString());
		booking.setBookingStatus(BookingStatusEnum.PENDING);
		
		this.bookingRepository.addBooking(booking);
		
		response.setBooking(booking);
		
		long millis = new Random().nextInt(5000);
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "listBookingsRequest")
	@ResponsePayload
	public ListBookingsResponse listBookings(@RequestPayload ListBookingsRequest request) {
		ListBookingsResponse response = new ListBookingsResponse();

		int start = request.getStartingItem().intValue();
		int count = request.getNumberOfItems().intValue();
		
		response.getBookingId().addAll(this.bookingRepository.getBookings(start, count));

		long millis = new Random().nextInt(300);
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateBookingStatusRequest")
	@ResponsePayload
	public UpdateBookingStatusResponse updateBookingStatus(@RequestPayload UpdateBookingStatusRequest request) {
		UpdateBookingStatusResponse response = new UpdateBookingStatusResponse();

		String bookingId = request.getBookingId();
		BookingStatusEnum status = request.getBookingStatus();
		
		this.bookingRepository.getBooking(bookingId).setBookingStatus(status);
		
		response.setBooking(this.bookingRepository.getBooking(request.getBookingId()));

		long millis = new Random().nextInt(500);
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "checkBookingStatusRequest")
	@ResponsePayload
	public CheckBookingStatusResponse checkBookingStatus(@RequestPayload CheckBookingStatusRequest request) {
		CheckBookingStatusResponse response = new CheckBookingStatusResponse();

		response.setBooking(this.bookingRepository.getBooking(request.getBookingId()));

		long millis = new Random().nextInt(2000);
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
	}

}
