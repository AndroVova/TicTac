package TikTakGame;

import java.util.Random;
import java.util.Scanner;

public class TikTakGame {
    private static final char[][] GAME_TABLE = {
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '}
    };
    private static final char[][] MAPPING_TABLE = {
            {'7', '8', '9'},
            {'4', '5', '6'},
            {'1', '2', '3'}
    };

    public static void main(String[] args) {
        System.out.println("Use the following mapping table to specify a cell using numbers from 1 to 9:");
        printTable(MAPPING_TABLE);

        if (new Random().nextBoolean()) {
            makeComputerMove();

            printTable(GAME_TABLE);
        }
        while (true) {
            makePlayerMove();
            printTable(GAME_TABLE);
            if (isPlayerWin()) {
                System.out.println("Player Win");
                break;
            }
            if (isDraw()) {
                System.out.println("Sorry, DRAW!");
                break;
            }
            makeComputerMove();
            printTable(GAME_TABLE);
            if (isComputerWin()) {
                System.out.println("Computer Win");
                break;
            }
            if (isDraw()) {
                System.out.println("Sorry, DRAW!");
                break;
            }
        }
        System.out.println("Game over!");

    }

    private static boolean isPlayerWin()  {
        return isWinner('X');
    }

    private static boolean isComputerWin() {
        return isWinner('O');
    }

    private static boolean isDraw() {
        for (char[] cells : GAME_TABLE) {
            for (char cell : cells) {
                if (cell == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isWinner(char ch) {
        if(isWinnerByCols(ch)){
            return true;
        }
        if(isWinnerByRows(ch)){
            return true;
        }
        if(isWinnerByLeftToRightDiagonal(ch)){
            return true;
        }
        if(isWinnerByRightToLeftDiagonal(ch)){
            return true;
        }
        return false;
    }

    private static boolean isWinnerByRightToLeftDiagonal(char ch) {
        if (GAME_TABLE[0][2] == GAME_TABLE[1][1] &&
                GAME_TABLE[1][1] == GAME_TABLE[2][0] &&
                GAME_TABLE[0][2] == ch) {
            return true;
        }
        return false;
    }

    private static boolean isWinnerByLeftToRightDiagonal(char ch) {
        if (GAME_TABLE[0][0] == GAME_TABLE[1][1] &&
                GAME_TABLE[1][1] == GAME_TABLE[2][2] &&
                GAME_TABLE[0][0] == ch) {
            return true;
        }
        return false;
    }

    private static boolean isWinnerByRows(char ch) {
        for (int i = 0; i < GAME_TABLE.length; i++) {
            if (GAME_TABLE[0][i] == GAME_TABLE[1][i] &&
                    GAME_TABLE[1][i] == GAME_TABLE[2][i] &&
                    GAME_TABLE[0][i] == ch) {
                return true;
            }
        }
        return false;
    }

    private static boolean isWinnerByCols(char ch) {
        for (int i = 0; i < GAME_TABLE.length; i++) {
            if (GAME_TABLE[i][0] == GAME_TABLE[i][1] &&
                    GAME_TABLE[i][1] == GAME_TABLE[i][2] &&
                    GAME_TABLE[i][0] == ch) {
                return true;
            }
        }
        return false;
    }


    private static void makeComputerMove() {
        System.out.println("Computer move:\n");
        if (GAME_TABLE[1][1] == ' ') {
            GAME_TABLE[1][1] = 'O';
        } else {
            char computerWinMove = nextMoveToWin('O');
            if (computerWinMove != '0') {
                makeComputerMoveToCell(computerWinMove);
            } else {
                char playerWinMove = nextMoveToWin('X');
                if (playerWinMove != '0') {
                    makeComputerMoveToCell(playerWinMove);
                } else if (isOnlyOneO()) {

                } else {
                    randomComputerMove();
                }
            }
        }
    }


    private static boolean isOnlyOneO() {
        for (int i = 0; i < GAME_TABLE.length; i++) {
            for (int j = 0; j < GAME_TABLE[i].length; j++) {
                if (GAME_TABLE[i][j] == 'O') {
                    if (i == 1 && j == 1) {
                        randomComputerMove();
                        return true;
                    } else if (j == 2 || j == 0) {
                        for (int newIndex = 0; newIndex < 3; newIndex++) {
                            if (GAME_TABLE[newIndex][j] == ' ') {
                                makeComputerMoveToCell(MAPPING_TABLE[newIndex][j]);
                                return true;
                            }
                        }
                    } else if (i == 2 || i == 0) {
                        for (int newIndex = 0; newIndex < 3; newIndex++) {
                            if (GAME_TABLE[i][newIndex] == ' ') {
                                makeComputerMoveToCell(GAME_TABLE[i][newIndex]);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private static void randomComputerMove() {
        Random random = new Random();
        while (true) {
            int row = random.nextInt(3);
            int col = random.nextInt(3);
            if (GAME_TABLE[row][col] == ' ') {
                GAME_TABLE[row][col] = 'O';
                return;
            }
        }
    }

    private static void makeComputerMoveToCell(char cellNumber) {
        for (int i = 0; i < MAPPING_TABLE.length; i++) {
            for (int j = 0; j < MAPPING_TABLE[i].length; j++) {
                if (cellNumber == MAPPING_TABLE[i][j]) {
                    GAME_TABLE[i][j] = 'O';
                }
            }
        }
    }

    private static char nextMoveToWin(char ch) {

        for (int i = 0; i < GAME_TABLE.length; i++) {
            if (GAME_TABLE[i][0] == GAME_TABLE[i][1] && GAME_TABLE[i][0] == ch && GAME_TABLE[i][2] == ' ') {
                return MAPPING_TABLE[i][2];
            } else if (GAME_TABLE[i][1] == GAME_TABLE[i][2] && GAME_TABLE[i][1] == ch && GAME_TABLE[i][0] == ' ') {
                return MAPPING_TABLE[i][0];
            } else if (GAME_TABLE[i][0] == GAME_TABLE[i][2] && GAME_TABLE[i][0] == ch && GAME_TABLE[i][1] == ' ') {
                return MAPPING_TABLE[i][1];
            }
        }

        for (int i = 0; i < GAME_TABLE.length; i++) {
            if (GAME_TABLE[0][i] == GAME_TABLE[1][i] && GAME_TABLE[0][i] == ch && GAME_TABLE[2][i] == ' ') {
                return MAPPING_TABLE[2][i];
            } else if (GAME_TABLE[1][i] == GAME_TABLE[2][i] && GAME_TABLE[1][i] == ch && GAME_TABLE[0][i] == ' ') {
                return MAPPING_TABLE[0][i];
            } else if (GAME_TABLE[0][i] == GAME_TABLE[2][i] && GAME_TABLE[0][i] == ch && GAME_TABLE[1][i] == ' ') {
                return MAPPING_TABLE[1][i];
            }
        }

        if (GAME_TABLE[0][0] == GAME_TABLE[1][1] && GAME_TABLE[0][0] == ch && GAME_TABLE[2][2] == ' ') {
            return MAPPING_TABLE[2][2];
        } else if (GAME_TABLE[0][0] == GAME_TABLE[2][2] && GAME_TABLE[0][0] == ch && GAME_TABLE[1][1] == ' ') {
            return MAPPING_TABLE[1][1];
        } else if (GAME_TABLE[1][1] == GAME_TABLE[2][2] && GAME_TABLE[1][1] == ch && GAME_TABLE[0][0] == ' ') {
            return MAPPING_TABLE[0][0];
        }

        if (GAME_TABLE[1][1] == GAME_TABLE[0][2] && GAME_TABLE[1][1] == ch && GAME_TABLE[2][0] == ' ') {
            return MAPPING_TABLE[2][0];
        } else if (GAME_TABLE[2][0] == GAME_TABLE[1][1] && GAME_TABLE[2][0] == ch && GAME_TABLE[0][2] == ' ') {
            return MAPPING_TABLE[0][2];
        } else if (GAME_TABLE[2][0] == GAME_TABLE[0][2] && GAME_TABLE[2][0] == ch && GAME_TABLE[1][1] == ' ') {
            return MAPPING_TABLE[1][1];
        }
        return '0';
    }

    private static void makePlayerMove() {
        while (true) {
            System.out.println("Enter num form 1 to 9: ");
            String str = new Scanner(System.in).nextLine();
            if (str.length() == 1) {
                char digit = str.charAt(0);
                if (digit >= '1' && digit <= '9') {
                    makePlayerMoveToCell(digit);
                    return;
                }
            }
        }
    }

    private static void makePlayerMoveToCell(char digit) {
        for (int i = 0; i < MAPPING_TABLE.length; i++) {
            for (int j = 0; j < MAPPING_TABLE[i].length; j++) {
                if (MAPPING_TABLE[i][j] == digit) {
                    if (GAME_TABLE[i][j] == ' ') {
                        GAME_TABLE[i][j] = 'X';
                    } else {
                        System.out.println("Cell is not empty. Try Again");
                        makePlayerMove();
                    }
                }
            }
        }
    }

    private static void printTable(char[][] table) {
        for (int i = 0; i < 3; i++) {
            System.out.println("-------------");
            for (int j = 0; j < 3; j++) {
                System.out.print("| " + table[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("-------------\n");
    }


}
