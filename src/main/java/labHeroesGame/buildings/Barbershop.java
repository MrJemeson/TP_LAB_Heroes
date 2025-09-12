package labHeroesGame.buildings;

import labHeroesGame.Game;
import labHeroesGame.heroes.BasicHero;
import labHeroesGame.units.BasicUnit;



public class Barbershop extends ThreadBuilding {
    public Barbershop(Game game, String placement, int occupancyTime) {
        super(game, placement, occupancyTime);
        setName("Парикмахерская");
        setNumOfOccupants(2);
        setWorkTime(9, 18, 12, 13);
    }

    @Override
    public void serviceAction(BasicHero hero) {
        for(BasicUnit unit: hero.getArmy()) {
            unit.upgradePower(1.2f);
        }
    }
}
