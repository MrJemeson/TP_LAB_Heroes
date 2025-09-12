package labHeroesGame.authorization;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private final String name;
    private final String password;

    @Serial
    private static final long serialVersionUID = 1L;

    public User() {
        this.name = null;
        this.password = null;
    }

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

    @Override
    public String toString() {
        return "User " + name;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof User user)) return false;
        return Objects.equals(name, user.name) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password);
    }
}
