"use client";
import React, { useState } from "react";
import ResultRow from "./ResultRow";

export default function Result({ title, content }) {
  const initialData = {
    "Palabras reservadas": 0,
    "Identificadores": 0,
    "Operadores Relacionales": 0,
    "Operadores Lógicos": 0,
    "Operadores Aritméticos": 0,
    "Asignaciones": 0,
    "Números Enteros": 0,
    "Números Decimales": 0,
    "Cadena de caracteres": 0,
    "Comentarios Multilínea": 0,
    "Comentarios de Linea": 0,
    "Paréntesis": 0,
    "Llaves": 0,
    "Errores": 0,
  };

  const [resultMapFinal, setResultMap] = useState(initialData);

  const handleReload = () => {
    window.location.reload();
  };

  const palabrasReservadas = [
    "if",
    "else",
    "switch",
    "case",
    "default",
    "for",
    "while",
    "break",
    "int",
    "String",
    "double",
    "char",
    "print",
  ];

  const whitespaceChars = ["\n", "\t", " ", "\0", ""];

  const resultMap = {
    "Palabras reservadas": 0,
    "Identificadores": 0,
    "Operadores Relacionales": 0,
    "Operadores Lógicos": 0,
    "Operadores Aritméticos": 0,
    "Asignaciones": 0,
    "Números Enteros": 0,
    "Números Decimales": 0,
    "Cadena de caracteres": 0,
    "Multilínea": 0,
    "Comentarios de Linea": 0,
    "Paréntesis": 0,
    "Llaves": 0,
    "Errores": 0,
  };

  const processData = () => {
    const wordArray = filterWords();
    const states = [];
    wordArray.forEach((word) => {
      states.push(processWord(word));
    });
    processStates(states);
    setResultMap(resultMap);
  };

  const processStates = (states) => {
    states.forEach((state) => {
      switch (state) {
        case 2:
          resultMap["Errores"]++;
          break;
        case 3:
          resultMap["Operadores Lógicos"]++;
          break;
        case 5:
          resultMap["Asignaciones"]++;
          break;
        case 6:
          resultMap["Operadores Relacionales"]++;
          break;
        case 7:
          resultMap["Operadores Relacionales"]++;
          break;
        case 8:
          resultMap["Paréntesis"]++;
          break;
        case 9:
          resultMap["Llaves"]++;
          break;
        case 10:
          resultMap["Operadores Aritméticos"]++;
          break;
        case 11:
          resultMap["Comentarios de Linea"]++;
          break;
        case 14:
          resultMap["Multilínea"]++;
          break;
        case 16:
          resultMap["Cadena de caracteres"]++;
          break;
        case 17:
          resultMap["Operadores Aritméticos"]++;
          break;
        case 18:
          resultMap["Operadores Aritméticos"]++;
          break;
        case 19:
          resultMap["Números Enteros"]++;
          break;
        case 20:
          resultMap["Números Decimales"]++;
          break;
        case 21:
          resultMap["Identificadores"]++;
          break;
        case 22:
          resultMap["Palabras reservadas"]++;
          break;
        case 23:
          resultMap["Operadores Lógicos"]++;
          break;
        default:
      }
    });
  }

  const filterWords = () => {
    const charArray = [];
    const charText = content.split(""); //Convert content to char Array
    let word = "";

    charText.forEach((c) => {
      //Traverse the content
      if (!whitespaceChars.includes(c)) {
        word += c;
      } else if (word.length > 0) {
        charArray.push(word);
        word = "";
      }
    });
    return charArray;
  };

  function isLetter(char) {
    const charCode = char.charCodeAt(0);
    return (charCode >= 65 && charCode <= 90) || (charCode >= 97 && charCode <= 122);
  }

  const processWord = (word) => {
    let state = 0;
    const charArray = word.split("");
    charArray.forEach((c) => {
      switch (state) {
        case 0:
          if (c == "&") state = 1;
          if (c == '|') state = 4;
          if (c == "!") state = 3
          if (c == '=') state = 5
          if (c == '/') state = 10
          if (c == '"') state = 15
          if (c == '-') state = 18
          if (!isNaN(c)) state = 19
          if (isLetter(c)) state = 21
          if (c == '<' || c == '>') state = 6
          if (c == '(' || c == ')') state = 8
          if (c == '{' || c == '}') state = 9
          if (c == '+' || c == '*' || c == '%') state = 17
          break;
        case 1:
          if (c == '&') state = 23
          else state = 2
          break;
        case 2: break;
        case 3:
          if (c == '=') state = 7
          else state = 2
          break;
        case 4:
          if (c == '|') state = 23
          else state = 2
          break;
        case 5:
          if (c == '=') state = 7
          else state = 2
          break;
        case 6:
          if (c == '=') state = 7
          else state = 2
          break;
        case 7:
          state = 2
          break;
        case 8:
          state = 2
          break;
        case 9:
          state = 2
          break;
        case 10:
          if (c == '/') state = 11
          else if (c == '*') state = 12
          else state = 2
          break;
        case 11:
          break;
        case 12:
          if (c == '*') state = 13
          break;
        case 13:
          if (c == '/') state = 14
          else state = 12
          break;
        case 14:
          state = 2
          break;
        case 15:
          if (c == '"') state = 16
          break;
        case 16:
          state = 2;
          break;
        case 17:
          state = 2
          break;
        case 18:
          if (!isNaN(c)) state = 19
          break;
        case 19:
          if (c == '.') state = 20
          break;
        case 20:
          if (c == '.') state = 2
          break;
        case 21:
          if (!isLetter(c) || !c == '_')
            state = 2
          break;
        case 23:
          state = 2;
          break;
        default:
      }
    });
    //Check if the word is reserved
    if (palabrasReservadas.includes(word))
      state = 22;
    if (state == 7)
      console.log(word)
    return state;
  };

  return (
    <div className="col-4 ">
      <h2 className="transbox">{title}</h2>
      <div className="container text-center ">
        <div
          class="btn-group"
          role="group"
          aria-label="Basic mixed styles example"
        >
          <button
            type="button"
            onClick={handleReload}
            class="btn btn-danger"
          >
            Reset
          </button>
          <button
            type="button"
            class="btn btn-success"
            onClick={processData}
          >
            Start
          </button>
        </div>

        <div className=" transbox">
          {Object.entries(resultMapFinal).map(([label, value], index) => (
            <ResultRow
              key={index}
              label={label}
              value={value}
            />
          ))}
        </div>
      </div>
    </div>
  );
}