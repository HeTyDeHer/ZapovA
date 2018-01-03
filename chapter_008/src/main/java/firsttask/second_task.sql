
-- 1. Написать запрос получение всех продуктов с типом "СЫР"

SELECT * FROM products WHERE type_id = (SELECT id FROM type WHERE name = 'СЫР');

-- 2. Написать запрос получения всех продуктов, у кого в имени есть слово "мороженное"

SELECT * FROM products WHERE name like '%мороженное%';

-- 3. Написать запрос, который выводит все продукты, срок годности которых заканчивается в следующем месяце.

SELECT * FROM products WHERE expired_date BETWEEN now() AND (now() + INTERVAL '1 month');

-- или

SELECT * FROM products WHERE expired_date BETWEEN (date_trunc('month', now()) + '1 month') AND (date_trunc('month', now()) + '2 month');

-- 4. Написать запрос, который вывод самый дорогой продукт.

SELECT * FROM products WHERE price = (SELECT max(price) FROM products);

-- 5. Написать запрос, который выводит количество всех продуктов определенного типа.

SELECT SUM(quantity) FROM products WHERE type_id = (SELECT id FROM type WHERE name = 'TYPE_NAME');

-- 6. Написать запрос получение всех продуктов с типом "СЫР" и "МОЛОКО"

SELECT * FROM products WHERE type_id = (SELECT id FROM type WHERE name = 'СЫР') OR type_id = (SELECT id FROM type WHERE name = 'МОЛОКО');

-- 7. Написать запрос, который выводит тип продуктов, которых осталось меньше 10 штук.  

SELECT * FROM products WHERE quantity <= 10;

-- 8. Вывести все продукты и их тип.

SELECT type.name, products.id, products.name, products.expired_date, products.price, products.quantity
	FROM products 
    FULL OUTER JOIN type ON products.type_id = type.id;