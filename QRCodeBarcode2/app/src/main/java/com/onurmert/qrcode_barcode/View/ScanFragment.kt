package com.onurmert.qrcode_barcode.View

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import com.onurmert.qrcode_barcode.CaptureQR
import com.onurmert.qrcode_barcode.InternetControl
import com.onurmert.qrcode_barcode.databinding.FragmentScanBinding


class ScanFragment : Fragment() {

    private lateinit var binding: FragmentScanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val internetControl = InternetControl(requireContext(), view)

        internetControl.internetSnackMessage(internetControl.netControl1())

        //Click on image for qrcode
        binding.imageView2.setOnClickListener {
            try {
                scanCode()
            }catch (e : Exception){
                println(e.localizedMessage)
            }
        }
    }
    //Camera opens according to permission response
    private fun scanCode() {
        val scanOptions = ScanOptions()
        scanOptions.setBeepEnabled(true) //beep when read
        scanOptions.setOrientationLocked(true) //will work in portrait mode
        scanOptions.captureActivity = CaptureQR::class.java
        qrLauncher.launch(scanOptions)
    }
    private var qrLauncher = registerForActivityResult(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents != null){
            message(result.contents)
        }
    }
    //it will ask if you want to go to the link
    private fun message(message : String){
        val dialog =
            context?.let { AlertDialog.Builder(it) }
        dialog?.setMessage(message)

        //Clicking OK opens the link.
        dialog?.setPositiveButton(
            "Okey"
        ) { dialogInterface, i ->
            golink(message)
        }
        dialog?.show()
    }
    //Opens the link.
    private fun golink(link: String) {
        val intent = Intent(Intent.ACTION_WEB_SEARCH)
        intent.putExtra(SearchManager.QUERY, link)
        startActivity(intent)
    }
}