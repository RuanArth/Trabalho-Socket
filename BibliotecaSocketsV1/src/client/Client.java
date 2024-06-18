package client;



import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
  public static void main(String[] args) throws IOException {
        int portConnect = 8082;
        String host = "localhost";
        ObjectInputStream messageServer = null;
        
        try {
            //Realiza a conexão com o socket server
            Socket clienteSocket = new Socket(host,portConnect);
            Scanner scanner = new Scanner(System.in);
            
            ClienteThread cThread = new ClienteThread(clienteSocket);
            cThread.start();
            
            PrintStream saida = new PrintStream(clienteSocket.getOutputStream());
            System.out.print("Digite o comando >>> ");
            saida.println(scanner.nextLine());
            
        }catch(Exception e){
            System.out.println("Erro: " + e);
        }finally{
            //messageServer.close();
        }
    }
}