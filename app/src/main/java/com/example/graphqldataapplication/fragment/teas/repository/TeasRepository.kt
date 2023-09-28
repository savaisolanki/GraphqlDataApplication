package com.example.graphqldataapplication.fragment.teas.repository

import com.apollographql.apollo3.api.ApolloResponse
import com.example.graphqldataapplication.networkdata.ApolloClient
import com.example.graphqldatapplication.DeleteTeasMutation
import com.example.graphqldatapplication.TeasQuery
import com.example.graphqldatapplication.UpdateTeasMutation

class TeasRepository {
    suspend fun getTeas(): ApolloResponse<TeasQuery.Data> {
        return ApolloClient.apolloClient().query(TeasQuery()).execute()
    }

    suspend fun updateTeas(
        id: String,
        name: String,
        desc: String,
        price: Double
    ): ApolloResponse<UpdateTeasMutation.Data> {
        return ApolloClient.apolloClient()
            .mutation(UpdateTeasMutation(id = id, name = name, desc = desc, price = price))
            .execute()
    }


    suspend fun deleteTea(
        id: String
    ): ApolloResponse<DeleteTeasMutation.Data> {
        return ApolloClient.apolloClient().mutation(DeleteTeasMutation(id = id)).execute()
    }


}