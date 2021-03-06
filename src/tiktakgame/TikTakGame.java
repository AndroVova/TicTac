package tiktakgame;

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

    private static boolean isPlayerWin() {
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
        if (isWinnerByCols(ch)) {
            return true;
        }
        if (isWinnerByRows(ch)) {
            return true;
        }
        if (isWinnerByLeftTopToRightBottomDiagonal(ch)) {
            return true;
        }
        if (isWinnerByLeftBottomToRightTopDiagonal(ch)) {
            return true;
        }
        return false;
    }

    private static boolean isWinnerByLeftBottomToRightTopDiagonal(char ch) {
        return GAME_TABLE[0][2] == GAME_TABLE[1][1] &&
                GAME_TABLE[1][1] == GAME_TABLE[2][0] &&
                GAME_TABLE[0][2] == ch;
    }

    private static boolean isWinnerByLeftTopToRightBottomDiagonal(char ch) {
        return GAME_TABLE[0][0] == GAME_TABLE[1][1] &&
                GAME_TABLE[1][1] == GAME_TABLE[2][2] &&
                GAME_TABLE[0][0] == ch;
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
            makeMoveToCell('5', 'O');
        } else {
            char cellNumber = getCellNumberToMakeNextMove('O');
            if (cellNumber != '0') {
                makeMoveToCell(cellNumber, 'O');
            } else {
                cellNumber = getCellNumberToMakeNextMove('X');
                if (cellNumber != '0') {
                    makeMoveToCell(cellNumber, 'O');
                } else {
                    cellNumber = getCellNumberIfOnlyOneODetected();
                    if (cellNumber != '0') {
                        makeMoveToCell(cellNumber, 'O');
                    } else {
                        makeRandomComputerMove();
                    }
                }
            }
        }
    }

    private static char getCellNumberIfOnlyOneODetected() {
        for (int i = 0; i < GAME_TABLE.length; i++) {
            for (int j = 0; j < GAME_TABLE[i].length; j++) {
                if (GAME_TABLE[i][j] == 'O') {
                    if (i == 1 && j == 1) {
                        return '0';
                    } else if (j == 2 || j == 0) {
                        for (int newIndex = 0; newIndex < 3; newIndex++) {
                            if (GAME_TABLE[newIndex][j] == ' ') {
                                return MAPPING_TABLE[newIndex][j];
                            }
                        }
                    } else if (i == 2 || i == 0) {
                        for (int newIndex = 0; newIndex < 3; newIndex++) {
                            if (GAME_TABLE[i][newIndex] == ' ') {
                                return GAME_TABLE[i][newIndex];
                            }
                        }
                    }
                }
            }
        }
        return '0';
    }

    private static void makeRandomComputerMove() {
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

    private static char getCellNumberToMakeNextMove(char ch) {
        char cellNumberToMakeNextMove = getCellNumberToMakeNextMoveByCols(ch);
        if (cellNumberToMakeNextMove != '0')
            return cellNumberToMakeNextMove;

        cellNumberToMakeNextMove = getCellNumberToMakeNextMoveByRows(ch);
        if (cellNumberToMakeNextMove != '0')
            return cellNumberToMakeNextMove;

        cellNumberToMakeNextMove = getCellNumberToMakeNextMoveByLeftTopToRightBottomDiagonal(ch);
        if (cellNumberToMakeNextMove != '0')
            return cellNumberToMakeNextMove;

        cellNumberToMakeNextMove = getCellNumberToMakeNextMoveByRightTopToLeftBottomDiagonal(ch);
        if (cellNumberToMakeNextMove != '0')
            return cellNumberToMakeNextMove;

        return '0';
    }

    private static char getCellNumberToMakeNextMoveByRightTopToLeftBottomDiagonal(char ch) {
        if (GAME_TABLE[1][1] == GAME_TABLE[0][2] && GAME_TABLE[1][1] == ch && GAME_TABLE[2][0] == ' ') {
            return MAPPING_TABLE[2][0];
        } else if (GAME_TABLE[2][0] == GAME_TABLE[1][1] && GAME_TABLE[2][0] == ch && GAME_TABLE[0][2] == ' ') {
            return MAPPING_TABLE[0][2];
        } else if (GAME_TABLE[2][0] == GAME_TABLE[0][2] && GAME_TABLE[2][0] == ch && GAME_TABLE[1][1] == ' ') {
            return MAPPING_TABLE[1][1];
        }
        return '0';
    }

    private static char getCellNumberToMakeNextMoveByLeftTopToRightBottomDiagonal(char ch) {
        if (GAME_TABLE[0][0] == GAME_TABLE[1][1] && GAME_TABLE[0][0] == ch && GAME_TABLE[2][2] == ' ') {
            return MAPPING_TABLE[2][2];
        } else if (GAME_TABLE[0][0] == GAME_TABLE[2][2] && GAME_TABLE[0][0] == ch && GAME_TABLE[1][1] == ' ') {
            return MAPPING_TABLE[1][1];
        } else if (GAME_TABLE[1][1] == GAME_TABLE[2][2] && GAME_TABLE[1][1] == ch && GAME_TABLE[0][0] == ' ') {
            return MAPPING_TABLE[0][0];
        }
        return '0';
    }

    private static char getCellNumberToMakeNextMoveByRows(char ch) {
        for (int i = 0; i < GAME_TABLE.length; i++) {
            if (GAME_TABLE[0][i] == GAME_TABLE[1][i] && GAME_TABLE[0][i] == ch && GAME_TABLE[2][i] == ' ') {
                return MAPPING_TABLE[2][i];
            } else if (GAME_TABLE[1][i] == GAME_TABLE[2][i] && GAME_TABLE[1][i] == ch && GAME_TABLE[0][i] == ' ') {
                return MAPPING_TABLE[0][i];
            } else if (GAME_TABLE[0][i] == GAME_TABLE[2][i] && GAME_TABLE[0][i] == ch && GAME_TABLE[1][i] == ' ') {
                return MAPPING_TABLE[1][i];
            }
        }
        return '0';
    }

    private static char getCellNumberToMakeNextMoveByCols(char ch) {
        for (int i = 0; i < GAME_TABLE.length; i++) {
            if (GAME_TABLE[i][0] == GAME_TABLE[i][1] && GAME_TABLE[i][0] == ch && GAME_TABLE[i][2] == ' ') {
                return MAPPING_TABLE[i][2];
            } else if (GAME_TABLE[i][1] == GAME_TABLE[i][2] && GAME_TABLE[i][1] == ch && GAME_TABLE[i][0] == ' ') {
                return MAPPING_TABLE[i][0];
            } else if (GAME_TABLE[i][0] == GAME_TABLE[i][2] && GAME_TABLE[i][0] == ch && GAME_TABLE[i][1] == ' ') {
                return MAPPING_TABLE[i][1];
            }
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
                    if (makeMoveToCell(digit, 'X')) {
                        return;
                    }
                }
            }
        }
    }

    private static boolean makeMoveToCell(char cellNumber, char ch) {
        for (int i = 0; i < MAPPING_TABLE.length; i++) {
            for (int j = 0; j < MAPPING_TABLE[i].length; j++) {
                if (MAPPING_TABLE[i][j] == cellNumber) {
                    if (GAME_TABLE[i][j] == ' ') {
                        GAME_TABLE[i][j] = ch;
                        return true;
                    } else {
                        System.out.println("Cell is not empty. Try Again");
                        return false;
                    }
                }
            }
        }
        return false;
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