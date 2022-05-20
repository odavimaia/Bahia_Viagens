package com.example.bahiaviagens.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bahiaviagens.R;
import com.example.bahiaviagens.ui.activity.model.Pacote;
import com.example.bahiaviagens.util.DataUtil;
import com.example.bahiaviagens.util.DiasUtil;
import com.example.bahiaviagens.util.MoedaUtil;
import com.example.bahiaviagens.util.ResourcesUtil;

import java.util.concurrent.atomic.AtomicReference;


public class ResumoPacoteActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Resumo do Pacote";
    public static final String CHAVE_PACOTE = "pacote";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_pacote);
        setTitle(TITULO_APPBAR);
        carregaPacoteRecebido();
    }

    private void carregaPacoteRecebido() {
        AtomicReference<Intent> intent = new AtomicReference<>(getIntent());

        if (intent.get().hasExtra(CHAVE_PACOTE)) {
            final Pacote pacote = (Pacote) intent.get().getSerializableExtra(CHAVE_PACOTE);
            inicializaCampos(pacote);
            configuraBotao(intent, pacote);
        }
    }

    private void configuraBotao(AtomicReference<Intent> intent, Pacote pacote) {
        Button botaoRealizaPagamento = findViewById(R.id.resumo_pacote_botao_realizarpagamento);
        botaoRealizaPagamento.setOnClickListener(view -> vaiParaPagamento(intent, pacote));
    }

    private void vaiParaPagamento(AtomicReference<Intent> intent, Pacote pacote) {
        intent.set(new Intent(ResumoPacoteActivity.this,
                PagamentoActivity.class));
        intent.get().putExtra(CHAVE_PACOTE, pacote);
        startActivity(intent.get());
    }

    private void inicializaCampos(Pacote pacote) {
        mostraLocal(pacote);
        mostraImagem(pacote);
        mostraDias(pacote);
        mostraPreco(pacote);
        mostraData(pacote);
    }

    private void mostraData(Pacote pacote) {
        TextView data = findViewById(R.id.resumo_pacote_periodo);
        String dataFormatadaDaViagem = DataUtil.periodoEmTexto(pacote.getDias());
        data.setText(dataFormatadaDaViagem);
    }

    private void mostraPreco(Pacote pacote) {
        TextView preco = findViewById(R.id.resumo_pacote_preco);
        String moedaBrasileira = MoedaUtil
                .formataPreco(pacote.getPreco());
        preco.setText(moedaBrasileira);
    }

    private void mostraDias(Pacote pacote) {
        TextView dias = findViewById(R.id.resumo_pacote_dias);
        String diasEmTexto = DiasUtil.formataEmTexto(pacote.getDias());
        dias.setText(diasEmTexto);
    }

    private void mostraImagem(Pacote pacote) {
        ImageView imagem = findViewById(R.id.resumo_pacote_imagem);
        Drawable drawableDoPacote = ResourcesUtil
                .devolveDrawable(this, pacote.getImagem());
        imagem.setImageDrawable(drawableDoPacote);
    }

    private void mostraLocal(Pacote pacote) {
        TextView local = findViewById(R.id.resumo_pacote_local);
        local.setText(pacote.getLocal());
    }

}