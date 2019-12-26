public class excelExportDriver {

    public static String[] genFilesNames(String inPath, String outPath) {
        String[] out = new String[12];
        int layerHeight = 100;
        for (int i = 0; i < out.length; i++) {
            String inName = inPath + layerHeight + "_benchy.gcode";
            String outName = outPath + layerHeight + "_benchy.csv";
            out[i] = inName;
            i++;
            out[i] = outName;
            layerHeight += 50;
        }
        return out;
    }


    public static void main(String[] args) {
        String inPath = "/mnt/c/Users/windr/Documents/vSlice/benchy_gcode/";
        String outPath = "/mnt/c/Users/windr/Documents/vSlice/csv/";
        String[] fileNames = genFilesNames(inPath, outPath);
        for (int i = 0; i < fileNames.length; i += 2) {
            excelExport gcode = new excelExport(fileNames[i], fileNames[i+1]);
            gcode.read();
            gcode.trim(40);
            gcode.process();
            gcode.export();
        }
    }
}