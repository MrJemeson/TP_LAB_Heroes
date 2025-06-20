package labHeroesGame.battles;


import labHeroesGame.Render;
import labHeroesGame.battlefields.Castlefield;
import labHeroesGame.buildings.Castle;
import labHeroesGame.heroes.BasicHero;

public class CastleBattle extends Battle{
    private int roundCCount = 0;

    public CastleBattle(BasicHero hero1, BasicHero hero2, Castle castle) {
        super(hero1, hero2);
        setBattlefield(new Castlefield(10));
    }

    @Override
    public void specialEvent() {
        if(roundCCount < 3){
            roundCCount++;
            Render.displayCastleSpecialEvent();
            switch (roundCCount) {
                case 1 -> {
                    getBattlefield().setObstacle("F7", false);
                    getBattlefield().setObstacle("F8", false);
                    getBattlefield().setObstacle("G7", false);
                    getBattlefield().setObstacle("G8", false);
                }
                case 2 -> {
                    getBattlefield().setObstacle("F4", false);
                    getBattlefield().setObstacle("F5", false);
                    getBattlefield().setObstacle("G4", false);
                    getBattlefield().setObstacle("G5", false);
                }
                case 3 -> {
                    getBattlefield().setObstacle("F1", false);
                    getBattlefield().setObstacle("F2", false);
                    getBattlefield().setObstacle("G1", false);
                    getBattlefield().setObstacle("G2", false);
                }
            }
        }
    }
}
