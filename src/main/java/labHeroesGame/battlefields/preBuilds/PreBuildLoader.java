package labHeroesGame.battlefields.preBuilds;

import labHeroesGame.Render;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class PreBuildLoader {
    public static void loadPreBuilds() {
        File folder = new File("savedPreBuilds");

        if (!folder.exists() || !folder.isDirectory()) {
            Render.displayFolderLoadError();
            new File("savedPreBuilds").mkdirs();
            return;
        }

        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".pb"));
        if (files == null) return;

        for (File file : files) {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
                PreBuild preBuild = (PreBuild) inputStream.readObject();
                MapPreBuilds.getCustomPreBuilds().add(preBuild);
                Render.displayPreBuildLoaded(file.getName());
            } catch (IOException | ClassNotFoundException e) {
                Render.displayPreBuildLoadError(file.getName());
                e.printStackTrace();
            }
        }
    }
}
