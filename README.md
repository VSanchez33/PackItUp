# Pack It Up      

## Project Structure
- `src/` – Source code for the application
- `pom.xml` – Maven configuration file
- `target/` – Compiled output (generated after build)
- `javafx-sdk-23.0.2/` – JavaFX SDK

## Running Pack It Up
### Requirements 
* A Unix/Linux based system (not tested on Windows)
* Java 23+
* Maven (to generate JAR files)

### 1. Clone the Repository
```bash
git clone https://github.com/NMSU-CS-CS371/cs371-sp2025-teamproject-pack-it-up.git 
```
> **Note**: All commands below should be run from the directory PackItUp
```bash
mvn clean package
```

### 3. Running the App
```bash
java --module-path javafx-sdk-23.0.2/lib --add-modules javafx.controls,javafx.fxml -jar target/Main-1.0-SNAPSHOT.jar
```

### [Wiki Home Page](https://github.com/NMSU-CS-CS371/cs371-sp2025-teamproject-pack-it-up/wiki)
All of the information about Pack It Up can be found in the wiki!

## User Guide
Need Help? Here is a user guide to get your packing started!

[Pack It Up User Guide.pdf](https://github.com/user-attachments/files/20021868/Pack.It.Up.User.Guide.pdf)


