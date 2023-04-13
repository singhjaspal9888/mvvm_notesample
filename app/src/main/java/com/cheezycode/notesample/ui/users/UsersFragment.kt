package com.cheezycode.notesample.ui.users

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.cheezycode.notesample.R
import com.cheezycode.notesample.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersFragment : Fragment() {

    private val usersViewModel by viewModels<UsersViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usersViewModel.getUsersList()
        bindObservers()
    }

    private fun bindObservers() {
        usersViewModel.getUsersLiveData.observe(viewLifecycleOwner, Observer {
            Log.d("USERSFRAGMENT","Loding........")
            when(it){
                is NetworkResult.Success ->{
                    Log.d("USERSFRAGMENT","success: "+it.data.toString())
                }
                is NetworkResult.Error ->{
                    Log.d("USERSFRAGMENT","error"+it.message)
                }
                is NetworkResult.Loading ->{
                    Log.d("USERSFRAGMENT","Loding........")
                }

            }
        })
    }


}