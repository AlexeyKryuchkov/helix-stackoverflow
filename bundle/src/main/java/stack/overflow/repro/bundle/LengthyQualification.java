package stack.overflow.repro.bundle;

import java.util.Objects;

import static java.lang.String.format;

public class LengthyQualification {
    public static String get() {
        String qual = "";
        for(int i = 0; i < 10000; i++) {
            String id = String.format("%016d", i);
            String subQual = String.format("('1' = \"%s\")", id);
            if (Objects.equals(qual, "")) {
                qual = subQual;
            } else {
                qual += " OR " + subQual;
            }
        }
        return qual;
    }
}
