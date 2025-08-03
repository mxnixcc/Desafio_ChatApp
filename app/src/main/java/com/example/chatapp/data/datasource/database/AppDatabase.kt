package com.example.chatapp.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.chatapp.data.datasource.database.converters.Converters
import com.example.chatapp.data.datasource.database.dao.ChatRoomDao
import com.example.chatapp.data.datasource.database.dao.MessageDao
import com.example.chatapp.data.datasource.database.dao.UserDao
import com.example.chatapp.domain.model.ChatRoom
import com.example.chatapp.domain.model.Message
import com.example.chatapp.domain.model.User



/**
 * La clase principal de la base de datos Room para la aplicación de chat.
 */
@Database(
    entities = [User::class, ChatRoom::class, Message::class], // Entidades incluidas en la base de datos.
    version = 1, // Versión de la base de datos.
    exportSchema = false // No exportar el esquema, útil para proyectos pequeños.
)
@TypeConverters(Converters::class) // Aplica los convertidores de tipo personalizados.
abstract class AppDatabase : RoomDatabase() {
    /**
     * Retorna el DAO para las operaciones de usuario.
     * @return [UserDao]
     */
    abstract fun userDao(): UserDao

    /**
     * Retorna el DAO para las operaciones de sala de chat.
     * @return [ChatRoomDao]
     */
    abstract fun chatRoomDao(): ChatRoomDao

    /**
     * Retorna el DAO para las operaciones de mensaje.
     * @return [MessageDao]
     */
    abstract fun messageDao(): MessageDao
}