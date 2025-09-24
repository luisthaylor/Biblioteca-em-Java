import java.util.List;
import java.util.Scanner;

public class Main {
    // Dependências
    private static Biblioteca biblioteca = new Biblioteca();
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        String menu = """
                === Sistema Biblioteca ===
                Escolha uma das opções abaixo:
                1 - Adicionar Livro
                2 - Listar Acervo
                3 - Pesquisar Livro
                4 - Remover Livro
                5 - Editar Livro
                6 - Ordenar Livros 
                7 - Pesquisar por Ano
                0 - Sair
                """;
        int opcao;
        do {
            System.out.println(menu);
            opcao = Input.scanInt("Digite sua escolha: ", scan);
            switch (opcao) {
                case 1:
                    cadastrarLivro();
                    System.out.println("Pressione Enter para continuar");
                    scan.nextLine();
                    break;
                case 2:
                    listarAcervo();
                    System.out.println("Pressione Enter para continuar");
                    scan.nextLine();
                    break;
                case 3:
                    pesquisarLivro();
                    System.out.println("Pressione Enter para continuar");
                    scan.nextLine();
                    break;
                case 4:
                    removerLivro();
                    System.out.println("Pressione Enter para continuar");
                    scan.nextLine();
                    break;
                case 5:
                    editarLivro();
                    System.out.println("Pressione Enter para continuar");
                    scan.nextLine();
                    break;
                case 6:
                    ordenarAcervo();
                    System.out.println("Pressione Enter para continuar");
                    System.out.println("Funcionalidade em desenvolvimento.");

                    scan.nextLine();
                    break;
                case 7:
                    PesquisarPorAno();
                    System.out.println("Pressione Enter para continuar");
                    scan.nextLine();
                    break;
                case 0:
                    System.out.println("Volte Sempre!!!");
                    break;
                default:
                    System.out.println("Opção Inválida!");
                    break;
            }
        } while (opcao != 0);
    }

    private static void cadastrarLivro() {
        String titulo = Input.scanString("Digite o Título: ", scan);
        String autor = Input.scanString("Digite o Autor: ", scan);
        int anoPublicacao = Input.scanInt("Digite o ano de publicação: ", scan);
        int numeroPaginas = Input.scanInt("Digite o número de páginas: ", scan);
        Livro novoLivro = new Livro(titulo, autor, anoPublicacao, numeroPaginas);
        try {
            biblioteca.adicionar(novoLivro);
            System.out.println("Livro adicionado com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void listarAcervo() {
        var acervo = biblioteca.pesquisar();
        // List<Livro> acervo = biblioteca.pesquisar();
        imprimirLista(acervo);
    }

    private static void pesquisarLivro() {
        String titulo = Input.scanString("Digite o título que procuras: ", scan);
        String pesquisaAutor = Input.scanString(
            "Deseja pesquiar por autor? (S/N) ", scan);
        List<Livro> livros;
        if (pesquisaAutor.toLowerCase().charAt(0) == 's'){
            String autor = Input.scanString("Digite o nome do autor: ", scan);
            livros = biblioteca.pesquisar(titulo, autor);
        } else {
            livros = biblioteca.pesquisar(titulo);
        }
        imprimirLista(livros);
    }

    private static void imprimirLista(List<Livro> acervo) {
        if (acervo == null || acervo.isEmpty())
            System.out.println("Nenhum Livro Encontrado");
        else {
            System.out.println("Livros Encrontrados");
            for (int i = 0; i < acervo.size(); i++) {
                System.out.println("Livro " + (i + 1) + ": " + acervo.get(i));
            }
        }
    }
    private static void removerLivro() {
        String titulo = Input.scanString("Digite o título do livro a ser removido: ", scan);
        List<Livro> livrosEncontrados = biblioteca.pesquisar(titulo);
        if (livrosEncontrados.isEmpty()) {
            System.out.println("Nenhum livro encontrado com o título fornecido.");
            return;
        }
        System.out.println("Livros encontrados:");
        for (int i = 0; i < livrosEncontrados.size(); i++) {
            System.out.println((i + 1) + ": " + livrosEncontrados.get(i));
        }
        int escolha = Input.scanInt("Digite o número do livro que deseja remover: ", scan);
        if (escolha < 1 || escolha > livrosEncontrados.size()) {
            System.out.println("Escolha inválida.");
            return;
        }
        Livro livroARemover = livrosEncontrados.get(escolha - 1);
        try {
            biblioteca.removerLivro(livroARemover);
            System.out.println("Livro removido com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao remover o livro: " + e.getMessage());
        }
    }
private static void editarLivro() {
        String titulo = Input.scanString("Digite o título do livro a ser editado: ", scan);
        List<Livro> livrosEncontrados = biblioteca.pesquisar(titulo);
        if (livrosEncontrados.isEmpty()) {
            System.out.println("Nenhum livro encontrado com o título fornecido.");
            return;
        }
        System.out.println("Livros encontrados:");
        for (int i = 0; i < livrosEncontrados.size(); i++) {
            System.out.println((i + 1) + ": " + livrosEncontrados.get(i));
        }
        int escolha = Input.scanInt("Digite o número do livro que deseja editar: ", scan);
        if (escolha < 1 || escolha > livrosEncontrados.size()) {
            System.out.println("Escolha inválida.");
            return;
        }
        Livro livroAntigo = livrosEncontrados.get(escolha - 1);
        String novoTitulo = Input.scanString("Digite o novo título (caso deseje manter o mesmo, favor confirmar.): ", scan);
        String novoAutor = Input.scanString("Digite o novo autor (caso deseje manter o mesmo, favor confirmar.): ", scan);
        int novoAnoPublicacao = Input.scanInt("Digite o novo ano de publicação (ou 0 para manter o atual): ", scan);
        int novoNumeroPaginas = Input.scanInt("Digite o novo número de páginas (ou 0 para manter o atual): ", scan);
        Livro livroNovo = new Livro(
            novoTitulo.isEmpty() ? livroAntigo.getTitulo() : novoTitulo,
            novoAutor.isEmpty() ? livroAntigo.getAutor() : novoAutor,
            novoAnoPublicacao == 0 ? livroAntigo.getAnoPublicacao() : novoAnoPublicacao,
            novoNumeroPaginas == 0 ? livroAntigo.getNumeroPaginas() : novoNumeroPaginas
        );
        try {
            biblioteca.editarLivro(livroAntigo, livroNovo);
            System.out.println("Livro editado com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao editar o livro: " + e.getMessage());
        }
    }
private static void ordenarAcervo() {
    String menuOrdenacao = """
            === Opções de Ordenação ===
            1 - Ordenar por Título
            2 - Ordenar por Autor
            3 - Ordenar por Ano de Publicação
            0 - Voltar
            """;
    System.out.println(menuOrdenacao);
    
    int escolha = Input.scanInt("Digite sua escolha: ", scan);
    List<Livro> acervoOrdenado;

    switch (escolha) {
        case 1:
            System.out.println("--- Acervo Ordenado por Título ---");
            acervoOrdenado = biblioteca.getAcervoOrdenadoPorTitulo();
            imprimirLista(acervoOrdenado);
            break;
        case 2:
            System.out.println("--- Acervo Ordenado por Autor ---");
            acervoOrdenado = biblioteca.getAcervoOrdenadoPorAutor();
            imprimirLista(acervoOrdenado);
            break;
        case 3:
            System.out.println("--- Acervo Ordenado por Ano ---");
            acervoOrdenado = biblioteca.getAcervoOrdenadoPorAno();
            imprimirLista(acervoOrdenado);
            break;
        case 0:
            System.out.println("Voltando ao menu principal...");
            return;
        default:
            System.out.println("Opção inválida!");
            break;
    }
    
    System.out.println("\nPressione Enter para continuar...");
    scan.nextLine();
}

  private static void PesquisarPorAno() {
    int ano = Input.scanInt("Digite o ano de publicação para pesquisar: ", scan);
    List<Livro> livrosEncontrados = biblioteca.pesquisarPorAno(ano);
    imprimirLista(livrosEncontrados);
  }
}       

