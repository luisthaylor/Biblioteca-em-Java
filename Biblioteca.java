import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Biblioteca {
    public static final int ANO_PUBLICACAO_MINIMO = 1900;
    private List<Livro> acervo;
    private static final String NOME_ARQUIVO = "acervo.txt";

    public Biblioteca() {
        this.acervo = new ArrayList<>();
        carregarDeArquivo();
    }

    public Livro adicionar(Livro livro) throws Exception {
        if (livro == null)
            throw new Exception("Livro não pode ser nulo");

        livro.setTitulo(livro.getTitulo().trim());
        if (livro.getTitulo() == null || livro.getTitulo().isEmpty())
            throw new Exception("Título não pode ser em branco");

        livro.setAutor(livro.getAutor().trim());
        if (livro.getAutor() == null || livro.getAutor().isEmpty())
            throw new Exception("Autor não pode ser em branco");

        int anoAtual = LocalDate.now().getYear();
        if (livro.getAnoPublicacao() < ANO_PUBLICACAO_MINIMO
                || livro.getAnoPublicacao() > anoAtual)
            throw new Exception("Ano de publicação deve estar entre " + ANO_PUBLICACAO_MINIMO + " e o ano atual");

        if (livro.getNumeroPaginas() <= 0)
            throw new Exception("Número de páginas deve ser maior que zero");

        acervo.add(livro);
        salvarEmArquivo();
        return livro;
    }

    public void removerLivro(Livro livro) throws Exception {
        if (livro == null)
            throw new Exception("Livro não pode ser nulo");
        if (!acervo.remove(livro))
            throw new Exception("Livro não encontrado no acervo");
        salvarEmArquivo();
    }

    public void editarLivro(Livro livroAntigo, Livro livroNovo) throws Exception {
        if (livroAntigo == null || livroNovo == null)
            throw new Exception("Livros não podem ser nulos");
            
        livroNovo.setTitulo(livroNovo.getTitulo().trim());
        if (livroNovo.getTitulo() == null || livroNovo.getTitulo().isEmpty())
            throw new Exception("Título não pode ser em branco");

        livroNovo.setAutor(livroNovo.getAutor().trim());
        if (livroNovo.getAutor() == null || livroNovo.getAutor().isEmpty())
            throw new Exception("Autor não pode ser em branco");

        int anoAtual = LocalDate.now().getYear();
        if (livroNovo.getAnoPublicacao() < ANO_PUBLICACAO_MINIMO
                || livroNovo.getAnoPublicacao() > anoAtual)
            throw new Exception("Ano de publicação deve estar entre " + ANO_PUBLICACAO_MINIMO + " e o ano atual");

        if (livroNovo.getNumeroPaginas() <= 0)
            throw new Exception("Número de páginas deve ser maior que zero");
            
        int index = acervo.indexOf(livroAntigo);
        if (index == -1)
            throw new Exception("Livro antigo não encontrado no acervo");
            
        acervo.set(index, livroNovo);
        salvarEmArquivo();
    }

    private void salvarEmArquivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOME_ARQUIVO))) {
            for (Livro livro : acervo) {
                String linha = "";
                if (livro instanceof LivroFisico) {
                    LivroFisico lf = (LivroFisico) livro;
                    linha = "FISICO;" + lf.getTitulo() + ";" + lf.getAutor() + ";" + lf.getAnoPublicacao() + ";" + lf.getNumeroPaginas() + ";" + lf.getNumeroExemplares() + ";" + lf.getDimensoes();
                } else if (livro instanceof LivroDigital) {
                    LivroDigital ld = (LivroDigital) livro;
                    linha = "DIGITAL;" + ld.getTitulo() + ";" + ld.getAutor() + ";" + ld.getAnoPublicacao() + ";" + ld.getNumeroPaginas() + ";" + ld.getformato() + ";" + ld.gettamanhoArquivo();
                }
                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("ERRO AO SALVAR ARQUIVO: " + e.getMessage());
        }
    }

    private void carregarDeArquivo() {
        File arquivo = new File(NOME_ARQUIVO);
        if (!arquivo.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(NOME_ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length < 6) continue;

                String tipo = partes[0];
                String titulo = partes[1];
                String autor = partes[2];
                int ano = Integer.parseInt(partes[3]);
                int paginas = Integer.parseInt(partes[4]);
                Livro livro = null;

                if (tipo.equals("FISICO") && partes.length == 7) {
                    int exemplares = Integer.parseInt(partes[5]);
                    String dimensoes = partes[6];
                    livro = new LivroFisico(titulo, autor, ano, paginas, exemplares, dimensoes);
                } else if (tipo.equals("DIGITAL") && partes.length == 7) {
                    String formato = partes[5];
                    double tamanho = Double.parseDouble(partes[6].replace(',', '.'));
                    livro = new LivroDigital(titulo, autor, ano, paginas, formato, tamanho);
                }
                if (livro != null) {
                    this.acervo.add(livro);
                }
            }
        } catch (Exception e) {
            System.out.println("ERRO AO CARREGAR ARQUIVO: " + e.getMessage());
        }
    }

    public List<Livro> pesquisar() {
        return acervo;
    }

    public List<Livro> pesquisar(String titulo) {
        return pesquisar(titulo, null);
    }

    public List<Livro> pesquisar(String titulo, String autor) {
        List<Livro> livrosEncontrados = new ArrayList<>();
        for (Livro livro : acervo) {
            if (livro.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                if (autor == null ||
                        livro.getAutor().toLowerCase().contains(autor.toLowerCase()))
                    livrosEncontrados.add(livro);
            }
        }
        return livrosEncontrados;
    }

    public List<Livro> pesquisarPorAno(int ano) {
        List<Livro> livrosEncontrados = new ArrayList<>();
        for (Livro livro : acervo) {
            if (livro.getAnoPublicacao() == ano) {
                livrosEncontrados.add(livro);
            }
        }
        return livrosEncontrados;
    }

    public List<Livro> getAcervoOrdenadoPorTitulo() {
        return this.acervo.stream()
                .sorted(Comparator.comparing(Livro::getTitulo))
                .collect(Collectors.toList());
    }



    public List<Livro> getAcervoOrdenadoPorAutor() {
        return this.acervo.stream()
                .sorted(Comparator.comparing(Livro::getAutor))
                .collect(Collectors.toList());
    }

    public List<Livro> getAcervoOrdenadoPorAno() {
        return this.acervo.stream()
                .sorted(Comparator.comparingInt(Livro::getAnoPublicacao))
                .collect(Collectors.toList());
    }
}

