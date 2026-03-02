import java.nio.charset.StandardCharsets;

public class CsvExporter extends Exporter {
    @Override
    public ExportResult export(ExportRequest req) {
        // LSP issue: changes meaning by lossy conversion
        String escapedBody = "\"" + req.body.replace("\"", "\"\"") + "\"";
        String csv = "title,body\n" + req.title + "," + escapedBody + "\n";
        return new ExportResult("text/csv", csv.getBytes(StandardCharsets.UTF_8));
    }
}
