package labHeroesGame.player;

import labHeroesGame.Game;
import labHeroesGame.battles.Battle;
import labHeroesGame.buildings.Castle;
import labHeroesGame.buildings.Tower;
import labHeroesGame.heroes.BasicHero;
import labHeroesGame.units.BasicUnit;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class BasicPlayer implements Serializable {
    transient private Scanner scanner;
    private ArrayList<BasicHero> heroArmy = new ArrayList<>();
    private String name;
    private int personalGold = 0;
    private ArrayList<Tower> personalTowers = new ArrayList<>();
    private Castle castle;
    private ArrayList<BasicHero> killed = new ArrayList<>();

    @Serial
    private static final long serialVersionUID = 1L;

    public Castle getCastle() {
        return castle;
    }

    public void setCastle(Castle castle) {
        this.castle = castle;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public BasicPlayer(Scanner scanner) {
        this.scanner = scanner;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setHeroArmy(ArrayList<BasicHero> heroArmy) {
        this.heroArmy = heroArmy;
    }

    public void setPersonalTowers(ArrayList<Tower> personalTowers) {
        this.personalTowers = personalTowers;
    }

    public ArrayList<BasicHero> getKilled() {
        return killed;
    }

    public int getPersonalGold() {
        return personalGold;
    }

    public void takePersonalGold(int gold) {
        personalGold -= gold;
    }

    public abstract void requestMoveHero(Game game, BasicHero hero);

    public ArrayList<Tower> getPersonalTowers() {
        return personalTowers;
    }

    public BasicPlayer() {
        heroArmy = new ArrayList<>();
    }

    public void addPersonalGold(int gold){
        personalGold += gold;
    }

    public void addHero(BasicHero newHero) {
        heroArmy.add(newHero);
    }

    public ArrayList<BasicHero> getHeroArmy() {
        return heroArmy;
    }

    public abstract void buyHero(Game game);

    public abstract String toString();

    public abstract boolean equals(Object obj);

    public abstract void requestMoveUnit(Battle battle, BasicHero hero, BasicUnit unit);
}
