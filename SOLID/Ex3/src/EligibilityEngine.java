import java.util.*;

public class EligibilityEngine {
    private final FakeEligibilityStore store;
    private final List<EligibilityRule> rules;

    public EligibilityEngine(FakeEligibilityStore store) {
        this.store = store;

        this.rules = new ArrayList<>();
        rules.add(new DisciplinaryRule());

        rules.add(new AttendanceRule());

        rules.add(new CreditRule());
    }

    public void runAndPrint(StudentProfile s) {
        ReportPrinter p = new ReportPrinter();

        EligibilityEngineResult r = evaluate(s);
        p.print(s, r);

        store.save(s.rollNo, r.status);
    }

    public EligibilityEngineResult  evaluate(StudentProfile s)
    {
        List<String>  reasons = new ArrayList<>();

        String status = "ELIGIBLE";

        for (int i = 0; i < rules.size(); i++) {
            EligibilityRule  rule = rules.get(i);
            if (!rule.isEligible(s)) {

                status =  "NOT_ELIGIBLE";

                reasons.add(rule.getReason());
                break;
            }
        }

        return new EligibilityEngineResult(status, reasons);
    }
}