package digital.basto.vault.ui.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.text.DecimalFormat;

import digital.basto.vault.data.VaultRepository;
import digital.basto.vault.data.Result;
import digital.basto.vault.R;
import digital.basto.vault.data.model.VaultData;

public class UnlockViewModel extends ViewModel {

    private MutableLiveData<UnlockFormState> unlockFormState = new MutableLiveData<>();
    private MutableLiveData<UnlockResult> unlockResult = new MutableLiveData<>();
    private VaultRepository vaultRepository;

    UnlockViewModel(VaultRepository vaultRepository) {
        this.vaultRepository = vaultRepository;
    }

    LiveData<UnlockFormState> getUnlockFormState() {
        return unlockFormState;
    }

    LiveData<UnlockResult> getUnlockResult() {
        return unlockResult;
    }

    public void unlock(String password) {
        Result<VaultData> result = vaultRepository.unlock(password);

        if (result instanceof Result.Success) {
            VaultData data = ((Result.Success<VaultData>) result).getData();
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            String balanceString = decimalFormat.format(data.getBalance()).replace(".", ",") + " €";
            unlockResult.setValue(new UnlockResult(new UnlockedVaultView(balanceString)));
        } else {
            unlockResult.setValue(new UnlockResult(R.string.unlock_failed));
        }
    }

    public void unlockDataChanged(String password) {
        if (!isPasswordValid(password)) {
            unlockFormState.setValue(new UnlockFormState(R.string.invalid_password));
        } else {
            unlockFormState.setValue(new UnlockFormState(true));
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 0;
    }
}
