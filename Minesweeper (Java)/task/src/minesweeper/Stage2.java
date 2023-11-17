package minesweeper;

import java.util.Random;
import java.util.Scanner;

public class Stage2 {

  private static Scanner scanner = new Scanner(System.in);
  private static Random random = new Random();

  private static void call() {
    // write your code here
    System.out.print("How many mines do you want on the field? ");
    int mines = scanner.nextInt();
    printField(mines);
  }

  private static void printField(int mines) {
    char[][] field = new char[9][9];
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        field[i][j] = '.';
      }
    }
    while (mines > 0) {
      int i = random.nextInt(9);
      int j = random.nextInt(9);
      if (field[i][j] == '.') {
        field[i][j] = 'X';
        --mines;
      }
    }
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        System.out.print(field[i][j]);
      }
      System.out.println();
    }
  }
}
