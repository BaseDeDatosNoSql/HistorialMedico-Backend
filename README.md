# Historial Médico Backend

Este proyecto es un sistema de backend que permite gestionar el historial médico de pacientes. 
El backend está construido con **Spring Boot** y utiliza **MongoDB** como base de datos para almacenar los registros médicos. 
El sistema expone servicios RESTful para realizar operaciones y consultas específicas.


## **Tabla de Contenidos**
1. [Formato de Intercambio de Datos](#formato-de-intercambio-de-datos)
2. [Descripción de URL de los Servicios](#descripción-de-url-de-los-servicios)
3. [Instalación](#instalación)
4. [Configuración](#configuración)
5. [Plataformas Utilizadas](#plataformas-utilizadas)
6. [Lenguajes Utilizados](#lenguajes-utilizados)
7. [Bases de Datos Utilizadas](#bases-de-datos-utilizadas)
8. [Justificación de la Base de Datos Elegida](#justificación-de-la-base-de-datos-elegida)
9. [Diseño del Esquema](#diseño-del-esquema)

## **Formato de Intercambio de Datos**

El proyecto utiliza **JSON** como formato principal para el intercambio de datos en las APIs REST. Cada solicitud y respuesta sigue el siguiente formato:

## **Descripción de URL de los Servicios**

El sistema expone varias URL para interactuar con el sistema de registros médicos. A continuación se detallan las principales:

- GET	/api/registros	Obtener todos los registros médicos
- GET	/api/registros/{id}	Obtener un registro médico por su ID
- POST	/api/registros	Crear un nuevo registro médico
- PUT	/api/registros/{id}	Actualizar un registro médico existente
- DELETE	/api/registros/{id}	Eliminar un registro médico por su ID
- GET	/api/registros/criterios	. Buscar registros médicos por criterios (tipo, diagnóstico, etc.)
- *Parámetros de búsqueda en /api/registros/criterios*:
tipo: Filtrar por el tipo de consulta (CONSULTA, EXAMEN, INTERNACION).
diagnostico: Filtrar por diagnóstico (e.g., Gripe).
medico: Filtrar por el nombre del médico (e.g., Dr. García).
institucion: Filtrar por el nombre de la institución médica (e.g., Mutualista Cosem).

## **Instalación**

Para instalar el proyecto localmente, sigue estos pasos:
1. Clona el repositorio de GitHub:
  git clone https://github.com/usuario/historial-medico-backend.git
  cd historial-medico-backend
2. Asegúrate de tener Java 17 instalado.
3. Instala las dependencias de Maven con ./mvnw clean install
4. Configura las variables de entorno para conectar con MongoDB (detallado en la sección de configuración).
5. Ejecuta el proyecto con mvn spring-boot:run

## **Configuración**
- Configuración de la base de datos MongoDB:
Debes tener un servidor MongoDB corriendo.
- El archivo application.yml o application.properties se utiliza para definir las configuraciones del entorno, incluyendo la conexión a MongoDB.
En el caso de MongoDB en la nube (MongoDB Atlas), cambia el valor de uri por tu cadena de conexión.

## **Plataformas Utilizadas**
- Spring Boot: Framework para construir el backend con servicios REST.
- MongoDB: Base de datos NoSQL para almacenar los registros médicos.
- Maven: Herramienta de construcción y gestión de dependencias.

## **Lenguajes Utilizados**
- Java 17: Lenguaje principal del proyecto.
- JSON: Formato de intercambio de datos entre el cliente y el servidor.
