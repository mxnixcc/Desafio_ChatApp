package com.example.chatapp.domain.usecase

import com.example.chatapp.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * Caso de uso para cerrar la sesión del usuario actual.
 */
class LogoutUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    /**
     * Ejecuta el caso de uso para cerrar la sesión.
     */
    suspend operator fun invoke() {
        authRepository.logoutUser()
    }
}