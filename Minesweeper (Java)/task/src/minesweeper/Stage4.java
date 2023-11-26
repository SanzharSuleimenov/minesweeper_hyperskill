package minesweeper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Stage4 {

  private final Scanner scanner;
  private final int[][] field;
  private final ArrayList<Integer> minesPositions;
  private final ArrayList<Integer> marks;
  private final int mines;
  private final Random random;

  public Stage4() {
    this.scanner = new Scanner(System.in);
    System.out.print("How many mines do you want on the field? ");
    this.mines = scanner.nextInt();
    this.field = new int[11][11];
    this.minesPositions = new ArrayList<>();
    this.marks = new ArrayList<>();
    this.random = new Random();
    initField();
  }

  public void run() {
    printField();

    while (true) {
      System.out.print("Set/delete mines marks (x and y coordinates): ");
      int x = scanner.nextInt();
      int y = scanner.nextInt();
      boolean isDone = markCell(x, y);
      if (isDone) {
        System.out.println("Congratulations! You found all mines!");
        return;
      }
      printField();
    }
  }

  private boolean markCell(int x, int y) {
    // calculate correct sequence number of a cell
    // keep in mind that we start sequence from a 0
    Integer sequenceNumber = (y - 1) * 9 + x - 1;

    if (field[y][x] > 0) { // cell is a number
      System.out.println("There is a number here!");
      return false;
    } else if (field[y][x] == 0) { // empty cell
      marks.add(sequenceNumber); // add to marked cells list
      field[y][x] = -2; // mark cell
      return false;
    }

    if (field[y][x] == -1) { // cell is a mine
      marks.add(sequenceNumber); // add to marked cells list
      field[y][x] = -2; // mark cell
    } else if (field[y][x] == -2) { // it's a marked cell
      int index = minesPositions.indexOf(sequenceNumber); // identify if marked cell was a mine
      if (index == -1) {
        field[y][x] = 0; // marked cell was empty
      } else {
        field[y][x] = -1; // marked cell was a mine
      }
      marks.remove(sequenceNumber); // remove the cell from marked cells list
    }
    Collections.sort(marks); // sort marks list in ascending order
    Collections.sort(minesPositions); // sort mines positions list in ascending order
    return marks.equals(minesPositions); // two lists are equal, game over
  }

  private void addMine(int randomCell) {
    // calculate correct indexes
    int i = randomCell / 9 + 1;
    int j = randomCell % 9 + 1;

    // field[i][j] is a cell with mine
    field[i][j] = -1;

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

  private void printField() {
    // print top 2 rows
    for (int i = 0; i <= 9; i++) {
      if (i == 0) {
        System.out.print(" ");
      } else {
        System.out.print(i);
      }
      if (i == 9 || i == 0) {
        System.out.print("|");
      }
    }
    System.out.println();
    for (int i = 0; i <= 9; i++) {
      System.out.print("-");
      if (i == 0 || i == 9) {
        System.out.print("|");
      }
    }
    System.out.println();
    // print play field
    for (int i = 1; i <= 9; i++) {
      System.out.print(i + "|");
      for (int j = 1; j <= 9; j++) {
        switch (field[i][j]) {
          case 0:
            System.out.print(".");
            break;
          case -1:
            System.out.print(".");
            break;
          case -2:
            System.out.print("*");
            break;
          default:
            System.out.print(field[i][j]);
            break;
        }
      }
      System.out.println("|");
    }
    // print bottom row
    for (int i = 0; i <= 9; i++) {
      System.out.print("-");
      if (i == 0 || i == 9) {
        System.out.print("|");
      }
    }
    System.out.println();
  }

  private void initField() {
    // array of available positions to place mine
    ArrayList<Integer> positions = new ArrayList<>();
    for (int i = 0; i < 81; i++) {
      positions.add(i);
    }
    // while mines number is less than required, place a new mine on a field
    int cnt = 0;
    while (cnt < mines) {
      // find random element in a list
      int randomPosition = random.nextInt(positions.size());
      // get value of a random element
      int randomCell = positions.get(randomPosition);
      // remove random element by its position
      positions.remove(randomPosition);
      // add positions to mines list
      minesPositions.add(randomCell);
      // place mine on the filed
      addMine(randomCell);
      // increment number of placed fields
      cnt++;
    }
  }
}
