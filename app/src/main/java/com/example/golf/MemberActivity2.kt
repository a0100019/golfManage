package com.example.golf

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.golf.databinding.ActivityMemberBinding
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.time.Duration

@Suppress("DEPRECATION")
class MemberActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMemberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //fragment
        val fragment = MonthRank()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()

        // BottomNavigationView 아이템 선택 시 프래그먼트 전환
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.totalRank -> {
                    val totalRank = TotalRank()
                    switchFragment(totalRank)
                    true
                }
                R.id.monthRank -> {
                    val monthRank = MonthRank()
                    switchFragment(monthRank)
                    true
                }
                else -> {
                    val gradeTable = GradeTable()
                    switchFragment(gradeTable)
                    true
                }
            }
        }

        //상태바 없애기
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val uiOptions = window.decorView.systemUiVisibility
        var newUiOptions = uiOptions
        newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_FULLSCREEN
        newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = newUiOptions


        val receivedIntent = intent
        val number = receivedIntent.getStringExtra("number")
        binding.numberTextView.text = number.toString()

        
        //츨석 횟수 적용
        val firstDocRef = Firebase.firestore.collection("number").document(number.toString())
        firstDocRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val currentMonthAttendance = documentSnapshot.getLong("monthAttendance") ?: 0
                    val currentTotalAttendance = documentSnapshot.getLong("totalAttendance") ?: 0
                    val currentGrade = documentSnapshot.getLong("grade") ?: 0

                    val currentName = documentSnapshot.getString("name") ?:""
                    val coffee = documentSnapshot.getLong("coffee") ?: 0
                    val game = documentSnapshot.getLong("game") ?: 0
                    binding.nameTextView.text = currentName.toString()
                    binding.coffeeCountTextView.text = coffee.toString()
                    binding.gameCountTextView.text = game.toString()

                    binding.monthAttendanceTextView.text = currentMonthAttendance.toString()
                    binding.totalAttendanceTextView.text = currentTotalAttendance.toString()
                    val monthRank = documentSnapshot.getLong("monthRank") ?: 0
                    val totalRank = documentSnapshot.getLong("totalRank") ?: 0
                    binding.monthRankingTextView.text = monthRank.toString()
                    binding.totalRankingTextView.text = totalRank.toString()

                    val firstTimestamp = documentSnapshot.getTimestamp("firstTime")
                    val localFirstDateTime = firstTimestamp?.let { LocalDateTime.ofEpochSecond(it.seconds, firstTimestamp.nanoseconds, ZoneOffset.UTC) }
                    val formatter = DateTimeFormatter.ofPattern("MM/dd HH시 mm분")
                    val firstTime = localFirstDateTime?.format(formatter)
                    binding.frstTime.text = firstTime.toString()
                    val currentDateTime = LocalDateTime.now()
                    val minutesDiff = ChronoUnit.MINUTES.between(localFirstDateTime, currentDateTime)
                    val hours = minutesDiff / 60
                    val minutes = minutesDiff % 60
                    binding.timeInterval.text = "$hours 시간 $minutes 분"

                    //등급 모양 부여
                    gradeInsert(currentTotalAttendance)

                } else {
                    Log.d("aaaa", "Document does not exist.")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("aaaa", "Error getting document", exception)
            }


        binding.exitButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.nameEditButton.setOnClickListener {
            val editText = EditText(this)

            AlertDialog.Builder(this).apply {
                setTitle("닉네임 설정")
                setView(editText)
                setMessage("변경하실 닉네임을 입력해주세요. 최대 10자까지 가능합니다.")

                setNegativeButton("아니오") { _, _ ->
                }
                setPositiveButton("네") { _, _ ->
                    val updateName = hashMapOf<String, Any>(
                        "name" to editText.text.toString(),
                    )
                    firstDocRef.update(updateName)
                        .addOnSuccessListener {
                            Log.d("aaaa", "닉네임 변경 성공.")
                        }
                        .addOnFailureListener { exception ->
                            Log.w("aaaa", "닉네임 변경 실패", exception)
                        }
                    binding.nameTextView.text = editText.text.toString()
                }
            }.show()
        }

        //현재 시각
        val updateIntervalMillis: Long = 10000 // 업데이트 간격 (10초)
        val handler = Handler()
        val updateRunnable = object : Runnable {
            @SuppressLint("SetTextI18n")
            override fun run() {
                val currentDateTime = LocalDateTime.now()
                val minutes = currentDateTime.minute
                val hours = currentDateTime.hour
                val formattedMinutes = String.format("%02d", minutes) // 분을 2자리 숫자로 포맷팅
                val formattedHour = String.format("%02d", hours) // 시를 2자리 숫자로 포맷팅
                binding.timeTextView.text = formattedHour + "시 " + formattedMinutes + "분"
                // 일정한 간격으로 Runnable을 다시 실행하여 시간 업데이트 반복
                handler.postDelayed(this, updateIntervalMillis)
            }
        }
        handler.post(updateRunnable)

    }
//여기까지 oncreate

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun gradeInsert(currentTotalAttendance: Long) {
        when (currentTotalAttendance.toInt()) {
            1 -> {
                binding.gradeImageView.setImageResource(R.drawable.baseline_pets_24_1_3)
            }

            2 -> {
                binding.gradeImageView.setImageResource(R.drawable.baseline_pets_24_1_2)
            }

            3 -> {
                binding.gradeImageView.setImageResource(R.drawable.baseline_pets_24_1_1)
            }

            4, 5 -> {
                binding.gradeImageView.setImageResource(R.drawable.baseline_cruelty_free_24_2_3)
            }

            6, 7 -> {
                binding.gradeImageView.setImageResource(R.drawable.baseline_cruelty_free_24_2_2)
            }

            8, 9 -> {
                binding.gradeImageView.setImageResource(R.drawable.baseline_cruelty_free_24_2_1)
            }

            10, 11, 12 -> {
                binding.gradeImageView.setImageResource(R.drawable.baseline_favorite_24_3_3)
            }

            13, 14, 15 -> {
                binding.gradeImageView.setImageResource(R.drawable.baseline_favorite_24_3_2)
            }

            16, 17, 18 -> {
                binding.gradeImageView.setImageResource(R.drawable.baseline_favorite_24_3_1)
            }

            19, 20, 21, 22 -> {
                binding.gradeImageView.setImageResource(R.drawable.baseline_local_fire_department_24_4_3)
            }

            23, 24, 25, 26 -> {
                binding.gradeImageView.setImageResource(R.drawable.baseline_local_fire_department_24_4_2)
            }

            27, 28, 29, 30 -> {
                binding.gradeImageView.setImageResource(R.drawable.baseline_local_fire_department_24_4_1)
            }

            31, 32, 33, 34, 35 -> {
                binding.gradeImageView.setImageResource(R.drawable.baseline_filter_vintage_24_5_3)
            }

            36, 37, 38, 39, 40 -> {
                binding.gradeImageView.setImageResource(R.drawable.baseline_filter_vintage_24_5_2)
            }

            41, 42, 43, 44, 45 -> {
                binding.gradeImageView.setImageResource(R.drawable.baseline_filter_vintage_24_5_1)
            }

            else -> {
            }
        }

        when (currentTotalAttendance.toInt()) {
            46, 47, 48, 49, 50, 51 -> {
                binding.gradeImageView.setImageResource(R.drawable.baseline_local_florist_24_6_3)
            }

            52, 53, 54, 55, 56, 57 -> {
                binding.gradeImageView.setImageResource(R.drawable.baseline_local_florist_24_6_2)
            }

            58, 59, 60, 61, 62, 63 -> {
                binding.gradeImageView.setImageResource(R.drawable.baseline_local_florist_24_6_1)
            }

            64, 65, 66, 67, 68, 69, 70 -> {
                binding.gradeImageView.setImageResource(R.drawable.baseline_ac_unit_24_7_3)
            }

            71, 72, 73, 74, 75, 76, 77 -> {
                binding.gradeImageView.setImageResource(R.drawable.baseline_ac_unit_24_7_2)
            }

            78, 79, 80, 81, 82, 83, 84 -> {
                binding.gradeImageView.setImageResource(R.drawable.baseline_ac_unit_24_7_1)
            }

            85, 86, 87, 88, 89, 90, 91, 92 -> {
                binding.gradeImageView.setImageResource(R.drawable.baseline_bolt_24_8_3)
            }

            93, 94, 95, 96, 97, 98, 99, 100 -> {
                binding.gradeImageView.setImageResource(R.drawable.baseline_bolt_24_8_2)
            }

            101, 102, 103, 104, 105, 106, 107, 108 -> {
                binding.gradeImageView.setImageResource(R.drawable.baseline_bolt_24_8_1)
            }

            109, 110, 111, 112, 113, 114, 115, 116, 117 -> {
                binding.gradeImageView.setImageResource(R.drawable.baseline_diamond_24_9_3)
            }

            118, 119, 120, 121, 122, 123, 124, 125, 126 -> {
                binding.gradeImageView.setImageResource(R.drawable.baseline_diamond_24_9_2)
            }
            127, 128, 129, 130, 131, 132, 133, 134, 135 -> {
                binding.gradeImageView.setImageResource(R.drawable.baseline_diamond_24_9_1)
            }
            136, 137, 138, 139, 140, 141, 142, 143, 144, 145 -> {
                binding.gradeImageView.setImageResource(R.drawable.baseline_attractions_24_10_3)
            }
            146, 147, 148, 149, 150, 151, 152, 153, 154, 155 -> {
                binding.gradeImageView.setImageResource(R.drawable.baseline_attractions_24_10_2)
            }
            156, 157, 158, 159, 160, 161, 162, 163, 164, 165 -> {
                binding.gradeImageView.setImageResource(R.drawable.baseline_attractions_24_10_1)
            }

            else -> {

            }
        }
    }
}