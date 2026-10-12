package linhpham.bai4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var btnAdd: Button
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var studentList: List<Student>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listViewStudents)
        btnAdd = findViewById(R.id.btnAdd)
        dbHelper = DatabaseHelper(this)

        loadData()

        // Bấm vào item (ngoài nút sửa/xóa) -> Sang Trang 2 (Chi tiết)
        listView.setOnItemClickListener { _, _, position, _ ->
            val s = studentList[position]
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("id", s.id)
                putExtra("mssv", s.mssv)
                putExtra("name", s.name)
                putExtra("email", s.email)
                putExtra("avatar", s.avatar)
            }
            startActivity(intent)
        }

        // Bấm nút Thêm ở dưới cùng -> Sang Trang 3 (Thêm mới)
        btnAdd.setOnClickListener {
            val intent = Intent(this, EditAddActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadData() {
        studentList = dbHelper.getAllStudents()

        val adapter = StudentAdapter(
            this,
            studentList,
            onEditClick = { student ->
                // Nút Sửa trên item -> Sang Trang 3 để sửa
                val intent = Intent(this, EditAddActivity::class.java).apply {
                    putExtra("id", student.id)
                    putExtra("mssv", student.mssv)
                    putExtra("name", student.name)
                    putExtra("email", student.email)
                    putExtra("avatar", student.avatar)
                }
                startActivity(intent)
            },
            onDeleteClick = { student ->
                // Nút Xóa trên item -> Bật hộp thoại xác nhận
                AlertDialog.Builder(this)
                    .setTitle("Xác nhận")
                    .setMessage("Bạn có muốn xóa thông tin SV không?")
                    .setPositiveButton("Có") { _, _ ->
                        dbHelper.deleteStudent(student.id)
                        loadData()
                    }
                    .setNegativeButton("Không", null)
                    .show()
            }
        )
        listView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }
}