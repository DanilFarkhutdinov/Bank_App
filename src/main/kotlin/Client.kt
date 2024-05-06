import java.io.BufferedReader
import java.io.PrintWriter
import java.net.Socket

class Client() {
    val host: String = "localhost"
    val port: Int = 8080
    var socket: Socket? = null
    var pw: PrintWriter? = null
    var br: BufferedReader? = null

    var login: String? = null


    fun start(){
        try{
            socket = Socket(host, port)
        }
        catch (e: Exception){
            println(e.message)
        }
    }

    fun push(str: String){
        pw = PrintWriter(socket?.getOutputStream())
        pw?.println(str)
        pw?.flush()
    }

    fun pull(): String?{
        br = socket?.getInputStream()?.bufferedReader()
      return br?.readLine()
    }

    fun finish(){
        pw?.close()
        br?.close()
        socket?.close()
    }
}