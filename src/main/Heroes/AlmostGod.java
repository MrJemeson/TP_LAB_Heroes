package main.Heroes;

import main.Player.BasicPlayer;
import main.Units.BowGuy;
import main.Units.CavalryGuy;
import main.Units.SpearGuy;

public class AlmostGod extends BasicHero{
    public AlmostGod(BasicPlayer player){
        super(player);
        setName("Почти Бог");
        setPrice(100);
        setSpeed(10);
        addUnit(new BowGuy(10));
        addUnit(new SpearGuy(15));
        addUnit(new CavalryGuy(10));
        player.getHeroArmy().add(this);
        for(int i = 0; i < getArmy().size(); i++) {
            getArmy().get(i).upgradeUnit(new float[] {2F, 2F, 2F, 2F});
        }
    }

    @Override
    public void refill() {
        getArmy().clear();
        addUnit(new BowGuy(10));
        addUnit(new SpearGuy(15));
        addUnit(new CavalryGuy(10));
    }
}
