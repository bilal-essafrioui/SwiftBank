package util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class BirthDateValidator {

    public static LocalDate validate(String birthDateStr) {
        try {
            return LocalDate.parse(birthDateStr);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid birth date format. Use yyyy-mm-dd.");
        }
    }
}
