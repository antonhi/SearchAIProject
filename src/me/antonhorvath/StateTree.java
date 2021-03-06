package me.antonhorvath;
import java.util.*;

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
        int nextChildNodeAmount = 0;
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

                nextChildNodeAmount = target.getNextNodes().isEmpty() ? nextChildNodeAmount : nextChildNodeAmount+target.getNextNodes().size();

                if (counter == childNodeAmount || childNodeAmount < counter) {
                    counter = 1;
                    totalHeight++;
                    childNodeAmount = nextChildNodeAmount;
                    nextChildNodeAmount = 0;
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
            Node newNode = new Node(getNewState(state, index, i), node);
            if (!inTree.contains(newNode)) {
                possibleNodes.add(newNode);
            }
        }
        return possibleNodes;
    }

    private int [] getAdjacent(int index) {
        switch (index) {

            case 0:
                return new int[]{1, 3};
            case 1:
                return new int[]{0, 2, 4};
            case 2:
                return new int[]{1, 5};
            case 3:
                return new int[]{0, 4, 6};
            case 4:
                return new int[]{1, 3, 5, 7};
            case 5:
                return new int[]{2, 4, 8};
            case 6:
                return new int[]{3, 7};
            case 7:
                return new int[]{4, 6, 8};
            case 8:
                return new int[]{5, 7};
            default:
                return new int[]{};
        }
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

    public List<Node> aStarSearch(boolean misplaced) {
        // g(n) represents the number of nodes away from the root node
        // h(n) represents the number of misplaced tiles
        Node traverse = root;
        Stack<Node> nodeStack = new Stack<>();
        List<Node> visited = new ArrayList<>();
        HashMap<Node, Integer> values = new HashMap<>();

        while (traverse != null) {
            visited.add(traverse);
            if (traverse.equals(getTarget())) {
                nodeStack.push(traverse);
                while (traverse.getParent() != null) {
                    traverse = traverse.getParent();
                    nodeStack.push(traverse);
                }
                return getAStarPath(nodeStack);
            }

            else {
                for (Node n : traverse.getNextNodes()) {
                    int value = n.getHeight()+(misplaced ? getNumberOfMisplacedTiles(n) : getNumberOfTilesGreaterThanNext(n));
                    values.put(n, value);
                }
                Node minNode = null;
                for (Node node : values.keySet()) {
                    if (!visited.contains(node)) {
                        if (minNode == null) {
                            minNode = node;
                        } else {
                            minNode = values.get(node) < values.get(minNode) ? node : minNode;
                        }
                    }
                }
                traverse = minNode;
            }
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
                while (!nodeStack.empty() && depth >= 0) {
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

    private List<Node> getAStarPath(Stack<Node> stack) {
        System.out.println("\nNumber of states enqueued: " + (stack.size()+1));
        List<Node> path = new ArrayList<>();
        while (!stack.empty()) {
            path.add(stack.pop());
        }
        System.out.println("Number of moves: " + (path.size()-1));
        return path;
    }

    private List<Node> getPath(Stack<Node> stack) {
        System.out.println("\nNumber of states enqueued: " + (stack.size()+1));
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
        System.out.println("Number of moves: " + (path.size()-1));
        return path;
    }

    private Node getTarget() {
        List<Character> state = Arrays.asList('1', '2', '3', '4', '*', '5', '6', '7', '8');
        return new Node(state, null);
    }

    private int getNumberOfMisplacedTiles(Node node) {
        int count = 0;
        int index = 0;
        Node target = getTarget();
        for (char c : node.getState()) {
            if (c != target.getState().get(index)) {
                count++;
            }
            index++;
        }
        return count;
    }

    private int getNumberOfTilesGreaterThanNext(Node node) {
        int count = 0;
        List<Character> state = node.getState();
        for (int i = 0; i < state.size()-1; i++) {
            if (state.get(i+1) == '*' && (i+2) != state.size()) {
                if (state.get(i) > state.get(i+2)) {
                    count++;
                }
            }
            else {
                if (state.get(i) > state.get(i+1)) {
                    count++;
                }
            }
        }
        return count;
    }



}
