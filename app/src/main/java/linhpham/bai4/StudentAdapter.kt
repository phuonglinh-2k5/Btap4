package linhpham.bai4

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView

class StudentAdapter(
    private val context: Context,
    private val studentList: List<Student>,
    private val onEditClick: (Student) -> Unit,
    private val onDeleteClick: (Student) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = studentList.size
    override fun getItem(position: Int): Any = studentList[position]
    override fun getItemId(position: Int): Long = studentList[position].id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_student, parent, false)
        val student = studentList[position]

        val tvName = view.findViewById<TextView>(R.id.tvItemName)
        val tvMssv = view.findViewById<TextView>(R.id.tvItemMssv)
        val btnEdit = view.findViewById<Button>(R.id.btnItemEdit)
        val btnDelete = view.findViewById<Button>(R.id.btnItemDelete)

        tvName.text = student.name
        tvMssv.text = student.mssv

        btnEdit.setOnClickListener { onEditClick(student) }
        btnDelete.setOnClickListener { onDeleteClick(student) }

        return view
    }
}