package ru.fedorov.application;

import ru.fedorov.application.work.Start;


public class Main {
    private final Start START = new Start();

    public static void main(String[] args) {
        Main main = new Main();
        main.go();
    }

    private void go() {
        START.start();
    }
}
