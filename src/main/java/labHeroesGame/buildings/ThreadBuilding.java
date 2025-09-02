package labHeroesGame.buildings;

import labHeroesGame.Game;
import labHeroesGame.battlefields.squares.IdConverter;
import labHeroesGame.heroes.BasicHero;
import labHeroesGame.heroes.NPC;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

public abstract class ThreadBuilding implements Serializable {
    private Game game;
    private int occupancyTime;
    private ArrayList<ThreadBuildingService> services = new ArrayList<>();
    private int numOfOccupants;
    private String placement;
    private String name;

    public ThreadBuilding(Game game ,String placement, int occupancyTime) {
        this.game = game;
        this.occupancyTime = occupancyTime;
        this.placement = placement;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOccupancyTime() {
        return occupancyTime;
    }

    public String  getPlacement() {
        return placement;
    }

    public ArrayList<ThreadBuildingService> getServices() {
        return services;
    }

    public int getNumOfOccupants() {
        return numOfOccupants;
    }

    public void startService(BasicHero hero) {
        ThreadBuildingService newService = new ThreadBuildingService(this, hero);
        newService.getHeroOccupancy().setInBuilding(true);
        services.add(newService);
        newService.start();
    }

    private void placeHero(BasicHero hero) {
        ArrayList<String> squaresExits = new ArrayList<>();
        squaresExits.add(IdConverter.convertToStringID(IdConverter.convertToIntID(placement).get(0)-1, IdConverter.convertToIntID(placement).get(1)));
        squaresExits.add(IdConverter.convertToStringID(IdConverter.convertToIntID(placement).get(0), IdConverter.convertToIntID(placement).get(1)-1));
        squaresExits.add(IdConverter.convertToStringID(IdConverter.convertToIntID(placement).get(0)+1, IdConverter.convertToIntID(placement).get(1)));
        squaresExits.add(IdConverter.convertToStringID(IdConverter.convertToIntID(placement).get(0), IdConverter.convertToIntID(placement).get(1)+1));
        squaresExits = squaresExits.stream().filter(x->!game.getMap().getSquare(x).isPeacefulOccupied() && !game.getMap().getSquare(x).isObstacle() && !game.getMap().getSquare(x).isBuilding()).collect(Collectors.toCollection(ArrayList::new));
        Random random = new Random();
        int rand = random.nextInt(squaresExits.size());
        game.getHeroPlacement().put(hero, squaresExits.get(rand));
        game.getMap().getSquare(squaresExits.get(rand)).setPeacefulOccupancy(hero);
    }

    public void endService(ThreadBuildingService service) {
        serviceAction(service.getHeroOccupancy());
        if(!(service.getHeroOccupancy() instanceof NPC)) {
            placeHero(service.getHeroOccupancy());
        }
        services.remove(service);
        synchronized (service.getHeroOccupancy().getLock()) {
            service.getHeroOccupancy().setInBuilding(false);
            service.getHeroOccupancy().getLock().notify();
        }
        services = services.stream().filter(Objects::nonNull).collect(Collectors.toCollection(ArrayList::new));
    }

    public abstract void serviceAction(BasicHero hero);

    public void setNumOfOccupants(int numOfOccupants) {
        this.numOfOccupants = numOfOccupants;
    }

    public String getOccupancyInfo() {
        StringBuilder info = new StringBuilder("\n" + getName() + ": " + getPlacement() + "\nЗанятость:");
        int i = 1;
        services = services.stream().filter(Objects::nonNull).collect(Collectors.toCollection(ArrayList::new));
        if (getServices().isEmpty()) {
            info.append("\n Все места свободны");
        } else {
            for (ThreadBuildingService service: getServices()) {
                info.append("\n ").append(i++).append(") ").append(service.getHeroOccupancy().toString()).append(", времени осталось: ").append(service.getTimeLeft()).append(" сек");
            }
        }
        return new String(info);
    }
}
