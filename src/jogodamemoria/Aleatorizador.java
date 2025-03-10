package jogodamemoria;


import java.util.Random;

public class Aleatorizador {
    public static void embaralhar(Carta[] cartas) {
        Random random = new Random();
        for (int i = 0; i < cartas.length; i++) {
            int j = random.nextInt(cartas.length);
            Carta temp = cartas[i];
            cartas[i] = cartas[j];
            cartas[j] = temp;
        }
    }
}



    

