package Mongo

import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import com.mongodb.client.model.Indexes.descending
import java.util.logging.Level
import java.util.logging.LogManager

fun main() {
    LogManager.getLogManager().getLogger("").setLevel(Level.SEVERE)
    val con = MongoClient(MongoClientURI("mongodb://ad:ieselcaminas@89.36.214.106/?authSource=test"))
    val bd = con.getDatabase("test")

    val estadisticaInternet = bd.getCollection("EstadisticaInternet")


    val estadisticas = estadisticaInternet.find().sort(descending("InternetDiari"))

    for (estadistica in estadisticas) {
        val internetDiari = estadistica.get("InternetDiari").toString().replace(",", ".")
        val comprat3Mesos = estadistica.get("Comprat3Mesos").toString().replace(",", ".")
        if (internetDiari.toFloat() > 80.0 && comprat3Mesos.toFloat() in 50.0..55.0) {
            println(
                estadistica.get("Nom").toString() + "\t" + estadistica.get("InternetDiari")
                    .toString() + "\t" + estadistica.get("Comprat3Mesos").toString()
            )
        }


    }
}