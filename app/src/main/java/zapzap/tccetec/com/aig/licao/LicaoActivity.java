package zapzap.tccetec.com.aig.licao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import zapzap.tccetec.com.aig.R;

public class LicaoActivity extends AppCompatActivity {

    private TextView tvTituloLicao, tvSubUm, tvSubDois, tvSubTres, tvSubQuatro, tvSubCinco;
    private TextView tvTextoLicao, tvTextoDois, tvTextoTres, tvTextoQuatro, tvTextoCinco, tvTextoSeis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licao);

        tvTituloLicao = findViewById(R.id.tvLicaoTitulo);
        tvSubUm = findViewById(R.id.tvLicaoSubTitulo);
        tvSubDois = findViewById(R.id.tvLicaoSubTituloDois);
        tvSubTres = findViewById(R.id.tvLicaoSubTituloTres);
        tvSubQuatro = findViewById(R.id.tvLicaoSubTituloQuatro);
        tvSubCinco = findViewById(R.id.tvLicaoSubTituloCinco);

        tvTextoLicao = findViewById(R.id.tvLicaoTexto);
        tvTextoDois = findViewById(R.id.tvLicaoTextoDois);
        tvTextoTres = findViewById(R.id.tvLicaoTextoTres);
        tvTextoQuatro = findViewById(R.id.tvLicaoTextoQuatro);
        tvTextoCinco = findViewById(R.id.tvLicaoTextoCinco);
        tvTextoSeis = findViewById(R.id.tvLicaoTextoSeis);

        Intent intent = getIntent();
        String titulo = intent.getExtras().getString("TituloList");

        tvTituloLicao.setText(titulo);


        Intent dadosTema = getIntent();

        //pega o id
        int idSelected = intent.getExtras().getInt("pid");
        String oporra = String.valueOf(idSelected);

        //pega a categoria
        String categoriaSelected = intent.getExtras().getString("categoria");

        tvTextoLicao.setText("Texto não alterado");

        if(categoriaSelected.equals("iniciante")){

            if(idSelected == 0){

                tvTextoLicao.setText(R.string.mercadodeacoes);

                tvSubUm.setText("Para quem o mercado de ações é interessante?");
                tvTextoDois.setText(R.string.paraquemomercado);

            }else if(idSelected == 1){

                tvTextoLicao.setText(R.string.mercadodecapitais);

            }else if(idSelected == 2){

                tvTextoLicao.setText(R.string.comoinvestir);

            }else if(idSelected == 3){

                tvTextoLicao.setText(R.string.porqueabolsadevalores);

                tvSubUm.setText("O processo de queda");
                tvTextoDois.setText(R.string.processodequeda);

                tvSubDois.setText("Notícias da mídia");
                tvTextoTres.setText(R.string.noticiasdamidia);

            }else if(idSelected == 4){

                tvTextoLicao.setText(R.string.comolucrar);

                tvSubUm.setText("Principais estratégias para ser bem sucedido na bolsa de valores");
                tvTextoDois.setText(R.string.principaisestrategy);

                tvSubDois.setText("Análise de ações");
                tvTextoTres.setText(R.string.analisedeacoes);

            }else if(idSelected == 5){

                tvTextoLicao.setText(R.string.fatoresinternos);

                tvSubUm.setText("Qual a importância de delegar ações?");
                tvTextoDois.setText(R.string.delegaracoes);

                tvSubDois.setText("A importância do funcionário saber o objetivo da empresa");
                tvTextoTres.setText(R.string.objetivoempresa);

                tvSubTres.setText("Fatores externos");
                tvTextoQuatro.setText(R.string.fatoresexternos);

            }else if(idSelected == 6){

                tvTextoLicao.setText(R.string.tomardecisoes);

                tvSubUm.setText("Reconhecendo problemas");
                tvTextoDois.setText(R.string.reconhecerproblema);

                tvSubDois.setText("Busca de informações");
                tvTextoTres.setText(R.string.bucadeinfo);

                tvSubTres.setText("Escolhas");
                tvTextoQuatro.setText(R.string.escolha);

                tvSubQuatro.setText("Compra");
                tvTextoCinco.setText(R.string.compra);

                tvSubCinco.setText("Excesso de confiança");
                tvTextoSeis.setText(R.string.excessodeconfianca);
            }

        }else if(categoriaSelected.equals("intermediario")){

        }else if(categoriaSelected.equals("avançado")){

        }

    }



}