package me.antonhorvath;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        List<Character> test = getCharacters();
        if (test.isEmpty()) {
            return;
        }
        int searchCode = getSearchCode();
        StateTree tree = new StateTree(new Node(test, null));
        switch (searchCode) {
            case 1:
                outputInformation("DFS Search", tree.dfsSearch());
                break;
            case 2:
                outputInformation("Iterative Deepening Search", tree.iterativeDeepeningSearch());
                break;
            case 3:
                outputInformation("A* Search - Heuristic #1", tree.aStarSearch(true));
                break;
            case 4:
                outputInformation("A* Search - Heuristic #2", tree.aStarSearch(false));
                break;
        }
    }

    private static void outputInformation(String type, List<Node> path) {
        System.out.println("\n"+type+"\n------------------------------------------");
        System.out.println("Input\n");
        path.get(type.contains("Heuristic") ? 0 : path.size()-1).printState();
        System.out.println("\nOutput\n");
        if (path.size() == 1) {
            System.out.println("Goal State Couldn't Be Reached");
            return;
        }
        if (type.contains("Heuristic")) {
            for (int i = 0; i < path.size(); i++) {
                path.get(i).printState();
                System.out.println();
            }
        } else {
            for (int i = path.size()-1; i > -1; i--) {
                path.get(i).printState();
                System.out.println();
            }
        }
    }

    private static ArrayList<Character> getCharacters() {
        Scanner scan = new Scanner(System.in);
        ArrayList<Character> characters = new ArrayList<>();

        System.out.print("Enter 8-square details with spaces between characters: ");
        String name = scan.nextLine();
        if (name.length() != 17) {
            System.out.println("Invalid input");
        }
        else {
            for (int index = 0; index < name.length(); index+=2) {
                characters.add(name.charAt(index));
            }
        }
        return characters;
    }

    private static int getSearchCode() {
        Scanner scan = new Scanner(System.in);
        int value = -1;
        System.out.println("Enter the code of the search algorithm (1, 2, 3, 4):");
        System.out.println("   1. DFS Search");
        System.out.println("   2. Iterative Deepening Search");
        System.out.println("   3. A* Heuristic Search #1");
        System.out.println("   4. A* Heuristic Search #2");
        while (value < 1 || value > 4) {
            String input = scan.nextLine();
            try {
                value = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid price code");
            }
            if (value < 1 || value > 4) {
                System.out.println("Please enter a value between 1-4 inclusive");
            }
        }
        return value;
    }

}
