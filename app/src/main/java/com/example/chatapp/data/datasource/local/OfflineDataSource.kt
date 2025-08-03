package com.example.chatapp.data.datasource.local

import com.example.chatapp.data.database.dao.ChatRoomDao
import com.example.chatapp.data.database.dao.MessageDao
import com.example.chatapp.data.database.dao.UserDao
import com.example.chatapp.domain.model.ChatRoom
import com.example.chatapp.domain.model.Message
import com.example.chatapp.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Fuente de datos local para la persistencia offline utilizando Room Database.
 */
@Singleton
class OfflineDataSource @Inject constructor(
    private val userDao: UserDao,
    private val chatRoomDao: ChatRoomDao,
    private val messageDao: MessageDao
) {
    /**
     * Guarda un usuario en la base de datos local.
     * @param user El usuario a guardar.
     */
    suspend fun saveUser(user: User) {
        userDao.insertUser(user)
    }

    /**
     * Obtiene un usuario por su ID de la base de datos local.
     * @param userId El ID del usuario.
     * @return El usuario si se encuentra, null de lo contrario.
     */
    suspend fun getUserById(userId: String): User? {
        return userDao.getUserById(userId)
    }

    /**
     * Guarda una lista de salas de chat en la base de datos local.
     * @param chatRooms La lista de salas a guardar.
     */
    suspend fun saveChatRooms(chatRooms: List<ChatRoom>) {
        chatRoomDao.insertAllChatRooms(chatRooms)
    }

    /**
     * Obtiene un flujo de salas de chat desde la base de datos local.
     * @return Un [Flow] que emite la lista de [ChatRoom].
     */
    fun getChatRooms(): Flow<List<ChatRoom>> {
        return chatRoomDao.getAllChatRooms()
    }

    /**
     * Guarda una lista de mensajes para una sala específica en la base de datos local.
     * @param messages La lista de mensajes a guardar.
     */
    suspend fun saveMessages(messages: List<Message>) {
        messageDao.insertAllMessages(messages)
    }

    /**
     * Obtiene un flujo de mensajes para una sala específica desde la base de datos local.
     * @param roomId El ID de la sala.
     * @return Un [Flow] que emite la lista de [Message].
     */
    fun getMessages(roomId: String): Flow<List<Message>> {
        return messageDao.getMessagesForRoom(roomId)
    }

    /**
     * Añade un solo mensaje a la base de datos local de una sala.
     * @param message El mensaje a añadir.
     */
    suspend fun addMessage(message: Message) {
        messageDao.insertMessage(message)
    }

    /**
     * Actualiza el estado de un mensaje en la base de datos local.
     * @param messageId El ID del mensaje a actualizar.
     * @param newStatus El nuevo estado del mensaje.
     */
    suspend fun updateMessageStatus(messageId: String, newStatus: Message.MessageStatus) {
        // Primero, obtener el mensaje para actualizarlo
        val message = messageDao.getMessagesForRoom(messageId).firstOrNull()?.find { msg -> msg.id == messageId }
        message?.let {
            messageDao.updateMessage(it.copy(status = newStatus))
        }
    }

    /**
     * Limpia todos los datos almacenados en caché (base de datos local).
     */
    suspend fun clearAllData() {
        userDao.deleteAllUsers()
        chatRoomDao.deleteAllChatRooms()
        messageDao.deleteAllMessages()
    }

    /**
     * Obtiene el usuario actual autenticado de la base de datos local.
     * @return El usuario actual o null si no está autenticado.
     */
    suspend fun getCurrentUser(): User? {
        return userDao.getCurrentUser()
    }

}