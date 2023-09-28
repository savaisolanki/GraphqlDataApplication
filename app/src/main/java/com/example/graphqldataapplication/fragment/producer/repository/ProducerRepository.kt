package com.example.graphqldataapplication.fragment.producer.repository

import com.apollographql.apollo3.api.ApolloResponse
import com.example.graphqldataapplication.networkdata.ApolloClient
import com.example.graphqldatapplication.DeleteProducerMutation
import com.example.graphqldatapplication.DeleteTeasMutation
import com.example.graphqldatapplication.ProductListQuery
import com.example.graphqldatapplication.TeasQuery

class ProducerRepository {

    suspend fun getProducer(): ApolloResponse<ProductListQuery.Data> {
        return ApolloClient.apolloClient().query(ProductListQuery()).execute()
    }

    suspend fun deleteProducer(
        id: String
    ): ApolloResponse<DeleteProducerMutation.Data> {
        return ApolloClient.apolloClient().mutation(DeleteProducerMutation(id = id)).execute()
    }

}