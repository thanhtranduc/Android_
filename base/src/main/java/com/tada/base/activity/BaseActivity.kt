package com.tada.base.activity

import androidx.appcompat.app.AppCompatActivity
import com.tada.base.viewmodel.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

abstract class BaseActivity<T : BaseViewModel> : AppCompatActivity() {
    //todo
}