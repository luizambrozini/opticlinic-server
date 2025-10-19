package tec.br.opticlinic.api.application.util;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class DocumentUtilService {

    private static final Pattern ONLY_DIGITS = Pattern.compile("\\D+");

    // CNPJ
    private static final int[] CNPJ_W1 = {5,4,3,2,9,8,7,6,5,4,3,2};
    private static final int[] CNPJ_W2 = {6,5,4,3,2,9,8,7,6,5,4,3,2};

    // CPF
    private static final int[] CPF_W1  = {10,9,8,7,6,5,4,3,2};
    private static final int[] CPF_W2  = {11,10,9,8,7,6,5,4,3,2};

    /** Valida um CNPJ (com ou sem máscara). */
    public boolean verifyCnpj(String cnpj) {
        if (cnpj == null) return false;
        String digits = ONLY_DIGITS.matcher(cnpj).replaceAll("");
        if (digits.length() != 14) return false;
        if (allSameDigits(digits)) return false;

        String base = digits.substring(0, 12);
        int dv1 = calcDigit(base, CNPJ_W1);
        int dv2 = calcDigit(base + dv1, CNPJ_W2);

        return dv1 == charToInt(digits.charAt(12)) &&
                dv2 == charToInt(digits.charAt(13));
    }

    /** Valida um CPF (com ou sem máscara). */
    public boolean verifyCpf(String cpf) {
        if (cpf == null) return false;
        String digits = ONLY_DIGITS.matcher(cpf).replaceAll("");
        if (digits.length() != 11) return false;
        if (allSameDigits(digits)) return false;

        String base = digits.substring(0, 9);
        int dv1 = calcDigit(base, CPF_W1);
        int dv2 = calcDigit(base + dv1, CPF_W2);

        return dv1 == charToInt(digits.charAt(9)) &&
                dv2 == charToInt(digits.charAt(10));
    }

    // ===== helpers =====

    private boolean allSameDigits(String s) {
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) != s.charAt(0)) return false;
        }
        return true;
    }

    /** Regra comum (mod 11): dv = (resto < 2 ? 0 : 11 - resto). */
    private int calcDigit(String numbers, int[] weights) {
        int sum = 0;
        for (int i = 0; i < weights.length; i++) {
            sum += charToInt(numbers.charAt(i)) * weights[i];
        }
        int rest = sum % 11;
        return (rest < 2) ? 0 : (11 - rest);
    }

    private int charToInt(char c) {
        return c - '0';
    }
}
