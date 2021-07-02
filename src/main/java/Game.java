import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
public class Game {
    //константы
    private static final char DOT_EMPTY = '.';
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random RANDOM = new Random();

    //переменные
    private static char[][] field;
    private static char dotUser;
    private static char dotAI;
    private static int sizeField;
    private static int scoreUser;
    private static int scoreAI;

    public static void main(String[] args) {
        while (true) {
            playRound();
            System.out.printf("SCORE IS: HUMAN   AI\n            %d      %d\n", scoreUser, scoreAI);
            System.out.print("Wanna play again? Enter Y or N >>> ");
            if (!SCANNER.next().toLowerCase().equals("y")) break;
        }
    }

    private static void playRound() {
        userChoice();
        //System.out.println(dotUser +" " + dotAI);
        initField();
        printField();

        while (true) {
            humanTurn();
            if (checkGameState(dotUser)) break;

            aiTurn();
            printField();
            if (checkGameState(dotAI)) break;
        }
    }

    private static boolean checkGameState(char dot) {
        if (checkDraw()) return true;

        if (checkWin(dot)) {
            if (dot == dotUser) {
                System.out.println("Human win!");
                scoreUser++;
            } else {
                System.out.println("AI win!");
                scoreAI++;
            }
            return true;
        }
        return false;
    }

    private static boolean checkWin(char dot) {


        if (checkHoriz(dot) == true) return true;
        if (checkVert(dot) == true) return true;
        if (checkLeftDiag(dot) == true) return true;
        if (checkRightDiag(dot)==true) return true;
        if(checkReverseLeftDiag(dot)== true) return true;


        return false;
    }

    private static boolean checkDraw() {
        for (int y = 0; y < sizeField; y++) {
            for (int x = 0; x < sizeField; x++) {
                if (isCellEmpty(y, x)) return false;
            }
        }
        System.out.println("Ooops... It's DRAW!");
        return true;
    }

    private static void aiTurn() {
        int x = 0, y = 0;

        int mediana = (int)Math.ceil(sizeField/2);

        do {
            if(isCellEmpty(mediana,mediana)) {
                x=mediana;
                y=mediana;

            } else {
                x = RANDOM.nextInt(sizeField);
                y = RANDOM.nextInt(sizeField);
                // field[y][x] = dotAI;
            }
        } while (!isCellEmpty(y, x));

        field[y][x] = dotAI;
    }

    private static void humanTurn() {
        int x, y;

        do {
            System.out.print("Please enter coordinates of your turn x & y split with whitespace --> ");
            x = SCANNER.nextInt() - 1;
            y = SCANNER.nextInt() - 1;

        } while (!isCellValid(y, x) || !isCellEmpty(y, x));

        field[y][x] = dotUser;
    }

    private static boolean isCellEmpty(int y, int x) {
        return field[y][x] == DOT_EMPTY;
    }

    private static boolean isCellValid(int y, int x) {
        return x >= 0 && y >= 0 && x < sizeField && y < sizeField;
    }

    //выводим игровое поле
    private static void printField() {
        System.out.print("+");
        for (int i = 0; i < sizeField * 2 + 1; i++) {
            System.out.print(i % 2 == 0 ? "-" : i / 2 + 1);
        }
        System.out.println();

        for (int i = 0; i < sizeField; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < sizeField; j++) {
                System.out.print(field[i][j] + "|");
            }
            System.out.println();
        }
        for (int i = 0; i <= sizeField * 2 + 1; i++) {
            System.out.print("_");
        }
        System.out.println();
    }

    //игрок выбирает размеры игрового поля
    private static void initField() {
        int size;


        System.out.print("Enter the dimensions of the playing field ----> ");
        size = SCANNER.nextInt();
        sizeField = size;

        while (true) {
            if (size % 2 == 0) {
                System.out.print("Enter even value ----> ");
                size = SCANNER.nextInt();
                sizeField=size;
            } else break;


        }

        field = new char[size][size];

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                field[y][x] = DOT_EMPTY;
            }
        }


    }

    //игрок выбирает X или 0
    private static void userChoice() {
        int choice;
        boolean stopWhile = true;

        System.out.println("X    O");
        System.out.println("1    2");
        System.out.print("Please select X or O ----> ");
        choice = SCANNER.nextInt();

        while (true) {
            if (choice == 1) {
                dotUser = 'X';
                dotAI = 'O';
                break;
            } else if (choice == 2) {
                dotUser = 'O';
                dotAI = 'X';
                break;
            } else {
                System.out.print("1 or 2 >:( ---> ");
                choice = SCANNER.nextInt();
            }
        }
    }

    //проверяем по горизонтале
    private static boolean checkHoriz(char dot) {
        int check = 0;



        for(int y = 0; y < sizeField; y++) {
            check=0;

            for(int x = 0; x < sizeField; x++) {
                if (field[y][x] == dot) {
                    check++;
                }
                if (check == sizeField) return true;
            }
        }
        return false;
    }

    //проверяем по вертикале
    private static boolean checkVert(char dot) {
        int check = 0;

        for (int x = 0; x < sizeField; x++) {
            check=0;
            for (int y = 0; y < sizeField; y++) {
                if (field[y][x] == dot) {
                    check++;
                }
                if(check==sizeField) return true;
            }
        }
        return false;
    }

    //проверяем диагональ
    private static boolean checkLeftDiag(char dot) {
        int check = 0;

        for (int i = 0; i < sizeField; i++) {
            if (field[i][i] == dot) {
                check++;
            }
            if(check==sizeField) return true;
        }

        return false;
    }

    private static boolean checkReverseLeftDiag(char dot) {
        int check=0;

        for (int i = sizeField-1; i >= 0; i--) {
            if (field[i][i] == dot) {
                check++;
            }
            if(check==sizeField) return true;
        }
        return false;
    }

    private static boolean checkRightDiag(char dot) {
        int check=0;
        int reverse=sizeField-1;

        for (int i = 0; i < sizeField; i++) {
            if(field[i][reverse]==dot) {
                check++;
            }
            reverse--;
            if (check==sizeField) return true;
        }

        return false;
    }




}


