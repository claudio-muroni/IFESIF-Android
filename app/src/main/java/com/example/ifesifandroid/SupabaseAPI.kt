package com.example.ifesifandroid

import kotlinx.serialization.Serializable
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.http.GET

@Serializable
data class President (
    val id: Int,
    val nome: String,
    val cognome: String,
    val attivo: Boolean,
    val cash: Int
)

@Serializable
data class Contract (
    val id: Int,

    @SerialName(value = "id_presidente")
    val idPresidente: Int,
    @SerialName(value = "nome_presidente")
    val nomePresidente: String,
    @SerialName(value = "cognome_presidente")
    val cognomePresidente: String,

    val ruolo: String,
    val giocatore: String,
    val anno: Int,
    val durata: Int,
    val prezzo: Int,

    @SerialName(value = "prezzo_rinnovo")
    val prezzoRinnovo: Int
)

@Serializable
data class RankingPosition (
    val id: Int,

    @SerialName(value = "id_stagione")
    val idStagione: Int,
    val anno: Int,

    val competizione: String,
    val posizione: Int,

    @SerialName(value = "id_presidente")
    val idPresidente: Int,
    @SerialName(value = "nome_presidente")
    val nomePresidente: String,
    @SerialName(value = "cognome_presidente")
    val cognomePresidente: String
)

private const val BASE_URL = "https://filxsmvgahceegvhzdee.supabase.co"
private const val ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZpbHhzbXZnYWhjZWVndmh6ZGVlIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Mzc4NDQyMzMsImV4cCI6MjA1MzQyMDIzM30.pfSVEKiUtrCDzfH844CER82ce13stzGjTVtCeCwaMhA"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface SupabaseApiService {

    @GET("rest/v1/presidenti?apikey=$ANON_KEY")
    suspend fun getPresidents(): List<President>

    @GET("rest/v1/contratti?apikey=$ANON_KEY")
    suspend fun getContracts(): List<Contract>

    @GET("rest/v1/classifiche?apikey=$ANON_KEY")
    suspend fun getRankingPositions(): List<RankingPosition>
}

object SupabaseApi {
    val retrofitService : SupabaseApiService by lazy {
        retrofit.create(SupabaseApiService::class.java)
    }
}

suspend fun main() {

    println("PRESIDENTI")
    val presidentList = SupabaseApi.retrofitService.getPresidents()
    for (pres in presidentList) {
        println(pres)
    }

    println("CONTRATTI")
    val contractList = SupabaseApi.retrofitService.getContracts()
    for (contract in contractList.filter { it.nomePresidente == "Flavio" }) {
        println(contract)
    }

    println("CLASSIFICHE")
    val rankingPositionList = SupabaseApi.retrofitService.getRankingPositions()
    for (rankingPosition in rankingPositionList.filter { it.anno == 2024 }) {
        println(rankingPosition)
    }

}