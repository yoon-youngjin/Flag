package com.example.flag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flag.databinding.FragmentSportsTeamBinding


class SportsTeamFragment : Fragment() {
    lateinit var binding: FragmentSportsTeamBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentSportsTeamBinding.inflate(layoutInflater,container,false)

        return binding.root
    }

}