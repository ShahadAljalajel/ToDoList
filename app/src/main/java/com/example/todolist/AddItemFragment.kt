package com.example.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todolist.data.Item
import com.example.todolist.databinding.FragmentAddItemBinding
import com.google.android.material.datepicker.MaterialDatePicker

class AddItemFragment : Fragment() {

    private val navigationArgs: ItemDetailsFragmentArgs by navArgs()

    private val viewModel: ToDoListViewModel by activityViewModels {
        ToDoListViewModelFactory(
            (activity?.application as ToDoListApplication).database
                .itemDao()
        )
    }

    lateinit var item: Item

    private var _binding: FragmentAddItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.itemId
        if (id > 0) {
            viewModel.retrieveItem(id).observe(this.viewLifecycleOwner) { selectedItem ->
                item = selectedItem
                bind(item)
            }
        } else {
            binding.saveAction.setOnClickListener {
                addNewItem()
            }
        }

        binding.itemDateLabel.setStartIconOnClickListener {
            datePicker()
        }
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.itemTitle.text.toString(),
            binding.itemContent.text.toString()
        )
    }

    private fun addNewItem() {
        if (isEntryValid()) {
            viewModel.addNewItem(
                binding.itemTitle.text.toString(),
                binding.itemContent.text.toString(),
                1,
                binding.itemDate.text.toString()
            )
            val action = AddItemFragmentDirections.actionAddItemFragmentToItemListFragment()
            findNavController().navigate(action)
        }
    }

    private fun bind(item: Item) {

        binding.apply {
            itemTitle.setText(item.itemTitle, TextView.BufferType.SPANNABLE)
            itemContent.setText(item.itemContent, TextView.BufferType.SPANNABLE)
            saveAction.setOnClickListener { updateItem() }
        }
    }

    private fun datePicker() {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

        fragmentManager?.let { manager ->
            datePicker.show(manager, "DatePickerDialog")
        }

        datePicker.addOnPositiveButtonClickListener { datePicked ->
            binding.itemDate.setText(
                viewModel.convertLongToTime(datePicked),
                TextView.BufferType.SPANNABLE
            )
        }
    }

    private fun updateItem() {
        if (isEntryValid()) {
            viewModel.updateItem(
                this.navigationArgs.itemId,
                this.binding.itemTitle.text.toString(),
                this.binding.itemContent.text.toString(),
                1,
                this.binding.itemDate.text.toString()
            )
            val action = AddItemFragmentDirections.actionAddItemFragmentToItemListFragment()
            findNavController().navigate(action)
        }
    }

}