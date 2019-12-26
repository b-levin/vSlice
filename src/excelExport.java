import java.util.*;
import java.io.*;
import java.lang.Math;

public class excelExport {

    private Scanner input;
    private LinkedList<String> initialList;
    private LinkedList<Double[]> gcodeList;
    private PrintStream output;

    public excelExport(String gcodeName, String outName) {
        try {
            this.initialList = new LinkedList<>();
            this.gcodeList = new LinkedList<>();
            this.input = new Scanner(new File(gcodeName));
            this.output = new PrintStream(new File(outName));
        } catch (FileNotFoundException e) {
            System.out.println("Error: file not found.");
        }
    }

    public void read() {
        while (input.hasNextLine()) {
            String line = input.nextLine();
            initialList.add(line);
        }
    }

    public void trim(int num) {
        for (int i = 0; i < num; i++) {
            initialList.remove(0);
        }
    }

    public void process() {
        for (int i = 0; i < initialList.size(); i++) {
            String line = initialList.pop();
            if (line.contains("G1 X") &&
                line.contains("Y") &&
                line.contains("E") &&
                !line.contains("F")) {
                String[] lineContents = line.split(" ");
                Double[] out = new Double[3];
                String xVal = lineContents[1].replace("X", "");
                double x = Double.valueOf(xVal);
                out[0] = x;
                String yVal = lineContents[2].replace("Y", "");
                double y = Double.valueOf(yVal);
                out[1] = y;
                String eVal = lineContents[3].replace("E", "");
                double e = Double.valueOf(eVal);
                out[2] = e; 
                gcodeList.add(out);
            }
        }
        initialList.clear();
    }

    private double findDistance(double x1, double y1, double x2, double y2) {
        double distance = Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2);
        return Math.sqrt(distance);
    }

    //output formatting: distance, difference in extrusion
    public void export() {
        for (int i = 0; i < gcodeList.size(); i+=2) {
            Double[] line1 = gcodeList.pop();
            Double[] line2 = gcodeList.pop();
            double distance = this.findDistance(line1[0], line1[1], line2[0], line2[1]);
            double diff = line2[2] - line1[2];
            output.printf("%f,%f\n", distance, diff);
        }
        gcodeList.clear();
        input.close();
        output.close();
    }
}