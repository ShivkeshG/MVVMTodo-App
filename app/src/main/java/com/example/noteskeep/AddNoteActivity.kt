package com.example.noteskeep

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.noteskeep.databinding.ActivityAddNoteBinding
import com.example.noteskeep.model.Note
import java.text.SimpleDateFormat
import java.util.*

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding

    private lateinit var oldNote: Note
    private lateinit var note: Note
    private var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            oldNote = intent.getSerializableExtra("current_note") as Note
            binding.etTitle.setText(oldNote.title)
            binding.etNote.setText(oldNote.note)
            isUpdate = true
        }catch (e: Exception) {
            e.printStackTrace()
        }

        binding.ivCheck.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val noteDesc = binding.etNote.text.toString()

            if (title.isNotEmpty() || noteDesc.isNotEmpty()) {
                val formatter = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss a", Locale.UK)

                note = if (isUpdate) {
                    Note(
                        oldNote.id,
                        title,
                        noteDesc,
                        formatter.format(Date())
                    )
                }else {
                    Note(
                        null,
                        title,
                        noteDesc,
                        formatter.format(Date())
                    )
                }

                val intent = Intent()
                intent.putExtra("note", note)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }else {
                Toast.makeText(this@AddNoteActivity, "Please enter some text", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
    }
}