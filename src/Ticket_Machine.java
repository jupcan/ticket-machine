// Ticket Machine - Made by Christopher Santos Diaz and Juan Perea Campos

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;
public class Ticket_Machine {
	public static void main(String[] args) {
		final double TICKET_PRICE = Double.parseDouble(args[0]); // Gets ticket prize from command line
		boolean[][] seats = new boolean[10][4];
		int menu_option;

		for (int i = 0; i<seats.length; i++)
			for (int j = 0; j<seats[i].length; j++)
				seats[i][j]=false;

		Scanner scan=new Scanner(System.in);
		scan.useLocale(Locale.US);
		System.out.println("Welcome to our ticket machine!");

		do {
			System.out.println("Choose an option: \n1.Buy Tickets \n2.Cancel Ticket Purchase \n3.Show Seats Occupancy \n4.Exit");
			menu_option=scan.nextInt();
			// Menu
			switch (menu_option) {
			case 1:
				// Shows different buying options
				System.out.println("1.Buy Tickets Automatically \n2.Buy Tickets Manually \n3.Buy Tickets by Type");
				menu_option=scan.nextInt();
				switch (menu_option) {
				case 1:
					seats = buy_automatically(seats, TICKET_PRICE);
					break;
				case 2:
					seats = buy_manually(seats, TICKET_PRICE);
					break;
				case 3:
					seats = buy_type(seats, TICKET_PRICE);
					break;
				}
				break;
			case 2:
				seats = cancel(seats);
				break;
			case 3:
				show(seats);
				break;
			case 4:
				System.out.println("Thanks for using our ticket machine. Hope to see you again soon!");
				break;
			} // End switch Menu
		} while (menu_option!=4);
		scan.close();
	} // End main	

	public static boolean[][] buy_automatically(boolean[][] seats, double TICKET_PRICE) { // Allows us to buy tickets automatically
		int number_of_tickets = 0, number_of_free_seats = checkFreeSeats(seats), number_of_assigned_seats=0;
		double ticket_price_final;

		Scanner scan=new Scanner(System.in);
		scan.useLocale(Locale.US);

		if (number_of_free_seats==0)
			System.out.println("We're sorry, no more tickets available to buy.");
		else {
			System.out.println("How many tickets do you want to buy?");
			number_of_tickets=scan.nextInt();
			ticket_price_final = TICKET_PRICE*number_of_tickets*0.8; // Calculate the final price of the tickets

			// Loop if fail number
			while (number_of_tickets<0 || number_of_tickets>number_of_free_seats) {
				System.out.println("Please insert a number greater than 0 and lower or equal than " + number_of_free_seats);
				number_of_tickets=scan.nextInt();
			}

			for (int i = 0; i<seats.length; i++) {
				if (number_of_assigned_seats==number_of_tickets)
					break;
				for (int j = 0; j<seats[i].length; j++) {
					if (number_of_assigned_seats==number_of_tickets)
						break;
					if (seats[i][j]==false) {
						seats[i][j]=true;
						number_of_assigned_seats++;
					}
				}
			}

			System.out.printf(Locale.US, "Total price of %d tickets is $%.2f \n",number_of_tickets,ticket_price_final);
			show(seats);
			String content = "Total price of %d tickets is $%.2f \n";
			writeFile(content);
		}

		// scan.close();
		return seats;
	}

	public static boolean[][] buy_manually(boolean[][] seats, double TICKET_PRICE) { // Allows us to buy tickets manually
		String columnChoose;
		int row, column, number_of_tickets = 0, number_of_free_seats = checkFreeSeats(seats);
		double ticket_price_final;

		Scanner scan=new Scanner(System.in);
		scan.useLocale(Locale.US);

		if (number_of_free_seats==0)
			System.out.println("We're sorry, no more tickets available to buy.");
		else {
			System.out.println("How many tickets do you want to buy?");
			number_of_tickets=scan.nextInt();
			ticket_price_final = TICKET_PRICE*number_of_tickets*0.8; // Calculate the final price of the tickets
			// Loop if fail number
			while (number_of_tickets<0 || number_of_tickets>number_of_free_seats) {
				System.out.println("Please insert a number greater than 0 and lower or equal than " + number_of_free_seats);
				number_of_tickets=scan.nextInt();
			}
			System.out.printf(Locale.US, "Total price of %d tickets is $%.2f \n",number_of_tickets,ticket_price_final);

			do {
				show(seats);
				System.out.println("Select the seat you want to buy.");

				do {
					System.out.println("Enter the row (0-9):"); // Ask for the row of the seat to buy
					row=scan.nextInt();
				} while (row<0 || row>9);
				scan.nextLine();

				do {
					System.out.println("Enter the column (a, b, c or d):"); // Ask for the column of the seat to buy
					columnChoose=scan.nextLine();

					switch (columnChoose) {
					case "a":
						column=0;
						break;
					case "b":
						column=1;
						break;
					case "c":
						column=2;
						break;
					case "d":
						column=3;
						break;
					default:
						System.out.println("The column is not correct.");
						column=-1;
						break;
					} // End switch cancel
				} while(column<0 || column>3);

				if (seats[row][column]==false) {
					System.out.println("Seat bought successfully!");
					seats[row][column]=true;
					number_of_tickets --;
				} else
					System.out.println("This seat is ocuppied.");
			} while (number_of_tickets!=0);
		}
		// scan.close();
		return seats;
	}

	public static boolean[][] buy_type(boolean[][] seats, double TICKET_PRICE) { // Allows us to buy tickets manually selecting the type
		int number_of_tickets = 0, number_of_free_seats = checkFreeSeats(seats), typeChoose;
		double ticket_price_final;

		Scanner scan=new Scanner(System.in);
		scan.useLocale(Locale.US);

		if (number_of_free_seats==0)
			System.out.println("We're sorry, no more tickets available to buy.");
		else {
			System.out.println("How many tickets do you want to buy?");
			number_of_tickets=scan.nextInt();
			ticket_price_final = TICKET_PRICE*number_of_tickets*0.8; // Calculate the final price of the tickets
			// Loop if fail number
			while (number_of_tickets<0 || number_of_tickets>20) {
				System.out.println("Please insert a number greater than 0 and lower or equal than 20.");
				number_of_tickets=scan.nextInt();
			}
			do {
				System.out.printf(Locale.US, "Total price of %d tickets is $%.2f \n",number_of_tickets,ticket_price_final);
				System.out.println("Select the type of seat/s you want to buy:");
				System.out.println("1.Window Seat \n2.Aisle Seat"); // Ask for the type of seat to buy
				typeChoose=scan.nextInt();

				switch (typeChoose) {
				case 1:
					for (int i = 0; i<seats.length; i++) {
						for (int j = 0; j<seats[i].length; j++) {
							if (seats [i][0]==false && number_of_tickets>0) {
								seats [i][0]=true;
								number_of_tickets--;
							} else if (seats [i][3]==false && number_of_tickets>0) {
								seats [i][3]=true;
								number_of_tickets--;
							}
						}
					}
					show(seats);
					break;
				case 2:
					for (int i = 0; i<seats.length; i++) {
						for (int j = 0; j<seats[i].length; j++) {
							if (seats [i][1]==false && number_of_tickets>0) {
								seats [i][1]=true;
								number_of_tickets--;
							} else if (seats [i][2]==false && number_of_tickets>0) {
								seats [i][2]=true;
								number_of_tickets--;
							} 
						}
					}
					show(seats);
					break;
				} // End switch type of seat
			} while(number_of_tickets!=0);
		} 
		// scan.close();
		return seats;
	}

	public static boolean[][] cancel(boolean[][] seats) { // Allows us to cancel tickets
		String cancel_more, columnChoose;
		int row, column;

		Scanner scan=new Scanner(System.in);
		scan.useLocale(Locale.US);

		if (checkFreeSeats(seats)!=40)
			do {
				show(seats);
				System.out.println("Which seat do you want to cancel?");

				do {
					System.out.println("Enter the row (0-9):"); // Ask for the row of the seat to cancel
					row=scan.nextInt();
				} while (row<0 || row>9);
				scan.nextLine();

				do {
					System.out.println("Enter the column (a, b, c or d):"); // Ask for the column of the seat to cancel
					columnChoose=scan.nextLine();

					switch (columnChoose) {
					case "a":
						column=0;
						break;
					case "b":
						column=1;
						break;
					case "c":
						column=2;
						break;
					case "d":
						column=3;
						break;
					default:
						System.out.println("The column is not correct.");
						column=-1;
						break;
					} // End switch cancel
				} while(column<0 || column>3);

				if (seats[row][column]==true) {
					System.out.println("Seat cancelled successfully!");
					seats[row][column]=false;
				} else
					System.out.println("This seat is not ocuppied.");

				System.out.println("Do you want to cancel another one?");
				cancel_more = scan.nextLine();
			} while (checkFreeSeats(seats)!=40 && cancel_more.equalsIgnoreCase("Yes"));

		// scan.close();
		return seats;
	}

	public static void show(boolean[][] seats) { // Shows seats occupancy
		System.out.println("Seats occupation (true=occupied & false=free)\n");

		System.out.print("\ta\tb\tc\td");
		for (int i = 0; i<seats.length; i++) {
			System.out.print("\n" + i);
			for (int j = 0; j<seats[i].length; j++)
				System.out.print("\t" + seats[i][j]);
		}
		System.out.println("\n");
	}

	public static int checkFreeSeats(boolean[][] seats) { // Check free seats available
		int number_of_free_seats=0;

		for (int i = 0; i<seats.length; i++)
			for (int j = 0; j<seats[i].length; j++)
				if (seats[i][j]==false)
					number_of_free_seats++;

		return number_of_free_seats;
	}

	public static void writeFile(String content) { // Writes seats occupancy on a text file
		FileWriter fichero = null;
		PrintWriter pw = null;

		try {
			fichero = new FileWriter("./ticket.txt", true);
			pw = new PrintWriter(fichero);
			pw.println(content);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null!=fichero)
					fichero.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
} // End class