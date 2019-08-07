package digital.basto.vault.ui.view;

import androidx.annotation.Nullable;

class UnlockFormState {
    @Nullable
    private Integer passwordError;
    private boolean isDataValid;

    UnlockFormState(@Nullable Integer passwordError) {
        this.passwordError = passwordError;
        this.isDataValid = false;
    }

    UnlockFormState(boolean isDataValid) {
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}
