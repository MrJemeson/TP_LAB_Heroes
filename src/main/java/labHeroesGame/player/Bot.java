package labHeroesGame.player;

import labHeroesGame.Game;
import labHeroesGame.Render;
import labHeroesGame.battlefields.squares.Square;
import labHeroesGame.battles.Battle;
import labHeroesGame.buildings.Castle;
import labHeroesGame.buildings.Tower;
import labHeroesGame.heroes.BasicHero;
import labHeroesGame.units.BasicUnit;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;


public class Bot extends BasicPlayer {
    private static int identifiers = 0;
    private int identifier;

    public Bot(){
        super();
        identifier = identifiers;
        identifiers++;
    }

    @Override
    public String toString() {
        return "Бот " + identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Bot bot)) return false;
        return identifier == bot.identifier;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(identifier);
    }

    @Override
    public void requestMoveHero(Game game, BasicHero hero) {
        Render.displayMovementBotMessage(this, hero, game.getHeroPlacement().get(hero));
        ArrayList<ObjectOfInterest> objectOfInterest = new ArrayList<>();
        for(int i = 0; i < game.getAllHeroes().size(); i++){
            Square place = game.getMap().getSquare(game.getHeroPlacement().get(game.getAllHeroes().get(i)));
            objectOfInterest.add(new ObjectOfInterest(place, game.getAllHeroes().get(i)));
        }
        for (int i = 0; i < game.getAllTowers().size(); i++) {
            Square place = game.getMap().getSquare(game.getTowerPlacement().get(game.getAllTowers().get(i)));
            objectOfInterest.add(new ObjectOfInterest(place, game.getAllTowers().get(i)));
        }
        objectOfInterest.add(new ObjectOfInterest(game.getMap().getSquare("B18"), game.getLeftPlayer().getCastle()));
        objectOfInterest.add(new ObjectOfInterest(game.getMap().getSquare("S1"), game.getRightPlayer().getCastle()));
        ArrayList<ObjectOfInterest> objOfInterest = objectOfInterest.stream()
                .filter(curObj -> !curObj.getPlayerOwner().equals(hero.getPlayerOwner()))
                .collect(Collectors.toCollection(ArrayList::new));
        Square start = game.getMap().getSquare(game.getHeroPlacement().get(hero));
        Square end = objOfInterest.get(0).place;
        ArrayList<Square> preferredWay = game.getMap().findPathOrdinaryEXPERIMENTAL(start, end);
        for (int i = 1; i < objOfInterest.size(); i++) {
            ObjectOfInterest obj = objOfInterest.get(i);
            ArrayList<Square> curWay = game.getMap().findPathOrdinaryEXPERIMENTAL(start, obj.place);
            if (preferredWay.size() > curWay.size()){
                preferredWay = curWay;
            }
        }
        game.movingHero(hero, preferredWay);
    }

    class ObjectOfInterest {
        private BasicHero interestHero;
        private Square place;
        private Tower interestTower;
        private Castle interestCastle;
        private BasicPlayer playerOwner;

        public ObjectOfInterest(Square place, BasicHero hero){
            this.place = place;
            interestHero = hero;
            playerOwner = hero.getPlayerOwner();
        }

        public ObjectOfInterest(Square place, Tower tower){
            this.place = place;
            interestTower = tower;
            playerOwner = tower.getPlayerOwner();
        }

        public ObjectOfInterest(Square place, Castle castle){
            this.place = place;
            interestCastle = castle;
            playerOwner = castle.getPlayerOwner();
        }

        public BasicHero getInterestHero() {
            return interestHero;
        }

        public boolean isHero(){
            return interestHero != null;
        }

        public boolean isTower(){
            return interestTower != null;
        }

        public boolean isCastle(){
            return interestCastle != null;
        }

        public BasicPlayer getPlayerOwner() {
            return playerOwner;
        }

        public void setPlayerOwner(BasicPlayer playerOwner) {
            this.playerOwner = playerOwner;
        }

        public void setInterestHero(BasicHero interestHero) {
            this.interestHero = interestHero;
        }

        public Square getPlace() {
            return place;
        }

        public void setPlace(Square place) {
            this.place = place;
        }

        public Tower getInterestTower() {
            return interestTower;
        }

        public void setInterestTower(Tower interestTower) {
            this.interestTower = interestTower;
        }

        public Castle getInterestCastle() {
            return interestCastle;
        }

        public void setInterestCastle(Castle interestCastle) {
            this.interestCastle = interestCastle;
        }
    }

    @Override
    public void requestMoveUnit(Battle battle, BasicHero hero, BasicUnit unit) {
        Render.displayMovementBotMessage(this, unit, battle.getUnitsPlacement().get(unit));
        ArrayList<BasicUnit> objOfInterest = battle.getAllUnits().stream()
                .filter(curUnit -> !curUnit.getHeroOwner().equals(hero))
                .collect(Collectors.toCollection(ArrayList::new));
        Square start = battle.getBattlefield().getSquare(battle.getUnitsPlacement().get(unit));
        ArrayList<Square> preferredWay;
        Square target;
        boolean notFullWay = false;
        if(!objOfInterest.isEmpty()) {
            Square end = battle.getBattlefield().getSquare(battle.getUnitsPlacement().get(objOfInterest.get(0)));
            preferredWay = battle.getBattlefield().findPathOrdinaryEXPERIMENTAL(start, end);
            notFullWay = preferredWay.isEmpty();
            if(notFullWay) {
                preferredWay = battle.getBattlefield().findPathNoLimit(start, battle.getBattlefield().getSquare(battle.getUnitsPlacement().get(objOfInterest.get(0))));
                for(int j = 0; j < preferredWay.size(); j++) {
                    if(preferredWay.get(j).isOccupied() || preferredWay.get(j).isObstacle() || preferredWay.get(j).isBuilding()) {
                        preferredWay = new ArrayList<Square>(preferredWay.subList(0, j));
                    }
                }
            }
            target = end;
            for (int i = 1; i < objOfInterest.size(); i++) {
                ArrayList<Square> curWay = battle.getBattlefield().findPathOrdinaryEXPERIMENTAL(start, battle.getBattlefield().getSquare(battle.getUnitsPlacement().get(objOfInterest.get(i))));
                Square curTarget = battle.getBattlefield().getSquare(battle.getUnitsPlacement().get(objOfInterest.get(i)));
                if(notFullWay && curWay.isEmpty()){
                    curWay = battle.getBattlefield().findPathNoLimit(start, battle.getBattlefield().getSquare(battle.getUnitsPlacement().get(objOfInterest.get(i))));
                    for(int j = 0; j < curWay.size(); j++) {
                        if(curWay.get(j).isOccupied() || curWay.get(j).isObstacle() || curWay.get(j).isBuilding()) {
                            curWay = new ArrayList<Square>(curWay.subList(0, j));
                        }
                    }
                } else if (notFullWay) {
                    preferredWay = curWay;
                    target = curTarget;
                    notFullWay = false;
                }
                if (preferredWay.size() > curWay.size()){
                    preferredWay = curWay;
                    target = curTarget;
                }
            }
        } else {
            preferredWay = battle.getBattlefield().findPathOrdinaryEXPERIMENTAL(start, battle.getBattlefield().getSquare("G4"));
            target = battle.getBattlefield().getSquare("G4");
        }

        if(!hero.equals(battle.getRightHero()) && battle.getRightHero().getName().equals("Стражник Башни")) {
            ArrayList<Square> towerWay = battle.getBattlefield().findPathOrdinaryEXPERIMENTAL(start, battle.getBattlefield().getSquare("G4"));
            if(towerWay.size()<preferredWay.size()*1.2) {
                preferredWay = towerWay;
                target = battle.getBattlefield().getSquare("G4");
            }
        }

        if(target.isBuilding()) {
            Render.displayBotTargetTowerMessage();
        }
        else {
            Render.displayBotTargetMessage(target.getOccupancy(), battle.getUnitsPlacement().get(target.getOccupancy()));
        }
        if(!notFullWay){
            battle.movingUnit(hero, unit, preferredWay, target);
        } else {
            battle.simplifiedMovingUnit(hero, unit, preferredWay, target);
        }
    }

    @Override
    public void buyHero(Game game) {
        if(!getKilled().isEmpty()) {
            ArrayList<BasicHero> cloneKilled = (ArrayList<BasicHero>) getKilled().clone();
            for(BasicHero hero : cloneKilled) {
                if(getPersonalGold() > hero.getPrice()) {
                    Render.displayHeroBoughtMessage(hero);
                    takePersonalGold(hero.getPrice());
                    getHeroArmy().add(hero);
                    getKilled().remove(hero);
                    game.addHero(this, hero);
                }
            }
        }
    }
}

