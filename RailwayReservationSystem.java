
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Passenger {
    int id;
    String name;
    int age;
    String trainName;

    Passenger(int id, String name, int age, String trainName) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.trainName = trainName;
    }

    void display() {
        System.out.println("Passenger ID : " + id);
        System.out.println("Name         : " + name);
        System.out.println("Age          : " + age);
        System.out.println("Train        : " + trainName);
        System.out.println("----------------------------");
    }
}

public class RailwayReservationSystem {

    // Linked List stores confirmed reservations
    static LinkedList<Passenger> reservations =
            new LinkedList<>();

    // Queue stores passengers waiting for a seat
    static Queue<Passenger> waitingList =
            new LinkedList<>();

    static Scanner sc = new Scanner(System.in);

    static final int TOTAL_SEATS = 3;
    static int nextId = 1;

    static void bookTicket() {
        System.out.print("Enter passenger name: ");
        String name = sc.nextLine();

        System.out.print("Enter passenger age: ");
        int age = Integer.parseInt(sc.nextLine());

        System.out.print("Enter train name: ");
        String train = sc.nextLine();

        Passenger p = new Passenger(nextId++, name, age, train);

        if (reservations.size() < TOTAL_SEATS) {
            reservations.add(p);
            System.out.println(
                    "Ticket booked successfully!");
            System.out.println("Passenger ID: " + p.id);
        } else if (waitingList.size() < 5) {
            waitingList.offer(p);
            System.out.println(
                    "All seats are occupied.");
            System.out.println(
                    "Passenger added to waiting list.");
            System.out.println("Waiting ID: " + p.id);
        } else {
            System.out.println(
                    "Waiting list is full. Booking rejected.");
        }
    }

    static void cancelTicket() {
        System.out.print("Enter passenger ID to cancel: ");
        int id = Integer.parseInt(sc.nextLine());

        Passenger found = null;

        for (Passenger p : reservations) {
            if (p.id == id) {
                found = p;
                break;
            }
        }

        if (found != null) {
            reservations.remove(found);
            System.out.println(
                    "Reservation cancelled successfully.");

            // Promote the first waiting passenger
            if (!waitingList.isEmpty()) {
                Passenger next = waitingList.poll();
                reservations.add(next);

                System.out.println(
                        "Waiting passenger promoted to confirmed:");
                next.display();
            }
            return;
        }

        // Also allow cancellation from the waiting list
        Passenger waitingPassenger = null;

        for (Passenger p : waitingList) {
            if (p.id == id) {
                waitingPassenger = p;
                break;
            }
        }

        if (waitingPassenger != null) {
            waitingList.remove(waitingPassenger);
            System.out.println(
                    "Waiting-list entry cancelled.");
        } else {
            System.out.println(
                    "Passenger ID not found.");
        }
    }

    static void displayReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No confirmed reservations.");
            return;
        }

        System.out.println("\n--- Confirmed Reservations ---");
        for (Passenger p : reservations) {
            p.display();
        }
    }

    static void displayWaitingList() {
        if (waitingList.isEmpty()) {
            System.out.println("Waiting list is empty.");
            return;
        }

        System.out.println("\n--- Waiting List (FIFO) ---");
        for (Passenger p : waitingList) {
            p.display();
        }
    }

    static void checkQueue() {
        System.out.println(
                "Waiting queue empty: " + waitingList.isEmpty());
        System.out.println(
                "Waiting queue full: " + (waitingList.size() >= 5));
        System.out.println(
                "Waiting passengers: " + waitingList.size());
        System.out.println(
                "Available seats: "
                + (TOTAL_SEATS - reservations.size()));
    }

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n===== RAILWAY RESERVATION SYSTEM =====");
            System.out.println("1. Book Ticket");
            System.out.println("2. Cancel Ticket");
            System.out.println("3. Display Confirmed Reservations");
            System.out.println("4. Display Waiting List");
            System.out.println("5. Check Queue Conditions");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
                continue;
            }

            switch (choice) {
                case 1:
                    bookTicket();
                    break;

                case 2:
                    cancelTicket();
                    break;

                case 3:
                    displayReservations();
                    break;

                case 4:
                    displayWaitingList();
                    break;

                case 5:
                    checkQueue();
                    break;

                case 6:
                    System.out.println("Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 6);

        sc.close();
    }
}
