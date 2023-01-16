import ply.yacc as yacc
from LEXER import tokens

def p_documento(p): 
    '''documento : implementa_funcion 
    | instrucciones'''
    
def p_cuerpoF(p):
  '''cuerpoF : asignacion
  | impresion
  | asignadores
  | comparators
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
  '''add : operado PLUS operado
  '''
#ysrael larco faubla
def p_subtract(p):
  '''subtract : operado MINUS operado
  '''
#ysrael larco faubla
def p_multiply(p):
  '''multiply : operado TIMES operado
  '''
  #ysrael larco faubla
def p_divide(p):
  '''divide : operado DIVIDE operado
  '''

def p_operado(p):
    '''operado : add
    | subtract
    | multiply
    | divide
    | dato
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
    '''compareType : dato IGUAL IGUAL dato
    '''

def p_compareNotEqual(p):
    '''compareNotEqual : dato NOT IGUAL dato
    '''

def p_comparators(p):
    '''comparators : compareType
    | compareGreaterthan
    | compareSmallerthan
    | compareNotEqual
    '''
#ysrael larco faubla
def p_compareGreaterthan(p):
    '''compareGreaterthan : dato MAYOR dato
  '''
 #ysrael larco faubla 
def p_compareSmallerthan(p):
    '''compareSmallerthan : dato MINOR dato
  '''
#ysrael larco faubla
def p_impresion(p):
  'impresion : PRINTLN LPAREN datoretornado RPAREN'

def p_datoretornado(p):
    '''datoretornado : operado
    | booly
    '''
def p_condicion(p):
    ''' condicion : booly
    '''

def p_booly(p):
    '''booly : comparators
    | logic
    | TRUE
    | FALSE'''

def  p_logic(p):
    '''logic : booly logicoperador booly
    | NOT booly
    '''

def p_logicoperador(p):
    '''logicoperador : AND
    | OR'''

#ysrael larco faubla
def p_if(p):
    '''if : IF LPAREN condicion RPAREN LKEY instrucciones RKEY
    | IF LPAREN condicion RPAREN LKEY instrucciones RKEY else
    | IF LPAREN condicion RPAREN LKEY instrucciones RKEY elseif else
    '''

def p_elseif(p):
    'elseif : ELSE IF LPAREN condicion RPAREN LKEY instrucciones RKEY'

def p_else(p):
    'else : ELSE LKEY instrucciones RKEY'

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
    'for : FOR LPAREN condicionFor RPAREN LKEY instrucciones RKEY'

#ysrael larco faubla
def p_while(p):
    'while : WHILE LPAREN condicion RPAREN LKEY instrucciones RKEY'

#ysrael larco faubla
def p_when(p):
    'when : WHEN LPAREN VARIABLE RPAREN LKEY instrucciones RKEY'

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

def p_asignadores(p):
    '''asignadores : asigna
    | plusasigna
    | minasigna
    | multasigna
    | divasigna'''

def p_asigna(p):
  'asigna : VARIABLE IGUAL asignado'

def p_plusasigna(p):
    'plusasigna : VARIABLE PLUS IGUAL asignado'

def p_minasigna(p):
    'minasigna : VARIABLE MINUS IGUAL asignado'

def p_multasigna(p):
    'multasigna : VARIABLE TIMES IGUAL asignado'

def p_divasigna(p):
    'divasigna : VARIABLE DIVIDE IGUAL asignado'

def p_asignado(p):
  '''asignado : comparators
| operado'''

def p_dato(p):
    '''dato : valor
    | VARIABLE
    '''

def p_error(p):
    if p:
        print(f"Error de sintaxis - Token: {p.type}, Línea: {p.lineno}, Col: {p.lexpos}")
        parser.errok()
    else:
        print("Error de sintaxis Fin de Linea")


# Build the parser
parser = yacc.yacc()


