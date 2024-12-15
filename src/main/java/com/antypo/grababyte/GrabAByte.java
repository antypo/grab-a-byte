package com.antypo.grababyte;

import com.antypo.grababyte.lunch.Cuisine;
import com.antypo.grababyte.lunch.Lunch;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class GrabAByte {
  private static final Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    Lunch pierogi = new Lunch("Pierogi", Cuisine.POLISH, 10.0);
    Set<Lunch> menu = new HashSet<>();
    menu.add(pierogi);
    System.out.println("Hello, what would you like to order?");
    menu.forEach(item -> System.out.print(item.name() + ":" + item.cuisine()));
    int choice = Integer.parseInt(scanner.nextLine().trim());
    System.out.println(choice);
  }
}
