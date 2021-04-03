package com.example.courierapp.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.courierapp.MyApplication
import com.example.courierapp.R
import com.example.courierapp.databinding.FragmentProfileBinding
import com.example.courierapp.models.Courier
import com.example.courierapp.presentation.ProfilePresenter
import com.example.courierapp.util.*
import com.example.courierapp.views.ProfileView
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher
import java.io.ByteArrayOutputStream
import javax.inject.Inject


/**
 * Created by Andrey Morgunov on 12/03/2021.
 */

class ProfileFragment : MvpAppCompatFragment(R.layout.fragment_profile), ProfileView {

    private val presenter: ProfilePresenter by moxyPresenter { ProfilePresenter() }
    private val binding: FragmentProfileBinding by viewBinding()
    private lateinit var courier: Courier

    @Inject
    lateinit var preferencesManager: PreferencesManager
    private lateinit var loadingDialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog = loadingSpotsDialog(requireContext())
        (requireActivity().application as MyApplication).appComponent.inject(this)
        courier = preferencesManager.getCourier()!!
        presenter.getCourier(courier.CourierId)

        with(binding) {
            changeCourierDataBtn.setOnClickListener {
                presenter.updateCourierData(courier.CourierId, changedCourierData())
            }
            logOutButton.setOnClickListener {
                logOut()
            }
            changeCourierImageButton.setOnClickListener {
                getImageFromGallery()
            }
        }
    }

    private fun logOut() {
        AlertDialog.Builder(activity)
            .setMessage(R.string.dialog_logout_message)
            .setTitle(R.string.dialog_confirmation_title)
            .setPositiveButton(R.string.dialog_ok) { _, _ ->
                preferencesManager.deleteAllPreferences()
                findNavController().navigate(R.id.action_profileFragment_to_signInFragment)
            }
            .setNegativeButton(R.string.dialog_cancel, null)
            .create()
            .show()
    }

    private fun getImageFromGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri = data.data
            val inputStream = requireActivity().contentResolver.openInputStream(imageUri!!)
            val scaleBm =
                Bitmap.createScaledBitmap(
                    BitmapFactory.decodeStream(inputStream),
                    96,
                    96,
                    true
                )
            Glide.with(this)
                .load(scaleBm)
                .into(binding.courierImageView)
            val outputStream = ByteArrayOutputStream()
            scaleBm.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            courier.CourierImage = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
        }
    }

    override fun onSuccessUpdateData() {
        showToast(requireContext(), "Данные изменены!")
    }

    override fun onSuccessGetCourier(courier: Courier) {
        val mask = MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER)
        val watcher: FormatWatcher = MaskFormatWatcher(mask)
        watcher.installOn(binding.profilePhoneEditText)

        if (courier.CourierImage == null) {
            binding.courierImageView.setImageResource(R.drawable.default_user_image)
        } else {
            Glide.with(this)
                .load(Base64.decode(courier.CourierImage, Base64.DEFAULT))
                .into(binding.courierImageView)
        }
        with(binding) {
            profileNameEditText.setText(courier.CourierName)
            profileSurnameEditText.setText(courier.CourierSurname)
            profilePatronymicEditText.setText(courier.CourierPatronymic)
            profilePhoneEditText.setText(courier.CourierPhone)
            profileRatingTextView.text = getString(R.string.rating_text, courier.CourierRating.toString())
            profileRatingCountTextView.text = getString(R.string.profile_rating_count_text, courier.CourierRatingCount.toString())
            profileMoneyTextView.setText(courier.CourierMoney.toString())
        }
        preferencesManager.setCourier(courier)
    }

    override fun switchLoading(show: Boolean) {
        when (show) {
            true -> loadingDialog.show()
            false -> loadingDialog.dismiss()
        }
    }

    private fun changedCourierData(): Courier {
        with(courier) {
            CourierName = binding.profileNameEditText.text.toString()
            CourierSurname = binding.profileSurnameEditText.text.toString()
            CourierPatronymic = binding.profilePatronymicEditText.text.toString()
            CourierPhone = binding.profilePhoneEditText.text.toString()
        }
        return courier
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