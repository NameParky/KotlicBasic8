package com.example.firebasecrud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebasecrud.databinding.ActivityMainBinding
import com.example.firebasecrud.databinding.ActivityUserUpdateBinding

class UserUpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserUpdateBinding

    lateinit var sKey: String
    lateinit var sName: String
    lateinit var sAge: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserUpdateBinding.inflate(layoutInflater)
        // setContentView(R.layout.activity_user_list)로 쓰면 바인딩 객체를 쓸 수 없음.
        setContentView(binding.root)

        // 데이터베이스 객체
        val dao = UserDao()

        // 데이터 null 체크
        // ** intent는 액티비티의 기본 속성중 하나로, onCreate 메서드 내에서 별도 선언없이 사용가능.
        if(intent.hasExtra("key") && intent.hasExtra("name")
            && intent.hasExtra("age")) {

            // 데이터 담기
            sKey = intent.getStringExtra("key")!!
            sName = intent.getStringExtra("name")!!
            sAge = intent.getStringExtra("age")!!

            // 데이터 보여주기
            binding.upNameEdit.setText(sName)
            binding.upAgeEdit.setText(sAge)

            // 사용자정보 수정버튼 이벤트
            binding.updateBtn.setOnClickListener {

                // 입력값
                val uName = binding.upNameEdit.text.toString()
                val uAge = binding.upAgeEdit.text.toString()

                // 파라미터 세팅
                val hasMap:HashMap<String, Any> = HashMap()
                hasMap["userName"] = uName
                hasMap["userAge"] = uAge

                dao.userUpdate(sKey, hasMap).addOnSuccessListener {

                    Toast.makeText(applicationContext, "수정 성공", Toast.LENGTH_SHORT).show()

                    // 사용자 목록으로 이동
                    val intent: Intent = Intent(this@UserUpdateActivity,
                        UserListActivity::class.java)
                    startActivity(intent)
                    finish()

                }.addOnFailureListener {

                    Toast.makeText(applicationContext, "수정 실패 : ${it.message}",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}