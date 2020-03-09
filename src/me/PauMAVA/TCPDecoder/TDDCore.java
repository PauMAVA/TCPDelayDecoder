package me.PauMAVA.TCPDecoder;

import java.io.File;
import java.util.*;

public class TDDCore {

    private JsonReader reader;
    private int counter = 0;

    public static void main(String[] args) {
        File file = new File(args[0]);
        if (!file.exists()) {
            System.out.println("The file " + args[0] + " was not found!");
        } else {
            new TDDCore().execute(file);
        }
    }

    public void execute(File file) {
        this.reader = new JsonReader(file);
        System.out.println(this.reader.getDelayFromPackets().toString());
        extractBinaryData(this.reader.getDelayFromPackets());
    }

    private void extractBinaryData(HashMap<Integer, Double> map) {
        List<Integer> list = new ArrayList<>();
        List<Double> values = new ArrayList<>(map.values());
        for (int i = 7; i < values.size(); i +=2) {
            if (values.get(i) < 0.1D) {
                list.add(0);
            } else {
                list.add(1);
            }
        }
        System.out.print("0");
        counter = 1;
        list.stream().forEach(this::print);
    }

    private void print(int i) {
        System.out.print(i);
        if (counter == 7) {
            counter = 1;
            System.out.print(" 0");
        } else {
            counter++;
        }
    }

}