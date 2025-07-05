package labHeroesGame.gameSaving;

import labHeroesGame.Game;
import labHeroesGame.Render;
import labHeroesGame.authorization.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class GameSaver {
    private static File AUTO_SAVE_FILE;

    public static void saveGame(Game game, User user) {
        AUTO_SAVE_FILE = new File("autosaves/"+ user.getName() + "_autosave.dat");
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(AUTO_SAVE_FILE))) {
            outputStream.writeObject(game);
            Render.displayAutoSaveMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void deleteAutoSave(User user) {
        AUTO_SAVE_FILE = new File("autosaves/"+ user.getName()+ "_autosave.dat");
        if (AUTO_SAVE_FILE.exists()) {
            AUTO_SAVE_FILE.delete();
        }
    }
}
