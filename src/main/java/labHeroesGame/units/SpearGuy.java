package labHeroesGame.units;

import labHeroesGame.heroes.BasicHero;

public class SpearGuy extends BasicUnit{
    public SpearGuy(int amountCreated, BasicHero hero) {
        super(amountCreated, hero);
        setName("Копейщики");
        setSpeed(4);
        setPower(5);
        setHp(10);
        setRewardSingle(10);
        setReward(amountCreated*getRewardSingle());
        setRange(2);
    }

    public SpearGuy(int amountCreated) {
        super(amountCreated);
        setName("Копейщики");
        setSpeed(4);
        setPower(5);
        setHp(10);
        setRewardSingle(10);
        setReward(amountCreated*getRewardSingle());
        setRange(2);
    }
}
