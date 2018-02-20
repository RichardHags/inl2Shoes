package inl2shoes;

import java.sql.Date;

public class OutOfStock {

	private int id;
	private Date outOfStockDate;
	private int shoesOldId;
	
	OutOfStock(){
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getOutOfStockDate() {
		return outOfStockDate;
	}

	public void setOutOfStockDate(Date outOfStockDate) {
		this.outOfStockDate = outOfStockDate;
	}

	public int getShoesOldId() {
		return shoesOldId;
	}

	public void setShoesOldId(int shoesOldId) {
		this.shoesOldId = shoesOldId;
	}
	
	
}
