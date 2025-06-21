package labHeroesGame;

import labHeroesGame.battlefields.MainMap;
import labHeroesGame.battlefields.squares.Square;
import labHeroesGame.battles.Battle;
import labHeroesGame.battles.CastleBattle;
import labHeroesGame.battles.TowerBattle;
import labHeroesGame.buildings.Castle;
import labHeroesGame.buildings.Tower;
import labHeroesGame.heroes.BasicHero;
import labHeroesGame.player.BasicPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Game {
    private final BasicPlayer leftPlayer;
    private final BasicPlayer rightPlayer;
    private final MainMap map;
    private final ArrayList<BasicHero> allHeroes;
    private final ArrayList<Tower> allTowers;
    private final HashMap<BasicHero, String> heroPlacement;
    private final HashMap<Tower, String> towerPlacement;
    private int winCheck = 0;
    private String gameInfo;
    private int roundCount = 0;


    public String getGameInfo() {
        return gameInfo;
    }

    public void setGameInfo(String gameInfo) {
        this.gameInfo = gameInfo;
    }

    protected void fillGameInfo() {
        gameInfo = "Все герои в игре:";
        for(BasicHero hero : allHeroes) {
            gameInfo += "\n" + hero.getPlayerOwner().toString() + " " + hero + " : " + heroPlacement.get(hero);
        }
        gameInfo += "\nВсе башни в игре:";
        for(Tower tower : allTowers) {
            gameInfo += "\n" + tower.getPlayerOwner().toString() + " " + tower + " : " + towerPlacement.get(tower);
        }
    }

    public Game(BasicPlayer player1, BasicPlayer player2) {
        leftPlayer = player1;
        rightPlayer = player2;
        map = new MainMap();
        Castle castle1 = new Castle(player1);
        Castle castle2 = new Castle(player2);
        map.getSquare("B18").setCastleReference(castle1);
        map.getSquare("S1").setCastleReference(castle2);
        player1.setCastle(castle1);
        player2.setCastle(castle2);
        allTowers = new ArrayList<>();
        allHeroes = new ArrayList<>();
        heroPlacement = new HashMap<>();
        towerPlacement = new HashMap<>();
        ArrayList<String> towersId = new ArrayList<>(Arrays.asList("C10", "G13", "K17", "R10", "N6", "K2"));
        for(int i = 0; i < 3; i++) {
            Tower tower  = new Tower(player1);
            allTowers.add(tower);
            towerPlacement.put(tower, towersId.get(i));
            map.getSquare(towersId.get(i)).setTowerReference(tower);
        }
        for(int i = 3; i < 6; i++) {
            Tower tower  = new Tower(player2);
            allTowers.add(tower);
            towerPlacement.put(tower, towersId.get(i));
            map.getSquare(towersId.get(i)).setTowerReference(tower);
        }
        allHeroes.addAll(player1.getHeroArmy());
        allHeroes.addAll(player2.getHeroArmy());
        refillPlayerBuildings(player1);
        refillPlayerBuildings(player2);
        for (BasicHero hero : allHeroes) {
            hero.refill();
        }
    }

    public boolean startGame(){
        placeHeroes();
        fillGameInfo();
        if(gameRound()) {
            Render.displayWinningMessage(leftPlayer, roundCount);
            return true;
        }
        else {
            Render.displayWinningMessage(rightPlayer, roundCount);
            return false;
        }
    }

    public void placeHeroes(){
        ArrayList<String> heroPlaces = new ArrayList<>(Arrays.asList("C17", "B17", "C18", "R2", "S2", "R1"));
        for(int i = 0; i < leftPlayer.getHeroArmy().size(); i++) {
            map.placeHero(heroPlaces.get(i), allHeroes.get(i));
            heroPlacement.put(allHeroes.get(i), heroPlaces.get(i));
        }
        for(int i = leftPlayer.getHeroArmy().size(); i < leftPlayer.getHeroArmy().size() + rightPlayer.getHeroArmy().size(); i++) {
            map.placeHero(heroPlaces.get(i+2), allHeroes.get(i));
            heroPlacement.put(allHeroes.get(i), heroPlaces.get(i+2));
        }
    }

    public void refillPlayerBuildings(BasicPlayer player) {
        for(Tower tower : player.getPersonalTowers()) {
            tower.refill();
        }
        player.getCastle().getGuardian().refill();
    }

    public boolean gameRound() {
        while (true) {
            roundCount++;
            int roundPhase = 1;
            leftPlayer.addPersonalGold(200);
            rightPlayer.addPersonalGold(200);
            Render.displayShop(leftPlayer);
            leftPlayer.buyHero(this);
            ArrayList<BasicHero> anotherAllHeroes =(ArrayList<BasicHero>) allHeroes.clone();
            for (BasicHero hero : anotherAllHeroes) {
                if (!allHeroes.contains(hero)) {
                    continue;
                }
                Render.displayMap(map);
                fillGameInfo();
                Render.displayGameInfo(this);
                if (!hero.getPlayerOwner().equals(getLeftPlayer())) {
                    roundPhase = 2;
                    Render.displayShop(rightPlayer);
                    rightPlayer.buyHero(this);
                }
                switch (roundPhase) {
                    case 1 -> leftPlayer.requestMoveHero(this, hero);
                    case 2 -> rightPlayer.requestMoveHero(this, hero);
                }
                switch (winCheck) {
                    case 1 -> {
                        return true;
                    }
                    case 2 -> {
                        return false;
                    }
                }
            }
            switch (winCheck) {
                case 1 -> {
                    return true;
                }
                case 2 -> {
                    return false;
                }
            }
        }
    }

    public void addHero(BasicPlayer player, BasicHero hero) {
        if(player.equals(leftPlayer)) {
            if(!map.getSquare("C17").isPeacefulOccupied()) {
                map.getSquare("C17").setPeacefulOccupancy(hero);
                allHeroes.add(hero);
                heroPlacement.put(hero, "C17");
            } else if(!map.getSquare("C18").isPeacefulOccupied()) {
                map.getSquare("C18").setPeacefulOccupancy(hero);
                allHeroes.add(hero);
                heroPlacement.put(hero, "C18");
            } else if(!map.getSquare("B17").isPeacefulOccupied()) {
                map.getSquare("B17").setPeacefulOccupancy(hero);
                allHeroes.add(hero);
                heroPlacement.put(hero, "B17");
            }
        } else {
            if(!map.getSquare("R2").isPeacefulOccupied()) {
                map.getSquare("R2").setPeacefulOccupancy(hero);
                allHeroes.add(hero);
                heroPlacement.put(hero, "R2");
            } else if(!map.getSquare("S2").isPeacefulOccupied()) {
                map.getSquare("S2").setPeacefulOccupancy(hero);
                allHeroes.add(hero);
                heroPlacement.put(hero, "S2");
            } else if(!map.getSquare("R1").isPeacefulOccupied()) {
                map.getSquare("R1").setPeacefulOccupancy(hero);
                allHeroes.add(hero);
                heroPlacement.put(hero, "R1");
            }
        }
        fillGameInfo();
    }

    public Square actualMovingHero(BasicHero hero, ArrayList<Square> preferredWay, Square start, int range) {
        preferredWay.get(range).setPeacefulOccupancy(hero);
        getHeroPlacement().replace(hero, getMap().getSquareId(preferredWay.get(range)));
        start.setPeacefulOccupancy(null);
        return preferredWay.get(range);
    }

    public void killHero(Square start, Tower tower, boolean win) {
        if (win) {
            tower.getPlayerOwner().getPersonalTowers().remove(tower);
            start.getPeacefulOccupancy().getPlayerOwner().getPersonalTowers().add(tower);
            tower.setPlayerOwner(start.getPeacefulOccupancy().getPlayerOwner());
            refillPlayerBuildings(tower.getPlayerOwner());
            start.getPeacefulOccupancy().refill();
        } else {
            killHero(start);
            tower.refill();
        }
    }

    public void killHero(Square target) {
        target.getPeacefulOccupancy().getPlayerOwner().getKilled().add(target.getPeacefulOccupancy());
        target.getPeacefulOccupancy().getPlayerOwner().getHeroArmy().remove(target.getPeacefulOccupancy());
        allHeroes.remove(target.getPeacefulOccupancy());
        target.getPeacefulOccupancy().refill();
        target.setPeacefulOccupancy(null);
    }

    public void movingHero(BasicHero hero, ArrayList<Square> preferredWay) {
        Square start = getMap().getSquare(getHeroPlacement().get(hero));
        int actualSpeed = hero.getSpeed();
        for (Square i : preferredWay) {
            if (i.isRoad() && actualSpeed < hero.getSpeed()*2){
                actualSpeed++;
            }
        }
        Square target = preferredWay.getLast();
        if(preferredWay.size() <= actualSpeed) {
            if(preferredWay.size() > 1) {
                start = actualMovingHero(hero, preferredWay, start, preferredWay.size() - 2);
            }
            if (target.isPeacefulOccupied()) {
                Render.displayMap(map);
                Battle battle = new Battle(hero, target.getPeacefulOccupancy());
                Render.displayBattleStartMessage(hero, target.getPeacefulOccupancy());
                if (battle.startBattle()) {
                    Render.displayBattleWinningMessage(hero);
                    killHero(target);
                } else {
                    Render.displayBattleWinningMessage(target.getPeacefulOccupancy());
                    killHero(start);
                }
            } else if (target.isBuilding() && target.getBuildingType().equals("Tower") && !target.getTowerReference().getPlayerOwner().equals(hero.getPlayerOwner())) {
                Render.displayMap(map);
                TowerBattle battle = new TowerBattle(hero, target.getTowerReference().getGuardian(), target.getTowerReference());
                Render.displayBattleStartMessage(hero, target.getTowerReference());
                if (battle.startBattle()) {
                    Render.displayBattleWinningMessage(hero);
                    killHero(start, target.getTowerReference(), true);

                } else {
                    Render.displayBattleWinningMessage(target.getTowerReference());
                    killHero(start, target.getTowerReference(), false);
                }
            } else if (target.isBuilding() && target.getBuildingType().equals("Tower") && target.getTowerReference().getPlayerOwner().equals(hero.getPlayerOwner())) {
                hero.refill();
            } else if (target.isBuilding() && target.getBuildingType().equals("Castle") && !target.getCastleReference().getPlayerOwner().equals(hero.getPlayerOwner())) {
                Render.displayMap(map);
                CastleBattle battle = new CastleBattle(hero, target.getCastleReference().getGuardian(), target.getCastleReference());
                Render.displayBattleStartMessage(hero, target.getCastleReference());
                if (battle.startBattle()) {
                    if(target.getCastleReference().getPlayerOwner().equals(leftPlayer)){
                        winCheck = 2;
                    } else {
                        winCheck = 1;
                    }
                } else {
                    Render.displayBattleWinningMessage(target.getCastleReference());
                    killHero(start);
                    target.getCastleReference().getGuardian().refill();
                }
            } else if (target.isBuilding() && target.getBuildingType().equals("Castle") && target.getCastleReference().getPlayerOwner().equals(hero.getPlayerOwner())) {
                hero.refill();
            } else {
                if(!preferredWay.isEmpty()) {
                    actualMovingHero(hero, preferredWay, start, preferredWay.size()-1);
                }
            }
        } else {
            actualMovingHero(hero, preferredWay, start, actualSpeed - 1);
        }
    }

    public BasicPlayer getLeftPlayer() {
        return leftPlayer;
    }

    public BasicPlayer getRightPlayer() {
        return rightPlayer;
    }

    public MainMap getMap() {
        return map;
    }

    public ArrayList<BasicHero> getAllHeroes() {
        return allHeroes;
    }

    public HashMap<BasicHero, String> getHeroPlacement() {
        return heroPlacement;
    }

    public ArrayList<Tower> getAllTowers() {
        return allTowers;
    }

    public HashMap<Tower, String> getTowerPlacement() {
        return towerPlacement;
    }
}
