package labHeroesGame.battlefields.preBuilds;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import labHeroesGame.Render;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class PreBuildSaver {
    public static void savePreBuildJson(PreBuild preBuild) {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        try(FileWriter writer = new FileWriter("savedPreBuilds/" + preBuild.getName() + ".json")){
            gson.toJson(preBuild, writer);
            Render.displayPreBuildSavedMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void savePreBuildXml(PreBuild preBuild) {
        try{
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.writeValue(new File("savedPreBuilds/" + preBuild.getName() + ".xml"), preBuild);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
