DROP PROCEDURE IF EXISTS AddToCart;
DELIMITER //
CREATE PROCEDURE AddToCart (IN IDcustomer INT, IN IDorders INT, IN IDshoes INT, IN shoesInCart INT)
BEGIN

	DECLARE shoesInStock INT;
    
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		ROLLBACK;
	END;
    
    DECLARE EXIT HANDLER FOR SQLWARNING 
    BEGIN
          ROLLBACK;
          select ('SQLWARNING occurred, rollback done');
    END;
    
	DECLARE EXIT HANDLER FOR 1062
	begin
		ROLLBACK;
	 	select ('constraint broken, rollback done') as error;
	END;

	SELECT storageQuantity INTO shoesInStock FROM shoes
    WHERE id = IDshoes;
	
-- kollar först så att det finns tillräckligt med skor

    IF (shoesInStock >= shoesInCart)
	THEN

-- Om beställningen inte finns eller om vi skickar in null som beställningsid ska en ny beställning skapas 
-- och produkten läggas till i den.
	
		IF (IDorders NOT IN (SELECT id FROM orders) OR IDorders IS NULL) 
		THEN
    
		START TRANSACTION;
    
		INSERT INTO orders (created, expedited, customerId) VALUES (current_date(), false, IDcustomer);
		INSERT INTO orderInfo (quantity, ordersId, shoesId) VALUES (shoesInCart, last_insert_id(), IDshoes);
				
		COMMIT;
-- Om beställningen redan finns ska produkten läggas till i beställningen.

		ELSEIF (IDshoes NOT IN (SELECT shoesId FROM orderInfo WHERE ordersId = IDorders)) 
		THEN
	
		INSERT INTO orderInfo (quantity, ordersId, shoesId) VALUES (shoesInCart, IDorders, IDshoes);

-- Om beställningen finns och produkten redan finns i den ska vi lägga till 
-- ytterligare ett exemplar av produkten i beställningen.

		ELSE

			UPDATE orderInfo 
			SET quantity = quantity + shoesInCart 
			WHERE shoesId = IDshoes 
			AND ordersId = IDorders;


		END IF;
-- uppdaterar antal skor i tabellen
		UPDATE shoes
        SET storageQuantity = storageQuantity - shoesInCart
        WHERE id = IDshoes;
	
-- felmedelande ifall det inte finns tillräckligt med skor i lager
	ELSE

        SELECT 'There is not enough in stock' AS ERROR;
		
	END IF;

END //
DELIMITER ;