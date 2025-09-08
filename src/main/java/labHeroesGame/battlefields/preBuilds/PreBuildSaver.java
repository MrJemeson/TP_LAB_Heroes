package labHeroesGame.battlefields.preBuilds;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import labHeroesGame.Render;

import java.io.FileWriter;
import java.io.IOException;

public class PreBuildSaver {
    public static void savePreBuild(PreBuild preBuild) {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        try(FileWriter writer = new FileWriter("savedPreBuilds/" + preBuild.getName() + ".json")){
            gson.toJson(preBuild, writer);
            Render.displayPreBuildSavedMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
