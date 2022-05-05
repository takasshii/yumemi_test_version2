/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.databinding.FragmentOneBinding
import jp.co.yumemi.android.code_check.domain.model.item.Item
import jp.co.yumemi.android.code_check.view.adapter.CustomAdapter
import jp.co.yumemi.android.code_check.viewModel.OneViewModel

@AndroidEntryPoint
class OneFragment : Fragment(R.layout.fragment_one) {
    private val viewModel: OneViewModel by viewModels()
    private var _binding: FragmentOneBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentOneBinding.inflate(inflater, container,false)
        binding.lifecycleOwner = this

        val layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), layoutManager.orientation)
        val adapter = CustomAdapter(object : CustomAdapter.OnItemClickListener {
            override fun itemClick(item: Item) {
                gotoRepositoryFragment(item)
            }
        })

        //EditTextを制御
        binding.searchInputText
            .setOnEditorActionListener { editText, action, _ ->
                //ENTERが押された時の処理
                if (action == EditorInfo.IME_ACTION_SEARCH) {
                    editText.text.toString().let {
                        //submitListに入力された文字を代入
                        viewModel.searchResults(it)
                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

        //liveDataで検索結果を監視
        viewModel.items.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        //recyclerViewに代入
        binding.recyclerView.also {
            it.layoutManager = layoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = adapter
        }

        return binding.root
    }

    //bindingの解放
    override fun onDestroyView() {
        super.onDestroyView()
        //bindingの解放
        _binding = null
    }

    fun gotoRepositoryFragment(item: Item) {
        val action = OneFragmentDirections
            .actionRepositoriesFragmentToRepositoryFragment(item = item)
        findNavController().navigate(action)
    }
}
