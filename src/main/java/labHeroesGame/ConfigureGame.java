package labHeroesGame;

import labHeroesGame.battlefields.MapPreBuilds;
import labHeroesGame.heroes.BasicHero;
import labHeroesGame.heroes.ChampLight;
import labHeroesGame.heroes.UltimateHero;
import labHeroesGame.player.BasicPlayer;
import labHeroesGame.player.Bot;
import labHeroesGame.player.Human;

import java.util.Scanner;

public class ConfigureGame {

    public static BasicPlayer choosePlayer(int i){
        Scanner scanner = new Scanner(System.in);
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
            Human human = new Human();
            Render.displayNewPlayerMessage(i, human);
            return human;
        }
    }

    public static boolean chooseMode(){
        Scanner scanner = new Scanner(System.in);
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

    public static void main(String[] args) {
        new MapPreBuilds();
        Render.displayOpenMessage();
        BasicPlayer player1 = choosePlayer(1);
        BasicPlayer player2 = choosePlayer(2);
        Render.displayHonestPlayMessage();
        if(chooseMode()) {
            new ChampLight(player1);
        } else {
            new UltimateHero(player1);
        }
        new ChampLight(player2);
        Render.displayNewGameMessage(player1, player2);
        Game game = new Game(player1, player2);
        game.startGame();
    }
}
