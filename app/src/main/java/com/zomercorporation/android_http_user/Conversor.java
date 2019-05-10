package com.zomercorporation.android_http_user;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Conversor {


    public Pessoa getInformacao(String end){
        String json = ConexaoApi.getJsonFromApi(end);
        Pessoa retorno = parseJson(json);
        return retorno;
    }

    private Pessoa parseJson(String json){
        try {
            Pessoa pessoa = new Pessoa();

            JSONObject jsonObj = new JSONObject(json);
            JSONArray array = jsonObj.getJSONArray("results");

            JSONObject objArray = array.getJSONObject(0);

            JSONObject obj = objArray.getJSONObject("user");
            pessoa.setEmail(obj.getString("email"));
            pessoa.setUsername(obj.getString("username"));
            pessoa.setSenha(obj.getString("password"));
            pessoa.setTelefone(obj.getString("phone"));

            Date data = new Date(obj.getLong("dob")*1000);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            pessoa.setNascimento(sdf.format(data));

            //Nome da pessoa é um objeto
            JSONObject nome = obj.getJSONObject("name");
            pessoa.setNome(nome.getString("first"));
            pessoa.setSobrenome(nome.getString("last"));

            //Endereco é um Objeto
            JSONObject endereco = obj.getJSONObject("location");
            pessoa.setEndereco(endereco.getString("street"));
            pessoa.setEstado(endereco.getString("state"));
            pessoa.setCidade(endereco.getString("city"));

            //Imagem é um objeto
            JSONObject foto = obj.getJSONObject("picture");
            pessoa.setFoto(baixarImagem(foto.getString("large")));

            return pessoa;
        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    private Bitmap baixarImagem(String url) {
        //converte a imagem para o formato Bitmap
        try {
            URL endereco = new URL(url);
            InputStream inputStream = endereco.openStream();
            Bitmap imagem = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return imagem;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
