package com.dev.victormarques.sorteadordenumeros

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.dev.victormarques.sorteadordenumeros.databinding.FragmentDrawResultBinding
import kotlinx.coroutines.launch


class DrawResultFragment : Fragment() {

    private val drawViewModel: DrawViewModel by activityViewModels()

    private var _binding: FragmentDrawResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDrawResultBinding.inflate(inflater, container, false)
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
            lifecycleScope.launch {
                drawViewModel.uiState.collect { uiState ->
                    tvDrawNumber.text = getString(R.string._1_resultado, uiState.currentDrawNumber.toString())

                    clearLastDrewNumbers()

                    uiState.drawNumbers.forEach { drawNumber ->
                        generateDrawNumber(
                            drawNumber = drawNumber
                        )
                    }

                }
            }
        }
    }

    private fun FragmentDrawResultBinding.generateDrawNumber(drawNumber: Int) {
        val drawNumberTextView = TextView(requireContext()).apply {
            id = View.generateViewId()
            text = drawNumber.toString()
            setTextAppearance(R.style.TextAppearance_RobotoMono_Overline)
            textSize = 48f
            setTextColor(ContextCompat.getColor(requireContext(), R.color.content_brand))
        }

        root.addView(drawNumberTextView)
        flowResultNumberHelper.referencedIds =
            flowResultNumberHelper.referencedIds.plus(drawNumberTextView.id)
    }

    private fun FragmentDrawResultBinding.clearLastDrewNumbers() {
        flowResultNumberHelper.referencedIds.forEach {
            root.removeView(root.findViewById(it))
        }
    }
}