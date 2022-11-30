import tkinter as tk
from tkinter import messagebox
from tkinter import ttk
import time
import LEXER as lx
import main as sx
def analisis_lexico():
    try:
        file = open("source.txt")
        cadena=file.read()
        file.close()
        s = cadena
    except EOFError:
        print("Error: Archivo vacio")
    if s: 
        lx.validaRegla(s)

def analisis_sintactico():
    try:
        file = open("source.txt")
        cadena=file.read()
        file.close()
        s = cadena
    except EOFError:
        print("Error: Archivo vacio")
    if s: 
        sx.validaRegla(s)

mainwindow = tk.Tk()
mainwindow.geometry('1400x800+60+25')
mainwindow.resizable(False,False)
mainwindow.title('Analizador Kotlin')
mainwindow.config(bg='gray')

analizer = tk.Frame(mainwindow, bg='gray', width=1400, height=800)
analizer.grid(row=1, column=1)
analizer_name = tk.Label(analizer, text='Escribe tu código', fg='black', bg='gray', font=('Bernard MT Condensed', 35))
analizer_name.grid(row=1, column=1, sticky='new')
text = tk.Text(analizer, width=200, height=45, bd=0, wrap='word', bg='white', state='normal', cursor='arrow')
text.grid(row=2, column=1, columnspan=2, rowspan=3, sticky='sw')
scrolly = tk.Scrollbar(analizer, command=text.yview)
scrolly.grid(row=2, column=2, sticky='nsw')
text.config(yscrollcommand=scrolly.set)

enter = tk.Button(analizer, text='Análisis léxico', width=15, command=analisis_lexico, height=20, activebackground='blue', cursor='hand2', bg='blue', bd=0).grid(row=1, column=3, sticky='ne')
enter = tk.Button(analizer, text='Análisis sintáctico', width=15, command=analisis_sintactico, height=20, activebackground='blue', cursor='hand2', bg='blue', bd=0).grid(row=2, column=3, sticky='ne')
enter = tk.Button(analizer, text='Análisis semántico', width=15, command=analisis_sintactico, height=20, activebackground='blue', cursor='hand2', bg='blue', bd=0).grid(row=3, column=3, sticky='ne')

results = tk.Text(analizer, width=100, height=20, bd=0, wrap='word', bg='white', state='normal', cursor='arrow')
results.grid(row=4, column=3, sticky='sw')
rscrolly = tk.Scrollbar(analizer, command=results.yview)
rscrolly.grid(row=4, column=4, sticky='nsw')
results.config(yscrollcommand=rscrolly.set)

mainwindow.mainloop() 