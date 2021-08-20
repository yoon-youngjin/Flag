package com.example.flag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flag.databinding.FragmentESportsTeamBinding


class ESportsTeamFragment : Fragment() {
    lateinit var binding:FragmentESportsTeamBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentESportsTeamBinding.inflate(layoutInflater,container,false)

        return binding.root
    }

}