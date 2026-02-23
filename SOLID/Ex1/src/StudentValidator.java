import java.util.*;

public class StudentValidator {
    public List<String> validate  (Map<String, String> data) {

        List<String>  errors =  new ArrayList<>();
        if (data.getOrDefault ("name", "").isBlank()) errors.add("name is required");

        String email = data.getOrDefault("email", "");
        if  (email.isBlank() || !email.contains("@")) errors.add("email is invalid");

        String phone = data.getOrDefault("phone", "");
        if (phone.isBlank() ||  !phone.chars().allMatch(Character::isDigit)) errors.add("phone is invalid");

        String program = data.getOrDefault("program", "");

        List<String> allowed =   List.of("CSE", "AI", "SWE");

        if (!allowed.contains(program)) errors.add("program is invalid");

        return errors;
    }
}