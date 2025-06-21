package labHeroesGame;

import labHeroesGame.buildings.Tower;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerConfig {
    private static Logger logger = Logger.getLogger(Tower.class.getName());

    static  {
        try {
            FileHandler handler = new FileHandler("logs/app.log", 1024 * 4, 3, true);
            SimpleFormatter formatter = new SimpleFormatter();
            handler.setFormatter(formatter);
            logger.addHandler(handler);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}
