package com.example.golf

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.example.golf.databinding.ActivityMemberBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MemberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMemberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val receivedIntent = intent
        val number = receivedIntent.getStringExtra("number")
        binding.numberTextView.text = number.toString()

        val docRef = Firebase.firestore.collection("number").document(number.toString())
        docRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val currentMonthAttendance = documentSnapshot.getLong("monthAttendance") ?: 0
                    val updatedMonthAttendance = currentMonthAttendance + 1
                    val currentTotalAttendance = documentSnapshot.getLong("totalAttendance") ?: 0
                    val updatedTotalAttendance = currentTotalAttendance + 1
                    val currentGrade = documentSnapshot.getLong("grade") ?: 0

                    val currentName = documentSnapshot.getString("name") ?:""
                    val coffee = documentSnapshot.getLong("coffee") ?: 0
                    val game = documentSnapshot.getLong("game") ?: 0
                    binding.nameTextView.text = currentName.toString()
                    binding.coffeeCountTextView.text = coffee.toString()
                    binding.gameCountTextView.text = game.toString()

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

                    when(updatedTotalAttendance.toInt()) {
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
                        else -> {

                        }
                    }



                    val updates = hashMapOf<String, Any>(
                        "monthAttendance" to updatedMonthAttendance,
                        "totalAttendance" to updatedTotalAttendance,
                    )
                    docRef.update(updates)
                        .addOnSuccessListener {
                            Log.d("aaaa", "Coffee field updated successfully.")
                        }
                        .addOnFailureListener { exception ->
                            Log.w("aaaa", "Error updating coffee field", exception)
                        }

                    binding.monthAttendanceTextView.text = updatedMonthAttendance.toString()
                    binding.totalAttendanceTextView.text = updatedTotalAttendance.toString()
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
                    docRef.update(updateName)
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
}