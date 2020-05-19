package com.example.application.billsplitingapp.peopleList

import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.application.billsplitingapp.R
import com.example.application.billsplitingapp.models.PersonModel
import com.example.application.billsplitingapp.utils.Formatter
import com.example.application.billsplitingapp.utils.InputDialog
import org.jetbrains.anko.find

class PersonListFragment : Fragment() {

    private lateinit var viewModel: PersonListViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PersonListAdapter
    private lateinit var prefs : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PersonListViewModel::class.java)
        viewModel.setUp()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.people_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val deleteItem = menu.findItem(R.id.menu_delete)
        deleteItem.isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_add -> addPerson()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs = PreferenceManager.getDefaultSharedPreferences(activity!!)
        view.findViewById<TextView>(R.id.people_total_value).text = Formatter.currencyFormat(prefs.getFloat("total", 0f))
        recyclerView = view.findViewById(R.id.people_recycler)
        recyclerView.layoutManager = LinearLayoutManager(activity) as RecyclerView.LayoutManager?
        recyclerView.hasFixedSize()

        viewModel.list.observe(viewLifecycleOwner, Observer { personList ->

            if(!this::adapter.isInitialized) {
                adapter = PersonListAdapter(personList as MutableList<PersonModel>)
            } else {
                adapter.updateList(personList)
            }

            recyclerView.adapter = adapter

            adapter.setOnItemClickListener(object : PersonListAdapter.OnItemClickListener{
                override fun onItemClick(position: Int) {
                    val inputDialog = InputDialog(activity!!, getString(R.string.person_dialog_edit_title))
                    inputDialog.editText = adapter.list[position].name
                    inputDialog.show(DialogInterface.OnClickListener{ dialogInterface: DialogInterface, _: Int ->
                        viewModel.editPerson(adapter.list[position].id, inputDialog.editText!!)
                        dialogInterface.dismiss()
                        activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
                    })
                }
                override fun onDeleteClick(position: Int) {
                    viewModel.deletePerson(adapter.list[position].id)
                }
            })
        })
    }
    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
    }

    private fun addPerson(){
        val inputDialog = InputDialog(activity!!, getString(R.string.dialog_person_new_title))
        inputDialog.show(DialogInterface.OnClickListener { _: DialogInterface, _: Int ->
            viewModel.insertPerson(inputDialog.editText!!)
            activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        })
    }

}
