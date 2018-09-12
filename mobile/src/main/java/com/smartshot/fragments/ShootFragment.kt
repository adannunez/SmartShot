package com.smartshot.fragments

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.smartshot.R
import com.smartshot.viewmodels.ShootingViewModel
import java.util.*
import java.util.logging.Logger
import kotlin.concurrent.scheduleAtFixedRate
import kotlin.math.absoluteValue

//private const val DELAY_SECONDS = "delaySeconds"
//private const val TARGET_COUNT = "targetCount"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ShootFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ShootFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ShootFragment : Fragment() {
    private var delaySeconds = 3
    private var targetCount = 0

    private lateinit var shootingViewModel: ShootingViewModel
    private var newShotTask: TimerTask? = null

    private val targetHandler = Handler { message ->
        val shotCallView = view!!.findViewById<TextView>(R.id.ShotCall)
        logger.info("Setting value to ${message.arg1}")
        shotCallView.text = message.arg1.toString()
        return@Handler true
    }

    private val logger = Logger.getLogger("ShootFragment")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shootingViewModel = ViewModelProviders.of(activity!!).get(ShootingViewModel::class.java)

        shootingViewModel.delaySeconds.observeForever { newDelaySeconds ->
            delaySeconds = newDelaySeconds!!
        }

        shootingViewModel.targetCount.observeForever { newTargetCount ->
            targetCount = newTargetCount!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_shoot, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
        logger.info("Starting shooting series for $targetCount targets every $delaySeconds seconds.")

        val shotCallView = view!!.findViewById<TextView>(R.id.ShotCall)
        newShotTask = Timer().scheduleAtFixedRate(
                delay = 1000,
                period = (delaySeconds * 1000).toLong(),
                action = {
                    val oldValue = shotCallView.text.toString().toIntOrNull()
                    var newValue = oldValue
                    while (oldValue == newValue) {
                        newValue = (Random().nextInt(targetCount)+1)
                    }
                    val message = Message()
                    message.arg1 = newValue!!
                    targetHandler.sendMessage(message)
                }
        )

        val shotDescriptionView = view!!.findViewById<TextView>(R.id.shotDescription)
        shotDescriptionView.text = "Shooting ${shootingViewModel.targetCount.value} targets every ${shootingViewModel.delaySeconds.value} seconds."
    }

    override fun onPause() {
        super.onPause()
        newShotTask?.cancel()
    }

    // TODO: Rename method, update argument and hook method into UI event
//    fun onButtonPressed(uri: Uri) {
//        listener?.onFragmentInteraction(uri)
//    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
//    }

//    override fun onDetach() {
//        super.onDetach()
//        listener = null
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
//    interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        fun onFragmentInteraction(uri: Uri)
//    }
//
//    companion object {
//        @JvmStatic
//        fun newInstance(delaySeconds: Int, targetCount: Int) =
//                ShootFragment().apply {
//                    arguments = Bundle().apply {
//                        putInt(DELAY_SECONDS, delaySeconds)
//                        putInt(TARGET_COUNT, targetCount)
//                    }
//                }
//    }
}
