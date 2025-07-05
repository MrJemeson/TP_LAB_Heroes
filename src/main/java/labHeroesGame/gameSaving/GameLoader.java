package labHeroesGame.gameSaving;

import labHeroesGame.Game;
import labHeroesGame.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class GameLoader {
    public static boolean hasAutoSave(User user) {
        File file = new File("autosaves/" + user.getName() + "_autosave.dat");
        return file.exists();
    }

    public static Game loadSave(User user) {
        File file = new File("autosaves/" + user.getName() + "_autosave.dat");
        if (!file.exists()) return null;

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = inputStream.readObject();
            if (obj instanceof Game) {
                return (Game) obj;
            } else{
                return null;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
