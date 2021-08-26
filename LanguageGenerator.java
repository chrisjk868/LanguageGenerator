import java.util.*;
import java.util.function.*;
// Christopher Ku
// Section: AG with Jiamae Wang
// Assessment 5: LanguageGenerator
//
// This LanguageGenerator class represents a random language generator
// that follows the theory of generative grammar to generate possible
// sentences and grammatical expressions with the creation of a valid
// system of rules, and utilizing this insert different words or special
// characters known as terminals to form coherent sentences.
public class LanguageGenerator {
    private Map<String, String[]> terminalRules;
    private Random rand;

    /**
     * Constructs a new LanguageGenerator instance by taking in a Grammar
     * parameter datatype that contains production rules for different grammar
     * language categories.
     *
     * @param grammar   a Grammar datatype with the production rules for
     *                  different language categories.
     */
    public LanguageGenerator(Grammar grammar) {
        this.rand = new Random();
        this.terminalRules = grammar.productionRules.get();
    }

    /**
     * Constructs a new LanguageGenerator instance by taking in a Grammar
     * parameter datatype represting the production rules of
     * different grammar language categories and also taking in a Random class
     * parameter to enable randomness for the LanguageGenerator class
     *
     * @param grammar   a Grammar datatype with the production rules for
     *                  different language categories.
     *
     * @param random   a Random class that enables randomness for the Language
     *                 Generator class
     */
    public LanguageGenerator(Grammar grammar, Random random) {
        this.rand = random;
        this.terminalRules = grammar.productionRules.get();
    }

    /**
     * pre: Takes in a String parameter that represents either a terminal or
     *      non-terminal. This String can be null.
     *
     * post: Returns a String that represents a randomly generated sentence that
     *       follows the production rules of terminals.
     *
     * @param target   A String that can either be a terminal or non-terminal.
     * @return   returns a randomly generated sentence that follows the production
     *           rules of terminals.
     */
    public String generate(String target) {
        if (!terminalRules.containsKey(target)) {
            return target;
        } else {
            String[] productionRules = terminalRules.get(target);
            int select = rand.nextInt(productionRules.length);
            String selectedValue = productionRules[select];
            String change = "";
            String[] processSelected = selectedValue.split("\\s+");
            for (int i = 0; i < processSelected.length; i++) {
                change += " " + generate(processSelected[i]);
            }
            return change.trim();
        }
    }

    public enum Grammar {
        FORMULA(() -> {
            Map<String, String[]> result = new TreeMap<>();
            result.put("E", "T, E OP T".split(", "));
            result.put("T", "x, y, 1, 2, 3, ( E ), F1 ( E ), - T, F2 ( E . E )".split(", "));
            result.put("OP", "+, -, *, %, /".split(", "));
            result.put("F1", "sin, cos, tan, sqrt, abs".split(", "));
            result.put("F2", "max, min, pow".split(", "));
            return result;
        }),
        MUSIC(() -> {
            Map<String, String[]> result = new TreeMap<>();
            result.put("measure", "pitch-w, half half".split(", "));
            result.put("half", "pitch-h, quarter quarter".split(", "));
            result.put("quarter", "pitch-q, pitch pitch".split(", "));
            result.put("pitch", "C, D#, F, F#, G, A#, C6".split(", "));
            result.put("chordmeasure", "chord-w, halfchord halfchord".split(", "));
            result.put("halfchord", "chord-h, chord-q chord-q".split(", "));
            result.put("chord", "Cmin, Cmin7, Fdom7, Gdom7".split(", "));
            result.put("bassdrum", "O..o, O..., O..o, OO..".split(", "));
            result.put("snare", "..S., S..s, .S.S".split(", "));
            result.put("crash", "...*, *...".split(", "));
            result.put("claps", "x..x, xx..x".split(", "));
            return result;
        }),
        ENGLISH(() -> {
            Map<String, String[]> result = new TreeMap<>();
            result.put("SENTENCE", "NOUNP VERBP".split(", "));
            result.put("NOUNP", "DET ADJS NOUN, PROPNOUN".split(", "));
            result.put("PROPNOUN", "Seattle, Matisse, Kim, Zela, Nia, Remi, Alonzo".split(", "));
            result.put("ADJS", "ADJ, ADJ ADJS".split(", "));
            result.put("ADJ", "fluffy, bright, colorful, beautiful, purple, calming".split(", "));
            result.put("DET", "the, a".split(", "));
            result.put("NOUN", "cat, dog, bagel, apple, person, school, car, train".split(", "));
            result.put("VERBP", "TRANSVERB NOUNP, INTRANSVERB".split(", "));
            result.put("TRANSVERB", "ate, followed, drove, smacked, embraced, helped".split(", "));
            result.put("INTRANSVERB", "shined, smiled, laughed, sneezed, snorted".split(", "));
            return result;
        });

        public final Supplier<Map<String, String[]>> productionRules;

        private Grammar(Supplier<Map<String, String[]>> productionRules) {
            this.productionRules = productionRules;
        }
    }

    public static void main(String[] args) {
        LanguageGenerator generator = new LanguageGenerator(Grammar.FORMULA);
        System.out.println(generator.generate("E"));
    }
}
