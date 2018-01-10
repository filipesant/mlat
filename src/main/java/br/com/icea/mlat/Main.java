package br.com.icea.mlat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] p_asArgs) {
		
		ExecutorService app = Executors.newFixedThreadPool(1);
		
		// Verifica a linha de comando
		if ( p_asArgs.length != 2) {
			System.out.println("Erro na passagem de argumentos via linha de comando.");
			System.out.println();
			System.out.println("Uso: SRBCReceiver <porta udp> <número do radar>");
			System.out.println();
			System.out.println("onde :");
			System.out.println("porta udp       : porta de leitura da mensagem UDP [1025..65535]");
			System.out.println("número do radar : número do radar SRBC [1..32]");
			System.out.println();
			System.out.println("Para assumir valores default use : SRBCReceiver -1 -1");
			System.exit(1);
		}
		
		int porta = -1;
		int radarNo = -1;
		
		try {
		
			porta = Integer.parseInt(p_asArgs[0]);
			radarNo = Integer.parseInt(p_asArgs[1]);
			
		} catch (NumberFormatException exception) {
			
			exception.printStackTrace();
			
			System.out.println("Erro no parse dos argumentos da linha de comandos!");
			System.exit(2);
		}
		
		// Inicia as threads
		try {
			
			app.execute(new SRBCReceiver(porta,radarNo));
			System.out.println("Running application");
			
		} catch (Exception exception) {
			
			exception.printStackTrace();
			
		}
		
		app.shutdown();
	}

}
