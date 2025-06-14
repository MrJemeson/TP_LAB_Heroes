package main.Heroes;

import main.Player.BasicPlayer;
import main.Units.BowGuy;
import main.Units.CavalryGuy;
import main.Units.SpearGuy;


public class TowerHero extends BasicHero{
    public TowerHero(BasicPlayer player) {
        super(player);
        setName("Стражник Башни");
        setPrice(0);
        setSpeed(0);
        addUnit(new BowGuy(5));
        addUnit(new SpearGuy(10));
        addUnit(new CavalryGuy(5));
    }

    @Override
    public void refill() {
        getArmy().clear();
        addUnit(new BowGuy(5));
        addUnit(new SpearGuy(10));
        addUnit(new CavalryGuy(5));
    }
}
