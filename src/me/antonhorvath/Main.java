package me.antonhorvath;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Character> test = new ArrayList<>();
        test.add('1');
        test.add('2');
        test.add('3');
        test.add('*');
        test.add('7');
        test.add('5');
        test.add('4');
        test.add('6');
        test.add('8');
        StateTree tree = new StateTree(new Node(test));
        outputInformation("DFS Search", tree.dfsSearch());
        outputInformation("Iterative Deepening Search", tree.iterativeDeepeningSearch());
        outputInformation("A* Search - Heuristic #1", tree.aStarHeuristicOne());
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

}
