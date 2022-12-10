
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.IOException;
import java.util.Scanner;

public class AnaliseDeTempo{
  
  public static long[] Ping(String inputHost, int npacks){
    long[] answerTimes = new long[npacks];
    if (inputHost.length() > 0) {
      InetAddress address = null;
      try {
        address = InetAddress.getByName(inputHost);
      }
      catch (UnknownHostException e) {
        System.out.println("Não consigo alcançar host: " + inputHost);
      }

      try {
        if (address.isReachable(5000)) {
          long nanos = 0;
          long millis = 0;
          for(int i = 0; i < npacks; i++) {
            try {
              nanos = System.nanoTime();
              address.isReachable(5000);
              nanos = System.nanoTime()-nanos;  
            }
            catch (IOException e) {
              System.out.println("Falhou em alcançar o host");
            }
            millis = Math.round(nanos/Math.pow(10,6));
            answerTimes[i] = millis;
            //----------------------------------
            System.out.println("Envio do pacote "+ (i + 1) + " ao IP: " + address.getHostAddress()+" com o de tempo de " + millis+"ms");
            //----------------------------------
            try {
              Thread.sleep(Math.max(0, 1000-millis));
            }
            catch (InterruptedException e) {
              break;
            }
          }
        }
        else {
          System.out.println("Host "+address.getHostName()+" não é mais alcancável");
        }
      } 
      catch (IOException e) {
        System.out.println("Erro de rede.");
      }
    }
    else {
      System.out.println("Endereço inválido para teste. <inputHost>");
    }
    return answerTimes;
  }


  public static void main(String[] args){
    Scanner input = new Scanner(System.in);
    System.out.print("Digite o host a ser analisado: ");
    String inputHost = input.nextLine();
    System.out.print("Digite a quantidade de pacotes a ser enviada: ");
    int npacks = input.nextInt();
    input.close();
    long[] answerTimes = new long[npacks];
    answerTimes = Ping(inputHost, npacks);
  }  
}