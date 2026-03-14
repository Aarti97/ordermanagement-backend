-- STATUS MASTER
INSERT IGNORE INTO status(status_id, status_name)
VALUES (1, 'PENDING');

INSERT IGNORE INTO status(status_id, status_name)
VALUES (2, 'PROCESSING');

INSERT IGNORE INTO status(status_id, status_name)
VALUES (3, 'COMPLETED');




-- PAYMENT MODE MASTER
INSERT IGNORE INTO payment_mode (id, mode)
VALUES (1, 'Unpaid');
INSERT IGNORE INTO payment_mode (id, mode)
VALUES (2, 'Cash');
INSERT IGNORE INTO payment_mode (id, mode)
VALUES (3, 'Gpay');