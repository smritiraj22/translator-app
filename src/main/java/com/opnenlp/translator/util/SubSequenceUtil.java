package main.java.com.opnenlp.translator.util;

import java.util.Set;

public class SubSequenceUtil {

    private static boolean isSubSequence(String str1, String str2, int m, int n) {
        if (m == 0)
            return true;
        if (n == 0)
            return false;

        if (str1.charAt(m - 1) == str2.charAt(n - 1))
            return isSubSequence(str1, str2, m - 1, n - 1);

        return isSubSequence(str1, str2, m, n - 1);
    }

    public static String companyLookup(String company) {
        Set<String> companies = ExcelReader.companyList();

        for (String v : companies) {
            if (isSubSequence(company, v, company.length(), v.length())) {
                return v;
            }
        }
        return company;
    }

}
