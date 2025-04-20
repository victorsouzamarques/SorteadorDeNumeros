package com.dev.victormarques.sorteadordenumeros

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.dev.victormarques.sorteadordenumeros.databinding.FragmentDrawBinding


class DrawFragment : Fragment() {

    private val drawViewModel: DrawViewModel by activityViewModels()
    private var _binding: FragmentDrawBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDrawBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            swtNotRepeatNumbers.setOnCheckedChangeListener { _, isChecked ->
                swtNotRepeatNumbers.trackTintList = getColorStateList(
                    requireContext(),
                    if (isChecked) R.color.content_brand else R.color.content_tertiary
                )
                drawViewModel.setShouldRepeatNumbers(!isChecked)
            }

            etAmountNumbers.addTextChangedListener { amountNumber ->
                val amountNumberInt = transformToInt(amountNumber)
//                if (isNegative(amountNumberInt)) {
//                    showToast()
//                } else {
                    drawViewModel.setDrawAmountNumber(amountNumberInt)
//                }
            }

            etInitialLimit.addTextChangedListener { initNumber ->
                val initNumberInt = transformToInt(initNumber)
//                if (isNegative(initNumberInt)) {
//                    showToast()
//                } else {
                    drawViewModel.setInitialLimit(initNumberInt)
//                }
            }

            etFinalLimit.addTextChangedListener { finalNumber ->
//                val finalNumberInt = transformToInt(finalNumber)
//                if (isNegative(finalNumberInt)) {
//                    showToast()
//                }
                drawViewModel.setFinalLimit(finalNumber.toString().toIntOrNull() ?: 0)
            }

        }
    }

//    private fun showToast() {
//        Toast.makeText(requireContext(), "O numero nao pode ser menor ou igual a 0", Toast.LENGTH_LONG).show()
//    }
//
//    private fun isNegative(number: Int): Boolean {
//        return number <= 0
//    }
//
    private fun transformToInt(editableText: Editable?): Int {
        return editableText.toString().toIntOrNull() ?: 0
    }
}