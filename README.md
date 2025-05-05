# Bicycle Rack Management System (BRMS)

El programa es una aplicación CLI que permite gestionar un bicicletero. Permite al administrador ingresar bicicletas, ver los registros, marcar bicicletas como retiradas y en caso de error, actualizar la información de u n registro.

## Estructura del programa

EL programa está compuesto por cinco clases:

### Student

Una data class que representa a un estudiante, cuyos atributos son:
- `id (String)`: matrícula del estudiante (es un String porque el rut puede contener una K)
- `name`: nombre del estudiante

### Record

Una data class que representa un registro, cuyos atributos son:

- `student (Student)`: el estudiante que ingresó la bicicleta.
- `bicycleDescription (String)`: descripción de la bicicleta.
- `checkIn (LocalDateTime)`: fecha y hora de ingreso de la bicicleta. Inicialmente es la fecha y hora del momento de la creación del registro.
- `checkOut (LocalDateTime)`: fecha y hora del retiro de la bicicleta. Inicialmente es null.

### BicycleRackService

Una clase que se encarga de las operacions del bicicletero y almacena los registros. Sus métodos principales son:
- `checkIn`: crea un registro con la información del estudiante, la descripción de la bicicleta, la fecha y hora de ingreso, y lo almacena en la lista de registros.
- `getRecords`: devuelve la lista de registros.
- `checkOutByStudentId*`: busca un registro por el id del estudiante y lo marca como retirado.
- `checkOutByRecordId*`: busca un registro por el id del registro y lo marca como retirado.

- `updateStudentId`: reemplaza el id del estudiante de un registro con un nuevo id ingresado por el usuario.
- `updateStudentName`: reemplaza el nombre del estudiante de un registro con un nuevo nombre ingresada por el usuario.
- `updateBicycleDescription`: reemplaza la descripción de la bicicleta de un registro con una nueva descripción ingresada por el usuario.

*Los métodos `checkOut` no eliminan el registro, solo le añade la fecha y hora de retiro para mantener un historial de los registros.

### BicycleRackUI

Una clase que se encarga de mostrar mensajes en consola y de interactuar con el usuario. Sus métodos principales son:

- `menu`: un loop que muestra las opciones del menú, recibe la opción del usuario, y llama al método correspondiente a la opción de la clase `BicycleRackService`. El loop continua hasta que el usuario ingresa la opción para salir.
- `checkInBicycle`: pide al usuario ingresar la matrícula del estudiante, el nombre del estudiante y la descripción de la bicicleta. Luego llama al método `checkIn` de la clase `BicycleRackService` para crear y almacenar el registro.
- `listRecords`: recibe la lista de registros de `BicycleRackService` y los muestra en consola.
- `checkOutBicycle`: primero pide al usuario si quiere buscar el registro a retirar por el id del registro o por el id del estudiante, luego llama al método `checkOut` correspondiente de `BicycleRackService` para marcar el registro como retirado.
- `updateRecord`: primero le pide al usuario que dato del registro quiere actualizar, luego le pide al usuario el id del registro, el nuevo dato y llama al método correspondiente al dato que se quiera actualizar de `BicycleRackService`.

### BicycleRackApp

Una clase que se encarga de unir el servicio y la interfaz de usuario y de ejecutar el programa.

## Como ejecutar el programa

1. Clonar el repositorio
    ```bash
    git clone https://github.com/fcocaceresc/bicycle-rack-app
    ```
2. Navegar a la carpeta del proyecto
   ```bash
   cd bicycle-rack-app
   ```
3. Generar el archivo .jar
   ```bash
   mvn package
   ```
4. Ejecutar el programa
   ```bash
   java -jar target/BicycleRackApp-1.0-SNAPSHOT.jar
   ```
