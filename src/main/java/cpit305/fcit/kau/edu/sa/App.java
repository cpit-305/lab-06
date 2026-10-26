package cpit305.fcit.kau.edu.sa;

public class App {
    public static void main(String[] args) {
        System.out.println("Train Ticket Booking System Demo");

        // Create the booking system and event publisher
        TicketBookingSystem bookingSystem = new TicketBookingSystem();
        // Simulate concurrent bookings
        Thread[] bookingThreads = new Thread[5];
        for (int i = 0; i < bookingThreads.length; i++) {
            final int threadId = i;
            bookingThreads[i] = new Thread(() -> {
                for (int j = 1; j <= 3; j++) {
                    String passengerName = "Passenger-" + threadId + "-" + j;
                    int seatNumber = bookingSystem.bookSeat(passengerName);
                    try {
                        Thread.sleep(500); // Simulate some delay between bookings
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            bookingThreads[i].start();
        }

        // Wait for all booking threads to complete
        for (Thread thread : bookingThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        // Final statistics
        System.out.println("\n\nFinal Booking System Status:");
        System.out.println("Total seats booked: " + bookingSystem.getBookedSeatsCount());
        System.out.println("Total seats available: " +
                (bookingSystem.getTotalSeatsCapacity() - bookingSystem.getBookedSeatsCount()));
    }
}
