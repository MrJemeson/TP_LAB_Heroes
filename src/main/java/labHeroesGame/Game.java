package labHeroesGame;

import labHeroesGame.battlefields.MainMap;
import labHeroesGame.battlefields.preBuilds.PreBuild;
import labHeroesGame.battlefields.squares.Square;
import labHeroesGame.battles.Battle;
import labHeroesGame.battles.CastleBattle;
import labHeroesGame.battles.TowerBattle;
import labHeroesGame.buildings.Castle;
import labHeroesGame.buildings.Tower;
import labHeroesGame.gameSaving.GameSaver;
import labHeroesGame.heroes.BasicHero;
import labHeroesGame.player.BasicPlayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;

public class Game  implements Serializable {
    private final BasicPlayer leftPlayer;
    private final BasicPlayer rightPlayer;
    private final MainMap map;
    private final ArrayList<BasicHero> allHeroes;
    private final ArrayList<Tower> allTowers;
    private final HashMap<BasicHero, String> heroPlacement;
    private final HashMap<Tower, String> towerPlacement;
    private final PreBuild currentPreBuild;
    private final User currentUser;
    private int winCheck = 0;
    private String gameInfo;
    private int roundCount = 0;

    public PreBuild getCurrentPreBuild() {
        return currentPreBuild;
    }

    public User getCurrentUser() {
        return currentUser;
    }

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

    public Game(BasicPlayer player1, BasicPlayer player2, PreBuild preBuild, User user) {
        leftPlayer = player1;
        rightPlayer = player2;
        currentPreBuild = preBuild;
        currentUser = user;
        map = new MainMap(preBuild);
        Castle castle1 = new Castle(player1);
        Castle castle2 = new Castle(player2);
        map.getSquare(preBuild.getLeftCastle()).setCastleReference(castle1);
        map.getSquare(preBuild.getRightCastle()).setCastleReference(castle2);
        player1.setCastle(castle1);
        player2.setCastle(castle2);
        allTowers = new ArrayList<>();
        allHeroes = new ArrayList<>();
        heroPlacement = new HashMap<>();
        towerPlacement = new HashMap<>();
        for(int i = 0; i < 3; i++) {
            Tower tower  = new Tower(player1);
            allTowers.add(tower);
            towerPlacement.put(tower, preBuild.getLeftTowers().get(i));
            map.getSquare(preBuild.getLeftTowers().get(i)).setTowerReference(tower);
        }
        for(int i = 0; i < 3; i++) {
            Tower tower  = new Tower(player2);
            allTowers.add(tower);
            towerPlacement.put(tower, preBuild.getRightTowers().get(i));
            map.getSquare(preBuild.getRightTowers().get(i)).setTowerReference(tower);
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
        GameSaver.saveGame(this, currentUser);
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
        ArrayList<String> heroPlaces = new ArrayList<>(Arrays.asList(currentPreBuild.getLeftHeroPlacement(), currentPreBuild.getRightHeroPlacement()));
        for(int i = 0; i < leftPlayer.getHeroArmy().size(); i++) {
            map.placeHero(heroPlaces.get(i), allHeroes.get(i));
            heroPlacement.put(allHeroes.get(i), heroPlaces.get(i));
        }
        for(int i = leftPlayer.getHeroArmy().size(); i < leftPlayer.getHeroArmy().size() + rightPlayer.getHeroArmy().size(); i++) {
            map.placeHero(heroPlaces.get(i), allHeroes.get(i));
            heroPlacement.put(allHeroes.get(i), heroPlaces.get(i));
        }
    }

    public void refillPlayerBuildings(BasicPlayer player) {
        for(Tower tower : player.getPersonalTowers()) {
            tower.refill();
        }
        player.getCastle().getGuardian().refill();
    }

    public boolean gameRound() {
        fillGameInfo();
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
                        GameSaver.deleteAutoSave();
                        return true;
                    }
                    case 2 -> {
                        GameSaver.deleteAutoSave();
                        return false;
                    }
                }
                GameSaver.saveGame(this, currentUser);
            }
            switch (winCheck) {
                case 1 -> {
                    GameSaver.deleteAutoSave();
                    return true;
                }
                case 2 -> {
                    GameSaver.deleteAutoSave();
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
        System.out.println(preferredWay);
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
                    LoggerConfig.getLogger().log(Level.WARNING, target.getTowerReference() + " lost the battle to " + hero + " and changed its owner to " + hero.getPlayerOwner());
                    killHero(start, target.getTowerReference(), true);
                } else {
                    Render.displayBattleWinningMessage(target.getTowerReference());
                    LoggerConfig.getLogger().log(Level.INFO, target.getTowerReference() + " won the battle against " + hero);
                    killHero(start, target.getTowerReference(), false);
                }
            } else if (target.isBuilding() && target.getBuildingType().equals("Tower") && target.getTowerReference().getPlayerOwner().equals(hero.getPlayerOwner())) {
                hero.refill();
                LoggerConfig.getLogger().log(Level.FINE, hero + " refilled his army at " + target.getTowerReference());
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
