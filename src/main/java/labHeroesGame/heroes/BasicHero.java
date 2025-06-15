package labHeroesGame.heroes;

import labHeroesGame.player.BasicPlayer;
import labHeroesGame.units.BasicUnit;

import java.util.ArrayList;

public abstract class BasicHero {
    static int heroIdentifiers = 0;
    private final int identifier;
    private String name;
    private BasicPlayer playerOwner;
    private int price;
    private int speed;
    private ArrayList<BasicUnit> army = new ArrayList<BasicUnit>();

    public int getSpeed() {
        return speed;
    }

    public int getPrice() {
        return price;
    }

    public ArrayList<BasicUnit> getArmy() {
        return army;
    }

    public String getName() {
        return name;
    }


    public abstract void refill();

    public void setPrice(int price) {
        this.price = price;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BasicHero(){
        this.identifier = heroIdentifiers;
        heroIdentifiers++;
    }

    public BasicHero(BasicPlayer player){
        this.identifier = heroIdentifiers;
        heroIdentifiers++;
        playerOwner = player;
    }


    public boolean addUnit(BasicUnit unit) {
        int indexInArmy = unitInArmy(unit.getName());
        if (army.size() < 10 && indexInArmy == -1) {
            army.add(unit);
            unit.setHeroOwner(this);
        }
        else if (indexInArmy != -1) {
            army.get(indexInArmy).add(unit.getAmount());
        } else return false;
        return true;
    }

    public int unitInArmy(String unitName) {
        for (int i = 0; i < army.size(); i++){
            if (army.get(i).getName().equals(unitName))
                return i;
        }
        return -1;
    }


    public void setPlayerOwner(BasicPlayer playerOwner) {
        this.playerOwner = playerOwner;
    }

    public BasicPlayer getPlayerOwner() {
        return playerOwner;
    }

    public int getIdentifier() {
        return identifier;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.getClass() != obj.getClass()) return false;
        return this.getIdentifier() == ((BasicHero) obj).getIdentifier();
    }

    @Override
    public String toString() {
        return name + " (id: " + identifier + ")";
    }
}
