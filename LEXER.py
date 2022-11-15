import ply.lex as lex

# Tokens
reserved = {
  'if':'IF',
  'for':'FOR',
  'while':'WHILE',
  'switch':'SWITCH'
}

tokens = [
  'INT',
  'FLOAT',
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
  'VARIABLE'
] + list(reserved.values())

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

# A regular expression rule with some action code
def t_FLOAT(t):
  r'\d+\.\d+'
  t.value = float(t.value)
  return t
  
def t_INT(t):
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

# Test it out
instructions = '''3 + 4 * 10
   + -20 *2
'''

def getTokens(lexer):
  for tok in lexer:
    print(tok)

linea=" "

file = open("source.txt")
cadena=file.read()
file.close()
lexer.input(cadena)
getTokens(lexer)

while linea!="":
    linea=input(">>")
    lexer.input(linea)
    getTokens(lexer)
# Tokenize
print("Succesfull")