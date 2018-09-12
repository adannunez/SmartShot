package com.smartshot.fragments

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

import com.smartshot.R
import com.smartshot.viewmodels.ShootingViewModel

class SetupFragment : Fragment() {
    private lateinit var shootingViewModel: ShootingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shootingViewModel = ViewModelProviders.of(activity!!).get(ShootingViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
//        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_setup, container, false)
    }

    override fun onStart() {
        super.onStart()
        val delayView = view!!.findViewById<EditText>(R.id.delayEditText)
        delayView.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val seconds = if (s.isNullOrBlank()) 0 else s.toString().toInt()
                shootingViewModel.delaySeconds.value = seconds
            }
        })
        delayView.setText("3")

        val targetCountView = view!!.findViewById<EditText>(R.id.targetCountEditText)
        targetCountView.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val targets = if(s.isNullOrBlank()) 0 else s.toString().toInt()
                shootingViewModel.targetCount.value = targets
            }
        })
        targetCountView.setText("5")
    }
}
