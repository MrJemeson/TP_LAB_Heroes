package labHeroesGame.units;

import labHeroesGame.heroes.BasicHero;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public abstract class BasicUnit implements Serializable {
    private String name;
    private BasicHero heroOwner;
    private int amount;
    private int speed;
    private int power;
    private int hp;
    private int rewardSingle;
    private int reward;
    private int range;

    @Serial
    private static final long serialVersionUID = 1L;

    public List<Integer> showStats(){
        return Arrays.asList(speed, power, hp, rewardSingle, range);
    }

    BasicUnit(int amountCreated, BasicHero hero){
        amount = amountCreated;
        heroOwner = hero;
    }

    BasicUnit(int amountCreated){
        amount = amountCreated;
    }

    public void add(int amountAdded) {
        amount += amountAdded;
        reward += amountAdded*rewardSingle;
    }

    public int dealDamage(){
        return amount*power;
    }

    public boolean takeDamageOrDie(int damage) {
        int amountKilled = damage/hp;
        amount -= amountKilled;
        return amount <= 0;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void setRewardSingle(int rewardSingle) {
        this.rewardSingle = rewardSingle;
    }

    @Override
    public String toString() {
        return name + " (" + amount + ", " + heroOwner + ")";
    }

    public int getRewardSingle() {
        return rewardSingle;
    }

    public int getAmount() {
        return amount;
    }

    public BasicHero getHeroOwner() {
        return heroOwner;
    }

    public void setHeroOwner(BasicHero heroOwner) {
        this.heroOwner = heroOwner;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHp() {
        return hp;
    }

    public int getPower() {
        return power;
    }

    public int getReward() { return reward; }

    public int getRange() {
        return range;
    }

    public void upgradeSpeed(float multiplier) { speed = (int)(speed*multiplier); }

    public void upgradeHp(float multiplier) { hp = (int)(hp*multiplier);}

    public void upgradePower(float multiplier) { power = (int)(power*multiplier);}

    public void upgradeReward(float multiplier) { reward = (int)(reward*multiplier); }

    public void upgradeUnit(float[] multipliers) { // multipliers = [hp, power, speed, reward]
        upgradeHp(multipliers[0]);
        upgradePower(multipliers[1]);
        upgradeSpeed(multipliers[2]);
        upgradeReward(multipliers[3]);
    }

}