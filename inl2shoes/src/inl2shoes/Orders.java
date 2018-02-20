package inl2shoes;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Orders {

	private int id;
	private Date created;
	private List<Customer> customer = new ArrayList<>();
	private Customer cust;
	private boolean expedited;
	private int customerId;
	public int shoeId;
	public int orderListNr;

	Orders() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public List<Customer> getCustomer() {
		return customer;
	}

	public void setCustomer(List<Customer> customer) {
		this.customer = customer;
	}

	public Customer getCust(String string) {
		return cust;
	}

	public void setCust(Customer cust) {
		this.cust = cust;
	}

	public boolean isExpedited() {
		return expedited;
	}

	public void setExpedited(boolean expedited) {
		this.expedited = expedited;
	}

	public int getShoeId() {
		return shoeId;
	}

	public void setShoeId(int shoeId) {
		this.shoeId = shoeId;
	}

	public int getOrderListNr() {
		return orderListNr;
	}

	public void setOrderListNr(int orderListNr) {
		this.orderListNr = orderListNr;
	}
	

}
