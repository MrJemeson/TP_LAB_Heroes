package labHeroesGame.battlefields.preBuilds;

import labHeroesGame.Render;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class PreBuildSaver {
    public static void savePreBuild(PreBuild preBuild) {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("savedPreBuilds/" + preBuild.getName() + ".pb"))) {
            outputStream.writeObject(preBuild);
            Render.displayPreBuildSavedMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
