package labHeroesGame;

import labHeroesGame.authorization.AllUsers;
import labHeroesGame.authorization.User;
import labHeroesGame.battlefields.preBuilds.MapCreator;
import labHeroesGame.battlefields.preBuilds.MapPreBuilds;
import labHeroesGame.battlefields.preBuilds.PreBuild;
import labHeroesGame.gameRecords.GameRecords;
import labHeroesGame.gameSaving.GameLoader;
import labHeroesGame.heroes.ChampLight;
import labHeroesGame.heroes.UltimateHero;
import labHeroesGame.player.BasicPlayer;
import labHeroesGame.player.Bot;
import labHeroesGame.player.Human;

import java.util.Scanner;

public class ConfigureGame {
    private static Scanner scanner = new Scanner(System.in);

    public static BasicPlayer choosePlayer(int i){
        Render.displayGameModeChoose();
        int input;
        while(true) {
            while(!scanner.hasNextInt()) {
                Render.displayWrongInputMessage();
            }
            input = scanner.nextInt();
            if(input == 1 || input == 2) {
                break;
            } else {
                Render.displayWrongInputMessage();
            }
        }
        if(input == 1) {
            Bot bot = new Bot();
            Render.displayNewPlayerMessage(i, bot);
            return bot;
        } else {
            Human human = new Human(scanner);
            Render.displayNewPlayerMessage(i, human);
            return human;
        }
    }

    public static boolean chooseMode(Scanner scanner){
        String input;
        while(true) {
            while(!scanner.hasNext()) {
                Render.displayWrongInputMessage();
            }
            input = scanner.next().toLowerCase();
            if(input.charAt(0) == 'y' || input.charAt(0) == 'n' ) {
                break;
            } else {
                Render.displayWrongInputMessage();
            }
        }
        return input.charAt(0) == 'y';
    }

    public static PreBuild choosePreBuild(boolean isGame){
        Render.displayPreBuildsToChoose(isGame);
        int intInput;
        String input;
        int preBuildShift = ((isGame)?(0):(1));
        while(true){
            if(scanner.hasNextInt()){
                intInput = scanner.nextInt() + preBuildShift;
                if(intInput > 0 && intInput <= 1 + MapPreBuilds.getCustomPreBuilds().size()) {
                    if(intInput == 1) {
                        return MapPreBuilds.getMainMapPreBuild();
                    } else {
                        return MapPreBuilds.getCustomPreBuilds().get(intInput-2);
                    }
                } else{
                    Render.displayWrongInputMessage();
                }
            } else{
                input = scanner.next();
                if(input.equals("end")){
                    return null;
                }
                Render.displayWrongInputMessage();
            }
        }
    }

    public static void setUpGame(User curUser){
        BasicPlayer player1 = choosePlayer(1);
        BasicPlayer player2 = choosePlayer(2);
        Render.displayHonestPlayMessage();
        if(chooseMode(scanner)) {
            new ChampLight(player1);
        } else {
            new UltimateHero(player1);
        }
        new ChampLight(player2);
        Render.displayNewGameMessage(player1, player2);
        PreBuild preBuild = choosePreBuild(true);
        if (preBuild == null) {
            return;
        }
        Game game = new Game(player1, player2, preBuild, curUser);
        game.startGame();
    }

    public static User authorizeUser() {
        String inputLogin;
        String inputPassword;
        AllUsers.loadAllUsers();
        while (true) {
            Render.displayUserAuthorization();
            inputLogin = scanner.next();
            if(inputLogin.equals("skip")) {
                return null;
            }
            if(inputLogin.length() > 3 && !Character.isDigit(inputLogin.charAt(0))) {
                Render.displayPasswordAuthorization();
                inputPassword = scanner.next();
                int userChecker = AllUsers.checkUser(inputLogin, inputPassword);
                switch(userChecker) {
                    case 1: return AllUsers.getUser(inputLogin);
                    case 2: {
                        Render.displayWrongInputMessage();
                        continue;
                    }
                    case 3: {
                        User user = new User(inputLogin, inputPassword);
                        AllUsers.getListOfUsers().add(user);
                        AllUsers.saveAllUsers();
                        return user;
                    }
                }
            } else {
                Render.displayWrongInputMessage();
            }
        }
    }

    public static void menu(User curUser) {
        int intInput;
        String input;
        while (true) {
            Render.displayMenu(curUser);
            if(scanner.hasNextInt()) {
                intInput = scanner.nextInt();
                switch (intInput){
                    case 1: {
                        setUpGame(curUser);
                        return;
                    }
                    case 2: {
                        MapCreator.createPreBuild(scanner, curUser);
                        break;
                    }
                    case 3: {
                        if(!MapPreBuilds.getCustomPreBuilds().stream().filter(x -> x.getUserCreator() == curUser).toList().isEmpty()) {
                            MapCreator.changePreBuild(scanner, choosePreBuild(false));
                            break;
                        }
                    }
                    case 4: {
                        if(GameLoader.hasAutoSave(curUser)) {
                            Game game = GameLoader.loadSave(curUser);
                            assert game != null;
                            if(game.getLeftPlayer() instanceof Human) {
                                game.getLeftPlayer().setScanner(scanner);
                            } else if(game.getRightPlayer() instanceof Human) {
                                game.getRightPlayer().setScanner(scanner);
                            }
                            game.gameRound();
                        }
                    }
                    case 5: {
                        if(!GameRecords.getAllRecords().isEmpty()) {
                            Render.displayRecords();
                            continue;
                        }
                    }
                    default: Render.displayWrongInputMessage();
                }
            } else {
                input = scanner.next();
                if(input.equals("end")) {
                    return;
                } else {
                    Render.displayWrongInputMessage();
                }

            }
        }
    }

    public static void setScanner(Scanner scanner) {
        ConfigureGame.scanner = scanner;
    }

    public static void main(String[] args) {
        new MapPreBuilds();
        User curUser = authorizeUser();
        if (curUser == null) {
            return;
        }
        Render.displayOpenMessage();
        menu(curUser);
    }
}
