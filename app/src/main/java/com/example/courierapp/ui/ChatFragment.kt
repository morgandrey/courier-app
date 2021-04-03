package com.example.courierapp.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.courierapp.R
import com.example.courierapp.adapters.MessageAdapter
import com.example.courierapp.databinding.FragmentChatBinding
import com.example.courierapp.models.Message
import com.example.courierapp.presentation.ChatPresenter
import com.example.courierapp.util.showToast
import com.example.courierapp.views.ChatView
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


/**
 * Created by Andrey Morgunov on 03/04/2021.
 */

class ChatFragment : MvpAppCompatFragment(R.layout.fragment_chat), ChatView {

    private val presenter: ChatPresenter by moxyPresenter { ChatPresenter() }
    private lateinit var adapter: MessageAdapter
    private lateinit var messageList: MutableList<Message>
    private val binding: FragmentChatBinding by viewBinding()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val orderId = requireArguments().getLong("orderId")
        presenter.getMessageList(orderId)

        binding.buttonSend.setOnClickListener {
            val idChatClient = messageList[0].IdChatClient!!
            val message = Message(
                MessageDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm"))
                    .toString(),
                MessageInput = binding.enterMessageEditText.text.toString(),
                MessageFrom = "Курьер",
                MessageTo = "Клиент",
                IdOrder = orderId
            )
            presenter.sendMessage(idChatClient, binding.enterMessageEditText.text.toString())
            presenter.saveMessage(message)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onSuccessSaveMessage(message: Message) {
        adapter.addMessage(message)
        binding.messageRecyclerView.scrollToPosition(adapter.itemCount - 1)
    }

    override fun onSuccessGetMessageList(messageList: MutableList<Message>) {
        this.messageList = messageList
        if (messageList.isEmpty()) {
            binding.messageRecyclerView.visibility = View.INVISIBLE
            binding.buttonSend.isEnabled = false
        } else {
            binding.messageRecyclerView.visibility = View.VISIBLE
            binding.buttonSend.isEnabled = true

            binding.messageRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            adapter = MessageAdapter(requireContext(), messageList)
            binding.messageRecyclerView.adapter = adapter
        }
    }

    override fun showError(message: String) {
        showToast(requireContext(), message)
    }

    override fun showError(message: Int) {
        showToast(requireContext(), message)
    }
}