package com.example.firebasecrud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.firebasecrud.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // 뷰 바인딩 객체생성.
    private lateinit var  binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 바인딩을 사용하기 위한 수정.
        binding = ActivityMainBinding.inflate(layoutInflater)
        // setContentView를 binding.root로 꼭 수정할 것.
        setContentView(binding.root)

        // 데이터베이스 클래스 객체 생성.
        val dao = UserDao()

        // 버튼이벤트 (바인딩을 통해 뷰객체를 바로접근)
        binding.addBtn.setOnClickListener {

            // 레이아웃에서 언더스코어로 작업했던 애들을 사용해야할 땐
            // 기존의 언더스코어 -> 카멜 케이스로 변형해야한다.
            val name = binding.nameEdit.text.toString()
            val age = binding.ageEdit.text.toString()

            // 데이터 세팅
            val user = User("", name, age)

            // 이 형식은 람다식으로 간단한 구현가능.
            dao.add(user)?.addOnSuccessListener {
                Toast.makeText(this, "등록 성공", Toast.LENGTH_SHORT).show()
            }?.addOnFailureListener {
                Toast.makeText(this, "등록 실패 : 사유 ${it.message}", Toast.LENGTH_SHORT).show()
            }

        }

        // 사용자 목록 버튼 이벤트
        binding.listBtn.setOnClickListener {

            // 현재 위치와 이동할 위치를 설정해준다.
            val intent: Intent = Intent(this@MainActivity, UserListActivity::class.java)
            startActivity(intent)

        }

    }
}