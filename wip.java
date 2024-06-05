    int processWord(String word) {
        var state = 0;

        var charArray = word.split("");

        for (String chars : charArray) {
            switch (state) {
                case 0:
                    if (chars == "&")
                        state = 1;
                    if (chars == "|")
                        state = 4;
                    if (chars == "<" || chars == ">")
                        state = 6;
                    if (chars == "!")
                        state = 3;
                    if (chars == "=")
                        state = 5;
                    if (chars == "/")
                        state = 10;
                    if (chars == "\"")
                        state = 15;
                    if (chars == "-")
                        state = 18;
                    if (!Double.isNaN(isNumber(chars)))
                        state = 19;
                    if (isLetter(chars))
                        state = 21;
                    if (chars == "(" || chars == ")")
                        state = 8;
                    if (chars == "{" || chars == "}")
                        state = 9;
                    if (chars == "+" || chars == "*" || chars == "%")
                        state = 17;
                    break;
                case 1:
                    if (chars == "&")
                        state = 23;
                    else
                        state = 2;
                    break;
                case 2:
                    break;
                case 3:
                    if (chars == "=")
                        state = 7;
                    else
                        state = 2;
                    break;
                case 4:
                    if (chars == "|")
                        state = 23;
                    else
                        state = 2;
                    break;
                case 5:
                    if (chars == "=")
                        state = 7;
                    else
                        state = 2;
                    break;
                case 6:
                    if (chars == "=")
                        state = 7;
                    else
                        state = 2;
                    break;
                case 7:
                    state = 2;
                    break;
                case 8:
                    state = 2;
                    break;
                case 9:
                    state = 2;
                    break;
                case 10:
                    if (chars == "/")
                        state = 11;
                    else if (chars == "*")
                        state = 12;
                    else
                        state = 2;
                    break;
                case 11:
                    break;
                case 12:
                    if (chars == "*")
                        state = 13;
                    break;
                case 13:
                    if (chars == "/")
                        state = 14;
                    else
                        state = 12;
                    break;
                case 14:
                    state = 2;
                    break;
                case 15:
                    if (chars == "\"")
                        state = 16;
                    break;
                case 16:
                    state = 2;
                    break;
                case 17:
                    state = 2;
                    break;
                case 18:
                    if (!Double.isNaN(isNumber(chars)))
                        state = 19;
                    break;
                case 19:
                    if (chars == ".")
                        state = 20;
                    break;
                case 20:
                    if (chars == ".")
                        state = 2;
                    break;
                case 21:
                    if (!isLetter(chars) || chars != "_")
                        state = 2;
                    break;
                case 23:
                    state = 2;
                    break;
                default:
                    break;
            }
        }

        // Check if the word is reserved
        if (palabrasReservadas.contains(word))
            state = 22;

        return state;
    }