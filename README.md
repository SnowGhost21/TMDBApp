# TMDApp

TMDApp é um aplicativo que permite ao usuário visualizar uma lista de filmes que serão lançados em breve, assim como detalhes sobre a trama do filme e os generos dele.

# Tecnologia

### Linguagem
Este aplicativo foi escrito utilizando a linguagem [Kotlin](https://kotlinlang.org/), visto que a linguagem possibilita o uso do paradigma funcional.

### Arquitetura
O aplicativo segue com base da arquitetura [MVVM](https://www.journaldev.com/20292/android-mvvm-design-pattern)(Model-View-ViewModel) e se beneficía das bibliotecas do [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/).

O projeto foi dividio em uma camada de repositório em que os dados serão buscados através do Retrofi respeitando assim os princípios do [SOLID](https://en.wikipedia.org/wiki/SOLID).

### Testes
Neste projeto foi somente criado testes unitários da de ViewModel dos fragmentos, sendo os de adapters de RecyclerViews não entrando no mérito de testes e todos os testes foram escritos seguindo a teoria do [TDD](https://pt.wikipedia.org/wiki/Test_Driven_Development).

### Bibliotecas externas
Das bibliotecas externas utilizadas no aplicativo, destaca-se o [Navigation](https://developer.android.com/topic/libraries/architecture/navigation) do Architecture Components, que visa facilitar as mudanças de tela do aplicativo de acordo com a necessidade e o [Koin](https://insert-˜.io/) para injeção de dependência pois é menos verbosa que o Dagger 2 e 100% escrita em Kotlin assim como a utilização de coroutines para lidar com chamadas assincronas.

### Próximos passos
Os próximos passos do aplicativo se baseam na funcionalidade se salvar a lista de filmes no aplicativo respeitando a ideia de [Offline First](https://www.youtube.com/watch?v=70WqJxymPr8) se utilizando do Room para a camada de persistência de dados.
