CREATE USER 'ynallinpay'@'localhost' IDENTIFIED BY 'yntl12345';
GRANT ALL PRIVILEGES ON ynallinpayboot.* TO 'ynallinpay'@'localhost';
FLUSH PRIVILEGES;