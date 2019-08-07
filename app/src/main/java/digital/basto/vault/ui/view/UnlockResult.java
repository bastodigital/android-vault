package digital.basto.vault.ui.view;

import androidx.annotation.Nullable;

/**
 * Authentication result : success (user details) or error message.
 */
class UnlockResult {
    @Nullable
    private UnlockedVaultView success;
    @Nullable
    private Integer error;

    UnlockResult(@Nullable Integer error) {
        this.error = error;
    }

    UnlockResult(@Nullable UnlockedVaultView success) {
        this.success = success;
    }

    @Nullable
    UnlockedVaultView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}
