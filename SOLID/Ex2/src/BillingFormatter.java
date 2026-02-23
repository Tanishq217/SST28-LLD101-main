import java.util.List;

public class BillingFormatter {
    public String formatInvoice(String invId, List<String> itemLines, double subtotal, double taxRate, double tax, double discount, double total) {
        StringBuilder out = new StringBuilder();
        out.append("Invoice# ").append(invId).append("\n");
        for (String line : itemLines) {
            out.append(line);
        }
        out.append(String.format("Subtotal: %.2f\n", subtotal));
        out.append(String.format("Tax(%.0f%%): %.2f\n", taxRate, tax));
        out.append(String.format("Discount: -%.2f\n", discount));
        out.append(String.format("TOTAL: %.2f\n", total));
        return out.toString();
    }
}