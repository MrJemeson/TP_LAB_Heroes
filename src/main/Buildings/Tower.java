package main.Buildings;

import main.Heroes.TowerHero;
import main.Player.BasicPlayer;

public class Tower {
    private static int towerIdentifiers = 0;
    private final int towerIdentifier;
    private int health = 100;
    private int damage = 20;
    private BasicPlayer playerOwner;
    private TowerHero guardian;

    public Tower(BasicPlayer playerOwner){
        towerIdentifier =  towerIdentifiers;
        towerIdentifiers++;
        this.playerOwner = playerOwner;
        guardian = new TowerHero(playerOwner);
    }

    public TowerHero getGuardian() {
        return guardian;
    }

    public void heal(){
        health = 100;
    }

    public void refill(){
        guardian.refill();
        heal();
    }

    public void setHealth(int health){
        this.health = health;
    }

    public void setPlayerOwner(BasicPlayer playerOwner) {
        this.playerOwner = playerOwner;
    }

    public BasicPlayer getPlayerOwner() {
        return playerOwner;
    }

    public int getHealth() {
        return health;
    }

    public int getTowerIdentifier() {
        return towerIdentifier;
    }

    public boolean attacked(int damage) {
        health -= damage;
        return health <= 0;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public String toString() {
        return "Башня (id: " + towerIdentifier  + ", hp: " + health + ")";
    }
}

