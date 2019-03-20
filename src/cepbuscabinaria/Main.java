package cepbuscabinaria;

import java.io.RandomAccessFile;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception 
	{
		
		RandomAccessFile f = new RandomAccessFile("cep_ordenado.dat", "r");
		Endereco e = new Endereco();
		
		System.out.print("Digite um CEP a ser buscado (apenas numeros): ");
		Scanner sc = new Scanner(System.in);
		String cepDigit = sc.nextLine();
		
		long inicio = 0;
		long fim = ((f.length())/300);
		long meio = 0;
		boolean encontra = false;
		
		while(inicio <= fim && encontra == false) {
			meio = (inicio + fim)/2;
			
			f.seek(meio*300);
			e.leEndereco(f);
			
			long cepArquiConv = Long.parseLong(e.getCep());
			long cepDigitConv = Long.parseLong(cepDigit);
			
			if(cepArquiConv == cepDigitConv) {
				encontra = true;
			} else if(cepArquiConv < cepDigitConv) {
				inicio = meio + 1;
			} else if(cepArquiConv > cepDigitConv) {
				fim = meio - 1;
			}
		
		}
		
		if(encontra == true) {
			
			while((f.getFilePointer() - 1) < f.length()) { 	// para Detectar EOF
				System.out.println("CEP encontrado.");
				f.seek(f.getFilePointer()-1*300L);
				System.out.print("Posição: ");
				System.out.println(f.getFilePointer()/300);	
				e.leEndereco(f);
				System.out.print("Endereço: ");
				System.out.println(e.getLogradouro());
				System.out.print("Bairro: ");
				System.out.println(e.getBairro());
				System.out.print("Cidade: ");
				System.out.println(e.getCidade());
				System.out.print("Estado: ");
				System.out.println(e.getEstado());
				System.out.print("UF: ");
				System.out.println(e.getSigla());
				System.out.print("CEP: ");
				System.out.println(e.getCep());
				break;
			}
		} else {
			System.out.println("CEP não encontrado.");
		}
		
		f.close();
		sc.close();
	}
}
