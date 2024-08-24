package com.onear.syncnotifysender.ui.SendBox;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SendBoxViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public SendBoxViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
