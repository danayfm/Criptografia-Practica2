package Practica2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
/**
 *
 * @author Danay
 */
public class Cifrador {
 
        /**
         *
         */
        public Cifrador() {
                // TODO Auto-generated constructor stub
        }
        static File entrada;
        static File salida;
        static byte [] clave;
        static boolean cifrar;
        // En BYTES
        static int tamBloque = 8;
        static final int RONDAS = 8;
       
        //******************** METODOS OBLIGATORIOS (ES NECESARIO IMPLEMENTARLOS PARA APROBAR LA PRACTICA) *************************
       
       
        /**
         * Metodo que cifra todo un array de bytes segun el modo ECB (minimo OBLIGATORIO)
         * @param bytes Array de bytes a cifrar
         * @return Resultado del cifrado
         */
        static byte [] cifrarECB(byte [] bytes){
                /*
                 * Sigue la forma, conseguir bloque, separarlo en derecha e izquierda,
                 * conseguir subclave, aplicar subclave, volver a repetir 8 veces.
                 */
                byte [] resultado = new byte[bytes.length];
                byte [] bloqueEspecial = new byte[tamBloque/2];
                byte [] bloque = new byte[tamBloque];
                byte [] bloqueIzq = new byte[tamBloque/2];
                byte [] bloqueDer = new byte[tamBloque/2];
               
                System.out.println("[CifrarECB] Comienza el metodo");
                try{
                        for (int i =0; i < resultado.length/tamBloque; i++){
                                bloque = (obtenerBloque (bytes, i*8, (i*8)+8)).clone();
                                bloqueIzq = (mitadIzquierdaBloque(bloque)).clone();
                                bloqueDer = (mitadDerechaBloque(bloque)).clone();
                                for (int j = 0; j < 8; j++) {
                                        ejecutarRonda(bloqueDer, bloqueIzq, generarSubclave(clave, j+1));
                                        for (int k = 0; k < bloqueDer.length; k++) {
                                                bloqueEspecial[k]=bloqueIzq[k];
                                                bloqueIzq[k]=bloqueDer[k];
                                                bloqueDer[k]=bloqueEspecial[k];
                                               
                                        }
                                }
                                for(int k = 0; k < bytes.length/(bytes.length/tamBloque); k++){
                                        if(k<bytes.length/((bytes.length/tamBloque)*2)){
                                                resultado [k+(i*tamBloque)] = bloqueIzq[k];
                                        }else{
                                                resultado [k+(i*8)] = bloqueDer[k-bloqueIzq.length];
                                        }
                                }
                        }
                }catch(Exception e){
                                System.out.println("[CifrarECB] Problema. Cerrando...");
                                e.printStackTrace();
                        }
                System.out.println("[CifrarECB] Resultado:" + new String(resultado));
                        return resultado;
        }
       
       
        /**
         * Metodo que descifra todo un array de bytes segun el modo ECB (minimo OBLIGATORIO)
         * @param bytes Array de bytes a descifrar
         * @return Resultado del descifrado
         */
        static byte [] descifrarECB(byte [] bytes){
                byte [] resultado = new byte[bytes.length];
                byte [] bloqueEspecial = new byte[tamBloque/2];
                byte [] bloque = new byte[tamBloque];
                byte [] bloqueIzq = new byte[tamBloque/2];
                byte [] bloqueDer = new byte[tamBloque/2];
                System.out.println("[DescifrarECB] Comienza el metodo");
                try{
                        for (int i =0; i < resultado.length/tamBloque; i++){
                                bloque = (obtenerBloque (bytes, i*8, (i*8)+8)).clone();
                                bloqueIzq = (mitadIzquierdaBloque(bloque)).clone();
                                bloqueDer = (mitadDerechaBloque(bloque)).clone();
                                for (int j = 8; j > 0; j--) {
                                        ejecutarRonda(bloqueIzq, bloqueDer, generarSubclave(clave, j));
                                        for (int k = 0; k < bloqueDer.length; k++) {
                                                bloqueEspecial[k]=bloqueIzq[k];
                                                bloqueIzq[k]=bloqueDer[k];
                                                bloqueDer[k]=bloqueEspecial[k];
                                               
                                        }
                                }
                                for(int k = 0; k < bytes.length/(bytes.length/tamBloque); k++){
                                        if(k<bytes.length/((bytes.length/tamBloque)*2)){
                                                resultado [k+(i*tamBloque)] = bloqueIzq[k];
                                        }else{
                                                resultado [k+(i*8)] = bloqueDer[k-bloqueIzq.length];
                                        }
                                }
                        }
                       
                }catch(Exception e){
                                System.out.println("Problema. Cerrando...");
                                e.printStackTrace();
                        }
                System.out.println("[DescifrarECB] Resultado descifrado ECB:" + new String(resultado));
                        return resultado;
               
        }
       
        /**
         * Metodo que ejecuta una ronda de CIFRADO O DESCIFRADO
         * @param r Parte DERECHA del bloque que se va a cifrar/descifrar
         * @param l Parte IZQUIERDA del bloque que se va a cifrar/descifrar
         * @param subclave Subclave que se utilizara en esta ronda
         * @return Array de bytes con el resultado del cifrado/descifrado
         */
        static byte [] ejecutarRonda(byte [] r, byte[] l, byte [] subclave){
                byte [] resultado = new byte[r.length+l.length];
                //**** AQUI DEBE INTRODUCIRSE CODIGO *****
                // Ayuda: Consulte el mï¿½todo 'clone' de la clase Object
               
               
                byte  nueve = 9;
               
                //Por hacer, operacion xor y aï¿½adir 9 con + 0x09.
                       
                        resultado = ejecutarXOR (r,subclave).clone();
                       
                        for (int i = 0; i< resultado.length; i++){
                                resultado[i]=(byte) (resultado[i] + nueve);
                        }
                        resultado = ejecutarXOR (l, resultado).clone();
                        for (int i = 0; i< resultado.length; i++){
                                l[i]=resultado[i];
                        }      
                       
                return resultado;
        }
       
        /**
         * Desplazar a la izquierda tantos grupos de 4 bits de clave como indique vez
         * @param clave Array de bytes a desplazar
         * @param vez Numero de grupos de 4 bits a desplazar
         * @return Array de bytes conteniendo los mismos bytes que la clave, desplazando a la izquierda los correspondientes bits
         */
        static byte [] generarSubclave(byte [] clave, int vez){
               
                /*
                 *Esto crea la subclave que se usa para generar la funciï¿½n mas tarde, no se si luego se vuelve a
                 *usar la misma o se sigue usando con la generada anteriormente
                */
                byte [] resultado = new byte[clave.length];
                byte [] interClave = new byte[clave.length];
                byte [] checkClave = new byte[clave.length];
               
                for (int k2=0; k2<resultado.length; k2++)
                        resultado[k2]=clave[k2];
                for (int k=0; k<vez; k++){
                        for (int j = 0; j < clave.length; j++) {
                               
                                interClave[j]=resultado[j];
                                checkClave[j]=resultado[j];
                        }
                        for (int i = 0; i < interClave.length; i++) {
                                if(i==interClave.length-1){
                                        checkClave[i]= (byte) (interClave[i]<<4);
                                        checkClave[0]=(byte) (interClave[0]>>4);
                                        checkClave[i] += checkClave[0];
                                        resultado [i] = checkClave[i];
                                        System.out.println(resultado[i]);
                                }else{
                                        checkClave[i]= (byte) (interClave[i]<<4);
                                        checkClave[i+1]= (byte) (interClave[i+1]>>4);
                                        checkClave[i] += checkClave[i+1];
                                        resultado [i] = checkClave[i];
                                        System.out.println(resultado[i]);
                                }
                        }
                }      
                return resultado;
        }
       
       
        /**
         * Devuelve el resultado de aplicar la función F sobre bloque usando la subclave, teniendo en cuenta las simplificaciones que se han indicado.
         * @param bloque Array de bytes sobre el que se aplicara la funcion F. RECUERDE QUE LA FUNCION F SE APLICA SOBRE LA MITAD DEL BLOQUE QUE SE CONSIDERA EN LA RONDA
         * @param subclave Subclave que se va a utilizar para ejecutar la funcion F
         * @return Resultado de aplicar la funcion F sobre el bloque indicado
         */
        static byte [] ejecutarFuncionF (byte [] bloque, byte [] subclave){
                byte [] resultado = new byte[bloque.length];
                //**** AQUI DEBE INTRODUCIRSE CODIGO *****
                return resultado;
        }
       
        /**
         * Metodo que implementa la fase de sustitucion de la funcion F teniendo en cuenta las simplificaciones que se han indicado.
         * @param bloque
         * @return Devuelve el resultado de la fase de sustitución (dentro de la función F)
         */
        static byte [] sustituir(byte [] bloque){
                byte [] resultado = new byte [bloque.length];
                //**** AQUI DEBE INTRODUCIRSE CODIGO *****
                return resultado;
        }
       
       
        /**
         * Metodo que implementa la fase de permutacion de la funcion F. Permuta los bytes del bloque en un orden determinado.
         * @param bloque Array de bytes a permutar
         * @return Devuelve el resultado de permutar los bytes del bloque en un orden determinado.
         */
        static byte [] ejecutarPermutacionF (byte [] bloque){
                byte [] resultado = new byte[bloque.length];
                //**** AQUI DEBE INTRODUCIRSE CODIGO *****
                return resultado;
        }
       
       
        //******************** METODOS OPCIONALES (NO ES NECESARIO IMPLEMENTARLOS PARA SUPERAR EL CRITERIO 1) *************************
       
        /**
         * Método OPCIONAL
         * @param bytes Array de bytes a cifrar
         * @return resultado cifrado
         */
        static byte [] cifrarCBC(byte [] bytes){ return null;}
       
        /**
         * Método OPCIONAL
         * @param bytes Array de bytes a descifrar
         * @return resultado del descifrado
         */
        static byte [] descifrarCBC(byte [] bytes){return null; }
        /**
         * Metodo OPCIONAL
         * @param bloque Array de bytes a expandir
         * @return Resultado de la expansion
         */
        static byte [] expandirBloque(byte [] bloque){ return null; }
       
        //******************** METODOS YA IMPLEMENTADOS (NO REQUIEREN ATENCION POR PARTE DEL ALUMNO)   *******************************
 
        /** Metodo principal de la clase
         * @param args
         */
        public static void main(String[] args) {
                // TODO Auto-generated method stub
               
                        try{
                        //USO: java Cifrador entrada salida contraseña [cifrar-ECB | cifrar-CBC | descifrar-ECB | descifrar-CBC]
                        salida=new File(args[1]);
                        clave = args[2].getBytes();
                       
                        byte [] bytes = ficheroArray(args[0]);
                        byte [] resultado = null;
                        if(args[3].equalsIgnoreCase("cifrar-ECB")){
                                resultado=cifrarECB(bytes);
                                cifrar = true;
                        }else if(args[3].equalsIgnoreCase("cifrar-CBC")){
                                resultado=cifrarCBC(bytes);
                                cifrar = true;
                        }else if(args[3].equalsIgnoreCase("descifrar-ECB")){
                                resultado=descifrarECB(bytes);
                                cifrar = false;
                        }else{
                                //descifrar-CBC
                                resultado=descifrarCBC(bytes);
                                cifrar = false;
                        }
                        //**** AQUI DEBE INTRODUCIRSE CODIGO SI SE IMPLEMENTAN OTROS MODOS DE OPERACION
                arrayFichero(resultado);
                        }catch(Exception e){
                                System.out.println("USO: java Cifrador FicheroEntrada FicheroSalida clave [cifrar-ECB | cifrar-CBC | descifrar-ECB | descifrar-CBC]");
                                System.out.println("Cerrando...");
                                e.printStackTrace();
                        }
               
        }
        /**
         * Método YA IMPLEMENTADO
         * @param bytes Array de bytes a escribir en el fichero 'salida'
         *
         */
        static void arrayFichero(byte [] bytes){
                try{
                OutputStream os = new FileOutputStream(salida);
                os.write(bytes);
                os.close();
                }catch(Exception e){
                        System.out.println("Problema. Cerrando...");
                        e.printStackTrace();
                }
        }
       
        /**
         * Método YA IMPLEMENTADO
         * @param nombreFichero Nombre del fichero que se quiere leer
         * @return array de bytes conteniendo el texto de dicho fichero, o null en caso de excepcion
         */
        static byte [] ficheroArray (String nombreFichero){
                byte[] bytes=null;
                entrada=new File(nombreFichero);
                try{
                InputStream is = new FileInputStream(entrada);
       
        // Get the size of the file
        long length = entrada.length();
   
        // Create the byte array to hold the data
        bytes = new byte[(int)length];
        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
   
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+entrada.getName());
        }
   
        // Close the input stream and return bytes
        is.close();
        String res = new String (bytes);
        System.out.println("[DEBUG] bytes:" + res);
       
                }catch(IOException e){
                        System.out.println("Problema. Cerrando...");
                        e.printStackTrace();
            }
                return bytes;
               
        }
 
        /**
         * Metodo YA IMPLEMENTADO
         * @param a
         * @param b
         * @return Resultado de la operacion a XOR b, ejecutando BYTE A BYTE la operacion XOR
         */
        static byte [] ejecutarXOR (byte [] a, byte [] b){
                byte [] resultado = new byte [a.length];
                for(int i=0;i<resultado.length;i++){
                        resultado[i]=(byte)( a[i] ^ b[i]);
                }
                System.out.println("XOR:" + new String(a) + " ^ " + new String(b) + " = " + new String(resultado));
                return resultado;
        }
 
/**
 * Metodo YA IMPLEMENTADO
 * @param texto Array de bytes del que se quiere obtener un bloque
 * @param inicio Indice de inicio del bloque a obtener (debe ser menor que la longitud de texto)
 * @param fin Indice del byte final del trozo a obtener (debe ser, como mucho, la longitud de texto menos uno)
 * @return array de bytes del bloque
 */
        static byte [] obtenerBloque(byte [] texto, int inicio, int fin){
                int longitud = fin - inicio;
                byte [] resultado = new byte[longitud];
                for(int i = 0 ; i<longitud; i++){
                        resultado[i] = texto[inicio+i];
                }
                return resultado;
        }
 
 
        /**
         * Metodo YA IMPLEMENTADO. Devuelve la mitad izquierda del bloque que se pasa por parametro
         * @param bloque
         * @return la mitad izquierda del bloque indicado
         */
        static byte [] mitadIzquierdaBloque(byte[] bloque){
                int mitad = bloque.length/2;
                byte [] resultado = new byte[mitad];
                for(int i=0;i<mitad;i++){
                        resultado[i]=bloque[i];
                }
                System.out.println("MITAD IZQ BLOQUE:" + new String(bloque) + ": ES: " + new String(resultado));
                return resultado;
                       
        }
        /**
         * Metodo YA IMPLEMENTADO. Devuelve la mitad derecha del bloque que se pasa por parametro
         * @param bloque
         * @return la mitad derecha del bloque indicado
         */
        static byte [] mitadDerechaBloque(byte[] bloque){
                int mitad = bloque.length/2;
                byte [] resultado = new byte[mitad];
                for(int i=0;i<mitad;i++){
                        resultado[i]=bloque[mitad+i];
                }
                System.out.println("MITAD DER BLOQUE:" + new String(bloque) + ": ES: " + new String(resultado));
                return resultado;
                               
        }
}