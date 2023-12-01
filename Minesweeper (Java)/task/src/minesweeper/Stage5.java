package minesweeper;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Stage5 {

  private final Scanner scanner;
  private final int[][] backField;
  private final char[][] gameField;
  private final Set<Integer> mines;
  private final Set<Integer> marks;
  private final int minesNumber;
  private final Random random;
  private boolean isInitiated = false;
  private boolean isWin = false;

  public Stage5() {
    this.scanner = new Scanner(System.in);
    System.out.print("How many mines do you want on the field? ");
    this.minesNumber = scanner.nextInt();
    this.backField = new int[11][11];
    this.gameField = new char[11][11];
    this.mines = new HashSet<>();
    this.marks = new HashSet<>();
    this.random = new Random();

    for (int i = 1; i <= 9; i++) {
      for (int j = 1; j <= 9; j++) {
        gameField[i][j] = '.';
      }
    }
  }

  public void run() {
    printField();
    boolean isEnd = false;
    while (!isEnd) {
      System.out.print("Set/unset mines marks or claim a cell as free: ");
      int col = scanner.nextInt();
      int row = scanner.nextInt();
      String command = scanner.next();
      if (command.equals("free")) {
        if (!isInitiated) {
          initField(col, row);
          isInitiated = true;
        }
        boolean result = exploreCell(col, row);
        if (!result) {
          showMines();
          isEnd = true;
        }
      } else {
        boolean result = markCell(col, row);
        if (result) {
          isWin = true;
          isEnd = true;
        }
      }
      printField();
    }
    if (isWin) {
      System.out.println("Congratulations! You found all the mines!");
    } else {
      System.out.println("You stepped on a mine and failed!");
    }
  }

  private void showMines() {
    for (Integer mine: mines) {
      int[] indexes = numberToIndexes(mine);
      gameField[indexes[0]][indexes[1]] = 'X';
    }
  }

  private boolean exploreCell(int col, int row) {
    if (backField[row][col] == -1) {
      return false;
    }
    if (backField[row][col] > 0) {
      gameField[row][col] = (char) (backField[row][col] + '0');
      return true;
    }
    if (gameField[row][col] == '*') {
      marks.remove(indexesToNumber(row, col));
    }
    Queue<int[]> queue = new ArrayDeque<>();
    queue.add(new int[]{row, col});
    while (!queue.isEmpty()) {
      int[] cur = queue.poll();
      int i = cur[0], j = cur[1];
      if (gameField[i][j] == '.' || gameField[i][j] == '*') {
        if (backField[i][j] > 0) {
          gameField[i][j] = (char) (backField[i][j] + '0');
          continue;
        }
        if (gameField[i][j] == '*') {
          marks.remove(indexesToNumber(i, j));
        }
        gameField[i][j] = '/';
        for (int k = -1; k <= 1; k++) {
          for (int l = -1; l <= 1; l++) {
            if (k == 0 && l == 0) {
              continue;
            }
            if (i + k == 0 || i + k == 10 || j + l == 0 || j + l == 10) {
              continue;
            }
            queue.add(new int[]{i + k, j + l});
          }
        }
      }
    }
    return true;
  }

  private boolean markCell(int c, int r) {
    if (gameField[r][c] == '*') {
      gameField[r][c] = '.';
      marks.remove((r - 1) * 9 + c);
    } else {
      gameField[r][c] = '*';
      marks.add((r - 1) * 9 + c);
    }
    return marks.equals(mines);
  }

  private void printField() {
    // print top 2 rows
    System.out.println(" |123456789|");
    System.out.println("-|---------|");
    // print play field
    for (int i = 1; i <= 9; i++) {
      System.out.print(i + "|");
      for (int j = 1; j <= 9; j++) {
        System.out.print(gameField[i][j]);
      }
      System.out.println("|");
    }
    // print bottom row
    System.out.println("-|---------|");
  }

  private void initField(int col, int row) {
    int cellNumber = indexesToNumber(row, col);
    ArrayList<Integer> positions = new ArrayList<>();
    for (int i = 1; i <= 81; i++) {
      if (i != cellNumber) {
        positions.add(i);
      }
    }
    int cnt = this.minesNumber;
    while (cnt-- > 0) {
      int randomPosition = random.nextInt(positions.size());
      int randomCell = positions.get(randomPosition);
      positions.remove(randomPosition);
      addMine(randomCell);
    }
  }

  private void addMine(int randomCell) {
    // calculate correct indexes
    int[] indexes = numberToIndexes(randomCell);
    int i = indexes[0], j = indexes[1];

    // field[i][j] is a cell with mine
    backField[i][j] = -1;
    mines.add((i - 1) * 9 + j);

    for (int k = -1; k <= 1; k++) {
      for (int l = -1; l <= 1; l++) {
        if (k == 0 && l == 0) {
          continue;
        }
        int offsetI = i + k, offsetJ = j + l;
        backField[offsetI][offsetJ] += backField[offsetI][offsetJ] == -1 ? 0 : 1;
      }
    }
  }

  static int[] numberToIndexes(int number) {
    return new int[]{(number - 1) / 9 + 1, (number - 1) % 9 + 1};
  }

  static int indexesToNumber(int row, int col) {
    return (row - 1) * 9 + col;
  }
}
