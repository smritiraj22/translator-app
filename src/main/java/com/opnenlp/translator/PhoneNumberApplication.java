package main.java.com.opnenlp.translator;

import main.java.com.opnenlp.translator.util.ExcelReader;
import main.java.com.opnenlp.translator.util.TwilloLookup;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

public class PhoneNumberApplication {

    public static void main(String args[]) throws Exception {

        new TwilloLookup().initTwillo();

        InputStream inputStreamTokenizer = new
                FileInputStream("C:\\Users\\Smriti\\IdeaProjects\\translator-app\\src\\main\\resources\\en-token.bin");
        TokenizerModel tokenModel = new TokenizerModel(inputStreamTokenizer);


        InputStream inputStreamNameFinder = new
                FileInputStream("C:\\Users\\Smriti\\IdeaProjects\\translator-app\\src\\main\\resources\\de-ner-phone.bin");
        TokenNameFinderModel model = new TokenNameFinderModel(inputStreamNameFinder);

        NameFinderME nameFinder = new NameFinderME(model);

        Map<String, String> translatorTable = ExcelReader.descriptionMerchantMapping();
        TokenizerME tokenizer = new TokenizerME(tokenModel);

        for (String d : translatorTable.keySet()) {

            nameFinder.clearAdaptiveData();

            String tokens[] = tokenizer.tokenize(d);
            Span[] nameSpans = nameFinder.find(tokens);

            String org = null;
            if (nameSpans.length > 0) {
                inner:
                for (Span s : nameSpans) {
                    org = tokens[s.getStart()];
                    System.out.println(org);
                    break inner;
                }
            }

            if ((translatorTable.get(d) == null || translatorTable.get(d).equals("")) && org != null) {
                org = TwilloLookup.phoneNumberLookup(org);
                translatorTable.put(d, org);
            }

        }

        for (String d : translatorTable.keySet()) {
            System.out.println(translatorTable.get(d));
        }

    }

}
