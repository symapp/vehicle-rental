module me.simao.vehicle_rental_2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires jakarta.persistence;
    requires lombok;


    opens me.simao.vehicle_rental_2.db.models;
    opens me.simao.vehicle_rental_2;
//    opens me.simao.vehicle_rental_2 to javafx.fxml;
    exports me.simao.vehicle_rental_2;
    exports me.simao.vehicle_rental_2.controllers;
    opens me.simao.vehicle_rental_2.controllers to javafx.fxml;
}