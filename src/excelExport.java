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

    private double pythag(double x, double y) {
        double distance = x*x + y*y;
        return Math.sqrt(distance);
    }

    //output formatting: distance, total extrusion, difference in extrusion
    public void export() {
        double prevE = 0.0;
        for (int i = 0; i < gcodeList.size(); i++) {
            Double[] line = gcodeList.pop();
            double distance = this.pythag(line[0], line[1]);
            double diff = line[2] - prevE;
            output.printf("%f,%f,%f\n", distance, line[2], diff);
            prevE = line[2];
        }
        gcodeList.clear();
        input.close();
        output.close();
    }
}