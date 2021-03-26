package com.ondev.sharecontent

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_first).setOnClickListener {

            if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                val bm = (view.findViewById<ImageView>(R.id.img_share).drawable as BitmapDrawable).bitmap
                val intent = Intent(Intent.ACTION_SEND_MULTIPLE)
                intent.putExtra(Intent.EXTRA_TEXT, "YOUR TEXT")
                val path = MediaStore.Images.Media.insertImage(requireActivity().contentResolver, bm, "", null)
                val screenshotUri: Uri = Uri.parse(path)
                intent.putExtra(Intent.EXTRA_STREAM, screenshotUri)
                intent.type = "image/*"
                startActivity(Intent.createChooser(intent, "Share image via..."))
            } else {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            }
        }
    }
}