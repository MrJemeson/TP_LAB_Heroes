package main.Units;

import main.Heroes.BasicHero;

public class CavalryGuy extends BasicUnit {
    public CavalryGuy(int amountCreated, BasicHero hero) {
        super(amountCreated, hero);
        setName("Кавалерия");
        setSpeed(6);
        setPower(10);
        setHp(5);
        setRewardSingle(15);
        setReward(amountCreated*getRewardSingle());
        setRange(1);
    }

    public CavalryGuy(int amountCreated) {
        super(amountCreated);
        setName("Кавалерия");
        setSpeed(6);
        setPower(15);
        setHp(5);
        setRewardSingle(15);
        setReward(amountCreated*getRewardSingle());
        setRange(1);
    }
}
