package labHeroesTests;

import labHeroesGame.ConfigureGame;
import labHeroesGame.authorization.AllUsers;
import labHeroesGame.authorization.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthorizationTests {

    @Test
    public void testCreatingUser() {
        ByteArrayInputStream in = new ByteArrayInputStream(("Tester\ntest\n").getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        ConfigureGame.setScanner(scanner);
        ConfigureGame.authorizeUser();
        AllUsers.loadAllUsers();
        assertEquals(1, AllUsers.checkUser("Tester", "test"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"us\nskip\n", "123456\nskip\n", "1ustest\nskip\n"})
    public void testIncorrectNames(String input) {
        ByteArrayInputStream in = new ByteArrayInputStream((input).getBytes());
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        ConfigureGame.setScanner(scanner);
        ConfigureGame.authorizeUser();
        assertTrue(output.toString().endsWith("Не подходящий ввод! Попробуйте снова: \nВведите имя пользователя: "));
    }

    @AfterEach
    public void clearUsers() {
        AllUsers.getListOfUsers().remove(AllUsers.getUser("Tester"));
        AllUsers.saveAllUsers();
    }
}
