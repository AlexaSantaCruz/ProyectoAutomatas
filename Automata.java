public class Automata {
  /** The text to evaluate */
  private String text;

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
   * 
   * </ul>
   */
  private int arithmeticalOperators = 0;

  private int assignations = 0;
  // #endregion

  /** Initialize a new Automata with the given text */
  public Automata(String text) {
    this.text = text;
  }

  /**
   * Start the execution of the automata. Letter by letter.
   */
  public Automata computeAutomata() {

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
        "Números enteros: " + this.relationalOperators + "\n" +
        "Números decimales: " + this.relationalOperators + "\n" +
        "Incremento: " + this.relationalOperators + "\n" +
        "Decremento: " + this.relationalOperators + "\n" +
        "Cadena de caracteres: " + this.relationalOperators + "\n" +
        "Comentario: " + this.relationalOperators + "\n" +
        "Comentario de línea: " + this.relationalOperators + "\n" +
        "Paréntesis: " + this.relationalOperators + "\n" +
        "Llaves: " + this.relationalOperators + "\n" +
        "Errores: " + this.relationalOperators + "\n";
  }
}