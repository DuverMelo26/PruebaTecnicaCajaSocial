# Prueba Técnica Caja Social - Aplicación de Rick And Morty

Esta aplicación muestra una lista de personajes de rick and morty y permite crear personajes básicos localmente, usando Jetpack Compose y una arquitectura limpia basada en el patrón MVVM.

Autor: Duver Alexander Melo Mendivelso.

# Tecnologías utilizadas

- Kotlin.
- Jetpack Compose.
- ViewModel + LiveData.
- Hilt (Inyección de Dependencias).
- Navigation Compose.
- Retrofit.
- Coil (Carga de imágenes).
- Coroutines.
- Room
- Pruebas unitarias (ioMockk).
- Arquitectura limpia dividida en capas: data, domain y presentation.

# Instrucciones para ejecutar la aplicación

1. Clonar el repositorio: https://github.com/DuverMelo26/PruebaTecnicaCajaSocial
2. Abrir el proyecto en Android Studio.
3. Ejecutar la aplicación desde Android Studio.

# Explicación de las decisiones técnicas

1. Pantalla Principal - Personajes desde API
    - Se utilizó la API pública de Rick and Morty para obtener un listado paginado de personajes y detalles de cada uno.
    - La pantalla principal CharactersScreen se desarrolló con Jetpack Compose.
    - Se implementó un campo TextField que permite filtrar la lista en tiempo real según el nombre, tipo o género, como se solicitó en la prueba.
    - Se agregó un botón en la parte inferior derecha que lleva al usuario a la pantalla de creación de personajes locales.
    - Se implementó un menú desplegable en la parte superior que permite ordenar la lista de personajes por fecha de creación, ya sea del más reciente al más antiguo o viceversa, tal como se solicitó en el requerimiento.
    - Se agregó un menú de categorías por especie de los personajes para que el usuario pueda filtrar los resultados provenientes de la API, cumpliendo también con un requerimiento de la prueba.

2. Pantalla Personajes Locales
    - Se creó una segunda pantalla para mostrar los personajes creados por el usuario.
    - Esta pantalla también está construida con Jetpack Compose y obtiene los datos desde una base de datos local implementada con Room.
    - Se implementó un campo TextField que permite filtrar la lista en tiempo real según el nombre del personaje.
    - Se agregó un botón en la parte inferior derecha que lleva al usuario a la pantalla de consulta de personajes desde la API de Rick and Morty.
    - Se implementó un botón en la parte inferior izquierda que despliega un diálogo para crear nuevos personajes.
    - Se añadieron dos botones en cada tarjeta de personaje local, permitiendo editar el nombre o eliminar el personaje, como se solicitó en la prueba técnica.

3. Integración de SDK Externos
    - Las bibliotecas de terceros usadas fueron:
        * Retrofit: Usada para hacer las llamadas HTTP
        * Convert-gson: Permite convertir los JSON de las llamadas HTTP de Retrofit en objetos
        * Hilt: Libreria que brinda las etiquetas y clases necesarias para implementar inyección de dependencias entre clases.
        * Coil: Permite cargar imagenes de URLs en Jetpack compose
        * Navigation Compose: Permite la navegación entre pantallas Compose
        * ioMockk: Libreria que permite crear objetos Mockeados para las pruebas unitarias
        * Room: Usada para crear la base de datos local.
    - La librería Coil permitió cargar imágenes en Jetpack Compose y se utilizó para mostrar las imágenes de cada personaje obtenidas desde la API de Rick and Morty.

4. Test
- Se realizaron pruebas unitarias en las capas Domain, Data y ViewModel.
- Como se solicitó en la prueba técnica, se implementaron pruebas unitarias tanto para los consumos de API como para las operaciones CRUD con Room.
