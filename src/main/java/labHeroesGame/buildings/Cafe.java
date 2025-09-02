package labHeroesGame.buildings;

import labHeroesGame.Game;
import labHeroesGame.heroes.BasicHero;
import labHeroesGame.units.BasicUnit;

public class Cafe extends ThreadBuilding {
    public Cafe(Game game, String placement, int occupancyTime) {
        super(game, placement, occupancyTime);
        setName("Кафе");
        setNumOfOccupants(3);
    }

    @Override
    public void serviceAction(BasicHero hero) {
        for(BasicUnit unit: hero.getArmy()) {
            unit.upgradeSpeed(1.2f);
        }
    }
}
