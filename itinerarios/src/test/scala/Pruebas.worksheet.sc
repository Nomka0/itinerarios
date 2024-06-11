
import Datos._
import Itinerarios._
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
val itsB1 = itinerarios(vuelosB1, aeropuertos.toList)
val itsTiempoB1 = itinerariosTiempo(vuelosB1, aeropuertos.toList)
val itsEscalasB1 = itinerariosEscalas(vuelosB1, aeropuertos.toList)
val itsAireB1 = itinerariosAire(vuelosB1, aeropuertos.toList)
val itSalidaB1 = itinerarioSalida(vuelosB1, aeropuertos.toList)

val itsC1 = itinerarios(vuelosC1, aeropuertos.toList)
val itsTiempoC1 = itinerariosTiempo(vuelosC1, aeropuertos.toList)
val itsEscalasC1 = itinerariosEscalas(vuelosC1, aeropuertos.toList)
val itsAireC1 = itinerariosAire(vuelosC1, aeropuertos.toList)
val itsSalidaC1 = itinerarioSalida(vuelosC1, aeropuertos.toList)

val its200C = itinerarios(vuelosC1 ++ vuelosC2, aeropuertos.toList)
val itsTiempo200C = itinerariosTiempo(vuelosC1 ++ vuelosC2, aeropuertos.toList)
val itsEscalas200C = itinerariosEscalas(vuelosC1 ++ vuelosC2, aeropuertos.toList)
val itsAire200C = itinerariosAire(vuelosC1 ++ vuelosC2, aeropuertos.toList)
val itsSalida200C = itinerarioSalida(vuelosC1 ++ vuelosC2, aeropuertos.toList)



val (resultado1, tiempo1) = tiempoResultado(itsB1("PHX", "DFW"))
println(s"Resultado itsCurso PHX-DFW: $resultado1, Tiempo: $tiempo1")
val (resultado2, tiempo2) = tiempoResultado(itsB1("CLO", "SVO"))
println(s"Resultado itsCurso CLO-SVO: $resultado2, Tiempo: $tiempo2")
val (resultado3, tiempo3) = tiempoResultado(itsB1("CLO", "MEX"))
println(s"Resultado itsCurso CLO-MEX: $resultado3, Tiempo: $tiempo3")
val (resultado4, tiempo4) = tiempoResultado(itsB1("CTG", "PTY"))
println(s"Resultado itsCurso CTG-PTY: $resultado4, Tiempo: $tiempo4")
// Prueba itinerariosTiempo
val (resultado5, tiempo5) = tiempoResultado(itsTiempoB1("MID", "SVCS"))
println(s"Resultado itsTiempoCurso MID-SVCS: $resultado5, Tiempo: $tiempo5")
val (resultado6, tiempo6) = tiempoResultado(itsTiempoB1("CLO", "SVCS"))
println(s"Resultado itsTiempoCurso CLO-SVCS: $resultado6, Tiempo: $tiempo6")
val (resultado7, tiempo7) = tiempoResultado(itsTiempoB1("CLO", "SVO"))
println(s"Resultado itsTiempoCurso CLO-SVO: $resultado7, Tiempo: $tiempo7")
val (resultado8, tiempo8) = tiempoResultado(itsTiempoB1("CLO", "MEX"))
println(s"Resultado itsTiempoCurso CLO-MEX: $resultado8, Tiempo: $tiempo8")
val (resultado9, tiempo9) = tiempoResultado(itsTiempoB1("CTG", "PTY"))
println(s"Resultado itsTiempoCurso CTG-PTY: $resultado9, Tiempo: $tiempo9")
// Prueba itinerariosEscalas
val (resultado10, tiempo10) = tiempoResultado(itsEscalasB1("MID", "SVCS"))
println(s"Resultado itsEscalasCurso MID-SVCS: $resultado10, Tiempo: $tiempo10")
val (resultado11, tiempo11) = tiempoResultado(itsEscalasB1("CLO", "SVCS"))
println(s"Resultado itsEscalasCurso CLO-SVCS: $resultado11, Tiempo: $tiempo11")
val (resultado12, tiempo12) = tiempoResultado(itsEscalasB1("CLO", "SVO"))
println(s"Resultado itsEscalasCurso CLO-SVO: $resultado12, Tiempo: $tiempo12")
val (resultado13, tiempo13) = tiempoResultado(itsEscalasB1("CLO", "MEX"))
println(s"Resultado itsEscalasCurso CLO-MEX: $resultado13, Tiempo: $tiempo13")
val (resultado14, tiempo14) = tiempoResultado(itsEscalasB1("CTG", "PTY"))
println(s"Resultado itsEscalasCurso CTG-PTY: $resultado14, Tiempo: $tiempo14")
// Prueba itinerariosAire
val (resultado15, tiempo15) = tiempoResultado(itsAireB1("MID", "SVCS"))
println(s"Resultado itsAireCurso MID-SVCS: $resultado15, Tiempo: $tiempo15")
val (resultado16, tiempo16) = tiempoResultado(itsAireB1("CLO", "SVCS"))
println(s"Resultado itsAireCurso CLO-SVCS: $resultado16, Tiempo: $tiempo16")
val (resultado17, tiempo17) = tiempoResultado(itsAireB1("CLO", "SVO"))
println(s"Resultado itsAireCurso CLO-SVO: $resultado17, Tiempo: $tiempo17")
val (resultado18, tiempo18) = tiempoResultado(itsAireB1("CLO", "MEX"))
println(s"Resultado itsAireCurso CLO-MEX: $resultado18, Tiempo: $tiempo18")
val (resultado19, tiempo19) = tiempoResultado(itsAireB1("CTG", "PTY"))
println(s"Resultado itsAireCurso CTG-PTY: $resultado19, Tiempo: $tiempo19")
// Prueba itinerarioSalida
val (resultado20, tiempo20) = tiempoResultado(itSalidaB1("CTG", "PTY", 11, 40))
println(s"Resultado itSalidaCurso CTG-PTY 11:40: $resultado20, Tiempo: $tiempo20")
val (resultado21, tiempo21) = tiempoResultado(itSalidaB1("CTG", "PTY", 11, 55))
println(s"Resultado itSalidaCurso CTG-PTY 11:55: $resultado21, Tiempo: $tiempo21")
val (resultado22, tiempo22) = tiempoResultado(itSalidaB1("CTG", "PTY", 10, 30))
println(s"Resultado itSalidaCurso CTG-PTY 10:30: $resultado22, Tiempo: $tiempo22")
// Pruebas con datos adicionales
val (resultado23, tiempo23) = tiempoResultado(itsC1("ORD", "TPA"))
println(s"Resultado its100C1 ORD-TPA: $resultado23, Tiempo: $tiempo23")
val (resultado24, tiempo24) = tiempoResultado(itsTiempoC1("ORD", "TPA"))
println(s"Resultado itsTiempo100C1 ORD-TPA: $resultado24, Tiempo: $tiempo24")
val (resultado25, tiempo25) = tiempoResultado(itsEscalasC1("ORD", "TPA"))
println(s"Resultado itsEsc100C1 ORD-TPA: $resultado25, Tiempo: $tiempo25")
val (resultado26, tiempo26) = tiempoResultado(itsAireC1("ORD", "TPA"))
println(s"Resultado itsAire100C1 ORD-TPA: $resultado26, Tiempo: $tiempo26")
val (resultado27, tiempo27) = tiempoResultado(itsSalidaC1("ORD", "TPA", 18, 30))
println(s"Resultado itsSalida100C1 ORD-TPA: $resultado27, Tiempo: $tiempo27")

val (resultado28, tiempo28) = tiempoResultado(its200C("ORD", "TPA"))
println(s"Resultado its200C ORD-TPA: $resultado28, Tiempo: $tiempo28")

val (resultado29, tiempo29) = tiempoResultado(itsTiempo200C("ORD", "TPA"))
println(s"Resultado itsTiempo200C ORD-TPA: $resultado29, Tiempo: $tiempo29")

val (resultado30, tiempo30) = tiempoResultado(itsEscalas200C("ORD", "TPA"))
println(s"Resultado itsEscalas200C ORD-TPA: $resultado30, Tiempo: $tiempo30")

val (resultado31, tiempo31) = tiempoResultado(itsAire200C("ORD", "TPA"))
println(s"Resultado itsAire200C ORD-TPA: $resultado31, Tiempo: $tiempo31")

val (resultado32, tiempo32) = tiempoResultado(itsSalida200C("ORD", "TPA", 18, 30))
println(s"Resultado itsSalida200C ORD-TPA: $resultado32, Tiempo: $tiempo32")
