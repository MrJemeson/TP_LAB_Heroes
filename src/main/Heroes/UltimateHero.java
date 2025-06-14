package main.Heroes;

import main.Player.BasicPlayer;
import main.Units.BowGuy;
import main.Units.CavalryGuy;
import main.Units.SpearGuy;

public class UltimateHero extends BasicHero {
    public UltimateHero(BasicPlayer player){
        super(player);
        setName("Абсолютная легенда");
        setPrice(100);
        setSpeed(10);
        addUnit(new BowGuy(10));
        addUnit(new SpearGuy(15));
        addUnit(new CavalryGuy(10));
        player.getHeroArmy().add(this);
    }

    @Override
    public void refill() {
        getArmy().clear();
        addUnit(new BowGuy(10));
        addUnit(new SpearGuy(15));
        addUnit(new CavalryGuy(10));
    }
}
