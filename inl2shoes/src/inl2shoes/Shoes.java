package inl2shoes;

import java.util.ArrayList;
import java.util.List;

public class Shoes {

	private int id;
	private int size;
	private String color;
	private double price;
	private int storageQuantity;
	private List<Brand> brand = new ArrayList<>();
	private String brandName;
	private String shoeName;
	private String categoryName;


	Shoes(){}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getStorageQuantity() {
		return storageQuantity;
	}

	public void setStorageQuantity(int storageQuantity) {
		this.storageQuantity = storageQuantity;
	}

	public List<Brand> getBrand() {
		return brand;
	}

	public void setBrand(List<Brand> brand) {
		this.brand = brand;
	}
	
	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getShoeName() {
		return shoeName;
	}

	public void setShoeName(String shoeName) {
		this.shoeName = shoeName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	
}
