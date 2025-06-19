package labHeroesGame.battles;

import labHeroesGame.Render;
import labHeroesGame.battlefields.Towerfield;
import labHeroesGame.battlefields.squares.IdConverter;
import labHeroesGame.battlefields.squares.Square;
import labHeroesGame.buildings.Tower;
import labHeroesGame.heroes.BasicHero;
import labHeroesGame.units.BasicUnit;

import java.util.ArrayList;
import java.util.Scanner;

public class TowerBattle extends Battle{
    private final Tower tower;

    public TowerBattle(BasicHero hero1, BasicHero hero2, Tower tower) {
        super(hero1, hero2);
        this.tower = tower;
        setBattlefield(new Towerfield(10));
    }

    @Override
    protected boolean checkWin() {
        return tower.getHealth() <= 0;
    }

    public Tower getTower() {
        return tower;
    }

    @Override
    protected void specialEvent() {
        ArrayList<Square> nearestEnemy = getBattlefield().findPathNoLimit("G4", getUnitsPlacement().get(getAllUnits().get(0)));
        for (int i = 1; i < getLeftHero().getArmy().size(); i++) {
            ArrayList<Square> curEnemy = getBattlefield().findPathNoLimit("G4", getUnitsPlacement().get(getAllUnits().get(i)));
            if (curEnemy.size() < nearestEnemy.size()) {
                nearestEnemy = curEnemy;
            }
        }
        BasicUnit enemyUnit = nearestEnemy.getLast().getOccupancy();
        Render.displayTowerAttack(getTower(), enemyUnit);
        if (enemyUnit.takeDamageOrDie(getTower().getDamage())) {
            Render.displayUnitDeathMessage(this, enemyUnit);
            killUnit(enemyUnit, nearestEnemy.getLast());
        }
    }

    @Override
    protected void placeUnits() {
        int x = 0;
        int y = 0;
        for (int i = 0; i < getLeftHero().getArmy().size(); i++) {
            getBattlefield().placeUnit(getBattlefield().getSquare(x, y), getLeftHero().getArmy().get(i));
            getUnitsPlacement().put(getLeftHero().getArmy().get(i), IdConverter.convertToStringID(x, y));
            if(x == 0 && y == 8) {
                y = 1;
                x = 1;
            }
            else y += 2;
        }
        x = 7;
        y = 0;
        int delta = 3;
        for (int i = 0; i < getRightHero().getArmy().size(); i++) {
            getBattlefield().placeUnit(getBattlefield().getSquare(x, y), getRightHero().getArmy().get(i));
            getUnitsPlacement().put(getRightHero().getArmy().get(i), IdConverter.convertToStringID(x, y));
            if(x == 7 && y == 9) {
                y = 2;
                x = 8;
                delta = 5;
            } else if (x == 8 && y == 7) {
                y = 0;
                x = 9;
                delta = 3;
            } else y += delta;
        }
    }

    @Override
    public void movingUnit(BasicHero hero, BasicUnit unit, ArrayList<Square> preferredWay, Square target) {
        Square start = getBattlefield().getSquare(getUnitsPlacement().get(unit));
        if (preferredWay.size() - unit.getSpeed() <= unit.getRange()) {
            if (preferredWay.size() > unit.getRange()) {
                actualMovingUnit(hero, unit, preferredWay, start, preferredWay.size() - 1 - unit.getRange());
            }
            if(target.isBuilding()) {
                Render.displayUnitAttackMessage(this, unit, getTower());
                getTower().attacked(unit.dealDamage());
            } else {
                BasicUnit enemyUnit = target.getOccupancy();
                Render.displayUnitAttackMessage(this, unit, enemyUnit);
                if (enemyUnit.takeDamageOrDie(unit.dealDamage())) {
                    Render.displayUnitDeathMessage(this, enemyUnit);
                    killUnit(enemyUnit, target);
                }
            }
        } else {
            actualMovingUnit(hero, unit, preferredWay, start, unit.getSpeed() - 1);
        }
    }
    @Override
    protected void fillBattleInfo(){
        setBattleInfo("Все юниты в битве:");
        for(BasicUnit unit : getAllUnits()) {
            addBattleInfo("\n" + unit.toString() + " : " + getUnitsPlacement().get(unit));
        }
        addBattleInfo("\n"+getTower().toString());
    }
}