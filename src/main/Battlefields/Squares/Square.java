package main.Battlefields.Squares;

import main.Buildings.Castle;
import main.Buildings.Tower;
import main.Heroes.BasicHero;
import main.Units.BasicUnit;

public class Square{
    private BasicUnit occupancy;
    private BasicHero peacefulOccupancy;
    private boolean obstacle;
    private boolean building;
    private String buildingType;
    private Tower towerReference;
    private Castle castleReference;
    private Square upSquare;
    private Square downSquare;
    private Square leftSquare;
    private Square rightSquare;
    private int gold = 0;


    public Square(boolean obstacle) {
        this.obstacle = obstacle;
        this.building = false;
    }

    public Tower getTowerReference() {
        return towerReference;
    }

    public void setTowerReference(Tower towerReference) {
        this.towerReference = towerReference;
    }

    public Castle getCastleReference() {
        return castleReference;
    }

    public void setCastleReference(Castle castleReference) {
        this.castleReference = castleReference;
    }

    public void addGold(int goldAmount){
        gold += goldAmount;
    }

    public int takeGold(){
        int amountTaken = gold;
        gold = 0;
        return amountTaken;
    }

    public int getGold() {
        return gold;
    }

    public Square() {
        this.obstacle = false;
        this.building = false;
    }

    public void setBuilding(String buildingType) {
        building = true;
        this.buildingType = buildingType;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public void setRoad() {
        this.obstacle = false;
        this.building = false;
        buildingType = "#Road";
    }

    public boolean isRoad(){
        return buildingType != null &&  buildingType.charAt(0) == '#';
    }

    public void setObstacle() {
        this.obstacle = true;
    }

    public void setObstacle(boolean obstacle) {
        this.obstacle = obstacle;
    }

    public boolean isObstacle() {
        return obstacle;
    }

    public boolean isBuilding() {
        return building;
    }

    public void setOccupancy(BasicUnit unit) {
        this.occupancy = unit;
    }

    public BasicUnit getOccupancy() {
        return occupancy;
    }

    public Square getDownSquare() {
        return downSquare;
    }

    public void setPeacefulOccupancy(BasicHero peacefulOccupancy) {
        this.peacefulOccupancy = peacefulOccupancy;
    }

    public BasicHero getPeacefulOccupancy() {
        return peacefulOccupancy;
    }

    public void setDownSquare(Square downSquare) {
        this.downSquare = downSquare;
    }

    public void setUpSquare(Square upSquare) {
        this.upSquare = upSquare;
    }

    public void setLeftSquare(Square leftSquare) {
        this.leftSquare = leftSquare;
    }

    public void setRightSquare(Square rightSquare) {
        this.rightSquare = rightSquare;
    }

    public Square getUpSquare() {
        return upSquare;
    }

    public Square getLeftSquare() {
        return leftSquare;
    }

    public Square getRightSquare() {
        return rightSquare;
    }

    public boolean isOccupied() {
        return occupancy != null;
    }

    public boolean isPeacefulOccupied() {
        return peacefulOccupancy != null;
    }

    @Override
    public int hashCode() {
        return System.identityHashCode(this);
    }
}
