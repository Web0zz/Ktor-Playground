package com.web0zz.model

data class BlogEntry(val headline: String, val body: String)

val blogData = mutableListOf<BlogEntry>(
    BlogEntry(
        "First blog entry of my life",
        "Before beginning, Hello World."
    ),BlogEntry(
        "Second life",
        "Will I wake up when I die."
    ),BlogEntry(
        "Third role",
        "Is this real?"
    )
)