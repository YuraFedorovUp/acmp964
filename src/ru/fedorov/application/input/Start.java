package ru.fedorov.application.input;

import ru.fedorov.application.work.Work;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Start {

    private static final Work work = new Work();

    public void start() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            final String quantityResponseString = reader.readLine().trim();
            final int quantityResponse = Integer.parseInt(quantityResponseString);
            for (int i = 0; i < quantityResponse; i++) {
                final String input = reader.readLine().trim();
                String output = work.work(input);
                if (i != quantityResponse - 1) {
                    output += "\n=====";
                }
                System.out.println(output);
            }
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }
}
