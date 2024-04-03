package src.controller;

import java.util.Scanner;

import src.algorithm.Algorithm;
import src.algorithm.Dijkstra;
import src.algorithm.FiniteState;
import src.algorithm.Kruskal;
import src.algorithm.Prim;
import src.algorithm.graph.Finite;
import src.algorithm.graph.Graph;
import src.view.Terminal;

public class Controller {
    private Terminal terminal;
    private Graph graph;
    private Finite finite;
    private Algorithm algorithm;
    private Scanner sc;

    public Controller(Terminal t) {
        this.terminal = t;
        this.initialize();
    }

    private void initialize() {
        this.terminal.printWelcomeMsg();
        this.terminal.pressAnyKeyToContinue();
        this.sc = new Scanner(System.in);
        this.setupType();
    }

    private void setupType() {
        this.terminal.clearScreen();
        sc.nextLine();
        String tmp;
        while (true) {
            System.out.print("Choose Graph or Finite (or Exit): ");
            tmp = sc.nextLine().toLowerCase();
            terminal.clearScreen();
            if (tmp.equals("graph")) {
                this.setupGraph();
                this.setupAlgo();
            } else if (tmp.equals("finite")) {
                this.setupFinite();
                this.runFinite();
            } else if (tmp.equals("exit")) {
                break;
            } else {
                System.out.println(this.terminal.color("red", "Invalid input, try again!"));
            }
        }
        this.sc.close();
        System.out.println(this.terminal.color("green", "Goodbye!"));
    }

    private void setupGraph() {
        terminal.clearScreen();
        System.out.print("Input Number of Vertex (ex: 5): ");
        int n = this.sc.nextInt();
        this.graph = new Graph();
        this.sc.nextLine();

        String tmp[];
        System.out.println("Input Vertex Name and(/or) Integer (ex: A, 1):");

        for (int i = 0; i < n; i++) {
            tmp = sc.nextLine().split("\s*,\s*");
            if (!this.graph.addVertex(tmp[0], tmp.length > 1 ? Integer.parseInt(tmp[1]) : 0)) {
                System.out.println(this.terminal.color("red", "Can not add vertex, try again!"));
                i--;
            }
        }

        terminal.clearScreen();
        while (true) {
            for (String[] row : this.graph.toAdjacencyMatrix()) {
                for (String col : row) {
                    System.out.print(col + " ");
                }
                System.out.println();
            }
            System.out.println(
                    "Is all vertex connected?: " + (this.graph.isAllConnected() ? this.terminal.color("green", "Yes")
                            : this.terminal.color("red", "No")) + "\b\n");
            System.out
                    .println("Input Edge Name, Integer, Start Vertex Point, and End Vertex Point (ex: E1, 10, A, B):");
            tmp = sc.nextLine().split("\s*,\s*");
            if (tmp.length < 4 && this.graph.isAllConnected()) {
                this.graph.mergeGraph();
                break;
            }

            terminal.clearScreen();
            if (tmp.length < 4 || Integer.parseInt(tmp[1]) <= 0 || Integer.parseInt(tmp[1]) == Integer.MAX_VALUE
                    || !this.graph.addEdge(tmp[0], Integer.parseInt(tmp[1]), tmp[2], tmp[3]))
                System.out.println(this.terminal.color("red", "Can not add edge, try again!"));
        }
    }

    private void setupAlgo() {
        terminal.clearScreen();
        String tmp;
        while (true) {
            System.out.print("\r\n* Choose Algorithm to find the answer. (Type Number)\r\n" + //
                                "    ================================\r\n" + //
                                "    |   1. Kuskal`s Algorithm      |\r\n" + //
                                "    |   2. Prim`s Algorithm        |\r\n" + //
                                "    |   3. Dijkstra`s Algorithm    |\r\n" + //
                                "    |                              |\r\n" + //
                                "    |   0. Go back                 |\r\n" + //
                                "    ================================\r\n");
            tmp = sc.nextLine().toLowerCase().trim();
            terminal.clearScreen();
            switch (tmp) {
                case "1":
                    this.algorithm = new Kruskal(this.graph);
                    this.runAlgo();
                    break;
                case "2":
                    this.algorithm = new Prim(this.graph);
                    this.runAlgo();
                    break;
                case "3":
                    this.algorithm = new Dijkstra(this.graph);
                    this.runAlgo();
                    break;
                case "0":
                    return;
                default:
                    System.out.println(this.terminal.color("red", "Invalid input, try again!"));
                    break;
            }
        }
    }

    private void runAlgo() {
        this.terminal.clearScreen();
        if (this.algorithm != null) {
            this.algorithm.input(this);
            this.terminal.clearScreen();
            if (this.algorithm.run())
                this.algorithm.result();
            else
                System.out.println(this.terminal.color("red", "Algorithm failed to run!"));
        }
        this.terminal.pressAnyKeyToContinue();
        this.terminal.clearScreen();
        this.sc.nextLine();
    }

    private void setupFinite() {
        terminal.clearScreen();
        System.out.print("Input Number of State (ex: 5): ");
        int n = this.sc.nextInt();
        this.finite = new Finite();
        this.sc.nextLine();

        String tmp[];
        System.out.println("Input State Name and(/or) Integer (ex: A, 1):");

        for (int i = 0; i < n; i++) {
            tmp = sc.nextLine().split("\s*,\s*");
            if (!this.finite.addVertex(tmp[0], tmp.length > 1 ? Integer.parseInt(tmp[1]) : 0)) {
                System.out.println(this.terminal.color("red", "Can not add vertex, try again!"));
                i--;
            }
        }

        this.terminal.clearScreen();
        while (true) {
            for (String[] row : this.finite.toAdjacencyMatrix()) {
                for (String col : row) {
                    System.out.print(col + " ");
                }
                System.out.println();
            }
            System.out.println(
                    "Is all state connected?: " + (this.finite.isAllConnected() ? this.terminal.color("green", "Yes")
                            : this.terminal.color("red", "No")) + "\b\n");
            System.out
                    .println(
                            "Input Transition function name, Input Data, Source State, and Destination State (ex: E1, H, A, B):");
            tmp = sc.nextLine().split("\s*,\s*");
            if (tmp.length < 4 && this.finite.isAllConnected()) {
                this.finite.mergeGraph();
                break;
            }

            this.terminal.clearScreen();
            if (tmp.length < 4
                    || !this.finite.addEdge(tmp[0], tmp[1], tmp[2], tmp[3]))
                System.out.println(this.terminal.color("red", "Can not add Transition function, try again!"));
        }

        this.terminal.clearScreen();
        while (true) {
            System.out.println("Input Accept State (ex: A, B, C):");
            String[] tmp2 = sc.nextLine().split("\s*,\s*");
            this.terminal.clearScreen();
            if (this.finite.setAccept(tmp2).length == tmp2.length)
                break;
            System.out.println(this.terminal.color("red", "Can not add accept state, try again!"));
        }
    }

    private void runFinite() {
        while (true) {
            this.terminal.clearScreen();
            this.algorithm = new FiniteState(this.finite);
            this.algorithm.input(this);
            this.terminal.clearScreen();
            if (this.algorithm.run())
                this.algorithm.result();
            else
                System.out.println(this.terminal.color("red", "Algorithm failed to run!"));
            this.terminal.pressAnyKeyToContinue();
            this.terminal.clearScreen();
            this.sc.nextLine();
            System.out.print("Do you want to run again? (Y/N): ");
            if (!this.sc.nextLine().toLowerCase().equals("y"))
                break;
        }
        this.terminal.clearScreen();
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public Finite getFinite() {
        return finite;
    }

    public Graph getGraph() {
        return graph;
    }

    public Scanner getScanner() {
        return sc;
    }

    public Terminal getTerminal() {
        return terminal;
    }
}
