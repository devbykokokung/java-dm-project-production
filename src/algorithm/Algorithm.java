package src.algorithm;

import src.controller.Controller;

public interface Algorithm {
    public boolean input(Controller c);

    public boolean run();

    public void result();
}
