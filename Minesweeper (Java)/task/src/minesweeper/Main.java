package minesweeper;

import java.util.Scanner;

public class Main {

  private static Scanner scanner = new Scanner(System.in);
  private static Stage3 stage3 = new Stage3();

  private static void stage3() {
    System.out.print("How many mines do you want on the field? ");
    int mines = scanner.nextInt();
    stage3.generateField(mines);
    stage3.printField();
  }

  public static void main(String[] args) {
    Stage4 stage4 = new Stage4();
    stage4.run();
  }
}
