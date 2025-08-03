package com.example.chatapp.domain.usecase


import com.example.chatapp.domain.model.Message
import com.example.chatapp.domain.repository.MessageRepository
import javax.inject.Inject


/**
 * Caso de uso para enviar un mensaje en una sala de chat.
 */
class SendMessageUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {
    /**
     * Invoca el caso de uso para enviar un mensaje.
     * @param message El objeto Message a enviar.
     * @return Un [Result] que indica el éxito o fracaso de la operación.
     */
    suspend operator fun invoke(message: Message): Unit {
        return messageRepository.sendMessage(message)
    }
}