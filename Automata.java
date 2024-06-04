import java.util.ArrayList;

public class Automata {
    // #region Properties

    /** The text to evaluate */
    private String text;

    /** The text splitted character by character */
    private String[] splittedText;
    /** List of all the words of the automata */
    private ArrayList<String> wordArray;
    /** The list of all reserved words */
    private ArrayList<String> reservedWords;
    /** The list of all symbols considered "whitespace" */
    private ArrayList<String> whitespaceChars;

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

    // #region Constants
    private final int Q0 = 0;
    private final int Q13 = 13;
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
     * Start the execution of the automata. Letter by letter.
     */
    public Automata computeAutomata() {
        prepareAutomata();

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

        return this;
    }

    /**
     * Return the formatted result of all counters.
     * Must be called before <code>computeAutomata</code> otherwise, all counters
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