
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.IOException;
import java.util.Scanner;

public class AnaliseDeTempo{
  
  public float[] Ping(String inputHost, int npacks){
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
          long shorterTime = 0;
          long longerTime = 0;
          long total = 0;
          int successful = 0;
          int failures = 0;
          int loops = 20;
          for(int i = 1; i <= loops; i++) {
            try {
              nanos = System.nanoTime();
              address.isReachable(5000);
              nanos = System.nanoTime()-nanos;
              successful++;
            }
            catch (IOException e) {
              failures++;
              System.out.println("Falhou em alcançar o host");
            }
            millis = Math.round(nanos/Math.pow(10,6));
            
            if(i == 1){
              shorterTime = longerTime = millis;
            }
            else{
              if(millis < shorterTime){
                shorterTime = millis;
              }
              if(millis > longerTime){
                longerTime = millis;
              }
            }
            total = total + millis;
            System.out.println("Envio do pacote "+ i + " ao IP: " + address.getHostAddress()+" com o de tempo de " + millis+"ms");
            try {
              Thread.sleep(Math.max(0, 1000-millis));
            }
            catch (InterruptedException e) {
              break;
            }
          }
          System.out.println("Tentativas com sucesso: " + successful + "(" + ((float)successful/loops)*100 + "%) de sucesso.");
          System.out.println("Tentativas com falhas: " + failures + "(" + ((float)failures/loops)*100 + "%) de falhas.");
          System.out.println("Menor tempo registrado: " + shorterTime + "ms");
          System.out.println("Maior tempo registrado: " + longerTime + "ms");
          System.out.println("Média das tentativas com sucesso: " + (float)total/successful + "ms");
          System.out.println("Média geral (Com tentativas com sucesso e com falhas): " + (float)total/loops + "ms");
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
  }
  

  public static void main(String[] args){
    Scanner input = new Scanner(System.in);
    System.out.print("Digite o host a ser analisado: ");
    String inputHost = input.nextLine();
    System.out.print("Digite a quantidade de pacotes a ser enviada: ");
    int npacks = input.nextInt();
    input.close();
    float[] answerTimes = new float[npacks];
    answerTimes = Ping(inputHost, npacks);
  }  
}