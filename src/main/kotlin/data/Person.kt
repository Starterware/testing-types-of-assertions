package data

class Person(
    val firstName: String,
    val lastName: String,
    val age: Int,
    val address: Address?,
    val pets: MutableList<String>,
) {

    private constructor(builder: Builder) : this(builder.firstName, builder.lastName, builder.age, builder.address, builder.pets)

    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        var firstName: String = ""
        var lastName: String = ""
        var age: Int = -1
        var address: Address? = null
        val pets: MutableList<String> = mutableListOf()

        fun build() = Person(this)
    }
}