package br.com.concretesolutions.canarinho.sample.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import br.com.concretesolutions.canarinho.sample.R;
import br.com.concretesolutions.canarinho.sample.ui.model.Watchers;
import br.com.concretesolutions.canarinho.watcher.BaseCanarinhoTextWatcher;
import br.com.concretesolutions.canarinho.watcher.ValorMonetarioWatcher;

public class CanarinhoValorMonetarioWatcherFragment extends BaseWatcherFragment {

    private EditText watcherEdit;
    private TextView watcherTitle;
    private TextInputLayout watcherInputLayout;
    private TextWatcher currentWatcher;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View layout = inflater.inflate(R.layout.fragment_valor_monetario_watcher, null);
        watcherTitle = (TextView) layout.findViewById(R.id.watcher_title);
        watcherInputLayout = (TextInputLayout) layout.findViewById(R.id.edit_input_layout);
        watcherEdit = (EditText) layout.findViewById(R.id.edit_text);
        setModel(Watchers.VALOR_MONETARIO);
        bind(model);
        return layout;
    }

    public CanarinhoValorMonetarioWatcherFragment bind(Watchers model) {

        watcherTitle.setText(model.getTitle());
        watcherEdit.setHint(model.getHint());

        if (currentWatcher != null) {
            watcherEdit.removeTextChangedListener(currentWatcher);
        }

        currentWatcher = new ValorMonetarioWatcher.Builder()
                .comSimboloReal()
                .comMantemZerosAoLimpar()
                .build();
        watcherEdit.addTextChangedListener(currentWatcher);

        if (currentWatcher instanceof BaseCanarinhoTextWatcher) {
            final BaseCanarinhoTextWatcher canarinhoWatcher = (BaseCanarinhoTextWatcher) currentWatcher;
            final Watchers.SampleEventoDeValidacao eventoDeValidacao = (Watchers.SampleEventoDeValidacao) canarinhoWatcher.getEventoDeValidacao();
            eventoDeValidacao.setTextInputLayout(watcherInputLayout);
        }

        return this;
    }
}
