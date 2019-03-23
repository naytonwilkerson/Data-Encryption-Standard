import java.io.PrintWriter;
import java.util.Scanner;

public class MyEncryption {
	
	
	
	Scanner userInput = new Scanner(System.in);
    PrintWriter pw = new PrintWriter(System.out, true);
    String chavePura = "";
    String[] RoundKeyArray = new String[16];
    String[] Keys = new String[16];
    String permutatedKey = "";
    String plainTextBinary = "";
    String finalResult = "";
    String encipher = "";
    String textEncipher = "";
    int leftSpace;
    String decipher = "";
    String binaryDecipher = "";
    String RoundKey = "";
    
    
    int vet[] = new int[64];
    
    	int[] NumLeftShifts = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};
    
    	int[] PC1 = {
	        57, 49, 41, 33, 25, 17, 9,
	        1, 58, 50, 42, 34, 26, 18,
	        10, 2, 59, 51, 43, 35, 27,
	        19, 11, 3, 60, 52, 44, 36,
	        63, 55, 47, 39, 31, 23, 15,
	        7, 62, 54, 46, 38, 30, 22,
	        14, 6, 61, 53, 45, 37, 29,
	        21, 13, 5, 28, 20, 12, 4};

	    int[] PC2 = {
	        14, 17, 11, 24, 1, 5,
	        3, 28, 15, 6, 21, 10,
	        23, 19, 12, 4, 26, 8,
	        16, 7, 27, 20, 13, 2,
	        41, 52, 31, 37, 47, 55,
	        30, 40, 51, 45, 33, 48,
	        44, 49, 39, 56, 34, 53,
	        46, 42, 50, 36, 29, 32};
	
	int [] IP  = {
	        58, 50, 42, 34, 26, 18, 10, 2,
	        60, 52, 44, 36, 28, 20, 12, 4,
	        62, 54, 46, 38, 30, 22, 14, 6,
	        64, 56, 48, 40, 32, 24, 16, 8,
	        57, 49, 41, 33, 25, 17, 9, 1,
	        59, 51, 43, 35, 27, 19, 11, 3,
	        61, 53, 45, 37, 29, 21, 13, 5,
	        63, 55, 47, 39, 31, 23, 15, 7}
	        ;
	   
	    
	    int[] FP = {
	        40, 8, 48, 16, 56, 24, 64, 32,
	        39, 7, 47, 15, 55, 23, 63, 31,
	        38, 6, 46, 14, 54, 22, 62, 30,
	        37, 5, 45, 13, 53, 21, 61, 29,
	        36, 4, 44, 12, 52, 20, 60, 28,
	        35, 3, 43, 11, 51, 19, 59, 27,
	        34, 2, 42, 10, 50, 18, 58, 26,
	        33, 1, 41, 9, 49, 17, 57, 25};
	    
	    int[] E = {
		        32, 1, 2, 3, 4, 5,
		        4, 5, 6, 7, 8, 9,
		        8, 9, 10, 11, 12, 13,
		        12, 13, 14, 15, 16, 17,
		        16, 17, 18, 19, 20, 21,
		        20, 21, 22, 23, 24, 25,
		        24, 25, 26, 27, 28, 29,
		        28, 29, 30, 31, 32, 1};
	    
	    int[][] S1 = {
		        {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
		        {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
		        {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
		        {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}};
	    
	    int[][] S2 = {
		        {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
		        {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
		        {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
		        {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}};

		    int[][] S3 = {
		        {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
		        {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
		        {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
		        {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}};

		    int[][] S4 = {
		        {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
		        {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
		        {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
		        {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}};

		    int[][] S5 = {
		        {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
		        {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
		        {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
		        {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}};

		    int[][] S6 = {
		        {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
		        {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
		        {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
		        {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}};

		    int[][] S7 = {
		        {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
		        {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
		        {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
		        {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}};

		    int[][] S8 = {
		        {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
		        {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
		        {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
		        {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}};
		    
		    int[] P = {16, 7, 20, 21,
			        29, 12, 28, 17,
			        1, 15, 23, 26,
			        5, 18, 31, 10,
			        2, 8, 24, 14,
			        32, 27, 3, 9,
			        19, 13, 30, 6,
			        22, 11, 4, 25};
	    
	    public void Encryption() {

	        try {
	        	
	        	pw.println("Entrar com chave 64-bit: ");
	            chavePura = userInput.nextLine();
	            System.out.println("Chave original em Texto: " + chavePura);
	            

	            
	            
//------------------------------------------- Encriptando chave -------------------------------------------------
	            
	            
	            String binaryKey = strTobin(chavePura);
	            
	            System.out.println("Chave pura em Binario: "+binaryKey);
	            
	            binaryKey = binaryKey.replace(" ", "");
	            
	            for (int i : PC1) {
	                permutatedKey += binaryKey.charAt(i - 1);
	            }
	            

	            //Dividindo a chave em metades esquerda e direita, LK e RK, onde cada metade tem 28 bits.
	            String LK = permutatedKey.substring(0, 28);
	            String RK = permutatedKey.substring(28, 56);
	           
	           // executando 16 turnos circulares esquerdos dos originais LK e RK
	            String Lkey = LK;
	            String Rkey = RK;
	            pw.println();
	            int Index = 0;
	            for (int a : NumLeftShifts) {
	                Lkey = CircularLeftShift(Lkey, a);
	                Rkey = CircularLeftShift(Rkey, a);
	                Keys[Index] = Lkey + Rkey;
	                Index++;
	            }
	            
	            // construindo as 16 chaves secund�rias de 48 bits usando a tabela PC-2
	            
	            Index = 0;
	            for (String key : Keys) {
	                for (int j : PC2) {
	                    RoundKey += key.charAt(j - 1);
	                }
	                RoundKeyArray[Index] = RoundKey;
	                Index++;
	                RoundKey = "";
	            }
	            
//------------------------------------Chave encriptada-----------------------------------------------------------
	        
//-----------------------------------Encriptando texto-----------------------------------------------------------
	            
	            pw.println("Entre com Texto puro: ");
	            chavePura = userInput.nextLine();

	            pw.println("Texto plano Original: " + chavePura);
	           

	          //  Converte uma seq��ncia de caracteres alfanum�ricos em seus d�gitos hexadecimais equivalentes.
	            pw.println();
	            pw.println("Texto em bin�rio: " + binaryKey);
	            binaryKey = binaryKey.replace(" ", "");
	            String BinaryText = binaryKey;
	            
	            // Usar plainTextBinary para formar um loop
	            
	            int plainTextLength = binaryKey.length();
	            if (plainTextLength < 64) {
	                pw.println("Meu texto plano " + plainTextLength +"Deve ter pelo menos 64 bits(8 caracteres)");
	                System.exit(0);
	            }else {
	            	pw.println("_________________________________________________________________________________");
	                int leftpadString = plainTextLength % 64;
	                int loop = leftpadString != 0 ? plainTextLength/64 + 1 : plainTextLength/64;
	                int start = 0;
	                int end = 64;
	                
	                for(int id = loop, wordCount = 1; id >0; id--, wordCount++) {
	                	
	                	int leftpad = 64 - leftpadString;
	                    leftSpace = leftpad % 64 != 0 ? leftpad/8 : 0;
	                    end = plainTextLength - start < 64 ? plainTextLength : end;
	                    plainTextBinary = BinaryText.substring(start, end);
	                    while(plainTextBinary.length() != 64) {
	                        plainTextBinary = "0" + plainTextBinary;
	                    }
	                    
	                    	
	                    //fazendo uma permuta��o inicial no texto simples	
	                    String IPBinary = "";
	                    for (int i : IP) {
	                        IPBinary += plainTextBinary.charAt(i - 1);
	                    }
	                    
	                    
	                   // Dividindo o bloco permutado em duas metades de 32 bits
	                    String LeftIPBinary = IPBinary.substring(0, 32);
	                    String RightIPBinary = IPBinary.substring(32, 64);
	                    
	                    
	                   
	                 //  16 chaves, por isso, 16 rodadas
	                    int counter = 1;
	                    for (String k : RoundKeyArray) {
	                        pw.println();
	                        pw.println("64-bit " + wordCount+ " Encripta��o redonda " + counter + "     ");
	                        pw.println("Chave = " + k);
	                
	                        
	                        //Bloco da esquerda torna-se o bloco da direita da rodada anterior
	                        String LeftBlock = RightIPBinary;
	                        pw.println("BLOCO ESQUERDO  = " + LeftBlock);

	                     // Bloco da direita � o bloco da esquerda 
	                        String expand = "";
	                        for (int i : E) {
	                            expand += RightIPBinary.charAt(i - 1);
	                         // Cada bloco de 4 bits originais foi expandido para um bloco de 6 bits de sa�da

	                        }
	                       
	                        // expand e a chave tem 48 bits
	                        StringBuilder sb = new StringBuilder();
	                        for (int i = 0; i < k.length(); i++) {
	                            sb.append((k.charAt(i) ^ expand.charAt(i)));
	                        }
	                        String result = sb.toString(); 
	                        
	                    
	                        // 8 grupos de 6 bits retornam como 4 bits para que o bloco Left recupere seu tamanho original de 32 bits
	                        String RB1 = result.substring(0, 6);
	                        String row1 = String.valueOf(RB1.substring(0, 1) + RB1.substring(5, 6));
	                        String col1 = String.valueOf(RB1.substring(1, 5));
	                        int target = S1[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
	                        String binaryTarget = String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');
	                        
	                        
	                        String RB2 = result.substring(6, 12);
	                        row1 = String.valueOf(RB2.substring(0, 1) + RB2.substring(5, 6));
	                        col1 = String.valueOf(RB2.substring(1, 5));
	                        target = S2[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
	                        binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

	                        String RB3 = result.substring(12, 18);
	                        row1 = String.valueOf(RB3.substring(0, 1) + RB3.substring(5, 6));
	                        col1 = String.valueOf(RB3.substring(1, 5));
	                        target = S3[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
	                        binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

	                        String RB4 = result.substring(18, 24);
	                        row1 = String.valueOf(RB4.substring(0, 1) + RB4.substring(5, 6));
	                        col1 = String.valueOf(RB4.substring(1, 5));
	                        target = S4[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
	                        binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

	                        String RB5 = result.substring(24, 30);
	                        row1 = String.valueOf(RB5.substring(0, 1) + RB5.substring(5, 6));
	                        col1 = String.valueOf(RB5.substring(1, 5));
	                        target = S5[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
	                        binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

	                        String RB6 = result.substring(30, 36);
	                        row1 = String.valueOf(RB6.substring(0, 1) + RB6.substring(5, 6));
	                        col1 = String.valueOf(RB6.substring(1, 5));
	                        target = S6[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
	                        binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

	                        String RB7 = result.substring(36, 42);
	                        row1 = String.valueOf(RB7.substring(0, 1) + RB7.substring(5, 6));
	                        col1 = String.valueOf(RB7.substring(1, 5));
	                        target = S7[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
	                        binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

	                        String RB8 = result.substring(42, 48);
	                        row1 = String.valueOf(RB8.substring(0, 1) + RB8.substring(5, 6));
	                        col1 = String.valueOf(RB8.substring(1, 5));
	                        target = S8[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
	                        binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');
	                    
	                        //pw.println(binaryTarget);
	                    
	                        //para obter f, estou permutando a sa�da da caixa-S (binaryTarget) usando a tabela P para obter o valor final
	                        
	                        String function = "";
	                        for (int d : P) {
	                            function += binaryTarget.charAt(d - 1);
	                        }
	                        
	                        sb = new StringBuilder();
	                        for (int i = 0; i < LeftIPBinary.length(); i++) {
	                            sb.append((LeftIPBinary.charAt(i) ^ function.charAt(i)));
	                        }
	                        result = sb.toString();

	                        RightIPBinary = result;
	                        pw.println("BLOCO DIREITO = " + RightIPBinary);
	                        result = "";
	                        
	                        
	                        counter++;
	                        if (counter > 16) {
	                            // Combinando reversamente os dois blocos para formar um bloco de 64 bits

	                            result = RightIPBinary + LeftBlock;
	                            
	                            finalResult = "";
	                            // Permuta��o Final FP: O inverso da permuta��o inicial IP
	                            for (int x : FP) {
	                                finalResult += result.charAt(x - 1);
	                            }
	                            encipher += finalResult;
	                            textEncipher += id == 1 && leftSpace != 0 ? intTostr(finalResult, 8).substring(leftSpace) : intTostr(finalResult, 8);  
	                        }
	                        LeftIPBinary = LeftBlock;
	                    }
	                    end += 64;
	                    start += 64;
	                    pw.println();
	                    pw.println("CIFRA DE 64-bit " + wordCount +" = " + intTostr(finalResult, 8));
	                }
	                    pw.println("\nCIFRA = " + encipher.replaceAll("(.{8})(?!$)", "$1 "));
		                pw.println("CIFRA EM TEXTO PLANO= " + textEncipher);
		                
		                
	                }
		                
		                pw.println("*****************************************************DECRIPTA��O***************************************************************");
		 	           
		                BinaryText = encipher;
		                // Usando o encipher para formar um loop
		                int encipherLength = encipher.length();
			            int leftpadString = encipherLength % 64;
			            int loop = leftpadString != 0 ? encipherLength/64 + 1 : encipherLength/64;
			            int start = 0;
			            int end = 64;
		                
			            for(int id = loop, wordCount = 1; id >0; id--,wordCount++) {
			                int leftpad = 64 - leftpadString;
			                end = encipherLength - start < 64 ? encipherLength : end;
			                String cipherTextBinary = BinaryText.substring(start, end);
			    
			    
			                 String IPBinary = "";
			                for (int i : IP) {
			                    IPBinary += cipherTextBinary.charAt(i - 1);
			                }
	                    
			                /*Dividindo o bloco permutado em duas metades de 32 bits */
			                String LeftIPBinary = IPBinary.substring(0, 32);
			                String RightIPBinary = IPBinary.substring(32, 64);
			                
			                //percorrer as 16 chaves no ORDEM REVERSO
			                int counter = 1;
			                String k;
			                for (int p = 15; p >= 0; p--) {
			                    pw.println();
			                    pw.println("                    64-bit " + wordCount+ " DECRYPTION ROUND " + counter + "                            ");
			                    k = RoundKeyArray[p];
			                    pw.println("CHAVE = " + k);

			                    //Bloco da esquerda torna-se o bloco da direita da rodada anterior

			                    String LeftBlock = RightIPBinary;
			                    pw.println("BLOCO ESQUERDO  = " + LeftBlock);

			                    String expand = "";
			                    for (int i : E) {
			                        expand += RightIPBinary.charAt(i - 1);
			                    }

			                    StringBuilder sb = new StringBuilder();
			                    for (int i = 0; i < k.length(); i++) {
			                        sb.append((k.charAt(i) ^ expand.charAt(i)));
			                    }
			                    
			                    String result = sb.toString();         

			                    //8 grupos de seis bits retornam como 4 bits para retornar ao tamanho original de 32 bits
			                    String RB1 = result.substring(0, 6);
			                    String row1 = String.valueOf(RB1.substring(0, 1) + RB1.substring(5, 6));
			                    String col1 = String.valueOf(RB1.substring(1, 5));
			                    int target = S1[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
			                    String binaryTarget = String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

			                    String RB2 = result.substring(6, 12);
			                    row1 = String.valueOf(RB2.substring(0, 1) + RB2.substring(5, 6));
			                    col1 = String.valueOf(RB2.substring(1, 5));
			                    target = S2[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
			                    binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

			                    String RB3 = result.substring(12, 18);
			                    row1 = String.valueOf(RB3.substring(0, 1) + RB3.substring(5, 6));
			                    col1 = String.valueOf(RB3.substring(1, 5));
			                    target = S3[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
			                    binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

			                    String RB4 = result.substring(18, 24);
			                    row1 = String.valueOf(RB4.substring(0, 1) + RB4.substring(5, 6));
			                    col1 = String.valueOf(RB4.substring(1, 5));
			                    target = S4[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
			                    binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

			                    String RB5 = result.substring(24, 30);
			                    row1 = String.valueOf(RB5.substring(0, 1) + RB5.substring(5, 6));
			                    col1 = String.valueOf(RB5.substring(1, 5));
			                    target = S5[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
			                    binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

			                    String RB6 = result.substring(30, 36);
			                    row1 = String.valueOf(RB6.substring(0, 1) + RB6.substring(5, 6));
			                    col1 = String.valueOf(RB6.substring(1, 5));
			                    target = S6[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
			                    binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

			                    String RB7 = result.substring(36, 42);
			                    row1 = String.valueOf(RB7.substring(0, 1) + RB7.substring(5, 6));
			                    col1 = String.valueOf(RB7.substring(1, 5));
			                    target = S7[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
			                    binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');

			                    String RB8 = result.substring(42, 48);
			                    row1 = String.valueOf(RB8.substring(0, 1) + RB8.substring(5, 6));
			                    col1 = String.valueOf(RB8.substring(1, 5));
			                    target = S8[Integer.parseInt(row1, 2)][Integer.parseInt(col1, 2)];
			                    binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');
			            
			                    String function = "";
			                    for (int d : P) {
			                        function += binaryTarget.charAt(d - 1);
			                    }
			                   
			                    sb = new StringBuilder();
			                    for (int i = 0; i < LeftIPBinary.length(); i++) {
			                        sb.append((LeftIPBinary.charAt(i) ^ function.charAt(i)));
			                    }
			                    result = sb.toString();

			                    RightIPBinary = result;
			                    pw.println("BLOCO DIREITO = " + RightIPBinary);
			                    result = "";
			                    
			                    
			                    counter++;
			                    if (counter > 16) {
			                        //Reverse the order of the two blocks to form a 64-bit block
			                        result = RightIPBinary + LeftBlock;

			                        //Final Permutation: The inverse of the initial permutation
			                        String finalResult = "";
			                        for (int x : FP) {
			                            finalResult += result.charAt(x - 1);
			                        }
			                        
			                        binaryDecipher += finalResult;
			                        decipher += id == 1 && leftSpace != 0 ? intTostr(finalResult, 8).substring(leftSpace) : intTostr(finalResult, 8);   
			                        pw.println("CIFRA DECRIPTADA DE 64-bit " + wordCount +" = " + intTostr(finalResult, 8));                     
			                    }
			                    LeftIPBinary = LeftBlock;
			                }    

			                end += 64;
			                start += 64;
			                 }
			            //Exibe o texto simples original
			            pw.println("\nCIFRA DECRIPTADA = " + binaryDecipher.replaceAll("(.{8})(?!$)", "$1 "));
			            pw.println("CIFRA DECRIPTADA EM TEXTO PLANO = " + decipher);
			                   
	        }catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	    }
	    
	    
	    //Converter string em string bin�ria
	    public String strTobin( String chavepura ) {
	        
	        byte[] bytes = chavepura.getBytes();
	        StringBuilder binary = new StringBuilder();
	        for (byte b : bytes) {
	            int val = b;
	            for (int i = 0; i < 8; i++) {
	                  binary.append((val & 128) == 0 ? 0 : 1);
	                  val <<= 1;
	            }
	            binary.append(' ');
	        }
	        return binary.toString();
	    }
	    
	    // Converte Inteiro em String
	    public String intTostr( String stream, int size ) { 
	        
	        String result = "";
	        for (int i = 0; i < stream.length(); i += size) {
	            result += (stream.substring(i, Math.min(stream.length(), i + size)) + " ");
	        }  
	        String[] ss = result.split( " " );
	        StringBuilder sb = new StringBuilder();
	        for ( int i = 0; i < ss.length; i++ ) { 
	            sb.append( (char)Integer.parseInt( ss[i], 2 ) );                                                                                                                                                        
	        }   
	        return sb.toString();
	    } 
	    
	    /*Fun��o de deslocamento � esquerda */
	    String CircularLeftShift(String s, int k) {
	        
	        String result = s.substring(k);
	        for (int i = 0; i < k; i++) {
	            result += s.charAt(i);
	        }
	        return result;
	    }


	    
	   
}
