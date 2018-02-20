use inl1shoes;
DROP TRIGGER IF EXISTS outOfStock

DELIMITER //
CREATE TRIGGER outOfStock
AFTER UPDATE ON shoes
FOR EACH ROW
BEGIN

IF (NEW.storageQuantity = 0) 
THEN
	
    INSERT INTO OutOfStock (outOfStockDate, shoesOldId) VALUES (current_date(), NEW.id);

END IF;
END //
DELIMITER ;

