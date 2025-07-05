package labHeroesGame;

import labHeroesGame.battlefields.preBuilds.MapCreator;
import labHeroesGame.battlefields.preBuilds.MapPreBuilds;
import labHeroesGame.battlefields.preBuilds.PreBuild;
import labHeroesGame.battlefields.preBuilds.PreBuildLoader;
import labHeroesGame.gameSaving.GameLoader;
import labHeroesGame.heroes.ChampLight;
import labHeroesGame.heroes.UltimateHero;
import labHeroesGame.player.BasicPlayer;
import labHeroesGame.player.Bot;
import labHeroesGame.player.Human;

import java.util.Scanner;

public class ConfigureGame {
    private static final Scanner scanner = new Scanner(System.in);

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

    public static PreBuild choosePreBuild(){
        Render.displayPreBuildsToChoose();
        int intInput;
        String input;
        while(true){
            if(scanner.hasNextInt()){
                intInput = scanner.nextInt();
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
                    System.exit(0);
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
        Game game = new Game(player1, player2, choosePreBuild(), curUser);
        game.startGame();
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
                        if(GameLoader.hasAutoSave(curUser)) {
                            Game game = GameLoader.loadSave(curUser);
                            if(game.getLeftPlayer() instanceof Human) {
                                game.getLeftPlayer().setScanner(scanner);
                            } else if(game.getRightPlayer() instanceof Human) {
                                game.getRightPlayer().setScanner(scanner);
                            }
                            game.gameRound();

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

    public static void main(String[] args) {
        new MapPreBuilds();
        User curUser = new User("TestUser");
        Render.displayOpenMessage();
        menu(curUser);
    }
}
