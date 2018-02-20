package shiftand;

//bibliotecas usadas no codigo
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Principal {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in); //variavel para coletar dados do teclado
		
		//variaveis usadas no codigo
		int posiçao = 256,c=0,i,j,linha,coluna,aux=0; //usamos tabela ascii de 256 posições para o alfabeto
		char[] alfabeto = new char[posiçao];
		String texto = "",texto_atualizado = "",padrao; 
		int[][] ap = new int[posiçao][]; //matriz para comparar padrão e alfabeto
		int[][] ro_and; //matriz do ro>>1
		int[][] ro_atual; //matriz do ro_atual
		int[][] mti; //matriz m[t[i]]
		
		File arquivo = new File("arquivo.txt"); //informar o endereço do arquivo texto ja preenchido com o texto
		
		try {
			FileReader ler = new FileReader(arquivo); // informa o nome do arquivo que sera aberto atraves do objeto ler
			BufferedReader lerb = new BufferedReader(ler); //fluxo de entrada atraves do objeto lerb
			
			String arq = lerb.readLine(); //ler a linha do arquivo
			
			//função para concatenar as linhas na variavel texto
			while(arq != null) {
				texto = texto + arq;
				arq = lerb.readLine();	
			}
			
			
		}catch(IOException ex){ //responsável pelas exceções em operações de entrada e saída
			
		}
		System.out.println(texto);
		
		//aloca no vetor alfabeto a tabela ascii completa
		for (i = 0; i < posiçao; i++)
		{
			alfabeto[i] = (char) c;
			c++;
		}
		
		/*for (i = 0; i < posiçao; i++)
		{
			System.out.print(alfabeto[i]);
		}
		System.out.println();*/
		
		System.out.print("Informe um padrão: ");
		padrao = scan.nextLine();
		
		char[] caracteres_alfabeto = padrao.toCharArray(); //pega a string padrão e transforma em vetor
		char[] caracteres_texto = texto.toCharArray(); //pega a string texto e transforma em vetor 
		
		ro_and = new int[texto.length()][padrao.length()]; //alocando o ro_and
		mti = new int[texto.length()][padrao.length()]; //alocando o mit
		ro_atual = new int[texto.length()][padrao.length()]; //alocando o ro_atual
		ap = new int[posiçao][padrao.length()]; //alocando o ap
		
		//laço para comparar os caracteres e colocar os valores corretos na tabela ap
		for(linha=0;linha<posiçao;linha++) {
			for(coluna=0;coluna<padrao.length();coluna++) {
				if(caracteres_alfabeto[coluna] == alfabeto[linha])
					ap[linha][coluna] = 1;
				else
					ap[linha][coluna] = 0;
			}
		}
		
		/*for(linha=0;linha<posiçao;linha++) {
			for(coluna=0;coluna<padrao.length();coluna++) {
				System.out.print(ap[linha][coluna]);
			}
			System.out.println();
		}*/
		
		//laço para colocar a matriz ap na mti corretamente de acordo com as linhas do texto
		for(linha=0;linha<texto.length();linha++) {
			for(coluna=0;coluna<posiçao;coluna++) {
				if(caracteres_texto[linha] == alfabeto[coluna])
					for(j=0;j<padrao.length();j++) {
						mti[linha][j] = ap[coluna][j];
					}
			}
		}
		
		/*System.out.println();
		for(linha=0;linha<texto.length();linha++) {
			for(coluna=0;coluna<padrao.length();coluna++) {
				System.out.print(mti[linha][coluna]);
			}
			System.out.println();
		}*/
		
		//alocando a primeira linha do ro_and
		ro_and[0][0] = 1;
		for(coluna=1;coluna<padrao.length();coluna++) {
			ro_and[0][coluna] = 0;
		}
		
		//laço para alocar alocar o ro_atual e usar o metodo shiftand no ro_and
		for(linha=1;linha<texto.length();linha++) {
			for(coluna=0;coluna<padrao.length();coluna++) {
				ro_atual[linha-1][coluna] = ro_and[linha-1][coluna]*mti[linha-1][coluna];
			}
			for(j=padrao.length()-1;j>0;j--) {
				ro_and[linha][j] = ro_atual[linha-1][j-1];
			}
			ro_and[linha][0] = 1;
		}
		//linha de codigo para alocar a ultima linha do ro_atual
		ro_atual[texto.length()-1][padrao.length()-1] = ro_and[texto.length()-1][padrao.length()-1]*mti[texto.length()-1][padrao.length()-1];
		
		/*System.out.println();
		for(linha=0;linha<texto.length();linha++) {
			for(coluna=0;coluna<padrao.length();coluna++) {
				System.out.print(ro_atual[linha][coluna]);
			}
			System.out.println();
		}*/
		
		/*System.out.println();
		for(linha=0;linha<texto.length();linha++) {
			for(coluna=0;coluna<padrao.length();coluna++) {
				System.out.print(ro_and[linha][coluna]);
			}
			System.out.println();
		}*/
		
		//laço para conferir onde o metodo shiftand teve sucesso e tranformar os caracteres em maiusculos
		for(linha=0;linha<texto.length();linha++) {
			for(coluna=0;coluna<padrao.length();coluna++) {
				if(ro_atual[linha][padrao.length()-1] == 1) {
					aux = 1;
					for(j=linha;j>linha-padrao.length();j--) {
						caracteres_texto[j] = Character.toUpperCase(caracteres_texto[j]);
					}
				}
			}
		}
		
		//mensagem ativada se o padrão não for encontrado
		if(aux == 0) {
			System.out.println("PADRÃO NAO ENCONTRADO");
			System.exit(0); //sai da aplicação
		}
		
		texto_atualizado = String.valueOf(caracteres_texto); //tranforma vetor de char atualizado em string
		
		File arquivo2 = new File("arquivo2.txt"); //cria um arquivo novo
		
		try {
			
			//Escrever dentro do arquivo
			FileWriter fileWriter = new FileWriter(arquivo2);
			BufferedWriter escrever = new BufferedWriter(fileWriter);
			
			escrever.write(texto_atualizado);
			escrever.close();
			fileWriter.close();	
			
		}catch(IOException ex){
			
		}
		System.out.println("\n"+texto_atualizado);
		
		System.exit(0); //sai da aplicação

	}

}
