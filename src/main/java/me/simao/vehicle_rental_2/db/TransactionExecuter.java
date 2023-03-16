package me.simao.vehicle_rental_2.db;

import org.hibernate.Session;

public interface TransactionExecuter {
    void execute(Session s);
}
