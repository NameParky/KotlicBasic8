package com.example.firebasecrud

import android.graphics.Canvas
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasecrud.databinding.ActivityUserListBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

class UserListActivity : AppCompatActivity() {

    lateinit var binding: ActivityUserListBinding

    lateinit var dao: UserDao

    lateinit var adapter: UserAdapter

    lateinit var userList: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserListBinding.inflate(layoutInflater)
        // setContentView(R.layout.activity_user_list)로 쓰면 바인딩이안되서 리스트 안뜸.
        setContentView(binding.root)

        // userList 초기화
        userList = ArrayList()

        // dao 초기화
        dao = UserDao()

        // adapter 초기화
        adapter = UserAdapter(this, userList)

        // recyclerView 초기화
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // 사용자 정보 가져오기
        getUserList()

        // 사용자 삭제 기능
        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            // 지금 당장 필요한건 onMove가 아닌 onSwipe이므로 onMove에 return false를 표기.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                // 해당 위치 값을 변수에 담는다.
                val position = viewHolder.bindingAdapterPosition

                when(direction) {

                    // 터치값이 왼쪽일 때 삭제기능 구현.
                    ItemTouchHelper.LEFT -> {
                        val key = userList[position].userKey

                        dao.userDelete(key).addOnSuccessListener {

                            // makeText의 인자에는 (context, 표시할 문자값, 메세지 표시시간을 나타내는 상수 값)
                            // 이 들어간다.
                            Toast.makeText(applicationContext, "삭제 성공", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener {

                            Toast.makeText(applicationContext, "삭제 실패 : ${it.message}",
                                Toast.LENGTH_SHORT).show()
                        }
                    } // LEFT
                } //when
            } // onSwiped

            // 스와이프 화면 꾸미기
            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder,
                    dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(Color.RED)
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                    .addSwipeLeftLabel("삭제")
                    .setSwipeLeftLabelColor(Color.WHITE)
                    .create()
                    .decorate()

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }).attachToRecyclerView(binding.recyclerView)

    } // onCreate()

    private fun getUserList() {

        // 사용자 목록을 가져온다.
        dao.getUserList()?.addValueEventListener(object: ValueEventListener{

            // 값이 변경될 때 호출되는 함수.
            override fun onDataChange(snapshot: DataSnapshot) {

                // 리스트 초기화
                userList.clear()

                // 사용자 정보가 담긴다.
                for(dataSnapshot in snapshot.children) {

                    val user = dataSnapshot.getValue(User::class.java)

                    // 키값 가져오기
                    val key = dataSnapshot.key

                    // 사용자 정보에 키 값 담기.
                    user?.userKey = key.toString()

                    if(user != null) {
                        userList.add(user)
                    }

                }

                // 데이터 적용
                adapter.notifyDataSetChanged()

            }
            // 오류가 났을 떄 호출되는 함수.
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}