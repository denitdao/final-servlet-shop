package ua.denitdao.servlet.shop.model.util;

import ua.denitdao.servlet.shop.model.entity.enums.Status;

public final class SQLQueries {

    public static final String USER_INSERT = "insert into\n" +
            "users (first_name, second_name, login, password, role, created_at, updated_at)\n" +
            "values (?, ?, ?, ?, ?, ?, ?)";
    public static final String USER_FIND_ID = "select u.*, if(bu.user_id is not null, true, false) blocked\n" +
            "from users u\n" +
            "left join blocked_users bu on u.id = bu.user_id\n" +
            "where id=?";
    public static final String USER_FIND_LOGIN = "select u.*, if(bu.user_id is not null, true, false) blocked\n" +
            "from users u\n" +
            "left join blocked_users bu on u.id = bu.user_id\n" +
            "where login=?";
    public static final String USER_FIND_ALL = "select u.*, if(bu.user_id is not null, true, false) blocked\n" +
            "from users u\n" +
            "left join blocked_users bu on u.id = bu.user_id";
    public static final String USER_BLOCK = "insert into blocked_users (user_id, created_at)\n" +
            "values (?, ?)";
    public static final String USER_UNBLOCK = "delete from blocked_users\n" +
            "where user_id=?";
    public static final String USER_UPDATE = "update users\n" +
            "set first_name=?, second_name=?, login=?, password=?, role=?, updated_at=?\n" +
            "where id=?";


    public static final String PRODUCT_INSERT = "insert into\n" +
            "products (category_id, price, weight, image_url, created_at, updated_at)\n" +
            "values (?, ?, ?, ?, ?, ?)";
    public static final String PRODUCT_INFO_INSERT = "insert into\n" +
            "product_info (product_id, locale, title, description, color)\n" +
            "values (?, ?, ?, ?, ?)";
    public static final String PRODUCT_PROPERTIES_INSERT = "insert into\n" +
            "product_properties (product_id, category_properties_id, value)\n" +
            "values (?, ?, ?)";
    public static final String PRODUCT_FIND_ID = "select products.*, pi.*, c.id c_id, c.title c_title\n" +
            "from products\n" +
            "         left join product_info pi on products.id = pi.product_id\n" +
            "         left join (select id, title\n" +
            "                    from categories\n" +
            "                             left join category_info ci on categories.id = ci.category_id\n" +
            "                    where locale = ?) c\n" +
            "                   on products.category_id = c.id\n" +
            "where product_id = ?\n" +
            "  and locale = ?";
    public static final String PRODUCT_FIND_ALL_CATEGORY = "select products.*, pi.*\n" +
            "from products\n" +
            "         left join product_info pi on products.id = pi.product_id\n" +
            "where category_id = ?\n" +
            "  and locale = ? and deleted = 0\n" +
            "  and price between ? and ?\n";
    public static final String PRODUCT_FIND_ALL_CATEGORY_LIMIT = " limit ?,?";
    public static final String PRODUCT_UPDATE = "update products\n" +
            "set price=?, weight=?, updated_at=?, image_url=?\n" +
            " where id=?";
    public static final String PRODUCT_INFO_UPDATE = "update product_info\n" +
            "set title=?, description=?, color=?\n" +
            "where product_id=? and locale=?";
    public static final String PRODUCT_PROPERTIES_UPDATE = "update product_properties\n" +
            "set value=?\n" +
            "where product_id=? and category_properties_id=?";
    public static final String PRODUCT_DELETE = "update products\n" +
            "set deleted=?, updated_at=?\n" +
            " where id=?";
    public static final String PRODUCT_COUNT_CATEGORY = "select count(*) as total\n" +
            "from products p\n" +
            "         left join categories c on p.category_id = c.id\n" +
            "where category_id = ? and p.deleted=0\n" +
            "  and price between ? and ?";


    public static final String CATEGORY_FIND_ID = "select id, title, description, created_at, updated_at\n" +
            "from categories\n" +
            "         left join category_info ci on categories.id = ci.category_id\n" +
            "where id = ? and ci.locale = ?";
    public static final String CATEGORY_FIND_ALL = "select id, title, description, created_at, updated_at\n" +
            "from categories\n" +
            "         left join category_info ci on categories.id = ci.category_id\n" +
            "where ci.locale = ?";


    public static final String ORDER_PRODUCT_INSERT = "insert into order_product (order_id, product_id, amount)\n" +
            "values ((select o.id from orders o where o.user_id = ? and o.status = ?), ?, ?)\n" +
            "on duplicate key update amount=?";
    public static final String ORDER_PRODUCT_FIND_CART = "select product_id, amount from order_product\n" +
            "where order_id=(select o.id from orders o where o.user_id = ? and o.status = ?)";
    public static final String ORDER_PRODUCT_FIND_CART_PRODUCT = "select products.*, pi.title, pi.description, pi.color, amount\n" +
            "from products\n" +
            "         left join product_info pi on products.id = pi.product_id\n" +
            "         inner join order_product op on products.id = op.product_id\n" +
            "where order_id=(select o.id from orders o where o.user_id = ? and o.status = ?)\n" +
            "  and locale = ?";


    public static final String ORDER_PRODUCT_DELETE_CART = "delete from order_product\n" +
            "where order_id=(select o.id from orders o where o.user_id = ? and o.status = ?)\n" +
            "and product_id=?";
    public static final String ORDER_PRODUCT_INSERT_CART = "insert into orders (user_id, status)\n" +
            "select *\n" +
            "from (select ? as user_id, ? as status) as tmp\n" +
            "where not exists(\n" +
            "        select user_id, status from orders where user_id = tmp.user_id and status = tmp.status\n" +
            "    )\n" +
            "limit 1";


    public static final String PRODUCT_PROPERTIES_FIND_PRODUCT_ID = "select cp.id, title, value, data_type\n" +
            "from category_properties cp\n" +
            "         left join product_properties pp on cp.id = pp.category_properties_id\n" +
            "where product_id = ?\n" +
            "  and locale = ? order by locale";
    public static final String CATEGORY_PROPERTIES_FIND_CATEGORY_ID = "select *\n" +
            "from category_properties\n" +
            "where category_id = ? order by locale";


    public static final String ORDER_UPDATE_FROM_CART = "update orders\n" +
            "set status=?, created_at=?, updated_at=?\n" +
            "where user_id = ? and status = ?";
    public static final String ORDER_FIND_ID = "select * from orders where id=?";
    public static final String PRODUCTS_FIND_ORDER_ID = "select products.*, pi.title, pi.description, pi.color, amount\n" +
            "from products\n" +
            "         left join product_info pi on products.id = pi.product_id\n" +
            "         inner join order_product op on products.id = op.product_id\n" +
            "where order_id = ?\n" +
            "  and locale = ?";
    public static final String ORDER_FIND_ALL = "select * from orders\n" +
            "where status <> 'CART'\n" +
            "order by case status\n" +
            "when '" + Status.REGISTERED + "' then 1\n" +
            "when '" + Status.DELIVERED + "' then 2\n" +
            "when '" + Status.CANCELLED + "' then 3\n" +
            "end, updated_at desc";
    public static final String ORDER_FIND_ALL_USER = "select * from orders\n" +
            "where user_id=? and status <> 'CART'\n" +
            "order by case status\n" +
            "when '" + Status.REGISTERED + "' then 1\n" +
            "when '" + Status.DELIVERED + "' then 2\n" +
            "when '" + Status.CANCELLED + "' then 3\n" +
            "end, updated_at desc";
    public static final String ORDER_UPDATE = "update orders\n" +
            "set status=?, updated_at=?\n" +
            " where id=?";

    private SQLQueries() {
    }
}
