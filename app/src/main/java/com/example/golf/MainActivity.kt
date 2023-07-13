package com.example.golf

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.golf.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.StringBuilder
import kotlin.math.ln

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val number = StringBuilder("")
    private var lastClickTime: Long = 0
    private val MIN_CLICK_INTERVAL = 2000 // 최소 클릭 간격 (1초)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //확인 버튼 비활성화
        binding.buttonEnter.isEnabled = false
//        binding.buttonEnter.setOnClickListener {
//            if(number.toString() == "77113695") {
//                val intent = Intent(this, ManagerActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//            else if(number.toString() == "45843696") {
//                val intent = Intent(this, MemberActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//            else {
//
//            }
//        }

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

            //툭정 문서 가져오기
//        Firebase.firestore.collection("number").document("77113696")
//            .get()
//            .addOnSuccessListener { document ->
//                if (document != null) {
//                    Log.d("11", "DocumentSnapshot data: ${document.data}")
//                } else {
//                    Log.d("11", "No such document")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.d("11", "get failed with ", exception)
//            }


            //문서 검색 쿼리
            Firebase.firestore.collection("number")
                .whereEqualTo("number", number.toString())
                .get()
                .addOnSuccessListener { documents ->
                    if (documents != null && !documents.isEmpty) {
                        // 검색 결과가 존재하는 경우
                        for (document in documents) {
                            Log.d("aaaa", "${document.id} => ${document.data}")
                        }
                        Log.d("aaaa", "11")

                        //유저 정보 Activity 열기
                        val intent = Intent(this, MemberActivity::class.java)
                        intent.putExtra("number", number.toString())
                        startActivity(intent)
                        finish()
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
                                    "name" to number.toString(),
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
}