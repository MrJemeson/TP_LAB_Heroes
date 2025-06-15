package labHeroesGame.player;

import labHeroesGame.Game;
import labHeroesGame.battles.Battle;
import labHeroesGame.buildings.Castle;
import labHeroesGame.buildings.Tower;
import labHeroesGame.heroes.BasicHero;
import labHeroesGame.units.BasicUnit;

import java.util.ArrayList;

public abstract class BasicPlayer {
    private ArrayList<BasicHero> heroArmy;
    private String name;
    private int personalGold = 0;
    private ArrayList<Tower> personalTowers = new ArrayList<>();
    private Castle castle;
    private ArrayList<BasicHero> killed = new ArrayList<>();

    public Castle getCastle() {
        return castle;
    }

    public void setCastle(Castle castle) {
        this.castle = castle;
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
