package labHeroesGame.battlefields.preBuilds;

import labHeroesGame.Render;
import labHeroesGame.authorization.User;
import labHeroesGame.battlefields.Battlefield;
import labHeroesGame.battlefields.squares.IdConverter;
import labHeroesGame.heroes.ChampLight;
import labHeroesGame.player.Human;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class MapCreator {
    private static int castleNum;
    private static int towerNum;
    private static int heroPlacementNum;
    private static int hotelNum;
    private static int barberNum;
    private static int cafeNum;

    private static boolean objectSideRequest(Scanner scanner) {
        Render.displayObjectSideRequestMessage();
        int intInput;
        while (true) {
            if(scanner.hasNextInt()) {
                intInput = scanner.nextInt();
                switch (intInput) {
                    case 1: return true;
                    case 2: return false;
                    default: Render.displayWrongInputMessage();
                }
            } else {
                String input = scanner.next();
                Render.displayWrongInputMessage();
            }
        }
    }

    private static void placeMultipleSquares(Scanner scanner, Battlefield battlefield, PreBuild preBuild, String type){
        Render.displayLineToPlaceRequest(type);
        String input;
        while (true) {
            if(scanner.hasNext()) {
                input = scanner.nextLine();
                input = input.toLowerCase();
                if(input.split(" ").length == 0) {
                    Render.displayWrongInputMessage();
                    continue;
                }
                if (input.split(" ").length > 1) {
                    while (input.contains("  ")) {
                        input = input.replace("  ", " ");
                    }
                    if(IdConverter.isStringID(battlefield, input.split(" ")[0]) && IdConverter.isStringID(battlefield, input.split(" ")[1])) {
                        String point1 = input.split(" ")[0];
                        String point2 = input.split(" ")[1];
                        ArrayList<Integer> point1Int = IdConverter.convertToIntID(point1);
                        ArrayList<Integer> point2Int = IdConverter.convertToIntID(point2);
                        if(point1.charAt(0) == point2.charAt(0)) {
                            for(int i = Integer.min(point1Int.get(1), point2Int.get(1)); i < Integer.max(point1Int.get(1), point2Int.get(1)+1); i++) {
                                if(preBuild.getMapInfo().containsKey(IdConverter.convertToStringID(point1Int.get(0), i))) {
                                    if(preBuild.getMapInfo().get(IdConverter.convertToStringID(point1Int.get(0), i)).equals("Tower")) {
                                        towerNum--;
                                    } else if (preBuild.getMapInfo().get(IdConverter.convertToStringID(point1Int.get(0), i)).equals("Castle")) {
                                        castleNum--;
                                    } else if(preBuild.getMapInfo().get(IdConverter.convertToStringID(point1Int.get(0), i)).equals("HeroSpawn") && !type.equals("Road")) {
                                        if (input.equals(preBuild.getLeftHeroPlacement())) {
                                            battlefield.getSquare(input).setPeacefulOccupancy(null);
                                            preBuild.setLeftHeroPlacement(null);
                                            heroPlacementNum--;
                                        } else if (input.equals(preBuild.getRightHeroPlacement())) {
                                            battlefield.getSquare(input).setPeacefulOccupancy(null);
                                            preBuild.setRightHeroPlacement(null);
                                            heroPlacementNum--;
                                        }
                                    } else if (preBuild.getMapInfo().get(IdConverter.convertToStringID(point1Int.get(0), i)).equals("R.Hotel")) {
                                        preBuild.setHotelPlacement(null);
                                        hotelNum--;
                                    } else if (preBuild.getMapInfo().get(IdConverter.convertToStringID(point1Int.get(0), i)).equals("Barber")) {
                                        preBuild.setHotelPlacement(null);
                                        barberNum--;
                                    } else if (preBuild.getMapInfo().get(IdConverter.convertToStringID(point1Int.get(0), i)).equals("F.Cafe")) {
                                        preBuild.setHotelPlacement(null);
                                        cafeNum--;
                                    }
                                    preBuild.getMapInfo().replace(IdConverter.convertToStringID(point1Int.get(0), i), type);
                                } else {
                                    preBuild.getMapInfo().put(IdConverter.convertToStringID(point1Int.get(0), i), type);
                                }
                                battlefield.getSquare(point1Int.get(0), i).setSmthng("Nothing");
                                battlefield.getSquare(point1Int.get(0), i).setSmthng(type);
                            }
                            return;
                        } else if (point1.substring(1).equals(point2.substring(1))) {
                            for(int i = Integer.min(point1Int.get(0), point2Int.get(0)); i < Integer.max(point1Int.get(0), point2Int.get(0)+1); i++) {
                                if(preBuild.getMapInfo().containsKey(IdConverter.convertToStringID(i, point1Int.get(1)))) {
                                    if(preBuild.getMapInfo().get(IdConverter.convertToStringID(i, point1Int.get(1))).equals("Tower")) {
                                        preBuild.getLeftTowers().remove(input);
                                        preBuild.getRightTowers().remove(input);
                                        towerNum--;
                                    } else if (preBuild.getMapInfo().get(IdConverter.convertToStringID(i, point1Int.get(1))).equals("Castle")) {
                                        if(preBuild.getLeftCastle().equals(input)) {
                                            preBuild.setLeftCastle(null);
                                        } else {
                                            preBuild.setRightCastle(null);
                                        }
                                        castleNum--;
                                    } else if (preBuild.getMapInfo().get(IdConverter.convertToStringID(i, point1Int.get(1))).equals("HeroSpawn")) {
                                        if (input.equals(preBuild.getLeftHeroPlacement())) {
                                            battlefield.getSquare(input).setPeacefulOccupancy(null);
                                            preBuild.setLeftHeroPlacement(null);
                                            heroPlacementNum--;
                                        } else if (input.equals(preBuild.getRightHeroPlacement())) {
                                            battlefield.getSquare(input).setPeacefulOccupancy(null);
                                            preBuild.setRightHeroPlacement(null);
                                            heroPlacementNum--;
                                        }
                                    } else if (preBuild.getMapInfo().get(IdConverter.convertToStringID(i, point1Int.get(1))).equals("R.Hotel")) {
                                        preBuild.setHotelPlacement(null);
                                        hotelNum--;
                                    } else if (preBuild.getMapInfo().get(IdConverter.convertToStringID(i, point1Int.get(1))).equals("Barber")) {
                                        preBuild.setHotelPlacement(null);
                                        barberNum--;
                                    } else if (preBuild.getMapInfo().get(IdConverter.convertToStringID(i, point1Int.get(1))).equals("F.Cafe")) {
                                        preBuild.setHotelPlacement(null);
                                        cafeNum--;
                                    }
                                    preBuild.getMapInfo().replace(IdConverter.convertToStringID(i, point1Int.get(1)), type);
                                } else {
                                    preBuild.getMapInfo().put(IdConverter.convertToStringID(i, point1Int.get(1)), type);
                                }
                                battlefield.getSquare(i, point1Int.get(1)).setSmthng("Nothing");
                                battlefield.getSquare(i, point1Int.get(1)).setSmthng(type);

                            }
                            return;
                        } else {
                            Render.displayWrongInputMessage();
                        }
                    } else {
                        Render.displayWrongInputMessage();
                    }
                } else if (IdConverter.isStringID(battlefield, input)) {
                    if(preBuild.getMapInfo().containsKey(input)) {
                        if(preBuild.getMapInfo().get(input).equals("Tower")) {
                            preBuild.getLeftTowers().remove(input);
                            preBuild.getRightTowers().remove(input);
                            towerNum--;
                        } else if (preBuild.getMapInfo().get(input).equals("Castle")) {
                            if(preBuild.getLeftCastle().equals(input)) {
                                preBuild.setLeftCastle(null);
                            } else {
                                preBuild.setRightCastle(null);
                            }
                            castleNum--;
                        } else if(preBuild.getMapInfo().get(input).equals("HeroSpawn")) {
                            if (input.equals(preBuild.getLeftHeroPlacement())) {
                                battlefield.getSquare(input).setPeacefulOccupancy(null);
                                preBuild.setLeftHeroPlacement(null);
                                heroPlacementNum--;
                            } else if (input.equals(preBuild.getRightHeroPlacement())) {
                                battlefield.getSquare(input).setPeacefulOccupancy(null);
                                preBuild.setRightHeroPlacement(null);
                                heroPlacementNum--;
                            }
                        } else if(preBuild.getMapInfo().get(input).equals("R.Hotel")) {
                            preBuild.setHotelPlacement(null);
                            hotelNum--;
                        } else if(preBuild.getMapInfo().get(input).equals("Barbershop")) {
                            preBuild.setBarberPlacement(null);
                            barberNum--;
                        } else if(preBuild.getMapInfo().get(input).equals("F.Cafe")) {
                            preBuild.setCafePlacement(null);
                            cafeNum--;
                        }
                        preBuild.getMapInfo().replace(input, type);
                    } else {
                        preBuild.getMapInfo().put(input, type);
                    }
                    battlefield.getSquare(input).setSmthng("Nothing");
                    battlefield.getSquare(input).setSmthng(type);
                    return;
                } else {
                    Render.displayWrongInputMessage();
                }
            }

        }
    }

    private static void placeSingleSquare(Scanner scanner, Battlefield battlefield, PreBuild preBuild, String type) {
        Render.displaySquareToPlaceRequest(type);
        String input;
        while (true) {
            input = scanner.next();
            if (IdConverter.isStringID(battlefield, input)) {
                if(preBuild.getMapInfo().containsKey(input)) {
                    if(preBuild.getMapInfo().get(input).equals("Tower")) {
                        preBuild.getLeftTowers().remove(input);
                        preBuild.getRightTowers().remove(input);
                        towerNum--;
                    } else if (preBuild.getMapInfo().get(input).equals("Castle")) {
                        if(preBuild.getLeftCastle().equals(input)) {
                            preBuild.setLeftCastle(null);
                        } else {
                            preBuild.setRightCastle(null);
                        }
                        castleNum--;
                    } else if (input.equals(preBuild.getLeftHeroPlacement())) {
                        battlefield.getSquare(input).setPeacefulOccupancy(null);
                        preBuild.setLeftHeroPlacement(null);
                        heroPlacementNum--;
                    } else if (input.equals(preBuild.getRightHeroPlacement())) {
                        battlefield.getSquare(input).setPeacefulOccupancy(null);
                        preBuild.setRightHeroPlacement(null);
                        heroPlacementNum--;
                    } else if(preBuild.getMapInfo().get(input).equals("R.Hotel")) {
                        preBuild.setHotelPlacement(null);
                        hotelNum--;
                    } else if(preBuild.getMapInfo().get(input).equals("Barbershop")) {
                        preBuild.setBarberPlacement(null);
                        barberNum--;
                    } else if(preBuild.getMapInfo().get(input).equals("F.Cafe")) {
                        preBuild.setCafePlacement(null);
                        cafeNum--;
                    }
                    preBuild.getMapInfo().replace(input, type);
                } else {
                    preBuild.getMapInfo().put(input, type);
                }
                battlefield.getSquare(input).setSmthng("Nothing");
                if (type.equals("HeroSpawn")) {
                    battlefield.getSquare(input).setPeacefulOccupancy(new ChampLight(new Human()));
                } else {
                    battlefield.getSquare(input).setSmthng(type);
                }
                if(type.equals("Tower")) {
                    if(preBuild.getLeftTowers().size() == 3){
                        preBuild.getRightTowers().add(input);
                    } else if (preBuild.getRightTowers().size() == 3) {
                        preBuild.getLeftTowers().add(input);
                    } else {
                        if(objectSideRequest(scanner)) {
                            preBuild.getLeftTowers().add(input);
                        } else {
                            preBuild.getRightTowers().add(input);
                        }
                    }
                    towerNum++;
                } else if (type.equals("Castle")) {
                    if(preBuild.getRightCastle() != null){
                        preBuild.setLeftCastle(input);
                    } else if (preBuild.getLeftCastle() != null) {
                        preBuild.setRightCastle(input);
                    } else {
                        if(objectSideRequest(scanner)) {
                            preBuild.setLeftCastle(input);
                        } else {
                            preBuild.setRightCastle(input);
                        }
                    }
                    castleNum++;
                } else if (type.equals("HeroSpawn")) {
                    if(preBuild.getLeftHeroPlacement() != null) {
                        preBuild.setRightHeroPlacement(input);
                    } else if(preBuild.getRightHeroPlacement() != null) {
                        preBuild.setLeftHeroPlacement(input);
                    } else {
                        if(objectSideRequest(scanner)) {
                            preBuild.setLeftHeroPlacement(input);
                        } else {
                            preBuild.setRightHeroPlacement(input);
                        }
                    }
                    heroPlacementNum++;
                } else if (type.equals("R.Hotel")) {
                    preBuild.setHotelPlacement(input);
                    hotelNum++;
                } else if (type.equals("Barber")) {
                    preBuild.setBarberPlacement(input);
                    barberNum++;
                } else if (type.equals("F.Cafe")) {
                    preBuild.setCafePlacement(input);
                    cafeNum++;
                }
                return;
            } else {
                Render.displayWrongInputMessage();
            }
        }
    }

    public static void placeObj(Scanner scanner, PreBuild preBuild, Battlefield battlefield) {
        String input;
        while(true){
            Render.displayMap(battlefield);
            int objInt = Render.displayObjectsToPlace(castleNum, towerNum, heroPlacementNum, hotelNum, cafeNum, barberNum);
            while(true) {
                if(scanner.hasNextInt()){
                    int intInput = scanner.nextInt();
                    int objMove = intInput;
                    if(castleNum==2 && intInput > 3) objMove++;
                    if(towerNum==6 && intInput > 4) objMove++;
                    if(heroPlacementNum==2 && intInput > 5) objMove++;
                    if(hotelNum==1 && intInput > 6) objMove++;
                    if(cafeNum==1 && intInput > 7) objMove++;
                    if(barberNum==1 && intInput > 8) objMove++;
                    if(objMove > 0 && objMove <= objInt){
                        switch(objMove) {
                            case 1: {
                                placeMultipleSquares(scanner, battlefield, preBuild, "Road");
                                break;
                            }
                            case 2: {
                                placeMultipleSquares(scanner, battlefield, preBuild, "Obstacle");
                                break;
                            }
                            case 3: {
                                placeMultipleSquares(scanner, battlefield, preBuild, "Nothing");
                                break;
                            }
                            case 4: {
                                if(castleNum < 2){
                                    placeSingleSquare(scanner, battlefield, preBuild, "Castle");
                                    break;
                                }
                            }
                            case 5: {
                                if(towerNum<6) {
                                    placeSingleSquare(scanner, battlefield, preBuild, "Tower");
                                    break;
                                }
                            }
                            case 6: {
                                if(heroPlacementNum < 2) {
                                    placeSingleSquare(scanner, battlefield, preBuild, "HeroSpawn");
                                    break;
                                }
                            }
                            case 7: {
                                if(hotelNum<1) {
                                    placeSingleSquare(scanner, battlefield, preBuild, "R.Hotel");
                                    break;
                                }
                            }
                            case 8: {
                                if(cafeNum<1) {
                                    placeSingleSquare(scanner, battlefield, preBuild, "F.Cafe");
                                    break;
                                }
                            }
                            case 9: {
                                if(barberNum<1) {
                                    placeSingleSquare(scanner, battlefield, preBuild, "Barber");
                                    break;
                                }
                            }
                            default: Render.displayWrongInputMessage();
                        }
                    }
                    break;
                } else {
                    input = scanner.next();
                    if(Objects.equals(input, "skip")) {
                        return;
                    } else if (Objects.equals(input, "end")) {
                        if(towerNum == 6 && castleNum == 2 && heroPlacementNum == 2) {
                            return;
                        }
                        else{
                            Render.displayNotEnoughBuildingsCreated(towerNum, castleNum, heroPlacementNum);
                            continue;
                        }
                    }
                }
                Render.displayWrongInputMessage();
            }
        }
    }

    public static void createPreBuild(Scanner scanner, User user) {
        Render.displayMapNameRequest();
        ArrayList<String> existingNames = MapPreBuilds.getPreBuildNames();
        String input;
        while(true) {
            input = scanner.next();
            if(Objects.equals(input, "skip")) {
                return;
            }
            if(input.length()>3 && !existingNames.contains(input)){
                break;
            }
            Render.displayWrongInputMessage();
        }
        PreBuild preBuild = new PreBuild(input, user);
        castleNum = 0;
        towerNum = 0;
        heroPlacementNum = 0;
        Battlefield battlefield = new Battlefield(20);
        placeObj(scanner, preBuild, battlefield);
        MapPreBuilds.getCustomPreBuilds().add(preBuild);
        chooseSaveMethod(scanner, preBuild);
    }

    public static void changePreBuild(Scanner scanner, PreBuild preBuild) {
        Battlefield battlefield = new Battlefield(20);
        castleNum = 2;
        towerNum = 6;
        heroPlacementNum = 2;
        if(preBuild.getHotelPlacement() != null) {
            hotelNum = 1;
        }
        if(preBuild.getCafePlacement() != null) {
            cafeNum = 1;
        }
        if(preBuild.getBarberPlacement() != null) {
            barberNum = 1;
        }
        battlefield.getSquare(preBuild.getLeftHeroPlacement()).setPeacefulOccupancy(new ChampLight(new Human()));
        battlefield.getSquare(preBuild.getRightHeroPlacement()).setPeacefulOccupancy(new ChampLight(new Human()));
        MapPreBuilds.useBattlePreBuild(battlefield, preBuild.getMapInfo());
        placeObj(scanner, preBuild, battlefield);
        switch (preBuild.getSaveMethod()) {
            case "json" -> PreBuildSaver.savePreBuildJson(preBuild);
            case "xml" -> PreBuildSaver.savePreBuildXml(preBuild);
        }
    }

    private static void chooseSaveMethod(Scanner scanner, PreBuild preBuild) {
        Render.displayChooseSaveMethodMessage();
        String input;
        outerloop:
        while(true){
            if(scanner.hasNext()) {
                input = scanner.next();
                input = input.toLowerCase();
                switch (input) {
                    case "json" -> {
                        preBuild.setSaveMethod("json");
                        PreBuildSaver.savePreBuildJson(preBuild);
                        break outerloop;
                    }
                    case "xml" -> {
                        preBuild.setSaveMethod("xml");
                        PreBuildSaver.savePreBuildXml(preBuild);
                        break outerloop;
                    }
                    default -> Render.displayWrongInputMessage();
                }
            }
        }
    }
}
