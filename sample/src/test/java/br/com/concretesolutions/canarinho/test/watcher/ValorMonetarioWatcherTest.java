package br.com.concretesolutions.canarinho.test.watcher;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import br.com.concretesolutions.canarinho.sample.BuildConfig;
import br.com.concretesolutions.canarinho.watcher.ValorMonetarioWatcher;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.robolectric.Robolectric.buildActivity;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22)
public class ValorMonetarioWatcherTest {

    private EditText editText;

    @Before
    @SuppressLint("SetTextI18n")
    public void setUp() {
        final ActivityController<Activity> activityController = buildActivity(Activity.class);
        final Activity activity = activityController.create().get();
        activityController.start().resume().visible();

        TextInputLayout textInputLayout;
        activity.setContentView(textInputLayout = new TextInputLayout(activity));
        textInputLayout.addView(editText = new EditText(activity));
        editText.setText("0,00");
    }

    @Test
    public void watcher_formataOk() {
        editText.addTextChangedListener(new ValorMonetarioWatcher());
        editText.append("1234567890");
        assertThat(editText.getText().toString(), is("12.345.678,90"));
    }

    @Test
    public void watcher_formataOkComSimbolo() {
        editText.addTextChangedListener(new ValorMonetarioWatcher.Builder()
                .comSimboloReal()
                .comMantemZerosAoLimpar()
                .build());
        editText.append("1234567890");
        assertThat(editText.getText().toString(), is("R$ 12.345.678,90"));
    }

    @Test
    public void watcher_canEmptyTextAndKeepZeroes() {
        editText.addTextChangedListener(new ValorMonetarioWatcher.Builder()
                .comSimboloReal()
                .comMantemZerosAoLimpar()
                .build());
        editText.append("1234567890");
        assertThat(editText.getText().toString(), is("R$ 12.345.678,90"));

        editText.getText().clear();
        assertThat(editText.getText().toString(), is("R$ 0,00"));

        editText.getText().append('1');
        assertThat(editText.getText().toString(), is("R$ 0,01"));
    }

    @Test
    public void watcher_canEmptyTextWithoutZeroes() {
        editText.addTextChangedListener(new ValorMonetarioWatcher.Builder()
                .comSimboloReal()
                .build());
        editText.append("1234567890");
        assertThat(editText.getText().toString(), is("R$ 12.345.678,90"));

        editText.getText().clear();
        assertThat(editText.getText().toString(), is(""));

        editText.getText().append('1');
        assertThat(editText.getText().toString(), is("R$ 0,01"));
    }
}
