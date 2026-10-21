package com.example.utspemrogramanmobile.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.utspemrogramanmobile.R

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ====================================================
        // TODO: Isi data profil Anda di bawah ini
        // ====================================================
        val tvName = view.findViewById<TextView>(R.id.tv_profile_name)
        val tvEmail = view.findViewById<TextView>(R.id.tv_profile_email)
        val tvPhone = view.findViewById<TextView>(R.id.tv_profile_phone)
        val tvProdi = view.findViewById<TextView>(R.id.tv_profile_prodi)
        val tvJurusan = view.findViewById<TextView>(R.id.tv_profile_jurusan)
        val tvUniversity = view.findViewById<TextView>(R.id.tv_profile_university)

        tvName.text = "MUTHOHHIRIN HAKIMI"
        tvEmail.text = "Hakimi@gmail.com"
        tvPhone.text = "08123445678"
        tvProdi.text = "D4 - Rekayasa Perangkat Lunak"
        tvJurusan.text = "TEKNIK INFORMATIKA"
        tvUniversity.text = "POLITEKNIK NEGERI BENGKALIS"
    }
}
