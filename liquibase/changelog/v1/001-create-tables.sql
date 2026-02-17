
--changeset andre:001
CREATE SCHEMA IF NOT EXISTS order_schema;
CREATE SCHEMA IF NOT EXISTS payment_schema;
CREATE SCHEMA IF NOT EXISTS inventory_schema;



CREATE TYPE order_status AS ENUM (
  'CREATED',
  'CONFIRMED',
  'CANCELLED'
);

CREATE TYPE payment_status AS ENUM (
  'PENDING',
  'APPROVED',
  'FAILED',
  'COMPENSATED'
);

CREATE TYPE inventory_status AS ENUM (
  'RESERVED',
  'RELEASED',
  'FAILED'
);



CREATE TABLE order_schema.orders
(
    id           UUID PRIMARY KEY,
    total_amount DECIMAL,
    status       order_status,
    created_at   TIMESTAMP,
    updated_at   TIMESTAMP
);

CREATE TABLE order_schema.order_items
(
    id         UUID PRIMARY KEY,
    order_id   UUID,
    product_id UUID,
    quantity   INT,
    unit_price DECIMAL,
    CONSTRAINT fk_order_items_order
        FOREIGN KEY (order_id)
            REFERENCES order_schema.orders (id)
);

CREATE TABLE order_schema.order_events
(
    id         UUID PRIMARY KEY,
    order_id   UUID,
    event_type VARCHAR,
    payload    JSON,
    created_at TIMESTAMP,
    CONSTRAINT fk_order_events_order
        FOREIGN KEY (order_id)
            REFERENCES order_schema.orders (id)
);

CREATE TABLE order_schema.orquestrator
(
    id       UUID PRIMARY KEY,
    order_id UUID,
    step     VARCHAR,
    CONSTRAINT fk_orquestrator_order
        FOREIGN KEY (order_id)
            REFERENCES order_schema.orders (id)
);


CREATE TABLE payment_schema.payments
(
    id         UUID PRIMARY KEY,
    order_id   UUID,
    amount     DECIMAL,
    status     payment_status,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE payment_schema.payment_events
(
    id         UUID PRIMARY KEY,
    payment_id UUID,
    event_type VARCHAR,
    payload    JSON,
    created_at TIMESTAMP,
    CONSTRAINT fk_payment_events_payment
        FOREIGN KEY (payment_id)
            REFERENCES payment_schema.payments (id)
);


CREATE TABLE inventory_schema.inventory
(
    product_id         UUID PRIMARY KEY,
    available_quantity INT,
    updated_at         TIMESTAMP
);

CREATE TABLE inventory_schema.inventory_reservations
(
    id         UUID PRIMARY KEY,
    order_id   UUID,
    product_id UUID,
    quantity   INT,
    status     inventory_status,
    created_at TIMESTAMP,
    is_active    BOOLEAN,
    CONSTRAINT fk_inventory_res_product
        FOREIGN KEY (product_id)
            REFERENCES inventory_schema.inventory (product_id)
);
COMMIT;
--changeset andre:002
ALTER TABLE inventory_schema.inventory ADD description VARCHAR(1000);
COMMIT;