package labHeroesGame.authorization;

import java.io.Serializable;

public class User implements Serializable {
    private final String name;
    private final String password;

    public User(String name, String password){
        this.name = name;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
