package com.lear7.showcase.di

import com.lear7.showcase.viewmodel.TimeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
@author Lear
@description
@date 2019/12/27 19:19
 */
val appleModule = module {
    viewModel { TimeViewModel() }
}