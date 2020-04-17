CREATE TABLE IF NOT EXISTS vendors
(
    id      BIGSERIAL PRIMARY KEY,
    name    VARCHAR(50) UNIQUE NOT NULL,
    country VARCHAR(30)        NOT NULL
);

CREATE TABLE IF NOT EXISTS categories
(
    id        BIGSERIAL PRIMARY KEY,
    name      VARCHAR(50) NOT NULL,
    parent_id BIGINT REFERENCES categories (id),
    UNIQUE (name, parent_id)
);

CREATE TABLE IF NOT EXISTS products
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(50)                       NOT NULL,
    price       NUMERIC(15, 2)                    NOT NULL,
    category_id BIGINT REFERENCES categories (id) NOT NULL,
    vendor_id   BIGINT REFERENCES vendors (id)    NOT NULL,
    UNIQUE (name, category_id, vendor_id)
);

CREATE TABLE IF NOT EXISTS users
(
    id         BIGSERIAL PRIMARY KEY,
    username   VARCHAR(50) UNIQUE NOT NULL,
    first_name VARCHAR(50)        NOT NULL,
    last_name  VARCHAR(50)        NOT NULL,
    password   VARCHAR(80)        NOT NULL,
    email      VARCHAR(50) UNIQUE NOT NULL,
    created_at DATE               NOT NULL,
    status     VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS user_role
(
    roles   VARCHAR(30) NOT NULL,
    user_id BIGINT      NOT NULL,
    UNIQUE (user_id, roles)
);

CREATE TABLE IF NOT EXISTS orders
(
    id           BIGSERIAL PRIMARY KEY,
    created_at   DATE                         NOT NULL,
    total_amount NUMERIC(15, 2)               NOT NULL,
    user_id      BIGINT REFERENCES users (id) NOT NULL
);
CREATE TABLE IF NOT EXISTS order_entries
(
    id               BIGSERIAL PRIMARY KEY,
    product_quantity INT                             NOT NULL,
    price            NUMERIC(15, 2)                  NOT NULL,
    total            NUMERIC(15, 2)                  NOT NULL,
    product_id       BIGINT REFERENCES products (id) NOT NULL,
    order_id         BIGINT REFERENCES orders (id)   NOT NULL
);

CREATE TABLE IF NOT EXISTS cart_entries
(
    product_quantity INT                             NOT NULL,
    product_id       BIGINT REFERENCES products (id) NOT NULL,
    user_id          BIGINT REFERENCES users (id)    NOT NULL,
    UNIQUE (product_id, user_id)
);


CREATE INDEX idx_name_categories ON categories (lower(name));
CREATE INDEX idx_name_products ON products (lower(name));
CREATE INDEX idx_name_category_id_products ON products (category_id, lower(name));
CREATE INDEX idx_user_id_orders ON orders (user_id);
CREATE INDEX idx_order_id_order_entries ON order_entries (order_id);