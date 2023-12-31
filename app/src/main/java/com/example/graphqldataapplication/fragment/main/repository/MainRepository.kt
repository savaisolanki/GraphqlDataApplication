package com.example.graphqldataapplication.fragment.main.repository

import com.apollographql.apollo3.api.ApolloResponse
import com.example.graphqldataapplication.networkdata.ApolloClient
import com.example.graphqldatapplication.AddProducerMutation


class MainRepository {

    suspend fun getProducerAdd(
        name: String,
        city: String
    ): ApolloResponse<AddProducerMutation.Data> {
        return ApolloClient.apolloClient().mutation(AddProducerMutation(name=name, location = city)).execute()
    }
}