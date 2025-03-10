package jogodamemoria;

import javax.swing.ImageIcon;

public class Carta {
    private int valor;
    private ImageIcon imagem;
    private boolean estaVirada;

    public Carta(int valor, ImageIcon imagem) {
        this.valor = valor;
        this.imagem = imagem;
        this.estaVirada = false;
    }

    public int getValor() {
        return valor;
    }

    public ImageIcon getImagem() {
        return imagem;
    }

    public boolean estaVirada() {
        return estaVirada;
    }

    public void virar() {
        estaVirada = !estaVirada;
    }

    public void desvirar() {
        estaVirada = false;
    }
}
