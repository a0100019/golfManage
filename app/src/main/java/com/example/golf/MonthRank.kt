package com.example.golf

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.golf.databinding.FragmentMonthRankBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MonthRank : Fragment() {

    private var _binding: FragmentMonthRankBinding? = null
    private val binding get() = _binding!!
    var rankImageName = R.drawable.baseline_question_mark_24
    private var monthRankArrayList: ArrayList<String>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMonthRankBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Bundle에서 monthRankArrayList를 가져옴
        arguments?.let {
            monthRankArrayList = it.getStringArrayList("monthRankArrayList")
        }

        monthRankArrayList?.let {
                binding.rank1Name.text = monthRankArrayList?.get(0)
                binding.rank1Attendance.text = monthRankArrayList?.get(1) + " 회"
                gradeInsert(monthRankArrayList?.get(2).toString())
                binding.rank1Image.setImageResource(rankImageName)

            binding.rank2Name.text = monthRankArrayList?.get(3)
            binding.rank2Attendance.text = monthRankArrayList?.get(4) + " 회"
            gradeInsert(monthRankArrayList?.get(5).toString())
            binding.rank2Image.setImageResource(rankImageName)

            binding.rank3Name.text = monthRankArrayList?.get(6)
            binding.rank3Attendance.text = monthRankArrayList?.get(7) + " 회"
            gradeInsert(monthRankArrayList?.get(8).toString())
            binding.rank3Image.setImageResource(rankImageName)

            binding.rank4Name.text = monthRankArrayList?.get(9)
            binding.rank4Attendance.text = monthRankArrayList?.get(10) + " 회"
            gradeInsert(monthRankArrayList?.get(11).toString())
            binding.rank4Image.setImageResource(rankImageName)

            binding.rank5Name.text = monthRankArrayList?.get(12)
            binding.rank5Attendance.text = monthRankArrayList?.get(13) + " 회"
            gradeInsert(monthRankArrayList?.get(14).toString())
            binding.rank5Image.setImageResource(rankImageName)

            binding.rank6Name.text = monthRankArrayList?.get(15)
            binding.rank6Attendance.text = monthRankArrayList?.get(16) + " 회"
            gradeInsert(monthRankArrayList?.get(17).toString())
            binding.rank6Image.setImageResource(rankImageName)

            binding.rank7Name.text = monthRankArrayList?.get(18)
            binding.rank7Attendance.text = monthRankArrayList?.get(19) + " 회"
            gradeInsert(monthRankArrayList?.get(20).toString())
            binding.rank7Image.setImageResource(rankImageName)

            binding.rank8Name.text = monthRankArrayList?.get(21)
            binding.rank8Attendance.text = monthRankArrayList?.get(22) + " 회"
            gradeInsert(monthRankArrayList?.get(23).toString())
            binding.rank8Image.setImageResource(rankImageName)

            binding.rank9Name.text = monthRankArrayList?.get(24)
            binding.rank9Attendance.text = monthRankArrayList?.get(25) + " 회"
            gradeInsert(monthRankArrayList?.get(26).toString())
            binding.rank9Image.setImageResource(rankImageName)

            binding.rank10Name.text = monthRankArrayList?.get(27)
            binding.rank10Attendance.text = monthRankArrayList?.get(28) + " 회"
            gradeInsert(monthRankArrayList?.get(29).toString())
            binding.rank10Image.setImageResource(rankImageName)

            }
        }






    //여기까지 oncreate

    private fun gradeInsert(totalAttendance: String) {
        when (totalAttendance.toInt()) {
            1 -> {
                rankImageName = R.drawable.baseline_pets_24_1_3
            }

            2 -> {
                rankImageName = R.drawable.baseline_pets_24_1_2
            }

            3 -> {
                rankImageName = R.drawable.baseline_pets_24_1_1
            }

            4, 5 -> {
                rankImageName = R.drawable.baseline_cruelty_free_24_2_3
            }

            6, 7 -> {
                rankImageName = R.drawable.baseline_cruelty_free_24_2_2
            }

            8, 9 -> {
                rankImageName = R.drawable.baseline_cruelty_free_24_2_1
            }

            10, 11, 12 -> {
                rankImageName = R.drawable.baseline_favorite_24_3_3
            }

            13, 14, 15 -> {
                rankImageName = R.drawable.baseline_favorite_24_3_2
            }

            16, 17, 18 -> {
                rankImageName = R.drawable.baseline_favorite_24_3_1
            }

            19, 20, 21, 22 -> {
                rankImageName = R.drawable.baseline_local_fire_department_24_4_3
            }

            23, 24, 25, 26 -> {
                rankImageName = R.drawable.baseline_local_fire_department_24_4_2
            }

            27, 28, 29, 30 -> {
                rankImageName = R.drawable.baseline_local_fire_department_24_4_1
            }

            31, 32, 33, 34, 35 -> {
                rankImageName = R.drawable.baseline_filter_vintage_24_5_3
            }

            36, 37, 38, 39, 40 -> {
                rankImageName = R.drawable.baseline_filter_vintage_24_5_2
            }

            41, 42, 43, 44, 45 -> {
                rankImageName = R.drawable.baseline_filter_vintage_24_5_1
            }

            else -> {
            }
        }

        when (totalAttendance.toInt()) {
            46, 47, 48, 49, 50, 51 -> {
                rankImageName = R.drawable.baseline_local_florist_24_6_3
            }

            52, 53, 54, 55, 56, 57 -> {
                rankImageName = R.drawable.baseline_local_florist_24_6_2
            }

            58, 59, 60, 61, 62, 63 -> {
                rankImageName = R.drawable.baseline_local_florist_24_6_1
            }

            64, 65, 66, 67, 68, 69, 70 -> {
                rankImageName = R.drawable.baseline_ac_unit_24_7_3
            }

            71, 72, 73, 74, 75, 76, 77 -> {
                rankImageName = R.drawable.baseline_ac_unit_24_7_2
            }

            78, 79, 80, 81, 82, 83, 84 -> {
                rankImageName = R.drawable.baseline_ac_unit_24_7_1
            }

            85, 86, 87, 88, 89, 90, 91, 92 -> {
                rankImageName = R.drawable.baseline_bolt_24_8_3
            }

            93, 94, 95, 96, 97, 98, 99, 100 -> {
                rankImageName = R.drawable.baseline_bolt_24_8_2
            }

            101, 102, 103, 104, 105, 106, 107, 108 -> {
                rankImageName = R.drawable.baseline_bolt_24_8_1
            }

            109, 110, 111, 112, 113, 114, 115, 116, 117 -> {
                rankImageName = R.drawable.baseline_diamond_24_9_3
            }

            118, 119, 120, 121, 122, 123, 124, 125, 126 -> {
                rankImageName = R.drawable.baseline_diamond_24_9_2
            }
            127, 128, 129, 130, 131, 132, 133, 134, 135 -> {
                rankImageName = R.drawable.baseline_diamond_24_9_1
            }
            136, 137, 138, 139, 140, 141, 142, 143, 144, 145 -> {
                rankImageName = R.drawable.baseline_attractions_24_10_3
            }
            146, 147, 148, 149, 150, 151, 152, 153, 154, 155 -> {
                rankImageName = R.drawable.baseline_attractions_24_10_2
            }
            156, 157, 158, 159, 160, 161, 162, 163, 164, 165 -> {
                rankImageName = R.drawable.baseline_attractions_24_10_1
            }

            else -> {

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
