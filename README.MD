# Bookings SOAP Service

This is just a sample Bookings SOAP Service built using Springboot

A WSDL is available at http://localhost:8888/ws/bookings.wsdl

The service exposes 4 operations:
* initBooking: creates a booking entry
* listBookings: lists the booking from the repository
* checkBookingStatus: access the details of a booking
* updateBookingStatus: updates the status of a booking

The service simulates operations that take time to be performed through a Thread.sleep call