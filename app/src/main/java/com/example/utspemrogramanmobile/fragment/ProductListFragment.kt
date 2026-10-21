package com.example.utspemrogramanmobile.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.utspemrogramanmobile.ProductDetailActivity
import com.example.utspemrogramanmobile.R
import com.example.utspemrogramanmobile.adapter.ProductAdapter
import com.example.utspemrogramanmobile.api.RetrofitClient
import com.example.utspemrogramanmobile.model.Product
import com.example.utspemrogramanmobile.model.ProductResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductListFragment : Fragment() {

    private lateinit var rvProducts: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvError: TextView
    private val productList = mutableListOf<Product>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvProducts = view.findViewById(R.id.rv_products)
        progressBar = view.findViewById(R.id.progress_bar)
        tvError = view.findViewById(R.id.tv_error)

        rvProducts.layoutManager = LinearLayoutManager(requireContext())
        rvProducts.adapter = ProductAdapter(productList) { product ->
            val intent = Intent(requireContext(), ProductDetailActivity::class.java)
            intent.putExtra("PRODUCT_DATA", product)
            startActivity(intent)
        }

        fetchProducts()
    }

    private fun fetchProducts() {
        progressBar.visibility = View.VISIBLE
        rvProducts.visibility = View.GONE
        tvError.visibility = View.GONE

        RetrofitClient.instance.getProducts().enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    response.body()?.let { productResponse ->
                        productList.clear()
                        productList.addAll(productResponse.products)
                        rvProducts.adapter?.notifyDataSetChanged()
                        rvProducts.visibility = View.VISIBLE
                    }
                } else {
                    showError("Gagal memuat data: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                showError("Gagal terhubung ke server: ${t.message}")
            }
        })
    }

    private fun showError(message: String) {
        tvError.text = message
        tvError.visibility = View.VISIBLE
        rvProducts.visibility = View.GONE
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}
