Este projeto implementa um servidor de biblioteca em Java que permite gerenciar livros através de uma conexão socket. Ele inclui funcionalidades básicas como listar livros, cadastrar novos livros, alugar e devolver livros.

Pré-requisitos
- Java Development Kit (JDK) 17 ou superior instalado
- Maven 

1. Compilar o Código
Você pode compilar o código manualmente ou usando Maven:

Compilar manualmente (sem Maven):
Certifique-se de estar no diretório onde o arquivo ServidorBiblioteca.java está localizado e execute o seguinte comando para compilar:
javac ServidorBiblioteca.java

Compilar usando Maven:
Se preferir usar Maven, certifique-se de que o Maven está instalado e execute o seguinte comando no diretório raiz do projeto (onde está localizado o arquivo pom.xml):
mvn compile

2. Executar o Servidor
Depois de compilar o código, você pode iniciar o servidor da biblioteca. 

Executar manualmente:
java ServidorBiblioteca caminho/para/seu/arquivo.json 12345
- Substitua caminho/para/seu/arquivo.json pelo caminho real do arquivo JSON que contém os dados da biblioteca.
- Substitua 12345 pela porta desejada onde o servidor deverá escutar por conexões.

3. Interagir com o Servidor
Após iniciar o servidor, você pode interagir com ele usando um cliente socket que envie comandos adequados para listar livros, cadastrar novos livros, alugar e devolver livros.
