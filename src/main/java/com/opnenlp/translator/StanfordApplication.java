package main.java.com.opnenlp.translator;

import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.ie.util.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.semgraph.*;
import edu.stanford.nlp.trees.*;
import main.java.com.opnenlp.translator.util.ExcelReader;

import java.util.*;

public class StanfordApplication {

    public static void main(String[] args) {

        Properties props = new Properties();

        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,coref");

        props.setProperty("coref.algorithm", "neural");

        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        Map<String, String> translatorTable = ExcelReader.descriptionMerchantMapping();

        for (String text : translatorTable.keySet()) {

            CoreDocument document = new CoreDocument(text);

            pipeline.annotate(document);



            CoreSentence sentence = document.sentences().get(1);


            List<String> nerTags = sentence.nerTags();
            System.out.println("Example: ner tags");
            System.out.println(nerTags);
            System.out.println();

        }


    }
}
