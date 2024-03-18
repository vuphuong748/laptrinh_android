package com.example.b2004748_buoi4.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.b2004748_buoi4.R
import com.example.b2004748_buoi4.apis.Constants
import com.example.b2004748_buoi4.databinding.FragmentLoginBinding
import com.example.b2004748_buoi4.databinding.FragmentRegisterBinding
import com.example.b2004748_buoi4.models.RequestRegisterOrLogin
import com.example.b2004748_buoi4.sharedpreferences.AppSharedPreferences
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var mAppSharedPreferences: AppSharedPreferences
    private var username = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)

        //khoi tao mAppSharedPreferences
        mAppSharedPreferences = AppSharedPreferences(requireContext())

        binding.apply {
            tvLogin.setOnClickListener{
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, LoginFragment())
                    .commit()
            }

            btnRegister.setOnClickListener {
                if(edtUsername.text.isNotEmpty()) {
                    username = edtUsername.text.toString().trim()
                    //call api dang nhap tai khoan
                    registerUser(username)
                }
                else {
                    Snackbar.make(it, "Vui lòng nhập mã số sinh viên", Snackbar.LENGTH_LONG).show()
                }
            }
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun registerUser(username: String) {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main) {
                    val resp = Constants.getInstance().registerUser(RequestRegisterOrLogin(username))
                        .body()
                    //phan hoi
                    if(resp != null) {
                        if(resp.success) {
                            //dang nhap thanh cong
                            //nhan idUser va luu vao sharedPreferences
                            mAppSharedPreferences.putIdUser("idUser", resp.idUser!!)
                            requireActivity().supportFragmentManager.beginTransaction()
                                .replace(R.id.frame_layout, WishListFragment())
                                .commit()
                            progressBar.visibility = View.GONE
                        }
                        else {
                            //dang nhap that bai
                            tvMessage.text = resp.message
                            tvMessage.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }
}