package labHeroesGame.battlefields;

public class MainMap extends Battlefield{
    public MainMap() {
        super(20);
        MapPreBuilds.useBattlePreBuild(this, MapPreBuilds.getMainMapPreBuild());
    }
}
