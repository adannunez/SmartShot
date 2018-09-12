package com.smartshot.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class ShootingViewModel : ViewModel() {
    var delaySeconds = MutableLiveData<Int>()
    var targetCount = MutableLiveData<Int>()
}