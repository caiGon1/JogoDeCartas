package jogodamemoria;

import javax.swing.JOptionPane;
import java.awt.event.*;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class Jframe2 extends JFrame {

    private final int NUM_JOGADORES = 2;
    private Jogador[] jogadores;
    public Jogador jogMomento;

    private Carta[] cartasDoJogo;
    private Carta cartaVirada1;
    private Carta cartaVirada2;

    public JLabel[] carta;
    public JLabel[] cartaVirada;
    public JLabel[] pontos = new JLabel[2];
    public JLabel txtpontos;
    public JLabel[] jogMomento1 = new JLabel[2];
    public JLabel jogador;

    public int fimDeJogo = 0;

    public Jframe2() {

        setSize(500, 550);
        setTitle("Jogo da Memória");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);

        iniciarInterfaceDoJogo();
    }

    private void iniciarInterfaceDoJogo() {
        jogadores = new Jogador[NUM_JOGADORES];
        for (int i = 0; i < NUM_JOGADORES; i++) {
            String nomeJogador = JOptionPane.showInputDialog("Nome do " + (i + 1) + "º Jogador:");
            jogadores[i] = new Jogador();
            jogadores[i].setNome(nomeJogador);
        }

        jogMomento = jogadores[0];

        cartasDoJogo = new Carta[8];
        cartasDoJogo[0] = new Carta(1, new ImageIcon(getClass().getResource("shrek.png")));
        cartasDoJogo[1] = new Carta(1, new ImageIcon(getClass().getResource("shrek.png")));
        cartasDoJogo[2] = new Carta(2, new ImageIcon(getClass().getResource("fiona.png")));
        cartasDoJogo[3] = new Carta(2, new ImageIcon(getClass().getResource("fiona.png")));
        cartasDoJogo[4] = new Carta(3, new ImageIcon(getClass().getResource("burro.png")));
        cartasDoJogo[5] = new Carta(3, new ImageIcon(getClass().getResource("burro.png")));
        cartasDoJogo[6] = new Carta(4, new ImageIcon(getClass().getResource("gato.png")));
        cartasDoJogo[7] = new Carta(4, new ImageIcon(getClass().getResource("gato.png")));

        Aleatorizador.embaralhar(cartasDoJogo);

        carta = new JLabel[8];
        cartaVirada = new JLabel[8];
        int y = 10;
        int x = 20;

        txtpontos = new JLabel();
        txtpontos.setBounds(20, 435, 100, 100);
        txtpontos.setText("Pontos: ");
        txtpontos.setVisible(true);
        add(txtpontos);

        for (int i = 0; i < pontos.length; i++) {
            pontos[i] = new JLabel();
            pontos[i].setBounds(70, 435, 100, 100);
            pontos[i].setText(String.valueOf(jogadores[i].getPontos()));
            add(pontos[i]);
        }   
            pontos[0].setVisible(true);
            pontos[1].setVisible(false);

        for (int i = 0; i < 2; i++) {
            jogMomento1[i] = new JLabel();
            jogMomento1[i].setBounds(390, 435, 100, 100);
            jogMomento1[i].setText(jogadores[i].getNome());
            jogMomento1[i].setVisible(true);
            add(jogMomento1[i]);
        }

        jogMomento1[1].setVisible(false);

        jogador = new JLabel();
        jogador.setBounds(330, 435, 100, 100);
        jogador.setText("Jogador: ");
        jogador.setVisible(true);
        add(jogador);

        for (int i = 0; i < 8; i++) {
            final int j = i;
            carta[i] = new JLabel();
            carta[i].setBounds(x, y, 100, 80);
            carta[i].setIcon(new ImageIcon(getClass().getResource("cartasvirada.png")));
            carta[i].setVisible(true);
            add(carta[i]);

            cartaVirada[i] = new JLabel();
            cartaVirada[i].setBounds(x, y, 260, 110);
            cartaVirada[i].setIcon(cartasDoJogo[i].getImagem());
            cartaVirada[i].setVisible(false);
            add(cartaVirada[i]);

            carta[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!cartasDoJogo[j].estaVirada()) {
                        cartasDoJogo[j].virar();
                        carta[j].setVisible(false);
                        cartaVirada[j].setVisible(true);

                        if (cartaVirada1 == null) {
                            cartaVirada1 = cartasDoJogo[j];
                        } else {
                            cartaVirada2 = cartasDoJogo[j];
                            verificarPar();
                        }
                    }
                }
            });

            if (i == 3) {
                x = 260;
                y = 10;
            } else {
                y += 120;
            }
        }
    }

    public void mudarJogador() {
        if (jogMomento == jogadores[0]) {
            jogMomento1[0].setVisible(false);
            jogMomento1[1].setVisible(true);
            pontos[0].setVisible(false);
            pontos[1].setVisible(true);
            jogMomento = jogadores[1];
        } else {
            jogMomento1[0].setVisible(true);
            jogMomento1[1].setVisible(false);
            pontos[0].setVisible(true);
            pontos[1].setVisible(false);
            jogMomento = jogadores[0];
        }
    }

    public void atualizarCartas() {
        for (int i = 0; i < pontos.length; i++) {
            pontos[i].setText(String.valueOf(jogadores[i].getPontos()));
        }
    }

    private void verificarPar() {
        if (cartaVirada1 != null && cartaVirada2 != null) {
            if (cartaVirada1.getValor() == cartaVirada2.getValor()) {
                jogMomento.acrescentarPontos(25);
                atualizarCartas();
                fimDeJogo++;

                if (fimDeJogo == cartasDoJogo.length / 2) {
                    Jogador vencedor = vencedorEh();
                    if (vencedor != null) {
                        JOptionPane.showMessageDialog(null, "O vencedor é " + vencedor.getNome() + " com " + vencedor.getPontos() + " pontos!");
                    } else {
                        JOptionPane.showMessageDialog(null, "O jogo terminou em empate!");
                    }
                    reiniciarJogo();
                }
                cartaVirada1 = null;
                cartaVirada2 = null;
            } else {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        cartaVirada1.desvirar();
                        cartaVirada2.desvirar();
                        cartaVirada[getIndex(cartaVirada1)].setVisible(false);
                        carta[getIndex(cartaVirada1)].setVisible(true);
                        cartaVirada[getIndex(cartaVirada2)].setVisible(false);
                        carta[getIndex(cartaVirada2)].setVisible(true);
                        cartaVirada1 = null;
                        cartaVirada2 = null;
                        mudarJogador();
                        atualizarCartas();
                    }
                }, 1000);
            }
        }
    }

    private int getIndex(Carta carta) {
        for (int i = 0; i < cartasDoJogo.length; i++) {
            if (cartasDoJogo[i] == carta) {
                return i;
            }
        }
        return -1;
    }

    private Jogador vencedorEh() {
        if (jogadores[0].getPontos() > jogadores[1].getPontos()) {
            return jogadores[0];
        } else if (jogadores[0].getPontos() < jogadores[1].getPontos()) {
            return jogadores[1];
        } else {
            return null;
        }
    }

    public void reiniciarJogo() {
        for (Jogador jogador : jogadores) {
            jogador.resetPontos();
        }
        for (Carta carta : cartasDoJogo) {
            carta.desvirar();
        }

        for (int i = 0; i < carta.length; i++) {
            carta[i].setVisible(true);
            cartaVirada[i].setVisible(false);
            pontos[i].setText(String.valueOf(jogadores[i].getPontos()));
        }
        fimDeJogo = 0;
        cartaVirada1 = null;
        cartaVirada2 = null;
        Aleatorizador.embaralhar(cartasDoJogo);

    }
}
