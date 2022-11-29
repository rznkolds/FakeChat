package com.rk.fakechat.ui.search

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rk.fakechat.R
import com.rk.fakechat.common.viewBinding
import com.rk.fakechat.databinding.FragmentSearchBinding

class SearchFragment : Fragment(R.layout.fragment_search) {

    private val adapter by lazy { SearchAdapter() }
    private val viewModel: SearchViewModel by viewModels()
    private val binding by viewBinding(FragmentSearchBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.onSearchClick = {
            viewModel.add(it)
        }

        binding.search.setOnClickListener {
            viewModel.search(binding.searchText.text.toString())
            hideKeyboard()
        }

        initObservers()
    }

    private fun initObservers() = viewModel.user.observe(viewLifecycleOwner) {
        binding.searchList.layoutManager = LinearLayoutManager(requireContext())
        binding.searchList.adapter = adapter
        adapter.setData(it)
    }

    private fun hideKeyboard() {
        val manager = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(binding.search.windowToken, 0)
    }
}