package ru.job4j.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.job4j.models.User;


public class UserStorage {

    private static final String TABLE = "duties";

    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");

        StandardServiceRegistryBuilder serviceBuilder = new StandardServiceRegistryBuilder().
                applySettings(configuration.getProperties());
        SessionFactory factory = configuration.addAnnotatedClass(User.class)
                .buildSessionFactory(serviceBuilder.build());

        Session session = factory.openSession();
        session.beginTransaction();

        User user = new User();
        user.setName("testName");
        user.setPassword("testPassword");
        user.setCreated(true);

        session.save(user);

        System.out.println(session.createQuery(String.format("from %s", TABLE)).list());

        session.getTransaction().commit();
        session.close();
        factory.close();
    }
}
