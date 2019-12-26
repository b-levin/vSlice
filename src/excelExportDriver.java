import java.nio.file.Paths;

public class excelExportDriver {

    public static void main(String[] args) {
        /*
        String cwd = Paths.get(".").toAbsolutePath().normalize().toString();
        String in = cwd + "/benchy_gode/200_benchy.gcode";
        String out = cwd + "csv/200.csv";
        System.out.println(in);
        */
        excelExport g200 = new excelExport("200_benchy.gcode", "200.csv");
        g200.read();
        g200.process();
        g200.export();
    }
}