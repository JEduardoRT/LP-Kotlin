import tkinter as tk
from tkinter import messagebox
from tkinter import ttk
import time
from LEXER import lexer as lx
from sintactic import parser as sx

def analisis_lexico():
    try:
        cadena=text.get(0)
        s = cadena
    except EOFError:
        print("Error: Archivo vacio")
    if s: 
        result = lx.parse(s)
        print(s)
        print(result)

def analisis_sintactico():
    try:
        cadena=text.get(0)
        s = cadena
    except EOFError:
        print("Error: Archivo vacio")
    if s: 
        result = sx.parse(s)
        print(s)
        print(result)

mainwindow = tk.Tk()
mainwindow.geometry('1400x800+60+25')
mainwindow.resizable(False,False)
mainwindow.title('Analizador Kotlin')
mainwindow.config(bg='gray')

analizer = tk.Frame(mainwindow, bg='gray', width=1400, height=800)
analizer.grid(row=1, column=1)
analizer_name = tk.Label(analizer, text='Escribe tu código', fg='black', bg='gray', font=('Bernard MT Condensed', 35))
analizer_name.grid(row=1, column=1, sticky='new')
text = tk.Text(analizer, width=115, height=45, bd=0, wrap='word', bg='white', borderwidth=1, state='normal', cursor='arrow')
text.grid(row=2, column=1, rowspan=3, sticky='sw')
scrolly = tk.Scrollbar(analizer, command=text.yview)
scrolly.grid(row=2, column=2, rowspan=3, sticky='nsw')
text.config(yscrollcommand=scrolly.set)

lexbut = tk.Button(analizer, text='Análisis léxico', width=15, command=analisis_lexico, height=2, activebackground='blue', cursor='hand2', bg='blue', bd=0).grid(row=1, column=3, sticky='new')
sintbut = tk.Button(analizer, text='Análisis sintáctico', width=15, command=analisis_sintactico, height=2, activebackground='blue', cursor='hand2', bg='blue', bd=0).grid(row=2, column=3, sticky='new')
sembut = tk.Button(analizer, text='Análisis semántico', width=15, command=analisis_sintactico, height=2, activebackground='blue', cursor='hand2', bg='blue', bd=0).grid(row=3, column=3, sticky='new')

results = tk.Text(analizer, width=55, height=20, bd=0, wrap='word', bg='white', state='normal', cursor='arrow')
results.grid(row=4, column=3, sticky='sw')
rscrolly = tk.Scrollbar(analizer, command=results.yview)
rscrolly.grid(row=4, column=4, sticky='nsw')
results.config(yscrollcommand=rscrolly.set)

mainwindow.mainloop() 