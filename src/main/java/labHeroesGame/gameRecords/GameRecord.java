package labHeroesGame.gameRecords;

import labHeroesGame.authorization.User;
import labHeroesGame.player.BasicPlayer;

import java.io.Serial;
import java.io.Serializable;

public class GameRecord implements Serializable {
    private final User userRecord;
    private final int numOfRounds;
    private final BasicPlayer leftPlayer;
    private final BasicPlayer rightPlayer;

    @Serial
    private static final long serialVersionUID = 1L;

    public GameRecord(User userRecord, int numOfRounds, BasicPlayer leftPlayer, BasicPlayer rightPlayer) {
        this.userRecord = userRecord;
        this.numOfRounds = numOfRounds;
        this.leftPlayer = leftPlayer;
        this.rightPlayer = rightPlayer;
    }

    public User getUserRecord() {
        return userRecord;
    }

    public int getNumOfRounds() {
        return numOfRounds;
    }

    public BasicPlayer getLeftPlayer() {
        return leftPlayer;
    }

    public BasicPlayer getRightPlayer() {
        return rightPlayer;
    }
}
