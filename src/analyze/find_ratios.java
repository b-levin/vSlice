package analyze;

import java.util.*;
import java.io.*;

public class find_ratios {

    private Scanner input1;
    private Scanner input2;
    private HashMap<Double, Double> coordMap1;
    private HashMap<Double, Double> coordMap2;

    public find_ratios(String src1, String src2, String outName) {
        //src1 must be a higher layer height than src2
        try {
            this.input1 = new Scanner(new File(src1));
            this.input2 = new Scanner(new File(src2));
            this.coordMap1 = new HashMap<>();
            this.coordMap2 = new HashMap<>();
        } catch (FileNotFoundException e) {
            System.out.println("Error, file not found.");
        }
    }

    private void popMap1() {
        while (this.input1.hasNextLine()) {
            String line = this.input1.nextLine();
            String[] lineArr = line.split(",");
            double dist = Double.valueOf(lineArr[0]);
            double extrusion = Double.valueOf(lineArr[0]);
            this.coordMap1.put(dist, extrusion);
        }
        this.input1.close();
    }

    private void popMap2() {
        while (this.input2.hasNextLine()) {
            String line = this.input2.nextLine();
            String[] lineArr = line.split(",");
            double dist = Double.valueOf(lineArr[0]);
            double extrusion = Double.valueOf(lineArr[1]);
            this.coordMap2.put(dist, extrusion);
        }
        this.input2.close();
    }

    public void populateMaps() {
        this.popMap1();
        this.popMap2();
    }

    public double getAvgRatio() {
        double average = 0.0;
        double size = 0.0;
        for (double dist : this.coordMap1.keySet()) {
            double ext1 = this.coordMap1.get(dist);
            double ext2 = this.coordMap2.get(dist);
            average += (ext1/ext2);
            size += 1.0;
        }
        return average/size;
    }
}