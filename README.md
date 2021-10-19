# AndroidFoodTracker 

AndroidFoodTracker es una aplicación desarrollada en Kotlin y esta orientada a pacientes de un consultorio de nutrición.
El objetivo es que estos puedan llevar un trackeo de las comidas que consumen diariamente.


## Arquitectura

La aplicacion se desarrolló siguiendo la arquitectura MVVM y single-activity. 


## Autenticación

Para la autenticación se utilizó Firebase

Usuario ejemplo:

* mail : pato.mdp@gmail.com
* contraseña: 123456

Usted también podra registrar su propio usuario.


## Funcionamiento de la aplicación

La aplicación inicia con una splash screen y luego pide al usuario autentificarse.
Si es la primera vez que el usuario se loguea o se registra, la aplicacion le proporciona un formulario para que pueda ingresar sus datos.
Si no es la primera vez que se loguea, muestra la UI con los datos previamente cargados.

Si el usuario presiona en el icono de navegación puede acceder a 3 pantallas diferentes: 

1) Mis datos
2) Cargar Comida
3) Mis registros

Dentro de la seccion "Cargar comida" el usuario debe completar algunos datos obligatorios (Fecha, tipo de comida, comida principal, etc..). Hasta tanto el usuario no complete estos campos no podra hacer el registro de la comida.
Una vez que se cargue un registro, si el dispositivo esta online, la aplicación le otorgara como premio un trago que puede consumir por su buen comportamiento. Si se encuentra sin conexión no tendra tal beneficio. 

En la seccion "Mis registros", el usuario podria ver un listado de las comidas cargadas, diferenciadas segun su tipo (Desayuno, almuerzo, merienda y cena).


## Herramientas

- Para el consumo de la API de los tragos se utilizo retrofit y glide.

- Para la interfaz gráfica se uso la libreria MaterialUI.

- La base de datos implementada fue Firestore.


## Observaciones

AndroidFoodTracker fue desarollada en pocos días y no ha sido optimizada para todos los tamaños de pantalla. Se recomienda testear en un dispositivo de 1080x2280 px.


## Autor ✒️

* **Patricio Cabo**  - [github](https://github.com/patocabo)
