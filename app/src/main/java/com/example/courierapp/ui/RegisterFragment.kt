package com.example.courierapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.courierapp.R
import com.example.courierapp.databinding.FragmentRegisterBinding
import com.example.courierapp.models.Courier
import com.example.courierapp.presentation.RegisterPresenter
import com.example.courierapp.util.showToast
import com.example.courierapp.views.RegisterView
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher


/**
 * Created by Andrey Morgunov on 11/03/2021.
 */

class RegisterFragment : MvpAppCompatFragment(R.layout.fragment_register), RegisterView {

    private val presenter: RegisterPresenter by moxyPresenter { RegisterPresenter() }
    private val binding: FragmentRegisterBinding by viewBinding()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mask = MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER)
        val watcher: FormatWatcher = MaskFormatWatcher(mask)
        watcher.installOn(binding.registerPhoneEditText)

        binding.registrationButton.setOnClickListener {
            if (!checkEmptyFields()) {
                showToast(requireContext(), R.string.fill_in_all_fields)
            } else if (binding.registerPasswordEditText.text.toString() !=
                binding.registerConfirmPasswordEditText.text.toString()
            ) {
                showToast(requireContext(), R.string.registration_passwords_mismatch)
            } else {
                presenter.signUpCourier(
                    Courier(
                        CourierName = binding.registerNameEditText.text.toString(),
                        CourierSurname = binding.registerSurnameEditText.text.toString(),
                        CourierPhone = binding.registerPhoneEditText.text.toString(),
                        CourierPassword = binding.registerPasswordEditText.text.toString()
                    )
                )
            }
        }
    }

    override fun onSuccessSignUp(isSignUp: Boolean) {
        when (isSignUp) {
            true -> {
                showToast(requireContext(), R.string.registration_success)
                requireView().findNavController().popBackStack()
            }
            false -> showToast(requireContext(), R.string.registration_user_exists)
        }
    }

    override fun showError(message: String) {
        showToast(requireContext(), message)
    }

    override fun showError(message: Int) {
        showToast(requireContext(), message)
    }

    private fun checkEmptyFields(): Boolean {
        return binding.registerNameEditText.text!!.isNotBlank() &&
                binding.registerSurnameEditText.text!!.isNotBlank() &&
                binding.registerPhoneEditText.text!!.isNotBlank() &&
                binding.registerPasswordEditText.text!!.isNotBlank() &&
                binding.registerConfirmPasswordEditText.text!!.isNotBlank()
    }
}