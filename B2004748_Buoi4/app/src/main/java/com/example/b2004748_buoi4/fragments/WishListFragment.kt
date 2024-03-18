package com.example.b2004748_buoi4.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.b2004748_buoi4.R
import com.example.b2004748_buoi4.adapters.WishAdapter
import com.example.b2004748_buoi4.apis.Constants
import com.example.b2004748_buoi4.databinding.FragmentWishListBinding
import com.example.b2004748_buoi4.models.RequestDeleteWish
import com.example.b2004748_buoi4.models.Wish
import com.example.b2004748_buoi4.sharedpreferences.AppSharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WishListFragment : Fragment() {
    private lateinit var binding: FragmentWishListBinding
    private lateinit var mWishList: ArrayList<Wish>
    private lateinit var mWishAdapter: WishAdapter
    private lateinit var mAppSharedPreferences: AppSharedPreferences
    private var idUser = ""
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWishListBinding.inflate(layoutInflater, container, false)
        
        //khoi tao mAppSharedPreferences va lay ra idUSer
        mAppSharedPreferences = AppSharedPreferences(requireActivity())
        idUser = mAppSharedPreferences.getIdUser("idUser").toString()
        
        //khoi tao mWishList
        mWishList = ArrayList()
        
        //call api lay danh sach dieu uoc
        getWishList()
        
        binding.btnAdd.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, AddFragment())
                .commit() //xac nhan va thay doi giao dien
        }
        
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun getWishList() {
        binding.progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch { 
            withContext(Dispatchers.Main) {
                val resp = Constants.getInstance().getWishList().body()
                if(resp != null) {
                    mWishList.addAll(resp)
                    //khoi tao adapter va thiet lap giao dien
                    initAdapterAndSetLayout()
                }
            }
        }
    }

    private fun initAdapterAndSetLayout() {
        if(context==null) {
            return
        }
        //qli ds wish
        mWishAdapter = WishAdapter(requireActivity(), mWishList, mAppSharedPreferences,
            object : WishAdapter.IClickItemWish {
                @SuppressLint("CommitTransaction")
                override fun onCLickUpdate(idWish: String, fullName: String, content: String) {
                    //luu thong tin dieu uoc vao mAppSharedPreferences va chuyen vao man hinh cap nhat dieu uoc
                    mAppSharedPreferences.putWish("idWish", idWish)
                    mAppSharedPreferences.putWish("fullName", fullName)
                    mAppSharedPreferences.putWish("content", content)
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, UpdateFragment())
                        .commit()
                }

                override fun onCLickRemove(idWish: String) {
                    //call api xoa dieu uoc
                    deleteWish(idWish)
                }
            })
        binding.rcvWishList.adapter = mWishAdapter
        binding.rcvWishList.layoutManager = LinearLayoutManager(requireActivity())
        binding.progressBar.visibility = View.GONE
    }

    private fun deleteWish(idWish: String) {
        binding.progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                val resp = Constants.getInstance().deleteWish(RequestDeleteWish(idUser, idWish))
                    .body()
                if(resp != null) {
                    if(resp.success) {
                        binding.progressBar.visibility = View.GONE
                        //xoa dieu uoc thanh cong
                        Toast.makeText(requireContext(), resp.message, Toast.LENGTH_SHORT).show()
                        //load lai man hinh WishList
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, WishListFragment())
                            .commit()
                    }
                    else {
                        binding.progressBar.visibility = View.GONE
                        //xoa dieu uoc khong thanh cong
                        Toast.makeText(requireContext(), resp.message, Toast.LENGTH_SHORT).show()
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, LoginFragment())
                            .commit()
                    }
                }
            }
        }
    }
}