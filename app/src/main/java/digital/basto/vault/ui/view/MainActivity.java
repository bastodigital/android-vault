package digital.basto.vault.ui.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.TextView;

import digital.basto.vault.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView balanceLabel = findViewById(R.id.balanceText);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String vaultBalanceString = extras.getString("vaultBalanceString");
            balanceLabel.setText(vaultBalanceString);
        }
    }

}
