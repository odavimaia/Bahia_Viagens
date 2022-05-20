package com.example.bahiaviagens.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bahiaviagens.R;
import com.example.bahiaviagens.ui.activity.model.Pacote;
import com.example.bahiaviagens.util.MoedaUtil;

import java.util.concurrent.atomic.AtomicReference;

public class PagamentoActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Pagamento";
    public static final String CHAVE_PACOTE = "pacote";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);
        setTitle(TITULO_APPBAR);
        carregaPacoteRecebido();


    }

    private void carregaPacoteRecebido() {
        AtomicReference<Intent> intent = new AtomicReference<>(getIntent());
        if (intent.get().hasExtra(CHAVE_PACOTE)) {
            Pacote pacote = (Pacote) intent.get().getSerializableExtra(CHAVE_PACOTE);
            mostraPreco(pacote);
            configuraBotaoFinalizaCompra(intent, pacote);
        }
    }

    private void configuraBotaoFinalizaCompra(AtomicReference<Intent> intent, Pacote pacote) {
        Button botaoFinalizarCompra = findViewById(R.id.pagamento_botao_finalizar);
        botaoFinalizarCompra.setOnClickListener(view -> vaiParaResumoCompra(intent, pacote));
    }

    private void vaiParaResumoCompra(AtomicReference<Intent> intent, Pacote pacote) {
        intent.set(new Intent(PagamentoActivity.this,
                ResumoCompraActivity.class));
        intent.get().putExtra(CHAVE_PACOTE, pacote);
        startActivity(intent.get());
    }

    private void mostraPreco(Pacote pacoteSaoPaulo) {
        TextView preco = findViewById(R.id.pagamento_preco_pacote);
        String moedaBrasileira = MoedaUtil
                .formataPreco(pacoteSaoPaulo.getPreco());
        preco.setText(moedaBrasileira);
    }
}