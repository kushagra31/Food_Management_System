package com.example.symbibro.ui.home;

import android.widget.Button;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.symbibro.R;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;



    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");


    }

    public LiveData<String> getText() {
        return mText;
    }
}