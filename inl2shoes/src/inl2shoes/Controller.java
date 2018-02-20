package inl2shoes;

import java.util.List;

public class Controller {

	Repository repo = new Repository();

	Controller() {
	}

	public int getOrderId(int choice, int customerId) {
		int ordersId = 0;
		List<Orders> o = repo.getOrderListExpedited(customerId);
		for (int i = 0; i < o.size(); i++) {
			if (choice == o.get(i).getOrderListNr()) {
				ordersId = o.get(i).getId();
			}
		}
		return ordersId;
	}

	public int getShoeId(String shoeName) {
		int id = 0;
		for (Shoes s : repo.getShoeByName(shoeName)) {
			if (s.getShoeName().equalsIgnoreCase(shoeName)) {
				id = s.getId();
			}
		}
		return id;
	}

	public int getCustomerId(String inputFromUser) {
		int id = 0;
		for (Customer m : repo.getCustomers()) {
			if (m.getFirstName().equalsIgnoreCase(inputFromUser)) {
				id = m.getId();
			}
		}
		return id;
	}

	public boolean checkIfCustomerExist(int cus) {
		boolean exist = true;
		for (Customer c : repo.getCustomers()) {
			if (c.getId() == cus)
				exist = false;
		}
		return exist;
	}

	public boolean checkIfOrderExist(int customerId) {
		boolean exist = true;
		for (Orders o : repo.getOrders()) {
			if (o.getCustomerId() == customerId) {
				exist = false;
			}
		}
		return exist;
	}

	public boolean checkIfShoeExist(int shoeId, String shoeName) {
		boolean exist = true;
		int id = 0;
		for (Shoes s : repo.getShoeByName(shoeName)) {
			if (s.getShoeName().equalsIgnoreCase(shoeName)) {
				id = s.getId();
			}
			if (s.getId() == id)
				exist = false;
		}
		return exist;
	}
	
	public boolean checkOrderChoice(int choice, int customerId) {
		boolean check = true;
		List<Orders> o = repo.getOrderListExpedited(customerId);
		for (int i = 0; i < o.size(); i++) {
			if (choice > o.size() || choice <= -1) {
				check = false;
			}
		}
		return check;
	}

}
