package labHeroesGame.heroes;

import labHeroesGame.player.BasicPlayer;
import labHeroesGame.units.BowGuy;
import labHeroesGame.units.CavalryGuy;
import labHeroesGame.units.SpearGuy;

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
        addUnit(new SpearGuy(15 + getPlayerOwner().getPersonalTowers().size()));
        addUnit(new CavalryGuy(10));
    }
}
