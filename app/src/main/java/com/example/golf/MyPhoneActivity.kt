package com.example.golf

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.golf.databinding.ActivityMainBinding
import com.example.golf.databinding.ActivityMyPhoneBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MyPhoneActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyPhoneBinding
    val monthRankArrayList = ArrayList<String>()
    val totalRankArrayList = ArrayList<String>()
    var change = "total"
    var rankImageName = R.drawable.baseline_pets_24_1_3
    var totalCount = 0
    var pageCount = 1
    var int0 =0
    var int1 =1
    var int2 =2
    var int3 =3
    var int4 =4
    var int5 =5
    var int6 =6
    var int7 =7
    var int8 =8
    var int9 =9
    var int10 =10
    var int11 =11
    var int12 =12
    var int13 =13
    var int14 =14
    var int15 =15
    var int16 =16
    var int17 =17
    var int18 =18
    var int19 =19
    var int20 =20
    var int21 =21
    var int22 =22
    var int23 =23
    var int24 =24
    var int25 =25
    var int26 =26
    var int27 =27
    var int28 =28
    var int29 =29

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMyPhoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //월 순위 만들기
        Firebase.firestore.collection("number")
            .orderBy("monthRank")
            .get()
            .addOnSuccessListener { querySnapshot ->

                binding.size.text = (querySnapshot.size()-2).toString() + "명"
                totalCount = querySnapshot.size()-2

                for (document in querySnapshot) {
                    // 문서 데이터에 접근
                    val monthAttendance =
                        document.getLong("monthAttendance") // monthRank 필드의 값을 Long 타입으로 가져옴
                    val totalAttendance =
                        document.getLong("totalAttendance") // monthRank 필드의 값을 Long 타입으로 가져옴
                    val name = document.getString("name") // number 필드의 값을 String 타입으로 가져옴
                    val number = document.getString("number")

                    // 새로운 ArrayList를 생성하여 값 추가
                    monthRankArrayList.add(name.toString())
                    monthRankArrayList.add(number.toString()+" /"+monthAttendance)
                    monthRankArrayList.add(totalAttendance.toString())

                }
            }

        //전체 순위 만들기
        Firebase.firestore.collection("number")
            .orderBy("totalRank")
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    // 문서 데이터에 접근
                    val totalAttendance =
                        document.getLong("totalAttendance") // monthRank 필드의 값을 Long 타입으로 가져옴
                    val name = document.getString("name") // number 필드의 값을 String 타입으로 가져옴
                    val number = document.getString("number")

                    // 새로운 ArrayList를 생성하여 값 추가
                    totalRankArrayList.add(name.toString())
                    totalRankArrayList.add(number.toString()+" /"+totalAttendance)
                    totalRankArrayList.add(totalAttendance.toString())

                }
            }

        binding.changeButton.setOnClickListener {
            pageCount = 1
            int0 =0
            int1 =1
            int2 =2
            int3 =3
            int4 =4
            int5 =5
            int6 =6
            int7 =7
            int8 =8
            int9 =9
             int10 =10
             int11 =11
             int12 =12
             int13 =13
             int14 =14
             int15 =15
             int16 =16
             int17 =17
             int18 =18
             int19 =19
             int20 =20
             int21 =21
             int22 =22
             int23 =23
             int24 =24
             int25 =25
             int26 =26
             int27 =27
             int28 =28
             int29 =29

            rankNumber()

            if (change == "month") {
                change = "total"
                binding.textView21.text = "전체 순위"
                binding.rankAttendance.text = "전화번호/홧수"
                totalRankArrayList?.let {
                    binding.rank1Name.text = totalRankArrayList?.get(int0)
                    binding.rank1Attendance.text = totalRankArrayList?.get(int1) + " 회"
                    gradeInsert(totalRankArrayList?.get(int2).toString())
                    binding.rank1Image.setImageResource(rankImageName)

                    binding.rank2Name.text = totalRankArrayList?.get(int3)
                    binding.rank2Attendance.text = totalRankArrayList?.get(int4) + " 회"
                    gradeInsert(totalRankArrayList?.get(int5).toString())
                    binding.rank2Image.setImageResource(rankImageName)

                    binding.rank3Name.text = totalRankArrayList?.get(int6)
                    binding.rank3Attendance.text = totalRankArrayList?.get(int7) + " 회"
                    gradeInsert(totalRankArrayList?.get(int8).toString())
                    binding.rank3Image.setImageResource(rankImageName)

                    binding.rank4Name.text = totalRankArrayList?.get(int9)
                    binding.rank4Attendance.text = totalRankArrayList?.get(int10) + " 회"
                    gradeInsert(totalRankArrayList?.get(int11).toString())
                    binding.rank4Image.setImageResource(rankImageName)

                    binding.rank5Name.text = totalRankArrayList?.get(int12)
                    binding.rank5Attendance.text = totalRankArrayList?.get(int13) + " 회"
                    gradeInsert(totalRankArrayList?.get(int14).toString())
                    binding.rank5Image.setImageResource(rankImageName)

                    binding.rank6Name.text = totalRankArrayList?.get(int15)
                    binding.rank6Attendance.text = totalRankArrayList?.get(int16) + " 회"
                    gradeInsert(totalRankArrayList?.get(int17).toString())
                    binding.rank6Image.setImageResource(rankImageName)

                    binding.rank7Name.text = totalRankArrayList?.get(int18)
                    binding.rank7Attendance.text = totalRankArrayList?.get(int19) + " 회"
                    gradeInsert(totalRankArrayList?.get(int20).toString())
                    binding.rank7Image.setImageResource(rankImageName)

                    binding.rank8Name.text = totalRankArrayList?.get(int21)
                    binding.rank8Attendance.text = totalRankArrayList?.get(int22) + " 회"
                    gradeInsert(totalRankArrayList?.get(int23).toString())
                    binding.rank8Image.setImageResource(rankImageName)

                    binding.rank9Name.text = totalRankArrayList?.get(int24)
                    binding.rank9Attendance.text = totalRankArrayList?.get(int25) + " 회"
                    gradeInsert(totalRankArrayList?.get(int26).toString())
                    binding.rank9Image.setImageResource(rankImageName)

                    binding.rank10Name.text = totalRankArrayList?.get(int27)
                    binding.rank10Attendance.text = totalRankArrayList?.get(int28) + " 회"
                    gradeInsert(totalRankArrayList?.get(int29).toString())
                    binding.rank10Image.setImageResource(rankImageName)

                }
            } else {
                change = "month"
                binding.textView21.text = "월 순위"
                monthRankArrayList?.let {
                    binding.rank1Name.text = monthRankArrayList?.get(int0)
                    binding.rank1Attendance.text = monthRankArrayList?.get(int1) + " 회"
                    gradeInsert(monthRankArrayList?.get(int2).toString())
                    binding.rank1Image.setImageResource(rankImageName)

                    binding.rank2Name.text = monthRankArrayList?.get(int3)
                    binding.rank2Attendance.text = monthRankArrayList?.get(int4) + " 회"
                    gradeInsert(monthRankArrayList?.get(int5).toString())
                    binding.rank2Image.setImageResource(rankImageName)

                    binding.rank3Name.text = monthRankArrayList?.get(int6)
                    binding.rank3Attendance.text = monthRankArrayList?.get(int7) + " 회"
                    gradeInsert(monthRankArrayList?.get(int8).toString())
                    binding.rank3Image.setImageResource(rankImageName)

                    binding.rank4Name.text = monthRankArrayList?.get(int9)
                    binding.rank4Attendance.text = monthRankArrayList?.get(int10) + " 회"
                    gradeInsert(monthRankArrayList?.get(int11).toString())
                    binding.rank4Image.setImageResource(rankImageName)

                    binding.rank5Name.text = monthRankArrayList?.get(int12)
                    binding.rank5Attendance.text = monthRankArrayList?.get(int13) + " 회"
                    gradeInsert(monthRankArrayList?.get(int14).toString())
                    binding.rank5Image.setImageResource(rankImageName)

                    binding.rank6Name.text = monthRankArrayList?.get(int15)
                    binding.rank6Attendance.text = monthRankArrayList?.get(int16) + " 회"
                    gradeInsert(monthRankArrayList?.get(int17).toString())
                    binding.rank6Image.setImageResource(rankImageName)

                    binding.rank7Name.text = monthRankArrayList?.get(int18)
                    binding.rank7Attendance.text = monthRankArrayList?.get(int19) + " 회"
                    gradeInsert(monthRankArrayList?.get(int20).toString())
                    binding.rank7Image.setImageResource(rankImageName)

                    binding.rank8Name.text = monthRankArrayList?.get(int21)
                    binding.rank8Attendance.text = monthRankArrayList?.get(int22) + " 회"
                    gradeInsert(monthRankArrayList?.get(int23).toString())
                    binding.rank8Image.setImageResource(rankImageName)

                    binding.rank9Name.text = monthRankArrayList?.get(int24)
                    binding.rank9Attendance.text = monthRankArrayList?.get(int25) + " 회"
                    gradeInsert(monthRankArrayList?.get(int26).toString())
                    binding.rank9Image.setImageResource(rankImageName)

                    binding.rank10Name.text = monthRankArrayList?.get(int27)
                    binding.rank10Attendance.text = monthRankArrayList?.get(int28) + " 회"
                    gradeInsert(monthRankArrayList?.get(int29).toString())
                    binding.rank10Image.setImageResource(rankImageName)

                }
            }
        }


        binding.nextButton.setOnClickListener {
            if (pageCount*10 <= totalCount - 10){
                pageCount++
                rankNumber()
                int0 += 30
                int1 += 30
                int2 += 30
                int3 += 30
                int4 += 30
                int5 += 30
                int6 += 30
                int7 += 30
                int8 += 30
                int9 += 30
                int10 += 30
                int11 += 30
                int12 += 30
                int13 += 30
                int14 += 30
                int15 += 30
                int16 += 30
                int17 += 30
                int18 += 30
                int19 += 30
                int20 += 30
                int21 += 30
                int22 += 30
                int23 += 30
                int24 += 30
                int25 += 30
                int26 += 30
                int27 += 30
                int28 += 30
                int29 += 30

                if (change == "total") {
                    totalRankArrayList?.let {
                        binding.rank1Name.text = totalRankArrayList?.get(int0)
                        binding.rank1Attendance.text = totalRankArrayList?.get(int1) + " 회"
                        gradeInsert(totalRankArrayList?.get(int2).toString())
                        binding.rank1Image.setImageResource(rankImageName)

                        binding.rank2Name.text = totalRankArrayList?.get(int3)
                        binding.rank2Attendance.text = totalRankArrayList?.get(int4) + " 회"
                        gradeInsert(totalRankArrayList?.get(int5).toString())
                        binding.rank2Image.setImageResource(rankImageName)

                        binding.rank3Name.text = totalRankArrayList?.get(int6)
                        binding.rank3Attendance.text = totalRankArrayList?.get(int7) + " 회"
                        gradeInsert(totalRankArrayList?.get(int8).toString())
                        binding.rank3Image.setImageResource(rankImageName)

                        binding.rank4Name.text = totalRankArrayList?.get(int9)
                        binding.rank4Attendance.text = totalRankArrayList?.get(int10) + " 회"
                        gradeInsert(totalRankArrayList?.get(int11).toString())
                        binding.rank4Image.setImageResource(rankImageName)

                        binding.rank5Name.text = totalRankArrayList?.get(int12)
                        binding.rank5Attendance.text = totalRankArrayList?.get(int13) + " 회"
                        gradeInsert(totalRankArrayList?.get(int14).toString())
                        binding.rank5Image.setImageResource(rankImageName)

                        binding.rank6Name.text = totalRankArrayList?.get(int15)
                        binding.rank6Attendance.text = totalRankArrayList?.get(int16) + " 회"
                        gradeInsert(totalRankArrayList?.get(int17).toString())
                        binding.rank6Image.setImageResource(rankImageName)

                        binding.rank7Name.text = totalRankArrayList?.get(int18)
                        binding.rank7Attendance.text = totalRankArrayList?.get(int19) + " 회"
                        gradeInsert(totalRankArrayList?.get(int20).toString())
                        binding.rank7Image.setImageResource(rankImageName)

                        binding.rank8Name.text = totalRankArrayList?.get(int21)
                        binding.rank8Attendance.text = totalRankArrayList?.get(int22) + " 회"
                        gradeInsert(totalRankArrayList?.get(int23).toString())
                        binding.rank8Image.setImageResource(rankImageName)

                        binding.rank9Name.text = totalRankArrayList?.get(int24)
                        binding.rank9Attendance.text = totalRankArrayList?.get(int25) + " 회"
                        gradeInsert(totalRankArrayList?.get(int26).toString())
                        binding.rank9Image.setImageResource(rankImageName)

                        binding.rank10Name.text = totalRankArrayList?.get(int27)
                        binding.rank10Attendance.text = totalRankArrayList?.get(int28) + " 회"
                        gradeInsert(totalRankArrayList?.get(int29).toString())
                        binding.rank10Image.setImageResource(rankImageName)

                    }
                } else {
                    monthRankArrayList?.let {
                        binding.rank1Name.text = monthRankArrayList?.get(int0)
                        binding.rank1Attendance.text = monthRankArrayList?.get(int1) + " 회"
                        gradeInsert(monthRankArrayList?.get(int2).toString())
                        binding.rank1Image.setImageResource(rankImageName)

                        binding.rank2Name.text = monthRankArrayList?.get(int3)
                        binding.rank2Attendance.text = monthRankArrayList?.get(int4) + " 회"
                        gradeInsert(monthRankArrayList?.get(int5).toString())
                        binding.rank2Image.setImageResource(rankImageName)

                        binding.rank3Name.text = monthRankArrayList?.get(int6)
                        binding.rank3Attendance.text = monthRankArrayList?.get(int7) + " 회"
                        gradeInsert(monthRankArrayList?.get(int8).toString())
                        binding.rank3Image.setImageResource(rankImageName)

                        binding.rank4Name.text = monthRankArrayList?.get(int9)
                        binding.rank4Attendance.text = monthRankArrayList?.get(int10) + " 회"
                        gradeInsert(monthRankArrayList?.get(int11).toString())
                        binding.rank4Image.setImageResource(rankImageName)

                        binding.rank5Name.text = monthRankArrayList?.get(int12)
                        binding.rank5Attendance.text = monthRankArrayList?.get(int13) + " 회"
                        gradeInsert(monthRankArrayList?.get(int14).toString())
                        binding.rank5Image.setImageResource(rankImageName)

                        binding.rank6Name.text = monthRankArrayList?.get(int15)
                        binding.rank6Attendance.text = monthRankArrayList?.get(int16) + " 회"
                        gradeInsert(monthRankArrayList?.get(int17).toString())
                        binding.rank6Image.setImageResource(rankImageName)

                        binding.rank7Name.text = monthRankArrayList?.get(int18)
                        binding.rank7Attendance.text = monthRankArrayList?.get(int19) + " 회"
                        gradeInsert(monthRankArrayList?.get(int20).toString())
                        binding.rank7Image.setImageResource(rankImageName)

                        binding.rank8Name.text = monthRankArrayList?.get(int21)
                        binding.rank8Attendance.text = monthRankArrayList?.get(int22) + " 회"
                        gradeInsert(monthRankArrayList?.get(int23).toString())
                        binding.rank8Image.setImageResource(rankImageName)

                        binding.rank9Name.text = monthRankArrayList?.get(int24)
                        binding.rank9Attendance.text = monthRankArrayList?.get(int25) + " 회"
                        gradeInsert(monthRankArrayList?.get(int26).toString())
                        binding.rank9Image.setImageResource(rankImageName)

                        binding.rank10Name.text = monthRankArrayList?.get(int27)
                        binding.rank10Attendance.text = monthRankArrayList?.get(int28) + " 회"
                        gradeInsert(monthRankArrayList?.get(int29).toString())
                        binding.rank10Image.setImageResource(rankImageName)

                    }
                }
            }
        }

        binding.previousButton.setOnClickListener {
            if (pageCount > 1) {
                pageCount--
                rankNumber()
                int0 -= 30
                int1 -= 30
                int2 -= 30
                int3 -= 30
                int4 -= 30
                int5 -= 30
                int6 -= 30
                int7 -= 30
                int8 -= 30
                int9 -= 30
                int10 -= 30
                int11 -= 30
                int12 -= 30
                int13 -= 30
                int14 -= 30
                int15 -= 30
                int16 -= 30
                int17 -= 30
                int18 -= 30
                int19 -= 30
                int20 -= 30
                int21 -= 30
                int22 -= 30
                int23 -= 30
                int24 -= 30
                int25 -= 30
                int26 -= 30
                int27 -= 30
                int28 -= 30
                int29 -= 30

                if (change == "total") {
                    totalRankArrayList?.let {
                        binding.rank1Name.text = totalRankArrayList?.get(int0)
                        binding.rank1Attendance.text = totalRankArrayList?.get(int1) + " 회"
                        gradeInsert(totalRankArrayList?.get(int2).toString())
                        binding.rank1Image.setImageResource(rankImageName)

                        binding.rank2Name.text = totalRankArrayList?.get(int3)
                        binding.rank2Attendance.text = totalRankArrayList?.get(int4) + " 회"
                        gradeInsert(totalRankArrayList?.get(int5).toString())
                        binding.rank2Image.setImageResource(rankImageName)

                        binding.rank3Name.text = totalRankArrayList?.get(int6)
                        binding.rank3Attendance.text = totalRankArrayList?.get(int7) + " 회"
                        gradeInsert(totalRankArrayList?.get(int8).toString())
                        binding.rank3Image.setImageResource(rankImageName)

                        binding.rank4Name.text = totalRankArrayList?.get(int9)
                        binding.rank4Attendance.text = totalRankArrayList?.get(int10) + " 회"
                        gradeInsert(totalRankArrayList?.get(int11).toString())
                        binding.rank4Image.setImageResource(rankImageName)

                        binding.rank5Name.text = totalRankArrayList?.get(int12)
                        binding.rank5Attendance.text = totalRankArrayList?.get(int13) + " 회"
                        gradeInsert(totalRankArrayList?.get(int14).toString())
                        binding.rank5Image.setImageResource(rankImageName)

                        binding.rank6Name.text = totalRankArrayList?.get(int15)
                        binding.rank6Attendance.text = totalRankArrayList?.get(int16) + " 회"
                        gradeInsert(totalRankArrayList?.get(int17).toString())
                        binding.rank6Image.setImageResource(rankImageName)

                        binding.rank7Name.text = totalRankArrayList?.get(int18)
                        binding.rank7Attendance.text = totalRankArrayList?.get(int19) + " 회"
                        gradeInsert(totalRankArrayList?.get(int20).toString())
                        binding.rank7Image.setImageResource(rankImageName)

                        binding.rank8Name.text = totalRankArrayList?.get(int21)
                        binding.rank8Attendance.text = totalRankArrayList?.get(int22) + " 회"
                        gradeInsert(totalRankArrayList?.get(int23).toString())
                        binding.rank8Image.setImageResource(rankImageName)

                        binding.rank9Name.text = totalRankArrayList?.get(int24)
                        binding.rank9Attendance.text = totalRankArrayList?.get(int25) + " 회"
                        gradeInsert(totalRankArrayList?.get(int26).toString())
                        binding.rank9Image.setImageResource(rankImageName)

                        binding.rank10Name.text = totalRankArrayList?.get(int27)
                        binding.rank10Attendance.text = totalRankArrayList?.get(int28) + " 회"
                        gradeInsert(totalRankArrayList?.get(int29).toString())
                        binding.rank10Image.setImageResource(rankImageName)

                    }
                } else {
                    monthRankArrayList?.let {
                        binding.rank1Name.text = monthRankArrayList?.get(int0)
                        binding.rank1Attendance.text = monthRankArrayList?.get(int1) + " 회"
                        gradeInsert(monthRankArrayList?.get(int2).toString())
                        binding.rank1Image.setImageResource(rankImageName)

                        binding.rank2Name.text = monthRankArrayList?.get(int3)
                        binding.rank2Attendance.text = monthRankArrayList?.get(int4) + " 회"
                        gradeInsert(monthRankArrayList?.get(int5).toString())
                        binding.rank2Image.setImageResource(rankImageName)

                        binding.rank3Name.text = monthRankArrayList?.get(int6)
                        binding.rank3Attendance.text = monthRankArrayList?.get(int7) + " 회"
                        gradeInsert(monthRankArrayList?.get(int8).toString())
                        binding.rank3Image.setImageResource(rankImageName)

                        binding.rank4Name.text = monthRankArrayList?.get(int9)
                        binding.rank4Attendance.text = monthRankArrayList?.get(int10) + " 회"
                        gradeInsert(monthRankArrayList?.get(int11).toString())
                        binding.rank4Image.setImageResource(rankImageName)

                        binding.rank5Name.text = monthRankArrayList?.get(int12)
                        binding.rank5Attendance.text = monthRankArrayList?.get(int13) + " 회"
                        gradeInsert(monthRankArrayList?.get(int14).toString())
                        binding.rank5Image.setImageResource(rankImageName)

                        binding.rank6Name.text = monthRankArrayList?.get(int15)
                        binding.rank6Attendance.text = monthRankArrayList?.get(int16) + " 회"
                        gradeInsert(monthRankArrayList?.get(int17).toString())
                        binding.rank6Image.setImageResource(rankImageName)

                        binding.rank7Name.text = monthRankArrayList?.get(int18)
                        binding.rank7Attendance.text = monthRankArrayList?.get(int19) + " 회"
                        gradeInsert(monthRankArrayList?.get(int20).toString())
                        binding.rank7Image.setImageResource(rankImageName)

                        binding.rank8Name.text = monthRankArrayList?.get(int21)
                        binding.rank8Attendance.text = monthRankArrayList?.get(int22) + " 회"
                        gradeInsert(monthRankArrayList?.get(int23).toString())
                        binding.rank8Image.setImageResource(rankImageName)

                        binding.rank9Name.text = monthRankArrayList?.get(int24)
                        binding.rank9Attendance.text = monthRankArrayList?.get(int25) + " 회"
                        gradeInsert(monthRankArrayList?.get(int26).toString())
                        binding.rank9Image.setImageResource(rankImageName)

                        binding.rank10Name.text = monthRankArrayList?.get(int27)
                        binding.rank10Attendance.text = monthRankArrayList?.get(int28) + " 회"
                        gradeInsert(monthRankArrayList?.get(int29).toString())
                        binding.rank10Image.setImageResource(rankImageName)

                    }
                }
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun rankNumber() {
        binding.rank1.text = ((pageCount-1)*10 + 1).toString()
        binding.rank2.text = ((pageCount-1)*10 + 2).toString()
        binding.rank3.text = ((pageCount-1)*10 + 3).toString()
        binding.rank4.text = ((pageCount-1)*10 + 4).toString()
        binding.rank5.text = ((pageCount-1)*10 + 5).toString()
        binding.rank6.text = ((pageCount-1)*10 + 6).toString()
        binding.rank7.text = ((pageCount-1)*10 + 7).toString()
        binding.rank8.text = ((pageCount-1)*10 + 8).toString()
        binding.rank9.text = ((pageCount-1)*10 + 9).toString()
        binding.rank10.text = ((pageCount-1)*10 + 10).toString()
    }


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
}