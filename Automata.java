import java.util.ArrayList;

public class Automata {
    // #region Properties

    /** The text to evaluate */
    private String text;
    /** The text splitted character by character */
    private String[] splittedText;
    /** List of all the words of the automata */
    private ArrayList<String> wordArray = new ArrayList<>();
    /** The list of all reserved words */
    private ArrayList<String> reservedWords = new ArrayList<>();
    /** The list of all symbols considered "whitespace" */
    private ArrayList<String> whitespaceChars = new ArrayList<>();
    /** All the states that are being computed */
    private ArrayList<Integer> states = new ArrayList<>();
    // #endregion

    // #region Counters

    /**
     * Any of the following keywords:
     * <ul>
     * <li><code>if</code></li>
     * <li><code>main</code></li>
     * <li><code>else</code></li>
     * <li><code>switch</code></li>
     * <li><code>case</code></li>
     * <li><code>default</code></li>
     * <li><code>for</code></li>
     * <li><code>do</code></li>
     * <li><code>while</code></li>
     * <li><code>break</code></li>
     * <li><code>int</code></li>
     * <li><code>String</code></li>
     * <li><code>double</code></li>
     * <li><code>char</code></li>
     * <li><code>print</code></li>
     * </ul>
     */
    private int reservedKeywords = 0;
    /**
     * Any word not reserved, with no spaces, no special characters except
     * underscore
     */
    private int identifiers = 0;
    /**
     * Any of the following symbols:
     * <ul>
     * <li><code>&#x3C;</code></li>
     * <li><code>&#x3C;=</code></li>
     * <li><code>&#x3E;</code></li>
     * <li><code>&#x3E;=</code></li>
     * <li><code>==</code></li>
     * <li><code>!=</code></li>
     * </ul>
     */
    private int relationalOperators = 0;
    /**
     * Any of the following symbols:
     * <ul>
     * <li><code>&&</code></li>
     * <li><code>||</code></li>
     * <li><code>!</code></li>
     * </ul>
     */
    private int logicalOperators = 0;
    /**
     * Any of the following symbols:
     * <ul>
     * <li><code> + </code></li>
     * <li><code> - </code></li>
     * <li><code> * </code></li>
     * <li><code> / </code></li>
     * <li><code> % </code></li>
     * </ul>
     */
    private int arithmeticalOperators = 0;
    /**
     * The following symbol: <code>++</code>
     */
    private int increments = 0;
    /**
     * The following symbol: <code>--</code>
     */
    private int decrements = 0;
    /**
     * The following symbol: <code>=</code>
     */
    private int assignations = 0;
    /**
     * All valid integer numbers (Positive or negative)
     */
    private int integerNumbers = 0;
    /**
     * All valid decimal numbers (Positive or negative)
     */
    private int decimalNumbers = 0;
    /**
     * Any character contained between <code>"</code> and <code>"</code>
     */
    private int strings = 0;
    /**
     * Any character contained between <code>/*</code> and <code>&#x2A;&#x2F;</code>
     */
    private int comments = 0;
    /**
     * Everything that start with <code>//</code>
     */
    private int inlineComments = 0;
    /**
     * Any of the following symbols:
     * <ul>
     * <li><code> ( </code></li>
     * <li><code> ) </code></li>
     * </ul>
     */
    private int parenthesis = 0;
    /**
     * Any of the following symbols:
     * <ul>
     * <li><code> { </code></li>
     * <li><code> } </code></li>
     * </ul>
     */
    private int curlyBraces = 0;
    /**
     * Everything else that cannot be evaluated
     */
    private int errors = 0;
    // #endregion

    // #region Public methods

    /**
     * Initialize a new Automata with the given text
     */
    public Automata(String text) {
        // Initialize all reserved words.

        this.reservedWords.add("if");
        this.reservedWords.add("else");
        this.reservedWords.add("switch");
        this.reservedWords.add("case");
        this.reservedWords.add("default");
        this.reservedWords.add("for");
        this.reservedWords.add("while");
        this.reservedWords.add("break");
        this.reservedWords.add("int");
        this.reservedWords.add("String");
        this.reservedWords.add("double");
        this.reservedWords.add("char");
        this.reservedWords.add("print");

        // Initialize whitespace list
        this.whitespaceChars.add("\n");
        this.whitespaceChars.add("\t");
        this.whitespaceChars.add(" ");
        this.whitespaceChars.add("\0");
        this.whitespaceChars.add("");

        this.text = text;
    }

    /**
     * Start the execution of the automata. Word by word and letter by letter.
     */
    public Automata computeAutomata() {
        prepareAutomata();

        wordArray.forEach(word -> {
            states.add(processWord(word));
        });

        processStates();

        return this;
    }

    /**
     * Set the text the automata will use to start counting tokens.
     * Also resets all counters to zero
     * 
     * @param text The string that will be evaluated
     * @return The Automata with the text.
     */
    public Automata setText(String text) {
        this.text = text;
        return this.resetAutomata();
    }

    /**
     * Set all counters to zero
     * 
     * @return The automata with all counters in zero
     */
    public Automata resetAutomata() {
        this.reservedKeywords = 0;
        this.identifiers = 0;
        this.relationalOperators = 0;
        this.logicalOperators = 0;
        this.arithmeticalOperators = 0;
        this.increments = 0;
        this.decrements = 0;
        this.assignations = 0;
        this.integerNumbers = 0;
        this.decimalNumbers = 0;
        this.strings = 0;
        this.comments = 0;
        this.inlineComments = 0;
        this.parenthesis = 0;
        this.curlyBraces = 0;
        this.errors = 0;

        this.states.clear();
        this.wordArray.clear();
        this.splittedText = new String[] {};

        return this;
    }

    /**
     * Return the formatted result of all counters.
     * Must be called after <code>computeAutomata</code> otherwise, all counters
     * will be set on zero.
     * If <code>resetAutomata</code> is called before this function, all counters
     * will be zero.
     */
    public String formatOutput() {
        return "Palabras reservadas: " + this.reservedKeywords + "\n" +
                "Identificadores: " + this.identifiers + "\n" +
                "Operadores relacionales: " + this.relationalOperators + "\n" +
                "Operadores lógicos: " + this.logicalOperators + "\n" +
                "Operadores aritméticos: " + this.arithmeticalOperators + "\n" +
                "Asignaciones: " + this.assignations + "\n" +
                "Números enteros: " + this.integerNumbers + "\n" +
                "Números decimales: " + this.decimalNumbers + "\n" +
                "Incremento: " + this.increments + "\n" +
                "Decremento: " + this.decrements + "\n" +
                "Cadena de caracteres: " + this.strings + "\n" +
                "Comentario: " + this.comments + "\n" +
                "Comentario de línea: " + this.inlineComments + "\n" +
                "Paréntesis: " + this.parenthesis + "\n" +
                "Llaves: " + this.curlyBraces + "\n" +
                "Errores: " + this.errors + "\n";
    }

    // #endregion

    // #region Private methods

    /**
     * Split the String in an array of words and save it in the automata
     */
    private void prepareAutomata() {
        this.splittedText = text.split("");
        var word = "";

        // Create all the words of the automata
        for (String chars : splittedText) {
            if (!whitespaceChars.contains(chars)) {
                word += chars;
            } else if (word.length() > 0) {
                wordArray.add(word);
                word = "";
            }
        }

    }

    /**
     * Process the word splitting all the characters and determine the final state.
     * 
     * @param word The word to process
     * @return The state of the word processed
     */
    private int processWord(String word) {
        int state = 0;

        var characterArray = word.split("");

        for (String character : characterArray) {
            switch (state) {
                case 0:
                    if (character.equals("\"")) {
                        state = 1;
                    } else if (character.equals("+")) {
                        state = 3;
                    } else if (character.equals("-")) {
                        state = 5;
                    } else if (!Double.isNaN(parseSafeDouble(character))) {
                        state = 10;
                    } else if (character.equals("*") || character.equals("%")) {
                        state = 11;
                    } else if (character.equals("{") || character.equals("}")) {
                        state = 12;
                    } else if (character.equals("(") || character.equals(")")) {
                        state = 13;
                    } else if (character.equals("=")) {
                        state = 16;
                    } else if (character.equals("<") || character.equals(">")) {
                        state = 14;
                    } else if (character.equals("!")) {
                        state = 17;
                    } else if (character.equals("&")) {
                        state = 18;
                    } else if (character.equals("|")) {
                        state = 20;
                    } else if (character.equals("/")) {
                        state = 21;
                    } else if (isLetter(character)) {
                        state = 26;
                    } else {
                        state = 999;
                    }
                    break;
                case 1:
                    if (character.equals("\"")) {
                        state = 2;
                    }
                    break;
                case 2:
                    state = 999;
                    break;
                case 3:
                    if (character.equals("+")) {
                        state = 4;
                    } else {
                        state = 999;
                    }
                    break;
                case 4:
                    state = 999;
                    break;
                case 5:
                    if (character.equals("-")) {
                        state = 6;
                    } else if (!Double.isNaN(parseSafeDouble(character))) {
                        state = 10;
                    } else {
                        state = 999;
                    }
                    break;
                case 6:
                    state = 999;
                    break;
                case 8:
                    if (!Double.isNaN(parseSafeDouble(character))) {
                        state = 9;
                    } else {
                        state = 999;
                    }
                    break;
                case 9:
                    if (Double.isNaN(parseSafeDouble(character))) {
                        state = 999;
                    }
                    break;
                case 10:
                    if (!Double.isNaN(parseSafeDouble(character))) {
                        state = 10;
                    } else if (character.equals(".")) {
                        state = 8;
                    } else {
                        state = 999;
                    }
                    break;
                case 11:
                    state = 999;
                    break;
                case 12:
                    state = 999;
                    break;
                case 13:
                    state = 999;
                    break;
                case 14:
                    if (character.equals("=")) {
                        state = 15;
                    } else {
                        state = 999;
                    }
                    break;
                case 15:
                    state = 999;
                    break;
                case 16:
                    if (character.equals("=")) {
                        state = 15;
                    } else {
                        state = 999;
                    }
                    break;
                case 17:
                    if (character.equals("=")) {
                        state = 15;
                    } else {
                        state = 999;
                    }
                    break;
                case 18:
                    if (character.equals("&")) {
                        state = 19;
                    } else {
                        state = 999;
                    }
                    break;
                case 19:
                    state = 999;
                    break;
                case 20:
                    if (character.equals("|")) {
                        state = 19;
                    } else {
                        state = 999;
                    }
                    break;
                case 21:
                    if (character.equals("/")) {
                        state = 22;
                    } else if (character.equals("*")) {
                        state = 23;
                    } else {
                        state = 999;
                    }
                    break;
                case 22:
                    state = 22;
                    break;
                case 23:
                    if (character.equals("*")) {
                        state = 24;
                    }
                    break;
                case 24:
                    if (character.equals("/")) {
                        state = 25;
                    } else {
                        state = 23;
                    }
                    break;
                case 25:
                    state = 999;
                    break;
                case 26:
                    if (!isLetter(character) || Double.isNaN(parseSafeDouble(character)) || !character.equals("_")) {
                        state = 999;
                    }
                    break;
                default:
                    break;
            }
            if (state == 999) {
                // No acceptation. Exit the loop
                break;
            }
        }

        // If the detected case is 1. Check if its reserved keyword
        if (state == 26 && this.reservedWords.contains(word)) {
            state = 27;
        }

        return state;
    }

    /**
     * Start the count of all saved states of all the evaluations of the automata
     */
    private void processStates() {
        for (Integer state : states) {
            switch (state) {
                case 2:
                    this.strings++;
                    break;
                case 3:
                    this.arithmeticalOperators++;
                    break;
                case 4:
                    this.increments++;
                    break;
                case 5:
                    this.arithmeticalOperators++;
                    break;
                case 6:
                    this.decrements++;
                    break;
                case 9:
                    this.decimalNumbers++;
                    break;
                case 10:
                    this.integerNumbers++;
                    break;
                case 11:
                    this.arithmeticalOperators++;
                    break;
                case 12:
                    this.curlyBraces++;
                    break;
                case 13:
                    this.parenthesis++;
                    break;
                case 14:
                    this.relationalOperators++;
                    break;
                case 15:
                    this.relationalOperators++;
                    break;
                case 16:
                    this.assignations++;
                    break;
                case 17:
                    this.logicalOperators++;
                    break;
                case 19:
                    this.logicalOperators++;
                    break;
                case 21:
                    this.arithmeticalOperators++;
                    break;
                case 22:
                    this.inlineComments++;
                    break;
                case 25:
                    this.comments++;
                    break;
                case 26:
                    this.identifiers++;
                    break;
                case 27:
                    this.reservedKeywords++;
                    break;
                default:
                    errors++;
                    break;
            }
        }
    }

    /**
     * Determine whether the first character of the string is a valid ASCII
     * character
     * 
     * @param chars The string. Only the first character is evaluated
     * @return <code>true</code> if is a valid ASCII character, <code>false</code>
     *         otherwise
     */
    private boolean isLetter(String chars) {
        int charCode = (int) chars.charAt(0);

        return ((charCode >= 65 && charCode <= 90) || (charCode >= 97 && charCode <= 122));
    }

    /**
     * Calls <code>Double.parseDouble()</code> over the string.
     * But, instead of throwing an exception, return <code>Double.NaN</code> if it's
     * an invalid Double
     * 
     * @param chars The string to be parsed
     * @return The number converted, or <code>Double.NaN</code>
     */
    private Double parseSafeDouble(String chars) {
        try {
            return Double.parseDouble(chars);
        } catch (Exception e) {
            return Double.NaN;
        }
    }

    // #endregion
}