package com.example.workoutgenerator

import android.util.Log
import com.adamratzman.spotify.SpotifyAppApi
import com.adamratzman.spotify.SpotifyClientApi
import com.adamratzman.spotify.models.SpotifyPublicUser
import com.adamratzman.spotify.models.SpotifySearchResult
import com.adamratzman.spotify.spotifyAppApi
import com.adamratzman.spotify.utils.Market

class SpotifyAPI {
    private val clientId = "1e375782478047af9f142bdea256c725"
    private val clientSecret = "e525e162b2b648c688bc81eb30ad3bc6"
    private var api : SpotifyAppApi? = null

    // pulls the cliend id and client secrete tokens and builds them into an object
    suspend fun buildSearchAPI() {
        api = spotifyAppApi(clientId, clientSecret).build()
    }

    public suspend fun getCurrSong() {
        val id = api?.search?.searchArtist("Cigarettes After Sex")?.get(0)?.id
        Log.e("spotify api", "${id?.let { api?.artists?.getArtistAlbums(it,50, market = Market.US) ?: toString() }}")
    }

    // performs spotify database query search for queries related to user info
    suspend fun userSearch(userQuery: String) : SpotifyPublicUser? {
        return api!!.users.getProfile(userQuery)
    }

    suspend fun trackSearch(searchQuery: String) : SpotifySearchResult {
        return api!!.search.searchAllTypes(searchQuery, 50, 1, market = Market.US)
    }

}