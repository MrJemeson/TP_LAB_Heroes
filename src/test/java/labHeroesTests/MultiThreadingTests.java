package labHeroesTests;

import labHeroesGame.Game;
import labHeroesGame.authorization.User;
import labHeroesGame.battlefields.preBuilds.MapCreator;
import labHeroesGame.battlefields.preBuilds.MapPreBuilds;
import labHeroesGame.battlefields.preBuilds.PreBuild;
import labHeroesGame.battlefields.squares.Square;
import labHeroesGame.buildings.ThreadBuildingService;
import labHeroesGame.heroes.AlmostGod;
import labHeroesGame.heroes.BasicHero;
import labHeroesGame.heroes.ChampLight;
import labHeroesGame.heroes.NPC;
import labHeroesGame.player.BasicPlayer;
import labHeroesGame.player.Bot;
import labHeroesGame.player.Human;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class MultiThreadingTests {
    private static Game game;

    @BeforeAll
    public static void createPreBuild(){
        ByteArrayInputStream in = new ByteArrayInputStream(("Test\n5\nA19\n1\n5\nA17\n1\n5\nC19\n1\n5\nT0\n5\nT2\n5\nR0\n4\nA0\n1\n4\nT19\n4\nA10\n1\n4\nT10\n4\nG1\n4\nI1\n4\nK1\nend\n").getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        MapCreator.createPreBuild(scanner, new User("Tester", "test"));
        BasicPlayer player1 = new Human();
        BasicPlayer player2 = new Bot();
        new ChampLight(player1);
        new AlmostGod(player2);
        PreBuild preBuild = MapPreBuilds.getCustomPreBuilds().stream().filter(x -> Objects.equals(x.getName(), "Test")).toList().get(0);
        game = new Game(player1, player2, preBuild, new User("Tester", "test"));
    }

    @Test
    public void testEnteringBuilding() {
        ByteArrayInputStream in = new ByteArrayInputStream(("y\n").getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        game.getLeftPlayer().setScanner(scanner);
        BasicHero hero = game.getLeftPlayer().getHeroArmy().get(0);
        game.getHeroPlacement().put(hero, "F2");
        game.getMap().getSquare("F2").setPeacefulOccupancy(hero);
        ArrayList<Square> fullWay = game.getMap().findPathOrdinaryEXPERIMENTAL("F2", "G1");
        game.movingHero(hero, fullWay);
        assertFalse(game.getHeroPlacement().containsKey(hero));
        for(ThreadBuildingService service: game.getHotel().getServices()) {
            if(service.getHeroOccupancy().equals(hero)) {
                return;
            }
        }
        fail("Hero is not in building");
    }

    @Test
    public void testWaitingFullBuilding() {
        ByteArrayInputStream in = new ByteArrayInputStream(("skip\n").getBytes());
        System.setIn(in);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        Scanner scanner = new Scanner(System.in);
        for(int i = 0; i < 10; i++) {
            NPC npc = new NPC(new Human());
            game.getNpcList().add(npc);
            game.findActivity(npc);
        }
        game.getLeftPlayer().setScanner(scanner);
        BasicHero hero = game.getLeftPlayer().getHeroArmy().get(0);
        game.getHeroPlacement().put(hero, "F2");
        game.getMap().getSquare("F2").setPeacefulOccupancy(hero);
        ArrayList<Square> fullWay = game.getMap().findPathOrdinaryEXPERIMENTAL("F2", "G1");
        game.movingHero(hero, fullWay);
        assertEquals(output.toString().substring(278, 326), "Все места в " + game.getHotel().getName() + " заняты. skip = выход из здания");
    }

    @AfterAll
    public static void cleanUpPreBuilds(){
        File file = new File("savedPreBuilds/Test.pb");
        MapPreBuilds.getCustomPreBuilds().remove(MapPreBuilds.getCustomPreBuilds().stream().filter(x -> Objects.equals(x.getName(), "Test")).toList().get(0));
        if (file.exists()) {
            file.delete();
        }
    }
}
