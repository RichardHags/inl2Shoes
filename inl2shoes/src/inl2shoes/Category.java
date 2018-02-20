package inl2shoes;

public class Category {

	private int id;
	private String categoryName;
	private int shoe;
	
	Category(){
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public void setShoe(int shoe) {
		this.shoe = shoe;
	}
	
	public int getShoe() {
		return shoe;
	}
}
