package me.antonhorvath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class StateTree {

    private Node root;

    private int height;

    public StateTree (Node root) {
        this.root = root;
        generate();
    }

    private void generate() {
        List<Node> inTree = new ArrayList<>();
        List<Node> queue = new ArrayList<>();
        int totalHeight = 0;
        int childNodeAmount = 1;
        int counter = 1;
        if (root != null) {
            queue.add(root);
            while (!queue.isEmpty() && totalHeight < 10) {
                Node target = queue.get(0);
                for (Node n : getPossibleMoves(target, inTree)) {
                    queue.add(n);
                    target.addNextNode(n);
                }
                inTree.add(target);
                queue.remove(target);
                if (counter == childNodeAmount) {
                    counter = 1;
                    totalHeight++;
                    childNodeAmount = target.getNextNodes().isEmpty() ? 0 : target.getNextNodes().size();
                }
                else {
                    counter++;
                }
            }
        }
        height = totalHeight;
    }

    private List<Node> getPossibleMoves(Node node, List<Node> inTree) {
        List<Character> state = node.getState();
        List<Node> possibleNodes = new ArrayList<>();
        int index = state.indexOf('*');
        for (int i : getAdjacent(index)) {
            Node newNode = new Node(getNewState(state, index, i));
            if (!inTree.contains(newNode)) {
                possibleNodes.add(newNode);
            }
        }
        return possibleNodes;
    }

    private int [] getAdjacent(int index) {
        return switch (index) {
            case 0 -> new int[]{1, 3};
            case 1 -> new int[]{0, 2, 4};
            case 2 -> new int[]{1, 5};
            case 3 -> new int[]{0, 4, 6};
            case 4 -> new int[]{1, 3, 5, 7};
            case 5 -> new int[]{2, 4, 8};
            case 6 -> new int[]{3, 7};
            case 7 -> new int[]{4, 6, 8};
            case 8 -> new int[]{5, 7};
            default -> new int[]{};
        };
    }

    private List<Character> getNewState(List<Character> currentState, int indexStar, int index) {
        List<Character> newState = new ArrayList<>(currentState);
        char c = newState.get(index);
        newState.set(indexStar, c);
        newState.set(index, '*');
        return newState;
    }

    public List<Node> dfsSearch() {
        Stack<Node> nodeStack = new Stack<>();
        List<Node> visited = new ArrayList<>();
        Node target = getTarget();
        nodeStack.push(root);
        while (!nodeStack.empty()) {

            Node parent = nodeStack.peek();

            if (parent.equals(target)) {
                return getPath(nodeStack);
            }

            if (parent.getNextNodes().isEmpty() || visited.contains(parent)) {
                nodeStack.pop();
            }

            for (Node n : parent.getNextNodes()) {
                nodeStack.push(n);
            }

            visited.add(parent);

        }
        List<Node> defaultNodeList = new ArrayList<>();
        defaultNodeList.add(root);
        return defaultNodeList;
    }

    public List<Node> iterativeDeepeningSearch() {
        List<Node> visited = new ArrayList<>();
        Stack<Node> nodeStack = new Stack<>();
        if (root != getTarget()) {
            for (int d = 0; d < height; d++) {
                int depth = 0;
                int counter = 0;
                List<Integer> nodesInDepth = new ArrayList<>();
                nodeStack.push(root);
                nodesInDepth.add(1);
                while (!nodeStack.empty()) {
                    Node parent = nodeStack.peek();

                    if (parent.equals(getTarget())) {
                        return getPath(nodeStack);
                    }

                    if (depth == d || parent.getNextNodes().isEmpty() || visited.contains(parent)) {
                        nodeStack.pop();
                        counter++;
                        if (counter == nodesInDepth.get(depth)) {
                            counter = 0;
                            depth--;
                        }
                    }

                    else {
                        depth++;
                        nodesInDepth.add(parent.getNextNodes().size());
                        for (Node n : parent.getNextNodes()) {
                            nodeStack.push(n);
                        }
                        visited.add(parent);
                    }
                }
                visited.clear();
            }
        }
        List<Node> defaultNodeList = new ArrayList<>();
        defaultNodeList.add(root);
        return defaultNodeList;
    }

    private List<Node> getPath(Stack<Node> stack) {
        List<Node> path = new ArrayList<>();
        int count = -1;
        while (!stack.empty()) {
            if (count != -1 && path.get(count) != null) {
                if (stack.peek().getNextNodes().contains(path.get(count))) {
                    path.add(stack.pop());
                    count++;
                }
                else {
                    stack.pop();
                }
            }
            else {
                path.add(stack.pop());
                count++;
            }
        }
        return path;
    }

    private Node getTarget() {
        List<Character> state = Arrays.asList('1', '2', '3', '4', '*', '5', '6', '7', '8');
        return new Node(state);
    }


}
