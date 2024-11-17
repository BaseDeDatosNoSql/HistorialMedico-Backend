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
10. [Implementación con Docker](#docker)
11. [Implementación con Jenkins](#Jenkins)

## **Formato de Intercambio de Datos**

El proyecto utiliza **JSON** como formato principal para el intercambio de datos en las APIs REST. Cada solicitud y respuesta sigue el siguiente formato:

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

<br>

---
---
---

<br>

## **Docker**
1. [Instalación](#instalación-docker)
2. [Configuración](#configuración-docker)
3. [Ejecución](#ejecución-docker)
4. [Troubleshooting](#troubleshooting-docker)

## **Instalación Docker**

Descargar de la [web de Docker](https://www.docker.com/) Docker Desktop en la versión correspondiente para el OS utilizado (*en éste caso AMD64 Windows 11*). También se puede utilizar un gestor de paquetes como Chocolatey o Scoop.

## **Configuración Docker**

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

El procedimiento consta en levantar el servicio Docker desde Powershell con privilegios de Administrador y luego continuar con el procedimiento normal.

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
    docker-compose up -d
````
<br>

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
2. Creación de Usuario Administrador (opcional)
    - Se puede definir un usuario, contraseña y correo electronico para el administrador de Jenkins
3. Finalización de configuracion
    - Se completa el asistente y accedemos al panel principal de Jenkins.

## Configuracion de herramientas en Jenkins (Maven)
1. Instalacion de maven en el equipo local

Procedemos a descargar apache maven desde su sitio oficial: https://maven.apache.org/download.cgi para este obligatorio usamos "apache-maven-3.9.9-bin.zip". 
Posterior a eso extraemos el archivo comprimido a una carpeta, en nuestro caso: "C:\apache-maven-3.9.9"

2. Configurar Maven como variable local en Jenkins
- Vamos a Manage Jenkins / Global Tool Configuration
- En la sección Maven, seleccionamos "Add Maven":
    - Name: Maven-3.9.9
    - Install automatically: desactivado.
    - Path to Maven:la ruta donde descomprimos el archivo "apache-maven-3.9.9-bin.zip".
## Nota importante: variable del sistema
- Si Jenkins no detecta Maven, es posible que no este sincronizado las variables de entorno.
- Solucion: Reiniciar el servicio de Jenkins, en caso de que sea necesario iniciar sesion en el servicio, el usaurio a ingresar es el configurado en Maven, en nuestro caso Admin.

## Crear y configurar un Trabajo en Jenkins
1. Crear el job.
    - Seleccionar New Item desde el panel principal.
    - Ingresamos el nombre del proyecto: HistorialMedico-Backend.
    - Seleccionamos Freestyle Project.
2. Configurar el repositorio Git.
    - Seleccionamos GitHub project, donde nos pediran la url del proyecto: https://github.com/BaseDeDatosNoSql/HistorialMedico-Backend/
    - En la seccion Source Code Managemnt seleccionamos git, donde tenemos que ingresar la url del repositorio de: https://github.com/BaseDeDatosNoSql/HistorialMedico-Backend.git

3. Configurar el disparador.
    - En la seccion Build triggers. seleccionamos Poll SCM.
    - Se habilitara un campo en el que ingresaremos:
    ````md
    H/5 * * * *
    ````
    - Esta configuracion hara que Jenkins sincronice cada 5 minutos. 

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
2. Cuando GitHUb detecta un cambio en el repositorio, Jenkins disparara autoamtiamente la construcccion y pruebas.


</br>



