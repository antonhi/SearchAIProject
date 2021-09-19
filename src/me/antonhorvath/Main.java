package me.antonhorvath;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        List<Character> test = getCharacters();
        /*test.add('1');
        test.add('2');
        test.add('3');
        test.add('4');
        test.add('5');
        test.add('*');
        test.add('6');
        test.add('7');
        test.add('8');*/
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

        System.out.print("Enter the file name: ");
        String name = scan.nextLine();
        File f = new File(name);
        if (f.exists()) {
            try {
                scan = new Scanner(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return characters;
            }
            String line = scan.nextLine();
            if (line.length() != 17) {
                System.out.println("Incorrect line format");
                return characters;
            }
            for (int i = 0; i < line.length(); i+=2) {
                characters.add(line.charAt(i));
            }
        }
        else {
            System.out.println("File does not exist in current directory");
        }
        return characters;
    }

}
