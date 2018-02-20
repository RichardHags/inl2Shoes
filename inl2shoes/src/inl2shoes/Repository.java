package inl2shoes;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Repository {

	public String name;
	public String password;
	public String connectionString;
	Properties p = new Properties();

	public Repository() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try (FileInputStream file = new FileInputStream("src/inl2shoes/Settings.properties")) {
			p.load(file);
			name = p.getProperty("name");
			password = p.getProperty("password");
			connectionString = p.getProperty("connectionString");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Shoes> getProductsCategory() {
		List<Shoes> shoe = new ArrayList<>();
		String query = "select * from categoryView;";

		try (Connection con = DriverManager.getConnection(connectionString, name, password);
				PreparedStatement stmt = con.prepareStatement(query);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Shoes temp = new Shoes();
				temp.setCategoryName(rs.getString("categoryName"));
				temp.setShoeName(rs.getString("shoeName"));
				temp.setStorageQuantity(rs.getInt("storageQuantity"));
				shoe.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return shoe;
	}

	public List<Customer> getCustomerOrderAmount(int id) {
		List<Customer> customer = new ArrayList<>();
		String query = "select customer.id, customer.firstName, customer.lastName, sum(shoes.price * orderinfo.quantity) as totaltPris from customer\n"
				+ "inner join orders on orders.customerId = customer.id\n"
				+ "inner join orderinfo on orderInfo.ordersId = orders.id\n"
				+ "inner join shoes on orderInfo.shoesId = shoes.id\n";

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(connectionString, name, password);

			if (id == 0) {
				query += "group by customer.lastName\n" + "order by totaltPris desc;";
				stmt = con.prepareStatement(query);
				rs = stmt.executeQuery();
				while (rs.next()) {
					Customer temp = new Customer();
					temp.setId(rs.getInt("id"));
					temp.setFirstName(rs.getString("firstName"));
					temp.setLastName(rs.getString("lastName"));
					temp.setTotalOrderAmount(rs.getDouble("totaltPris"));
					customer.add(temp);
				}
			} else if (id > 0) {
				query += "where customer.id = ?";
				stmt = con.prepareStatement(query);
				stmt.setInt(1, id);
				rs = stmt.executeQuery();
				while (rs.next()) {
					Customer temp = new Customer();
					temp.setId(rs.getInt("id"));
					temp.setFirstName(rs.getString("firstName"));
					temp.setLastName(rs.getString("lastName"));
					temp.setTotalOrderAmount(rs.getDouble("totaltPris"));
					customer.add(temp);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}

	public List<Customer> getCustomers() {
		List<Customer> customer = new ArrayList<>();
		String query = "select * from customer;";

		try (Connection con = DriverManager.getConnection(connectionString, name, password);
				PreparedStatement stmt = con.prepareStatement(query);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Customer temp = new Customer();
				temp.setId(rs.getInt("id"));
				temp.setFirstName(rs.getString("firstName"));
				temp.setLastName(rs.getString("lastName"));
				customer.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}

	public List<Shoes> getShoesInStock() {
		String query = "select shoes.id, shoes.shoeName, brand.brandName, shoes.size, shoes.color, shoes.price, shoes.storageQuantity from shoes\n"
				+ "inner join brand on shoes.brandId = brand.id\n" + "having storageQuantity > 0;";
		List<Shoes> shoes = new ArrayList<>();

		try (Connection con = DriverManager.getConnection(connectionString, name, password);
				PreparedStatement stmt = con.prepareStatement(query);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Shoes temp = new Shoes();
				temp.setId(rs.getInt("id"));
				temp.setShoeName(rs.getString("shoeName"));
				temp.setBrandName(rs.getString("brandName"));
				temp.setSize(rs.getInt("size"));
				temp.setColor(rs.getString("color"));
				temp.setPrice(rs.getDouble("price"));
				temp.setStorageQuantity(rs.getInt("storageQuantity"));
				shoes.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return shoes;
	}

	public List<Orders> getOrderListExpedited(int customerId) {
		String query = "select * from orders\n" + "where expedited = 0 and customerId = ?;";
		List<Orders> order = new ArrayList<>();
		int listNr = 1;
		try (Connection con = DriverManager.getConnection(connectionString, name, password);
				PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.setInt(1, customerId);
			try (ResultSet rs = stmt.executeQuery()) {

				while (rs.next()) {

					Orders temp = new Orders();
					temp.setOrderListNr(listNr);
					temp.setId(rs.getInt("id"));
					temp.setCreated(rs.getDate("created"));
					temp.setExpedited(rs.getBoolean("expedited"));
					temp.setCustomerId(rs.getInt("customerId"));
					order.add(temp);
					listNr++;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return order;
	}

	public List<Orders> getOrder(int customerId) {
		String query = "select orderInfo.shoesId, orders.id, created, expedited, customerId from orderInfo\n"
				+ "inner join orders on orderInfo.ordersId = orders.id\n" + "where customerId = ?;";
		List<Orders> order = new ArrayList<>();

		try (Connection con = DriverManager.getConnection(connectionString, name, password);
				PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.setInt(1, customerId);
			try (ResultSet rs = stmt.executeQuery()) {

				while (rs.next()) {

					Orders temp = new Orders();
					temp.setId(rs.getInt("id"));
					temp.setCreated(rs.getDate("created"));
					temp.setExpedited(rs.getBoolean("expedited"));
					temp.setCustomerId(rs.getInt("customerId"));
					temp.setShoeId(rs.getInt("shoesId"));
					order.add(temp);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return order;
	}

	public List<Orders> getOrders() {
		String query = "select * from orders;";
		List<Orders> order = new ArrayList<>();

		try (Connection con = DriverManager.getConnection(connectionString, name, password);
				PreparedStatement stmt = con.prepareStatement(query);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {

				Orders temp = new Orders();
				temp.setId(rs.getInt("id"));
				temp.setCreated(rs.getDate("created"));
				temp.setExpedited(rs.getBoolean("expedited"));
				temp.setCustomerId(rs.getInt("customerId"));
				order.add(temp);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return order;
	}

	public List<Shoes> getShoeByName(String shoeName) {
		String query = "select * from shoes where shoeName like ?";
		List<Shoes> shoes = new ArrayList<>();

		try (Connection con = DriverManager.getConnection(connectionString, name, password);
				PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.setString(1, shoeName);
			try (ResultSet rs = stmt.executeQuery()) {

				while (rs.next()) {
					Shoes temp = new Shoes();
					temp.setId(rs.getInt("id"));
					temp.setShoeName(rs.getString("shoeName"));
					temp.setBrandName(rs.getString("brandId"));
					temp.setSize(rs.getInt("size"));
					temp.setColor(rs.getString("color"));
					temp.setPrice(rs.getDouble("price"));
					temp.setStorageQuantity(rs.getInt("storageQuantity"));
					shoes.add(temp);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return shoes;
	}

	public String addToCart(int IDcustomer, int IDorders, int IDshoes, int shoesInCart) {
		String query = "call addToCart(?, ?, ?, ?)";
		int updatedRows = 0;

		try (Connection con = DriverManager.getConnection(connectionString, name, password);
				CallableStatement stmt = con.prepareCall(query)) {

			stmt.setInt(1, IDcustomer);
			stmt.setInt(2, IDorders);
			stmt.setInt(3, IDshoes);
			stmt.setInt(4, shoesInCart);
			updatedRows = stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (updatedRows <= 0)
			return "Nu gick något fel, beställningen slutfördes aldrig.";
		else
			return "Beställningen är gjord!";
	}

}