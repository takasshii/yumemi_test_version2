/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.view.activity.TopActivity.Companion.lastSearchDate
import jp.co.yumemi.android.code_check.databinding.FragmentTwoBinding

@AndroidEntryPoint
class TwoFragment : Fragment(R.layout.fragment_two) {

    private val args: TwoFragmentArgs by navArgs()

    private var _binding: FragmentTwoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentTwoBinding.inflate(inflater, container,false)

        Log.d("検索した日時", lastSearchDate.toString())

        //OneFragmentから値を受け取る
        val item = args.item

        binding.ownerIconView.load(item.ownerIconUrl)
        binding.nameView.text = item.name
        binding.languageView.text = item.language
        binding.starsView.text = "${item.stargazersCount} stars"
        binding.watchersView.text = "${item.watchersCount} watchers"
        binding.forksView.text = "${item.forksCount} forks"
        binding.openIssuesView.text = "${item.openIssuesCount} open issues"

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
