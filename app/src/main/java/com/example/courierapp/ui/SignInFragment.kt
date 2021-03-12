package com.example.courierapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.courierapp.R
import com.example.courierapp.databinding.FragmentSignInBinding
import com.example.courierapp.models.Courier
import com.example.courierapp.presentation.SignInPresenter
import com.example.courierapp.util.PreferencesManager
import com.example.courierapp.util.hideKeyboard
import com.example.courierapp.util.showToast
import com.example.courierapp.views.SignInView
import com.google.gson.Gson
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher


/**
 * Created by Andrey Morgunov on 11/03/2021.
 */

class SignInFragment : MvpAppCompatFragment(R.layout.fragment_sign_in), SignInView {

    private val presenter: SignInPresenter by moxyPresenter { SignInPresenter() }
    private val binding: FragmentSignInBinding by viewBinding()
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mask = MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER)
        val watcher: FormatWatcher = MaskFormatWatcher(mask)
        watcher.installOn(binding.courierPhoneEditText)

        binding.singUpTextView.setOnClickListener {
            hideKeyboard()
            it.findNavController().navigate(R.id.action_signInFragment_to_registerFragment)
        }
        binding.forgotPasswordTextView.setOnClickListener {
            //TODO Make forgot password fragment
        }
        binding.signInButton.setOnClickListener {
            if (!checkEmptyFields()) {
                showToast(requireContext(), R.string.fill_in_all_fields)
            } else {
                presenter.signInCourier(
                    Courier(
                        CourierPhone = binding.courierPhoneEditText.text.toString(),
                        CourierPassword = binding.courierPasswordEditText.text.toString()
                    )
                )
            }
        }
    }

    override fun onSuccessSignIn(courier: Courier) {
        preferencesManager = PreferencesManager(requireContext())
        preferencesManager.setCourier(courier)

        hideKeyboard()
        requireView().findNavController()
            .navigate(R.id.action_signInFragment_to_pinLockFragment)
    }

    override fun showError(message: String) {
        showToast(requireContext(), message)
    }

    override fun showError(message: Int) {
        showToast(requireContext(), message)
    }

    private fun checkEmptyFields(): Boolean {
        return binding.courierPhoneEditText.text!!.isNotBlank() &&
                binding.courierPasswordEditText.text!!.isNotBlank()
    }
}