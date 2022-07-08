INSERT INTO items (item_name, item_cost, number_in_stock)
VALUES
    ('doughnut', 1.5, 10),
    ('muffin', 2, 20),
    ('cupcake', 1.5, 25),
    ('cookie', 1, 30)
;

INSERT INTO customers (first_name, surname)
VALUES
    ('jordan', 'harrison'),
    ('janie', 'jones'),
    ('jimmy', 'jazz'),
    ('rudy', 'cafail')
;

INSERT INTO orders (customer_id, total_cost)
VALUES
    (1, 5),
    (2, 4)
;

INSERT INTO order_basket(order_id, item_id)
VALUES
    (1, 1),
    (1, 1),
    (1, 2),
    (2, 2),
    (2, 2)
;