import Datos._

package object Itinerarios {

  def itinerarios(vuelos: List[Vuelo], aeropuertos: List[Aeropuerto]): (String, String) => List[Itinerario] = {

    def buscar(origen: String, destino: String, visitados: Set[String] = Set()): List[Itinerario] = {
      if (origen == destino) {
        // si el origen es igual al destino, retornamos una lista con una lista vacía (indicando que llegamos al destino)
        List(List())
      } else {
        // filtramos los vuelos que salen del origen y cuyos destinos no han sido visitados aún
        vuelos
          .filter(vuelo => vuelo.org == origen && !visitados.contains(vuelo.dst))
          .flatMap { vuelo =>
            /* llamada recursiva para buscar itinerarios desde el destino del vuelo actual al destino final
            agregamos el vuelo actual al conjunto de visitados para evitar ciclos
            */
            buscar(vuelo.dst, destino, visitados + origen)
              // agregamos el vuelo actual al inicio de cada itinerario encontrado
              .map(itinerario => vuelo :: itinerario)
          }
      }
    }

    // Retornamos una función que toma el origen y destino como parámetros y llama a la función de búsqueda
    (origen: String, destino: String) => buscar(origen, destino)
  }

  def itinerariosTiempo(vuelos: List[Vuelo], aeropuertos: List[Aeropuerto]): (String, String) => List[Itinerario] = {

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
      val tiempoEnAire = itinerario.map { vuelo =>
        val hlModificado = if (vuelo.hl < vuelo.hs) vuelo.hl + 24 else vuelo.hl
        (hlModificado - vuelo.hs) * 60 + vuelo.ml - vuelo.ms
      }.sum

      val tiemposEspera = for ((vuelo1, vuelo2) <- itinerario.zip(itinerario.tail)) yield {
        val hsModificado = if (vuelo2.hs < vuelo1.hl) vuelo2.hs + 24 else vuelo2.hs
        (hsModificado - vuelo1.hl) * 60 + vuelo2.ms - vuelo1.ml
      }


      tiempoEnAire + tiemposEspera
    }

    // Retornamos una función que toma el origen y destino como parámetros y llama a la función de búsqueda
    (origen: String, destino: String) =>
      buscar(origen, destino)
        .map(itinerario => (itinerario, tiempoTotal(itinerario)))
        .sortBy(_._2)
        .map(_._1)
        .take(3)
  }

  def itinerariosEscalas(vuelos: List[Vuelo], aeropuertos: List[Aeropuerto]): (String, String) => List[Itinerario] = {

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
        .map(itinerario => (itinerario, numeroEscalas(itinerario)))
        .sortBy(_._2)
        .map(_._1)
        .take(3)
  }


  def itinerariosAire(vuelos: List[Vuelo], aeropuertos: List[Aeropuerto]): (String, String) => List[Itinerario] = {

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
        .map(itinerario => (itinerario, tiempoTotal(itinerario)))
        .sortBy(_._2)
        .map(_._1)
        .take(3)
  }


  def itinerarioSalida(vuelos: List[Vuelo], aeropuertos: List[Aeropuerto]): (String, String, Int, Int) => Itinerario = {

    def tiempoEnMinutos(hora: Int, minuto: Int): Int = hora * 60 + minuto

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
      val itinerariosValidos = buscar(origen, destino).filter { itinerario =>
        tiempoEnMinutos(itinerario.last.hl, itinerario.last.ml) <= tiempoEnMinutos(hora, minuto)
      }
      if (itinerariosValidos.isEmpty) List()
      else itinerariosValidos.maxBy(itinerario => (itinerario.head.hs, itinerario.head.ms))
    }

    // Retornamos una función que toma origen, destino, hora y minuto, y retorna el itinerario optimizado
    (origen: String, destino: String, hora: Int, minuto: Int) => optimizarSalida(origen, destino, hora, minuto)
  }



}