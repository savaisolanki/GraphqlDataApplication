package com.example.graphqldataapplication.networkdata

import com.apollographql.apollo3.ApolloClient

object ApolloClient {

    private lateinit var apolloClient: ApolloClient


    fun apolloClient():ApolloClient{
        apolloClient = ApolloClient.Builder()
            .serverUrl("https://graphql-teas-endpoint.netlify.app/")
            .build()
        return apolloClient
    }

}