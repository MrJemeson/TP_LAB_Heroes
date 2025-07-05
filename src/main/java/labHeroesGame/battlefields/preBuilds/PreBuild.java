package labHeroesGame.battlefields.preBuilds;

import labHeroesGame.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class PreBuild implements Serializable {
    private final String name;
    private HashMap<String, String> mapInfo = new HashMap<>();
    private String leftCastle;
    private String rightCastle;
    private String rightHeroPlacement;
    private String leftHeroPlacement;
    private ArrayList<String> leftTowers = new ArrayList<>();
    private ArrayList<String> rightTowers = new ArrayList<>();
    private User userCreator;

    public PreBuild(String name){
        this.name = name;
    }

    public PreBuild(String name, User user){
        this.name = name;
        userCreator = user;
    }

    public String getRightCastle() {
        return rightCastle;
    }

    public void setRightCastle(String rightCastle) {
        this.rightCastle = rightCastle;
    }

    public String getLeftCastle() {
        return leftCastle;
    }

    public void setLeftCastle(String leftCastle) {
        this.leftCastle = leftCastle;
    }

    public ArrayList<String> getLeftTowers() {
        return leftTowers;
    }

    public ArrayList<String> getRightTowers() {
        return rightTowers;
    }

    public HashMap<String, String> getMapInfo() {
        return mapInfo;
    }

    public String getName() {
        return name;
    }

    public String getRightHeroPlacement() {
        return rightHeroPlacement;
    }

    public void setRightHeroPlacement(String rightHeroPlacement) {
        this.rightHeroPlacement = rightHeroPlacement;
    }

    public String getLeftHeroPlacement() {
        return leftHeroPlacement;
    }

    public void setLeftHeroPlacement(String leftHeroPlacement) {
        this.leftHeroPlacement = leftHeroPlacement;
    }

    public void setRightTowers(ArrayList<String> rightTowers) {
        this.rightTowers = rightTowers;
    }

    public void setLeftTowers(ArrayList<String> leftTowers) {
        this.leftTowers = leftTowers;
    }

    public void setMapInfo(HashMap<String, String> mapInfo) {
        this.mapInfo = mapInfo;
    }

    @Override
    public String toString() {
        return name + ". Creator: " + userCreator.getName();
    }
}
