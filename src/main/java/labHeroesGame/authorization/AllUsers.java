package labHeroesGame.authorization;

import java.io.*;
import java.util.ArrayList;

public class AllUsers {
    private static ArrayList<User> listOfUsers = new ArrayList<>();

    public static void loadAllUsers(){
        File file = new File("users/allusers.dat");
        if (!file.exists()) return;
        try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = inputStream.readObject();
            if (obj instanceof ArrayList<?> obj1) {
                if (!obj1.isEmpty()) {
                    if(obj1.getFirst() instanceof User) {
                        listOfUsers = (ArrayList<User>) obj1.clone();
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void saveAllUsers(){
        new File("users").mkdirs();
        File file = new File("users/allusers.dat");
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))){
            outputStream.writeObject(listOfUsers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int checkUser(String name, String password){
        for (User user: listOfUsers) {
            if (user.getName().equals(name)) {
                if (user.getPassword().equals(password)) {
                    return 1;
                } else {
                    return 2;
                }
            }
        }
        return 3;
    }

    public static User getUser(String name) {
        for (User user: listOfUsers) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    public static ArrayList<User> getListOfUsers() {
        return listOfUsers;
    }
}
