package labHeroesGame.battlefields;

public class Castlefield extends Battlefield{
    public Castlefield(int size){
        super(size);
        MapPreBuilds.useBattlePreBuild(this, MapPreBuilds.getCastleBattlePreBuild());
    }
}
