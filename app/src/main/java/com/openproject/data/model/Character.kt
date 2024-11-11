package com.openproject.data.model

data class Character(
    val id: Int, //	The id of the character.
    val name: String,//	The name of the character.
    var status: String,//	The status of the character ('Alive', 'Dead' or 'unknown').
    var species: String,//	The species of the character.
    var type: String,//	The type or subspecies of the character.
    var gender: String,//	The gender of the character ('Female', 'Male', 'Genderless' or 'unknown').
    var origin: Origin,//	Name and link to the character's origin location.
    val location: Location,//Name and link to the character's last known location endpoint.
    var image: String,// (url)	Link to the character's image. All images are 300x300px and most are medium shots or portraits since they are intended to be used as avatars.
    var episode: List<String>, // (urls)	List of episodes in which this character appeared.
    var url: String,// (url)	Link to the character's own URL endpoint.
    var created: String,//	Time at which the ch
)

data class Location(val name: String, val url: String)
data class Origin(val name: String, val url: String)