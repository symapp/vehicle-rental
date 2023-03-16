package me.simao.vehicle_rental_2;

import me.simao.vehicle_rental_2.db.models.User;

public class Session {
    private static User loggedInUser;

    public static User getEmptyUser() {
        User u = new User();
        u.setId(1);
        return u;
    }
    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        Session.loggedInUser = loggedInUser;
    }
}
