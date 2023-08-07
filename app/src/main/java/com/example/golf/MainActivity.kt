package com.example.golf

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.golf.databinding.ActivityMainBinding
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Calendar

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val number = StringBuilder("")
    private var lastClickTime: Long = 0
    private val MIN_CLICK_INTERVAL = 2000 // 최소 클릭 간격 (1초)
    val monthRankArrayList = ArrayList<String>()
    val totalRankArrayList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //월 순위 만들기
        Firebase.firestore.collection("number")
            .orderBy("monthRank")
            .limit(10)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    // 문서 데이터에 접근
                    val monthAttendance =
                        document.getLong("monthAttendance") // monthRank 필드의 값을 Long 타입으로 가져옴
                    val totalAttendance =
                        document.getLong("totalAttendance") // monthRank 필드의 값을 Long 타입으로 가져옴
                    val name = document.getString("name") // number 필드의 값을 String 타입으로 가져옴

                    // 새로운 ArrayList를 생성하여 값 추가
                    monthRankArrayList.add(name.toString())
                    monthRankArrayList.add(monthAttendance.toString())
                    monthRankArrayList.add(totalAttendance.toString())

                }
            }

        //전체 순위 만들기
        Firebase.firestore.collection("number")
            .orderBy("totalRank")
            .limit(10)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    // 문서 데이터에 접근
                    val totalAttendance =
                        document.getLong("totalAttendance") // monthRank 필드의 값을 Long 타입으로 가져옴
                    val name = document.getString("name") // number 필드의 값을 String 타입으로 가져옴

                    // 새로운 ArrayList를 생성하여 값 추가
                    totalRankArrayList.add(name.toString())
                    totalRankArrayList.add(totalAttendance.toString())
                    totalRankArrayList.add(totalAttendance.toString())

                }
            }


        //fragment
        val fragment = EventFragment()
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
                    val eventFragment = EventFragment()
                    switchFragment(eventFragment)
                    true
                }
            }
        }

        //상태바 없애기
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val uiOptions = window.decorView.systemUiVisibility
        var newUiOptions = uiOptions
        newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_FULLSCREEN
        newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = newUiOptions

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

        //확인 버튼 비활성화
        binding.buttonEnter.isEnabled = false

    }
//여기까지 oncreate

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    fun numberClicked(view : View) {
        if (number.length < 8) {
            val numberString = (view as? Button)?.text?.toString() ?: ""
            number.append(numberString)
            if(number.length > 3) {
                number.insert(4, "-")
                binding.numberTextView.text = number.toString()
                number.deleteCharAt(4)
            } else {
                binding.numberTextView.text = number.toString()
            }

        }

        if (number.length == 8) {
            number.insert(4, "-")
            binding.numberTextView.text = number.toString()
            number.deleteCharAt(4)
            binding.buttonEnter.isEnabled = true
        }
    }

    fun minusClicked(view : View) {
        if (number.isNotEmpty()) {
            number.delete(number.length-1, number.length)
            if(number.length > 3) {
                number.insert(4, "-")
                binding.numberTextView.text = number.toString()
                number.deleteCharAt(4)
            } else {
                binding.numberTextView.text = number.toString()
            }
            Log.d("aa", "11111")
        }

        //확인 버튼 비활성화
        binding.buttonEnter.isEnabled = false
    }

    private fun keyPadFlowEnd() {
        binding.button0.isEnabled = false
        binding.button1.isEnabled = false
        binding.button2.isEnabled = false
        binding.button3.isEnabled = false
        binding.button4.isEnabled = false
        binding.button5.isEnabled = false
        binding.button6.isEnabled = false
        binding.button7.isEnabled = false
        binding.button8.isEnabled = false
        binding.button9.isEnabled = false
        binding.buttonMinus.isEnabled = false
        binding.buttonEnter.isEnabled = false
    }

    private fun keyPadFlowStart() {
        binding.button0.isEnabled = true
        binding.button1.isEnabled = true
        binding.button2.isEnabled = true
        binding.button3.isEnabled = true
        binding.button4.isEnabled = true
        binding.button5.isEnabled = true
        binding.button6.isEnabled = true
        binding.button7.isEnabled = true
        binding.button8.isEnabled = true
        binding.button9.isEnabled = true
        binding.buttonMinus.isEnabled = true
        binding.buttonEnter.isEnabled = true
    }


    fun inClicked(view : View) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime >= MIN_CLICK_INTERVAL) {
            // 최소 클릭 간격이 지난 경우에만 함수 실행
            lastClickTime = currentTime

            keyPadFlowEnd()

            Firebase.firestore.collection("time").document("time")
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {

                        // Firestore에서 가져온 시간 정보
                        val firstTime = documentSnapshot.getTimestamp("time")

                            // Firestore의 Timestamp를 Calendar 객체로 변환
                            val calendar = Calendar.getInstance()
                            calendar.time = firstTime?.toDate()!!

                            // Firestore에서 가져온 시간의 월
                            val firestoreMonth = calendar.get(Calendar.MONTH)

                            // 현재 시간의 월
                            val currentCalendar = Calendar.getInstance()
                            val currentMonth = currentCalendar.get(Calendar.MONTH)

                            // 월 비교
                            if (firestoreMonth == currentMonth) {
                                // 월이 같은 경우
                                //전화번호 검색 쿼리
                                Firebase.firestore.collection("number")
                                    .whereEqualTo("number", number.toString())
                                    .get()
                                    .addOnSuccessListener { querySnapshot ->
                                        if (querySnapshot != null && !querySnapshot.isEmpty) {
                                            // 검색 결과가 존재하는 경우
                                            val documents = querySnapshot.documents
                                            for (document in documents) {
                                                val firstTimestamp = document.getTimestamp("firstTime")
                                                val totalAttendance = document.getLong("totalAttendance")

                                                if (firstTimestamp != null) {
                                                    val localFirstDateTime = firstTimestamp.let { LocalDateTime.ofEpochSecond(it.seconds, firstTimestamp.nanoseconds, ZoneOffset.UTC) }
                                                    val currentDateTime = LocalDateTime.now()
                                                    if ((localFirstDateTime?.toLocalDate()
                                                            ?: 0) == currentDateTime.toLocalDate()
                                                    ) {
                                                        // 두 LocalDateTime 객체가 같은 날짜인 경우
                                                        val intent = Intent(this, MemberActivity2::class.java)
                                                        intent.putExtra("totalAttendance", totalAttendance?.toInt())
                                                        intent.putExtra("number", number.toString())
                                                        intent.putStringArrayListExtra("totalRankArrayList", totalRankArrayList)
                                                        intent.putStringArrayListExtra("monthRankArrayList", monthRankArrayList)
                                                        startActivity(intent)
                                                        finish()
                                                        Log.d("aaaa", "같은 날짜")

                                                    } else {
                                                        // 두 LocalDateTime 객체가 다른 날짜인 경우
                                                        val intent = Intent(this, MemberActivity::class.java)
                                                        intent.putExtra("totalAttendance", totalAttendance?.toInt())
                                                        intent.putExtra("number", number.toString())
                                                        intent.putStringArrayListExtra("totalRankArrayList", totalRankArrayList)
                                                        intent.putStringArrayListExtra("monthRankArrayList", monthRankArrayList)
                                                        startActivity(intent)
                                                        finish()
                                                        Log.d("aaaa", "다른 날짜")

                                                    }
                                                } else {
                                                    // 필드가 존재하지 않거나 값이 null인 경우 처리
                                                }
                                            }




                                        } else {
                                            // 검색 결과가 없는 경우
                                            Log.d("aaaa", "No documents found.")

                                            AlertDialog.Builder(this).apply {
                                                number.insert(4, "-")
                                                setTitle("010-${number}")
                                                number.deleteCharAt(4)
                                                setMessage("출석 기록이 없는 번호입니다. 출석 이벤트를 시작하겠습니까?")
                                                setNegativeButton("아니오") { _, _ ->
                                                    keyPadFlowStart()
                                                }
                                                setPositiveButton("네") { _, _ ->

                                                    //문서 만드는 코드
                                                    val number1 = hashMapOf(
                                                        "name" to number.substring(0, 4),
                                                        "number" to number.toString(),
                                                        "monthAttendance" to 0,
                                                        "totalAttendance" to 0,
                                                        "coffee" to 0,
                                                        "game" to 0,
                                                        "grade" to 1,
                                                    )
                                                    Firebase.firestore.collection("number").document(number.toString())
                                                        .set(number1)
                                                        .addOnSuccessListener {
                                                            Log.d("aaaa", "문서 만들기 성공")
                                                            val intent = Intent(this@MainActivity, MemberActivity::class.java)
                                                            intent.putExtra("number", number.toString())
                                                            intent.putStringArrayListExtra("totalRankArrayList", totalRankArrayList)
                                                            intent.putStringArrayListExtra("monthRankArrayList", monthRankArrayList)
                                                            startActivity(intent)
                                                            finish()
                                                        }
                                                        .addOnFailureListener { e -> Log.w("aaaa", "문서 만들기 실패", e)
                                                            keyPadFlowStart()
                                                        }
                                                }
                                            }.show()
                                        }
                                    }
                                    .addOnFailureListener { exception ->
                                        Log.w("aaaa", "Error getting documents: ", exception)
                                        keyPadFlowStart()
                                    }

                            } else {
                                // 월이 다른 경우
                                // Firebase Firestore 인스턴스 가져오기
                                val db = Firebase.firestore

                                val data = hashMapOf<String, List<String>>()
                        // "number" 컬렉션에서 "monthRank" 필드를 기준으로 오름차순 정렬하고 상위 10개의 문서를 가져옴
                                db.collection("number")
                                    .orderBy("monthRank")
                                    .limit(10)
                                    .get()
                                    .addOnSuccessListener { querySnapshot ->
                                        var rank = 0

                                        for (document in querySnapshot) {
                                            // 문서 데이터에 접근
                                            val monthAttendance = document.getLong("monthAttendance") ?: 0 // monthAttendance 필드의 값을 Long 타입으로 가져옴
                                            val name = document.getString("name") ?: "" // name 필드의 값을 String 타입으로 가져옴
//                                            val game = document.getLong("game") ?: 0
                                            val number = document.getString("number") ?: ""

//                                            db.collection("number").document(document.id)
//                                                .update("game", game+1)

                                            val monthAttendanceText = monthAttendance.toString() + "회"

                                            val list = listOf(name, number, monthAttendanceText)
                                            data[rank.toString()] = list

                                            rank++
                                        }
                                        // "monthRank" 컬렉션에 문서를 생성하고 데이터 추가
                                        val currentCalendar = Calendar.getInstance().time.toString()
                                        db.collection("monthRank").document(currentCalendar)
                                            .set(data)
                                            .addOnSuccessListener {
                                            }

                                        //월 출석 횟수 초기화
                                        db.collection("number")
                                            .get()
                                            .addOnSuccessListener { querySnapshot ->
                                                val batch = db.batch()

                                                for (document in querySnapshot) {
                                                    // 해당 필드를 0으로 업데이트
                                                    batch.update(document.reference, "monthAttendance", 0)
                                                }

                                                batch.commit()
                                                    .addOnSuccessListener {
                                                        // 모든 문서의 필드 업데이트 성공 시 실행되는 코드
                                                        //전화번호 검색 쿼리
                                                        Firebase.firestore.collection("number")
                                                            .whereEqualTo("number", number.toString())
                                                            .get()
                                                            .addOnSuccessListener { querySnapshot ->
                                                                if (querySnapshot != null && !querySnapshot.isEmpty) {
                                                                    // 검색 결과가 존재하는 경우
                                                                    val documents = querySnapshot.documents
                                                                    for (document in documents) {
                                                                        val firstTimestamp = document.getTimestamp("firstTime")
                                                                        val totalAttendance = document.getLong("totalAttendance")

                                                                        if (firstTimestamp != null) {
                                                                            val localFirstDateTime = firstTimestamp.let { LocalDateTime.ofEpochSecond(it.seconds, firstTimestamp.nanoseconds, ZoneOffset.UTC) }
                                                                            val currentDateTime = LocalDateTime.now()
                                                                            if ((localFirstDateTime?.toLocalDate()
                                                                                    ?: 0) == currentDateTime.toLocalDate()
                                                                            ) {
                                                                                // 두 LocalDateTime 객체가 같은 날짜인 경우
                                                                                val intent = Intent(this, MemberActivity2::class.java)
                                                                                intent.putExtra("totalAttendance", totalAttendance?.toInt())
                                                                                intent.putExtra("number", number.toString())
                                                                                startActivity(intent)
                                                                                finish()
                                                                                Log.d("aaaa", "같은 날짜")

                                                                            } else {
                                                                                // 두 LocalDateTime 객체가 다른 날짜인 경우
                                                                                val intent = Intent(this, MemberActivity::class.java)
                                                                                intent.putExtra("totalAttendance", totalAttendance?.toInt())
                                                                                intent.putExtra("number", number.toString())
                                                                                startActivity(intent)
                                                                                finish()
                                                                                Log.d("aaaa", "다른 날짜")

                                                                            }
                                                                        } else {
                                                                            // 필드가 존재하지 않거나 값이 null인 경우 처리
                                                                        }
                                                                    }




                                                                } else {
                                                                    // 검색 결과가 없는 경우
                                                                    Log.d("aaaa", "No documents found.")

                                                                    AlertDialog.Builder(this).apply {
                                                                        number.insert(4, "-")
                                                                        setTitle("010-${number}")
                                                                        number.deleteCharAt(4)
                                                                        setMessage("출석 기록이 없는 번호입니다. 출석 이벤트를 시작하겠습니까?")
                                                                        setNegativeButton("아니오") { _, _ ->
                                                                            keyPadFlowStart()
                                                                        }
                                                                        setPositiveButton("네") { _, _ ->

                                                                            //문서 만드는 코드
                                                                            val number1 = hashMapOf(
                                                                                "name" to number.substring(0, 4),
                                                                                "number" to number.toString(),
                                                                                "monthAttendance" to 0,
                                                                                "totalAttendance" to 0,
                                                                                "coffee" to 0,
                                                                                "game" to 0,
                                                                                "grade" to 1,
                                                                            )
                                                                            Firebase.firestore.collection("number").document(number.toString())
                                                                                .set(number1)
                                                                                .addOnSuccessListener {
                                                                                    Log.d("aaaa", "문서 만들기 성공")
                                                                                    val intent = Intent(this@MainActivity, MemberActivity::class.java)
                                                                                    intent.putExtra("number", number.toString())
                                                                                    intent.putStringArrayListExtra("totalRankArrayList", totalRankArrayList)
                                                                                    intent.putStringArrayListExtra("monthRankArrayList", monthRankArrayList)
                                                                                    startActivity(intent)
                                                                                    finish()
                                                                                }
                                                                                .addOnFailureListener { e -> Log.w("aaaa", "문서 만들기 실패", e)
                                                                                    keyPadFlowStart()
                                                                                }
                                                                        }
                                                                    }.show()
                                                                }
                                                            }
                                                            .addOnFailureListener { exception ->
                                                                Log.w("aaaa", "Error getting documents: ", exception)
                                                                keyPadFlowStart()
                                                            }


                                                    }
                                            }

                                        val currentTime = Timestamp.now()

                                        Firebase.firestore.collection("time").document("time")
                                            .update("time", currentTime)
                                            .addOnSuccessListener {
                                                // 업데이트 성공적으로 수행됨
                                                // ...
                                            }


                                    }
                            }
                    } else {
                        Log.d("aaaa", "Document does not exist.")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("aaaa", "Error getting document", exception)
                }




        }
    }
}