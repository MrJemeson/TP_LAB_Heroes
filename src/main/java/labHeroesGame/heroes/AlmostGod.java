package labHeroesGame.heroes;


import labHeroesGame.player.BasicPlayer;
import labHeroesGame.units.BowGuy;
import labHeroesGame.units.CavalryGuy;
import labHeroesGame.units.SpearGuy;

public class AlmostGod extends BasicHero{
    public AlmostGod(BasicPlayer player){
        super(player);
        setName("Почти Бог");
        setPrice(100);
        setSpeed(100);
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
        for(int i = 0; i < getArmy().size(); i++) {
            getArmy().get(i).upgradeUnit(new float[] {2F, 2F, 2F, 2F});
        }
    }
}
