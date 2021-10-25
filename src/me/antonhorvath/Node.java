package me.antonhorvath;

import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable {

    private List<Character> state;
    private List<Node> nextNodes;
    private Node parent;

    public Node (List<Character> state, Node parent) {
        this.state = new ArrayList<>(state);
        nextNodes = new ArrayList<>();
        this.parent = parent;
    }

    public Node getParent() { return parent; }

    public int getHeight() {
        int height = 0;
        Node p = parent;
        while (p != null) {
            height++;
            p = p.getParent();
        }
        return height;
    }

    public List<Character> getState() {
        return state;
    }

    public List<Node> getNextNodes() {
        return nextNodes;
    }

    public void addNextNode(Node node) {
        nextNodes.add(node);
    }

    public void printState() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(state.get(i*3+j) + "  ");
            }
            System.out.println();
        }
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Node) {
            if (state.equals(((Node) o).getState())) {
                return true;
            }
        }
        return false;
    }
}
