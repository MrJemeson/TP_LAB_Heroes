package labHeroesGame.battlefields;

import labHeroesGame.battlefields.preBuilds.MapPreBuilds;

public class Castlefield extends Battlefield{
    public Castlefield(int size){
        super(size);
        MapPreBuilds.useBattlePreBuild(this, MapPreBuilds.getCastleBattlePreBuild().getMapInfo());
    }
}
