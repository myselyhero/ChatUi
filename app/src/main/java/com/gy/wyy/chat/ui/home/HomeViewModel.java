package com.gy.wyy.chat.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> liveData;

    public HomeViewModel() {
        liveData = new MutableLiveData<>();
        liveData.setValue("This is home fragment");
    }

    /**
     *
     * @return
     */
    public MutableLiveData<String> getLiveData() {
        return liveData;
    }
}