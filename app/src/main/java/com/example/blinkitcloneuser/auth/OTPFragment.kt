package com.example.blinkitcloneuser.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.blinkitcloneuser.R
import com.example.blinkitcloneuser.Utils
import com.example.blinkitcloneuser.activity.UsersMainActivity
import com.example.blinkitcloneuser.databinding.FragmentOTPBinding
import com.example.blinkitcloneuser.models.Users
import com.example.blinkitcloneuser.viewmodels.AuthViewModel
import kotlinx.coroutines.launch


class OTPFragment : Fragment() {

    private val viewModel: AuthViewModel by viewModels()
    private lateinit var binding: FragmentOTPBinding
    private lateinit var userNumber: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOTPBinding.inflate(layoutInflater)


        getUserNumber()
        customizingEnteringOTP()
        sendOTP()
        onBackButtonClicked()
        onLoginButtonClicked()
        return binding.root
    }

    private fun onLoginButtonClicked() {
        binding.btnLogin.setOnClickListener{
            Utils.showDialog(requireContext(),"Signing you...")
            val editTexts = arrayOf(binding.etOTP1,binding.etOTP2,binding.etOTP3,binding.etOTP4,binding.etOTP5,binding.etOTP6)
            val otp =  editTexts.joinToString(""){it.text.toString()}
            if (otp.length<editTexts.size){
                Utils.showToast(requireContext(),"Please enter the correct OTP")
            }else{
                editTexts.forEach { it.text?.clear(); it.clearFocus() }
                verifyOTP(otp)
            }
        }
    }

    private fun verifyOTP(otp: String) {

        val user = Users(uid = null, userPhoneNumber = userNumber, userAddress = null )
        viewModel.signInWithPhoneAuthCredential(otp,userNumber, user)
        lifecycleScope.launch{
            viewModel.isSignedInSuccessfully.collect{
                if (it){
                    Utils.hideDialog()
                    Utils.showToast(requireContext(),"Logged IN...")
                    startActivity(Intent(requireActivity(), UsersMainActivity::class.java))
                    requireActivity().finish()
                }
            }
        }

    }

    private fun sendOTP() {
        Utils.showDialog(requireContext(), "Sending OTP...")
        viewModel.apply { sendOTP(userNumber, requireActivity())
            lifecycleScope.launch{
                otpSent.collect{otpSent
                    if (it){
                        Utils.hideDialog()
                        Utils.showToast(requireContext(),"OTP sent successfully")
                    }
                }
            }

        }
    }

    private fun onBackButtonClicked() {
        binding.tbOtpFragment.setNavigationOnClickListener{
            findNavController().navigate(R.id.action_OTPFragment_to_signInFragment)
        }
    }

    private fun customizingEnteringOTP() {
        val editTexts = arrayOf(binding.etOTP1,binding.etOTP2,binding.etOTP3,binding.etOTP4,binding.etOTP5,binding.etOTP6)
        for (i in editTexts.indices) {
            editTexts[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?,start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?,start: Int,count: Int, end: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1){
                        if (i < editTexts.size - 1) {
                            editTexts[i + 1].requestFocus()
                        }
                    }else if (s?.length == 0){
                        if (i > 0) {
                            editTexts[i - 1].requestFocus()
                        }
                    }
                }


            })

        }


    }

    private fun getUserNumber() {
        val bundle = arguments
        userNumber = bundle?.getString("number").toString()

        binding.tvUserNumber.text = userNumber
    }

}


