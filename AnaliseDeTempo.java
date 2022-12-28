
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.IOException;
import java.util.Scanner;

public class AnaliseDeTempo{

  public static long ping(InetAddress address){
    long nanos = 0L;
    long millis = 0L;
    try {
      if (address.isReachable(5000)) {
          try {
            nanos = System.nanoTime();
            address.isReachable(5000);
            nanos = System.nanoTime()-nanos;
          }
          catch (IOException e) {
            millis = -1L;
            //System.out.println("Falhou em alcançar o host");
          }
          
          millis = Math.round(nanos/Math.pow(10,6));
      }
      else {
        millis = -2L; // Não é mais alcançável
      }
    } 
    catch (IOException e) {
      millis = -3L; //Erro de rede.
    }
    return millis;
  }

  public static void Statistic(long []answerTimes){
    long smaller = 0;
    long larger = 0;
    int success = 0;
    int fail = 0;
    long summation = 0;
    for(int i = 0; i < answerTimes.length; i++){
      if(answerTimes[i] >= 0){
        success++;
        summation = summation + answerTimes[i];
        if(success == 1){
          smaller = larger = answerTimes[i];
        }
        else{
          if(answerTimes[i]<smaller){
            smaller = answerTimes[i];
          }
          if(answerTimes[i]>larger){
            larger = answerTimes[i];
          }
        }
      }
      else{
        fail++;
      }
    }
    System.out.println("Número de tentativas: " + answerTimes.length + ".");
    System.out.println("Número de tentativas bem sucedidas: " + success + " (" + (success/answerTimes.length)*100 + "% do total).");
    System.out.println("Número de tentativas bem sucedidas: " + fail + " (" + (fail/answerTimes.length)*100 + "% do total).");
    System.out.println("Menor tempo de resposta registrado: " + smaller + "ms.");
    System.out.println("Maior tempo de resposta registrado: " + larger + "ms.");
    System.out.println("Média de tempo: " + summation/answerTimes.length +"ms.");
  }

  public static void main(String[] args){
    Scanner input = new Scanner(System.in);
    
    System.out.print("Digite o host a ser analisado: ");
    String inputHost = input.nextLine();
    System.out.print("Digite o número de pacotes a serem enviados: ");
    int npacks = input.nextInt();
    input.close();
    
    long []answerTimes = new long[npacks];

    if (inputHost.length() > 0) {
      InetAddress address = null;
      try {
        address = InetAddress.getByName(inputHost);
      }
      catch (UnknownHostException e) {
        System.out.println("Não consigo alcançar host: " + inputHost);
      }
      for(int i = 0; i < npacks; i++){
        answerTimes[i] = ping(address);
        System.out.println("Envio do pacote[" + (i + 1) +"]: " + answerTimes[i] + "ms");
        try {
          Thread.sleep(Math.max(0, 1000 - answerTimes[i]));
        }
        catch (InterruptedException e) {
          break;
        }
      }
    }
    else {
      System.out.println("Endereço inválido para teste. <inputHost>");
    }
    Statistic(answerTimes);
  }
}