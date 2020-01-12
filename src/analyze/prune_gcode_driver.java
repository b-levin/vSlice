package analyze;

public class prune_gcode_driver {

    public static String[] genFilesNames(String inPath, String outPath) {
        //Generates an array of the filenames for input/output
        //form: in_1, out_1, in_2, out_2....
        String[] out = new String[12];
        int layerHeight = 100;
        for (int i = 0; i < out.length; i++) {
            String inName = inPath + "cube_" + layerHeight + ".gcode";
            String outName = outPath + "cube_" + layerHeight + ".csv";
            out[i] = inName;
            i++;
            out[i] = outName;
            layerHeight += 50;
        }
        return out;
    }

    public static void runPrune(String inPath, String outPath) {
        //Runs the prune_gcode class for 100 to 350 um files
        String[] fileNames = genFilesNames(inPath, outPath);
        for (int i = 0; i < fileNames.length; i += 2) {
            prune_gcode gcode = new prune_gcode(fileNames[i], fileNames[i+1]);
            gcode.read();
            gcode.process();
            gcode.export();
        }
    }

    public static void main(String args[]) {
        String inPath = "/mnt/c/Users/windr/Documents/vSlice/cube_gcode/";
        String outPath = "/mnt/c/Users/windr/Documents/vSlice/csv/";
        runPrune(inPath, outPath);
    }
}