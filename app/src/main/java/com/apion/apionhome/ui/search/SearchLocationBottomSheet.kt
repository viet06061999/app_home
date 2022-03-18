package com.apion.apionhome.ui.search

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import com.apion.apionhome.base.BindingFragment
import com.apion.apionhome.base.BindingFragmentBottomSheet
import com.apion.apionhome.data.model.local.ILocation
import com.apion.apionhome.databinding.BottomsheetSearchLocationBinding
import com.apion.apionhome.ui.adapter.SearchLocationAdapter
import com.apion.apionhome.utils.RxSearchView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.concurrent.TimeUnit

abstract class SearchLocationBottomSheet<L : ILocation>() :
    BindingFragment<BottomsheetSearchLocationBinding>(BottomsheetSearchLocationBinding::inflate) {

    override val viewModel by sharedViewModel<SearchViewModel>()

    private val suggestionsAdapter = SearchLocationAdapter(::onItemSearchClick)

    private var disposeable: Disposable? = null
    abstract fun getHint(): String
    abstract fun onItemClick(item: L)
    abstract fun onSearch(query: String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread {
            onSearch("")
        }.start()
    }

    override fun setupView() {
        binding.lifecycleOwner = this
        binding.searchViewModel = viewModel
        binding.recyclerView.adapter = suggestionsAdapter
        binding.search.queryHint = getHint()
        setUpSearchView()
    }

    override fun onDestroy() {
        println("on destroy")
        super.onDestroy()
        viewModel.clearSearch()
        disposeable?.dispose()
    }

    private fun onItemSearchClick(item: L) {
        onItemClick(item)
        findNavController().popBackStack()
    }

    private fun setUpSearchView() {
        binding.apply {
            textViewCancel.setOnClickListener {
                findNavController().popBackStack()
            }
            disposeable = RxSearchView.fromSearchView(search) {
                search.setQuery("", true)
                search.clearFocus()

            }
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    onSearch(it)
                }
        }
    }
}
