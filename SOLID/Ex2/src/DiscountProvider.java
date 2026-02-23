public interface DiscountProvider {
    double calculateDiscount(String customerType, double subtotal,
                             int distinctLines);
}