package com.example.courierapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andrognito.pinlockview.IndicatorDots
import com.andrognito.pinlockview.PinLockListener
import com.example.courierapp.R
import com.example.courierapp.databinding.FragmentPinLockBinding
import com.example.courierapp.models.Courier
import com.example.courierapp.presentation.PinLockPresenter
import com.example.courierapp.util.PreferencesManager
import com.example.courierapp.util.showToast
import com.example.courierapp.views.PinLockView
import com.google.gson.Gson
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


/**
 * Created by Andrey Morgunov on 12/03/2021.
 */

class PinLockFragment : MvpAppCompatFragment(R.layout.fragment_pin_lock), PinLockView {

    private val presenter: PinLockPresenter by moxyPresenter { PinLockPresenter() }
    private val binding: FragmentPinLockBinding by viewBinding()

    private lateinit var preferencesManager: PreferencesManager
    private lateinit var courier: Courier
    private var courierPinCode: String? = ""
    private var pinCodeOne: String = ""
    private var pinCodeTwo: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pin_lock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.indicatorDots.indicatorType = IndicatorDots.IndicatorType.FIXED
        binding.pinCode.attachIndicatorDots(binding.indicatorDots)

        preferencesManager = PreferencesManager(requireContext())
        courierPinCode = preferencesManager.getPin()
        courier = preferencesManager.getCourier()

        if (courierPinCode.isNullOrEmpty()) {
            createPinCode()
        } else {
            binding.pinCodeTitleTextView.text =
                getString(R.string.pin_code_name_title, courier.CourierName, courier.CourierSurname)
//            activity?.onBackPressedDispatcher?.addCallback(
//                viewLifecycleOwner,
//                object : OnBackPressedCallback(
//                    true
//                ) {
//                    override fun handleOnBackPressed() {
//                        val intent = Intent(Intent.ACTION_MAIN)
//                        intent.addCategory(Intent.CATEGORY_HOME)
//                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                        startActivity(intent)
//                    }
//                })
            enterPinCode()
        }
    }

    private fun enterPinCode() {
        binding.pinCode.setPinLockListener(pinLockEnterListener)
    }

    private fun createPinCode() {
        binding.pinCode.setPinLockListener(pinLockCreateListener)
    }

    private val pinLockEnterListener: PinLockListener = object : PinLockListener {
        override fun onComplete(pin: String) {
            if (pin == courierPinCode) {
                showToast(requireContext(), "Good")
                // Переход на основную страницу
            } else {
                showToast(requireContext(), getString(R.string.wrong_pin_code))
                binding.pinCode.resetPinLockView()
            }
        }

        override fun onEmpty() {}
        override fun onPinChange(pinLength: Int, intermediatePin: String) {}
    }

    private val pinLockCreateListener: PinLockListener = object : PinLockListener {
        override fun onComplete(pin: String) {
            if (pinCodeOne.isEmpty()) {
                pinCodeOne = pin
                binding.pinCodeTitleTextView.text = getString(R.string.enter_the_pin_code_again)
                binding.pinCode.resetPinLockView()
            } else {
                pinCodeTwo = pin
                if (pinCodeOne != pinCodeTwo) {
                    showToast(requireContext(), getString(R.string.pin_codes_do_not_match))
                    binding.pinCode.resetPinLockView()
                } else {
                    preferencesManager.setPin(pinCodeOne)
                    // Переход на основной экран
                }
            }

        }

        override fun onEmpty() {}
        override fun onPinChange(pinLength: Int, intermediatePin: String) {}
    }
}