import Datos._
import common._
import Itinerarios._

import scala.collection.parallel.CollectionConverters._
import scala.collection.parallel.ParSeq

package object ItinerariosPar {

  def itinerariosPar(vuelos: List[Vuelo], aeropuertos: List[Aeropuerto]): (String, String) => List[Itinerario] = {

    def buscar(origen: String, destino: String, visitados: Set[String] = Set()): List[Itinerario] = {
      if (origen == destino) {
        // si el origen es igual al destino, retornamos una lista con una lista vacía (indicando que llegamos al destino)
        List(List())
      } else {
        // filtramos los vuelos que salen del origen y cuyos destinos no han sido visitados aún
        vuelos
          .filter(vuelo => vuelo.org == origen && !visitados.contains(vuelo.dst))
          .par.flatMap { vuelo =>
            /* llamada recursiva para buscar itinerarios desde el destino del vuelo actual al destino final
            agregamos el vuelo actual al conjunto de visitados para evitar ciclos
            */
            buscar(vuelo.dst, destino, visitados + origen)
              // agregamos el vuelo actual al inicio de cada itinerario encontrado
              .map(itinerario => vuelo :: itinerario)
          }.toList
      }
    }

    // Retornamos una función que toma el origen y destino como parámetros y llama a la función de búsqueda
    (origen: String, destino: String) => buscar(origen, destino)
  }

  def itinerariosTiempoPar(vuelos: List[Vuelo], aeropuertos: List[Aeropuerto]): (String, String) => List[Itinerario] = {

    // Función recursiva interna que realiza la búsqueda de itinerarios
    def buscar(origen: String, destino: String, visitados: Set[String] = Set()): List[Itinerario] = {
      if (origen == destino) List(List())
      else {
        vuelos
          .filter(vuelo => vuelo.org == origen && !visitados.contains(vuelo.dst))
          .flatMap(vuelo => buscar(vuelo.dst, destino, visitados + origen).map(vuelo :: _))
      }
    }

    // Función para calcular el tiempo total de un itinerario
    def tiempoTotal(itinerario: List[Vuelo]): Int = {
      val tiempoEnAire = itinerario.par.map { vuelo =>
        val hlModificado = if (vuelo.hl < vuelo.hs) vuelo.hl + 24 else vuelo.hl
        (hlModificado - vuelo.hs) * 60 + vuelo.ml - vuelo.ms
      }.sum

      val tiemposEspera = (itinerario.par.zip(itinerario.tail.par)).map { case (vuelo1, vuelo2) =>
        val hsModificado = if (vuelo2.hs < vuelo1.hl) vuelo2.hs + 24 else vuelo2.hs
        (hsModificado - vuelo1.hl) * 60 + vuelo2.ms - vuelo1.ml
      }.sum

      tiempoEnAire + tiemposEspera
    }

    (origen: String, destino: String) =>
      buscar(origen, destino)
        .par.map(itinerario => (itinerario, tiempoTotal(itinerario))).toList
        .sortBy(_._2)
        .map(_._1)
        .take(3)
  }

  def itinerariosEscalasPar(vuelos: List[Vuelo], aeropuertos: List[Aeropuerto]): (String, String) => List[Itinerario] = {

    // Función recursiva interna que realiza la búsqueda de itinerarios
    def buscar(origen: String, destino: String, visitados: Set[String] = Set()): List[Itinerario] = {
      if (origen == destino) List(List())
      else {
        vuelos
          .filter(vuelo => vuelo.org == origen && !visitados.contains(vuelo.dst))
          .flatMap(vuelo => buscar(vuelo.dst, destino, visitados + origen).map(vuelo :: _))
      }
    }

    // Función para calcular el número de escalas de un itinerario
    def numeroEscalas(itinerario: List[Vuelo]): Int = itinerario.length

    // Retornamos una función que toma el origen y destino como parámetros y llama a la función de búsqueda
    (origen: String, destino: String) =>
      buscar(origen, destino)
        .par.map(itinerario => (itinerario, numeroEscalas(itinerario))).toList
        .sortBy(_._2)
        .map(_._1)
        .take(3)
  }


  def itinerariosAirePar(vuelos: List[Vuelo], aeropuertos: List[Aeropuerto]): (String, String) => List[Itinerario] = {

    // Función recursiva interna que realiza la búsqueda de itinerarios
    def buscar(origen: String, destino: String, visitados: Set[String] = Set()): List[Itinerario] = {
      if (origen == destino) List(List())
      else {
        vuelos
          .filter(vuelo => vuelo.org == origen && !visitados.contains(vuelo.dst))
          .flatMap(vuelo => buscar(vuelo.dst, destino, visitados + origen).map(vuelo :: _))
      }
    }

    // Función para calcular el tiempo total en aire de un itinerario
    def tiempoTotal(itinerario: List[Vuelo]): Int = {
      itinerario.map { vuelo =>
        val hlModificado = if (vuelo.hl < vuelo.hs) vuelo.hl + 24 else vuelo.hl
        (hlModificado - vuelo.hs) * 60 + vuelo.ml - vuelo.ms
      }.sum
    }

    // Retornamos una función que toma el origen y destino como parámetros y llama a la función de búsqueda
    (origen: String, destino: String) =>
      buscar(origen, destino)
        .par.map(itinerario => (itinerario, tiempoTotal(itinerario))).toList
        .sortBy(_._2)
        .map(_._1)
        .take(3)
  }


  def itinerarioSalidaPar(vuelos: List[Vuelo], aeropuertos: List[Aeropuerto]): (String, String, Int, Int) => Itinerario = {

    // Función para convertir horas y minutos a minutos totales en el día
    def tiempoEnMinutos(hora: Int, minuto: Int): Int = hora * 60 + minuto

    // Función recursiva interna que realiza la búsqueda de itinerarios
    def buscar(origen: String, destino: String, visitados: Set[String] = Set()): List[Itinerario] = {
      if (origen == destino) List(List())
      else {
        vuelos
          .filter(vuelo => vuelo.org == origen && !visitados.contains(vuelo.dst))
          .flatMap(vuelo => buscar(vuelo.dst, destino, visitados + origen).map(vuelo :: _))
      }
    }

    // Función para optimizar la salida del itinerario
    def optimizarSalida(origen: String, destino: String, hora: Int, minuto: Int): Itinerario = {
      val itinerariosValidos = buscar(origen, destino).par.filter { itinerario =>
        tiempoEnMinutos(itinerario.last.hl, itinerario.last.ml) <= tiempoEnMinutos(hora, minuto)
      }.seq
      if (itinerariosValidos.isEmpty) List()
      else itinerariosValidos.maxBy(itinerario => (itinerario.head.hs, itinerario.head.ms))
    }

    // Retornamos una función que toma origen, destino, hora y minuto, y retorna el itinerario optimizado
    (origen: String, destino: String, hora: Int, minuto: Int) => optimizarSalida(origen, destino, hora, minuto)
  }


}
