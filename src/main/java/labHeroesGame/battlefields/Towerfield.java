package labHeroesGame.battlefields;

public class Towerfield extends Battlefield {
    public Towerfield(int size){
        super(size);
        MapPreBuilds.useBattlePreBuild(this, MapPreBuilds.getTowerBattlePreBuild());
    }
}
