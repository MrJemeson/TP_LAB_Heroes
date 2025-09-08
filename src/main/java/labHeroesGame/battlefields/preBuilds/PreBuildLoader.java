package labHeroesGame.battlefields.preBuilds;

import com.google.gson.Gson;
import labHeroesGame.Render;

import java.io.*;

public class PreBuildLoader {
    public static void loadPreBuilds() {
        File folder = new File("savedPreBuilds");

        if (!folder.exists() || !folder.isDirectory()) {
            Render.displayFolderLoadError();
            new File("savedPreBuilds").mkdirs();
            return;
        }

        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));
        if (files == null) return;
        Gson gson = new Gson();
        for (File file : files) {
            try (FileReader fileReader = new FileReader(file)) {
                PreBuild preBuild = gson.fromJson(fileReader, PreBuild.class);
                MapPreBuilds.getCustomPreBuilds().add(preBuild);
                Render.displayPreBuildLoaded(file.getName());
            } catch (IOException e) {
                Render.displayPreBuildLoadError(file.getName());
                e.printStackTrace();
            }
        }
    }
}
