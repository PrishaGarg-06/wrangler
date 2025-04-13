grammar Directives;

options {
  language = Java;
}

@lexer::header {
/*
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
}

/**
 * Parser Grammar for recognizing tokens and constructs of the directives language.
 */
recipe
 : statements EOF
 ;

statements
 :  ( Comment | macro | directive SCOLON | pragma SCOLON | ifStatement)* 
 ;

directive
 : command
  ( aggregateStats
    | codeblock
    | identifier
    | macro
    | text
    | number
    | bool
    | column
    | colList
    | numberList
    | boolList
    | stringList
    | numberRanges
    | properties
    | byteSizeArg
    | timeDurationArg
  )*?
  ;

ifStatement
  : IF expression OBrace statements
    (ELSE IF expression OBrace statements)* 
    (ELSE OBrace statements)? 
    CBrace
  ;

expression
  : OParen (~OParen | expression)* CParen
  ;

forStatement
 : FOR OParen Identifier Assign expression SCOLON expression SCOLON expression CParen OBrace statements CBrace
 ;

macro
 : Dollar OBrace (~OBrace | macro | Macro)*? CBrace
 ;

pragma
 : PRAGMA (pragmaLoadDirective | pragmaVersion)
 ;

pragmaLoadDirective
 : LOAD_DIRECTIVES identifierList
 ;

pragmaVersion
 : VERSION Number
 ;

codeblock
 : EXP Space* Colon condition
 ;

identifier
 : Identifier
 ;

properties
 : PROP Colon OBrace (propertyList)+  CBrace
 | PROP Colon OBrace OBrace (propertyList)+ CBrace { notifyErrorListeners("Too many start paranthesis"); }
 | PROP Colon OBrace (propertyList)+ CBrace CBrace { notifyErrorListeners("Too many start paranthesis"); }
 | PROP Colon (propertyList)+ CBrace { notifyErrorListeners("Missing opening brace"); }
 | PROP Colon OBrace (propertyList)+  { notifyErrorListeners("Missing closing brace"); }
 ;

propertyList
 : property (Comma property)*
 ;

property
 : Identifier Assign ( text | number | bool )
 ;

numberRanges
 : numberRange ( Comma numberRange)*
 ;

numberRange
 : Number Colon Number Assign value
 ;

value
 : String | Number | Column | Bool | BYTE_SIZE | TIME_DURATION
 ;

ecommand
 : External Identifier
 ;

config
 : Identifier
 ;

column
 : Column
 ;

text
 : String
 ;

number
 : Number
 ;

bool
 : Bool
 ;

condition
 : OBrace (~CBrace | condition)* CBrace
 ;

command
 : Identifier
 ;

colList
 : Column (Comma Column)+
 ;

numberList
 : Number (Comma Number)+
 ;

boolList
 : Bool (Comma Bool)+
 ;

stringList
 : String (Comma String)+
 ;

identifierList
 : Identifier (Comma Identifier)*
 ;

byteSizeArg
 : BYTE_SIZE
 ;

timeDurationArg
 : TIME_DURATION
 ;

aggregateStats
 : 'aggregate-stats' ( identifier | number | String )* // Corrected 'string' to 'String'
 ;

/*
 * Following are the Lexer Rules used for tokenizing the recipe.
 */
OBrace   : '{';
CBrace   : '}';
SCOLON   : ';';
Or       : '||';
And      : '&&';
Equals   : '==';
NEquals  : '!=';
GTEquals : '>=';
LTEquals : '<=';
Match    : '=~';
NotMatch : '!~';
QuestionColon : '?:';
StartsWith : '=^';
NotStartsWith : '!^';
EndsWith : '=$';
NotEndsWith : '!$';
PlusEqual : '+=';
SubEqual : '-=';
MulEqual : '*=';
DivEqual : '/=';
PerEqual : '%=';
AndEqual : '&=';
OrEqual  : '|=';
XOREqual : '^=';
Pow      : '^';
External : '!';
GT       : '>';
LT       : '<';
Add      : '+';
Subtract : '-';
Multiply : '*';
Divide   : '/';
Modulus  : '%';
OBracket : '[';
CBracket : ']';
OParen   : '(';
CParen   : ')';
Assign   : '=';
Comma    : ',';
QMark    : '?';
Colon    : ':';
Dot      : '.';
At       : '@';
Pipe     : '|';
BackSlash: '\\';
Dollar   : '$';
Tilde    : '~';

Bool
 : 'true'
 | 'false'
 ;

Number
 : Int (Dot Digit*)?
 ;

Identifier
 : [a-zA-Z_\-] [a-zA-Z_0-9\-]* 
 ;

Macro
 : [a-zA-Z_] [a-zA-Z_0-9]* 
 ;

Column
 : ':' [a-zA-Z_\-] [:a-zA-Z_0-9\-]* 
 ;

String
 : '\'' ( EscapeSequence | ~('\'') )* '\''
 | '"'  ( EscapeSequence | ~('"') )* '"'
 ;

BYTE_SIZE
 : Digit+ (Dot Digit+)? BYTE_UNIT
 ;

TIME_DURATION
 : Digit+ (Dot Digit+)? TIME_UNIT
 ;

fragment BYTE_UNIT
 : [kK][bB]
 | [mM][bB]
 | [gG][bB]
 | [bB]
 ;

fragment TIME_UNIT
 : 'ms'
 | 's'
 | 'sec'
 | 'm'
 | 'min'
 ;

EscapeSequence
   :   '\\' ('b'|'t'|'n'|'f'|'r'|'"'|'\''|'\\')
   |   UnicodeEscape
   |   OctalEscape
   ;

fragment
OctalEscape
   :   '\\' ('0'..'3') ('0'..'7') ('0'..'7')
   |   '\\' ('0'..'7') ('0'..'7')
   |   '\\' ('0'..'7')
   ;

fragment
UnicodeEscape
   :   '\\' 'u' HexDigit HexDigit HexDigit HexDigit
   ;

fragment HexDigit : ('0'..'9'|'a'..'f'|'A'..'F') ;

fragment Int
 : '-'? [1-9] Digit* [L]*
 | '0'
 ;

fragment Digit
 : [0-9]
 ;

Comment
 : ('//' ~[\r\n]* | '/*' .*? '*/' | '--' ~[\r\n]* ) -> skip
 ;

Space
 : [ \t\r\n\u000C]+ -> skip
 ;

/* Keywords as tokens */
IF : 'if';
ELSE : 'else';
FOR : 'for';
EXP : 'exp';
VERSION : 'version';
PRAGMA : '#pragma';
LOAD_DIRECTIVES : 'load-directives';
PROP : 'prop';
