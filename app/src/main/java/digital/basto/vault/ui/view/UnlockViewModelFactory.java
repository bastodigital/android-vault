package digital.basto.vault.ui.view;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import digital.basto.vault.data.VaultDataSource;
import digital.basto.vault.data.VaultRepository;

public class UnlockViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UnlockViewModel.class)) {
            return (T) new UnlockViewModel(VaultRepository.getInstance(new VaultDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
