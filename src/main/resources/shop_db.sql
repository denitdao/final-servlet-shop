create table users
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

INSERT INTO shop_db.users (id, first_name, second_name, login, password, role, created_at, updated_at) VALUES (1, 'Denys', 'Churchyn', 'den', '$2a$10$R4F5kahWKAdLuQGxdLQikezEYqBUBHUEekQlotvEArfocf1ydiIF2', 'ADMIN', '2021-05-20 14:47:59', '2021-05-20 14:48:04');
INSERT INTO shop_db.users (id, first_name, second_name, login, password, role, created_at, updated_at) VALUES (2, 'User', 'Tester', 'user', '$2a$10$R4F5kahWKAdLuQGxdLQikezEYqBUBHUEekQlotvEArfocf1ydiIF2', 'USER', '2021-05-27 13:33:06', '2021-05-27 13:33:09');
INSERT INTO shop_db.users (id, first_name, second_name, login, password, role, created_at, updated_at) VALUES (12, 'Random', 'Human', 'random', '$2a$10$R4F5kahWKAdLuQGxdLQikezEYqBUBHUEekQlotvEArfocf1ydiIF2', 'USER', '2021-05-31 20:11:26', '2021-06-01 17:45:32');
INSERT INTO shop_db.users (id, first_name, second_name, login, password, role, created_at, updated_at) VALUES (15, 'la', 'fero', 'lafero', '$2a$10$XwggbJ4LwMFrF/0Vk/12Mu9vttUzJQwIHAm0rPZyiw5SbnKlEwh5u', 'ADMIN', '2021-06-02 14:23:35', '2021-06-02 14:23:35');
INSERT INTO shop_db.users (id, first_name, second_name, login, password, role, created_at, updated_at) VALUES (22, 'тоха', 'тохевич', 'dorogant', '$2a$10$rEtv/pjI5YTI5D7hZG2qzuNLIKXBHzjlm4OnymgoO8zECTz4z9iKG', 'USER', '2021-06-05 20:22:04', '2021-06-05 20:22:04');

create table categories
(
    id         int auto_increment
        primary key,
    deleted    tinyint(1) not null,
    created_at datetime   not null,
    updated_at datetime   not null
);

INSERT INTO shop_db.categories (id, deleted, created_at, updated_at) VALUES (1, 0, '2021-05-20 14:50:33', '2021-05-20 14:50:38');
INSERT INTO shop_db.categories (id, deleted, created_at, updated_at) VALUES (2, 0, '2021-05-27 17:00:28', '2021-05-27 17:00:32');
INSERT INTO shop_db.categories (id, deleted, created_at, updated_at) VALUES (3, 0, '2021-05-30 16:22:21', '2021-05-30 16:22:30');

create table products
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

INSERT INTO shop_db.products (id, category_id, price, weight, deleted, created_at, updated_at, image_url) VALUES (11, 3, 2, 0, 1, '2021-05-30 21:17:40', '2021-06-03 17:59:01', null);
INSERT INTO shop_db.products (id, category_id, price, weight, deleted, created_at, updated_at, image_url) VALUES (12, 3, 34, 0.9, 0, '2021-06-02 14:38:45', '2021-06-05 18:50:33', 'db6e0f99-6285-4338-8f34-2db7db8c28df');
INSERT INTO shop_db.products (id, category_id, price, weight, deleted, created_at, updated_at, image_url) VALUES (13, 1, 139, 2.3, 0, '2021-06-02 16:45:33', '2021-06-05 18:44:29', '797576ad-dac5-47cf-903c-bb6cb22c1d80');
INSERT INTO shop_db.products (id, category_id, price, weight, deleted, created_at, updated_at, image_url) VALUES (14, 1, 129, 3.2, 0, '2021-06-02 16:50:18', '2021-06-05 18:45:04', '44230e6f-26d8-4106-a305-a223096dcc8a');
INSERT INTO shop_db.products (id, category_id, price, weight, deleted, created_at, updated_at, image_url) VALUES (15, 1, 215, 1.62, 0, '2021-06-02 18:17:12', '2021-06-05 18:42:18', '91ab1785-1474-42fb-be98-05b75d70b16e');
INSERT INTO shop_db.products (id, category_id, price, weight, deleted, created_at, updated_at, image_url) VALUES (16, 1, 159, 6.25, 0, '2021-06-05 15:19:59', '2021-06-06 19:02:57', 'e07a97a0-6341-4833-b4db-922d21e325a4');
INSERT INTO shop_db.products (id, category_id, price, weight, deleted, created_at, updated_at, image_url) VALUES (17, 3, 20, 0.76, 0, '2021-06-05 19:29:58', '2021-06-05 19:29:58', '9862b571-e7a3-436e-8567-6f26c9cda5bb');
INSERT INTO shop_db.products (id, category_id, price, weight, deleted, created_at, updated_at, image_url) VALUES (18, 3, 29, 0.56, 0, '2021-06-05 19:40:10', '2021-06-05 19:40:10', 'e7dc08a3-9864-432d-bd4e-b43c92702f34');
INSERT INTO shop_db.products (id, category_id, price, weight, deleted, created_at, updated_at, image_url) VALUES (19, 2, 285, 0.06, 0, '2021-06-05 19:46:56', '2021-06-05 19:46:56', '4683eef2-599e-43c8-85f3-71403e363dd0');
INSERT INTO shop_db.products (id, category_id, price, weight, deleted, created_at, updated_at, image_url) VALUES (20, 2, 327, 0.11, 0, '2021-06-05 19:50:20', '2021-06-05 19:52:16', '4850eb50-49df-446e-81a2-53a9718c65a5');
INSERT INTO shop_db.products (id, category_id, price, weight, deleted, created_at, updated_at, image_url) VALUES (21, 2, 699, 0.11, 0, '2021-06-05 19:59:19', '2021-06-05 20:27:23', '11947dfc-c4f0-4d4d-b19d-50613f5be96c');
INSERT INTO shop_db.products (id, category_id, price, weight, deleted, created_at, updated_at, image_url) VALUES (22, 2, 333, 0.13, 0, '2021-06-05 20:21:39', '2021-06-05 20:23:54', '87d9061f-a867-4363-9fe6-988cd4c2c6a7');

create table category_properties
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

INSERT INTO shop_db.category_properties (id, locale, category_id, title, data_type) VALUES (1, 'en', 1, 'Power (W)', 'number');
INSERT INTO shop_db.category_properties (id, locale, category_id, title, data_type) VALUES (2, 'uk', 1, 'Потужність (Вт)', 'number');
INSERT INTO shop_db.category_properties (id, locale, category_id, title, data_type) VALUES (3, 'en', 1, 'Brand', 'text');
INSERT INTO shop_db.category_properties (id, locale, category_id, title, data_type) VALUES (4, 'uk', 1, 'Виробник', 'text');
INSERT INTO shop_db.category_properties (id, locale, category_id, title, data_type) VALUES (6, 'en', 3, 'Shape', 'text');
INSERT INTO shop_db.category_properties (id, locale, category_id, title, data_type) VALUES (7, 'uk', 3, 'Форма', 'text');
INSERT INTO shop_db.category_properties (id, locale, category_id, title, data_type) VALUES (8, 'en', 3, 'Material', 'text');
INSERT INTO shop_db.category_properties (id, locale, category_id, title, data_type) VALUES (9, 'uk', 3, 'Матеріали', 'text');
INSERT INTO shop_db.category_properties (id, locale, category_id, title, data_type) VALUES (10, 'en', 3, 'Brand', 'text');
INSERT INTO shop_db.category_properties (id, locale, category_id, title, data_type) VALUES (11, 'uk', 3, 'Виробник', 'text');
INSERT INTO shop_db.category_properties (id, locale, category_id, title, data_type) VALUES (12, 'en', 2, 'Brand', 'text');
INSERT INTO shop_db.category_properties (id, locale, category_id, title, data_type) VALUES (13, 'uk', 2, 'Виробник', 'text');
INSERT INTO shop_db.category_properties (id, locale, category_id, title, data_type) VALUES (14, 'en', 2, 'Memory Storage (GB)', 'number');
INSERT INTO shop_db.category_properties (id, locale, category_id, title, data_type) VALUES (15, 'uk', 2, 'Обсяг Пам''яті', 'number');
INSERT INTO shop_db.category_properties (id, locale, category_id, title, data_type) VALUES (16, 'en', 2, 'Operating System', 'text');
INSERT INTO shop_db.category_properties (id, locale, category_id, title, data_type) VALUES (17, 'uk', 2, 'Операційна Система', 'text');
INSERT INTO shop_db.category_properties (id, locale, category_id, title, data_type) VALUES (18, 'en', 2, 'Screen Size"', 'number');
INSERT INTO shop_db.category_properties (id, locale, category_id, title, data_type) VALUES (19, 'uk', 2, 'Розмір екрану', 'number');

create table product_properties
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

INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (11, 6, 'Rectangle');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (11, 7, 'прямокутний');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (11, 8, 'Wood');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (11, 9, 'дерево');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (11, 10, 'JALL');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (11, 11, 'JALL');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (12, 6, 'Round');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (12, 7, 'Круглі');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (12, 8, 'Plywood');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (12, 9, 'Фанера');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (12, 10, 'Waw');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (12, 11, 'Waw');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (13, 1, '550');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (13, 2, '550');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (13, 3, 'BOSCH');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (13, 4, 'BOSCH');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (14, 1, '550');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (14, 2, '550');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (14, 3, 'ROWENTA');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (14, 4, 'ROWENTA');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (15, 1, '2000');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (15, 2, '2000');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (15, 3, 'TEFAL');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (15, 4, 'TEFAL');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (16, 1, '850');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (16, 2, '850');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (16, 3, 'TEFAL');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (16, 4, 'TEFAL');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (17, 6, 'Round');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (17, 7, 'Круглі');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (17, 8, 'Wood');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (17, 9, 'Дерево');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (17, 10, 'Plumeet');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (17, 11, 'Plumeet');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (18, 6, 'round');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (18, 7, 'круглий');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (18, 8, 'polycarbonate');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (18, 9, 'полікарбонат');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (18, 10, 'The Ultimate Wall Clock');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (18, 11, 'The Ultimate Wall Clock');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (19, 12, 'Apple');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (19, 13, 'Apple');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (19, 14, '64');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (19, 15, '64');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (19, 16, 'iOS');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (19, 17, 'iOS');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (19, 18, '5');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (19, 19, '5');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (20, 12, 'Samsung');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (20, 13, 'Samsung');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (20, 14, '128');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (20, 15, '128');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (20, 16, 'Android');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (20, 17, 'Android');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (20, 18, '6');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (20, 19, '6');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (21, 12, 'Google');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (21, 13, 'Google');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (21, 14, '128');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (21, 15, '128');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (21, 16, 'Android');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (21, 17, 'Android');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (21, 18, '6');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (21, 19, '6');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (22, 12, 'Apple');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (22, 13, 'Apple');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (22, 14, '64');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (22, 15, '64');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (22, 16, 'iOS');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (22, 17, 'iOS');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (22, 18, '6');
INSERT INTO shop_db.product_properties (product_id, category_properties_id, value) VALUES (22, 19, '6');

create table product_info
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

INSERT INTO shop_db.product_info (product_id, locale, title, description, color) VALUES (11, 'en', 'Digital Alarm Clock', 'This must be the most delicate alarm clock you’ve ever seen. It’s not only a clock, but also a great decoration for your bedroom.', 'black');
INSERT INTO shop_db.product_info (product_id, locale, title, description, color) VALUES (11, 'uk', 'Цифровий будильник', 'Це, мабуть, найтонший будильник, який ви коли-небудь бачили. Це не тільки годинник, але і чудова прикраса вашої спальні.', 'чорний');
INSERT INTO shop_db.product_info (product_id, locale, title, description, color) VALUES (12, 'en', 'Waw Deco HexaFlower wall clock', 'Wooden Designer Wall Clock for home or office interior - Waw Deco HexaFlower quality original and stylish - Wall decor with quartz silent clock mechanism - for wall decoration living room kitchen bedroom hall, Dark brown', 'Dark brown');
INSERT INTO shop_db.product_info (product_id, locale, title, description, color) VALUES (12, 'uk', 'Настінний годинник Waw Deco HexaFlower', 'Дерев''яні дизайнерські Годинники Настінні для інтер''єру будинку або офісу - Waw Deco HexaFlower якісні оригінальні і стильні - Декор на стіну з кварцовим безшумним механізмом годин - для декорування стіни вітальні кухні спальні залу, Темно-коричневий', 'Темно-коричневий');
INSERT INTO shop_db.product_info (product_id, locale, title, description, color) VALUES (13, 'en', 'Bosch BGC2U230 vacuum cleaner', 'Convenient, powerful, and most importantly - quite quiet and without a bag, provides flawless operation with high quality cleaning results.', 'red');
INSERT INTO shop_db.product_info (product_id, locale, title, description, color) VALUES (13, 'uk', 'Пилосос Bosch BGC2U230', 'Зручний, потужний, а головне — досить тихий і без мішка, забезпечує бездоганну роботу з високоякісними результатами прибирання.', 'червоний');
INSERT INTO shop_db.product_info (product_id, locale, title, description, color) VALUES (14, 'en', 'ROWENTA Compact Power XXL Vacuum cleaner', 'The Rowenta Compact Power XXL vacuum cleaner has a powerful container capacity and at the same time a compact and thoughtful design for comfortable cleaning without borders. The record capacity of a flask of 2.5 l is ideally suited for the most various premises, providing the big autonomy', 'blue');
INSERT INTO shop_db.product_info (product_id, locale, title, description, color) VALUES (14, 'uk', 'Пилосос ROWENTA Compact Power XXL', 'Пилосос Rowenta Compact Power XXL має потужну місткість контейнера і водночас компактний і продуманий дизайн для комфортного прибирання без кордонів. Рекордна місткість колби 2.5 л ідеально підходить для найрізноманітніших житлових приміщень, забезпечуючи велику автономність і легкий процес очищення', 'синій');
INSERT INTO shop_db.product_info (product_id, locale, title, description, color) VALUES (15, 'en', 'TEFAL OptiGrill Initial GC706 grill', 'OptiGrill Initial grill: From steak with blood to well-roasted - the perfect grill quality is always.
The unique OptiGrill Initial will make every cooking simple and convenient. Cook on the grill at home with a unique interface: 5 roasting stages, 6 automatic programs and defrost function.', 'black');
INSERT INTO shop_db.product_info (product_id, locale, title, description, color) VALUES (15, 'uk', 'Гриль TEFAL OptiGrill+ Initial GC706', 'OptiGrill Initial grill: Від стейка з кров''ю до добре прожареного — ідеальна якість гриля завжди.
Унікальний OptiGrill Initial зробить кожне приготування простим і зручним. Готуйте на грилі вдома з унікальним інтерфейсом: 5 ступенів підсмажування, 6 автоматичних програм і функція розморожування.', 'чорний');
INSERT INTO shop_db.product_info (product_id, locale, title, description, color) VALUES (16, 'en', 'TEFAL Multicook & Grain RK90 multicooker', 'The new multicooker from Tefal Multicook & Grains RK900 - for preparation of simple, tasty, useful and various dishes daily. You and your loved ones will always have a healthy and balanced menu!', 'white');
INSERT INTO shop_db.product_info (product_id, locale, title, description, color) VALUES (16, 'uk', 'Мультиварка TEFAL Multicook & Grain RK90', 'Нова мультиварка від Tefal Multicook&Grains RK900 — для приготування простих, смачних, корисних і різноманітних страв щодня. Ви та ваші близькі будуть мати корисне та збалансоване меню завжди!', 'біла');
INSERT INTO shop_db.product_info (product_id, locale, title, description, color) VALUES (17, 'en', '12'''' Wooden Wall Clock - Plumeet Framele', 'Simple Style - Made of white wood panels. No extra patterns, no frames, no glass and no second hand. This makes the overall design of the wall clock extremely clean and tidy, white wash farmhouse appeal and will look great with any kitchen or bedrooms.', 'white');
INSERT INTO shop_db.product_info (product_id, locale, title, description, color) VALUES (17, 'uk', '12" настінний годинник - Plumeet Framele', 'Простий стиль - виготовлений з білих дерев’яних панелей. Ніяких зайвих візерунків, ніяких рамок, жодного скла та секонд хенду. Це робить загальний дизайн настінних годинників надзвичайно чистим та акуратним, привабливим для ферми в стилі білого миття і буде чудово виглядати з будь-якою кухнею', 'білий');
INSERT INTO shop_db.product_info (product_id, locale, title, description, color) VALUES (18, 'en', '14" Atomic, The Ultimate, Black', 'Great for any room at home, work or classroom. The classic black frame with satin finish and modern design compliments any room’s decor.', 'black');
INSERT INTO shop_db.product_info (product_id, locale, title, description, color) VALUES (18, 'uk', '14" Atomic, The Ultimate, Чорний', 'Чудово підходить для будь-якої кімнати вдома, на роботі чи в класі. Класична чорна рамка з атласною обробкою та сучасним дизайном доповнює декор будь-якої кімнати.', 'чорний');
INSERT INTO shop_db.product_info (product_id, locale, title, description, color) VALUES (19, 'en', 'Apple iPhone 8 Plus, 64GB, Space Gray', 'This pre-owned product is not Apple certified, but has been professionally inspected, tested and cleaned by Amazon-qualified suppliers.', 'black');
INSERT INTO shop_db.product_info (product_id, locale, title, description, color) VALUES (19, 'uk', 'Apple iPhone 8 Plus, 64GB, Space Gray', 'Цей уживаний продукт не сертифікований компанією Apple, але його професійно перевірили, випробували та очистили кваліфіковані постачальники Amazon.', 'чорний');
INSERT INTO shop_db.product_info (product_id, locale, title, description, color) VALUES (20, 'en', 'Samsung Galaxy S10, 128GB, Prism Black', 'Fully Unlocked: Fully unlocked and compatible with any carrier of choice (e.g. AT&T, T-Mobile, Sprint, Verizon, US-Cellular, Cricket, Metro, etc.), both domestically and internationally.', 'Prism Black');
INSERT INTO shop_db.product_info (product_id, locale, title, description, color) VALUES (20, 'uk', 'Samsung Galaxy S10, 128GB, Prism Black', 'Повністю розблокована: повністю розблокована та сумісна з будь-яким вибраним оператором (наприклад, AT&T, T-Mobile, Sprint, Verizon, US-Cellular, Cricket, Metro тощо) як на внутрішньому, так і на міжнародному рівні.', 'Призма Чорний');
INSERT INTO shop_db.product_info (product_id, locale, title, description, color) VALUES (21, 'en', 'Google Pixel 5 - 5G Android Phone', 'What happens when you bring together the ultimate Google phone and the speed of 5G? You get Pixel 5. Download a movie in seconds, play your favorite games on the go, and so much more with the super fast 5G Google smartphone.', 'black');
INSERT INTO shop_db.product_info (product_id, locale, title, description, color) VALUES (21, 'uk', 'Google Pixel 5 - 5G Android Phone', 'Що трапляється, коли ви поєднуєте ідеальний телефон Google і швидкість 5G? Ви отримуєте Pixel 5. Завантажте фільм за лічені секунди, грайте у свої улюблені ігри на ходу та багато іншого завдяки надшвидкий 5G-смартфон Google.', 'чорний');
INSERT INTO shop_db.product_info (product_id, locale, title, description, color) VALUES (22, 'en', 'Apple iPhone X, 64GB, Space Gray', 'The device does not come with headphones or a SIM card. It does include a charger and charging cable that may be generic, in which case it will be UL or Mfi (Made for iPhone) Certified.', 'Space Gray');
INSERT INTO shop_db.product_info (product_id, locale, title, description, color) VALUES (22, 'uk', 'Apple iPhone X, 64GB, Space Gray', 'Пристрій не постачається з навушниками чи SIM-карткою. Він включає зарядний та зарядний кабелі, які можуть бути загальними, і в цьому випадку він буде сертифікований UL або Mfi (зроблено для iPhone).', 'Космічний Сірий');

create table orders
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

INSERT INTO shop_db.orders (id, user_id, status, created_at, updated_at) VALUES (1, 1, 'CANCELLED', '2021-05-31 14:58:34', '2021-06-01 19:06:57');
INSERT INTO shop_db.orders (id, user_id, status, created_at, updated_at) VALUES (3, 1, 'DELIVERED', '2021-06-01 19:06:04', '2021-06-03 00:02:23');
INSERT INTO shop_db.orders (id, user_id, status, created_at, updated_at) VALUES (4, 12, 'DELIVERED', '2021-06-01 19:18:44', '2021-06-04 21:11:46');
INSERT INTO shop_db.orders (id, user_id, status, created_at, updated_at) VALUES (5, 15, 'CANCELLED', '2021-06-02 14:29:19', '2021-06-02 14:30:57');
INSERT INTO shop_db.orders (id, user_id, status, created_at, updated_at) VALUES (6, 1, 'REGISTERED', '2021-06-02 14:49:10', '2021-06-02 14:49:10');
INSERT INTO shop_db.orders (id, user_id, status, created_at, updated_at) VALUES (7, 1, 'REGISTERED', '2021-06-02 18:28:45', '2021-06-02 18:28:45');
INSERT INTO shop_db.orders (id, user_id, status, created_at, updated_at) VALUES (8, 1, 'DELIVERED', '2021-06-02 19:41:03', '2021-06-02 19:41:33');
INSERT INTO shop_db.orders (id, user_id, status, created_at, updated_at) VALUES (13, 1, 'REGISTERED', '2021-06-02 23:51:21', '2021-06-02 23:51:21');
INSERT INTO shop_db.orders (id, user_id, status, created_at, updated_at) VALUES (14, 1, 'REGISTERED', '2021-06-04 18:21:53', '2021-06-04 18:21:53');
INSERT INTO shop_db.orders (id, user_id, status, created_at, updated_at) VALUES (15, 2, 'CART', null, null);
INSERT INTO shop_db.orders (id, user_id, status, created_at, updated_at) VALUES (16, 1, 'CART', null, null);
INSERT INTO shop_db.orders (id, user_id, status, created_at, updated_at) VALUES (17, 22, 'CART', null, null);

create table order_product
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

INSERT INTO shop_db.order_product (order_id, product_id, amount) VALUES (1, 11, 4);
INSERT INTO shop_db.order_product (order_id, product_id, amount) VALUES (3, 11, 2);
INSERT INTO shop_db.order_product (order_id, product_id, amount) VALUES (4, 11, 7);
INSERT INTO shop_db.order_product (order_id, product_id, amount) VALUES (5, 11, 2);
INSERT INTO shop_db.order_product (order_id, product_id, amount) VALUES (6, 11, 4);
INSERT INTO shop_db.order_product (order_id, product_id, amount) VALUES (6, 12, 1);
INSERT INTO shop_db.order_product (order_id, product_id, amount) VALUES (7, 12, 2);
INSERT INTO shop_db.order_product (order_id, product_id, amount) VALUES (7, 14, 4);
INSERT INTO shop_db.order_product (order_id, product_id, amount) VALUES (7, 15, 3);
INSERT INTO shop_db.order_product (order_id, product_id, amount) VALUES (8, 12, 3);
INSERT INTO shop_db.order_product (order_id, product_id, amount) VALUES (13, 12, 5);
INSERT INTO shop_db.order_product (order_id, product_id, amount) VALUES (13, 13, 3);
INSERT INTO shop_db.order_product (order_id, product_id, amount) VALUES (13, 15, 1);
INSERT INTO shop_db.order_product (order_id, product_id, amount) VALUES (14, 13, 4);
INSERT INTO shop_db.order_product (order_id, product_id, amount) VALUES (15, 13, 3);
INSERT INTO shop_db.order_product (order_id, product_id, amount) VALUES (16, 13, 3);
INSERT INTO shop_db.order_product (order_id, product_id, amount) VALUES (16, 19, 1);
INSERT INTO shop_db.order_product (order_id, product_id, amount) VALUES (17, 22, 2);

create table category_info
(
    category_id int          not null,
    locale      varchar(6)   not null,
    title       varchar(20)  not null,
    description varchar(100) null,
    primary key (category_id, locale),
    constraint categories_category_info_fk
        foreign key (category_id) references categories (id)
);

INSERT INTO shop_db.category_info (category_id, locale, title, description) VALUES (1, 'en', 'Home appliances', 'A wide range of home appliances for your kitchen and home');
INSERT INTO shop_db.category_info (category_id, locale, title, description) VALUES (1, 'uk', 'Побутові прилади', 'Широкий асортимент побутової техніки для вашої кухні та будинку');
INSERT INTO shop_db.category_info (category_id, locale, title, description) VALUES (2, 'en', 'Smartphones', 'New smartphones from all popular manufacturers');
INSERT INTO shop_db.category_info (category_id, locale, title, description) VALUES (2, 'uk', 'Смартфони', 'Нові смартфони усіх популярних виробників');
INSERT INTO shop_db.category_info (category_id, locale, title, description) VALUES (3, 'en', 'Wall clocks', 'Buy wall clocks of different shapes, colors and sizes');
INSERT INTO shop_db.category_info (category_id, locale, title, description) VALUES (3, 'uk', 'Настінні годинники', 'Купуйте настінні годинники різних форм, кольорів та розмірів');

create table blocked_users
(
    user_id     int          not null
        primary key,
    description varchar(100) null,
    created_at  datetime     not null,
    constraint users_blocked_users_fk
        foreign key (user_id) references users (id)
);

INSERT INTO shop_db.blocked_users (user_id, description, created_at) VALUES (12, null, '2021-06-05 15:07:29');