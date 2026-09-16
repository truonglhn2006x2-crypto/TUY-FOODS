package com.example.tuyfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class ChatFragment : Fragment() {

    private lateinit var chatContainer: LinearLayout
    private lateinit var scrollView: ScrollView
    private lateinit var edtMessage: EditText
    private lateinit var btnSend: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_chat,
            container,
            false
        )

        chatContainer = view.findViewById(R.id.chatContainer)
        scrollView = view.findViewById(R.id.scrollView)
        edtMessage = view.findViewById(R.id.edtMessage)
        btnSend = view.findViewById(R.id.btnSend)

        // Tin nhắn chào mừng
        addMessage("Xin chào! Tôi có thể giúp bạn chọn món ăn hôm nay 😊", false)

        btnSend.setOnClickListener {
            val message = edtMessage.text.toString().trim()
            if (message.isEmpty()) return@setOnClickListener

            // Hiện tin nhắn user
            addMessage(message, true)
            edtMessage.setText("")

            // Gọi API chat
            lifecycleScope.launch {
                try {
                    val response = RetrofitClient.instance.chat(
                        mapOf("message" to message)
                    )
                    addMessage(response.reply, false)
                } catch (e: Exception) {
                    addMessage("Xin lỗi, tôi đang gặp sự cố. Vui lòng thử lại!", false)
                }
            }
        }

        return view
    }

    private fun addMessage(text: String, isUser: Boolean) {
        val textView = TextView(requireContext())
        textView.text = text
        textView.textSize = 15f
        textView.setPadding(24, 16, 24, 16)

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.topMargin = 8

        if (isUser) {
            params.gravity = android.view.Gravity.END
            params.marginStart = 80
            textView.setBackgroundColor(android.graphics.Color.parseColor("#E8192C"))
            textView.setTextColor(android.graphics.Color.WHITE)
        } else {
            params.gravity = android.view.Gravity.START
            params.marginEnd = 80
            textView.setBackgroundColor(android.graphics.Color.parseColor("#EEEEEE"))
            textView.setTextColor(android.graphics.Color.BLACK)
        }

        textView.layoutParams = params
        chatContainer.addView(textView)

        // Scroll xuống cuối
        scrollView.post {
            scrollView.fullScroll(ScrollView.FOCUS_DOWN)
        }
    }
}