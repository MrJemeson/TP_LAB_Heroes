package labHeroesGame.battlefields;

import labHeroesGame.battlefields.preBuilds.MapPreBuilds;

public class Towerfield extends Battlefield {
    public Towerfield(int size){
        super(size);
        MapPreBuilds.useBattlePreBuild(this, MapPreBuilds.getTowerBattlePreBuild().getMapInfo());
    }
}
