package inl2shoes;

import java.util.Scanner;

public class MainMenu {

	String input;
	int choice = 0;
	int orderChoice;
	String yesOrNo;
	boolean bool = true;
	boolean bool2 = true;
	Repository rp = new Repository();
	Controller cr = new Controller();
	Scanner sc = new Scanner(System.in);

	MainMenu() {

		System.out.println(
				"1: Kundlista med best�llningsv�rde " + "\n2: Se lista av produkter per kategori " + "\n3: AddToCart ");

		while (bool) { // Meny val
			input = sc.nextLine();
			try {
				choice = Integer.parseInt(input);
				bool = false;
			} catch (NumberFormatException e) {
				bool = false;
			}
		}
		switch (choice) {

		case 1:
			showCustomerListMenu();
			break;

		case 2:
			displayCategoryList();
			break;

		case 3:
			addToCartMenu();
			break;

		default:
			System.out.println("V�lj en av de 3 alternativen.");
			break;
		}
	}

	public void displayCategoryList() {
		System.out.printf("%-11s | %-10s | %s\n", "Kategori", "Sko namn", "Antal");
		System.out.println("--------------------------------");
		for (Shoes s : rp.getProductsCategory()) {
			System.out.printf("%-11s | %-10s | %s\n", s.getCategoryName(), s.getShoeName(), s.getStorageQuantity());
		}
	}

	public void displayCustomerValueList(int id) {
		System.out.printf("%-7s | %-10s | %-12s\n", "F�rnamn", "Efternamn", "Total Summa");
		System.out.println("----------------------------------");
		for (Customer m : rp.getCustomerOrderAmount(id)) {
			System.out.printf("%-7s | %-10s | %-12s\n", m.getFirstName(), m.getLastName(), m.getTotalOrderAmount());
		}
	}

	public void displayCustomerList() {
		for (Customer m : rp.getCustomers()) {
			System.out.printf("%s %s\n", m.getFirstName(), m.getLastName());
		}
	}

	public void displayOrdersList(int customer) {
		System.out.printf("%-3s| %s\n", "Nr", "Skapades");
		System.out.println("-----------------------");
		// int counter = 1;
		for (Orders o : rp.getOrderListExpedited(customer)) {
			System.out.printf("%-3s| %s\n", o.getOrderListNr(), o.getCreated());
			// counter++;
		}
	}

	public void displayOrders() {
		for (Orders o : rp.getOrders()) {
			System.out.printf("%s %s %s %s\n", o.getId(), o.getCreated(), o.isExpedited(), o.getCustomerId());
		}
	}

	public void displayShoesList() {
		System.out.printf("%-10s | %-11s | %-7s | %-6s | %-7s | %s\n", "Namn", "M�rke", "Storlek", "F�rg", "Pris",
				"LagerAntal");
		System.out.println("---------------------------------------------------------");
		for (Shoes s : rp.getShoesInStock()) {
			System.out.printf("%-10s | %-11s | %-7s | %-6s | %-7s | %s\n", s.getShoeName(), s.getBrandName(),
					s.getSize(), s.getColor(), s.getPrice(), s.getStorageQuantity());
		}
	}

	public void showCustomerListMenu() {
		int customerId;

		while (bool2) {
			System.out.print("Ange kund id eller l�mna blankt: ");
			input = sc.nextLine().trim();

			if (input.matches("\\d+")) {
				customerId = Integer.parseInt(input);
				bool2 = false;
				System.out.println("");
				displayCustomerValueList(customerId);
			} else if (input.isEmpty()) {
				bool2 = false;
				System.out.println("");
				displayCustomerValueList(0);
			} else {
				System.out.println("\nAnv�nd siffror eller l�mna blankt\n");
			}
		}
	}

	public void addToCartMenu() {
		System.out.println("\nKundlista: \n");
		displayCustomerList();

		System.out.print("\nAnge namn p� kunden du vill anv�nda: ");
		input = sc.nextLine().trim();

		int trackCustomerId = cr.getCustomerId(input);

		if (cr.checkIfCustomerExist(trackCustomerId)) {
			System.out.println("Kunden finns inte.");
			System.exit(0);
		} else {
			System.out.println("\nProduktlista som finns p� lager: \n");
			displayShoesList();
			System.out.println("\nAnge namnet f�r den sko du vill l�gga till: \n");

			String trackShoeName = sc.nextLine().trim();
			int trackShoeId = cr.getShoeId(trackShoeName);

			if (cr.checkIfShoeExist(trackShoeId, trackShoeName)) {
				System.out.println("Skon finns inte.");
			} else {
				System.out.println("hur m�nga skor vill du l�gga till?");
				input = sc.nextLine().trim();
				int amountOfShoes = Integer.parseInt(input);
				// displayOrders(); //F�r att kolla ordrar under kodning

				if (cr.checkIfOrderExist(trackCustomerId)) {

					System.out.println("Finns ingen best�llning p� ditt namn");
					System.out.println("Vill du skapa en ny best�llning? ja/nej");
					yesOrNo = sc.next();

					if (yesOrNo.equalsIgnoreCase("ja")) {
						int newId = -2;
						System.out.println(rp.addToCart(trackCustomerId, newId, trackShoeId, amountOfShoes));
						System.exit(0);

					} else {
						System.out.println("Nej? ok d� st�nger vi ner.");
						System.exit(0);
					}

				} else {
					System.out.println("Finns icke-expedierad best�llning f�r ditt kund id");
					System.out.println("Vill du skapa en ny best�llning eller anv�nda en av de �ldre? ");
					System.out.println("[1] Ny order\n[2] Ej expiderad order\n");
					System.out.println("V�lj: ");
					int option = Integer.parseInt(sc.next());
					
					if (option == 1) {
						int newId = -2;
						System.out.println(rp.addToCart(trackCustomerId, newId, trackShoeId, amountOfShoes));
						System.exit(0);
					
					} else if (option == 2) {
						displayOrdersList(trackCustomerId);
						System.out.println("\nVilken order vill du anv�nda?");
						
						orderChoice = Integer.parseInt(sc.next());
						int trackOrderId = cr.getOrderId(orderChoice, trackCustomerId);

						if (cr.checkOrderChoice(orderChoice, trackCustomerId))
							System.out.println(rp.addToCart(trackCustomerId, trackOrderId, trackShoeId, amountOfShoes));
						else
							System.out.println("Du m�ste ange en siffra som st�mmer �verens med antal ordrar.");
					} else {
						System.out.println("Du m�ste ange 1 eller 2.");
						System.exit(0);
					}

				}
			}
		}
	}

	public static void main(String[] args) {

		MainMenu x = new MainMenu();
	}
}