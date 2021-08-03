package com.example.application.billsplitingapp.productList

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceManager
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.application.billsplitingapp.R
import com.example.application.billsplitingapp.allBills.AllBillsActivity
import com.example.application.billsplitingapp.models.PersonModel
import com.example.application.billsplitingapp.models.ProductModel
import com.example.application.billsplitingapp.utils.Formatter
import com.example.application.billsplitingapp.newProductDialog.NewProductDialog
import com.example.application.billsplitingapp.utils.Constants

class ProductListFragment : Fragment() {


    private lateinit var viewModel: ProductListViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductListAdapter
    private var deletionMode = false
    private lateinit var totalValue: TextView

    private var productList : List<ProductModel> = ArrayList()
    private var relationList : List<List<PersonModel>> = ArrayList()

    private lateinit var prefs : SharedPreferences
    private lateinit var editor : SharedPreferences.Editor

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
                activity!!.invalidateOptionsMenu()
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

    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs = PreferenceManager.getDefaultSharedPreferences(activity!!)
        editor = prefs.edit()
        totalValue = view.findViewById(R.id.product_total_value)
        recyclerView = view.findViewById(R.id.product_recycler)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.hasFixedSize()
        totalValue.text = Formatter.currencyFormat(prefs.getFloat(Constants.TOTAL, 0f))

        viewModel.list.observe(viewLifecycleOwner, Observer { productList ->

            view.findViewById<TextView>(R.id.product_empty_alert).visibility = if(productList.isEmpty()) View.VISIBLE else View.GONE

            this.productList = productList
            var priceList : MutableList<Float> = ArrayList()
            productList.forEach {
                priceList.add(it.price * it.amount)
            }
            if(priceList.sum() != prefs.getFloat(Constants.TOTAL, 0f)) {
                viewModel.setBillValue(priceList.sum())
                editor.putFloat(Constants.TOTAL, priceList.sum())
                editor.apply()
                totalValue.text = Formatter.currencyFormat(prefs.getFloat(Constants.TOTAL, 0f))
            }

            val rList = viewModel.getRelations(productList)
            relationList = rList

            if (!this::adapter.isInitialized) {
                adapter = ProductListAdapter(productList as MutableList<ProductModel>, relationList)
            } else {
                adapter.updateList(productList as MutableList<ProductModel>, relationList)
            }

            recyclerView.adapter = adapter

            adapter.setOnItemClickListener(object : ProductListAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    val newProductDialog =
                        NewProductDialog(
                            activity!!, viewModel.getPersonList(),
                            viewModel.getOneRelation(adapter.productList[position].id) as MutableList<Int>
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
                    activity!!.invalidateOptionsMenu()
                }

                override fun returnMode() {
                    deletionMode = false
                    activity!!.invalidateOptionsMenu()
                }

                override fun onAddClick(position: Int) {
                    viewModel.addAmount(adapter.productList[position], adapter.relationList[position])
                }

            })
        })
    }

    private fun addProduct(){
        val newProductDialog =
            NewProductDialog(
                activity!!,
                viewModel.getPersonList()
            )
        newProductDialog.show(View.OnClickListener {
            val product =
                ProductModel(newProductDialog.nameStr!!, newProductDialog.priceFloat!!, newProductDialog.amountInt!!, prefs.getInt(Constants.BILL_ID, 0))
            viewModel.insertProduct(product, newProductDialog.getSelected())
            activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
            newProductDialog.dismiss()
        })
    }

}
