## Standard/Scientific Calculator with Java

![screenshot1](https://raw.githubusercontent.com/hethon/thecalc/84218e9555e958968d2158f4d6e36283691ad75d/screenshots/basicMode.png?token=GHSAT0AAAAAACTAPU7ULQIRATFZOTNIWP7MZWZL32A)
![screenshot2](https://raw.githubusercontent.com/hethon/thecalc/master/screenshots/scientificMod.png?token=GHSAT0AAAAAACTAPU7VD2PZDCGWNJLRSPHGZWZMBIA)

# How to run?

## Run the following command from the project's root directory'
`javac app/App.java`
## Then,
`java app/App`

## 1. app
This package contains everything related to the GUI. Uses the parser package to evaluate expressions.
## 2. evaluator
This package contains methods for evaluating different mathematical statements.
## 3. parser
### 1. Standardization
### the parser's basic operand is a `String`. it decodes the string to obtain functions, numbers, brackets and basic operations. 
### To avoid complications here are the standards that the string must follow.
- All functions are 3 character words.
- No spaces between characters in the string.
- Multiplication is represented by '*' (not x or X).
- Subscripts are represented by '_' and superscripts by '^'.
- Absolute value is represented by abs() not |f|.
- Generally, the string should not contain anything weird.
- The parser can only handle syntax errors (mathematical errors are handled by the evaluator and sent back as signals to the parser).
### 2. Considered functions
### These are the considered functions so far:
- [x] `null` - (no function)
- [x] `srt` - (square root)
- [x] `crt` - (cube root)
- [x] `sin`
- [x] `cos`
- [x] `tan`
- [x] `sec`
- [x] `csc`
- [x] `cot`
- [x] `lan` - (ln)
- [x] `log`
- [x] `asi` - (arcsin)
- [x] `acs` - (arccos)
- [x] `atn` - (arctan)
- [x] `ase` - (arcsec)
- [x] `acc` - (arccsc)
- [x] `act` - (arccot)
> edit and add more to the list
