package src.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import src.algorithm.graph.Finite;
import src.controller.Controller;

public class FiniteState implements Algorithm {
    private Finite finite;
    private Controller ctrl;
    private String StartPoint;
    private String[] input;
    private ArrayList<Integer> resultPath;
    private boolean isAccept = false;

    public FiniteState(Finite fn) {
        this.finite = fn;
    }

    @Override
    public boolean input(Controller c) {
        this.ctrl = c;
        while (true) {
            System.out.print("Enter the start point: ");
            this.StartPoint = c.getScanner().nextLine();
            this.ctrl.getTerminal().clearScreen();
            if (this.finite.getVertexPos().contains(this.StartPoint))
                break;
            System.out.println(this.ctrl.getTerminal().color("red", "Invalid input, try again!"));
        }
        this.ctrl.getTerminal().clearScreen();
        System.out.print("Enter the input: ");
        this.input = c.getScanner().nextLine().split("\s*,\s*");
        return true;
    }

    @Override
    public boolean run() {
        this.resultPath = this.run(this.StartPoint, this.input);
        this.isAccept = (this.resultPath.get(this.resultPath.size() - 1) != -1
                && this.finite.isAccept(this.resultPath.get(this.resultPath.size() - 1)));
        return true;
    }

    @Override
    public void result() {
        System.out.println("Result of Finite State Automata\n");
        System.out.println("Path: "
                + this.resultPath.stream().map(x -> {
                    try {
                        String tmp = this.finite.getVertexPos().get(x);
                        return tmp;
                    } catch (IndexOutOfBoundsException e) {
                        return "!Invalid";
                    }
                })
                        .collect(Collectors.joining(" -> ")));

        System.out.println(this.isAccept ? "Accepted" : "Rejected");
    }

    /**
     * @param input - input string format "i1, i2, i3, ..."
     */
    public ArrayList<Integer> run(String start, String[] input) {
        String[][] am = new String[this.finite.getVertexLength()][];

        for (int i = 0; i < this.finite.getAdjacencyMatrix().length; i++)
            am[i] = this.finite.getAdjacencyMatrix()[i].clone();

        ArrayList<Integer> resultPath = new ArrayList<>();

        for (int i = 0; i < am.length; i++) {
            for (int j = 0; j < am[i].length; j++) {
                if (am[i][j] != null)
                    am[i][j] = this.finite.getEdge(am[i][j]).getData();
            }
        }

        int idx = this.finite.getVertexPos().indexOf(start);
        resultPath.add(idx);

        for (int i = 0; i < input.length; i++) {
            String x = input[i];
            idx = Arrays.asList(am[idx]).indexOf(x.trim());
            resultPath.add(idx);
            try {
                System.out.println("Running ==> Finite State" + "\n");
                System.out.println("Round ==> " + (i + 1));
                System.out.println("Input: " + x + "\n");
                System.out.println(
                        "Current State: " + (idx != -1 ? this.finite.getVertexPos().get(idx) : "!Invalid") + "\n");
                System.out.println("Finite sequence history: " + resultPath.stream().map(y -> {
                    try {
                        String tmp = this.finite.getVertexPos().get(y);
                        return tmp;
                    } catch (IndexOutOfBoundsException e) {
                        return "!Invalid";
                    }
                }).collect(Collectors.joining(" -> ")) + "\n");

                Thread.sleep(900);
                this.ctrl.getTerminal().clearScreen();
            } catch (Exception e) {
                System.out.println("Error");
            }
            if (idx == -1)
                break;
        }

        // return idx != -1 && this.finite.isAccept(idx);
        return resultPath;
    }
}
