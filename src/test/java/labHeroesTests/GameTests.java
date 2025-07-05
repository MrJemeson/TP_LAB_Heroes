package labHeroesTests;

import labHeroesGame.Game;
import labHeroesGame.Render;
import labHeroesGame.authorization.User;
import labHeroesGame.battlefields.preBuilds.MapPreBuilds;
import labHeroesGame.battlefields.squares.Square;
import labHeroesGame.buildings.Tower;
import labHeroesGame.heroes.AlmostGod;
import labHeroesGame.heroes.BasicHero;
import labHeroesGame.heroes.ChampLight;
import labHeroesGame.player.BasicPlayer;
import labHeroesGame.player.Bot;
import labHeroesGame.player.Human;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class GameTests {
    private Game game;

    @BeforeEach
    public void createGame(){
        new MapPreBuilds();
        BasicPlayer player1 = new Human();
        BasicPlayer player2 = new Bot();
        new ChampLight(player1);
        new AlmostGod(player2);
        game = new Game(player1, player2, MapPreBuilds.getMainMapPreBuild(), new User("Tester", "test"));
    }

    @Test
    public void testMovingPenalty() {
        BasicHero hero = game.getLeftPlayer().getHeroArmy().get(0);
        game.getMap().getSquare("J9").setPeacefulOccupancy(hero);
        game.getHeroPlacement().put(hero, "J9");
        ArrayList<Square> fullWay = game.getMap().findPathOrdinaryEXPERIMENTAL("J9", "D3");
        game.movingHero(hero, fullWay);
        ArrayList<Square> remainingWay = game.getMap().findPathOrdinaryEXPERIMENTAL(game.getHeroPlacement().get(hero), "D3");
        assertEquals(fullWay.size() - remainingWay.size(), hero.getSpeed());
        game.getMap().getSquare(game.getHeroPlacement().get(hero)).setPeacefulOccupancy(null);
        game.getMap().getSquare("J9").setPeacefulOccupancy(hero);
        game.getHeroPlacement().replace(hero, "J9");
        fullWay = game.getMap().findPathOrdinaryEXPERIMENTAL("J9", "Q2");
        game.movingHero(hero, fullWay);
        Render.displayMap(game.getMap());
        remainingWay = game.getMap().findPathOrdinaryEXPERIMENTAL(game.getHeroPlacement().get(hero), "Q2");
        assertEquals(fullWay.size() - remainingWay.size(), hero.getSpeed()*2);
    }

    @Test
    public void testWinPlayer() {
        game.getRightPlayer().getCastle().getGuardian().getArmy().clear();
        ByteArrayInputStream in = new ByteArrayInputStream(("S1\n").getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        game.getLeftPlayer().setScanner(scanner);
        game.getMap().getSquare("S2").setPeacefulOccupancy(game.getLeftPlayer().getHeroArmy().get(0));
        game.getHeroPlacement().put(game.getLeftPlayer().getHeroArmy().get(0), "S2");
        assertTrue(game.gameRound());
    }

    @Test
    public void testWinBot() {
        game.getLeftPlayer().getCastle().getGuardian().getArmy().clear();
        ByteArrayInputStream in = new ByteArrayInputStream(("skip\nskip\nskip\nskip\nskip\nskip\nskip\nskip\nskip\nskip\n").getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        game.getLeftPlayer().setScanner(scanner);
        assertFalse(game.startGame());
    }

    @Test
    public void testHeroDying(){
        ByteArrayInputStream in = new ByteArrayInputStream(("A1\nskip\nskip\nskip\nskip\nskip\nskip\nskip\nskip\nskip\n").getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        game.getLeftPlayer().setScanner(scanner);
        game.getRightPlayer().getHeroArmy().get(0).getArmy().clear();
        game.getMap().getSquare("A0").setPeacefulOccupancy(game.getLeftPlayer().getHeroArmy().get(0));
        game.getHeroPlacement().put(game.getLeftPlayer().getHeroArmy().get(0), "A0");
        game.getMap().getSquare("A1").setPeacefulOccupancy(game.getRightPlayer().getHeroArmy().get(0));
        game.getLeftPlayer().requestMoveHero(game, game.getLeftPlayer().getHeroArmy().get(0));
        assertFalse(game.getMap().getSquare("A1").isPeacefulOccupied());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Z0\nskip\n", "E7\nskip\n"})
    public void testMovingToImpossiblePlaces(String input){
        ByteArrayInputStream in = new ByteArrayInputStream((input).getBytes());
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        game.getLeftPlayer().setScanner(scanner);
        game.placeHeroes();
        game.getLeftPlayer().requestMoveHero(game, game.getLeftPlayer().getHeroArmy().get(0));
        assertTrue(output.toString().endsWith("Некорректная клетка. Выберите клетку: "));
    }

    @Test
    public void testBuildingRefill(){
        ByteArrayInputStream in = new ByteArrayInputStream(("G13\n").getBytes());
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        game.getLeftPlayer().setScanner(scanner);
        game.getLeftPlayer().getHeroArmy().get(0).getArmy().clear();
        game.placeHeroes();
        game.getLeftPlayer().requestMoveHero(game, game.getLeftPlayer().getHeroArmy().get(0));
        assertEquals(3,  game.getLeftPlayer().getHeroArmy().get(0).getArmy().size());
    }

    @Test
    public void testTowerEffect(){
        assertEquals(13, game.getLeftPlayer().getPersonalTowers().get(0).getGuardian().getArmy().get(1).getAmount());
        assertEquals(19, game.getLeftPlayer().getCastle().getGuardian().getArmy().get(1).getAmount());
        new Tower(game.getLeftPlayer());
        game.refillPlayerBuildings(game.getLeftPlayer());
        assertEquals(14, game.getLeftPlayer().getPersonalTowers().get(0).getGuardian().getArmy().get(1).getAmount());
    }

    @AfterEach
    public void cleanUpGame() {
        game = null;
    }
}
