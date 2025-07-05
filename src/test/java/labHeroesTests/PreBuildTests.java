package labHeroesTests;

import labHeroesGame.authorization.User;
import labHeroesGame.battlefields.preBuilds.MapCreator;
import labHeroesGame.battlefields.preBuilds.MapPreBuilds;
import labHeroesGame.battlefields.preBuilds.PreBuild;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

public class PreBuildTests {

    @Test
    public void testPlacingObject(){
        ByteArrayInputStream in = new ByteArrayInputStream(("Test\n5\nA19\n1\n5\nA17\n1\n5\nC19\n1\n5\nT0\n5\nT2\n5\nR0\n4\nA0\n1\n4\nT19\n4\nA10\n1\n4\nT10\nend\n").getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        MapCreator.createPreBuild(scanner, new User("Tester", "test"));
        PreBuild preBuild = MapPreBuilds.getCustomPreBuilds().get(0);
        assertEquals("Tower", preBuild.getMapInfo().get("A19"));
        assertEquals("Tower", preBuild.getMapInfo().get("A17"));
        assertEquals("Tower", preBuild.getMapInfo().get("C19"));
        assertEquals("Tower", preBuild.getMapInfo().get("T0"));
        assertEquals("Tower", preBuild.getMapInfo().get("T2"));
        assertEquals("Tower", preBuild.getMapInfo().get("R0"));
        assertEquals("Castle", preBuild.getMapInfo().get("A0"));
        assertEquals("A0", preBuild.getLeftCastle());
        assertEquals("HeroSpawn", preBuild.getMapInfo().get("A10"));
        assertEquals("A10", preBuild.getLeftHeroPlacement());
        assertEquals("Castle", preBuild.getMapInfo().get("T19"));
        assertEquals("T19", preBuild.getRightCastle());
        assertEquals("HeroSpawn", preBuild.getMapInfo().get("T10"));
        assertEquals("T10", preBuild.getRightHeroPlacement());
    }

    @AfterEach
    public void cleanUpPreBuilds(){
        File file = new File("savedPreBuilds/Test.pb");
        if (file.exists()) {
            file.delete();
        }
    }

}
