package com.gabriel.ribeiro.newsapp.models.responses

import com.gabriel.ribeiro.newsapp.models.Article

data class NewResponse(val status: String, val articles : List<Article>) {
}