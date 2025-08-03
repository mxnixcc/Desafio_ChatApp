package com.example.chatapp.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.chatapp.domain.model.User

/**
 * Interfaz DAO (Data Access Object) para operaciones con la tabla de usuarios.
 */
@Dao
interface UserDao {
    /**
     * Inserta un usuario en la base de datos o lo reemplaza si ya existe.
     * @param user El usuario a insertar.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    /**
     * Obtiene un usuario por su ID.
     * @param userId El ID del usuario.
     * @return El usuario si se encuentra, null de lo contrario.
     */
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: String): User?

    /**
     * Elimina todos los usuarios de la tabla.
     */
    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()

    /**
     * Obtiene el usuario actual autenticado de la base de datos local.
     * @return El usuario actual o null si no está autenticado.
     */
    @Query("SELECT * FROM users LIMIT 1")
    suspend fun getCurrentUser(): User?
}