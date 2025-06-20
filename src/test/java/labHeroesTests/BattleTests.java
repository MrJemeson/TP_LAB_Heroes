package labHeroesTests;

import labHeroesGame.battlefields.MapPreBuilds;
import labHeroesGame.battles.Battle;
import labHeroesGame.battles.CastleBattle;
import labHeroesGame.battles.TowerBattle;
import labHeroesGame.buildings.Castle;
import labHeroesGame.buildings.Tower;
import labHeroesGame.heroes.BasicHero;
import labHeroesGame.heroes.ChampLight;
import labHeroesGame.player.Bot;
import labHeroesGame.player.Human;
import labHeroesGame.units.BasicUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class BattleTests {
    private Battle battle;
    Human player1 = new Human();
    Bot player2 = new Bot();

    @BeforeEach
    public void createBattleConditions() {
        new MapPreBuilds();
        battle = new Battle(new ChampLight(player1), new ChampLight(player2));
    }

    @Test
    public void testRange() {
        battle.placeUnits();
        int i = 0;
        for(BasicUnit unit : battle.getLeftHero().getArmy()) {
            unit.upgradeUnit(new float[] {1F, 1F, 10F, 1F});
            battle.movingUnit(battle.getLeftHero(), unit, battle.getBattlefield().findPathOrdinaryEXPERIMENTAL("A" + i, "I" + i), battle.getBattlefield().getSquare("I" + i));
            assertEquals(battle.getBattlefield().findPathNoLimit(battle.getUnitsPlacement().get(unit), "I" + i).size(), unit.getRange());
            i += 2;
        }
    }

    @Test
    public void testBotActions(){
        ByteArrayInputStream in = new ByteArrayInputStream(("skip\nskip\nskip\nskip\nskip\nskip\nskip\n").getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        battle.getLeftHero().getPlayerOwner().setScanner(scanner);
        assertFalse(battle.startBattle());
    }

    @Test
    public void testUnitsDying(){
        battle.placeUnits();
        int i = 0;
        for(BasicUnit unit : battle.getLeftHero().getArmy()) {
            unit.upgradeUnit(new float[] {1F, 10F, 10F, 1F});
            battle.movingUnit(battle.getLeftHero(), unit, battle.getBattlefield().findPathOrdinaryEXPERIMENTAL("A" + i, "I" + i), battle.getBattlefield().getSquare("I" + i));
            assertFalse(battle.getBattlefield().getSquare("I" + i).isOccupied());
            i += 2;
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"Z0\nskip\n", "A0\nskip\n"})
    public void testMovingToImpossiblePlaces(String input){
        ByteArrayInputStream in = new ByteArrayInputStream((input).getBytes());
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        battle.getLeftHero().getPlayerOwner().setScanner(scanner);
        battle.placeUnits();
        player1.requestMoveUnit(battle, battle.getLeftHero(), battle.getAllUnits().get(0));
        assertTrue(output.toString().endsWith("Некорректная клетка. Выберите клетку: "));
    }

    @Test
    public void testBattleWin(){
        ByteArrayInputStream in = new ByteArrayInputStream(("I0\nI2\nI4\n").getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        battle.getLeftHero().getPlayerOwner().setScanner(scanner);
        for(BasicUnit unit : battle.getLeftHero().getArmy()) {
            unit.upgradeUnit(new float[]{1F, 10F, 10F, 1F});
        }
        assertTrue(battle.startBattle());
    }

    @Test
    public void testUnitsDroppingGold() {
        battle.placeUnits();
        int i = 0;
        for (BasicUnit unit : battle.getLeftHero().getArmy()) {
            unit.upgradeUnit(new float[]{1F, 10F, 10F, 1F});
            int gold = battle.getBattlefield().getSquare("I" + i).getOccupancy().getReward();
            battle.movingUnit(battle.getLeftHero(), unit, battle.getBattlefield().findPathOrdinaryEXPERIMENTAL("A" + i, "I" + i), battle.getBattlefield().getSquare("I" + i));
            assertEquals(battle.getBattlefield().getSquare("I" + i).getGold(), gold);
            i += 2;
        }
    }

    @Test
    public void testUnitAttacksTower(){
        ByteArrayInputStream in = new ByteArrayInputStream(("G4\n").getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        battle.getLeftHero().getPlayerOwner().setScanner(scanner);
        player1.getHeroArmy().clear();
        player2.getHeroArmy().clear();
        Tower tower = new Tower(player2);
        battle = new TowerBattle(new ChampLight(player1), new ChampLight(player2), tower);
        battle.placeUnits();
        battle.getLeftHero().getArmy().get(0).upgradeSpeed(10);
        player1.requestMoveUnit(battle, battle.getLeftHero(), battle.getLeftHero().getArmy().get(0));
        assertEquals(battle.getLeftHero().getArmy().get(0).dealDamage(), 100 - tower.getHealth());
    }

    @Test
    public void testTowerSpecialEvent(){
        player1.getHeroArmy().clear();
        player2.getHeroArmy().clear();
        Tower tower = new Tower(player2);
        BasicHero hero1 = new ChampLight(player1);
        BasicHero hero2 = new ChampLight(player2);
        battle = new TowerBattle(hero1, hero2, tower);
        battle.placeUnits();
        battle.getBattlefield().getSquare("E4").setOccupancy(hero1.getArmy().get(0));
        hero1.getArmy().get(0).add(100);
        battle.getUnitsPlacement().replace(hero1.getArmy().get(0), "E4");
        battle.specialEvent();
        assertNotEquals(105, hero1.getArmy().get(0).getAmount());
    }

    @Test
    public void testCastleSpecialEvent() {
        player1.getHeroArmy().clear();
        player2.getHeroArmy().clear();
        Castle castle = new Castle(player2);
        BasicHero hero1 = new ChampLight(player1);
        BasicHero hero2 = new ChampLight(player2);
        battle = new CastleBattle(hero1, hero2, castle);
        for (int i = 0; i < 3; i++) {
            battle.specialEvent();
            assertFalse(battle.getBattlefield().getSquare("F" + (7 - 3 * i)).isObstacle());
        }
    }


    @AfterEach
    public void cleanUpBattleConditions() {
        battle = null;
        player1 = null;
        player2 = null;
    }
}
