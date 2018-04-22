package main.java.com.opnenlp.translator;

import main.java.com.opnenlp.translator.util.ExcelReader;
import main.java.com.opnenlp.translator.util.SubSequenceUtil;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;
import org.apache.commons.text.WordUtils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

public class Application {

    public static void main(String args[]) throws Exception {


        InputStream inputStreamTokenizer = new
                FileInputStream("C:\\Users\\Smriti\\IdeaProjects\\translator-app\\src\\main\\resources\\en-token.bin");
        TokenizerModel tokenModel = new TokenizerModel(inputStreamTokenizer);


        InputStream inputStreamNameFinder = new
                FileInputStream("C:\\Users\\Smriti\\IdeaProjects\\translator-app\\src\\main\\resources\\en-ner-organization.bin");
        TokenNameFinderModel model = new TokenNameFinderModel(inputStreamNameFinder);

        NameFinderME nameFinder = new NameFinderME(model);

        Map<String, String> translatorTable = ExcelReader.descriptionMerchantMapping();
        TokenizerME tokenizer = new TokenizerME(tokenModel);

        Set<String> companies = ExcelReader.companyList();

        for (String d : translatorTable.keySet()) {

            nameFinder.clearAdaptiveData();
            d = d.toLowerCase();
            d = WordUtils.capitalize(d);
            String tokens[] = tokenizer.tokenize(d);
            Span[] nameSpans = nameFinder.find(tokens);
            double[] probs = nameFinder.probs();

            for(double p:probs){
                System.out.println(p);
            }

            String org = null;
            if (nameSpans.length > 0) {
                inner:
                for (Span s : nameSpans) {
                    org = tokens[s.getStart()];
                    break inner;
                }
            } else {
                int index = getIndexOfLargest(probs);
                org = tokens[index];
            }

            if (translatorTable.get(d) == null) {
                org = SubSequenceUtil.companyLookup(org);
                translatorTable.put(d, org);
            }

        }

        for (String d : translatorTable.keySet()) {
            System.out.println(translatorTable.get(d));
        }

    }

    public static int getIndexOfLargest(double[] array) {
        if (array == null || array.length == 0) return -1;

        int largest = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[largest]) largest = i;
        }

        return largest;
    }
}
