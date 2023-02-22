import redis.clients.jedis.Jedis

val connection = Jedis("89.36.214.106")

fun main(args: Array<String>) {

    //He dejado una captura del XQuery en exide en la root del proyecto
    connection.auth("ieselcaminas.ad")

    val lista = connection.keys("*").toTypedArray()

    connection.connect()

    connection.del("BomboNums_311")

    for (numeros in 1..25) {
        connection.sadd("BomboNums_311", "$numeros")
    }

    bombo()

}

fun bombo() {

    connection.del("BomboPremis_311")

    connection.sadd("BomboPremis_311", "Primer premio")
    connection.sadd("BomboPremis_311", "Segundo premio")
    connection.sadd("BomboPremis_311", "Tercer premio")

    println("¿Cuantas pedreas quieres?")

    var pedreas = readln().toInt()
    if (pedreas < 0)
        pedreas = 0

    if (pedreas > 0) {
        for (i in 1..pedreas) {
            connection.sadd("BomboPremis_311", "Pedrea $i")
        }
    }

    for (i in 1..3 + pedreas) {
        println(connection.spop("BomboPremis_311") + ": " + connection.spop("BomboNums_311"))
    }


    println("Numeros no premiados: " + connection.smembers("BomboNums_311"))
}