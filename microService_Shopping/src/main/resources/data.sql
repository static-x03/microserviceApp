INSERT INTO tb_invoice (id, number_invoice, description, customer_id, create_at, status) VALUES(1, '0001', 'invoice office items', 1, NOW(),'CREATED');

INSERT INTO tb_invoice_item ( invoice_id, product_id, quanty, price ) VALUES(1, 1 , 1, 178.89);
INSERT INTO tb_invoice_item ( invoice_id, product_id, quanty, price)  VALUES(1, 2 , 2, 12.5);
INSERT INTO tb_invoice_item ( invoice_id, product_id, quanty, price)  VALUES(1, 3 , 1, 40.06);
