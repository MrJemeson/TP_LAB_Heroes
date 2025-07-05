package labHeroesGame.buildings;

import labHeroesGame.heroes.BasicHero;
import labHeroesGame.heroes.CastleHero;
import labHeroesGame.player.BasicPlayer;

import java.io.Serializable;
import java.util.Objects;

public class Castle implements Serializable {
    private static int identifiers = 0;
    private int identifier;
    private BasicPlayer playerOwner;
    private BasicHero guardian;

    public Castle(BasicPlayer player) {
        identifier = identifiers;
        identifiers++;
        playerOwner = player;
        guardian = new CastleHero(player);
    }

    public BasicPlayer getPlayerOwner() {
        return playerOwner;
    }

    public BasicHero getGuardian() {
        return guardian;
    }

    public int getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return "Замок (" + identifier + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Castle castle)) return false;
        return identifier == castle.identifier;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(identifier);
    }
}
