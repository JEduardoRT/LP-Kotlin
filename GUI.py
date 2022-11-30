import tkinter

def take_input():
    input = text_input.get("1.0",'end-1c')
    text_output.insert('1.0',input+"\n")



#se crea la venta de interface Ysrael Larco
window = tkinter.Tk()

mainlabel = tkinter.Label(text="KOTLIN COMPILADOR")
mainlabel.pack()

frame = tkinter.Frame(window)
frame.pack()

#creamos la caja de texto donde se almacenara el input Ysrael larco
user_input_frame = tkinter.LabelFrame(frame, text="INPUT")
user_input_frame.grid(row=0,column=0,padx=20,pady=20)

text_input = tkinter.Text(user_input_frame, width=40,height=5,bg = "light cyan")
text_input.grid(row=0,column=0,padx=10,pady=10)

user_output_frame = tkinter.LabelFrame(frame, text="OUTPUT")
user_output_frame.grid(row=1,column=0,padx=20,pady=20)

text_output = tkinter.Text(user_output_frame, width=40,height=5,bg = "light green")
text_output.grid(row=0,column=0,padx=10,pady=10)

#crearemos los botones Ysrael Larco
button_interface_frame = tkinter.LabelFrame(frame, text="BUTTONS")
button_interface_frame.grid(row=2,column=0,padx=20,pady=20)

buttonRun = tkinter.Button(button_interface_frame,text="RUN",width=10,height=2, command=take_input)
buttonRun.grid(row=0,column=0,padx=10,pady=10)
buttonDebug = tkinter.Button(button_interface_frame,text="DEBUG",width=10,height=2)
buttonDebug.grid(row=0,column=1,padx=10,pady=10)
window.mainloop()

