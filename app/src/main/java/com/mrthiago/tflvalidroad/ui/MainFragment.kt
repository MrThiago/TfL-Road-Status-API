/*
 * Created by Thiago Gouvea 15/4/2021.
 * Copyright (c) 2021. All rights reserved.
 */

package com.mrthiago.tflvalidroad.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mrthiago.tflvalidroad.adapters.RoadStatusAdapter
import com.mrthiago.tflvalidroad.databinding.MainFragmentBinding
import com.mrthiago.tflvalidroad.domain.RoadStatus
import com.mrthiago.tflvalidroad.utilities.extensions.hideKeyboard
import com.mrthiago.tflvalidroad.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var binding: MainFragmentBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)

        binding.search.setOnClickListener {
            viewModel.getRoadById(
                binding.roadId.text.trim().toString()
            )
            activity?.hideKeyboard()
        }

        lifecycleScope.launch {
            viewModel.serviceRoadStatusData.collect { roadStatus ->
                when (roadStatus) {
                    is MainViewModel.TflDataState.Loading -> {
                        showLoading()
                    }
                    is MainViewModel.TflDataState.Success -> {
                        setUpList(binding.list, roadStatus.data)
                    }
                    MainViewModel.TflDataState.Empty -> {
                        hideView()
                    }
                    is MainViewModel.TflDataState.Failure -> {
                        showErrorMessage(roadStatus.message)
                    }
                }
            }
        }

        return binding.root
    }

    private fun showLoading() {
        // Show Progress and Hide List
        removeErrorMessage()
        binding.progressBar.visibility = View.VISIBLE
        binding.list.visibility = View.GONE
    }

    private fun showList() {
        // Hide Progress and Show List
        removeErrorMessage()
        binding.progressBar.visibility = View.GONE
        binding.list.visibility = View.VISIBLE
    }

    private fun hideView() {
        // Hide Progress and Hide List
        removeErrorMessage()
        binding.progressBar.visibility = View.GONE
        binding.list.visibility = View.GONE
    }

    private fun showErrorMessage(error: String) {
        // Show server error Message
        hideView()
        binding.errorMessage.text = error
        binding.errorMessage.visibility = View.VISIBLE
    }

    private fun removeErrorMessage() {
        // If error is shown then remove text and hide view
        if (binding.errorMessage.visibility == View.VISIBLE) {
            binding.errorMessage.text = ""
            binding.errorMessage.visibility = View.GONE
        }
    }

    private fun setUpList(view: View?, roadList: List<RoadStatus>) {
        // Setup the List view adapter and load the data
        with(view as RecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = RoadStatusAdapter(roadList)
        }
        showList()
    }
}