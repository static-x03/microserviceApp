insert into tb_category(id,nombre) values(
1,'Shoes' );
insert into tb_category(id,nombre) values(
2,'Books');
insert into tb_category(id,nombre) values(
3,'Electronics');

insert into tb_product(id,name,description,stock,price,status,created_at,category_id)
values(1,'Adidas Clod foam Ultimate','walk in the air Black',100,300,'Created','2021-03-04',1);
insert into tb_product(id,name,description,stock,price,status,created_at,category_id)
values(2,'Mio Cid','Book Adventure ',100,50,'Created','2021-03-05',1);
insert into tb_product(id,name,description,stock,price,status,created_at,category_id)
values(3,'Diadema','Sony Black',100,200,'Created','2021-03-04',2);