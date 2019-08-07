package digital.basto.vault.data;

import digital.basto.vault.data.model.VaultData;

import java.io.IOException;
import java.security.AccessControlException;

public class VaultDataSource {

    private String vaultCombination = "Subscr1be!";

    public Result<VaultData> unlock(String password) {

        try {
            if (vaultCombination.equals(password)) {
                VaultData unlockData =
                        new VaultData(
                                1337.42f);
                return new Result.Success<>(unlockData);
            } else {
                return new Result.Error(new AccessControlException("Wrong password!"));
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error unlocking view!", e));
        }
    }

    public void lock() {
    }
}
