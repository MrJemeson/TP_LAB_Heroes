package labHeroesGame.buildings;

import labHeroesGame.Game;
import labHeroesGame.heroes.BasicHero;
import labHeroesGame.units.BasicUnit;


public class Hotel extends ThreadBuilding {
    public Hotel(Game game, String placement, int occupancyTime) {
        super(game, placement, occupancyTime);
        setName("Отель");
        setNumOfOccupants(5);
    }

    @Override
    public void serviceAction(BasicHero hero) {
        for(BasicUnit unit: hero.getArmy()) {
            unit.upgradeHp(1.2f);
        }
    }
}
