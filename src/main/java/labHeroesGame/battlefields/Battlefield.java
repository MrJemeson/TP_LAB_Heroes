package labHeroesGame.battlefields;

import labHeroesGame.Render;
import labHeroesGame.battlefields.squares.IdConverter;
import labHeroesGame.battlefields.squares.Square;
import labHeroesGame.heroes.BasicHero;
import labHeroesGame.units.BasicUnit;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class Battlefield implements Serializable {
    private final int size;

    @Serial
    private static final long serialVersionUID = 1L;

    public BasicUnit unitOnSquare(Square square) {
        if (square.isOccupied()) {
            return square.getOccupancy();
        }
        return null;
    }

    public BasicUnit unitOnSquare(String identifier) {
        if (this.getSquare(identifier) != null && this.getSquare(identifier).isOccupied()) {
            return this.getSquare(identifier).getOccupancy();
        }
        return null;
    }

    public BasicUnit unitOnSquare(int colIndex, int row) {
        if (this.getSquare(colIndex, row) != null && this.getSquare(colIndex, row).isOccupied()) {
            return this.getSquare(colIndex, row).getOccupancy();
        }
        return null;
    }

    public BasicHero heroOnSquare(Square square) {
        if (square.isOccupied()) {
            return square.getPeacefulOccupancy();
        }
        return null;
    }

    public BasicHero heroOnSquare(String identifier) {
        if (this.getSquare(identifier) != null && this.getSquare(identifier).isOccupied()) {
            return this.getSquare(identifier).getPeacefulOccupancy();
        }
        return null;
    }

    public BasicHero heroOnSquare(int colIndex, int row) {
        if (this.getSquare(colIndex, row) != null && this.getSquare(colIndex, row).isOccupied()) {
            return this.getSquare(colIndex, row).getPeacefulOccupancy();
        }
        return null;
    }

    public void setBuilding(String id, String buildingType) {
        this.getSquare(id).setBuilding(buildingType);
    }

    public void setObstacle(String id) {
        getSquare(id).setObstacle();
    }

    public void setObstacle(String id, boolean obstacle) {
        getSquare(id).setObstacle(obstacle);
    }

    public void setRoad(String id){
        getSquare(id).setRoad();
    }

    public void placeUnit(String identifier, BasicUnit unit) {
        Square square = getSquare(identifier);
        if (square != null && !square.isObstacle() && !square.isBuilding() && !square.isOccupied()) {
            square.setOccupancy(unit);
        } else {
            Render.displayWrongPlacement(unit, identifier);
        }
    }

    public void placeUnit(Square square, BasicUnit unit) {
        if (square != null && !square.isObstacle() && !square.isBuilding() && !square.isOccupied()) {
            square.setOccupancy(unit);
        } else {
            Render.displayWrongPlacement(unit, getSquareId(square));
        }
    }

    public void placeHero(String identifier, BasicHero hero) {
        Square square = getSquare(identifier);
        if (square != null && !square.isObstacle() && !square.isBuilding() && !square.isPeacefulOccupied()) {
            square.setPeacefulOccupancy(hero);
        } else {
            Render.displayWrongPlacement(hero, identifier);
        }
    }

    public void placeHero(Square square, BasicHero hero) {
        if (square != null && !square.isObstacle() && !square.isBuilding() && !square.isPeacefulOccupied()) {
            square.setPeacefulOccupancy(hero);
        } else {
            Render.displayWrongPlacement(hero, getSquareId(square));
        }
    }



    private Square startSquare;

    public Battlefield(int size) {
        this.size = size;
        generateField();
    }

    public Square getStartSquare() {
        return startSquare;
    }

    private void generateField() {
        startSquare = new Square();
        Square rowStart = startSquare;
        Square prevRowStart = null;

        for (int i = 0; i < size; i++) {
            Square previous = null;
            for (int j = 0; j < size; j++) {
                Square newSquare = new Square();
                if (i == 0 && j == 0) {
                   previous = rowStart;
                   continue;
                }
                if (previous != null) {
                    previous.setRightSquare(newSquare);
                    newSquare.setLeftSquare(previous);
                }
                if (i > 0 && j > 0) {
                    Square above = getSquareFromStart(rowStart.getUpSquare(), j);
                    if (above != null) {
                        above.setDownSquare(newSquare);
                        newSquare.setUpSquare(above);
                    }
                }
                previous = newSquare;
                if (j == 0) {
                    rowStart = newSquare;
                    rowStart.setUpSquare(prevRowStart);
                    prevRowStart.setDownSquare(rowStart);

                }
            }
            prevRowStart = rowStart;
        }
    }

    private Square getSquareFromStart(Square start, int steps) {
        Square current = start;
        for (int i = 0; i < steps; i++) {
            if (current == null) {
                return null;
            }
            current = current.getRightSquare();
        }
        return current;
    }

    public Square getSquare(String id) {
        ArrayList<Integer> IntId = IdConverter.convertToIntID(id);
        if (IntId == null)
            return null;
        int colIndex = IntId.get(0);
        int row = IntId.get(1);
        if (row >= 0 && row < size && colIndex >= 0 && colIndex < size) {
            Square targetSquare = startSquare;
            for (int i = 0; i < colIndex; i++)
                targetSquare = targetSquare.getRightSquare();
            for (int i = 0; i < row; i++)
                targetSquare = targetSquare.getDownSquare();
            return targetSquare;
        }
        return null;
    }

    public Square getSquare(int colIndex, int row) {
        if (colIndex < 0 || colIndex >= size || row < 0 || row >= size) {
            return null;
        }
        Square targetSquare = startSquare;
        for (int i = 0; i < colIndex; i++)
            targetSquare = targetSquare.getRightSquare();
        for (int i = 0; i < row; i++)
            targetSquare = targetSquare.getDownSquare();
        return targetSquare;
    }

    public ArrayList<Square> findPathOrdinary(String startId, String endId) {
        Square start = getSquare(startId);
        Square end = getSquare(endId);
        return findPathOrdinary(start, end);
    }

    public ArrayList<Square> findPathOrdinary(Square start, Square end) {
        if (start == null || end == null) return null;

        Map<Square, Square> cameFrom = new HashMap<>();
        Map<Square, Integer> costSoFar = new HashMap<>();
        PriorityQueue<Square> frontier = new PriorityQueue<>(Comparator.comparingInt(costSoFar::get));

        costSoFar.put(start, 0);
        frontier.add(start);

        while (!frontier.isEmpty()) {
            Square current = frontier.poll();
            if (current == end) break;

            for (Square neighbor : Arrays.asList(current.getUpSquare(), current.getDownSquare(), current.getLeftSquare(), current.getRightSquare())) {
                if ((neighbor == null || neighbor.isObstacle() || neighbor.isBuilding() || neighbor.isOccupied()) && neighbor != end) continue;
                int newCost = costSoFar.get(current) + 1;
                if (!costSoFar.containsKey(neighbor) || newCost < costSoFar.get(neighbor)) {
                    costSoFar.put(neighbor, newCost);
                    frontier.add(neighbor);
                    cameFrom.put(neighbor, current);
                }
            }
        }

        ArrayList<Square> path = new ArrayList<>();
        Square step = end;
        while (cameFrom.containsKey(step)) {
            path.add(step);
            step = cameFrom.get(step);
        }
        Collections.reverse(path);
        return path;
    }

    static class PathNode {
        Square square;
        float cost;

        PathNode(Square square, float cost) {
            this.square = square;
            this.cost = cost;
        }
    }

    public ArrayList<Square> findPathOrdinaryEXPERIMENTAL(String startId, String endId) {
        Square start = getSquare(startId);
        Square end = getSquare(endId);
        return findPathOrdinaryEXPERIMENTAL(start, end);
    }

    public ArrayList<Square> findPathOrdinaryEXPERIMENTAL(Square start, Square end) {
        if (start == null || end == null) return null;

        Map<Square, Square> cameFrom = new HashMap<>();
        Map<Square, Float> costSoFar = new HashMap<>();
        PriorityQueue<PathNode> frontier = new PriorityQueue<>(Comparator.comparingDouble(n -> n.cost));

        costSoFar.put(start, 0f);
        frontier.add(new PathNode(start, 0f));

        while (!frontier.isEmpty()) {
            Square current = frontier.poll().square;
            if (current == end) break;

            for (Square neighbor : Arrays.asList(current.getUpSquare(), current.getDownSquare(), current.getLeftSquare(), current.getRightSquare())) {
                if ((neighbor == null || neighbor.isObstacle() || neighbor.isBuilding() || neighbor.isOccupied()) && neighbor != end) continue;
                float newCost = costSoFar.get(current) + 1f - neighbor.getGold()/10000f;
                if(neighbor.isRoad()) {
                    newCost -= 0.5F;
                }
                if (!costSoFar.containsKey(neighbor) || newCost < costSoFar.get(neighbor)) {
                    costSoFar.put(neighbor, newCost);
                    frontier.add(new PathNode(neighbor, newCost));
                    cameFrom.put(neighbor, current);
                }
            }
        }
        ArrayList<Square> path = new ArrayList<>();
        Square step = end;
        while (cameFrom.containsKey(step)) {
            path.add(step);
            step = cameFrom.get(step);
        }
        Collections.reverse(path);
        return path;
    }

    public ArrayList<Square> findPathNoLimit(String startId, String endId) {
        Square start = getSquare(startId);
        Square end = getSquare(endId);
        return findPathNoLimit(start, end);
    }

    public ArrayList<Square> findPathNoLimit(Square start, Square end) {
        if (start == null || end == null) return null;

        Map<Square, Square> cameFrom = new HashMap<>();
        Map<Square, Float> costSoFar = new HashMap<>();
        PriorityQueue<PathNode> frontier = new PriorityQueue<>(Comparator.comparingDouble(n -> n.cost));

        costSoFar.put(start, 0F);
        frontier.add(new PathNode(start, 0f));

        while (!frontier.isEmpty()) {
            Square current = frontier.poll().square;
            if (current == end) break;

            for (Square neighbor : Arrays.asList(current.getUpSquare(), current.getDownSquare(), current.getLeftSquare(), current.getRightSquare())) {
                if (neighbor == null) continue;
                float newCost = costSoFar.get(current) + 1f - neighbor.getGold()/10000f;
                if (!costSoFar.containsKey(neighbor) || newCost < costSoFar.get(neighbor)) {
                    costSoFar.put(neighbor, newCost);
                    frontier.add(new PathNode(neighbor, newCost));
                    cameFrom.put(neighbor, current);
                }
            }
        }

        ArrayList<Square> path = new ArrayList<>();
        Square step = end;
        while (cameFrom.containsKey(step)) {
            path.add(step);
            step = cameFrom.get(step);
        }
        Collections.reverse(path);
        return path;
    }

    public int getSize() {
        return size;
    }

    public String getSquareId(Square target) {
        if (target == null) return null;
        Square rowStart = startSquare;
        for (int row = 0; row < size; row++) {
            Square current = rowStart;
            for (int col = 0; col < size; col++) {
                if (current == target) {
                    return IdConverter.convertToStringID(col, row);
                }
                current = current.getRightSquare();
            }
            rowStart = rowStart.getDownSquare();
        }
        return null;
    }
}
