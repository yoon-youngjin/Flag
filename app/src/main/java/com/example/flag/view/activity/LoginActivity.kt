package com.example.flag.view.activity


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flag.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private var mBinding: ActivityLoginBinding? = null
    private val binding get() = mBinding!!

    private lateinit var email: String
    private lateinit var pw: String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // 뷰 바인딩
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 파이어베이스 객체 생성
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null && auth.currentUser?.isEmailVerified == true) {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
//            finish()
        }

        /**
         * 회원 가입 버튼
         */
        binding.tvLoginSignup.setOnClickListener() {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        /**
         * 로그인 버튼
         */
        binding.btnLoginLogin.setOnClickListener() {
            email = binding.etLoginId.text.toString()
            pw = binding.etLoginPw.text.toString()
            loginUserId(email, pw)
        }

        /**
         * 비밀번호 찾기
         */
//        binding.tvLoginFindpw.setOnClickListener() {
//            // 다이얼로그
//            val dialogView = LayoutInflater.from(this).inflate(R.layout.layout_dialog_findpw, null)
//            val builder = AlertDialog.Builder(this)
//                .setView(dialogView)
//                .setTitle("비밀번호 찾기")
//
//            builder.show()
//
//            // 이메일 전송 버튼
//            val sendEmail = dialogView.findViewById<Button>(R.id.btn_findpw_sendemail)
//            sendEmail.setOnClickListener {
//                email = dialogView.findViewById<EditText>(R.id.et_findpw_email).text.toString()
//                if (email == "" || !email.contains("@")) {
//                    toast("이메일을 다시 입력해주세요")
//                    return@setOnClickListener
//                }
//                resetPassword(email)
//            }
//        }

    }

    // 액티비티가 파괴될 때
    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }


    // 로그인
    private fun loginUserId(email: String, pw: String) {
        if (email == "" || pw == "") {
            toast("아이디와 비밀번호를 입력하세요")
            return
        }
        auth.signInWithEmailAndPassword(email, pw)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    // 로그인 성공
                    if (auth.currentUser?.isEmailVerified == true) {
                        // 인증받은 이메일
                        startActivity(Intent(this, SecondActivity::class.java))
//                        finish()
                    } else {
                        toast("이메일 인증 후 로그인 해주세요")
                    }
                }
            }.addOnFailureListener {
                when (it) {
                    is FirebaseAuthInvalidCredentialsException -> {
                        toast("아이디와 비밀번호를 다시 확인해주세요")
                    }
                    else -> {
                        toast("로그인 실패")
                    }
                }
                Log.e("loginUserId", it.toString())
            }
    }

    // 회원 가입한 비밀번호 재설정
    private fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    //비밀번호 재설정 메일을 보내기가 성공했을때 이벤트
                    toast("$email 비밀번호 재설정 메일을 전송하였습니다")
                }
            }.addOnFailureListener {
                when (it){
                    is FirebaseAuthInvalidUserException -> {
                        toast("가입하지 않은 이메일입니다")
                    }
                    is FirebaseAuthInvalidCredentialsException ->{
                        toast("잘못된 이메일 형식입니다")
                    }
                }
                Log.e("resetPassword", it.toString())
                toast("메일 전송에 실패하였습니다")
            }
    }

    // 토스트 재사용
    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

}