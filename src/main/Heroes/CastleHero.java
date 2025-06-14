package main.Heroes;

import main.Player.BasicPlayer;
import main.Units.BowGuy;
import main.Units.CavalryGuy;
import main.Units.SpearGuy;

import java.util.ArrayList;

public class CastleHero extends BasicHero {
    public CastleHero(BasicPlayer player) {
        super(player);
        setName("Стражник Замка");
        setPrice(0);
        setSpeed(0);
        addUnit(new BowGuy(5));
        addUnit(new SpearGuy(10));
//        addUnit(new CavalryGuy(5));
    }

    @Override
    public void refill() {
        getArmy().clear();
        addUnit(new BowGuy(5));
        addUnit(new SpearGuy(10));
//        addUnit(new CavalryGuy(5));
    }
}
