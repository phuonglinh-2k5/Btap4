package linhpham.bai4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {
    private lateinit var tvMssv: TextView
    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var btnEdit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        tvMssv = findViewById(R.id.tvDetailMssv)
        tvName = findViewById(R.id.tvDetailName)
        tvEmail = findViewById(R.id.tvDetailEmail)
        btnEdit = findViewById(R.id.btnGoToEdit)

        val id = intent.getIntExtra("id", -1)
        val mssv = intent.getStringExtra("mssv")
        val name = intent.getStringExtra("name")
        val email = intent.getStringExtra("email")
        val avatar = intent.getStringExtra("avatar")

        tvMssv.text = "Mã số SV: $mssv"
        tvName.text = "Họ tên: $name"
        tvEmail.text = "Email: $email"

        // Bấm nút chuyển sang Trang 3 để Sửa thông tin
        btnEdit.setOnClickListener {
            val intent = Intent(this, EditAddActivity::class.java).apply {
                putExtra("id", id)
                putExtra("mssv", mssv)
                putExtra("name", name)
                putExtra("email", email)
                putExtra("avatar", avatar)
            }
            startActivity(intent)
            finish()
        }
    }
}