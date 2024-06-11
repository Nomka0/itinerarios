
import Datos._
import ItinerariosPar._
import org.scalameter._

def tiempoResultado[T](body: => T): (T, Quantity[Double]) = {
  val configMedida = config(
    KeyValue(Key.exec.minWarmupRuns -> 20),
    KeyValue(Key.exec.maxWarmupRuns -> 60),
    KeyValue(Key.verbose -> false)
  )
  val tiempo = configMedida withWarmer(new Warmer.Default) measure (body)
  (body, tiempo)
}


// Funciones de itinerarios
val itsB1Par = itinerariosPar(vuelosB1, aeropuertos.toList)
val itsTiempoB1Par = itinerariosTiempoPar(vuelosB1, aeropuertos.toList)
val itsEscalasB1Par = itinerariosEscalasPar(vuelosB1, aeropuertos.toList)
val itsAireB1Par = itinerariosAirePar(vuelosB1, aeropuertos.toList)
val itSalidaB1Par = itinerarioSalidaPar(vuelosB1, aeropuertos.toList)

val itsC1Par = itinerariosPar(vuelosC1, aeropuertos.toList)
val itsTiempoC1Par = itinerariosTiempoPar(vuelosC1, aeropuertos.toList)
val itsEscalasC1Par = itinerariosEscalasPar(vuelosC1, aeropuertos.toList)
val itsAireC1Par = itinerariosAirePar(vuelosC1, aeropuertos.toList)
val itsSalidaC1Par = itinerarioSalidaPar(vuelosC1, aeropuertos.toList)

val its200CPar = itinerariosPar(vuelosC1 ++ vuelosC2, aeropuertos.toList)
val itsTiempo200CPar = itinerariosTiempoPar(vuelosC1 ++ vuelosC2, aeropuertos.toList)
val itsEscalas200CPar = itinerariosEscalasPar(vuelosC1 ++ vuelosC2, aeropuertos.toList)
val itsAire200CPar = itinerariosAirePar(vuelosC1 ++ vuelosC2, aeropuertos.toList)
val itsSalida200CPar = itinerarioSalidaPar(vuelosC1 ++ vuelosC2, aeropuertos.toList)


val (resultado1, tiempo1) = tiempoResultado(itsB1Par("MID", "SVCS"))
println(s"Resultado itsCursoPar MID-SVCS: $resultado1, Tiempo: $tiempo1")
val (resultado2, tiempo2) = tiempoResultado(itsB1Par("CLO", "SVCS"))
println(s"Resultado itsCursoPar CLO-SVCS: $resultado2, Tiempo: $tiempo2")
val (resultado3, tiempo3) = tiempoResultado(itsB1Par("CLO", "SVO"))
println(s"Resultado itsCursoPar CLO-SVO: $resultado3, Tiempo: $tiempo3")
val (resultado4, tiempo4) = tiempoResultado(itsB1Par("CLO", "MEX"))
println(s"Resultado itsCursoPar CLO-MEX: $resultado4, Tiempo: $tiempo4")
val (resultado5, tiempo5) = tiempoResultado(itsB1Par("CTG", "PTY"))
println(s"Resultado itsCursoPar CTG-PTY: $resultado5, Tiempo: $tiempo5")

// Prueba itinerariosTiempo
val (resultado6, tiempo6) = tiempoResultado(itsTiempoB1Par("MID", "SVCS"))
println(s"Resultado itsTiempoCursoPar MID-SVCS: $resultado6, Tiempo: $tiempo6")
val (resultado7, tiempo7) = tiempoResultado(itsTiempoB1Par("CLO", "SVCS"))
println(s"Resultado itsTiempoCursoPar CLO-SVCS: $resultado7, Tiempo: $tiempo7")
val (resultado8, tiempo8) = tiempoResultado(itsTiempoB1Par("CLO", "SVO"))
println(s"Resultado itsTiempoCursoPar CLO-SVO: $resultado8, Tiempo: $tiempo8")
val (resultado9, tiempo9) = tiempoResultado(itsTiempoB1Par("CLO", "MEX"))
println(s"Resultado itsTiempoCursoPar CLO-MEX: $resultado9, Tiempo: $tiempo9")
val (resultado10, tiempo10) = tiempoResultado(itsTiempoB1Par("CTG", "PTY"))
println(s"Resultado itsTiempoCursoPar CTG-PTY: $resultado10, Tiempo: $tiempo10")

// Prueba itinerariosEscalas
val (resultado11, tiempo11) = tiempoResultado(itsEscalasB1Par("MID", "SVCS"))
println(s"Resultado itsEscalasCursoPar MID-SVCS: $resultado11, Tiempo: $tiempo11")
val (resultado12, tiempo12) = tiempoResultado(itsEscalasB1Par("CLO", "SVCS"))
println(s"Resultado itsEscalasCursoPar CLO-SVCS: $resultado12, Tiempo: $tiempo12")
val (resultado13, tiempo13) = tiempoResultado(itsEscalasB1Par("CLO", "SVO"))
println(s"Resultado itsEscalasCursoPar CLO-SVO: $resultado13, Tiempo: $tiempo13")
val (resultado14, tiempo14) = tiempoResultado(itsEscalasB1Par("CLO", "MEX"))
println(s"Resultado itsEscalasCursoPar CLO-MEX: $resultado14, Tiempo: $tiempo14")
val (resultado15, tiempo15) = tiempoResultado(itsEscalasB1Par("CTG", "PTY"))
println(s"Resultado itsEscalasCursoPar CTG-PTY: $resultado15, Tiempo: $tiempo15")
// Prueba itinerariosAire
val (resultado16, tiempo16) = tiempoResultado(itsAireB1Par("MID", "SVCS"))
println(s"Resultado itsAireCursoPar MID-SVCS: $resultado16, Tiempo: $tiempo16")
val (resultado17, tiempo17) = tiempoResultado(itsAireB1Par("CLO", "SVCS"))
println(s"Resultado itsAireCursoPar CLO-SVCS: $resultado17, Tiempo: $tiempo17")
val (resultado18, tiempo18) = tiempoResultado(itsAireB1Par("CLO", "SVO"))
println(s"Resultado itsAireCursoPar CLO-SVO: $resultado18, Tiempo: $tiempo18")
val (resultado19, tiempo19) = tiempoResultado(itsAireB1Par("CLO", "MEX"))
println(s"Resultado itsAireCursoPar CLO-MEX: $resultado19, Tiempo: $tiempo19")
val (resultado20, tiempo20) = tiempoResultado(itsAireB1Par("CTG", "PTY"))
println(s"Resultado itsAireCursoPar CTG-PTY: $resultado20, Tiempo: $tiempo20")
// Prueba itinerarioSalida
val (resultado21, tiempo21) = tiempoResultado(itSalidaB1Par("CTG", "PTY", 11, 40))
println(s"Resultado itSalidaCursoPar CTG-PTY 11:40: $resultado21, Tiempo: $tiempo21")
val (resultado22, tiempo22) = tiempoResultado(itSalidaB1Par("CTG", "PTY", 11, 55))
println(s"Resultado itSalidaCursoPar CTG-PTY 11:55: $resultado22, Tiempo: $tiempo22")
val (resultado23, tiempo23) = tiempoResultado(itSalidaB1Par("CTG", "PTY", 10, 30))
println(s"Resultado itSalidaCursoPar CTG-PTY 10:30: $resultado23, Tiempo: $tiempo23")
// Pruebas  adicionales
val (resultado24, tiempo24) = tiempoResultado(itsC1Par("ORD", "TPA"))
println(s"Resultado its100C1Par ORD-TPA: $resultado24, Tiempo: $tiempo24")
val (resultado25, tiempo25) = tiempoResultado(itsTiempoC1Par("ORD", "TPA"))
println(s"Resultado itsTiempo100C1Par ORD-TPA: $resultado25, Tiempo: $tiempo25")
val (resultado26, tiempo26) = tiempoResultado(itsEscalasC1Par("ORD", "TPA"))
println(s"Resultado itsEsc100C1Par ORD-TPA: $resultado26, Tiempo: $tiempo26")
val (resultado27, tiempo27) = tiempoResultado(itsAireC1Par("ORD", "TPA"))
println(s"Resultado itsAire100C1Par ORD-TPA: $resultado27, Tiempo: $tiempo27")
