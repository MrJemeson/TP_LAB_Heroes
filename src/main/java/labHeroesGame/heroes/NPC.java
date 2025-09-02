package labHeroesGame.heroes;

import labHeroesGame.buildings.ThreadBuilding;
import labHeroesGame.player.BasicPlayer;

public class NPC extends BasicHero{
    private ThreadBuilding prevBuild = null;
    public NPC(BasicPlayer player){
        super(player);
        setName("Посетитель");
        setPrice(1);
        setSpeed(1);
        player.getHeroArmy().add(this);
    }

    public ThreadBuilding getPrevBuild() {
        return prevBuild;
    }

    public void setPrevBuild(ThreadBuilding prevBuild) {
        this.prevBuild = prevBuild;
    }

    @Override
    public void refill() {
        getArmy().clear();
    }
}
