# DiffTool
The `DiffTool` is a utility class designed to identify the differences between two objects of the same type. It handles nested properties, lists, and even objects within lists, all with precise tracking to identify exactly where changes occurred.

- [Prerequisites](#prerequisites)
    - [Java 17 Installation (OpenJDK)](#java-17-installation-openjdk)
        - [Ubuntu/Debian](#ubuntudebian)
        - [Fedora](#fedora)
        - [CentOS/RHEL](#centosrhel)
        - [macOS](#macos)
        - [Microsoft Windows](#microsoft-windows)
    - [Maven Installation](#maven-installation)
        - [Ubuntu/Debian](#ubuntudebian-1)
        - [Fedora](#fedora-1)
        - [CentOS/RHEL](#centosrhel-1)
        - [macOS](#macos-1)
        - [Microsoft Windows](#microsoft-windows-1)
- [How It Works](#how-it-works)
- [How to Use](#how-to-use)
- [Development Time](#development-time)
- [Maven Commands](#maven-commands)


## Prerequisites

### Java 17 Installation (OpenJDK)

#### Ubuntu/Debian:

```bash
sudo apt update
sudo apt install openjdk-17-jdk
```

#### Fedora
```bash
sudo dnf install java-17-openjdk-devel
```

#### CentOS/RHEL:
First, enable the EPEL repository:
```bash
sudo yum install epel-release
```
Then, install Java:
```bash
sudo yum install epel-release
```

#### macOS:
Using [Homebrew](https://brew.sh/):
```bash
brew update
brew install openjdk@17
```

After installation, you might want to set Java 17 as your default JDK or configure your IDE to use it for this project.

#### Microsoft Windows:
Visit the [Microsoft Build of OpenJDK](https://learn.microsoft.com/en-us/java/openjdk/download#openjdk-17) website.
Then download the Windows x64 MSI file.
Run the MSI installer and follow the instructions.

### Maven Installation

#### Ubuntu/Debian
```bash
sudo apt update
sudo apt install maven
```

#### Fedora
```bash
sudo dnf install maven
```

#### CentOS/RHEL:
First, enable the EPEL repository:

```bash
sudo yum install epel-release
```
Then, install Maven:

```bash
sudo yum install maven
```

#### macOS:
Using Homebrew:

```bash
brew update
brew install maven
```

#### Microsoft Windows:
1. Download Maven from [the official website](https://maven.apache.org/download.cgi).
2. Extract the archive to a directory of your choice.
3. Add the bin directory of the extracted location to your PATH environment variable.
4. Optionally, set the M2_HOME environment variable to the directory where you extracted Maven.
5. Verify the installation with:

```bash
mvn -v
```
This should display the version of Maven installed.

## How It Works
- For simple properties: A direct comparison checks if there's any change. If there's a change, it tracks the property's name, the previous value, and the current value.

- For nested properties: The property name is expressed in dot notation to denote the path.

- For lists: The utility checks for additions and removals. If an object within a list is updated, the property name includes the list item's id, which must be denoted by the field name "id" or annotated with @AuditKey.


## How to Use
1. Annotate the ID field of list items using `@AuditKey` or name it 'id'.
2. Call the `diff` method with the previous and current states of an object as arguments.

## Development Time
Creating this utility, testing, and documentation took approximately 4 hours.

## Maven Commands

For developers unfamiliar with Maven, here are some common commands and their descriptions:

- **`mvn clean`**: Removes the `target/` directory created previously during the build. This will clean up all compiled classes and resources.

- **`mvn compile`**: Compiles the source code of the project.

- **`mvn test`**: Runs the tests for the project using the test framework you have configured.

- **`mvn package`**: Takes the compiled code and packages it in its distributable format, such as a JAR.

- **`mvn install`**: Installs the package into the local repository, which can be used as a dependency in other projects locally.

- **`mvn verify`**: Runs any checks to verify the package is valid and meets quality criteria.

