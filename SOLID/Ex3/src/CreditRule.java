public class CreditRule implements EligibilityRule {
    @Override
    public boolean isEligible(StudentProfile s)
    {
        return s.earnedCredits > 20;
    }

    @Override
    public String getReason() {
        return "Credits below 20";
    }
}