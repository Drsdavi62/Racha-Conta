package com.example.application.billsplitingapp.productList

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.application.billsplitingapp.R
import com.example.application.billsplitingapp.models.ProductModel
import com.example.application.billsplitingapp.utils.NewProductDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProductListFragment : Fragment() {

    companion object {
        fun newInstance() = ProductListFragment()
    }

    private lateinit var viewModel: ProductListViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductListAdapter
    private var deletionMode = false
    private lateinit var totalValue: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val factory = ProductFactory(activity?.application!!)
        viewModel = ViewModelProvider(this).get(ProductListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.product_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete -> {
                adapter.selectedItems.forEach {
                    viewModel.deleteProduct(it.id)
                }
                adapter.selectedItems.clear()
                deletionMode = false
                ActivityCompat.invalidateOptionsMenu(activity!!)
            }
            R.id.menu_add -> {
                addProduct()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)

        val deleteItem = menu.findItem(R.id.menu_delete)
        deleteItem.isVisible = deletionMode

        val addItem = menu.findItem(R.id.menu_add)
        addItem.isVisible = !deletionMode
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        totalValue = view.findViewById(R.id.product_total_value)
        recyclerView = view.findViewById(R.id.product_recycler)
        recyclerView.layoutManager = LinearLayoutManager(activity) as RecyclerView.LayoutManager?
        recyclerView.hasFixedSize()

        viewModel.list.observe(viewLifecycleOwner, Observer { productList ->

            var priceList : MutableList<Float> = ArrayList()
            productList.forEach {
                priceList.add(it.price * it.amount)
            }
            totalValue.text = String.format("%.2f", priceList.sum())
            val relationList = viewModel.getRelations(productList)

            if (!this::adapter.isInitialized) {
                adapter = ProductListAdapter(productList as MutableList<ProductModel>, relationList)
            } else {
                adapter.updateList(productList as MutableList<ProductModel>, relationList)
            }

            recyclerView.adapter = adapter

            adapter.setOnItemClickListener(object : ProductListAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    val newProductDialog = NewProductDialog(
                        activity!!, viewModel.getPersonList(),
                        viewModel.getOneRelation(adapter.productList[position].id) as MutableList<Integer>
                    )
                    newProductDialog.nameStr = adapter.productList[position].name
                    newProductDialog.priceFloat = adapter.productList[position].price
                    newProductDialog.amountInt = adapter.productList[position].amount
                    newProductDialog.show(View.OnClickListener {
                        viewModel.editProduct(
                            adapter.productList[position].id,
                            newProductDialog.nameStr!!,
                            newProductDialog.priceFloat!!,
                            newProductDialog.amountInt!!,
                            newProductDialog.getSelected()
                        )
                        activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
                        newProductDialog.dismiss()
                    })
                }

                override fun onHold(itemView: View) {
                    itemView.setBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.colorAccent
                        )
                    )
                    deletionMode = true
                    ActivityCompat.invalidateOptionsMenu(activity!!)
                }

                override fun returnMode() {
                    deletionMode = false
                    ActivityCompat.invalidateOptionsMenu(activity!!)
                }
            })
        })
    }

    private fun addProduct(){
        val newProductDialog = NewProductDialog(activity!!, viewModel.getPersonList())
        newProductDialog.show(View.OnClickListener {
            val product =
                ProductModel(newProductDialog.nameStr!!, newProductDialog.priceFloat!!, newProductDialog.amountInt!!)
            viewModel.insertProduct(product, newProductDialog.getSelected())
            activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
            newProductDialog.dismiss()
        })
    }

}
