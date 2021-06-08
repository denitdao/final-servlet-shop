create table if not exists users
(
    id          int auto_increment
        primary key,
    first_name  varchar(20) not null,
    second_name varchar(20) not null,
    login       varchar(40) not null,
    password    varchar(65) not null,
    role        varchar(10) not null,
    created_at  datetime    not null,
    updated_at  datetime    not null,
    constraint users_login_uindex
        unique (login)
);

create table if not exists categories
(
    id         int auto_increment
        primary key,
    deleted    tinyint(1) not null,
    created_at datetime   not null,
    updated_at datetime   not null
);

create table if not exists products
(
    id          int auto_increment
        primary key,
    category_id int                  not null,
    price       decimal              not null,
    weight      double     default 0 not null,
    deleted     tinyint(1) default 0 not null,
    created_at  datetime             not null,
    updated_at  datetime             not null,
    image_url   varchar(40)          null,
    constraint categories_products_fk
        foreign key (category_id) references categories (id)
);

create table if not exists orders
(
    id         int auto_increment
        primary key,
    user_id    int         not null,
    status     varchar(12) not null,
    created_at datetime    null,
    updated_at datetime    null,
    constraint users_orders_fk
        foreign key (user_id) references users (id)
);

create table if not exists blocked_users
(
    user_id     int          not null
        primary key,
    description varchar(100) null,
    created_at  datetime     not null,
    constraint users_blocked_users_fk
        foreign key (user_id) references users (id)
);

create table if not exists category_info
(
    category_id int          not null,
    locale      varchar(6)   not null,
    title       varchar(20)  not null,
    description varchar(100) null,
    primary key (category_id, locale),
    constraint categories_category_info_fk
        foreign key (category_id) references categories (id)
);

create table if not exists category_properties
(
    id          int auto_increment
        primary key,
    locale      varchar(6)  not null,
    category_id int         not null,
    title       varchar(20) not null,
    data_type   varchar(20) not null,
    constraint categories_category_properties_fk
        foreign key (category_id) references categories (id)
);

create table if not exists order_product
(
    order_id   int not null,
    product_id int not null,
    amount     int not null,
    primary key (order_id, product_id),
    constraint orders_order_product_fk
        foreign key (order_id) references orders (id),
    constraint products_order_product_fk
        foreign key (product_id) references products (id)
);

create table if not exists product_info
(
    product_id  int auto_increment,
    locale      varchar(6)   not null,
    title       varchar(40)  not null,
    description varchar(300) null,
    color       varchar(20)  not null,
    primary key (product_id, locale),
    constraint products_product_info_fk
        foreign key (product_id) references products (id)
);

create table if not exists product_properties
(
    product_id             int auto_increment,
    category_properties_id int          not null,
    value                  varchar(100) null,
    primary key (product_id, category_properties_id),
    constraint category_properties_product_properties_fk
        foreign key (category_properties_id) references category_properties (id),
    constraint products_product_properties_fk
        foreign key (product_id) references products (id)
);

INSERT INTO users (id, first_name, second_name, login, password, role, created_at, updated_at)
VALUES (1, 'User', 'Tester', 'test', 'password', 'USER', '2021-05-27 13:33:06', '2021-05-27 13:33:09');

INSERT INTO categories (id, deleted, created_at, updated_at) VALUES (1, 0, '2021-05-20 14:50:33', '2021-05-20 14:50:38');

INSERT INTO category_info (category_id, locale, title, description) VALUES (1, 'en', 'Category name', 'category description');
INSERT INTO category_info (category_id, locale, title, description) VALUES (1, 'uk', 'Category name uk', 'category description uk');

INSERT INTO category_properties (id, locale, category_id, title, data_type) VALUES (1, 'en', 1, 'Num prop 1', 'number');
INSERT INTO category_properties (id, locale, category_id, title, data_type) VALUES (2, 'uk', 1, 'Num prop 1 uk', 'number uk');
INSERT INTO category_properties (id, locale, category_id, title, data_type) VALUES (3, 'en', 1, 'Text prop 2', 'text');
INSERT INTO category_properties (id, locale, category_id, title, data_type) VALUES (4, 'uk', 1, 'Text prop 2 uk', 'text uk');

INSERT INTO products (id, category_id, price, weight, deleted, created_at, updated_at, image_url)
VALUES (1, 1, 200, 1.5, 0, '2021-05-30 21:17:40', '2021-06-03 17:59:01', null);

INSERT INTO product_info (product_id, locale, title, description, color) VALUES (1, 'en', 'Test product', 'test product description', 'test color');
INSERT INTO product_info (product_id, locale, title, description, color) VALUES (1, 'uk', 'Test product uk', 'test product description uk', 'test color uk');

INSERT INTO product_properties (product_id, category_properties_id, value) VALUES (1, 1, '123');
INSERT INTO product_properties (product_id, category_properties_id, value) VALUES (1, 2, '123');
INSERT INTO product_properties (product_id, category_properties_id, value) VALUES (1, 3, 'text val');
INSERT INTO product_properties (product_id, category_properties_id, value) VALUES (1, 4, 'text val uk');
