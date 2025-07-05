package labHeroesGame.battlefields;

import labHeroesGame.battlefields.preBuilds.MapPreBuilds;
import labHeroesGame.battlefields.preBuilds.PreBuild;

public class MainMap extends Battlefield{
    public MainMap(PreBuild preBuild) {
        super(20);
        MapPreBuilds.useBattlePreBuild(this, preBuild.getMapInfo());
    }
}
