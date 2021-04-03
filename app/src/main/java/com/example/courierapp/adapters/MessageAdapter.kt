package com.example.courierapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.courierapp.R
import com.example.courierapp.models.Message


/**
 * Created by Andrey Morgunov on 03/04/2021.
 */

private const val VIEW_TYPE_COURIER_MESSAGE = 1
private const val VIEW_TYPE_CLIENT_MESSAGE = 2

class MessageAdapter(val context: Context, private val messages: MutableList<Message>) :
    RecyclerView.Adapter<MessageViewHolder>() {

    fun addMessage(message: Message) {
        messages.add(message)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].IdChatClient == null) {
            VIEW_TYPE_COURIER_MESSAGE
        } else {
            VIEW_TYPE_CLIENT_MESSAGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return if (viewType == VIEW_TYPE_COURIER_MESSAGE) {
            CourierMessageViewHolder(
                LayoutInflater.from(context)
                    .inflate(R.layout.courier_message_item_view, parent, false)
            )
        } else {
            ClientMessageViewHolder(
                LayoutInflater.from(context)
                    .inflate(R.layout.client_message_item_view, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.bind(message)
    }

    inner class CourierMessageViewHolder(view: View) : MessageViewHolder(view) {
        private var messageText: TextView = view.findViewById(R.id.courier_message_text_view)
        private var timeText: TextView = view.findViewById(R.id.courier_message_time_text_view)

        override fun bind(message: Message) {
            messageText.text = message.MessageInput
            timeText.text = message.MessageDate.toString()
        }
    }

    inner class ClientMessageViewHolder(view: View) : MessageViewHolder(view) {
        private var messageText: TextView = view.findViewById(R.id.client_message_text_view)
        private var userText: TextView = view.findViewById(R.id.client_name_text_view)
        private var timeText: TextView = view.findViewById(R.id.client_message_time_text_view)

        override fun bind(message: Message) {
            messageText.text = message.MessageInput
            userText.text = message.UserName
            timeText.text = message.MessageDate.toString()
        }
    }
}

open class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    open fun bind(message: Message) {}
}