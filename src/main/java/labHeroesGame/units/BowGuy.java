package labHeroesGame.units;

import labHeroesGame.heroes.BasicHero;

public class BowGuy extends BasicUnit{
    public BowGuy(int amountCreated, BasicHero hero) {
        super(amountCreated, hero);
        setName("Лучники");
        setSpeed(2);
        setPower(10);
        setHp(5);
        setRewardSingle(15);
        setReward(amountCreated*getRewardSingle());
        setRange(5);
    }

    public BowGuy(int amountCreated) {
        super(amountCreated);
        setName("Лучники");
        setSpeed(2);
        setPower(10);
        setHp(5);
        setRewardSingle(15);
        setReward(amountCreated*getRewardSingle());
        setRange(5);
    }
}
