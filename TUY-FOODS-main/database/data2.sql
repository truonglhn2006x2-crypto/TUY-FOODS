CREATE DATABASE food_order
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;
USE food_order;

SELECT DATABASE();
SHOW TABLES;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20),
    address VARCHAR(255),
    member_level_id BIGINT NULL,
    points INT DEFAULT 0,
    role ENUM('CUSTOMER', 'ADMIN') DEFAULT 'CUSTOMER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    image VARCHAR(500)
);

CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    description TEXT,
    price DECIMAL(12,2) NOT NULL,
    category_id BIGINT,
    image VARCHAR(500),
    rating DECIMAL(2,1) DEFAULT 0,
    available BOOLEAN DEFAULT TRUE,

    FOREIGN KEY (category_id)
        REFERENCES categories(id)
        ON DELETE SET NULL
);

CREATE TABLE carts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    total_amount DECIMAL(12,2) DEFAULT 0,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

CREATE TABLE cart_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cart_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    price DECIMAL(12,2) NOT NULL,

    FOREIGN KEY (cart_id)
        REFERENCES carts(id)
        ON DELETE CASCADE,

    FOREIGN KEY (product_id)
        REFERENCES products(id)
);

CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    total_amount DECIMAL(12,2) NOT NULL,
    status ENUM(
        'PENDING',
        'CONFIRMED',
        'PREPARING',
        'DELIVERING',
        'COMPLETED',
        'CANCELLED'
    ) DEFAULT 'PENDING',
    delivery_address VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    payment_method ENUM(
        'COD',
        'ONLINE'
    ) DEFAULT 'COD',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id)
        REFERENCES users(id)
);

CREATE TABLE order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT,
    product_name VARCHAR(150) NOT NULL,
    price DECIMAL(12,2) NOT NULL,
    quantity INT NOT NULL,

    FOREIGN KEY (order_id)
        REFERENCES orders(id)
        ON DELETE CASCADE,

    FOREIGN KEY (product_id)
        REFERENCES products(id)
        ON DELETE SET NULL
);

CREATE TABLE custom_products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name VARCHAR(150) NOT NULL,
    base_product_id BIGINT,
    total_price DECIMAL(12,2) NOT NULL,
    likes INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id)
        REFERENCES users(id),

    FOREIGN KEY (base_product_id)
        REFERENCES products(id)
        ON DELETE SET NULL
);

CREATE TABLE custom_options (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    value VARCHAR(100) NOT NULL,
    extra_price DECIMAL(12,2) DEFAULT 0
);

CREATE TABLE custom_product_options (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    custom_product_id BIGINT NOT NULL,
    option_id BIGINT NOT NULL,

    FOREIGN KEY (custom_product_id)
        REFERENCES custom_products(id)
        ON DELETE CASCADE,

    FOREIGN KEY (option_id)
        REFERENCES custom_options(id)
        ON DELETE CASCADE
);

CREATE TABLE member_levels (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    min_points INT DEFAULT 0,
    benefits TEXT
);

INSERT INTO member_levels
(name, min_points, benefits)
VALUES
('Member', 0, 'Thành viên thường'),
('Veteran', 500, 'Giảm 5%'),
('VIP', 1000, 'Giảm 10%');

ALTER TABLE users
ADD CONSTRAINT fk_user_member_level
FOREIGN KEY (member_level_id)
REFERENCES member_levels(id);

CREATE TABLE vouchers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    discount DECIMAL(12,2) NOT NULL,
    min_order DECIMAL(12,2) DEFAULT 0,
    start_date DATETIME,
    end_date DATETIME,
    quantity INT DEFAULT 0,
    status BOOLEAN DEFAULT TRUE
);

CREATE TABLE mini_games (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    reward INT DEFAULT 0,
    status BOOLEAN DEFAULT TRUE
);

CREATE TABLE game_results (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    game_id BIGINT NOT NULL,
    score INT DEFAULT 0,
    reward INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id)
        REFERENCES users(id),

    FOREIGN KEY (game_id)
        REFERENCES mini_games(id)
);
