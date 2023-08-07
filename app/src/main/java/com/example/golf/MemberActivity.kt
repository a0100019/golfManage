package com.example.golf

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.golf.databinding.ActivityMemberBinding
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Math.min
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Suppress("DEPRECATION")
class MemberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMemberBinding
    private val COUNTDOWN_TIME = 150000 // 5분(300초)
    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val receivedIntent = intent
        val number = receivedIntent.getStringExtra("number")
        binding.numberTextView.text = number.toString()
        val totalAttendance = receivedIntent.getIntExtra("totalAttendance", 0)

        // 타이머 생성
        countDownTimer = object : CountDownTimer(COUNTDOWN_TIME.toLong(), 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                // 타이머가 틱마다 호출되는 함수
                val secondsRemaining = millisUntilFinished / 1000
                binding.exitButton.text = "나가기(${secondsRemaining})"
            }

            override fun onFinish() {
                // 타이머가 끝나면 호출되는 함수
                // 다른 화면으로 전환
                startActivity(Intent(this@MemberActivity, MainActivity::class.java))
                finish() // MainActivity 종료 (선택사항)
            }
        }
        countDownTimer.start()

        val totalRankArrayList = intent.getStringArrayListExtra("totalRankArrayList")
        val monthRankArrayList = intent.getStringArrayListExtra("monthRankArrayList")

        //fragment
        val fragment = MonthRank().apply {
            arguments = Bundle().apply {
                // 전달할 값 설정

                putStringArrayList("monthRankArrayList", monthRankArrayList)
            }
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()

        // BottomNavigationView 아이템 선택 시 프래그먼트 전환
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.totalRank -> {
                    val totalRank = TotalRank().apply {
                        arguments = Bundle().apply {
                            // 전달할 값 설정

                            putStringArrayList("totalRankArrayList", totalRankArrayList)
                        }
                    }
                    switchFragment(totalRank)
                    true
                }
                R.id.monthRank -> {
                    val monthRank = MonthRank().apply {
                        arguments = Bundle().apply {
                            // 전달할 값 설정

                            putStringArrayList("monthRankArrayList", monthRankArrayList)
                        }
                    }
                    switchFragment(monthRank)
                    true
                }
                else -> {
                    val gradeTable = GradeTable().apply {
                        arguments = Bundle().apply {
                            // 전달할 값 설정

                            putInt("totalAttendance", totalAttendance)
                        }
                    }
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


        //츨석 횟수 적용
        val firstDocRef = Firebase.firestore.collection("number").document(number.toString())
        firstDocRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val currentMonthAttendance = documentSnapshot.getLong("monthAttendance") ?: 0
                    val updatedMonthAttendance = currentMonthAttendance + 1
                    val currentTotalAttendance = documentSnapshot.getLong("totalAttendance") ?: 0
                    val updatedTotalAttendance = currentTotalAttendance + 1
                    val currentGrade = documentSnapshot.getLong("grade") ?: 0

                    val currentName = documentSnapshot.getString("name") ?:""
                    val game = documentSnapshot.getLong("game") ?: 0
                    binding.nameTextView.text = currentName.toString()
                    binding.gameCountTextView.text = game.toString()


                    //커피 이벤트
                    var currentCoffee = documentSnapshot.getLong("coffee") ?: 0
                    if((updatedTotalAttendance % 10).toInt() == 0) {
                        binding.eventTextView.text = "커피 교환권 획득!"
                        currentCoffee++
                    } else {
                        binding.eventTextView.text = " ${updatedTotalAttendance % 10}/10 "
                    }

                    //등급 모양 부여
                    gradeInsert(updatedTotalAttendance)

                    // 출석 시간 입력
                    val currentDateTime = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofPattern("MM/dd HH시 mm분")
                    val firstTime = currentDateTime.format(formatter)
                    binding.frstTime.text = firstTime.toString()
                    val timestamp = Timestamp(currentDateTime.toEpochSecond(ZoneOffset.UTC), currentDateTime.nano)

                    //출석 횟수 입력
                    val updates = hashMapOf<String, Any>(
                        "monthAttendance" to updatedMonthAttendance,
                        "totalAttendance" to updatedTotalAttendance,
                        "firstTime" to timestamp,
                        "coffee" to currentCoffee,
                    )
                    firstDocRef.update(updates)
                        .addOnSuccessListener {
                            Log.d("aaaa", "Coffee field updated successfully.")
                            // 전체 순위 업데이트 하는 코드
                            val totalQuery = Firebase.firestore.collection("number").orderBy("totalAttendance", Query.Direction.DESCENDING)

                            totalQuery.get()
                                .addOnSuccessListener { querySnapshot ->
                                    var totalRank = 1
                                    for (document in querySnapshot.documents) {
                                        val documentRef = Firebase.firestore.collection("number").document(document.id)
                                        val updates = hashMapOf<String, Any>("totalRank" to totalRank)

                                        documentRef.update(updates)
                                            .addOnSuccessListener {
                                                // 성공적으로 rank 필드를 업데이트한 경우
                                                Log.d("aaaa", "Document ${document.id} rank updated successfully.")
                                            }

                                        totalRank++
                                    }
                                }

                            //월 순위 업데이트 하는 코드
                            val monthQuery = Firebase.firestore.collection("number")
                                .orderBy("monthAttendance", Query.Direction.DESCENDING).orderBy("totalAttendance", Query.Direction.DESCENDING)
                            val rank10MonthAttendance = 0
                            monthQuery.get()
                                .addOnSuccessListener { querySnapshot ->
                                    var monthRank = 1
                                    for (document in querySnapshot.documents) {
                                        val documentRef = Firebase.firestore.collection("number").document(document.id)
                                        val updates = hashMapOf<String, Any>("monthRank" to monthRank)

                                        documentRef.update(updates)
                                            .addOnSuccessListener {
                                                // 성공적으로 rank 필드를 업데이트한 경우
                                                Log.d("aaaa", "Document ${document.id} rank updated successfully.")
                                            }

                                        monthRank++
                                    }

                                    //순위 입력 코드
                                    val secondDocRef = Firebase.firestore.collection("number").document(number.toString())
                                    secondDocRef.get()
                                        .addOnSuccessListener { documentSnapshot ->
                                            if (documentSnapshot.exists()) {
                                                val monthRank = documentSnapshot.getLong("monthRank") ?: 0
                                                val totalRank = documentSnapshot.getLong("totalRank") ?: 0
                                                binding.monthRankingTextView.text = monthRank.toString()
                                                binding.totalRankingTextView.text = totalRank.toString()
                                            } else {
                                                Log.d("aaaa", "Document does not exist.")
                                            }
                                        }
                                }
                        }
                        .addOnFailureListener { exception ->
                            Log.w("aaaa", "Error updating coffee field", exception)
                        }
                    binding.coffeeCountTextView.text = currentCoffee.toString()
                    binding.monthAttendanceTextView.text = updatedMonthAttendance.toString()
                    binding.totalAttendanceTextView.text = updatedTotalAttendance.toString()
                } else {
                    Log.d("aaaa", "Document does not exist.")
                }




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
                    val newName = editText.text.toString().take(10)

                    if (newName.isBlank()) {
                        // 사용자가 아무것도 입력하지 않았을 때의 처리
                        Toast.makeText(this@MemberActivity, "닉네임을 입력해주세요.", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        val updateName = hashMapOf<String, Any>(
                            "name" to newName
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
        }

        var clickCount = 0
        binding.gradeImageView.setOnClickListener {
            clickCount++
            if (clickCount > 4 ) {
                AlertDialog.Builder(this).apply {
                    setTitle("교환권 사용하기")
                    setMessage("사용하실 교환권을 선택해주세요. 신중하게 클릭해주세요.")

                    setNegativeButton("커피 교환권 사용") { _, _ ->
                        firstDocRef.get()
                            .addOnSuccessListener { documentSnapshot ->
                                if (documentSnapshot.exists()) {
                                    val coffee = documentSnapshot.getLong("coffee") ?: 0
                                    val updateCoffee = coffee -1
                                    val updateName = hashMapOf<String, Any>(
                                        "coffee" to updateCoffee,
                                    )
                                    firstDocRef.update(updateName)
                                        .addOnSuccessListener {
                                            Log.d("aaaa", "커피 교환권 사용 성공.")
                                            binding.coffeeCountTextView.text = updateCoffee.toString()
                                        }
                                        .addOnFailureListener { exception ->
                                            Log.w("aaaa", "커피 교환권 사용 실패", exception)
                                        }

                                } else {
                                    Log.d("aaaa", "Document does not exist.")
                                }
                            }
                            .addOnFailureListener { exception ->
                                Log.w("aaaa", "Error getting document", exception)
                            }
                    }

                    setPositiveButton("게임 교환권 사용") { _, _ ->
                        firstDocRef.get()
                            .addOnSuccessListener { documentSnapshot ->
                                if (documentSnapshot.exists()) {
                                    val game = documentSnapshot.getLong("game") ?: 0
                                    val updateGame = game -1
                                    val updateName = hashMapOf<String, Any>(
                                        "game" to updateGame,
                                    )
                                    firstDocRef.update(updateName)
                                        .addOnSuccessListener {
                                            Log.d("aaaa", "게임 교환권 사용 성공.")
                                            binding.gameCountTextView.text = updateGame.toString()
                                        }
                                        .addOnFailureListener { exception ->
                                            Log.w("aaaa", "게임 교환권 사용 실패", exception)
                                        }

                                } else {
                                    Log.d("aaaa", "Document does not exist.")
                                }
                            }
                            .addOnFailureListener { exception ->
                                Log.w("aaaa", "Error getting document", exception)
                            }

                    }


                }.show()
            }
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

    override fun onDestroy() {
        super.onDestroy()
        // 액티비티가 종료될 때 타이머 취소
        countDownTimer.cancel()
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun gradeInsert(updatedTotalAttendance: Long) {
        when (updatedTotalAttendance.toInt()) {
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

        when (updatedTotalAttendance.toInt()) {
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