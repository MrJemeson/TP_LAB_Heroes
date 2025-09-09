package labHeroesGame;

import labHeroesGame.authorization.User;
import labHeroesGame.battlefields.Battlefield;
import labHeroesGame.battlefields.preBuilds.MapPreBuilds;
import labHeroesGame.battlefields.preBuilds.PreBuild;
import labHeroesGame.battlefields.squares.Square;
import labHeroesGame.battles.Battle;
import labHeroesGame.buildings.Castle;
import labHeroesGame.buildings.ThreadBuilding;
import labHeroesGame.buildings.Tower;
import labHeroesGame.gameRecords.GameRecord;
import labHeroesGame.gameRecords.GameRecords;
import labHeroesGame.gameSaving.GameLoader;
import labHeroesGame.heroes.BasicHero;
import labHeroesGame.player.BasicPlayer;
import labHeroesGame.units.BasicUnit;

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
        System.out.println(GlobalTime.getTime() + "\n" + game.getGameInfo() + "\n\n");
    }

    public static void displayShop(BasicPlayer player) {
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
                "Предупреждение! Игра двух ботов по умолчанию приводит к бесконечной партии (и это не баг)\n"
             );
    }

    public static void displayMenu(User curUser){
        int i = 3;
        System.out.println("\nМеню.\n" +
                "1) Начать игру\n" +
                "2) Создать пребилд\n" +
                ((!MapPreBuilds.getCustomPreBuilds().stream().filter(x -> x.getUserCreator().equals(curUser)).toList().isEmpty())?(i++ + ") Изменить пребилд\n"):("")) +
                ((GameLoader.hasAutoSave(curUser))?(i++ + ") Загрузить игру\n"):("")) +
                ((GameRecords.getAllRecords().isEmpty())?(""):(i + ") Показать таблицу лидеров\n")));
    }

    public static void displayUserAuthorization() {
        System.out.print("\nВведите имя пользователя: ");
    }

    public static void displayPasswordAuthorization(){
        System.out.print("Введите пароль: ");
    }

    public static void displayPreBuildsToChoose(boolean isGame){
        System.out.println("Выберите пребилд:" + ((isGame)?("\n 1) Классический пребилд"):("")));
        if(!MapPreBuilds.getCustomPreBuilds().isEmpty()) {
            int i = 2 - ((isGame)?(0):(1));
            for(PreBuild preBuild: MapPreBuilds.getCustomPreBuilds()) {
                System.out.println(" " + i++ + ") CUSTOM: " + preBuild);
            }
        }
    }

    public static void displayGameModeChoose() {
        System.out.print("Выберите режим игры для Игроков (1-Бот, 2-Человек)\n"  +
                "Предупреждение! Игра двух ботов по умолчанию приводит к бесконечной партии (и это не баг)\n" +"Режим игрока: ");
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

    public static void displayUnitDeathMessage(Battle battle, BasicUnit unit) {
        System.out.println(unit + " " + battle.getUnitsPlacement().get(unit) + " погибают, обронив " + unit.getReward() + " золота");
    }

    public static void displayCastleSpecialEvent()  {
        System.out.println("Баллиста ломает часть стены!");
    }

    public static void displayHeroMovementRequestMessage(BasicHero hero, String id) {
        System.out.print(hero + " " + id + " движется. Выберите клетку: ");
    }

    public static void displayMovementBotMessage(BasicPlayer player, Object object, String id) {
        System.out.println(player + " движется " + object + " " + id);
    }

    public static void displayWrongSquareID() {
        System.out.print("Некорректная клетка. Выберите клетку: ");
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

    public static void displayMapNameRequest(){
        System.out.print("Название карты: ");
    }

    public static int displayObjectsToPlace(int castleNum, int towerNum, int heroPlacementNum, int hotelNum, int cafeNum, int barberNum) {
        System.out.println("Выберите объект для установки: ");
        int objInt = 0;
        System.out.println(++objInt + " = Road" + ", " + ++objInt + " = Obstacle" + ", " + ++objInt + " = Nothing"
                +((castleNum<2)?(", " + ++objInt + " = Castle"):("")) +
                ((towerNum<6)?(", " + ++objInt + " = Tower"):("")) +
                ((heroPlacementNum<2)?(", " + ++objInt + " = Hero spawn"):("")) +
                ((hotelNum<1)?(", " + ++objInt + " = Hotel"):("")) +
                ((cafeNum<1)?(", " + ++objInt + " = Cafe"):("")) +
                ((barberNum<1)?(", " + ++objInt + " = Barbershop"):("")) +
                ((castleNum == 2 && towerNum == 6 && heroPlacementNum == 2)?(" (end = выйти из режима создания пребилда)"):("")));
        return objInt;
    }

    public static void displaySquareToPlaceRequest(String type) {
        System.out.print("Введите ячейку для размещения " + type + ": ");
    }

    public static void displayLineToPlaceRequest(String type) {
        System.out.print("Введите ячейку/линию для размещения " + type + ": ");
    }

    public static void displayNotEnoughBuildingsCreated(int towerNum, int castleNum, int heroPlacementNum) {
        System.out.println(("Выставлено недостаточно зданий следующих типов:") + ((castleNum<2)?(" Castle"):(" ")) + ((towerNum<6)?(" Tower"):("")) + ((heroPlacementNum<2)?(" HeroSpawn"):("")));
    }

    public static void displayObjectSideRequestMessage() {
        System.out.println("Владелец: 1 = Первый игрок; 2 = Второй игрок");
    }

    public static void displayPreBuildSavedMessage() {
        System.out.println("Пребилд сохранен");
    }

    public static void displayPreBuildLoaded(String name) {
        System.out.println("Загружен пребилд: " + name);
    }

    public static void displayPreBuildLoadError(String name) {
        System.out.println("Ошибка загрузки файла: " + name);
    }

    public static void displayFolderLoadError() {
        System.out.println("Папка не найдена: savedPreBuilds");
    }

    public static void displayAutoSaveMessage(){
        System.out.println("Автосохранение выполнено.");
    }

    public static void displayRecords() {
        int i = 1;
        System.out.println("\n");
        for (GameRecord gameRecord: GameRecords.getAllRecords()) {
            System.out.println(i++ + ")" + gameRecord.getUserRecord() + ": " + gameRecord.getLeftPlayer() + " победил " + gameRecord.getRightPlayer() + " за " + gameRecord.getNumOfRounds() + " ходов.");
        }
        System.out.println("\n");
    }

    public static void displayFullBuildingMessage(ThreadBuilding building) {
        System.out.println(building.getOccupancyInfo());
        System.out.println("Все места в " + building.getName() + " заняты. skip = выход из здания");
    }

    public static void displayClosedBuildingMessage(ThreadBuilding building) {
        System.out.println("Здание " + building.getName() + " на данным момент закрыто. Оно будет открыто в " + building.getWorkTime().get(0) + " часов" +
                "\nТекущее время: " + GlobalTime.getTime() +
                "\nskip = выход из здания");
    }

    public static void displayEnterBuildingRequest(String name) {
        System.out.println("Войти в " + name + "? y/n");
    }

    public static void displayHeroWaitingRequestMessage(){
        System.out.println("Герой занят услугой. Дождаться? y/n");
    }

    public static void displayHeroWaitingMessage() {
        System.out.println("Ожидание героя...");
    }
}
