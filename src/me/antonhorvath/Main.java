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
        StateTree tree = new StateTree(new Node(test));
        outputInformation("DFS Search", tree.dfsSearch());
        outputInformation("Iterative Deepening Search", tree.iterativeDeepeningSearch());
        outputInformation("A* Search - Heuristic #1", tree.aStarSearch(true));
        outputInformation("A* Search - Heuristic #2", tree.aStarSearch(false));
    }

    private static void outputInformation(String type, List<Node> path) {
        System.out.println(type+"\n------------------------------------------");
        System.out.println("Input\n");
        path.get(path.size()-1).printState();
        System.out.println("\nOutput\n");
        if (path.size() == 1) {
            System.out.println("Goal State Couldn't Be Reached");
            return;
        }
        for (int i = path.size()-1; i > -1; i--) {
            path.get(i).printState();
            System.out.println();
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

}
