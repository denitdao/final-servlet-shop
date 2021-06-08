# final-servlet-shop (Інтернет магазин)

Магазин має каталог Товарів, для якого необхідно реалізувати можливість:
- сортування за назвою товару (az, za);
- сортування товарів за ціною (від дешевих до дорогих, від дорогих до дешевих);
- сортування товарів за новизною;
- вибірки товарів за параметрами (категорія, проміжок ціни).

Користувач переглядає каталог і може додавати товари до свого кошика. 
Після додавання товарів у кошик, зареєстрований користувач може зробити Замовлення. 
Для незареєстрованого користувача ця опція недоступна. Після розміщення замовлення, йому (замовленню) присвоюється статус 'зареєстрований'.

Користувач має особистий кабінет, в якому може переглянути свої замовлення.
Адміністратор системи володіє правами:
- додавання/видалення товарів, зміни інформації про товар;
- блокування/розблокування користувача;
- переведення замовлення зі статусу 'зареєстрований' до 'доставлений' або 'скасований'.

# Database structure

![Structure diagram of the database](/shop_db.png)

# Technology stack
- Gradle as a build tool
- Java 8
- Tomcat 10 server
- Jakarta EE
- Servlets, JSP, JSTL, (.jspf, .tag)
- Log4j2
- MySQL - Apache Commons DBCP 2.8
- H2, Mockito for the tests
- Bootstrap

# Installation instructions

- configure database connection for the main app in the [dbconnect.properties](/src/main/resources/dbconnect.properties)
- create and populate database from the dump file [shop_db.sql](/src/main/resources/shop_db.sql)
- database records contain links to the images from the [folder](/src/main/webapp/resources/images)
