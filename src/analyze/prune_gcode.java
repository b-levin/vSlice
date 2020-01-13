package analyze;

import java.util.*;
import java.io.*;

public class prune_gcode {
    
    private Scanner input;
    private PrintStream output;
    private LinkedList<String> gcodeList;
    private LinkedList<Double[]> coordList;

    public prune_gcode(String inputName, String outputName) {
        try {
            this.input = new Scanner(new File(inputName));
            this.output = new PrintStream(new File(outputName));
            this.gcodeList = new LinkedList<>();
            this.coordList = new LinkedList<>();
        } catch (FileNotFoundException e) {
            System.out.printf("Error, %s not found.\n", inputName);
        }
    }

    public void read() {
        //Read the .gcode file, added all G1 commands to the gcodeList
        boolean add = false;
        while (this.input.hasNextLine()) {
            String line = this.input.nextLine();
            if (add && line.contains("G1")) {
                this.gcodeList.add(line);
            }
            if (line.contains(";MESH")) {
                add = true;
            }
        }
    }

    /*
    Cases:
        G1 F X Y E
        G1 X Y E
    */

    public void process() {
        //Exports the analyzed gcode as a .csv file
        for (int i = 0; i < this.gcodeList.size(); i++) {
            String line = this.gcodeList.pop();
            String[] lineArr = line.split(" ");
            if (lineArr.length == 4) {
                len4Process(lineArr);
            } else if (lineArr.length == 5){
                len5Process(lineArr);
            }
        }
        this.gcodeList.clear();
    }    

    private void len4Process(String[] arr) {
        //processes the len4 G1 command and adds it to the coordList
        //format: G1 X Y E
        Double[] out = new Double[3];
        out[0] = Double.valueOf(arr[1].replace("X", ""));
        out[1] = Double.valueOf(arr[2].replace("Y", ""));
        out[2] = Double.valueOf(arr[3].replace("E", ""));
        this.coordList.add(out);
    }

    private void len5Process(String[] arr) {
        //processes the len5 G1 command and adds it to the coordList
        //format: G1 F X Y E
        Double[] out = new Double[3];
        out[0] = Double.valueOf(arr[2].replace("X", ""));
        out[1] = Double.valueOf(arr[3].replace("Y", ""));
        out[2] = Double.valueOf(arr[4].replace("E", ""));
        this.coordList.add(out);
    }

    private double findDistance(double x1, double y1, double x2, double y2) {
        double distance = Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2);
        return Math.sqrt(distance);
    }

    public void export() {
        for (int i = 0; i < this.coordList.size(); i+=2) {
            Double[] line1 = this.coordList.pop();
            Double[] line2 = this.coordList.pop();
            double distance = this.findDistance(line1[0], line1[1], line2[0], line2[1]);
            double diff = line2[2] - line1[2];
            this.output.printf("%f,%f\n", distance, diff);
        }
        this.coordList.clear();
        this.input.close();
        this.output.close();
    }
}