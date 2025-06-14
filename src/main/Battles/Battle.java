package main.Battles;

import main.Battlefields.Battlefield;
import main.Battlefields.Squares.IdConverter;
import main.Battlefields.Squares.Square;
import main.Heroes.BasicHero;
import main.Render;
import main.Units.BasicUnit;

import java.util.ArrayList;
import java.util.HashMap;

public class Battle {
    private BasicHero leftHero;
    private BasicHero rightHero;
    private Battlefield battlefield;
    private ArrayList<BasicUnit> allUnits = new ArrayList<>();
    private HashMap<BasicUnit, String> unitsPlacement = new HashMap<>();
    private int goldBank = 0;
    private String battleInfo;


    public void setBattlefield(Battlefield battlefield) {
        this.battlefield = battlefield;
    }

    public Battle(BasicHero hero1, BasicHero hero2){
        leftHero = hero1;
        rightHero = hero2;
        this.battlefield = new Battlefield(10);
        allUnits.addAll(leftHero.getArmy());
        allUnits.addAll(rightHero.getArmy());
    }

    public String getBattleInfo() {
        return battleInfo;
    }

    public void setBattleInfo(String battleInfo) {
        this.battleInfo = battleInfo;
    }

    public void addBattleInfo(String info){
        battleInfo += info;
    }

    protected void fillBattleInfo() {
        battleInfo = "Все юниты в битве:";
        for(BasicUnit unit : allUnits) {
            battleInfo += "\n" + unit.getHeroOwner().getPlayerOwner().toString() + " " + unit+ " : " + unitsPlacement.get(unit);
        }
    }

    public boolean startBattle(){
        placeUnits();
        fillBattleInfo();
        return battleRound();
    }

    protected boolean checkWin(){
        return rightHero.getArmy().isEmpty();
    }

    protected void specialEvent() {}

    public int getGoldBank() {
        return goldBank;
    }

    public void addGoldBank(int gold) {
        goldBank += gold;
    }

    public void takeGoldFromBank(int gold) {
        goldBank -= gold;
    }

    public ArrayList<BasicUnit> getAllUnits() {
        return allUnits;
    }

    public HashMap<BasicUnit, String> getUnitsPlacement() {
        return unitsPlacement;
    }

    public BasicHero getLeftHero() {
        return leftHero;
    }

    public BasicHero getRightHero() {
        return rightHero;
    }

    public Battlefield getBattlefield() {
        return battlefield;
    }

    public Square actualMovingUnit(BasicHero hero, BasicUnit unit, ArrayList<Square> preferredWay, Square start, int range) {
        preferredWay.get(range).setOccupancy(unit);
        getUnitsPlacement().replace(unit, getBattlefield().getSquareId(preferredWay.get(range)));
        start.setOccupancy(null);
        takeGoldOnMove(hero, start, preferredWay);
        return getBattlefield().getSquare(getUnitsPlacement().get(unit));
    }

    public void takeGoldOnMove(BasicHero hero,Square start, ArrayList<Square> preferredWay) {
        Square goldTaker = start;
        int i = 0;
        int gold;
        while (!goldTaker.isOccupied()) {
            goldTaker = preferredWay.get(i);
            gold = goldTaker.takeGold();
            takeGoldFromBank(gold);
            hero.getPlayerOwner().addPersonalGold(goldTaker.takeGold());
            i++;
        }
    }

    public void killUnit(BasicUnit enemyUnit, Square target){
        addGoldBank(enemyUnit.getReward());
        target.addGold(enemyUnit.getReward());
        enemyUnit.getHeroOwner().getArmy().remove(enemyUnit);
        getAllUnits().remove(enemyUnit);
        getUnitsPlacement().remove(enemyUnit);
        target.setOccupancy(null);
    }

    public void simplifiedMovingUnit(BasicHero hero, BasicUnit unit, ArrayList<Square> preferredWay, Square target) {
        Square start = getBattlefield().getSquare(getUnitsPlacement().get(unit));
        if(preferredWay.size() > unit.getSpeed()) {
            start = actualMovingUnit(hero, unit, preferredWay, start, unit.getSpeed() - 1);
        } else {
            start = actualMovingUnit(hero, unit, preferredWay, start, preferredWay.size()-1);
        }
        if(getBattlefield().findPathNoLimit(start, target).size() <= unit.getRange()) {
            if(target.isOccupied() && !target.getOccupancy().equals(unit)) {
                BasicUnit enemyUnit = target.getOccupancy();
                Render.displayUnitAttackMessage(this, unit, enemyUnit);
                if (enemyUnit.takeDamageOrDie(unit.dealDamage())) {
                    Render.displayUnitDeathMessage(this, enemyUnit);
                    killUnit(enemyUnit, target);
                }
            }
        }
    }

    public void movingUnit(BasicHero hero, BasicUnit unit, ArrayList<Square> preferredWay, Square target) {
        Square start = getBattlefield().getSquare(getUnitsPlacement().get(unit));
            if (preferredWay.size() - unit.getSpeed() <= unit.getRange()) {
                if (preferredWay.size() > unit.getRange()) {
                    actualMovingUnit(hero, unit, preferredWay, start, preferredWay.size() - 1 - unit.getRange());
                }
                BasicUnit enemyUnit = target.getOccupancy();
                if(!enemyUnit.equals(unit)) {
                    Render.displayUnitAttackMessage(this, unit, enemyUnit);
                    if (enemyUnit.takeDamageOrDie(unit.dealDamage())) {
                        Render.displayUnitDeathMessage(this, enemyUnit);
                        killUnit(enemyUnit, target);
                    }
                }

            } else {
                actualMovingUnit(hero, unit, preferredWay, start, unit.getSpeed() - 1);;
            }

    }

    protected void placeUnits() {
        int x = 0;
        int y = 0;
        for (int i = 0; i < leftHero.getArmy().size(); i++) {
            battlefield.placeUnit(battlefield.getSquare(x, y), leftHero.getArmy().get(i));
            unitsPlacement.put(leftHero.getArmy().get(i), IdConverter.convertToStringID(x, y));
            if(x == 0 && y == 8) {
                y = 1;
                x = 1;
            }
            else y += 2;
        }
        x = 8;
        y = 0;
        for (int i = 0; i < rightHero.getArmy().size(); i++) {
            battlefield.placeUnit(battlefield.getSquare(x, y), rightHero.getArmy().get(i));
            unitsPlacement.put(rightHero.getArmy().get(i), IdConverter.convertToStringID(x, y));
            if(x == 8 && y == 8) {
                y = 1;
                x = 9;
            }
            else y += 2;
        }
    }
    protected boolean battleRound(){
        int roundPhase = 1;
        Render.displayMap(battlefield);
        Render.displayBattleInfo(this);
        ArrayList<BasicUnit> cloneAllUnits = (ArrayList<BasicUnit>) allUnits.clone();
        for (int i = 0; i < cloneAllUnits.size(); i++){
            if(!allUnits.contains(cloneAllUnits.get(i))) {
                continue;
            }
            if(!cloneAllUnits.get(i).getHeroOwner().equals(cloneAllUnits.get(0).getHeroOwner())) {
                roundPhase = 2;
            }
            switch (roundPhase) {
                case 1 -> leftHero.getPlayerOwner().requestMoveUnit(this, leftHero, cloneAllUnits.get(i));
                case 2 -> rightHero.getPlayerOwner().requestMoveUnit(this, rightHero, cloneAllUnits.get(i));
            }
            Render.displayMap(battlefield);
            fillBattleInfo();
            Render.displayBattleInfo(this);
            if (leftHero.getArmy().isEmpty() || checkWin()) {
                if (checkWin()) {
                    leftHero.getPlayerOwner().addPersonalGold(goldBank);
                } else {
                    rightHero.getPlayerOwner().addPersonalGold(goldBank);
                }
                return checkWin();
            }
        }
        specialEvent();
        if (leftHero.getArmy().isEmpty() || checkWin()){
            return checkWin();
        }
        return battleRound();
    }
}
