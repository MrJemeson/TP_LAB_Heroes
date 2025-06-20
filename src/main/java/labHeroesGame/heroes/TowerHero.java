package labHeroesGame.heroes;


import labHeroesGame.player.BasicPlayer;
import labHeroesGame.units.BowGuy;
import labHeroesGame.units.CavalryGuy;
import labHeroesGame.units.SpearGuy;

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
        addUnit(new SpearGuy(10 + getPlayerOwner().getPersonalTowers().size()));
        addUnit(new CavalryGuy(5));
    }
}
