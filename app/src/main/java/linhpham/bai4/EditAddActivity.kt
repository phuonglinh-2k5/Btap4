package linhpham.bai4

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class EditAddActivity : AppCompatActivity() {
    private lateinit var edtMssv: EditText
    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtAvatar: EditText
    private lateinit var btnSave: Button
    private lateinit var dbHelper: DatabaseHelper
    private var studentId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_add)

        edtMssv = findViewById(R.id.edtMssv)
        edtName = findViewById(R.id.edtName)
        edtEmail = findViewById(R.id.edtEmail)
        edtAvatar = findViewById(R.id.edtAvatar)
        btnSave = findViewById(R.id.btnSave)
        dbHelper = DatabaseHelper(this)

        // Kiểm tra xem có dữ liệu truyền qua không (Nếu có -> Sửa, Không có -> Thêm mới)
        if (intent.hasExtra("id")) {
            studentId = intent.getIntExtra("id", -1)
            edtMssv.setText(intent.getStringExtra("mssv"))
            edtName.setText(intent.getStringExtra("name"))
            edtEmail.setText(intent.getStringExtra("email"))
            edtAvatar.setText(intent.getStringExtra("avatar"))
            title = "Sửa Thông Tin"
        } else {
            title = "Thêm Sinh Viên"
        }

        btnSave.setOnClickListener {
            val mssv = edtMssv.text.toString().trim()
            val name = edtName.text.toString().trim()
            val email = edtEmail.text.toString().trim()
            val avatar = edtAvatar.text.toString().trim()

            if (studentId != -1) {
                // Chế độ Sửa: Bật hộp thoại xác nhận theo yêu cầu đề bài
                AlertDialog.Builder(this)
                    .setTitle("Xác nhận")
                    .setMessage("Bạn có muốn sửa thông tin SV không?")
                    .setPositiveButton("Có") { _, _ ->
                        dbHelper.updateStudent(studentId, mssv, name, email, avatar)
                        finish()
                    }
                    .setNegativeButton("Không", null)
                    .show()
            } else {
                // Chế độ Thêm mới trực tiếp
                dbHelper.insertStudent(mssv, name, email, avatar)
                finish()
            }
        }
    }
}