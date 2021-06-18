package com.krunal.cowinautobook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.krunal.cowinautobook.R
import com.krunal.cowinautobook.models.Beneficiary

class BenificiaryAdapter(
    context: Context,
    private val resources: Int,
    beneficiary: ArrayList<Beneficiary>,
    private val listener: BeneficiaryCbListener
) : ArrayAdapter<Beneficiary>(context, resources, beneficiary) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val name: String = getItem(position)!!.name
        val beneficiaryId: String = getItem(position)!!.beeficiaryId
        val photoId: String = getItem(position)!!.photoId
        val retView: View

        val beneficiary: Beneficiary = Beneficiary(name, beneficiaryId, photoId)

        val inflator: LayoutInflater = LayoutInflater.from(context)
        retView = inflator.inflate(resources, parent, false)

        val tvName: TextView = retView.findViewById(R.id.tvName)
        val tvBeneficiaryId: TextView = retView.findViewById(R.id.tvBeneficiaryId)
        val tvPhotoId: TextView = retView.findViewById(R.id.tvPhotoId)
        val cbBenificiary: CheckBox = retView.findViewById(R.id.cbBenificiary)

        tvName.text = name
        tvBeneficiaryId.text = beneficiaryId
        tvPhotoId.text = photoId

        if (listener.isExist(beneficiaryId)) cbBenificiary.isChecked = true

        cbBenificiary.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) listener.onChecked(beneficiaryId)
            else listener.onUnchecked(beneficiaryId)
        }

        return retView
    }
}