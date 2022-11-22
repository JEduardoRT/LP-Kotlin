import ply.yacc as yacc
import LEXER


def p_cuerpoF(p):
  '''cuerpoF : asignacion
  | impresion
  | compareType
  | compareGreaterthan
  | compareSmallerthan
  '''

def p_asignacion(p):
  'asignacion : designacion VARIABLE DOUBLEPOINTS tipo IGUAL valor'

def p_designacion(p):
    """designacion : VAL 
        | VAR
    """


def p_tipo(p):
    """tipo : TINT
        | TFLOAT
        | STRING
    """


def p_diamondtype(p):
    """diamondType : MINOR tipo MAYOR"""

def p_listAsignacion(p):
    ''' listAsignacion : designacion  VARIABLE DOUBLEPOINT LIST diamondType
    | designacion VARIABLE
    '''


def p_listOf(p):
    '''listOf : lISTOF LPAREN elementos RPAREN '''


def p_elementos(p):
    '''elementos : elementos COMA valor
    | valor
    '''


def p_valor(p):
    '''valor : VINT
    | VFLOAT
    | STRING
    '''


def p_list(p):
    '''list : listAsignacion IGUAL listof
    '''


def p_mutListAsignacion(p):
    ''' mutListAsignacion : designacion VARIABLE DOUBLEPOINT MUTABLELIST diamondType
    | designacion VARIABLE
    '''


def mutableListOf(p):
    '''mutableListOf : MUTABLELISTOF LPAREN elementos RPAREN'''


def p_mutableList(p):
    '''mutableList : mutListAsignacion IGUAL mutableListOf
    '''


def p_pairAsignation(p):
    '''pairAsignation : designacion RPAREN VARIABLE COMA VARIABLE LPAREN'''


def p_pairBody(p):
    '''pairBody : PAIR LPAREN valor COMA valor RPAREN'''


def p_pair(p):
    '''pair : pairAsignation IGUAL pairBody
    '''

def p_inData(p):
    '''inData : designacion VARIABLE igual READLINE LPAREN RPAREN'''


def p_compareType(p):
    '''compareType : VINT IGUAL VINT
    | VFLOAT IGUAL VFLOAT
    '''

def p_compareGreaterthan(p):
    '''compareGreaterthan : VINT MAYOR VINT
    | VFLOAT MAYOR VFLOAT
  '''
  
def p_compareSmallerthan(p):
    '''compareSmallerthan : VINT MENOR VINT
    | VFLOAT MENOR VFLOAT
  '''

def p_impresion(p):
  'impresion : PRINTLN LPAREN valor RPAREN'

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
    print(result)


while True:
    try:
        s = input('calc > ')
    except EOFError:
        break
    if not s: continue
    validaRegla(s)
