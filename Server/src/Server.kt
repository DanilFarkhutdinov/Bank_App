import java.io.BufferedReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

class Server(val port: Int = 8080) {

    val serverSocket = ServerSocket(port)

    fun start(){
        var clientSocket: Socket? = null
        var dbHelper = DBHelper()
        try{
            clientSocket = serverSocket.accept()

            var bw = clientSocket.getInputStream().bufferedReader()
            val components = bw.readLine().split("\\s".toRegex()).toList()

            var pw = PrintWriter(clientSocket.getOutputStream())

            if (components[0] == "reg"){

                if (components[1] == "" || components[2] == "" || components[3] == "" || components[4] == "" || components[5] == ""){
                    pw.println("1_reg")
                }
                else{
                    if (dbHelper.check_login(components[1]) != null){
                        pw.println("2_reg")
                    }
                    else{
                        if (components[2] != components[5]){
                            pw.println("3_reg")
                        }
                        else{
                            dbHelper.insert(components[1], components[2], components[3], components[4])
                            pw.println("0_reg")
                        }
                    }
                }
            }

            if (components[0] == "auth"){
                if (components[1] == "" || components[2] == ""){
                    pw.println("1_auth")
                }
                else{
                    if(dbHelper.check_login(components[1]) == null){
                        pw.println("2_auth")
                    }
                    else{
                        if (components[2] != dbHelper.check_password(components[1])){
                            pw.println("3_auth")
                        }
                        else{
                            pw.println("0_auth")
                        }
                    }
                }
            }

            if(components[0] == "profile"){
                pw.println(dbHelper.get_info(components[1]))
            }

            pw.flush()
            bw.close()
            pw.close()


        }
        catch(e: Exception){
            println(e.message)
        }
        finally {
            clientSocket?.close()
            serverSocket.close()
        }
    }
}