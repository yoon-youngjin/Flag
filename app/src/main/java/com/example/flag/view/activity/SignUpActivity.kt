package com.example.flag.view.activity


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flag.data.SchoolData
import com.example.flag.data.User
import com.example.flag.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.poifs.filesystem.POIFSFileSystem

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var pw: String

    private var mBinding: ActivitySignUpBinding? = null
    private val binding get() = mBinding!!

    //MutableList 생성
    private var schoolData: MutableList<SchoolData> = mutableListOf()

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // 뷰 바인딩
        mBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        /**
         * 약관 동의
         */
        binding.cbSignupAll.setOnClickListener() { OnCheckChanged(binding.cbSignupAll) }
        binding.cbSignupTerm1.setOnClickListener() { OnCheckChanged(binding.cbSignupTerm1) }
        binding.cbSignupTerm2.setOnClickListener() { OnCheckChanged(binding.cbSignupTerm2) }
        binding.cbSignupTerm3.setOnClickListener() { OnCheckChanged(binding.cbSignupTerm3) }
        binding.cbSignupTerm4.setOnClickListener() { OnCheckChanged(binding.cbSignupTerm4) }
        binding.cbSignupTerm5.setOnClickListener() { OnCheckChanged(binding.cbSignupTerm5) }


        /**
         * 회원가입 버튼
         */
        binding.btnSignupSignup.setOnClickListener() {
            name = binding.etSignupName.text.toString()
            email = binding.etSignupEmail.text.toString()
            pw = binding.etSignupPw.text.toString()

            if (name == "") {
                toast("이름을 입력하세요")
                return@setOnClickListener
            } else if (pw == "") {
                toast("비밀번호는 6자리 이상이어야 합니다")
                return@setOnClickListener
            } else if (email == "" || !email.contains("@")) {
                toast("이메일을 다시 입력해 주세요")
                return@setOnClickListener
            }

            // 이메일 도메인 확인
//            if (!email.contains(".ac.kr") && !email.contains(".edu")) {
//                toast("학교 메일 주소로 다시 입력해 주세요")
//                return@setOnClickListener
//            }

            if (!(binding.cbSignupTerm1.isChecked
                        && binding.cbSignupTerm2.isChecked
                        && binding.cbSignupTerm3.isChecked
                        && binding.cbSignupTerm4.isChecked)
            ) {
                toast("약관에 동의해주세요")
                return@setOnClickListener
            }

            join(name, email, pw)
        }

    }

    // 액티비티가 파괴될 때
    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }

    // 약관 동의
    private fun OnCheckChanged(checkBox: CheckBox) {
        when (checkBox) {
            // 전체 동의
            binding.cbSignupAll -> {
                if (binding.cbSignupAll.isChecked) {
                    binding.cbSignupTerm1.isChecked = true
                    binding.cbSignupTerm2.isChecked = true
                    binding.cbSignupTerm3.isChecked = true
                    binding.cbSignupTerm4.isChecked = true
                    binding.cbSignupTerm5.isChecked = true
                } else {
                    binding.cbSignupTerm1.isChecked = false
                    binding.cbSignupTerm2.isChecked = false
                    binding.cbSignupTerm3.isChecked = false
                    binding.cbSignupTerm4.isChecked = false
                    binding.cbSignupTerm5.isChecked = false
                }
            }
            // 개별 체크박스
            else -> {
                binding.cbSignupAll.isChecked = (
                        binding.cbSignupTerm1.isChecked
                                && binding.cbSignupTerm2.isChecked
                                && binding.cbSignupTerm3.isChecked
                                && binding.cbSignupTerm4.isChecked
                                && binding.cbSignupTerm5.isChecked)
            }
        }
    }

    // 회원 가입한 이메일 유효 확인
    private fun verifyEmail() {
        auth.currentUser?.sendEmailVerification()
            ?.addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    toast("메일이 전송되었습니다")
                }
            }?.addOnFailureListener(this) {
                Log.e("verifyEmail", it.toString())
            }
    }

    // 토스트 재사용
    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    // 엑셀 읽기
    private fun readExcel() {
        try {
            val assetManager = assets
            val input = assetManager.open("domain.xls")

            // POI File System 객체
            val fileSystem = POIFSFileSystem(input)

            val wb = HSSFWorkbook(fileSystem)
            val sheet = wb.getSheetAt(0)

            //행을 반복할 변수
            val rowIter = sheet.rowIterator()
            //행 넘버 변수 만들기
            var rowNo = 0

            //행 반복문
            while (rowIter.hasNext()) {
                val row = rowIter.next() as HSSFRow
                if (rowNo != 0) {
                    //열을 반복할 변수 만들어주기
                    val cellIter = row.cellIterator()
                    //열 넘버 변수 만들기
                    var colNo = 0
                    var schoolName = ""
                    var area = ""
                    var domain = ""
                    //열 반복문
                    while (cellIter.hasNext()) {
                        val cell = cellIter.next() as HSSFCell
                        when (colNo) {
                            0 -> schoolName = cell.toString()
                            1 -> area = cell.toString()
                            2 -> domain = cell.toString()
                        }
                        colNo++
                    }
                    schoolData.add(SchoolData(schoolName, area, domain))
                }
                rowNo++
            }
            Log.d("readExcel", " schoolData: $schoolData")
        } catch (e: Exception) {
            Log.e("readExcel", e.toString())
        }

    }

    // 도메인명에 해당하는 학교이름 반환
//    private fun findSchoolName(domainName: String?): String {
//        readExcel()
//        for (i in 0..schoolData.size) {
//            if (schoolData.get(i).domain == domainName) {
//                return schoolData.get(i).schoolName
//            }
//        }
//        return ""
//    }

    // 파이어베이스 데이터베이스에 올리기
    private fun writeNewUser(
        userId: String,
        name: String,
        email: String,
        school: String,
        team: String,
        record: String
    ) {
        val user = User(name, email, school, team, record)
        database = Firebase.database.reference

        database.child("users").child(userId).setValue(user)
    }


    // 회원가입
    private fun join(name: String, email: String, pw: String) {
        auth.createUserWithEmailAndPassword(email, pw)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    // 회원가입 성공
                    // 인증 메일 발송
                    verifyEmail()
                    val user = auth.currentUser
                    val domain = user?.email?.split("@")?.get(1)
//                    val school = findSchoolName(domain)
                    if (user != null) {
                        writeNewUser(user.uid, name, email, "건국대학교", "null", "")
                    }
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }.addOnFailureListener {
                when (it) {
                    is FirebaseAuthUserCollisionException -> toast("이미 가입한 회원입니다")
                    else -> {
                        toast("회원가입 실패")
                    }
                }

                Log.e("join", it.toString())
            }


    }

}