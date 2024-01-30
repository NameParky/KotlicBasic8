package com.example.firebasecrud

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query


class UserDao {

    // DatabaseReference란?
    // Firebase Realtime Database에서 데이터를 참조하기 위한 클래스
    private var databaseReference: DatabaseReference? = null

    // init 함수는 UserDao 객체 생성 시 호출됨.
    // 즉 객체 생성 시에 실행해야 될 코드를 넣는다.
    init{
        val db = FirebaseDatabase.getInstance()
        databaseReference = db.getReference("user")
    }
    // ** db.getReference("위치 이름")에 저장위치 이름을 지정하고
    // ** 그것을 databaseReference에 적용한다.

    // 등록
    fun add(user: User?): Task<Void> {
        return databaseReference!!.push().setValue(user)
    }
    // ** databaseReference 객체를 통해서 유저값을 push(입력)을 한다.
    // ** !!가 사용되었기 때문에 null일경우 예외 발생.

    // 조회
    fun getUserList(): Query? {
        return databaseReference
    }

    // 수정
    fun userUpdate(key: String, hashMap: HashMap<String, Any>): Task<Void> {

        return databaseReference!!.child(key)!!.updateChildren(hashMap)
    }

    // 삭제
    fun userDelete(key: String): Task<Void> {
        return databaseReference!!.child(key).removeValue()
    }
}