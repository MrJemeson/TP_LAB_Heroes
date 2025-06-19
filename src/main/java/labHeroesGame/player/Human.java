package labHeroesGame.player;

import labHeroesGame.Game;
import labHeroesGame.Render;
import labHeroesGame.battlefields.squares.IdConverter;
import labHeroesGame.battlefields.squares.Square;
import labHeroesGame.battles.Battle;
import labHeroesGame.heroes.BasicHero;
import labHeroesGame.units.BasicUnit;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Human extends BasicPlayer {
    private static int identifiers = 0;
    private int identifier;

    public Human(){
        super();
        identifier = identifiers;
        identifiers++;
    }


    public Human(Scanner scanner){
        super(scanner);
        identifier = identifiers;
        identifiers++;
    }

    @Override
    public void requestMoveHero(Game game, BasicHero hero) {
        Render.displayHeroMovementRequestMessage(hero, game.getHeroPlacement().get(hero));
        String input;
        while(true) {
            input = getScanner().next();
            if (IdConverter.isStringID(game.getMap(), input)) {
                break;
            }
            if(Objects.equals(input, "skip")) {
                return;
            }
            Render.displayWrongSquareID();
        }
        Square start = game.getMap().getSquare(game.getHeroPlacement().get(hero));
        ArrayList<Square> preferredWay = game.getMap().findPathOrdinaryEXPERIMENTAL(start, game.getMap().getSquare(input));
        game.movingHero(hero, preferredWay);
    }

    @Override
    public void buyHero(Game game) {
        if(!getKilled().isEmpty()) {
            Render.displayBuyingHeroMessage(this);
            int inputInt;
            String input;
            while (true) {
                while(!getScanner().hasNextInt()) {
                    if(getScanner().hasNext()) {
                        input = getScanner().next();
                        if(input.length() == 1 && input.charAt(0) == 'n') {
                            return;
                        }
                    }
                    Render.displayWrongInputMessage();
                }
                inputInt = getScanner().nextInt();
                if(inputInt >= 1 && inputInt <= getKilled().size()) {
                    BasicHero hero = getKilled().get(inputInt-1);
                    if(hero.getPrice() < getPersonalGold()) {
                        Render.displayHeroBoughtMessage(hero);
                        takePersonalGold(hero.getPrice());
                        getHeroArmy().add(hero);
                        getKilled().remove(hero);
                        game.addHero(this, hero);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Игрок " + identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Human human)) return false;
        return identifier == human.identifier;
    }

    @Override
    public void requestMoveUnit(Battle battle, BasicHero hero, BasicUnit unit) {
        Render.displayUnitMovementRequestMessage(battle, unit, this);
        String input;
        while(true) {
            input = getScanner().next();
            if (IdConverter.isStringID(battle.getBattlefield(), input)) {
                break;
            }
            if(Objects.equals(input, "skip")) {
                return;
            }
            Render.displayWrongSquareID();
        }
        Square start = battle.getBattlefield().getSquare(battle.getUnitsPlacement().get(unit));
        ArrayList<Square> preferredWay = battle.getBattlefield().findPathOrdinaryEXPERIMENTAL(start, battle.getBattlefield().getSquare(input));
        if(preferredWay.isEmpty()) {
            preferredWay = battle.getBattlefield().findPathNoLimit(start, battle.getBattlefield().getSquare(input));
            for(int j = 0; j < preferredWay.size(); j++) {
                if(preferredWay.get(j).isOccupied() || preferredWay.get(j).isObstacle() || preferredWay.get(j).isBuilding()) {
                    preferredWay = new ArrayList<Square>(preferredWay.subList(0, j));
                }
            }
            battle.simplifiedMovingUnit(hero, unit, preferredWay, preferredWay.getLast());
        } else {
            if(preferredWay.getLast().isOccupied() || preferredWay.getLast().isBuilding()) {
                battle.movingUnit(hero, unit, preferredWay, preferredWay.getLast());
            } else {
                battle.simplifiedMovingUnit(hero, unit, preferredWay, preferredWay.getLast());
            }
        }
    }
}
