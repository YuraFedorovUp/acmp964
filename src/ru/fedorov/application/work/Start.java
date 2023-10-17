package ru.fedorov.application.work;

import java.util.Scanner;

public class Start {

    private final Work work = new Work();

    public void start() {
        try (Scanner sc = new Scanner(System.in)) {
            final String quantityResponseString = sc.nextLine().trim();
            final int quantityResponse = Integer.parseInt(quantityResponseString);
            for (int i = 0; i < quantityResponse; i++) {
                final String input = sc.nextLine().trim();
                String output = work.work(input);
                if (i != quantityResponse - 1) {
                    output += "\n=====";
                }
                System.out.println(output);
                if (i == quantityResponse - 1) {
                    return;
                }
            }
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }
}