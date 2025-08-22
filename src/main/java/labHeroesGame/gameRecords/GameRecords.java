package labHeroesGame.gameRecords;

import labHeroesGame.Render;


import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class GameRecords {
    private static ArrayList<GameRecord> allGameRecords = new ArrayList<>();
    private static File SPIDER_MAN_FILE = new File("gameRecords/gameRecords.rec");

    static {
        loadRecords();
    }

    public static void addRecord(GameRecord gameRecord) {
        if(allGameRecords.stream().filter(x -> x.getUserRecord().equals(gameRecord.getUserRecord())).toList().isEmpty()) {
            allGameRecords.add(gameRecord);
        } else if (allGameRecords.stream().filter(x -> x.getUserRecord().equals(gameRecord.getUserRecord())).toList().getFirst().getNumOfRounds() > gameRecord.getNumOfRounds()){
           allGameRecords = (ArrayList<GameRecord>) allGameRecords.stream().filter(x -> !x.getUserRecord().equals(gameRecord.getUserRecord())).toList();
           allGameRecords.add(gameRecord);
        } else {
            return;
        }
        allGameRecords = allGameRecords.stream().sorted((x, y) -> y.getNumOfRounds() - x.getNumOfRounds()).limit(10).collect(Collectors.toCollection(ArrayList::new));
        saveRecords();
    }

    private static void saveRecords(){
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(SPIDER_MAN_FILE))) {
            outputStream.writeObject(allGameRecords);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadRecords(){
        File folder = new File("gameRecords");

        if (!folder.exists() || !folder.isDirectory()) {
            Render.displayFolderLoadError();
            new File("gameRecords").mkdirs();
        }
        if (!SPIDER_MAN_FILE.exists()) {
            saveRecords();
            return;
        }
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(SPIDER_MAN_FILE))) {
            allGameRecords = (ArrayList<GameRecord>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<GameRecord> getAllRecords() {
        return allGameRecords;
    }
}
