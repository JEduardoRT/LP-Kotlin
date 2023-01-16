import ply.lex as lex

#Ysrael Larco (inicio)

# Tokens
reserved = {
  'if':'IF',
  'for':'FOR',
  'while':'WHILE',
  'switch':'SWITCH',
  'as':'AS',
  'break':'BREAK',
  'else':'ELSE',
  'typealias':'TYPEALIAS',
  'typeof':'TYPEOF',
  'static':'STATIC',
  'abstract':'ABSTRACT',
  'import':'IMPORT',
  'enum':'ENUM',
  'in':'IN',
  'super':'SUPER',
  'async':'ASYNC',
  'interface':'INTERFACE',
  'infix':'INFIX',
  'extends':'EXTENDS',
  'is':'IS',
  'sync':'SYNC',
  'setparam':'SETPARAM',
  'external':'EXTERNAL',
  'library':'LIBRARY',
  'this':'THIS',
  'case':'CASE',
  'factory':'FACTORY',
  'override':'OVERRIDE',
  'throw':'THROW',
  'catch':'CATCH',
  'false':'FALSE',
  'true':'TRUE',
  'new':'NEW',
  'class':'CLASS',
  'final':'FINAL',
  'null':'NULL',
  'try':'TRY',
  'const':'CONST',
  'val':'VAL',
  'var':'VAR',
  'open':'OPEN',
  'continue':'CONTINUE',
  'operator':'OPERATOR',
  'init':'INIT',
  'function':'FUNCTION',
  'part':'PART',
  'void':'VOID',
  'default':'DEFAULT',
  'get':'GET',
  'constructor':'CONSTRUCTOR',
  'when':'WHEN',
  'hide':'HIDE',
  'return':'RETURN',
  'with':'WITH',
  'do':'DO',
  'set':'SET',
  'out':'OUT',
  'Int': 'TINT',
  'Float': 'TFLOAT',
  'String': 'STRING',
  'println': 'PRINTLN',
  'list': 'LIST',
  'listOf': 'LISTOF',
  'mutableList': 'MUTABLELIST',
  'mutableListOf': 'MUTABLELISTOF',
  'pair': 'PAIR',
  'readLine': 'READLINE',
  'fun': 'FUN'
}

#Ysrael Larco (final)

#Jandry Rodriguez (inicio)

tokens = [
  'VINT',
  'VFLOAT',
  'MINUS',
  'PLUS',
  'TIMES',
  'DIVIDE',
  'LPAREN',
  'RPAREN',
  'LKEY',
  'RKEY',
  'IGUAL',
  'MINOR',
  'MAYOR',
  'NEQUAL',
  'AND',
  'OR',
  'VARIABLE',
  'DOUBLEPOINTS',
  'DOT',
  'COMA',
  'ADMIRATION',
  'NOT'
] + list(reserved.values())

#Jandry Rodriguez (final)

#Luis Donoso (inicio)

# Regular expression rules for simple tokens
t_PLUS     = r'\+'
t_MINUS   = r'-'
t_TIMES   = r'\*'
t_DIVIDE  = r'/'
t_LPAREN  = r'\('
t_RPAREN  = r'\)'
t_LKEY  = r'\{'
t_RKEY  = r'\}'
t_IGUAL = r'='
t_MINOR = r'<'
t_MAYOR = r'>'
t_NEQUAL = r'<>'
t_AND = r'&&'
t_OR = r'\|\|'
t_DOUBLEPOINTS = r':'
t_DOT = r'\.'
t_COMA = r','
t_NOT = r'!'

#Luis Donoso (inicio)

#Jandry Rodriguez (inicio)

# A regular expression rule with some action code
def t_VFLOAT(t):
  r'\d+\.\d+'
  t.value = float(t.value)
  return t
  
def t_VINT(t):
  r'\d+'
  t.value = int(t.value)    
  return t
    
def t_VARIABLE(t):
  r'[a-zA-Z_][a-zA-Z0-9]*'
  t.type = reserved.get(t.value,'VARIABLE')
  return t
  
# Define a rule so we can track line numbers
def t_newline(t):
  r'\n+'
  t.lexer.lineno += len(t.value)
 
#Jandry Rodriguez (final)


#Ysrael Larco (inicio)

# A string containing ignored characters (spaces and tabs)
t_ignore  = ' \t'

def t_COMMENTS(t):
  r'\#.*'
  pass
  
# Error handling rule
def t_error(t):
  print("Caracter no permitido'%s'" % t.value[0])
  t.lexer.skip(1)
 
 # Build the lexer
lexer = lex.lex()

  