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
public class Inicio {
    public static void main(String[] args) {
		//**** AQUI PUEDE INTRODUCIRSE CODIGO SI SE IMPLEMENTAN OTROS MODOS DE OPERACION
		//**** HERE YOU HAVE TO CODE IF YOU WANT TO IMPLEMENT OTHERS MODES OF OPERATION
		try{
			if (args.length!=4){
				System.out.println("USO: java Cifrador.Inicio FicheroEntrada FicheroSalida clave [cifrar-ECB | cifrar-CBC | descifrar-ECB | descifrar-CBC]");
			} else {
				String entrada=args[0];
				String salida=args[1];
				byte [] clave = args[2].getBytes();
				String modo=args[3];
				byte [] resultado = null;
				byte [] bytes = Implementados.ficheroArray(entrada);
				if (modo.equals("cifrar-ECB")) {
					resultado=Cifrador_sencillo.cifrarECB(bytes, clave);
				}else if(modo.equals("descifrar-ECB")){
					resultado=Cifrador_sencillo.descifrarECB(bytes, clave);
				}
                                
                                
                               //FgdfgjddasdasdasdlfskanflsdfnosdngfsdfsdfasdaADIOOSSSSS
			
		        Implementados.arrayFichero(resultado,salida);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
