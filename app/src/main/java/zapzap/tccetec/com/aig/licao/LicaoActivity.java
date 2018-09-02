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
        /*
        tvSubUm = findViewById(R.id.tvLicaoSubTitulo);
        tvSubDois = findViewById(R.id.tvLicaoSubTituloDois);
        tvSubTres = findViewById(R.id.tvLicaoSubTituloTres);
        tvSubQuatro = findViewById(R.id.tvLicaoSubTituloQuatro);
        tvSubCinco = findViewById(R.id.tvLicaoSubTituloCinco);
*/
        tvTextoLicao = findViewById(R.id.tvLicaoTexto);
        /*
        tvTextoDois = findViewById(R.id.tvLicaoTextoDois);
        tvTextoTres = findViewById(R.id.tvLicaoTextoTres);
        tvTextoQuatro = findViewById(R.id.tvLicaoTextoQuatro);
        tvTextoCinco = findViewById(R.id.tvLicaoTextoCinco);
        tvTextoSeis = findViewById(R.id.tvLicaoTextoSeis);
*/
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

            }else if(idSelected == 1){

                tvTextoLicao.setText(R.string.mercadodecapitais);

            }else if(idSelected == 2){

                tvTextoLicao.setText(R.string.comoinvestir);

            }else if(idSelected == 3){

                tvTextoLicao.setText(R.string.porqueabolsadevalores);

            }else if(idSelected == 4){

                tvTextoLicao.setText(R.string.comolucrar);

            }else if(idSelected == 5){

                tvTextoLicao.setText(R.string.fatoresinternos);

            }else if(idSelected == 6){

                tvTextoLicao.setText(R.string.tomardecisoes);

            }

        }else if(categoriaSelected.equals("intermediario")){

            if(idSelected == 0){

                tvTextoLicao.setText(R.string.concorrenciaNoMercado);

            }else if(idSelected == 1){

                tvTextoLicao.setText(R.string.contabilidade);

            }else if(idSelected == 2){

                tvTextoLicao.setText(R.string.conceitosgeraisdecont);

            }else if(idSelected == 3){

                tvTextoLicao.setText(R.string.evolucaodacont);

            }else if(idSelected == 4){

                tvTextoLicao.setText(R.string.finalidadedecont);

            }else if(idSelected == 5){

                tvTextoLicao.setText(R.string.campodeappdecont);

            }else if(idSelected == 6){

                tvTextoLicao.setText(R.string.principiosdecont);

            }else if(idSelected == 7){

                tvTextoLicao.setText(R.string.educacaofinanceira);

            }

        }else if(categoriaSelected.equals("avançado")){

            if(idSelected == 0){

                tvTextoLicao.setText(R.string.tesourodireto);

            }else if(idSelected == 1){

                tvTextoLicao.setText(R.string.fundosimobiliarios);

            }else if(idSelected == 2){

                tvTextoLicao.setText(R.string.debentures);

            }else if(idSelected == 3){

                tvTextoLicao.setText(R.string.letrascreditoimobiliario);

            }else if(idSelected == 4){

                tvTextoLicao.setText(R.string.letrascreditoagronegocio);

            }else if(idSelected == 5){

                tvTextoLicao.setText(R.string.comoinvestiremacoes);

            } else if (idSelected == 6) {

                tvTextoLicao.setText("Você terminou todos os textos '0'");

            }
        }

    }



}