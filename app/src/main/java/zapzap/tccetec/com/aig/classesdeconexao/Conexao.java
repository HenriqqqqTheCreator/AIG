package zapzap.tccetec.com.aig.classesdeconexao;

import java.io.BufferedReader;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Conexao {

    public static String newPostDados(String urlUsuario, String parametroUsuario){

        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder(); //Server para passar os dados e pega a url do usuario

        builder.url(urlUsuario);

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

        RequestBody body = RequestBody.create(mediaType, parametroUsuario); //Corpo de dados

        builder.post(body);

        Request request = builder.build();

        try{
            Response response = client.newCall(request).execute();

            return response.body().string();
        }catch (IOException i){
            return null;
        }finally {

        }

    }
/*
    public static String postDado(String urlUsuario, String parametroUsuario){

        URL url;
        HttpURLConnection connection = null; //Este é o cara que se conecta à rede.

            try{

                url = new URL(urlUsuario); //Recebe o endereço do usuário
                connection = (HttpURLConnection) url.openConnection(); //Abre a conexão

                connection.setRequestMethod("POST"); //Especificar para a conexão com qual metodo vamos passar as informações

                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); //Especificamos aqui, como queremos que os dados sejam codificados

                connection.setRequestProperty("Content-Lenght", "" + Integer.toString(parametroUsuario.getBytes().length));

                connection.setRequestProperty("Content-Language", "pt-BR");

                connection.setUseCaches(false); //Não salva nada no aparelho, conexão é completamente em tempo real

                connection.setDoInput(true); //Entrada e Saida / O aplicativo tanto envia quanto recebe os dados
                connection.setDoOutput(true); //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^


                //Para escrever os dados vamos utilizar
                //DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream()); //Pegamos o dado de saida e armazenamos
                //dataOutputStream.writeBytes(parametroUsuario);                                          //na conexão, escrevemos e finalizamos.
                //dataOutputStream.flush();
                //dataOutputStream.close();


                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
                outputStreamWriter.write(parametroUsuario);
                outputStreamWriter.flush();

                InputStream inputStream = connection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String linha;
                StringBuffer resposta = new StringBuffer();

                while((linha = bufferedReader.readLine()) != null){

                    resposta.append(linha);
                    resposta.append('\r');

                }

                bufferedReader.close();

                return resposta.toString();


            }catch (Exception erro){
                erro.printStackTrace();

                return null;
            }finally {

                if(connection != null){
                    connection.disconnect();
                }

            }

    }
*/
}
