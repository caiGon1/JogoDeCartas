package jogodamemoria;

    public class Jogador {
    private String nome;
    private int pontos;

    public Jogador() {
        pontos = 0;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPontos() {
        return pontos;
    }

    public void acrescentarPontos(int pontos) {
        this.pontos += pontos;
    }

    public void resetPontos() {
        this.pontos = 0;
    }
}

