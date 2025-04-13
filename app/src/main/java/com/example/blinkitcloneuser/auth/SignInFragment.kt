@file:Suppress("DEPRECATION")

package com.example.blinkitcloneuser.auth

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.blinkitcloneuser.R
import com.example.blinkitcloneuser.Utils
import com.example.blinkitcloneuser.databinding.FragmentSignInBinding



class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // ????Very IMP (this binding code initiates the on create view of the fragment)
        binding = FragmentSignInBinding.inflate(layoutInflater)
        setStatusBarColor()

        getUserNumber()

        onContinueButtonClick()
        return binding.root
    }

    private fun onContinueButtonClick() {
        binding.btnContinue.setOnClickListener({
            val number = binding.etUserNumber.text.toString()

            if (number.isEmpty()||number.length !=10){
                Utils.showToast(context,"Please enter valid number")
            }else{
                val bundle = Bundle()
                bundle.putString("number",number)
                findNavController().navigate(R.id.action_signInFragment_to_OTPFragment,bundle)
            }
        })
    }

    private fun getUserNumber() {
        binding.etUserNumber.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(
                p0: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {
            }

            override fun onTextChanged(
                number: CharSequence?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {
                val len = number?.length
                if (len == 10){
                    binding.btnContinue.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green))
                }else{
                    binding.btnContinue.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.grey))
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

    }
    fun setStatusBarColor() {

        activity?.window?.apply {
            val statusBarColors = ContextCompat.getColor(requireContext(), R.color.yellow)
            statusBarColor = statusBarColors
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }
}



