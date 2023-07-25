package com.example.golf

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.golf.databinding.FragmentGradeTableBinding

class GradeTable : Fragment() {
    // 바인딩 변수 선언
    private var _binding: FragmentGradeTableBinding? = null
    private val binding get() = _binding!!
    private var gradeColor = 1
    private var gradeNumber = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 뷰 바인딩 초기화
        _binding = FragmentGradeTableBinding.inflate(inflater, container, false)
        val view = binding.root

        val totalAttendance = arguments?.getInt("totalAttendance") ?: 0
        gradeInsert(totalAttendance)

        //이전 등급들
        for (i in 1 until gradeNumber) {
                    var textViewName = "b$i"
                    val textViewIdb = resources.getIdentifier(textViewName, "id", requireContext().packageName)
                    val textViewb = view.findViewById<ImageView>(textViewIdb)
                    textViewb.isVisible = true

                    textViewName = "s$i"
                    val textViewIds = resources.getIdentifier(textViewName, "id", requireContext().packageName)
                    val textViews = view.findViewById<ImageView>(textViewIds)
                    textViews.isVisible = true

                    textViewName = "g$i"
                    val textViewIdg = resources.getIdentifier(textViewName, "id", requireContext().packageName)
                    val textViewg = view.findViewById<ImageView>(textViewIdg)
                    textViewg.isVisible = true
                }


            when (gradeColor) {
                1 -> {
                    var textViewName = "b$gradeNumber"
                    val textViewId = resources.getIdentifier(textViewName, "id", requireContext().packageName)
                    val textView = view.findViewById<ImageView>(textViewId)
                    textView.isVisible = true

                    textViewName = "s$gradeNumber"
                    val textViewIds = resources.getIdentifier(textViewName, "id", requireContext().packageName)
                    val textViews = view.findViewById<ImageView>(textViewIds)
                    textViews.setImageResource(R.drawable.baseline_question_mark_24)
                    textView.isVisible = true
                }
                2 -> {
                    var textViewName = "b$gradeNumber"
                    val textViewIdb = resources.getIdentifier(textViewName, "id", requireContext().packageName)
                    val textViewb = view.findViewById<ImageView>(textViewIdb)
                    textViewb.isVisible = true

                    textViewName = "s$gradeNumber"
                    val textViewIds = resources.getIdentifier(textViewName, "id", requireContext().packageName)
                    val textViews = view.findViewById<ImageView>(textViewIds)
                    textViews.isVisible = true

                    textViewName = "g$gradeNumber"
                    val textViewIdg = resources.getIdentifier(textViewName, "id", requireContext().packageName)
                    val textViewg = view.findViewById<ImageView>(textViewIdg)
                    textViewg.setImageResource(R.drawable.baseline_question_mark_24)
                    textViewg.isVisible = true
                }
                3 -> {
                    var textViewName = "b$gradeNumber"
                    val textViewIdb = resources.getIdentifier(textViewName, "id", requireContext().packageName)
                    val textViewb = view.findViewById<ImageView>(textViewIdb)
                    textViewb.isVisible = true

                    textViewName = "s$gradeNumber"
                    val textViewIds = resources.getIdentifier(textViewName, "id", requireContext().packageName)
                    val textViews = view.findViewById<ImageView>(textViewIds)
                    textViews.isVisible = true

                    textViewName = "g$gradeNumber"
                    val textViewIdg = resources.getIdentifier(textViewName, "id", requireContext().packageName)
                    val textViewg = view.findViewById<ImageView>(textViewIdg)
                    textViewg.isVisible = true

                    val textViewIdbb = resources.getIdentifier("b${gradeNumber+1}", "id", requireContext().packageName)
                    val textViewbb = view.findViewById<ImageView>(textViewIdbb)
                    textViewbb.setImageResource(R.drawable.baseline_question_mark_24)
                    textViewbb.isVisible = true
                }
            }




        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 바인딩 변수 해제
        _binding = null
    }

    private fun gradeInsert(totalAttendance: Int) {
        when (totalAttendance) {
            1 -> {
                gradeNumber = 1
                gradeColor = 1
            }

            2 -> {
                gradeNumber = 1
                gradeColor = 2
            }

            3 -> {
                gradeNumber = 1
                gradeColor = 3
            }

            4, 5 -> {
                gradeNumber = 2
                gradeColor = 1
            }

            6, 7 -> {
                gradeNumber = 2
                gradeColor = 2
            }

            8, 9 -> {
                gradeNumber = 2
                gradeColor = 3
            }

            10, 11, 12 -> {
                gradeNumber = 3
                gradeColor = 1
            }

            13, 14, 15 -> {
                gradeNumber = 3
                gradeColor = 2
            }

            16, 17, 18 -> {
                gradeNumber = 3
                gradeColor = 3
            }

            19, 20, 21, 22 -> {
                gradeNumber = 4
                gradeColor = 1
            }

            23, 24, 25, 26 -> {
                gradeNumber = 4
                gradeColor = 2
            }

            27, 28, 29, 30 -> {
                gradeNumber = 4
                gradeColor = 3
            }

            31, 32, 33, 34, 35 -> {
                gradeNumber = 5
                gradeColor = 1
            }

            36, 37, 38, 39, 40 -> {
                gradeNumber = 5
                gradeColor = 2
            }

            41, 42, 43, 44, 45 -> {
                gradeNumber = 5
                gradeColor = 3
            }

            else -> {
            }
        }

        when (totalAttendance) {
            46, 47, 48, 49, 50, 51 -> {
                gradeNumber = 6
                gradeColor = 1
            }

            52, 53, 54, 55, 56, 57 -> {
                gradeNumber = 6
                gradeColor = 2
            }

            58, 59, 60, 61, 62, 63 -> {
                gradeNumber = 6
                gradeColor = 3
            }

            64, 65, 66, 67, 68, 69, 70 -> {
                gradeNumber = 7
                gradeColor = 1
            }

            71, 72, 73, 74, 75, 76, 77 -> {
                gradeNumber = 7
                gradeColor = 2
            }

            78, 79, 80, 81, 82, 83, 84 -> {
                gradeNumber = 7
                gradeColor = 3
            }

            85, 86, 87, 88, 89, 90, 91, 92 -> {
                gradeNumber = 8
                gradeColor = 1
            }

            93, 94, 95, 96, 97, 98, 99, 100 -> {
                gradeNumber = 8
                gradeColor = 2
            }

            101, 102, 103, 104, 105, 106, 107, 108 -> {
                gradeNumber = 8
                gradeColor = 3
            }

            109, 110, 111, 112, 113, 114, 115, 116, 117 -> {
                gradeNumber = 9
                gradeColor = 1
            }

            118, 119, 120, 121, 122, 123, 124, 125, 126 -> {
                gradeNumber = 9
                gradeColor = 2
            }

            127, 128, 129, 130, 131, 132, 133, 134, 135 -> {
                gradeNumber = 9
                gradeColor = 3
            }

            136, 137, 138, 139, 140, 141, 142, 143, 144, 145 -> {
                gradeNumber = 10
                gradeColor = 1
            }

            146, 147, 148, 149, 150, 151, 152, 153, 154, 155 -> {
                gradeNumber = 10
                gradeColor = 2
            }

            156, 157, 158, 159, 160, 161, 162, 163, 164, 165 -> {
                gradeNumber = 10
                gradeColor = 3
            }

            else -> {

            }
        }
    }
}