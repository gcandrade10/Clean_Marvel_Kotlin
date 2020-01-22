package com.puzzlebench.clean_marvel_kotlin.presentation

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.puzzlebench.clean_marvel_kotlin.R
import com.puzzlebench.clean_marvel_kotlin.databinding.ActivityMainBinding
import com.puzzlebench.clean_marvel_kotlin.presentation.base.BaseRxActivity
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.CharacterView
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseRxActivity() {

    private val view = CharacterView(this)

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.vm = mainViewModel
        view.init()

        mainViewModel.getEvents().observe(this, Observer<MainEvents> {
            when (it) {
                ShowToastNoItemToShow -> view.showToastNoItemToShow()
                HideLoading -> view.hideLoading()
                is ShowToastNetworkError -> view.showToastNetworkError(it.message)
                is ShowCharacters -> view.showCharacters(it.characters)
                Refresh -> view.showLoading()
            }
        })
        mainViewModel.loadCharacters()
    }
}
