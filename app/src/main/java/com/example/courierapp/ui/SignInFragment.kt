package com.example.courierapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.courierapp.R
import com.example.courierapp.databinding.FragmentSignInBinding
import com.example.courierapp.presentation.SignInPresenter
import com.example.courierapp.util.hideKeyboard
import com.example.courierapp.views.SignInView
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


/**
 * Created by Andrey Morgunov on 11/03/2021.
 */

class SignInFragment : MvpAppCompatFragment(R.layout.fragment_sign_in), SignInView {

    private val presenter: SignInPresenter by moxyPresenter { SignInPresenter() }
    private val binding: FragmentSignInBinding by viewBinding()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.singUpTextView.setOnClickListener {
            hideKeyboard()
            it.findNavController().navigate(R.id.action_signInFragment_to_registerFragment)
        }
    }

    override fun onSuccessSignIn() {
        TODO("Not yet implemented")
    }

    override fun showError(error: Throwable) {
        TODO("Not yet implemented")
    }

}