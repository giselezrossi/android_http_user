package com.zomercorporation.android_http_user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ConexaoApi {

    public static String getJsonFromApi(String url){
        //metodo que  conecta na api e pega o json
        String retorno = "";
        try {
            URL apiEnd = new URL(url);
            HttpURLConnection conexao;


            conexao = (HttpURLConnection) apiEnd.openConnection();
            conexao.setRequestMethod("GET");
            conexao.setReadTimeout(150000);
            conexao.setConnectTimeout(150000);
            conexao.connect();

            int codigoResposta = conexao.getResponseCode();
            InputStream is;
            if(codigoResposta == HttpURLConnection.HTTP_OK){
                is = conexao.getInputStream();
            }else{
                is = conexao.getErrorStream();
            }

            retorno = converterInputStreamToString(is);
            is.close();
            conexao.disconnect();
        } catch (Exception e){
            e.printStackTrace();
        }
        return retorno;
    }

    private static String converterInputStreamToString(InputStream is){
        StringBuffer buffer = new StringBuffer();
        try{
            BufferedReader br;
            String linha;

            br = new BufferedReader(new InputStreamReader(is));
            while((linha = br.readLine())!=null){
                buffer.append(linha);
            }

            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        return buffer.toString();
    }
}