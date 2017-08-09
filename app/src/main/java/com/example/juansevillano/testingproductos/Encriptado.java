package com.example.juansevillano.testingproductos;

/**
 * Created by Juan Sevillano on 05/08/2017.
 */



public class Encriptado {

    String texto="JuanSevillano1988";

    private String getCifrado(String texto, String hashType) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance(hashType);
            byte[] array = md.digest(texto.getBytes());
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            System.err.println("Error " + e.getMessage());
        }
        return "";
    }

    public String md5() {
        return generaraleatorio()+getCifrado(texto, "MD5")+generaraleatorio();
    }


    public String generaraleatorio(){
        String numero="";
        for(int i=0;i<5;i++) {
            numero = numero+String.valueOf((int) (Math.random() * 9) + 1);
        }
        return numero;
    }

}
