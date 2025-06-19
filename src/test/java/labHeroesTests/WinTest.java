package labHeroesTests;

import labHeroesGame.Game;
import labHeroesGame.battlefields.MapPreBuilds;
import labHeroesGame.heroes.AlmostGod;
import labHeroesGame.player.Human;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WinTest {
    private Game game;

    @BeforeEach
    public void createWinConditions() {
        Human player1 = new Human();
        Human player2 = new Human();
        new AlmostGod(player1);
        new AlmostGod(player2);
        new MapPreBuilds();
        game = new Game(player1, player2);
        player2.getCastle().getGuardian().getArmy().clear();
        player1.getCastle().getGuardian().getArmy().clear();
    }

    @Test
    public void testWinPlayer1() {
        ByteArrayInputStream in = new ByteArrayInputStream(("S1\n").getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        game.getLeftPlayer().setScanner(scanner);
        game.getRightPlayer().setScanner(scanner);
        assertTrue(game.startGame());
    }

    @Test
    public void testWinPlayer2() {
        ByteArrayInputStream in = new ByteArrayInputStream(("skip\nB18\n").getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        game.getLeftPlayer().setScanner(scanner);
        game.getRightPlayer().setScanner(scanner);
        assertFalse(game.startGame());
    }

    @AfterEach
    public void cleanUpWinConditions(){
        game = null;
    }
}

