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
        | VARIABLE 
    """


def p_diamondtype(p):
    """diamondType : t_Minor tipo t_Mayor"""

def p_list(p):
    ''' list : designacion diamondType t_Igual
    '''
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
