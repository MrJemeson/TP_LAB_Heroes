package labHeroesGame.heroes;

import labHeroesGame.player.BasicPlayer;
import labHeroesGame.units.BowGuy;
import labHeroesGame.units.SpearGuy;

public class CastleHero extends BasicHero {
    public CastleHero(BasicPlayer player) {
        super(player);
        setName("Стражник Замка");
        setPrice(0);
        setSpeed(0);
        addUnit(new BowGuy(5));
        addUnit(new SpearGuy(10 + getPlayerOwner().getPersonalTowers().size()*3));
//        addUnit(new CavalryGuy(5));
    }

    @Override
    public void refill() {
        getArmy().clear();
        addUnit(new BowGuy(5));
        addUnit(new SpearGuy(10 + getPlayerOwner().getPersonalTowers().size()*3));
//        addUnit(new CavalryGuy(5));
    }
}
