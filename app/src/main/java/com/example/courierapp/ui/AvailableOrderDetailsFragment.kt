package com.example.courierapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.courierapp.MyApplication
import com.example.courierapp.R
import com.example.courierapp.databinding.FragmentAvailableOrderDetailsBinding
import com.example.courierapp.models.Order
import com.example.courierapp.presentation.AvailableOrderDetailsPresenter
import com.example.courierapp.util.PreferencesManager
import com.example.courierapp.util.showToast
import com.example.courierapp.views.OrderDetailsView
import com.google.gson.Gson
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject


/**
 * Created by Andrey Morgunov on 14/03/2021.
 */

class AvailableOrderDetailsFragment :
    MvpAppCompatFragment(R.layout.fragment_available_order_details),
    OrderDetailsView.Available {

    private val presenter: AvailableOrderDetailsPresenter by moxyPresenter { AvailableOrderDetailsPresenter() }
    private val binding: FragmentAvailableOrderDetailsBinding by viewBinding()

    @Inject
    lateinit var preferencesManager: PreferencesManager
    private lateinit var order: Order

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_available_order_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        order = Gson().fromJson(requireArguments().getString("order"), Order::class.java)
        (requireActivity().application as MyApplication).appComponent.inject(this)
        binding.takeOrderButton.setOnClickListener {
            order.CourierId = preferencesManager.getCourier()!!.CourierId
            presenter.takeOrder(order)
        }
        presenter.loadView()
    }

    override fun onSuccessTakeOrder() {
        showToast(requireContext(), "You are take this order!")
        findNavController().popBackStack()
    }

    override fun loadView() {
        with(binding) {
            orderDetailsClientTextView.text =
                getString(R.string.order_details_client_text, order.ClientName, order.ClientSurname)
            orderDetailsAddressTextView.text =
                getString(R.string.order_details_address_text, order.DeliveryAddress)
            var productString = ""
            for (product in order.Products!!) {
                productString += product.toString() + "\n"
            }
            orderDetailsProductsTextView.text = productString
            orderDetailsDescriptionTextView.text = order.OrderDescription
            orderDetailsRatingTextView.text =
                getString(R.string.rating_text, order.OrderRating.toString())
            orderDetailsRewardTextView.text =
                getString(R.string.courier_reward_text, order.CourierReward.toString())
        }
    }

    override fun showError(message: String) {
        showToast(requireContext(), message)
    }

    override fun showError(message: Int) {
        showToast(requireContext(), message)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}