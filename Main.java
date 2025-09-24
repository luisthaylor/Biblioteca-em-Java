    import java.util.List;
import java.util.Scanner;

public class Main {
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
                    scan.nextLine();
                    break;
                case 7:
                    pesquisarPorAno();
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
        System.out.println("--- Cadastro de Novo Livro ---");
        int tipoLivro = Input.scanInt("Qual o formato do livro? (1 - Físico / 2 - Digital): ", scan);

        String titulo = Input.scanString("Digite o Título: ", scan);
        String autor = Input.scanString("Digite o Autor: ", scan);
        int anoPublicacao = Input.scanInt("Digite o ano de publicação: ", scan);
        int numeroPaginas = Input.scanInt("Digite o número de páginas: ", scan);
        Livro novoLivro;

        if (tipoLivro == 1) {
            int exemplares = Input.scanInt("Digite o número de exemplares: ", scan);
            String dimensoes = Input.scanString("Digite as dimensões (ex: 15x23cm): ", scan);
            novoLivro = new LivroFisico(titulo, autor, anoPublicacao, numeroPaginas, exemplares, dimensoes);
        } else if (tipoLivro == 2) {
            String formatoArquivo = Input.scanString("Digite o tipo de arquivo (PDF, EPUB): ", scan);
            double tamanhoArquivo = Input.scanDouble("Digite o tamanho do arquivo (MB): ", scan);
            novoLivro = new LivroDigital(titulo, autor, anoPublicacao, numeroPaginas, formatoArquivo, tamanhoArquivo);
        } else {
            System.out.println("Tipo de livro inválido. Cadastro cancelado.");
            return;
        }

        try {
            biblioteca.adicionar(novoLivro);
            System.out.println("Livro adicionado com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private static void listarAcervo() {
        List<Livro> acervo = biblioteca.pesquisar();
        imprimirLista(acervo);
    }

    private static void pesquisarLivro() {
        String titulo = Input.scanString("Digite o título que procuras: ", scan);
        List<Livro> livros = biblioteca.pesquisar(titulo);
        imprimirLista(livros);
    }

    private static void imprimirLista(List<Livro> acervo) {
        if (acervo == null || acervo.isEmpty())
            System.out.println("Nenhum Livro Encontrado");
        else {
            System.out.println("Livros Encontrados");
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
        imprimirLista(livrosEncontrados);
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
        imprimirLista(livrosEncontrados);
        int escolha = Input.scanInt("Digite o número do livro que deseja editar: ", scan);
        if (escolha < 1 || escolha > livrosEncontrados.size()) {
            System.out.println("Escolha inválida.");
            return;
        }
        Livro livroAntigo = livrosEncontrados.get(escolha - 1);
        
        System.out.println("--- Digite os novos dados ---");
        String novoTitulo = Input.scanString("Digite o novo Título: ", scan);
        String novoAutor = Input.scanString("Digite o novo Autor: ", scan);
        int novoAno = Input.scanInt("Digite o novo ano de publicação: ", scan);
        int novoNumPaginas = Input.scanInt("Digite o novo número de páginas: ", scan);
        Livro livroNovo;

        if (livroAntigo instanceof LivroFisico) {
            int novosExemplares = Input.scanInt("Digite o novo número de exemplares: ", scan);
            String novasDimensoes = Input.scanString("Digite as novas dimensões: ", scan);
            livroNovo = new LivroFisico(novoTitulo, novoAutor, novoAno, novoNumPaginas, novosExemplares, novasDimensoes);
        } else if (livroAntigo instanceof LivroDigital) {
            String novoFormato = Input.scanString("Digite o novo tipo de arquivo (PDF, EPUB): ", scan);
            double novoTamanho = Input.scanDouble("Digite o novo tamanho do arquivo (MB): ", scan);
            livroNovo = new LivroDigital(novoTitulo, novoAutor, novoAno, novoNumPaginas, novoFormato, novoTamanho);
        } else {
             System.out.println("Tipo de livro desconhecido. Edição cancelada.");
             return;
        }
        
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
                acervoOrdenado = biblioteca.getAcervoOrdenadoPorTitulo();
                imprimirLista(acervoOrdenado);
                break;
            case 2:
                acervoOrdenado = biblioteca.getAcervoOrdenadoPorAutor();
                imprimirLista(acervoOrdenado);
                break;
            case 3:
                acervoOrdenado = biblioteca.getAcervoOrdenadoPorAno();
                imprimirLista(acervoOrdenado);
                break;
            default:
                break;
        }
    }

    private static void pesquisarPorAno() {
        int ano = Input.scanInt("Digite o ano de publicação para pesquisar: ", scan);
        List<Livro> livrosEncontrados = biblioteca.pesquisarPorAno(ano);
        imprimirLista(livrosEncontrados);
    }
}



