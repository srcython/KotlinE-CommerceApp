package com.sercancelik.turkcellgygfinal.ui.orders

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sercancelik.turkcellgygfinal.R
import com.sercancelik.turkcellgygfinal.adapters.orderadapter.OrderAdapter
import com.sercancelik.turkcellgygfinal.adapters.orderadapter.OrderProductAdapter
import com.sercancelik.turkcellgygfinal.databinding.FragmentOrdersBinding
import com.sercancelik.turkcellgygfinal.util.JwtUtils
import com.sercancelik.turkcellgygfinal.util.KeystoreManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrdersFragment : Fragment() {

    @Inject
    lateinit var keystoreManager: KeystoreManager

    @Inject
    lateinit var jwtUtils: JwtUtils

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!
    private val ordersViewModel: OrdersViewModel by viewModels()
    private lateinit var orderAdapter: OrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderAdapter = OrderAdapter { itemView, position ->
            handleItemClick(itemView, position)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = orderAdapter
        }

        val userId = getUserIdFromJwt()
        if (userId != -1L) {
            ordersViewModel.getUserCarts(userId).observe(viewLifecycleOwner, Observer { carts ->
                if (carts != null && carts.isNotEmpty()) {
                    orderAdapter.submitList(carts)
                }
            })
        }
    }

    private fun handleItemClick(itemView: View, position: Int) {
        val hiddenView: View = itemView.findViewById(R.id.hidden_view)
        val arrowButton: ImageButton = itemView.findViewById(R.id.arrowButton)
        val cardView: View = itemView.findViewById(R.id.baseCardView)

        val cart = orderAdapter.getItem(position)

        if (hiddenView.visibility == View.VISIBLE) {
            TransitionManager.beginDelayedTransition(
                cardView as ViewGroup,
                AutoTransition().apply { duration = 500 })
            hiddenView.visibility = View.GONE
            arrowButton.setImageResource(R.drawable.ic_arrow_down)
        } else {
            val orderProductAdapter = OrderProductAdapter(cart.products)

            val recyclerView: RecyclerView = itemView.findViewById(R.id.recycler_view)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = orderProductAdapter

            TransitionManager.beginDelayedTransition(
                cardView as ViewGroup,
                AutoTransition().apply { duration = 500 })
            hiddenView.visibility = View.VISIBLE
            arrowButton.setImageResource(R.drawable.ic_arrow_up)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getUserIdFromJwt(): Long {
        val token = keystoreManager.getToken()
        return if (token != null) {
            jwtUtils.getUserIdFromToken(token) ?: -1L
        } else {
            -1L
        }
    }
}
