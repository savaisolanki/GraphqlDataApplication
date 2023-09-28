package com.example.graphqldataapplication.networkdata

import com.apollographql.apollo3.ApolloClient
import com.example.graphqldataapplication.utils.ApplicationConstants


object ApolloClient {

    private lateinit var apolloClient: ApolloClient

    fun apolloClient(): ApolloClient {
        apolloClient = ApolloClient.Builder()
            .serverUrl(ApplicationConstants.BASE_URL).build()
        return apolloClient
    }


}