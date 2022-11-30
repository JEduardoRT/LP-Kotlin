import ply.yacc as yacc
from LEXER import tokens

def p_documento(p): 
    '''documento : implementa_funcion 
    | instrucciones'''
    
def p_cuerpoF(p):
  '''cuerpoF : asignacion
  | impresion
  | asigna
  | compareType
  | compareGreaterthan
  | compareSmallerthan
  | list
  | mutableList
  | pair
  | inData
  | if
  | for
  | while
  | when 
  '''

def p_asignacion(p):
  'asignacion : designacion VARIABLE DOUBLEPOINTS tipo IGUAL valor'

#Donoso Bravo Luis Alejandro
def p_designacion(p):
    """designacion : VAL 
        | VAR
    """

#Donoso Bravo Luis Alejandro

def p_tipo(p):
    """tipo : TINT
        | TFLOAT
        | STRING
    """

#Donoso Bravo Luis Alejandro

def p_diamondtype(p):
    """diamondType : MINOR tipo MAYOR"""
#Donoso Bravo Luis Alejandro

def p_listAsignacion(p):
    ''' listAsignacion : designacion  VARIABLE DOUBLEPOINTS LIST diamondType
    | designacion VARIABLE
    '''

#Donoso Bravo Luis Alejandro

def p_listOf(p):
    '''listOf : LISTOF LPAREN elementos RPAREN '''

#Donoso Bravo Luis Alejandro

def p_elementos(p):
    '''elementos : elementos COMA valor
    | valor
    '''

#Donoso Bravo Luis Alejandro

def p_valor(p):
    '''valor : VINT
    | VFLOAT
    | STRING
    '''

#Donoso Bravo Luis Alejandro

def p_list(p):
    '''list : listAsignacion IGUAL listOf
    '''

#ysrael larco faubla
def p_add(p):
  '''add : VINT PLUS VINT
  | VINT PLUS VFLOAT
  | VFLOAT PLUS VFLOAT
  '''
#ysrael larco faubla
def p_subtract(p):
  '''subtract : VINT MINUS VINT
  | VINT MINUS VFLOAT
  | VFLOAT MINUS VFLOAT
  
  '''
#ysrael larco faubla
def p_multiply(p):
  '''multiply : VINT TIMES VINT
  | VFLOAT TIMES VFLOAT
  | VFLOAT TIMES VINT
  | VINT TIMES VFLOAT
  '''
  #ysrael larco faubla
def p_divide(p):
  '''divide : VINT DIVIDE VINT
  | VFLOAT DIVIDE VFLOAT
  | VFLOAT DIVIDE VINT
  | VINT DIVIDE VFLOAT
  '''

#Donoso Bravo Luis Alejandro

def p_mutListAsignacion(p):
    ''' mutListAsignacion : designacion VARIABLE DOUBLEPOINTS MUTABLELIST diamondType
    | designacion VARIABLE
    '''

#Donoso Bravo Luis Alejandro

def p_mutableListOf(p):
    '''mutableListOf : MUTABLELISTOF LPAREN elementos RPAREN'''

#Donoso Bravo Luis Alejandro

def p_mutableList(p):
    '''mutableList : mutListAsignacion IGUAL mutableListOf
    '''

#Donoso Bravo Luis Alejandro

def p_pairAsignation(p):
    '''pairAsignation : designacion LPAREN VARIABLE COMA VARIABLE RPAREN'''

#Donoso Bravo Luis Alejandro

def p_pairBody(p):
    '''pairBody : PAIR LPAREN valor COMA valor RPAREN'''

#Donoso Bravo Luis Alejandro

def p_pair(p):
    '''pair : pairAsignation IGUAL pairBody
    '''
#Donoso Bravo Luis Alejandro

def p_inData(p):
    '''inData : designacion VARIABLE IGUAL READLINE LPAREN RPAREN'''

#ysrael larco faubla
def p_compareType(p):
    '''compareType : VINT IGUAL VINT
    | VFLOAT IGUAL VFLOAT
    '''
#ysrael larco faubla
def p_compareGreaterthan(p):
    '''compareGreaterthan : VINT MAYOR VINT
    | VFLOAT MAYOR VFLOAT
  '''
 #ysrael larco faubla 
def p_compareSmallerthan(p):
    '''compareSmallerthan : VINT MINOR VINT
    | VFLOAT MINOR VFLOAT
  '''
#ysrael larco faubla
def p_impresion(p):
  'impresion : PRINTLN LPAREN valor RPAREN'

def p_condicion(p):
    ''' condicion : compareType
    | compareGreaterthan
    | compareSmallerthan
    '''

#ysrael larco faubla
def p_if(p):
    '''if : IF LPAREN condicion RPAREN LKEY cuerpoF RKEY
    | IF LPAREN condicion RPAREN LKEY cuerpoF RKEY else
    | IF LPAREN condicion RPAREN LKEY cuerpoF RKEY elseif else
    '''

def p_elseif(p):
    'elseif : ELSE IF LPAREN condicion RPAREN LKEY cuerpoF RKEY'

def p_else(p):
    'else : LKEY cuerpoF RKEY'

def p_condicionFor(p):
    ''' condicionFor : condicionRango
    | condicionBloque
    '''

def p_condicionRango(p):
    'condicionRango :  VARIABLE IN VINT DOT DOT VINT '

def p_condicionBloque(p):
    'condicionBloque : VARIABLE IN LISTOF'

#ysrael larco faubla
def p_for(p):
    'for : FOR LPAREN condicionFor RPAREN LKEY cuerpoF RKEY'

#ysrael larco faubla
def p_while(p):
    'while : WHILE LPAREN condicion RPAREN LKEY cuerpoF RKEY'

#ysrael larco faubla
def p_when(p):
    'when : WHEN LPAREN VARIABLE RPAREN LKEY cuerpoF RKEY'

def p_implementa_funcion(p):

    '''implementa_funcion : FUN VARIABLE LPAREN listaparametros RPAREN DOUBLEPOINTS tipo LKEY instrucciones retorno RKEY
    | FUN VARIABLE LPAREN RPAREN DOUBLEPOINTS tipo LKEY instrucciones retorno RKEY
    | FUN VARIABLE LPAREN RPAREN DOUBLEPOINTS VOID LKEY instrucciones RKEY
    | FUN VARIABLE LPAREN listaparametros RPAREN DOUBLEPOINTS VOID LKEY instrucciones RKEY'''

def p_listaparametros(p):
    '''listaparametros : parametro
    | parametro COMA listaparametros'''

def p_parametro(p):
    'parametro : VARIABLE DOUBLEPOINTS tipo'

def p_retorno(p):
  'retorno : RETURN asignado'

def p_instrucciones(p):
  '''instrucciones : cuerpoF
      | cuerpoF instrucciones'''

def p_asigna(p):
  'asigna : VARIABLE IGUAL asignado'

def p_asignado(p):
  '''asignado : valor
| VARIABLE
| compareType
| compareGreaterthan
| compareSmallerthan
| add
| subtract
| multiply
| divide'''

def p_error(p):
    if p:
        print(f"Error de sintaxis - Token: {p.type}, Línea: {p.lineno}, Col: {p.lexpos}")
        parser.errok()
    else:
        print("Error de sintaxis Fin de Linea")


# Build the parser
parser = yacc.yacc()


def validaRegla(s):
    result = parser.parse(s)
    print(s)
    print(result)


try:
    file = open("source.txt")
    cadena=file.read()
    file.close()
    s = cadena
except EOFError:
    print("Error: Archivo vacio")
if s: 
    validaRegla(s)
