import src.view.Terminal;
import src.controller.Controller;

/**
 * Main
 */
public class Main {
    static String welcomeMsg = "\r\n" + //
            "                 __        .-.\r\n" + //
            "             .-\"` .`'.    /\\\\|\r\n" + //
            "     _(\\-/)_\" ,  .   ,\\  /\\\\\\/  M e o w G r a p h\r\n" + //
            "    {(#b^d#)} .   ./,  |/\\\\\\/\r\n" + //
            "    `-.(Y).-`  ,  |  , |\\.-`\r\n" + //
            "         /~/,_/~~~\\,__.-`\r\n" + //
            "        ////~    // ~\\\\\r\n" + //
            "      ==`==`   ==`   ==`\r\n" + //
            "\r\n" + //
            "Welcome to MeowGraph!\r\n" + //
            "\r\n" + //
            "* This program is designed to help you solve graph problems in Discrete Math.\r\n" + //
            "    ================================\r\n" + //
            "    |   ○ Minimum Spanning Tree    |\r\n" + //
            "    |     - Kuskal`s Algorithm     |\r\n" + //
            "    |     - Prim`s Algorithm       |\r\n" + //
            "    |     - Dijkstra`s Algorithm   |\r\n" + //
            "    |   ○ Fintite State Automata   |\r\n" + //
            "    ================================\r\n" + //
            "\r\n" + //
            "* You can just input graph format bellow ";

    public static void main(String[] args) {
        Terminal terminal = new Terminal(welcomeMsg);
        new Controller(terminal);
    }
}