public class LivroDigital extends Livro {
    private String formato; // ex pdf, epub
    private double tamanhoArquivo;

    @Override
    public String getFormato() {
        return formato;
    }

    public LivroDigital(String titulo, String autor, int anoPublicacao, int numeroPaginas, String formato,
            double tamanhoArquivo) {
        super(titulo, autor, anoPublicacao, numeroPaginas);
        this.setTitulo(titulo);
        this.setAutor(autor);
        this.setAnoPublicacao(anoPublicacao);
        this.setNumeroPaginas(numeroPaginas);
        this.formato = formato;
        this.tamanhoArquivo = tamanhoArquivo;
    } // ex MB, GB

    public String getformato() {
        return formato;
    }

    public void setformato(String formato) {
        this.formato = formato;

    }

    public Double gettamanhoArquivo() {
        return tamanhoArquivo;
    }

    public void settamanhoArquivo(double tamanhoArquivo) {
        this.tamanhoArquivo = tamanhoArquivo;

    }

public String toString() {
        String dadosLivro = "Titulo=" + this.getTitulo() 
                + ", autor=" + this.getAutor() 
                + ", anoPublicacao=" + this.getAnoPublicacao() 
                + ", numeroPaginas=" + this.getNumeroPaginas();
        return dadosLivro
        + ", Formato" + this.getformato() //acesso via get
        + ", Tamanho" + tamanhoArquivo; //acessa diretamente o atributo

    }
}



