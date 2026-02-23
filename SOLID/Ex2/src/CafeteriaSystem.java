import java.util.*;

public class CafeteriaSystem {

    private final Map<String, MenuItem> menu = new LinkedHashMap<>();

    private final InvoiceRepository repository = new FileStore();

    private final TaxProvider taxProvider = new CafeteriaTaxRules();

    private final DiscountProvider discountProvider = new CafeteriaDiscountRules();
    private final BillingFormatter formatter = new BillingFormatter();


    private int invoiceSeq = 1000;

    public void addToMenu(MenuItem i) { menu.put(i.id, i); }

    public void checkout(String customerType, List<OrderLine> lines) {
        String invId = "INV-" + (++invoiceSeq);

        List<String> itemLines = new ArrayList<>();
        double subtotal = 0.0;

        for (OrderLine l : lines) {
            MenuItem item = menu.get(l.itemId);
            double lineTotal = item.price * l.qty;

            subtotal += lineTotal;
            itemLines.add(String.format("- %s x%d = %.2f\n", item.name, l.qty, lineTotal));
        }

        double taxRate = taxProvider.getTaxRate(customerType);
        double tax = subtotal * (taxRate / 100.0);

        double discount = discountProvider.calculateDiscount(customerType, subtotal, lines.size());
        double total = subtotal + tax - discount;

        String printable = formatter.formatInvoice(invId, itemLines, subtotal, taxRate, tax, discount, total);

        System.out.print(printable);
        repository.save(invId, printable);
        System.out.println("Saved invoice: " + invId + " (lines=" + repository.countLines(invId) + ")");
    }
}