package client;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClienteThread extends Thread{

    private Socket socketClient;

    ClienteThread(Socket clienteSocket) {
        this.socketClient = clienteSocket;
    }
    
    @Override
    public void run(){
        try{
            InputStreamReader inputServer = new InputStreamReader(socketClient.getInputStream());
            BufferedReader readerServer = new BufferedReader(inputServer);
            //PrintStream saida = new PrintStream(socketClient.getOutputStream());
            String messageInputClient = null;
            Scanner scanner = new Scanner(System.in);
            List<String> comandos = new ArrayList<String>();
            comandos.add("KILL");
            comandos.add("__EOF");
           
            while((messageInputClient = readerServer.readLine())!=null){
                if(messageInputClient.equals("KILL")){
                    messageInputClient = "Conexão encerrada!\n <<< Pressione qualquer tecla para continuar >>>";
                }else if(!messageInputClient.contains("__EOF")) {
                    if(!comandos.contains(messageInputClient)) System.out.println(messageInputClient);
                }
                
                if(messageInputClient.equals("CADASTRARLIVRO")){
                    PrintStream saida = new PrintStream(socketClient.getOutputStream());

                    messageInputClient = readerServer.readLine();
                    String idNovoCadastro = messageInputClient;
                    
                    String jsonDadosLivros = "";
                    System.out.println("\nDigite os dados do livro\n");
                    jsonDadosLivros = jsonDadosLivros + "{";
                    jsonDadosLivros = jsonDadosLivros + "\"id\":" + idNovoCadastro + ",";
                    jsonDadosLivros = jsonDadosLivros + "\"alugados\":\"0\",";
                    System.out.print("Título:");
                    jsonDadosLivros = jsonDadosLivros + "\"titulo\":\"" + scanner.nextLine() + "\",";
                    System.out.print("Autor:");
                    jsonDadosLivros = jsonDadosLivros + "\"autor\":\"" + scanner.nextLine() + "\",";
                    System.out.print("Gênero:");
                    jsonDadosLivros = jsonDadosLivros + "\"genero\":\"" + scanner.nextLine() + "\",";
                    System.out.print("Quantidade de Exemplares:");
                    jsonDadosLivros = jsonDadosLivros + "\"exemplares\":" + scanner.nextLine();
                    jsonDadosLivros = jsonDadosLivros + "}";
                    
                    saida.println("DADOSLIVRO");
                    saida.println(jsonDadosLivros);
                }
                
                if(messageInputClient.equals("IDALUGAR")){
                    PrintStream saida = new PrintStream(socketClient.getOutputStream());
                    saida.println("DADOSIDALUGAR");
                    System.out.print("\nQual livro deseja alugar  (Digite o ID):");
                    saida.println(scanner.nextLine());
                }
                
                if(messageInputClient.equals("IDDEVOLVER")){
                    System.out.print("\nQual livro deseja devolver (Digite o ID):");
                    PrintStream saida = new PrintStream(socketClient.getOutputStream());
                    saida.println("DADOSIDDEVOLVER");
                    saida.println(scanner.nextLine());
                }

                if(messageInputClient.equals("CADASTROSUCESSO")) System.out.println("\nLivro cadastrado!");                
                if(messageInputClient.equals("ALUGADOSUCESSO")) System.out.println("\nLivro alugado!");
                if(messageInputClient.equals("ALUGUELSEMSUCESSO")) System.out.println("\nLivro não foi alugado. Sem exemplar disponível. Tente outro livro!");
                if(messageInputClient.equals("DEVOLVIDOSUCESSO")) System.out.println("\nLivro devolvido!");
                if(messageInputClient.equals("DEVOLUCAOSEMSUCESSO")) System.out.println("\nLivro não foi devolvido. Verifique se o ID do livro foi digitado corretamente!");
                if(messageInputClient.equals("DESCONHECIDO")) System.out.println("\nComando desconhecido. \nDigite \"AJUDA\" para informação dos comandos.\n\n");
                
                if(messageInputClient.contains("__EOF")){
                    PrintStream saida = new PrintStream(socketClient.getOutputStream());
                    System.out.print("Digite o comando >>> ");
                    saida.println(scanner.nextLine());
                }
            }
        }catch(Exception e){
            System.out.println("Erro no client thread + " + e);
        }
    }
    
}
