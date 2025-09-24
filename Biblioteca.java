import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;



public class Biblioteca {
    private List<Livro> acervo;

    public Biblioteca() {
        this.acervo = new ArrayList<>();
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
        if (livro.getAnoPublicacao() < 1900
                || livro.getAnoPublicacao() > anoAtual)
            throw new Exception("Ano de publicação deve estar entre 1900 e o ano atual");

        if (livro.getNumeroPaginas() <= 0)
            throw new Exception("Número de páginas deve ser maior que zero");

        acervo.add(livro);
        return livro;
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
    public void removerLivro(Livro livro) throws Exception {
        if (livro == null)
            throw new Exception("Livro não pode ser nulo");
        if (!acervo.remove(livro))
            throw new Exception("Livro não encontrado no acervo");
    }
    public void editarLivro(Livro livroAntigo, Livro livroNovo) throws Exception {
        if (livroAntigo == null || livroNovo == null)
            throw new Exception("Livros não podem ser nulos");
        int index = acervo.indexOf(livroAntigo);
        if (index == -1)
            throw new Exception("Livro antigo não encontrado no acervo");
        acervo.set(index, livroNovo);
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

