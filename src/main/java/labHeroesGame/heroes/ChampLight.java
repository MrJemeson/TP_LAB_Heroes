package labHeroesGame.heroes;


import labHeroesGame.player.BasicPlayer;
import labHeroesGame.units.BowGuy;
import labHeroesGame.units.CavalryGuy;
import labHeroesGame.units.SpearGuy;

public class ChampLight extends BasicHero{
    public ChampLight(BasicPlayer player){
        super(player);
        setName("Чемпион Света");
        setPrice(2000);
        setSpeed(5);
        addUnit(new BowGuy(5));
        addUnit(new SpearGuy(10));
        addUnit(new CavalryGuy(5));
        player.getHeroArmy().add(this);
        for(int i = 0; i < getArmy().size(); i++) {
            getArmy().get(i).upgradeUnit(new float[] {1.2F, 1.2F, 1.2F, 1.5F});
        }
    }

    @Override
    public void refill() {
        getArmy().clear();
        addUnit(new BowGuy(5));
        addUnit(new SpearGuy(10));
        addUnit(new CavalryGuy(5));
    }
}
