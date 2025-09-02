package labHeroesGame.battlefields.preBuilds;

import labHeroesGame.battlefields.Battlefield;
import labHeroesGame.battlefields.squares.IdConverter;
import labHeroesGame.battlefields.squares.Square;

import java.util.*;

public class MapPreBuilds {
    private static ArrayList<PreBuild> customPreBuilds = new ArrayList<>();
    private static PreBuild towerBattlePreBuild;
    private static PreBuild castleBattlePreBuild;
    private static PreBuild mainMapPreBuild;

    private void createTowerBattlePreBuild(){
        towerBattlePreBuild = new PreBuild("TowerPreBuild");
        towerBattlePreBuild.getMapInfo().put("g4","Tower");
        towerBattlePreBuild.getMapInfo().put("h4","Tower");
        towerBattlePreBuild.getMapInfo().put("g5","Tower");
        towerBattlePreBuild.getMapInfo().put("h5","Tower");
    }

    private void createCastleBattlePreBuild(){
        castleBattlePreBuild = new PreBuild("CastlePreBuild");
        for (int i = 0; i < 10; i++){
            String id = "F" + i;
            castleBattlePreBuild.getMapInfo().put(id, "Obstacle");
            id = "G" + i;
            castleBattlePreBuild.getMapInfo().put(id, "Obstacle");
        }
    }
    
    private void createMainMapPreBuild(){
        mainMapPreBuild = new PreBuild("MainMapPreBuild");
        mainMapPreBuild.getMapInfo().put("G13","Tower");
        mainMapPreBuild.getLeftTowers().add("G13");
        mainMapPreBuild.getMapInfo().put("N6","Tower");
        mainMapPreBuild.getRightTowers().add("N6");
        mainMapPreBuild.getMapInfo().put("B18","Castle");
        mainMapPreBuild.setLeftCastle("B18");
        mainMapPreBuild.getMapInfo().put("B17","Road");
        mainMapPreBuild.getMapInfo().put("C18","Road");
        mainMapPreBuild.getMapInfo().put("C10","Tower");
        mainMapPreBuild.getLeftTowers().add("C10");
        mainMapPreBuild.getMapInfo().put("K2","Tower");
        mainMapPreBuild.getRightTowers().add("K2");
        mainMapPreBuild.getMapInfo().put("K17","Tower");
        mainMapPreBuild.getLeftTowers().add("K17");
        mainMapPreBuild.getMapInfo().put("R10","Tower");
        mainMapPreBuild.getRightTowers().add("R10");
        mainMapPreBuild.getMapInfo().put("S1","Castle");
        mainMapPreBuild.setRightCastle("S1");
        mainMapPreBuild.getMapInfo().put("S2","Road");
        mainMapPreBuild.getMapInfo().put("R1","Road");
        mainMapPreBuild.setLeftHeroPlacement("C17");
        mainMapPreBuild.setRightHeroPlacement("R2");
        mainMapPreBuild.setHotelPlacement("Q16");
        mainMapPreBuild.getMapInfo().put("Q16", "R.Hotel");
        mainMapPreBuild.setCafePlacement("D3");
        mainMapPreBuild.getMapInfo().put("D3", "F.Cafe");
        mainMapPreBuild.setBarberPlacement("S18");
        mainMapPreBuild.getMapInfo().put("S18", "Barber");
        int x, y;
        ArrayList<List<Integer>> forest = new ArrayList<>(Arrays.asList(
                List.of(6, 3), List.of(7, 3), List.of(8, 3), List.of(8, 4), List.of(9, 4),
                List.of(10, 4), List.of(11, 4), List.of(12, 4), List.of(11, 5), List.of(11, 6),
                List.of(10, 7), List.of(9, 7), List.of(8, 6), List.of(7, 5), List.of(6, 4),
                List.of(7, 4), List.of(8, 5), List.of(9, 5), List.of(10, 5), List.of(10, 6),
                List.of(9, 6)
        ));
        for (List<Integer> xy : forest) {
            x = xy.get(0);
            y = xy.get(1);
            mainMapPreBuild.getMapInfo().put(IdConverter.convertToStringID(x, y), "Obstacle");
            mainMapPreBuild.getMapInfo().put(IdConverter.convertToStringID(19 - y,19 - x), "Obstacle");
            mainMapPreBuild.getMapInfo().put(IdConverter.convertToStringID(19 - x,19 - y), "Obstacle");
            mainMapPreBuild.getMapInfo().put(IdConverter.convertToStringID(y, x), "Obstacle");
        }
        for (int i = 17; i > 1; i--){
            x = 2;
            y = i;
            if(!mainMapPreBuild.getMapInfo().containsKey(IdConverter.convertToStringID(y,19 - y)) || !mainMapPreBuild.getMapInfo().get(IdConverter.convertToStringID(y, 19 - y)).equals("Tower")) {
                mainMapPreBuild.getMapInfo().put(IdConverter.convertToStringID(y, 19 - y), "Road");
                mainMapPreBuild.getMapInfo().put(IdConverter.convertToStringID(y, 19 - y - 1), "Road");
                mainMapPreBuild.getMapInfo().put(IdConverter.convertToStringID(y, 19 - y + 1), "Road");
            } else {
                if(mainMapPreBuild.getMapInfo().get(IdConverter.convertToStringID(y,19 - y)).equals("Tower")) {
                    for(int j = i-1; j < i+2; j++ ) {
                        for (int k = 19 - i - 1; k < 19 - i + 2; k++) {
                            if (!mainMapPreBuild.getMapInfo().containsKey(IdConverter.convertToStringID(k, j))) {
                                mainMapPreBuild.getMapInfo().put(IdConverter.convertToStringID(k, j), "Road");
                            }
                        }
                    }
                }
            }
            if(!mainMapPreBuild.getMapInfo().containsKey(IdConverter.convertToStringID(x, y)) || !mainMapPreBuild.getMapInfo().get(IdConverter.convertToStringID(x, y)).equals("Tower")){
                mainMapPreBuild.getMapInfo().put(IdConverter.convertToStringID(x, y), "Road");
                mainMapPreBuild.getMapInfo().put(IdConverter.convertToStringID(y, x), "Road");
                x = 17;
                mainMapPreBuild.getMapInfo().put(IdConverter.convertToStringID(x, y), "Road");
                mainMapPreBuild.getMapInfo().put(IdConverter.convertToStringID(y, x), "Road");
            }
            else{
                for(int j = i-1; j < i+2; j++ ){
                    for(int k = x - 1; k < x + 2; k++){
                        if(!mainMapPreBuild.getMapInfo().containsKey(IdConverter.convertToStringID(k, j)) || !mainMapPreBuild.getMapInfo().get(IdConverter.convertToStringID(k, j)).equals("Tower")) {
                            mainMapPreBuild.getMapInfo().put(IdConverter.convertToStringID(k, j), "Road");
                            mainMapPreBuild.getMapInfo().put(IdConverter.convertToStringID(j, k), "Road");
                        }
                    }
                    for(int k = x + 14; k < x + 17; k++){
                        if(!mainMapPreBuild.getMapInfo().containsKey(IdConverter.convertToStringID(k, j)) || !mainMapPreBuild.getMapInfo().get(IdConverter.convertToStringID(k, j)).equals("Tower")) {
                            mainMapPreBuild.getMapInfo().put(IdConverter.convertToStringID(k, j), "Road");
                            mainMapPreBuild.getMapInfo().put(IdConverter.convertToStringID(j, k), "Road");
                        }
                    }
                }
            }
        }
    }

    public MapPreBuilds(){
        createCastleBattlePreBuild();
        createMainMapPreBuild();
        createTowerBattlePreBuild();
        PreBuildLoader.loadPreBuilds();
    }

    public static PreBuild getCastleBattlePreBuild() {
        return castleBattlePreBuild;
    }

    public static PreBuild getTowerBattlePreBuild() {
        return towerBattlePreBuild;
    }

    public static PreBuild getMainMapPreBuild() {
        return mainMapPreBuild;
    }

    public static ArrayList<PreBuild> getCustomPreBuilds() {
        return customPreBuilds;
    }

    public static ArrayList<String> getPreBuildNames() {
        ArrayList<String> names = new ArrayList<>();
        for(int i = 0; i<customPreBuilds.size(); i++) {
            names.add(customPreBuilds.get(i).getName());
        }
        return names;
    }

    public static void useBattlePreBuild(Battlefield battlefield, Map<String, String> preBuild){
        for(String id : preBuild.keySet()) {
            if(Square.getAllBuildings().contains(preBuild.get(id))){
                battlefield.setBuilding(id, preBuild.get(id));
            } else if (Objects.equals(preBuild.get(id), "Road")) {
                battlefield.setRoad(id);
            } else if (Objects.equals(preBuild.get(id), "Obstacle")) {
                battlefield.setObstacle(id);
            }
        }
    }
}
