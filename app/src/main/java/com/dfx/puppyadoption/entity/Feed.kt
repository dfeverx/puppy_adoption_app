package com.dfx.puppyadoption.entity

sealed class Feed {
    class MainHeader(val title: String, val puppy: Puppy) : Feed()
    class Recommended(val pappies: List<Puppy>) : Feed()
    class More(val pappies: List<Puppy>) : Feed()
    class Liked(val pappies: List<Puppy>) : Feed()
}