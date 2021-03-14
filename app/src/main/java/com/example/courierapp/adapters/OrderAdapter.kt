package com.example.courierapp.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.courierapp.R
import com.example.courierapp.models.Order
import com.google.gson.Gson


/**
 * Created by Andrey Morgunov on 13/03/2021.
 */

class OrderAdapter(private var dataSet: List<Order>, private val active: Boolean) :
    RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val orderId: TextView = itemView.findViewById(R.id.order_id_text_view)
        private val orderAddress: TextView = itemView.findViewById(R.id.order_location_text_view)
        private val orderRating: TextView = itemView.findViewById(R.id.rating_text_view)
        private val courierReward: TextView = itemView.findViewById(R.id.order_reward_text_view)
        private val locationButton: ImageButton =
            itemView.findViewById(R.id.find_order_location_button)

        fun bind(item: Order, active: Boolean) {
            orderId.text =
                itemView.resources.getString(R.string.order_item_id, item.OrderId.toString())
            orderAddress.text = item.DeliveryAddress
            orderRating.text =
                itemView.context.getString(R.string.rating_text, item.OrderRating.toString())
            courierReward.text =
                itemView.context.getString(
                    R.string.courier_reward_text,
                    item.CourierReward.toString()
                )

            val gson = Gson()
            val orderJson = gson.toJson(item)
            val bundle = bundleOf("order" to orderJson)
            if (active) {
                itemView.setOnClickListener { view ->
                    view.findNavController().navigate(
                        R.id.action_activeOrderListFragment_to_activeOrderDetailsFragment,
                        bundle
                    )
                }
            } else {
                itemView.setOnClickListener { view ->
                    view.findNavController().navigate(
                        R.id.action_availableOrderListFragment_to_availableOrderDetailsFragment,
                        bundle
                    )
                }
            }
            locationButton.setOnClickListener {
                val gmmIntentUri = Uri.parse("geo:0,0?q=${item.DeliveryAddress}, Москва")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                ContextCompat.startActivity(itemView.context, mapIntent, null)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.order_item_view, parent, false)
                return ViewHolder(view)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = dataSet[position]
        viewHolder.bind(item, active)
    }

    override fun getItemCount() = dataSet.size
}
