package main;

import main.Battlefields.Battlefield;
import main.Battlefields.Squares.Square;
import main.Battles.Battle;
import main.Buildings.Castle;
import main.Buildings.Tower;
import main.Heroes.BasicHero;
import main.Player.BasicPlayer;
import main.Units.BasicUnit;

public class Render {
    public static void displayMap(Battlefield battlefield) {
        System.out.print("   ");
        for (char c = 'A'; c < 'A' + battlefield.getSize(); c++) {
            System.out.print(" " + c + " ");
        }
        System.out.println();

        Square row = battlefield.getStartSquare();
        for (int i = 0; i < battlefield.getSize(); i++) {
            if(i/10 == 0) {
                System.out.print(i + "  ");
            }
            else System.out.print(i + " ");
            Square current = row;
            for (int j = 0; j < battlefield.getSize(); j++) {
                if (current.isBuilding()) {
                    System.out.print(" " + current.getBuildingType().charAt(0) + " ");
                } else if (current.isObstacle()) {
                    System.out.print(" * ");
                } else if (current.isOccupied()) {
                    System.out.print(" U ");
                } else if (current.isPeacefulOccupied()) {
                    System.out.print(" H ");
                } else if (current.isRoad()) {
                    System.out.print(" o ");
                } else if (current.getGold() > 0) {
                    System.out.print(" G ");
                } else {
                    System.out.print(" - ");
                }
                current = current.getRightSquare();
            }
            System.out.println();
            row = row.getDownSquare();
        }
    }

    public static void displayBattleInfo(Battle battle){
        System.out.println(battle.getBattleInfo() + "\n\n");
    }

    public static void displayGameInfo(Game game){
        System.out.println(game.getGameInfo() + "\n\n");
    }

    public static void displayShop(BasicPlayer player){
        if(!player.getKilled().isEmpty()) {
            System.out.println("Магазин " + player + ":");
            int i = 1;
            for(BasicHero hero : player.getKilled()) {
                System.out.println(i + ") " + hero + " " + hero.getPrice());
                i++;
            }
            System.out.println("\n");
        }
    }

    public static void displayOpenMessage() {
        System.out.println(" Добро пожаловать в *почти*\n" +
                "Heroes of IU III: The Restoration of Programming\n" +
                "Выберите режим игры для Игроков (1-Бот, 2-Человек)\n" +
                "Предупреждение! Игра двух ботов по умолчанию приводит к бесконечной партии (и это не баг)\n"
             );
    }

    public static void displayGameModeChoose() {
        System.out.print("Режим игрока: ");
    }

    public static void displayHonestPlayMessage() {
        System.out.print("Честная игра? (y/n) (при нечестной: герой Игрока 1 сильнее): ");
    }

    public static void displayWrongInputMessage() {
        System.out.print("Не подходящий ввод! Попробуйте снова: ");
    }

    public static void displayNewPlayerMessage(int i, BasicPlayer player) {
        System.out.println("Игрок " + i + " - " + player);
    }

    public static void displayNewGameMessage(BasicPlayer player1, BasicPlayer player2) {
        System.out.println("Игрок 1 (" + player1 + ") против Игрока 2 (" + player2 + ")");
    }

    public static void displayUnitAttackMessage(Battle battle, BasicUnit unit1, BasicUnit unit2) {
        System.out.println("Юнит " + unit1 + " " +battle.getUnitsPlacement().get(unit1) + " атакует " + unit2 + " " + battle.getUnitsPlacement().get(unit2));
    }

    public static void displayUnitAttackMessage(Battle battle, BasicUnit unit, Tower tower) {
        System.out.println("Юнит " + unit + " " + battle.getUnitsPlacement().get(unit) + " атакует " + tower);
    }

    public static void displayTowerAttack(Tower tower, BasicUnit enemyUnit) {
        System.out.println(tower + " атакует " + enemyUnit);
    }

    public static void displayUnitDeathMessage(Battle battle, BasicUnit unit){
        System.out.println(unit + " " + battle.getUnitsPlacement().get(unit) + " погибают, обронив " + unit.getReward() + " золота");
    }

    public static void displayCastleSpecialEvent() {
        System.out.println("Баллиста ломает часть стены!");
    }

    public static void displayHeroMovementRequestMessage(BasicHero hero, String id){
        System.out.print(hero + " " + id + " движется. Выберите клетку: ");
    }

    public static void displayMovementBotMessage(BasicPlayer player, Object object, String id) {
        System.out.println(player + " движется " + object + " " + id);
    }

    public static void displayWrongSquareID() {
        System.out.print("Такой клетки нет. Выберите клетку: ");
    }

    public static void displayBuyingHeroMessage(BasicPlayer player) {
        System.out.print(player + "\n" + "Текущее золото: " + player.getPersonalGold() + '\n' +
                "Выбери героя для покупки ('n' для выхода из меню покупки): ");
    }

    public static void displayHeroBoughtMessage(BasicHero hero) {
        System.out.println(hero + " куплен");
    }

    public static void displayUnitMovementRequestMessage(Battle battle, BasicUnit unit, BasicPlayer player) {
        System.out.print("Ход " + player + ", движется " + unit + " " + battle.getUnitsPlacement().get(unit) +
                "\nВыберите клетку: ");
    }

    public static void displayWinningMessage(BasicPlayer player, int roundCount) {
        System.out.println(player + " победил за " + roundCount + " ходов");
    }

//    public static void displayBattleStartMessage(BasicHero hero1, BasicHero hero2) {
//        System.out.println("Битва между: " + hero1 + " и " + hero2);
//    }

//    public static void displayBattleStartMessage(BasicHero hero1, Tower tower) {
//        System.out.println("Битва между: " + hero1 + " и " + tower);
//    }

    public static void displayBattleStartMessage(BasicHero hero1, Object object) {
        System.out.println("Битва между: " + hero1 + " и " + object);
    }

    public static void displayBattleWinningMessage(BasicHero hero) {
        System.out.println(hero + " (" + hero.getPlayerOwner().toString() + ") победил\n\n");
    }

    public static void displayBattleWinningMessage(Tower tower) {
        System.out.println(tower + " (" + tower.getPlayerOwner().toString() + ") победила\n\n");
    }

    public static void displayBattleWinningMessage(Castle castle) {
        System.out.println(castle + " (" + castle.getPlayerOwner().toString() + ") победила\n\n");
    }

    public static void displayWrongPlacement(Object object, String id) {
        System.out.println("Невозможно разместить" +  object + " в клетке " + id);
    }

    public static void displayBotTargetTowerMessage() {
        System.out.println("Цель: Башня");
    }

    public static void displayBotTargetMessage(BasicUnit unit, String id) {
        System.out.println("Цель: " + unit + " " + id);
    }
}
