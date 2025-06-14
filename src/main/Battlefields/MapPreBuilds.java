package main.Battlefields;

import main.Battlefields.Squares.IdConverter;

import java.util.*;

public class MapPreBuilds {
    private static Map<String, String> towerBattlePreBuild = new HashMap<>();
    private static Map<String, String> castleBattlePreBuild = new HashMap<>();
    private static Map<String, String> mainMapPreBuild = new HashMap<>();

    private void createTowerBattlePreBuild(){
        towerBattlePreBuild.put("g4","Tower");
        towerBattlePreBuild.put("h4","Tower");
        towerBattlePreBuild.put("g5","Tower");
        towerBattlePreBuild.put("h5","Tower");
    }

    private void createCastleBattlePreBuild(){
        for (int i = 0; i < 10; i++){
            String id = "F" + i;
            castleBattlePreBuild.put(id, "Obstacle");
            id = "G" + i;
            castleBattlePreBuild.put(id, "Obstacle");
        }
    }
    
    private void createMainMapPreBuild(){
        mainMapPreBuild.put("G13","Tower");
        mainMapPreBuild.put("N6","Tower");
        mainMapPreBuild.put("B18","Castle");
        mainMapPreBuild.put("B17","#Road");
        mainMapPreBuild.put("C18","#Road");
        mainMapPreBuild.put("C10","Tower");
        mainMapPreBuild.put("K2","Tower");
        mainMapPreBuild.put("K17","Tower");
        mainMapPreBuild.put("R10","Tower");
        mainMapPreBuild.put("S1","Castle");
        mainMapPreBuild.put("S2","#Road");
        mainMapPreBuild.put("R1","#Road");
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
            mainMapPreBuild.put(IdConverter.convertToStringID(x, y), "Obstacle");
            mainMapPreBuild.put(IdConverter.convertToStringID(19 - y,19 - x), "Obstacle");
            mainMapPreBuild.put(IdConverter.convertToStringID(19 - x,19 - y), "Obstacle");
            mainMapPreBuild.put(IdConverter.convertToStringID(y, x), "Obstacle");
        }
        for (int i = 17; i > 1; i--){
            x = 2;
            y = i;
            if(!mainMapPreBuild.containsKey(IdConverter.convertToStringID(y,19 - y)) || !mainMapPreBuild.get(IdConverter.convertToStringID(y, 19 - y)).equals("Tower")) {
                mainMapPreBuild.put(IdConverter.convertToStringID(y, 19 - y), "#Road");
                mainMapPreBuild.put(IdConverter.convertToStringID(y, 19 - y - 1), "#Road");
                mainMapPreBuild.put(IdConverter.convertToStringID(y, 19 - y + 1), "#Road");
            } else {
                if(mainMapPreBuild.get(IdConverter.convertToStringID(y,19 - y)).equals("Tower")) {
                    for(int j = i-1; j < i+2; j++ ) {
                        for (int k = 19 - i - 1; k < 19 - i + 2; k++) {
                            if (!mainMapPreBuild.containsKey(IdConverter.convertToStringID(k, j))) {
                                mainMapPreBuild.put(IdConverter.convertToStringID(k, j), "#Road");
                            }
                        }
                    }
                }
            }
            if(!mainMapPreBuild.containsKey(IdConverter.convertToStringID(x, y)) || !mainMapPreBuild.get(IdConverter.convertToStringID(x, y)).equals("Tower")){
                mainMapPreBuild.put(IdConverter.convertToStringID(x, y), "#Road");
                mainMapPreBuild.put(IdConverter.convertToStringID(y, x), "#Road");
                x = 17;
                mainMapPreBuild.put(IdConverter.convertToStringID(x, y), "#Road");
                mainMapPreBuild.put(IdConverter.convertToStringID(y, x), "#Road");
            }
            else{
                for(int j = i-1; j < i+2; j++ ){
                    for(int k = x - 1; k < x + 2; k++){
                        if(!mainMapPreBuild.containsKey(IdConverter.convertToStringID(k, j)) || !mainMapPreBuild.get(IdConverter.convertToStringID(k, j)).equals("Tower")) {
                            mainMapPreBuild.put(IdConverter.convertToStringID(k, j), "#Road");
                            mainMapPreBuild.put(IdConverter.convertToStringID(j, k), "#Road");
                        }
                    }
                    for(int k = x + 14; k < x + 17; k++){
                        if(!mainMapPreBuild.containsKey(IdConverter.convertToStringID(k, j)) || !mainMapPreBuild.get(IdConverter.convertToStringID(k, j)).equals("Tower")) {
                            mainMapPreBuild.put(IdConverter.convertToStringID(k, j), "#Road");
                            mainMapPreBuild.put(IdConverter.convertToStringID(j, k), "#Road");
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
    }

    public static Map<String, String> getCastleBattlePreBuild() {
        return castleBattlePreBuild;
    }

    public static Map<String, String> getTowerBattlePreBuild() {
        return towerBattlePreBuild;
    }

    public static Map<String, String> getMainMapPreBuild() {
        return mainMapPreBuild;
    }

    public static void useBattlePreBuild(Battlefield battlefield, Map<String, String> preBuild){
        for(String id : preBuild.keySet()) {
            if(Objects.equals(preBuild.get(id), "Tower") || Objects.equals(preBuild.get(id), "Castle")){
                battlefield.setBuilding(id, preBuild.get(id));
            } else if (Objects.equals(preBuild.get(id), "#Road")) {
                battlefield.setRoad(id);
            } else if (Objects.equals(preBuild.get(id), "Obstacle")) {
                battlefield.setObstacle(id);
            }
        }
    }
}
