--changeset andre:004
ALTER TABLE order_schema.orders
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE order_schema.order_items
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE order_schema.order_events
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE order_schema.orquestrator
    ALTER COLUMN id SET DEFAULT gen_random_uuid();


ALTER TABLE payment_schema.payments
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE payment_schema.payment_events
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE inventory_schema.inventory_reservations
    ALTER COLUMN id SET DEFAULT gen_random_uuid();

ALTER TABLE inventory_schema.inventory
    ALTER COLUMN product_id SET DEFAULT gen_random_uuid();

ALTER TABLE inventory_schema.inventory
    ADD COLUMN price numeric(12, 2) NOT NULL;

--changeset andre:004
INSERT INTO inventory_schema.inventory
(available_quantity, updated_at, description, price)
VALUES
    (
        120,
        now(),
        'Notebook Dell Inspiron 15, Intel i5, 16GB RAM, SSD 512GB',
        3499.90
    ),
    (
        80,
        now(),
        'Mouse Logitech MX Master 3 sem fio',
        599.90
    ),
    (
        40,
        now(),
        'Teclado mecânico Keychron K6 RGB, switches brown',
        699.90
    ),
    (
        60,
        now(),
        'Monitor LG UltraWide 29", IPS, Full HD',
        1299.90
    ),
    (
        35,
        now(),
        'Headset HyperX Cloud II, som surround 7.1',
        499.90
    ),
    (
        25,
        now(),
        'Cadeira Gamer DT3 Sports Elite, ajuste lombar',
        1199.90
    ),
    (
        90,
        now(),
        'SSD Kingston NV2 1TB NVMe M.2',
        399.90
    ),
    (
        150,
        now(),
        'Mousepad SteelSeries QcK Large',
        119.90
    ),
    (
        20,
        now(),
        'Placa de vídeo NVIDIA RTX 4060 8GB',
        2399.90
    ),
    (
        45,
        now(),
        'Fonte Corsair 750W 80 Plus Gold',
        699.90
    ),
    (
        70,
        now(),
        'Teclado Logitech G Pro X mecânico hot-swappable',
        899.90
    ),
    (
        55,
        now(),
        'Webcam Logitech C920 Full HD',
        429.90
    ),
    (
        65,
        now(),
        'Microfone USB Blue Yeti',
        699.90
    ),
    (
        85,
        now(),
        'Hub USB-C 7 em 1',
        219.90
    ),
    (
        30,
        now(),
        'HD externo Seagate 2TB USB 3.0',
        479.90
    ),
    (
        40,
        now(),
        'Roteador Wi-Fi 6 AX1800',
        349.90
    ),
    (
        75,
        now(),
        'Cabo HDMI 2.1 2m',
        59.90
    ),
    (
        110,
        now(),
        'Carregador USB-C 65W',
        149.90
    ),
    (
        95,
        now(),
        'Adaptador USB-C para Ethernet',
        129.90
    ),
    (
        50,
        now(),
        'Base para notebook com cooler',
        199.90
    ),
    (
        28,
        now(),
        'Memoria RAM DDR4 16GB 3200MHz',
        229.90
    ),
    (
        26,
        now(),
        'Memoria RAM DDR4 32GB 3200MHz',
        399.90
    ),
    (
        18,
        now(),
        'Placa-mae B550 ATX',
        899.90
    ),
    (
        22,
        now(),
        'Processador AMD Ryzen 5 5600',
        899.90
    ),
    (
        15,
        now(),
        'Processador Intel i7 12700K',
        1899.90
    ),
    (
        12,
        now(),
        'Cooler de CPU torre 120mm',
        179.90
    ),
    (
        33,
        now(),
        'Gabinete mid tower com vidro',
        359.90
    ),
    (
        44,
        now(),
        'Fonte 600W 80 Plus Bronze',
        299.90
    ),
    (
        60,
        now(),
        'SSD SATA 480GB',
        199.90
    ),
    (
        48,
        now(),
        'SSD NVMe 2TB',
        899.90
    ),
    (
        70,
        now(),
        'Mouse gamer 12000 DPI',
        159.90
    ),
    (
        66,
        now(),
        'Teclado sem fio compacto',
        179.90
    ),
    (
        52,
        now(),
        'Suporte articulado para monitor',
        249.90
    ),
    (
        24,
        now(),
        'Switch Ethernet 8 portas gigabit',
        229.90
    ),
    (
        36,
        now(),
        'Fone bluetooth in-ear',
        149.90
    ),
    (
        32,
        now(),
        'Speaker bluetooth compacto',
        199.90
    ),
    (
        27,
        now(),
        'Tablet grafico pequeno',
        299.90
    ),
    (
        21,
        now(),
        'Leitor de cartao SD USB 3.0',
        49.90
    ),
    (
        19,
        now(),
        'Estabilizador 1000VA',
        329.90
    ),
    (
        58,
        now(),
        'Filtro de linha 6 tomadas',
        79.90
    );
COMMIT;