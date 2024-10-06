package com.debanshudatta.network.domain.di

import com.debanshudatta.network.domain.usecase.ClientWrapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideNetworkClient(): HttpClient {
        return HttpClient(OkHttp) {
            defaultRequest { url("https://api.themoviedb.org/3/") }
            install(Logging) {
                logger = Logger.SIMPLE
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens(
                            "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkZDZkNDMzZTZkY2I3NzZiMmY0ODMwY2Q5ZmEwZWFmNyIsIm5iZiI6MTcyODE1MjU1Ni40NTkzNzcsInN1YiI6IjVlZDI1YTg5NTI4YjJlMDAxZTY5MGQ2YiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.kpgM8ybcsPZLTwrJHQOmXYLE_SVYYAcVf3dExU2sCv0",
                            "",
                        )
                    }
                }
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    encodeDefaults = true
                    ignoreUnknownKeys = true
                })
            }
        }
    }

    @Singleton
    @Provides
    fun provideClientWrapper(networkClient: HttpClient): ClientWrapper {
        return ClientWrapper(networkClient)
    }
}