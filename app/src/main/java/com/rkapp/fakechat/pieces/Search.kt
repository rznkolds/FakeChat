package com.rkapp.fakechat.pieces

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rkapp.fakechat.R
import com.rkapp.fakechat.activities.Control
import com.rkapp.fakechat.adapter.SearchAdapter
import com.rkapp.fakechat.control.Connected
import com.rkapp.fakechat.databinding.FragmentSearchBinding
import com.rkapp.fakechat.viewmodels.SearchViewModel

class Search : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val adapter by lazy { SearchAdapter ( this , this.viewLifecycleOwner ) }

    override fun onCreateView ( inflater : LayoutInflater , container: ViewGroup? , savedInstanceState : Bundle? ) : View {

        binding = FragmentSearchBinding.bind ( inflater.inflate ( R.layout.fragment_search , container , false ) )

        val network = Connected ( ).network ( this.requireContext ( ) )

        if ( !network ) {

            startActivity ( Intent ( this.requireContext ( ) , Control::class.java ) )
        }

        binding.list.layoutManager = LinearLayoutManager(requireContext())

        binding.list.adapter = adapter

        val vm = ViewModelProvider (this ) [ SearchViewModel::class.java ]

        binding.search.setOnEditorActionListener { v, position, event ->

            if ( position == EditorInfo.IME_ACTION_SEARCH ) {

                vm.searchUser ( binding.search.text.toString ( ) )

                vm.user.observe ( this.viewLifecycleOwner ) {

                        adapter.setData ( it )
                }

                keyboard ( this.requireContext ( ) , binding.search )

                true

            } else {

                false
            }
        }

        return binding.root
    }

    private fun keyboard(context: Context, view: View) {

        val imm: InputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}