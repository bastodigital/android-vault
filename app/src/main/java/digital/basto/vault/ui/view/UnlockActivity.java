package digital.basto.vault.ui.view;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import digital.basto.vault.R;

public class UnlockActivity extends AppCompatActivity {

    private UnlockViewModel unlockViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock);
        unlockViewModel = ViewModelProviders.of(this, new UnlockViewModelFactory())
                .get(UnlockViewModel.class);

        final EditText passwordEditText = findViewById(R.id.password);
        final Button unlockButton = findViewById(R.id.unlock);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        unlockViewModel.getUnlockFormState().observe(this, new Observer<UnlockFormState>() {
            @Override
            public void onChanged(@Nullable UnlockFormState unlockFormState) {
                if (unlockFormState == null) {
                    return;
                }
                unlockButton.setEnabled(unlockFormState.isDataValid());
                if (unlockFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(unlockFormState.getPasswordError()));
                }
            }
        });

        unlockViewModel.getUnlockResult().observe(this, new Observer<UnlockResult>() {
            @Override
            public void onChanged(@Nullable UnlockResult unlockResult) {
                if (unlockResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (unlockResult.getError() != null) {
                    showUnlockFailed(unlockResult.getError());
                    return;
                }
                if (unlockResult.getSuccess() != null) {
                    updateUiWithVaultData(unlockResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                unlockViewModel.unlockDataChanged(
                        passwordEditText.getText().toString());
            }
        };
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }
        });

        unlockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                unlockViewModel.unlock(
                        passwordEditText.getText().toString());
            }
        });
    }

    private void updateUiWithVaultData(UnlockedVaultView model) {
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("vaultBalanceString", model.getDisplayBalance());
        startActivity(intent);
    }

    private void showUnlockFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
