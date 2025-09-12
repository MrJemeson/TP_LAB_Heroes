package labHeroesGame.buildings;

import labHeroesGame.heroes.BasicHero;

import java.io.Serializable;

public class ThreadBuildingService extends Thread implements Serializable {
    private BasicHero heroOccupancy;
    private int timeLeft;
    private ThreadBuilding building;

    public ThreadBuildingService (ThreadBuilding building, BasicHero hero) {
        setDaemon(true);
        heroOccupancy = hero;
        this.building = building;
        timeLeft = building.getOccupancyTime();
    }

    public synchronized int getTimeLeft() {
        return timeLeft;
    }

    @Override
    public void run() {
        try {
            while (timeLeft > 0) {
                Thread.sleep(1000); // ждем 1 сек
                synchronized (this) {
                    timeLeft--;
                }
            }
            building.endService(this);
        } catch (InterruptedException e) {}
    }

    public BasicHero getHeroOccupancy() {
        return heroOccupancy;
    }
}
