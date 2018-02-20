package inl2shoes;

import java.util.ArrayList;
import java.util.List;

public class OrderInfo {

	private int id;
	private int quantity;
	private List<Orders> orders = new ArrayList<>();
	private List<Shoes> shoes = new ArrayList<>();
	
	OrderInfo (){}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public List<Orders> getOrders() {
		return orders;
	}

	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}

	public List<Shoes> getShoes() {
		return shoes;
	}

	public void setShoes(List<Shoes> shoes) {
		this.shoes = shoes;
	}
	
	
}
