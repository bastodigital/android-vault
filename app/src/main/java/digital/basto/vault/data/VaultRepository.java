package digital.basto.vault.data;

import digital.basto.vault.data.model.VaultData;

public class VaultRepository {

    private static volatile VaultRepository instance;

    private VaultDataSource dataSource;
    
    private VaultData vault = null;

    // private constructor : singleton access
    private VaultRepository(VaultDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static VaultRepository getInstance(VaultDataSource dataSource) {
        if (instance == null) {
            instance = new VaultRepository(dataSource);
        }
        return instance;
    }

    public boolean isUnlocked() {
        return vault != null;
    }

    public void lock() {
        vault = null;
        dataSource.lock();
    }

    private void setVault(VaultData vault) {
        this.vault = vault;
    }

    public Result<VaultData> unlock(String password) {
        // handle view
        Result<VaultData> result = dataSource.unlock(password);
        if (result instanceof Result.Success) {
            setVault(((Result.Success<VaultData>) result).getData());
        }
        return result;
    }
}
