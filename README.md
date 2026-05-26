Segue estrutura do projeto.

lab04-final/
├── pom.xml                         
└── src/
    ├── main/
    │   ├── java/br/ifma/labbd/
    │   │   ├── model/
    │   │   │   ├── EntidadeBase.java    
    │   │   │   ├── TipoImovel.java
    │   │   │   ├── Cliente.java
    │   │   │   ├── Imovel.java
    │   │   │   ├── Locacao.java
    │   │   │   └── Aluguel.java
    │   │   └── repository/
    │   │       ├── DAOGenerico.java     
    │   │       ├── JPAUtil.java
    │   │       ├── ClienteRepository.java
    │   │       ├── ImovelRepository.java
    │   │       ├── LocacaoRepository.java
    │   │       └── AluguelRepository.java
    │   └── resources/META-INF/
    │       └── persistence.xml         ← 2 unidades: lab04PU + lab04PU-test
    └── test/
        └── java/br/ifma/labbd/repository/
            ├── ClienteRepositoryTest.java
            ├── ImovelRepositoryTest.java
            ├── LocacaoRepositoryTest.java
            └── AluguelRepositoryTest.java
