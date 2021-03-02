package com.dfx.puppyadoption.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PuppyDetailsVM @Inject constructor(val state: SavedStateHandle) : ViewModel() {
   fun puppyId(): Long {
       return if (state.get<Long>("puppy_id")==null) 1 else state.get<Long>("puppy_id")!!
   }
}


