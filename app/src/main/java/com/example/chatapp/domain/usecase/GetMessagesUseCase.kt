package com.example.chatapp.domain.usecase


import com.example.chatapp.domain.model.Message
import com.example.chatapp.domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {
    operator fun invoke(chatId: String): Flow<List<Message>> {
        return messageRepository.getMessages(chatId)
    }
}