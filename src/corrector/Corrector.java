/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corrector;

import java.io.*;
import java.util.*;
import java.util.regex.*;

class Corrector {

    private final HashMap<String, Integer> nWords = new HashMap<String, Integer>();

    public Corrector(String file) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(file));
        Pattern p = Pattern.compile("\\w+");
        for (String temp = ""; temp != null; temp = in.readLine()) {
            Matcher m = p.matcher(temp.toLowerCase());
            while (m.find()) {
                nWords.put((temp = m.group()), nWords.containsKey(temp) ? nWords.get(temp) + 1 : 1);
            }
        }
        in.close();
    }

    private final ArrayList<String> edits(String word) {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < word.length(); ++i) {
            result.add(word.substring(0, i) + word.substring(i + 1));
        }
        for (int i = 0; i < word.length() - 1; ++i) {
            result.add(word.substring(0, i) + word.substring(i + 1, i + 2) + word.substring(i, i + 1) + word.substring(i + 2));
        }
        for (int i = 0; i < word.length(); ++i) {
            for (char c = 'a'; c <= 'z'; ++c) {
                result.add(word.substring(0, i) + String.valueOf(c) + word.substring(i + 1));
            }
        }
        for (int i = 0; i <= word.length(); ++i) {
            for (char c = 'a'; c <= 'z'; ++c) {
                result.add(word.substring(0, i) + String.valueOf(c) + word.substring(i));
            }
        }
        return result;
    }

    public final String correct(String word) {
        if (nWords.containsKey(word)) {
            return word;
        }
        ArrayList<String> list = edits(word);
        HashMap<Integer, String> candidates = new HashMap<Integer, String>();
        for (String s : list) {
            if (nWords.containsKey(s)) {
                candidates.put(nWords.get(s), s);
            }
        }
        if (candidates.size() > 0) {
            return candidates.get(Collections.max(candidates.keySet()));
        }
        for (String s : list) {
            for (String w : edits(s)) {
                if (nWords.containsKey(w)) {
                    candidates.put(nWords.get(w), w);
                }
            }
        }
        return candidates.size() > 0 ? candidates.get(Collections.max(candidates.keySet())) : word;
    }

    public static void main(String args[]) throws IOException {
        //abrirarchivo("C:\\Users\\allis\\Downloads\\userF.txt");
      /*  while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Ingrese una palabra");

            String v = sc.nextLine();
            if (v.length() > 0) {
                System.out.println((new Corrector("spanish.txt")).correct(v));
            }
        }*/
      abrirarchivo();
    }

    public static void abrirarchivo() {
        File archivo= null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File("C:\\Users\\allis\\Downloads\\userF.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] split = linea.split("\\|");
                //String a =linea[0];
                System.out.println(split[2]);
                String[] cadena=split[2].split(" ");
                for (String palabra: cadena){
                    String toLowerCase = palabra.toLowerCase();
                    System.out.println("Palabra: " + toLowerCase);
                    if(toLowerCase.length()>0){
                        
                        System.out.println("Sugerencia:" + (new Corrector("spanish.txt")).correct(toLowerCase));
                    }
                    
                }
                Scanner sc = new Scanner(System.in);
                System.out.println("Desea continuar? digite s o n");
                String v = sc.nextLine();
                if(v.equals("s"))continue;
                else{System.exit(-1);}        
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }
}
