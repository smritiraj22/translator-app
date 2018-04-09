package main.java.com.opnenlp.translator.util;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import main.java.com.opnenlp.translator.beans.CompanyBean;
import main.java.com.opnenlp.translator.beans.TranslatorBean;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ExcelReader {

    public static Map<String, String> descriptionMerchantMapping() {
        Map<String, String> descriptionMerchantMapping = new ConcurrentHashMap<String, String>();
        String CSV_FILE_PATH = "C:\\Users\\Smriti\\IdeaProjects\\translator-app\\src\\main\\resources\\transaction_data.csv";
        try (
                Reader reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH));
        ) {
            CsvToBean<TranslatorBean> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(TranslatorBean.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            Iterator<TranslatorBean> translatorBeanIterator = csvToBean.iterator();

            while (translatorBeanIterator.hasNext()) {
                TranslatorBean translatorBean = translatorBeanIterator.next();
                //System.out.println(translatorBean.getDESCRIPTION() + translatorBean.getMERCHANT_NAME());
                descriptionMerchantMapping.put(translatorBean.getDESCRIPTION(), translatorBean.getMERCHANT_NAME());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return descriptionMerchantMapping;
    }

    public static Set<String> companyList() {
        Set<String> companyList = new HashSet<String>();
        String CSV_FILE_PATH = "C:\\Users\\Smriti\\IdeaProjects\\translator-app\\src\\main\\resources\\us_companies.csv";

        try (
                Reader reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH));
        ) {
            CsvToBean<CompanyBean> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(CompanyBean.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            Iterator<CompanyBean> companyIterator = csvToBean.iterator();

            while (companyIterator.hasNext()) {
                CompanyBean companyBean = companyIterator.next();
                companyList.add(companyBean.getCompany_name());
                // System.out.print(companyBean.getCompany_name());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return companyList;

    }
}
