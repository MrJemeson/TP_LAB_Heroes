package main.Battlefields.Squares;

import main.Battlefields.Battlefield;

import java.util.ArrayList;

public class IdConverter {
    public static String convertToStringID (int x, int y) {
        String str = Character.toString((char) x+65);
        str += Integer.toString(y);
        return str;
    }
    public static ArrayList<Integer> convertToIntID (String id) {
        if (id == null || id.length() < 2) {
            return null;
        }
        id = id.toUpperCase();
        char column = id.charAt(0);
        int row;
        try {
            row = Integer.parseInt(id.substring(1));
        } catch (NumberFormatException e) {
            return null;
        }
        int colIndex = column - 'A';
        ArrayList<Integer> res = new ArrayList<>();
        res.add(colIndex);
        res.add(row);
        return res;
    }

    public static boolean isNumeric(String str) {
        return str.matches("\\d+");
    }

    public static boolean isStringID(Battlefield battlefield, String id) {
        if (id == null || id.length() < 2) {
            return false;
        }
        id = id.toUpperCase();
        if(id.charAt(0) >= 'A' && id.charAt(0) <= 'A' + battlefield.getSize()) {
            if(isNumeric(id.substring(1))) {
                return true;
            }
            else return false;
        } else return false;
    }
}
