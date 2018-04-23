package com.grenzfrequence.githubviewcleanarchitecture.ui.base.ui

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.grenzfrequence.githubviewcleanarchitecture.ui.common.ui.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment<VIEWMODEL: BaseViewModel> : Fragment() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelFactory

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(getLayoutId(), container, false)

}