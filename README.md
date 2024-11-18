# Historial Médico Backend

Este proyecto es un sistema de backend que permite gestionar el historial médico de pacientes. 
El backend está construido con **Spring Boot** y utiliza **MongoDB** como base de datos para almacenar los registros médicos. 
El sistema expone servicios RESTful para realizar operaciones y consultas específicas.

La colección de Postman para interactuar con esta API está disponible públicamente. Puedes acceder a ella haciendo clic en el siguiente enlace:
[Ver colección en Postman]https://www.postman.com/technical-explorer-93498435/public-api-workspace

Elegimos MongoDb ya que el backend del proyecto fue desarrollado con Spring Boot, que cuenta con soporte nativo y herramientas robustas para interactuar con MongoDB a través del Spring Data MongoDB. Permitió una integración más rápida y directa con las funcionalidades requeridas, como operaciones CRUD y almacenamiento de documentos estructurados en JSON.

## **Tabla de Contenidos**
1. [Formato de Intercambio de Datos](#formato-de-intercambio-de-datos)
2. [Descripción de URL de los Servicios](#descripción-de-url-de-los-servicios)
3. [Instalación](#instalación)
4. [Configuración](#configuración)
5. [Plataformas Utilizadas](#plataformas-utilizadas)
6. [Lenguajes Utilizados](#lenguajes-utilizados)
7. [Base de Datos](#base-de-datos-utilizada)
8. [Implementación con Docker](#Dockerización-del-Proyecto-(Backend-y-Base-de-Datos))
9. [Implementación con Jenkins](#Implementacion-de-Jenkins) 

## **Formato de Intercambio de Datos**

El proyecto utiliza **JSON** (JavaScript Object Notation) como formato principal para el intercambio de datos en las APIs REST. <Cada solicitud y respuesta sigue el siguiente formato:>

## **Descripción de URL de los Servicios**

El sistema expone varias URL para interactuar con el sistema de registros médicos. A continuación se detallan las principales:

- GET	/api/registros	Obtener todos los registros médicos
- GET	/api/registros/{id}	Obtener un registro médico por su ID
- POST	**http://localhost:8080/api/pacientes**	: AGREGAR PACIENTE
- POST	**http://localhost:8080/api/registros/{{ciPaciente}}**	: AGREGAR REGISTRO MEDICO asociado a un paciente
- GET **http://localhost:8080/api/registros/historial/{{ciPaciente}}?** : CONSULTAR HISTORIAL MEDICO por CI del paciente, con paginación
- PUT	/api/registros/{id}	: Actualizar un registro médico existente
- DELETE	/api/registros/{id}	Eliminar un registro médico por su ID
- GET	**http://localhost:8080/api/registros/criterios?**	: OBTENER REGISTROS POR CRITERIO, pueden ser por tipo(CONSULTA,EXAMEN,INTERNACION), diagnostico, medico y/o institucion.
 *Parámetros de búsqueda en /api/registros/criterios*:
tipo: Filtrar por el tipo de consulta (CONSULTA, EXAMEN, INTERNACION).
diagnostico: Filtrar por diagnóstico (e.g., Gripe).
medico: Filtrar por el nombre del médico (e.g., Dr. García).
institucion: Filtrar por el nombre de la institución médica (e.g., Mutualista Cosem).

## **Instalación**

Para instalar el proyecto localmente, sigue estos pasos:
1. Clonar el repositorio de GitHub: <br>
  *git clone https://github.com/usuario/historial-medico-backend.git* <br>
  *cd historial-medico-backend*
2. Asegurarse de tener Java 17 instalado.
3. Instalar las dependencias de Maven con: *./mvnw clean install*
4. Configura las variables de entorno para conectar con MongoDB (detallado en la sección de configuración).
5. Ejecuta el proyecto con: *mvn spring-boot:run*

## **Configuración**
- Configuración de la base de datos MongoDB:
Se debe tener un servidor MongoDB corriendo.
- El archivo application.yml o application.properties se utiliza para definir las configuraciones del entorno, incluyendo la conexión a MongoDB.
En el caso de MongoDB en la nube (MongoDB Atlas), cambia el valor de uri por tu cadena de conexión.

## **Plataformas Utilizadas**
- Spring Boot: Framework para construir el backend con servicios REST.
- MongoDB: Base de datos NoSQL para almacenar los registros médicos.
- Maven: Herramienta de construcción y gestión de dependencias.

## **Lenguajes Utilizados**
- Java 17: Lenguaje principal del proyecto.
- JSON: Formato de intercambio de datos entre el cliente y el servidor.

## **Base de datos utilizada**
En éste proyecto se utilizó MongoBD ya que: 
- Es una base de datos en la que todos (en mayor o menor medida) conocemos.
- Presenta un modelo de datos flexible permitiendonos almacenar datos en estructuras variadas (anidaciones y matrices) de ser necesario.
- Posee la herramienta MongoDB Compass que facilita la visualización y gestión de los datos.
- Una gran comunidad en donde se puede encontrar infinidad de tutoriales, guías y documentación para aprender y resolver problemas más rápidamente.


<br>

---
---
---

<br>

## **Dockerización del Proyecto (Backend y Base de Datos)**
1. [Ventajas](#ventajas-en-la-utilización-de-docker)
2. [Instalación](#instalación-docker)
3. [Configuración](#configuración-docker)
4. [Ejecución](#ejecución-docker)
5. [Troubleshooting](#troubleshooting-docker)

## **Ventajas en la utilización de Docker**

Portabilidad: Se puede mover el contenedor fácilmente entre entornos sin que el entorno afecte el funcionamiento (basta con copiar la carpeta /data y el docker-composer.yml). <br>
Aislamiento: El contenedor de Docker corre de forma aislada, evitando conflictos de dependencias o configuración con otros programas.<br>
Facilidad de replicación: Se puede crear múltiples instancias idénticas del contenedor para pruebas, desarrollo o escalado en producción.<br>

## **Instalación Docker**

Descargar de la [web de Docker](https://www.docker.com/) Docker Desktop en la versión correspondiente para el OS utilizado (*en éste caso AMD64 Windows 11*). También se puede utilizar un gestor de paquetes como Chocolatey o Scoop.

## **Configuración Docker**

### Archivos necesarios

1. **Dockerfile** (para el backend):
   Ubícalo en el directorio raíz del proyecto backend.
    ````md
    FROM openjdk:17
    WORKDIR /app
    COPY target/historialmedico-backend.jar app.jar
    EXPOSE 8080
    ENTRYPOINT ["java", "-jar", "app.jar"]
    ````
 
2. **Docker-compose.yml** (para ambos servicios: backend y base de datos): Ubícalo en el directorio raíz del proyecto.
    ````
    version: '3.8'
    services:
    backend:
        build:
        context: .
        dockerfile: Dockerfile
        ports:
        - "8081:8080"
        environment:
        - SPRING_DATA_MONGODB_URI=mongodb://mongo_container:27017/historial_medico
        depends_on:
        - mongo_container

    mongo_container:
        image: mongo:8.0.1
        container_name: mongo_container
        ports:
      - "27018:27017"
        volumes:
        - ./data/db:/data/db
    ````

3. **Configuración adicional**: En [application.properties](## "..\project\src\main\resources") *(o application.yml)* del backend:
    ````md
    spring.application.name=Obligatorio
    spring.data.mongodb.uri=mongodb://mongo_container:27017/historial_medico
    ````
------

 ***DOCKERIZAR UNICAMENTE LA BASE DE DATOS***: 

Se debe crear un directorio en el cual se encontrará el archivo de configuración llamado *docker-compose.yml* y donde se almacenarán los datos persistidos, en éste ejemplo se creará en: *C:\Users\Developer1\Desktop\DOCKER* 

Así mismo también se debe crear un archivo en la ruta del proyecto llamado *Dockerfile.txt*, que contendrá la información para vincular el proyecto con Docker.
A continuación se presentan ejemplos de cada uno:

<details>
  <summary><b>Dockerfile.txt</b></summary>
 
    FROM mongo:latest
    WORKDIR /data/db
    EXPOSE 27017
<details>
        <summary><i>Descripción</i></summary>
        &emsp;&emsp;<b>FROM mongo:latest </b> <- <i> Indica utilizar la última version de Mongo. (Se puede especificar version)</i> <br>
        &emsp;&emsp;<b>WORKDIR /data/db </b> <- <i> La ubicación del directorio donde persistirán los datos dentro del contenedor.</i><br>
        &emsp;&emsp;<b>EXPOSE 27017 </b> <-<i> Puerto que expone el contenedor para conectarse desde el host.</i><br>
    </details>
</details>
<br>
<details>
  <summary><b>docker-compose.yml</b></summary>

    services:
        mongodb:
            image: mongo:8.0.1
            container_name: mongo_container
        #        environment:
        #            - MONGO_INITDB_ROOT_USERNAME=admin
        #            - MONGO_INITDB_ROOT_PASSWORD=password
            ports:
                - "27017:27017"
            volumes:
                - /c/Users/Developer1/Desktop/DOCKER/data/db:/data/db
    volumes:
        mongo_data:
            driver: local
<details>
        <summary><i>Descripción</i></summary>
    <b>services:</b><i> <- Define los contenedores (servicios) que se ejecutarán en esta configuración de Docker Compose.</i><br>
        &emsp;<b>mongodb:</b><i> <- Nombre del servicio que vamos a definir.</i><br>
            &emsp;&emsp;<b>image: mongo:8.0.1</b><i> <- Especifica la version. Docker descarga la version indicada si es que no está localmente.</i><br>
            &emsp;&emsp;<b>container_name: mongo_container</b><i> <- Nombre que nosotros elegimos para el contenedor.</i><br>
        #        &emsp;&emsp;<b>environment:</b><i> <- En ésta sección se pueden settear variables de entorno que MongoDB utilizará al iniciarse.</i><br>
        #            &emsp;&emsp;&emsp;<b>- MONGO_INITDB_ROOT_USERNAME=admin</b><i> <- Nombre de usuario para conectarse.</i><br>
        #            &emsp;&emsp;&emsp;<b>- MONGO_INITDB_ROOT_PASSWORD=password</b><i> <- Password a utilizar para conectarse.</i><br>
            &emsp;&emsp;<b>ports:</b><i> <- Define el mapeo de puertos entre el contenedor y el host.</i><br>
                &emsp;&emsp;&emsp;<b>- "27017:27017"</b><i> <- Al mapear este puerto, se puede acceder a MongoDB desde el host en localhost:27017, y las conexiones se redirigirán al contenedor</i><br>
            &emsp;&emsp;<b>volumes:</b><i> <- Define los volúmenes para persistir los datos.</i><br>
                &emsp;&emsp;&emsp;<b>- /c/Users/Developer1/Desktop/DOCKER/data/db:/data/db</b><i> <- Especifica un volumen, mapeando una carpeta del host al contenedor.</i><br>
    <b>volumes:</b><i> <- Define volúmenes a nivel global en Docker Compose, lo que significa que pueden ser utilizados por otros servicios.</i><br>
        &emsp;<b>mongo_data:</b><i> <- Nombre que le damos al volumen global</i><br>
            &emsp;&emsp;<b>driver: local</b><i> <- Indica que Docker almacenará el volumen en el sistema de archivos local del host.</i><br>
    </details>
</details>




## **Ejecución Docker**

Pasos para levantar el backend y la base de datos:

1- Inicia Docker Desktop.

2- Navega al directorio donde se encuentra el archivo docker-compose.yml.

3- Construye y ejecuta los servicios en segundo plano: **docker-compose up -d --build**

*Esto construirá la imagen del backend usando el Dockerfile e iniciará el contenedor de MongoDB.*

4- Verificar que los contenedores estén corriendo: **docker ps**

*Salida esperada:*

|CONTAINER ID|   IMAGE|               STATUS|      PORTS|
|------------|--------|---------------------|-----------|
|5937bf099539|mongo:8.0.1|            Up 2 minutes|0.0.0.0:27018->27017/tcp|
|7d14beb97d43|historialmedico-backend|Up 2 minutes|0.0.0.0:8081->8080/tcp|

5- Acceder al backend desde http://localhost:8081 y asegúrarse de que esté conectado a la base de datos MongoDB en el puerto 27018.

**Detener los servicios**

Detener ambos servicios: **docker-compose down**
 
--------

 
***UNICAMENTE LA BASE DE DATOS***:

El procedimiento consta en: primero ejecutar Docker Desktop y luego (en el directorio donde se encuentra el docker-compose.yml) levantar el servicio Docker desde Powershell con privilegios de Administrador y luego continuar con el procedimiento normal.

1. ### Ejecutar Docker Compose
````md
    docker-compose up -d
````
&emsp;&emsp;&emsp;Esto iniciará el contenedor en segundo plano (-d), descargando la imagen de MongoDB si es necesario.

2. ### Verificar que MongoDB está corriendo
````md
    docker ps
````
&emsp;&emsp;&emsp; Lo que debe mostrar algo como lo de a continuación

|CONTAINER ID|IMAGE|COMMAND|CREATED|STATUS|PORTS|NAMES|
|------------|-----|-------|-------|------|-----|-----|
|2a07757a35fe|mongo:8.0.1|"docker-entrypoint.s…"|15 hours ago|Up 15 hours|0.0.0.0:27017->27017/tcp|mongo_container|

3. ### Conectar a la Base de Datos

&emsp;&emsp;&emsp; Ahora cuando se corra la DB y se le empiece a enviar solicitudes aparecerá una carpeta llamada ***data*** en el directorio donde se encuentra ***docker-compose.yml***.

4. ### Desconectar la Base de Datos

&emsp;&emsp;&emsp; Para desconectar la DB basta con correr:
````md
    docker-compose down
````
&emsp;&emsp;&emsp; A tener en cuenta de **no** pasar el parámetro ***-v*** ya que ésto eliminará los datos persistidos.

## Troubleshooting Docker

Si luego de la instalación Docker no inicia mostrando un error *"deploying WSL2 distributions..."* hay que habilitar la característica de Windows 11 de virtualización utilizando las herramientas de DISM:

- Desde Powershell con privilegios administrador 
````md
    dism.exe /online /enable-feature /featurename:VirtualMachinePlatform /all /norestart
 ````
 
- Si se necesitan mayor información de la conexión con el contenedor se pueden chequear los log:
````md
    docker logs NOMBRE_DEL_CONTENEDOR
````

- Se realizo cambios en docker-compose.yml y no los veo reflejados problablemente sea que para que se apliquen se debe levantar nuevamente el servicio:

````md
    docker-compose up -d --build
````
<br>

- Puerto ocupado: Si ves errores como "port is already allocated", asegúrate de que los puertos especificados en docker-compose.yml no estén en uso.

## Notas Adicionales
1- Directorio persistente de datos:
MongoDB persiste los datos en **./data/db** 

*Este directorio debe estar accesible en el sistema.*

2- Tener la carpeta target del backend generada: **mvn clean package** 

*Esto genera el archivo historialmedico-backend.jar en el directorio target.*

3- URL de MongoDB: Si se cambia los puertos en **docker-compose.yml**, actualizar la URL de MongoDB en el archivo de configuración del backend.



## **Implementacion de Jenkins**
1.  [Uso de Jenkins y automatización del Backend](#Uso-de-Jenkins-y-automatización-del-Backend)
2. [Instalación](#Instalación-de-Jenkins)
3. [Configuración Inicial](#Configuración-Inicial-de-Jenkins)
4. [Configuracion de herramientas en Jenkins (Maven)](#Configuracion-de-herramientas-en-Jenkins-(Maven))
5. [Crear y configurar un Trabajo en Jenkins](#Crear-y-configurar-un-Trabajo-en-Jenkins-Jenkins)
6. [Verificación y ejecución automática](#Verificación-y-ejecución-automática)



## **Uso de Jenkins y automatización del Backend**
Jenkins es utilizado para automatizar las siguientes tareas en este proyecto:

- Construcción del código: Compilar el proyecto con Maven.
- Ejecución de pruebas unitarias: Ejecutar los tests definidos en el proyecto y publicar los resultados.
- Generación del archivo ejecutable: Generar el archivo .jar con Maven.
- Verificación continua: Detectar cambios en el repositorio de GitHub y disparar automáticamente los pasos anteriores.

## Instalación de Jenkins


1. Instalación de Jenkins
Descargar Jenkins:
Se puede realizr la descarga de Jenkins desde su sitio oficial: https://www.jenkins.io/download/

2. Instalación:
Una vez descargada el ejecutable (msi en el caso usado), se procede a iniciar el ejecutable y seguir los pasos de la instalacion local en el equipo. Durante la instalación, Jenkins configurará un servicio de Windows.
Al finalizar, Jenkins estará disponible en:
http://localhost:8080.

3. Desbloquear Jenkins:

Accediendo desde el navegador al siguiente enlace: http://localhost:8080.
Nos pedira un usuario y contraseña por defecto, el usuuario es: Admin y la contraseña se encuentra por defecto en la siguiente direccion

````md
    C:\Program Files (x86)\Jenkins\secrets\initialAdminPassword
````

## Configuración Inicial de Jenkins

1. Instalación de Plugins Recomendados
    - Posterior a la instalacion de Jenkins es necesiario instalar los plugins recomendados.
    - Jenkins estalará automaticamente:
        - Git Plugins.
        - Maven Integration.
        - JUnit Plugin.
        - Otros.
  <br>Nota: en caso de que no se instale correctamente el plugin de Maven, se tendra que ir en Jenkins: Panel de control/Plugins/Avaliable Plugins y buscar Maven Integration, una vez encontrada instalarla.
2. Creación de Usuario Administrador (opcional)
    - Se puede definir un usuario, contraseña y correo electronico para el administrador de Jenkins
3. Finalización de configuración
    - Se completa el asistente y accedemos al panel principal de Jenkins.

## Configuración de herramientas en Jenkins (Maven)
1. Instalación de maven en el equipo local

Procedemos a descargar apache maven desde su sitio oficial: https://maven.apache.org/download.cgi para este obligatorio usamos "apache-maven-3.9.9-bin.zip". 
Posterior a eso extraemos el archivo comprimido a una carpeta, en nuestro caso: "C:\apache-maven-3.9.9"

2. Configurar Maven como variable local en Jenkins
- Vamos a Manage Jenkins / Global Tool Configuration
- En la sección Maven, seleccionamos "Add Maven":
    - Name: Maven-3.9.9
    - Install automatically: desactivado.
    - Path to Maven:la ruta donde descomprimos el archivo "apache-maven-3.9.9-bin.zip".
## Nota: variable del sistema
- Si Jenkins no detecta Maven, es posible que no este sincronizado las variables de entorno.
- Solucion1: Reiniciar el servicio de Jenkins, en caso de que sea necesario iniciar sesion en el servicio, el usaurio a ingresar es el configurado en Maven, en nuestro caso Admin.
- Solucion2: Dentro de la interfaz de Jenkins ir a: Admnistrar Jenkinks.
  - Ingresar a System.
  - Vamos a la seccion variables globales.
  - Agregamos una nueva variable global "PATH".
  - Se le asigna a dicha variable el siguiente valor: C:\apache-maven-3.9.9\bin;C:\Program Files\Java\jdk-17\bin  

## Crear y configurar un Trabajo en Jenkins
1. Crear el job.
    - Seleccionar New Item desde el panel principal.
    - Ingresamos el nombre del proyecto: HistorialMedico-Backend.
    - Seleccionamos Freestyle Project.
2. Configurar el repositorio Git.
    - Seleccionamos GitHub project, donde nos pediran la url del proyecto: https://github.com/BaseDeDatosNoSql/HistorialMedico-Backend/
    - En la sección Source Code Managemnt seleccionamos git, donde tenemos que ingresar la url del repositorio de: https://github.com/BaseDeDatosNoSql/HistorialMedico-Backend.git

3. Configurar el disparador.
    - En la sección Build triggers. seleccionamos Poll SCM.
    - Se habilitará un campo en el que ingresaremos:
    ````md
    H/5 * * * *
    ````
    - Esta configuración hara que Jenkins sincronice cada 5 minutos. 

4. Configurar Maven como paso de construcción.
    - En la seccion de Build, seleccionamos Add build step.
    - Seleccionamos Invoke top-level Maven targets.
    - Eso hara que se habilite un campo "Goals" donde ingresamos:
    ````md
    clean package
    ````
    - La funcion de este paso es limpiar archivos previos, compilara el proyecto y generara un archivo.jar.

5. Publicar los resultados de pruebas.
    - Vamos a la seccion Post-build Actions.
    - Seleccionamos Publich Junit test result report.
    - En el campo Test Report XML ingresamos: 
    ````md
    **/target/surefire-reports/*.xml
    ```` 
6. Guardar y ejecutar.
    - Seleccionamos "guardar".
    - Ahora nuestro "Trabajo" aparece en el panel principal, donde podemos seleccionar Build Now para ejecutarlo.

## Verificación y ejecución automática
1. Despues de configurar el Trabajo, verifacamos el log desde la consola de salida para identificar errores si los hubiera.
2. Cuando GitHUb detecta un cambio en el repositorio, Jenkins disparara autocamtiamente la construccción y pruebas.

## Nota: Modificacion de puerto 
En caso de que se ejecuten la app java y Jenkins a la vez, se tiene que optar por una de las dos soluciones:
1. Configuracion de Proxy para que se pueda reutilizar el mismo puerto a la vez.
2. Cambiar los puertos que se usan (el de la app java o el que usa el propio Jenkins)
En este laboratorio se opto por la segunda opción, siendo Jenkins el software a que le cambiara el numero de puerto, en nuestro caso seleccionamos el 8082.
Para lo mismo se debe realizar los siguientes pasos:
1. Ir a  C:\Program Files (x86)\Jenkins\jenkins.xml y editar el archivo.
2. En la linea donde declara: <arguments>--httpPort=8080</arguments> cambiar al numero de puerto 8082.
3. Guardar los cambios.
4. Reiniciar el servicio de Jenkins o reiniciar el ordenador que tenga la instalación.
</br>



