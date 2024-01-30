package com.example.triparchitect;

import com.example.triparchitect.users.Share;
import com.example.triparchitect.users.User;

import java.io.*;

/**
 * class to serialize and deserialize objects as are user data and shared trips
 */
public class SerializationUtils {

    /**
     * Serialize user.
     *
     * @param user the user
     * @throws IOException the io exception
     */
    public static void serializeUser(User user) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(user.getUsername() + ".ser"))) {
            oos.writeObject(user);
        }
    }

    /**
     * Deserialize user user.
     *
     * @param username the username
     * @return user
     * @throws IOException            the io exception
     * @throws ClassNotFoundException the class not found exception
     */
    public static User deserializeUser(String username) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(username + ".ser"))) {
            return (User) ois.readObject();
        }
    }

    /**
     * Serialize shared trips.
     *
     * @param share the shared trips
     * @throws IOException the io exception
     */
    public static void serializeShare(Share share) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("shared_trips.ser"))) {
            oos.writeObject(share);
        }
    }

    /**
     * Deserialize shared trips
     *
     * @return shared trips
     * @throws IOException            the io exception
     * @throws ClassNotFoundException the class not found exception
     */
    public static Share deserializeShare() throws IOException, ClassNotFoundException {
        File file = new File("shared_trips.ser");
        if (!file.exists()) {
            return new Share();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Share) ois.readObject();
        }
    }

}