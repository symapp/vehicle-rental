package me.simao.vehicle_rental_2.db;

import me.simao.vehicle_rental_2.db.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class DBUtil {
    private static Session session = null;
    private static SessionFactory sf;

    private static Session getSession() {
        if (session != null) return session;

        Configuration conf = new Configuration();

        conf.configure("hibernate.config.xml");
        conf.addAnnotatedClass(User.class);

        sf = conf.buildSessionFactory();
        session = sf.openSession();

        return session;
    }

    public static void load() {
        getSession();
    }

    public static void doWithTransaction(TransactionExecuter a) {
        if (session == null) getSession();
        if (!session.isOpen()) session = sf.openSession();

        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            a.execute(session);

            tx.commit();
        }
        catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
        finally {
            session.close();
        }

    }
}
