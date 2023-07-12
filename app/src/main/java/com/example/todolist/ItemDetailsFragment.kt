package com.example.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todolist.data.Item
import com.example.todolist.databinding.FragmentItemDetailsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class ItemDetailsFragment : Fragment() {

    private val navigationArgs: ItemDetailsFragmentArgs by navArgs()

    private val viewModel: ToDoListViewModel by activityViewModels {
        ToDoListViewModelFactory(
            (activity?.application as ToDoListApplication).database.itemDao()
        )
    }

    lateinit var item: Item

    private var _binding: FragmentItemDetailsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.itemId
        viewModel.retrieveItem(id).observe(this.viewLifecycleOwner) { selectedItem ->
            item = selectedItem
            bind(item)
        }
    }

    private fun editItem() {
        val action = ItemDetailsFragmentDirections.actionItemDetailFragmentToAddItemFragment(
            getString(R.string.edit_fragment_title),
            item.id
        )
        this.findNavController().navigate(action)
    }

    private fun deleteItem() {
        viewModel.deleteItem(item)
        findNavController().navigateUp()
    }

    private fun bind(item: Item) {
        binding.apply {
            itemName.text = item.itemTitle
            itemConent.text = item.itemContent
            itemDone.text = viewModel.printDone(item)
            btnRelist.visibility = viewModel.asKToReList(item)
            btnRelist.setOnClickListener { viewModel.reList(item) }
            itemDate.text = item.date
            saveItem.setOnClickListener {
                viewModel.markDone(item)
            }
            saveItem.isEnabled = viewModel.isItemDone(item)
            saveItem.setOnClickListener { viewModel.markDone(item) }
            deleteItem.setOnClickListener { showConfirmationDialog() }
            editItem.setOnClickListener { editItem() }
        }
    }

    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteItem()
            }
            .show()
    }

}