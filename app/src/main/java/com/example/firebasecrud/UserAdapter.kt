package com.example.firebasecrud

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// UserAdapter 생성자에 컨텍스트 타입의 변수와 어레이리스트 타입의 변수를 추가.
class UserAdapter(private val context: Context, private val userList: ArrayList<User>):
    RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    // user_layout.xml의 뷰 객체를 만들 클래스 추가.
    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val nameText: TextView = itemView.findViewById(R.id.name_text)
        val ageText: TextView = itemView.findViewById(R.id.age_text)
    }

    // 화면을 연결해주는 함수 (화면 설정).
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {

        val view = LayoutInflater.from(context)
            .inflate(R.layout.user_layout, parent, false)

        return UserViewHolder(view)
    }

    // 값 개수 리턴.
    override fun getItemCount(): Int {

        return userList.size
    }

    // 데이터를 설정해주는 함수 (데이터 설정).
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val user: User = userList[position]

        holder.nameText.text = user.userName
        holder.ageText.text = user.userAge

        holder.itemView.setOnClickListener {

            // 사용자 수정화면으로 이동
            val intent = Intent(context, UserUpdateActivity::class.java)
            intent.putExtra("key", user.userKey)
            intent.putExtra("name", user.userName)
            intent.putExtra("age", user.userAge)
            context.startActivity(intent)
            (context as Activity).finish()
        }

    }

}