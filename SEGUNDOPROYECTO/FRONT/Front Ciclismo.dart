
import 'package:flutter/material.dart';
import 'dart:async';
import 'dart:math';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Ciclismo'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  final String title;

  const MyHomePage({
    Key? key,
    required this.title,
  }) : super(key: key);

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> { 

  
  @override
  Widget build(BuildContext context) {
    
    
    return MaterialApp(
      home: DefaultTabController(
        length: 4,
        child: Scaffold(
          appBar: AppBar(
            bottom: const TabBar(
              labelColor: Colors.white,
                unselectedLabelColor: Colors.white,
                indicatorSize: TabBarIndicatorSize.label,
                indicator: BoxDecoration(
                    borderRadius: BorderRadius.only(
                        topLeft: Radius.circular(10),
                        topRight: Radius.circular(10)),
                    color: Colors.green),
              tabs: [
                Tab(
                  child: Align(
                      alignment: Alignment.center,
                      child: Text("Home"),
                    ),
                ),
                Tab(
                  child: Align(
                      alignment: Alignment.center,
                      child: Text("Mercado"),
                    ),
                ),
                 Tab(
                  child: Align(
                      alignment: Alignment.center,
                      child: Text("Eventos"),
                    ),
                ),
                 Tab(
                  child: Align(
                      alignment: Alignment.center,
                      child: Text("Mi"),
                    ),
                ),
              ],
            ),
          ),
          body:  TabBarView(
            children: [
              SingleChildScrollView(
                child: Column(
                  children: [
                    CardPost(nombre: "John Wick"),
                    CardPost(nombre: "Steve Rogers"),
                    CardPost(nombre: "Tony Star"),
                  ],
                ),
              ),
              SingleChildScrollView(
                child: Column(
                  children: [
                    CardSell(nombre: "Juan Gomez",precio:"\$215"),
                    CardSell(nombre: "Claudia Rivas",precio:"\$225"),
                    CardSell(nombre: "Rodrigo Espol",precio:"\$205"),
                  ],
                ),
              ),
              Column(
                children: [
                   Text("Comming Soon"),
                ],
              ),
              Column(
                children: [
                   Text("Comming Soon"),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class CardPost extends StatelessWidget{
  
  final String nombre;
  
  const CardPost({super.key,required this.nombre});

  @override
  Widget build(BuildContext context){
    return Container(
      margin: const EdgeInsets.only(top: 10),
      width: 400,
      height: 300,
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(10),
        boxShadow: [
          BoxShadow(
            color: Colors.grey.withOpacity(0.5),
            spreadRadius: 3,
            blurRadius: 10,
            offset: const Offset(0,3),
          ),
        ],
      ),
      child: Padding(
        padding: const EdgeInsets.symmetric(horizontal: 10),
        child: Column(
          children: [
            Container(
              margin: const EdgeInsets.only(top: 10),
              child: Row(
                children: [
                  Image.network("https://cdn-icons-png.flaticon.com/512/149/149071.png",width: 50),
                  const SizedBox(width: 10),
                  Column(
                    children: [
                      Text(this.nombre, style: TextStyle(fontWeight: FontWeight.bold),),
                      const Text("hace 4 horas", style: TextStyle(fontSize: 8),),
                    ],
                  ),
                ],
              ),
            ),
            Container(
              alignment: Alignment.centerLeft,
              margin: const EdgeInsets.only(top: 10),
              child: const Text("Solo yo y mi fuel companera recorriendo el pais"),
            ),
            Container(
              margin: const EdgeInsets.only(top: 10),
              child: Image.network("https://media.tacdn.com/media/attractions-splice-spp-674x446/0a/9e/62/de.jpg",
                                  width:380,
                                  height:160,
                                  fit: BoxFit.cover),
            ),
            Container(
              margin: const EdgeInsets.only(top: 10),
              child: Row(
                children: [
                  const Icon(
                    Icons.favorite,
                    color: Colors.pink,
                    size: 24.0,
                  ),
                  const SizedBox(width: 5),
                  const Text("15K",style: TextStyle(fontWeight: FontWeight.bold),),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class CardSell extends StatelessWidget{
  
  final String nombre;
  final String precio;
  
  const CardSell({super.key,required this.nombre,required this.precio});

  @override
  Widget build(BuildContext context){
    return Container(
      margin: const EdgeInsets.only(top: 10),
      width: 400,
      height: 300,
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(10),
        boxShadow: [
          BoxShadow(
            color: Colors.grey.withOpacity(0.5),
            spreadRadius: 3,
            blurRadius: 10,
            offset: const Offset(0,3),
          ),
        ],
      ),
      child: Padding(
        padding: const EdgeInsets.symmetric(horizontal: 10),
        child: Column(
          children: [
            Container(
              margin: const EdgeInsets.only(top: 10),
              child: Row(
                children: [
                  Image.network("https://cdn-icons-png.flaticon.com/512/149/149071.png",width: 50),
                  const SizedBox(width: 10),
                  Column(
                    children: [
                      Text(this.nombre, style: TextStyle(fontWeight: FontWeight.bold),),
                      const Text("hace 5 horas", style: TextStyle(fontSize: 8),),
                    ],
                  ),
                ],
              ),
            ),
            Container(
              margin: const EdgeInsets.only(top: 10),
              child: Row(
                children: [
                  Text("Bicicleta GW3",textAlign: TextAlign.left,),
                  const SizedBox(width: 250),
                  Text(this.precio,textAlign: TextAlign.right,),
                ],
              ),
            ),
            Container(
              margin: const EdgeInsets.only(top: 10),
              child: Image.network("https://m.media-amazon.com/images/I/81wGn2TQJeL._SX425_.jpg",
                                  width:380,
                                  height:160,
                                  fit: BoxFit.cover),
            ),
            Container(
              margin: const EdgeInsets.only(top: 10),
              child: Row(
                children: [
                  const Icon(
                    Icons.location_on,
                    color: Colors.black,
                    size: 24.0,
                  ),
                  const SizedBox(width: 5),
                  const Text("Guayaquil , Guayas",style: TextStyle(fontWeight: FontWeight.bold),),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
