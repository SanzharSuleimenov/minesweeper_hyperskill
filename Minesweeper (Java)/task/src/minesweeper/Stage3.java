package minesweeper;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Stage3 {

  private final Scanner scanner = new Scanner(System.in);
  private final Random random = new Random();
  private final int m;
  private final int n;
  private final int[][] field;

  public Stage3() {
    this.m = 9;
    this.n = 9;
    field = new int[m + 2][n + 2];
  }

  public void generateField(int mines) {
    clearArray();
    ArrayList<Integer> positions = new ArrayList<>();
    for (int i = 0; i < 81; i++) {
      positions.add(i);
    }
    while (mines > 0) {
      int randomPosition = random.nextInt(positions.size());
      int randomCell = positions.get(randomPosition);
      positions.remove(randomPosition);
      int i = randomCell / m + 1;
      int j = randomCell % n + 1;
      field[i][j] = -1;
      addMine(i, j);
      mines--;
    }
  }

  public void printField() {
    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        if (field[i][j] == 0) {
          System.out.print(".");
        } else if (field[i][j] == -1) {
          System.out.print("X");
        } else {
          System.out.print(field[i][j]);
        }
      }
      System.out.println();
    }
  }

  public void addMine(int i, int j) {
    if (field[i - 1][j - 1] != -1) {
      field[i - 1][j - 1]++;
    }
    if (field[i - 1][j] != -1) {
      field[i - 1][j]++;
    }
    if (field[i - 1][j + 1] != -1) {
      field[i - 1][j + 1]++;
    }

    if (field[i][j - 1] != -1) {
      field[i][j - 1]++;
    }
    if (field[i][j + 1] != -1) {
      field[i][j + 1]++;
    }

    if (field[i + 1][j - 1] != -1) {
      field[i + 1][j - 1]++;
    }
    if (field[i + 1][j] != -1) {
      field[i + 1][j]++;
    }
    if (field[i + 1][j + 1] != -1) {
      field[i + 1][j + 1]++;
    }
  }

  public void clearArray() {
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        field[i][j] = 0;
      }
    }
  }
}
