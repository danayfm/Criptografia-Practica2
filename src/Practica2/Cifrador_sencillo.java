package Practica2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Danay
 */
public class Cifrador_sencillo {

	// modo  True=Cifrando False=Descifrando
	// modo  True=Encrypting False=Decrypting
	
	
	
	boolean modo;
	
	
	byte [] clave;
	byte [] array;
	
	
	/*	if(modo){
			array = cifrarECB(bytes, clave);
		}else{
			array = descifrarECB(bytes, clave);
		}
		
	*/
	
	
	
	//******************** METODOS OBLIGATORIOS *************************
	//********************** MANDATORY METHODS **************************
	
	/** cifrarECB
	 * Metodo que cifra todo un array de bytes segun el modo ECB 
	 * @param bytes Array de bytes a cifrar
	 * @return Resultado del cifrado
	 * Method that encrypt one array of bytes by ECB mode
	 * @param bytes Array of byte to encrypt
	 * @return result of encryption
	 */
	public static byte [] cifrarECB(byte [] bytes, byte [] clave) throws Exception{
		
		
		
		
		return null;
	}
	
	
	/** descifrarECB
	 * Metodo que descifra todo un array de bytes segun el modo ECB
	 * @param bytes Array de bytes a descifrar
	 * @return Resultado del descifrado
	 * Method that decrypt one array of bytes by ECB mode
	 * @param bytes Array of byte to decrypt
	 * @return result of decryption
	 */
	public static byte [] descifrarECB(byte [] bytes, byte [] clave) throws Exception{

		return null;
	}
	
	/** ejecutarRonda
	 * Metodo que ejecuta una ronda de CIFRADO O DESCIFRADO
	 * @param r Parte derecha  del bloque 
	 * @param l Parte izquierda del bloque
	 * @param subclave Subclave que se utilizara en esta ronda
	 * @return Array de bytes con el resultado del cifrado/descifrado
	 * Method that runs one round of encryption/decryption
	 * @param r Right half of the block
	 * @param l Left half of the block
	 * @param subclave Subkey of this round
	 * @return Array of bytes of the result
	 */
	public static byte [] ejecutarRonda(byte [] r, byte[] l, byte [] subclave) throws Exception{
		return null;
	}
	
	/** generarSubclave
	 * Metodo que genera la subclave de la ronda especificada
	 * @param clave Array de bytes que contiene la clave
	 * @param ronda de la subclave a generar
	 * @return Array de bytes conteniendo la subclave
	 * Method that gets the subkey of the round
	 * @param clave Array of bytes that contains the key
	 * @param ronda round of the subkey to be generated
	 * @return Array of bytes that contains the subkey
	 */
	public static byte [] generarSubclave(byte [] clave, int ronda) throws Exception{
		/*
		 * Codigo de los profesores (es curioso porque segun Santi esto no viene en el codigo dado...
		 * -->de donde ha salido???)
		ronda = ronda *4;
		byte [] bloqueCircular = new byte [ronda];
		byte [] aux;
		for(int i = 0; i<ronda; i++){
			bloqueCircular[i] = clave[i];
		}
		*/
		
		// Subclave a devolver
		byte [] subClave = new byte [8];
		// Bytes desplazados
		byte [] bloqueCircular = new byte [ronda];
		// Array auxiliar que almacenara el resto de bytes
		byte [] aux = new byte [8];
		for(int i = 0; i<ronda; i++){
			// Obtenemos los bytes desplazados
			bloqueCircular[i] = clave[7-i];
			// Los primeros bytes de la subclave seran los bytes desplazados
			subClave[i] = bloqueCircular[i];
			// Las posiciones del array auxiliar que sobren seran rellenadas con 0 para evitar errores de compilacion
			aux[7-i] = 0;
		}
		// Rellenamos las posiciones validas del array auxiliar
		for(int i = 0; i<(7-ronda); i++) aux[i] = clave[i];
		// Los ultimos bytes de la subclave seran las posiciones validas del array auxiliar
		for(int i = ronda; i<8; i++) subClave[i] = aux [i-ronda];
		return subClave;
	}
	
	
	/** ejecutarFuncionF
	 * Metodo que aplica la función F
	 * @param bloque Array de bytes que contiene el bloque
	 * @param subclave Subclave que se va a utilizar para ejecutar la funcion F
	 * @return Resultado de aplicar la funcion F sobre el bloque indicado
	 * Method that runs the F function
	 * @param bloque Array of bytes of the block
	 * @param subclave subkey
	 * @return Result of the aplication of the F function
	 */
	public static byte [] ejecutarFuncionF (byte [] bloque, byte [] subclave) throws Exception{ 
		byte resultadoF[];
		// Fase de mezcla
		resultadoF = Implementados.ejecutarXOR(bloque,subclave);
		// Fase de sustitucion
		resultadoF = sustituir(resultadoF);
		// Fase de permutacion
		resultadoF = permutar(resultadoF);
		return resultadoF;
	}
	
	/** sustituir
	 * Metodo que implementa la fase de sustitucion de la funcion F.
	 * @param bloque Array de bytes que contiene el bloque 
	 * @return Devuelve el resultado de la fase de sustitución
	 * Method that implements the substitution fase of the F function
	 * @param bloque Array of bytes of the block
	 * @return Result of the substitution fase
	 */
	public static byte [] sustituir(byte [] bloque) throws Exception{ 
		// Sumamos 1 al primer byte, 2 al segundo, 3 al tercero y 4 al cuarto
		for(int i=1; i<5; i++) bloque[i-1] += (byte)i;
		return bloque;
	}	
	
	/**
	 * @param bloque Array de bytes a permutar
	 * @return Devuelve el resultado de permutar los bytes del bloque
 	 * Method that implements the permutation fase of the F function
	 * @param bloque Array of bytes of the block
	 * @return Result of the permutation fase
	 */
	public static byte [] permutar(byte [] bloque) throws Exception{ 

		return null;
	}
	
}
