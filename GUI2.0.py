import tkinter
from lexico import lexer as lx
import sintactico
from sintactico import parser as sx


def analisis():
    text_output_lexico.delete("1.0", "end")
    text_output_lexico.insert(tkinter.INSERT, analisis_lexico())
    text_output_sintactico.delete("1.0", "end")
    text_output_sintactico.insert(tkinter.INSERT, analisis_sintactico())


#jandry rodriguez
def analisis_lexico():
    lexreturn = ''
    cadena = text_input.get("1.0", tkinter.END)
    s = cadena
    if s:
        lx.input(s)
        for tok in lx:
            lexreturn = lexreturn + str(tok) + '\n'
    return lexreturn


#jandry rodriguez
def analisis_sintactico():
    result = ''
    cadena = text_input.get("1.0", tkinter.END)
    s = cadena
    if s:
        sintactico.output = ''
        result = sx.parse(s)
    if sintactico.output:
        return str(sintactico.output)+str(result)
    else:
        return str(result)


#Ysrael Larco
def clear_input():
    text_input.delete("1.0", tkinter.END)
    text_output_lexico.delete("1.0", tkinter.END)
    text_output_semantico.delete("1.0", tkinter.END)
    text_output_sintactico.delete("1.0", tkinter.END)


#se crea la venta de interface Ysrael Larco
window = tkinter.Tk()

mainlabel = tkinter.Label(text="KOTLIN ANALIZADOR")
mainlabel.pack()

frame = tkinter.Frame(window)
frame.pack()

#creamos la caja de texto donde se almacenara el input Ysrael larco
user_input_frame = tkinter.LabelFrame(frame, text="INPUT")
user_input_frame.grid(row=0, column=0, padx=20, pady=20)

text_input = tkinter.Text(user_input_frame,
                          width=40,
                          height=8,
                          bg="light cyan")
text_input.grid(row=0, column=0, padx=10, pady=10)

user_outputs_frame = tkinter.LabelFrame(frame, text="OUTPUT")
user_outputs_frame.grid(row=1, column=0, padx=20, pady=20)

output_lexico_label = tkinter.Label(user_outputs_frame, text="LEXICO")
output_lexico_label.grid(row=0, column=0)

text_output_lexico = tkinter.Text(user_outputs_frame,
                                  width=40,
                                  height=9,
                                  bg="light green")
text_output_lexico.grid(row=1, column=0, padx=10, pady=10)

output_sintactico_label = tkinter.Label(user_outputs_frame, text="SINTACTICO")
output_sintactico_label.grid(row=0, column=1)

text_output_sintactico = tkinter.Text(user_outputs_frame,
                                      width=40,
                                      height=9,
                                      bg="light green")
text_output_sintactico.grid(row=1, column=1, padx=10, pady=10)

output_sementico_label = tkinter.Label(user_outputs_frame, text="SEMANTICO")
output_sementico_label.grid(row=0, column=2)

text_output_semantico = tkinter.Text(user_outputs_frame,
                                     width=40,
                                     height=9,
                                     bg="light green")
text_output_semantico.grid(row=1, column=2, padx=10, pady=10)

#crearemos los botones Ysrael Larco
button_interface_frame = tkinter.LabelFrame(frame, text="BUTTONS")
button_interface_frame.grid(row=2, column=0, padx=20, pady=20)

buttonRun = tkinter.Button(button_interface_frame,
                           text="RUN",
                           width=10,
                           height=2,
                           command=analisis)
buttonRun.grid(row=0, column=0, padx=10, pady=10)
buttonDebug = tkinter.Button(button_interface_frame,
                             text="DEBUG",
                             width=10,
                             height=2)
buttonDebug.grid(row=0, column=1, padx=10, pady=10)
buttonClear = tkinter.Button(button_interface_frame,
                             text="CLEAR",
                             width=10,
                             height=2,
                             command=clear_input)
buttonClear.grid(row=0, column=2, padx=10, pady=10)
window.mainloop()