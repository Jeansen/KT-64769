import libuuid.uuid_generate_random
import libuuid.uuid_t

val uuid: String by lazy {
    var x: uuid_t? = null
    uuid_generate_random(x)
    x.toString()
}

fun main() {
    println(uuid)
}
